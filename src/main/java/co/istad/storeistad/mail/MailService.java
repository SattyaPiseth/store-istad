package co.istad.storeistad.mail;


import jakarta.mail.MessagingException;

public interface MailService {
    /**
     * send mail to user
     * @param mail mail object
     * @throws MessagingException if mail sending failed
     */
    void sendMail(Mail<?> mail) throws MessagingException;
}
