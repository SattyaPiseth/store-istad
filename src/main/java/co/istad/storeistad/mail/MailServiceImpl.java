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
public class MailServiceImpl implements MailService{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendMail(Mail<?> mail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setTo(mail.getSender());
        helper.setFrom(mail.getReceiver());
        helper.setSubject(mail.getSubject());

        Context context = new Context();
        context.setVariable("metaData",mail.getMetaData());
        String htmlTemplate = templateEngine.process(mail.getTemplate(),context);
        helper.setText(htmlTemplate,true);

        javaMailSender.send(mimeMessage);
    }

}
