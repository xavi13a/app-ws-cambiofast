package com.pichincha.money.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;

import jakarta.annotation.PostConstruct;

@Service
public class SendGridEmailService {

    @Value("${sendgrid.from.email:NOT_FOUND}")
    private String fromEmail;
    
    @Value("${sendgrid.api.key:NOT_FOUND}")
    private String sendGridApiKey;

    @PostConstruct
    public void init() {
    	System.out.println("SendGrid API Key: " + fromEmail);
        System.out.println("SendGrid API Key: " + sendGridApiKey);
        System.out.println("Perfil activo: " + System.getProperty("spring.profiles.active"));
    }
    
    public boolean sendEmail(String toEmail, String magicLink) {
    	Email email = new Email();
    	email.setFrom("bot", "MS_seArKr@trial-3zxk54vee61ljy6v.mlsender.net");
        email.addRecipient("Joel", toEmail);

        email.setSubject("Tu enlace de acceso");

//        email.setPlain("This is the text content");
        email.setHtml("Haz clic en el siguiente enlace para acceder: <a href='" + magicLink + "'>Iniciar sesión</a>");
    	
        MailerSend ms = new MailerSend();
        ms.setToken(sendGridApiKey);
        
        try {    
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
            return response.messageId != null;
        } catch (MailerSendException e) {
            e.printStackTrace();
            return false;
        }
    }
}
