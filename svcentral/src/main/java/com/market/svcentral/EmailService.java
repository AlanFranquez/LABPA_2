package com.market.svcentral;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    private final String username = "directmarket.utec@gmail.com"; // Cambia esto por tu correo
    private final String password = "jpjm tdjl rrsx kldi"; // Cambia esto por tu contraseña o token de aplicación

    /**
     * Envía un correo electrónico al destinatario especificado
     *
     * @param recipientEmail Dirección de correo del destinatario
     * @param subject Asunto del correo
     * @param body Contenido del mensaje de correo
     */
    public void sendEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Correo enviado correctamente a " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    /**
     * Envía un correo de bienvenida al destinatario especificado
     *
     * @param recipientEmail Dirección de correo del destinatario
     */
    public void sendWelcomeEmail(String recipientEmail) {
        String subject = "Bienvenido a Direct Market";
        String body = "<html>"
                + "<body>"
                + "<h1>Bienvenido a Direct Market</h1>"
                + "<p>Estimado usuario,</p>"
                + "<p>Gracias por unirse a Direct Market. Esperamos que disfrute de una experiencia de compra satisfactoria con nosotros.</p>"
                + "<br>"
                + "<p>Saludos,<br>El equipo de Direct Market</p>"
                + "</body>"
                + "</html>";

        sendEmail(recipientEmail, subject, body);
    }
}
