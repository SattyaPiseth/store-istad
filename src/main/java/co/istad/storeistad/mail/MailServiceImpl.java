package co.istad.storeistad.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine; // OK, SpringTemplateEngine also works

    @Override
    public void sendMail(Mail<?> mail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // true = multipart (inline images / attachments), UTF-8 for safety
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // 🔧 FIX: from/to were swapped
        helper.setFrom(mail.getSender());
        helper.setTo(mail.getReceiver());
        helper.setSubject(mail.getSubject());

        // Render template (your template currently uses ${metaData})
        Context context = new Context();
        context.setVariable("metaData", mail.getMetaData());
        String html = templateEngine.process(mail.getTemplate(), context);

        helper.setText(html, true);
        javaMailSender.send(mimeMessage);
    }
}
