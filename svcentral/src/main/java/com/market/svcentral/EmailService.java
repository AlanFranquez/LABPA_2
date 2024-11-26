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
	
    private ISistema sistema; // Campo ISistema para acceder a los métodos del sistema

	public EmailService(ISistema sistema) {
        this.sistema = sistema;
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
	 * @param subject        Asunto del correo
	 * @param body           Contenido del mensaje de correo
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
		String body = "<html>" + "<body>" + "<h1>Bienvenido a Direct Market</h1>" + "<p>Estimado usuario,</p>"
				+ "<p>Gracias por unirse a Direct Market. Esperamos que disfrute de una experiencia de compra satisfactoria con nosotros.</p>"
				+ "<br>" + "<p>Saludos,<br>El equipo de Direct Market</p>" + "</body>" + "</html>";

		sendEmail(recipientEmail, subject, body);
	}

	public void sendChangeState(String recipientEmail, String newState) {
	    System.out.println("Estado recibido en sendChangeState: " + newState);

	    if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
	        System.out.println("Error: Dirección de correo nula o vacía.");
	        return;
	    }

	    // Obtener el cliente por su correo electrónico usando sistema
	    Cliente cliente = sistema.getClientePorCorreo(recipientEmail);

	    // Validar que el cliente exista y que tenga activadas las notificaciones
	    if (cliente == null) {
	        System.out.println("Error: No se encontró un cliente con el correo " + recipientEmail);
	        return;
	    }

	    if (!cliente.isRecibirNotificaciones()) {
	        System.out.println("El cliente ha desactivado las notificaciones.");
	        return;
	    }

	    String subject = "Actualización en el estado de tu orden de compra";
	    // Usar el token de desactivación que ya tiene el cliente
	    String linkDesactivacion = "http://localhost/webApplication/desactivarNotiEstado?token=" + cliente.getTokenDesactivacion();

	    String body = "<html>" +
	                  "<body style='font-family: Arial, sans-serif; color: #333;'>" +
	                  "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;'>" +
	                  "<h2 style='color: #0066cc;'>¡Actualización en el estado de tu orden!</h2>" +
	                  "<p>Estimado cliente,</p>" +
	                  "<p>El estado de tu orden ha cambiado. Ahora es: <strong>" + newState + "</strong>.</p>" +
	                  "<p>Gracias por tu preferencia.</p>" +
	                  "<hr>" +
	                  "<footer>" +
	                  "<p>Si no deseas recibir más correos, puedes <a href='" + linkDesactivacion + "'>desactivar las notificaciones aquí</a>.</p>" +
	                  "</footer>" +
	                  "</div>" +
	                  "</body>" +
	                  "</html>";

	    sendEmail(recipientEmail, subject, body);
	}

	public void sendCommentNotification(String recipientEmail, String productName, String commenterName,
			String newCommentText) {
		if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
			System.out.println("Error: Dirección de correo nula o vacía.");
			return;
		}

		// Obtener el cliente por su correo electrónico usando sistema
	    Cliente cliente = sistema.getClientePorCorreo(recipientEmail);

	    // Validar que el cliente exista y que tenga activadas las notificaciones
	    if (cliente == null) {
	        System.out.println("Error: No se encontró un cliente con el correo " + recipientEmail);
	        return;
	    }

	    if (!cliente.isRecibirNotificaciones()) {
	        System.out.println("El cliente ha desactivado las notificaciones.");
	        return;
	    }
	    // Usar el token de desactivación que ya tiene el cliente
	    String linkDesactivacion = "http://localhost/webApplication/desactivarNotiEstado?token=" + cliente.getTokenDesactivacion();
	    
		String subject = "Nuevo comentario en tu producto comprado!";
		String body = "<html>" + "<body style='font-family: Arial, sans-serif; color: #333;'>"
				+ "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;'>"
				+ "<h2 style='color: #0066cc;'>¡Tienes un nuevo comentario sobre tu compra!</h2>" + "<p>Hola,</p>"
				+ "<p>Te informamos que <strong>" + commenterName
				+ "</strong> ha dejado un nuevo comentario sobre el producto que adquiriste:</p>"
				+ "<h3 style='color: #444;'>" + productName + "</h3>"
				+ "<blockquote style='font-style: italic; color: #555; padding: 10px; border-left: 4px solid #0066cc;'>"
				+ newCommentText + "</blockquote>"
				+ "<p>Esperamos que esta información te sea útil. ¡Gracias por confiar en nosotros!</p>"
				+ "<p>Saludos cordiales,<br>Equipo de Atención al Cliente</p>"
				+  "<hr>" +
                "<footer>" +
                "<p>Si no deseas recibir más correos, puedes <a href='" + linkDesactivacion + "'>desactivar las notificaciones aquí</a>.</p>" +
                "</footer>" + "</div>" + "</body>" + "</html>";

		try {
			sendEmail(recipientEmail, subject, body);
			System.out.println("Notificación de nuevo comentario enviada a " + recipientEmail);
		} catch (Exception e) {
			System.out.println("Error al enviar el correo a " + recipientEmail + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendNewProductNotification(String recipientEmail, String proveedorNombre, String clienteNombre,
			String productoNombre) {
		if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
			System.out.println("Error: Dirección de correo nula o vacía.");
			return;
		}

		// Obtener el cliente por su correo electrónico usando sistema
	    Cliente cliente = sistema.getClientePorCorreo(recipientEmail);

	    // Validar que el cliente exista y que tenga activadas las notificaciones
	    if (cliente == null) {
	        System.out.println("Error: No se encontró un cliente con el correo " + recipientEmail);
	        return;
	    }

	    if (!cliente.isRecibirNotificaciones()) {
	        System.out.println("El cliente ha desactivado las notificaciones.");
	        return;
	    }
		// Usar el token de desactivación que ya tiene el cliente
	    String linkDesactivacion = "http://localhost/webApplication/desactivarNotiEstado?token=" + cliente.getTokenDesactivacion();
		
	    String subject = "Notificación de Nuevo Producto Disponible";
		String body = "<html>" + "<body style='font-family: Arial, sans-serif; color: #333;'>"
				+ "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;'>"
				+ "<h2 style='color: #0066cc;'>¡Nuevo Producto Disponible!</h2>" + "<p>Estimado/a " + clienteNombre
				+ ",</p>" + "<p>Nos complace informarle que el proveedor <strong>" + proveedorNombre
				+ "</strong> ha registrado un nuevo producto en nuestra plataforma:</p>" + "<h3 style='color: #444;'>"
				+ productoNombre + "</h3>"
				+ "<p>Gracias por ser parte de nuestra comunidad.</p>"
				+ "<p>Atentamente,<br>Equipo de Notificaciones</p>"
				+  "<hr>" +
                "<footer>" +
                "<p>Si no deseas recibir más correos, puedes <a href='" + linkDesactivacion + "'>desactivar las notificaciones aquí</a>.</p>" +
                "</footer>" + "</div>" + "</body>" + "</html>";

		try {
			sendEmail(recipientEmail, subject, body);
			System.out.println("Notificación de nuevo producto enviada a " + recipientEmail);
		} catch (Exception e) {
			System.out.println("Error al enviar el correo a " + recipientEmail + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void sendReplyNotification(String recipientEmail, String productName, String responderName, String replyText) {
	    if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
	        System.out.println("Error: Dirección de correo nula o vacía.");
	        return;
	    }

	    // Obtener el cliente por su correo electrónico usando sistema
	    Cliente cliente = sistema.getClientePorCorreo(recipientEmail);

	    // Validar que el cliente exista y que tenga activadas las notificaciones
	    if (cliente == null) {
	        System.out.println("Error: No se encontró un cliente con el correo " + recipientEmail);
	        return;
	    }

	    if (!cliente.isRecibirNotificaciones()) {
	        System.out.println("El cliente ha desactivado las notificaciones.");
	        return;
	    }

	    String subject = "¡Tienes una nueva respuesta a tu comentario!";
	    String body = "<html>" + 
	                  "<body style='font-family: Arial, sans-serif; color: #333;'>" +
	                  "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;'>" +
	                  "<h2 style='color: #0066cc;'>¡Nueva respuesta en " + productName + "!</h2>" +
	                  "<p>Hola,</p>" +
	                  "<p><strong>" + responderName + "</strong> ha respondido a tu comentario:</p>" +
	                  "<blockquote style='font-style: italic; color: #555; padding: 10px; border-left: 4px solid #0066cc;'>" +
	                  replyText + "</blockquote>" +
	                  "<p>Esperamos que esta información te sea útil. ¡Gracias por ser parte de nuestra comunidad!</p>" +
	                  "<p>Saludos cordiales,<br>Equipo de Atención al Cliente</p>" +
	                  "<hr>" +
	                  "<footer>" +
	                  "<p>Si no deseas recibir más correos, puedes <a href='http://localhost/webApplication/desactivarNotiEstado?token=" + cliente.getTokenDesactivacion() + "'>desactivar las notificaciones aquí</a>.</p>" +
	                  "</footer>" +
	                  "</div>" +
	                  "</body>" +
	                  "</html>";

	    try {
	        sendEmail(recipientEmail, subject, body);
	        System.out.println("Notificación de respuesta enviada a " + recipientEmail);
	    } catch (Exception e) {
	        System.out.println("Error al enviar la notificación de respuesta a " + recipientEmail + ": " + e.getMessage());
	        e.printStackTrace();
	    }
	}


}