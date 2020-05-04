package br.com.kerubin.api.servicecore.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MailInfoCount {
	
	private String email;
	
	@Builder.Default
	private String personal = MailSender.EMAIL_DEFAULT_PERSONAL;
	
	@Builder.Default
	private int sendCount = 0;
	
	@ToString.Exclude
	private String password;
	
	public int incCount() {
		return ++sendCount;
	}
	
	
	public MailInfo toMailInfo() {
		return new MailInfo(email, personal, password);
	}
}
