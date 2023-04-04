package SingVersion.FitnesApp.service.impl.user;

import SingVersion.FitnesApp.configuration.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

    private final Mail mailSender = new Mail();
    private final JavaMailSender emailSender = mailSender.getJavaMailSender();

    public void sendSimpleMessage(
            String to, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("grisha_griva@mail.ru");
        message.setTo(to);
        message.setSubject("Регистрация прошла успешно!");
        String textMail = text.formatted("""
                Для верификации перейдите по ссылке ниже:
                                
                "$s"
                """);
        message.setText(textMail);
        emailSender.send(message);
    }
}
