package br.com.kerubin.api.servicecore.mail;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSender {
	
	public static final String EMAIL_FROM_DEFAULT = "kerubin.platform@gmail.com";
	public static final String EMAIL_DEFAULT_PWD = "lobo181169";

	//@Inject
	// private JavaMailSender mailSender;

	public void sendMailAsync(String from, List<String> recipients, String subject, String message) {
		CompletableFuture.runAsync(() -> sendMail(from, recipients, subject, message));
	}
	
	public void sendMail(String from, List<String> recipients, String subject, String message) {
		String logHeader = MessageFormat.format("from \"{0}\" to \"{1}\" subject \"{2}\"", from, recipients, subject);
		
		JavaMailSender mailSender = buildJavaMailSender(from, EMAIL_DEFAULT_PWD);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		try {
			helper.setFrom(from);
			helper.setTo(recipients.toArray(new String[recipients.size()]));
			helper.setSubject(subject);
			helper.setText(message, true);
			
			log.info("Sending e-mail {} ", logHeader);
			mailSender.send(mimeMessage);			
			log.info("E-mail sended {} ", logHeader);
		} catch (Exception e) {
			log.error("Error sending e-mail " + logHeader, e);
			new MailSenderException("Error sending e-mail " + logHeader);
		}

	}
	
	public JavaMailSender buildJavaMailSender(String username, String password) {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.connectiontimeout", 10000);
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setJavaMailProperties(props);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		return mailSender;
	}

}
