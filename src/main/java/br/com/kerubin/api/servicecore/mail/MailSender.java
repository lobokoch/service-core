package br.com.kerubin.api.servicecore.mail;

import java.util.List;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSender {
	
	public static final String EMAIL_FROM_DEFAULT = "lobokoch@gmail.com";

	@Inject
	private JavaMailSender mailSender;

	public void sendMail(String from, List<String> recipients, String subsject, String message) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		try {
			helper.setFrom(from);
			helper.setTo(recipients.toArray(new String[recipients.size()]));
			helper.setSubject(subsject);
			helper.setText(message, true);
			
			log.info("Sending e-mail to: " + recipients);
			mailSender.send(mimeMessage);			
			log.info("E-mail sended to: " + recipients);
		} catch (Exception e) {
			log.error("Error sending e-mail to : " + recipients, e);
			new MailSenderException("Error sending e-mail.");
		}

	}

}
