package com.tendajoam.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tendajoam.service.interfaces.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarConfirmacio(String destinatari, String token) {

        String link = "http://localhost:8081/api/auth/confirm?token=" + token;

        SimpleMailMessage missatge = new SimpleMailMessage();
        missatge.setTo(destinatari);
        missatge.setSubject("Confirma el teu compte - Tenda JOAM");
        missatge.setText(
                "Benvingut/da!\n\n" +
                "Fes clic en aquest enllaç per confirmar el teu compte:\n" +
                link + "\n\n" +
                "Si no has creat aquest compte, ignora aquest correu."
        );

        mailSender.send(missatge);
    }
    
    @Override
    public void notificarNouVenedorAdmin(String nomEmpresa, String correuVenedor, String token) {

        String linkValidacio = "http://localhost:8081/api/auth/confirm?token=" + token;

        SimpleMailMessage missatge = new SimpleMailMessage();
        missatge.setTo("tendajoam@gmail.com"); 
        missatge.setSubject("Nova sol·licitud de venedor - Tenda JOAM");
        missatge.setText(
                "Hola administrador,\n\n" +
                "S'ha rebut una nova sol·licitud de registre de venedor:\n\n" +
                "- Nom de l'empresa: " + nomEmpresa + "\n" +
                "- Correu de l'usuari: " + correuVenedor + "\n\n" +
                "Si vols aprovar aquest venedor i activar el seu compte, fes clic aquí:\n" +
                linkValidacio + "\n\n" +
                "Si ignores aquest correu, el compte seguirà bloquejat."
        );

        mailSender.send(missatge);
    }
}
