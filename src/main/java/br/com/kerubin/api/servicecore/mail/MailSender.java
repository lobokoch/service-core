package br.com.kerubin.api.servicecore.mail;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSender {
	
	public static final String EMAIL_FROM_DEFAULT = "kerubin.platform@gmail.com";

	@Inject
	private JavaMailSender mailSender;

	public void sendMailAsync(String from, List<String> recipients, String subject, String message) {
		CompletableFuture.runAsync(() -> sendMail(from, recipients, subject, message));
	}
	
	public void sendMail(String from, List<String> recipients, String subject, String message) {
		String logHeader = MessageFormat.format("from \"{0}\" to \"{1}\" subject \"{2}\"", from, recipients, subject);
		
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

}
