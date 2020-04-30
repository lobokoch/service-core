package br.com.kerubin.api.servicecore.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class MailInfo {

	private final String email;
	private String personal = MailSender.EMAIL_DEFAULT_PERSONAL;
	
	@ToString.Exclude
	private String password = MailUtils.get_EMAIL_KERUBIN_PLATFORM_APP_PWD();
}
