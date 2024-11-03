package com.market.svcentral;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    private String username;
    private String password;
    
    public EmailService() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("No se pudo cargar el archivo config.properties");
                return;
            }
            properties.load(input);
            this.username = properties.getProperty("correo.email");
            this.password = properties.getProperty("correo.clave");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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
    
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            System.out.println("Error: Dirección de correo nula o vacía.");
            return;
        }
        if (body == null) {
            System.out.println("Error: El cuerpo del mensaje es nulo. Estableciendo contenido predeterminado.");
            body = "<html><body><p>Contenido del mensaje no disponible.</p></body></html>";
        }
        

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
    
    public void sendChangeState(String recipientEmail, String newState) {
        System.out.println("Estado recibido en sendChangeState: " + newState); // Para depuración

        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            System.out.println("Error: Dirección de correo nula o vacía.");
            return;
        }

        if (newState == null) {
            System.out.println("Error: El estado de la orden es nulo."); 
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        String subject = "Hubo un cambio en una orden de compra!";
        String body = "<html>"
                + "<body>"
                + "<h1>Orden</h1>"
                + "<p>Estado actualizado: " + newState + "</p>" 
                + "</body>"
                + "</html>";

        sendEmail(recipientEmail, subject, body);
    }

    

    public void sendCommentNotification(String recipientEmail, String productName, String commenterName, String newCommentText) {
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            System.out.println("Error: Dirección de correo nula o vacía.");
            return;
        }

        String subject = "Nuevo comentario sobre un producto que compraste!";
        String body = "<html>"
                    + "<body>"
                    + "<h1>Comentario sobre " + productName + "</h1>"
                    + "<p><strong>" + commenterName + "</strong> ha comentado: " + newCommentText + "</p>"
                    + "</body>"
                    + "</html>";

        try {
            sendEmail(recipientEmail, subject, body);
            System.out.println("Notificación de nuevo comentario enviada a " + recipientEmail);
        } catch (Exception e) {
            System.out.println("Error al enviar el correo a " + recipientEmail + ": " + e.getMessage());
            e.printStackTrace();
        }
    }


    

}
