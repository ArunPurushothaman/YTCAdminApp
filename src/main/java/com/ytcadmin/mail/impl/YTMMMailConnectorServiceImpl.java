package com.ytcadmin.mail.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ytcadmin.common.model.EmailDetails;
import com.ytcadmin.mail.intf.IYTMMMailConnectorService;

@Component
@Configuration
@PropertySource("classpath:/config/ytmm.properties")
public class YTMMMailConnectorServiceImpl implements IYTMMMailConnectorService{
	
	@Autowired
	private Environment env;

	@Autowired
	private YTMMMailSenderServiceImpl ytcMailSenderService;
	
	public void sendEmail(EmailDetails emailDetails){
		if(emailDetails != null && env != null){
			emailDetails.setHost(env.getProperty("mail.smtp.host"));
			emailDetails.setPort(env.getProperty("mail.smtp.port"));
			emailDetails.setEnvironment(env.getProperty("mail.environment"));
			emailDetails.setUserName(env.getProperty("mail.smtp.username"));
			emailDetails.setPassword(env.getProperty("mail.smtp.password"));
				
			ytcMailSenderService.sendMail(emailDetails);
		}
	}
}
