package com.lalith.ecommerce.email;

import com.lalith.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lalith.ecommerce.email.EmailTemplate.ORDER_CONFIRMATION;
import static com.lalith.ecommerce.email.EmailTemplate.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    )throws MessagingException{
        if (destinationEmail == null || destinationEmail.isBlank()) {
            log.warn("Cannot send payment email: destination email is null or empty");
            return;
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("lalithkumaur@gamil.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String,Object> components = new HashMap<>();
        components.put("customerName", customerName);
        components.put("amount", amount);
        components.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(components);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("Email sent successfully to [%s] with template %s.", destinationEmail,templateName));
        }catch (MessagingException e){
            log.warn("WARNING - can't sent email to {}",destinationEmail);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("lalithkumaur@gamil.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String,Object> components = new HashMap<>();
        components.put("customerName", customerName);
        components.put("amount", amount);
        components.put("orderReference", orderReference);
        components.put("products", products);

        Context context = new Context();
        context.setVariables(components);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("Email sent successfully to [%s] with template %s.", destinationEmail,templateName));
        }catch (MessagingException e){
            log.warn("WARNING - can't sent email to {}",destinationEmail);
        }
    }

}
