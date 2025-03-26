package com.proyectopp.proyectopp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo de confirmación de cuenta al usuario.
     *
     * @param to Dirección de correo del destinatario.
     * @param subject Asunto del correo.
     * @param confirmationLink Enlace de confirmación.
     */
    public void sendConfirmatioEmail(String to, String subject, String confirmationLink) {
        String content = "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                "        .container { background-color: #fff; border-radius: 5px; padding: 20px; max-width: 600px; margin: auto; }" +
                "        .header { background-color: #333; padding: 10px; color: #fff; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }" +
                "        .content { padding: 20px; font-size: 16px; line-height: 1.6; }" +
                "        .button { display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #28a745; color: #fff; text-decoration: none; border-radius: 5px; }" +
                "        .footer { font-size: 12px; color: #777; text-align: center; margin-top: 20px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h1>Ticotees Store</h1>" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <p>Hola,</p>" +
                "            <p>Gracias por registrarte en Ticotees Store. Para comenzar a disfrutar de nuestros servicios, por favor confirma tu cuenta haciendo clic en el siguiente botón:</p>" +
                "            <p style=\"text-align: center;\">" +
                "                <a href=\"" + confirmationLink + "\" class=\"button\">Confirmar mi cuenta</a>" +
                "            </p>" +
                "            <p>Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:</p>" +
                "            <p><a href=\"" + confirmationLink + "\">" + confirmationLink + "</a></p>" +
                "            <p>Si no solicitaste este registro, puedes ignorar este correo.</p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <p>© 2025 Ticotees Store. Todos los derechos reservados.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        sendEmail(to, subject, content);
    }

    public void sendResetPasswordEmail(String to, String subject, String resetLink) {
        String content = "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                "        .container { background-color: #fff; border-radius: 5px; padding: 20px; max-width: 600px; margin: auto; }" +
                "        .header { background-color: #333; padding: 10px; color: #fff; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }" +
                "        .content { padding: 20px; font-size: 16px; line-height: 1.6; }" +
                "        .button { display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #dc3545; color: #fff; text-decoration: none; border-radius: 5px; }" +
                "        .footer { font-size: 12px; color: #777; text-align: center; margin-top: 20px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h1>Ticotees Store</h1>" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <p>Hola,</p>" +
                "            <p>Recibimos una solicitud para restablecer la contraseña de tu cuenta en Ticotees Store.</p>" +
                "            <p>Si realizaste esta solicitud, haz clic en el siguiente botón para reestablecer tu contraseña:</p>" +
                "            <p style=\"text-align: center;\">" +
                "                <a href=\"" + resetLink + "\" class=\"button\">Restablecer contraseña</a>" +
                "            </p>" +
                "            <p>Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:</p>" +
                "            <p><a href=\"" + resetLink + "\">" + resetLink + "</a></p>" +
                "            <p>Si no solicitaste este cambio, ignora este correo.</p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <p>© 2025 Ticotees Store. Todos los derechos reservados.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        sendEmail(to, subject, content);
    }

    public void sendPasswordChangedNotification(String to, String subject) {
        String content = "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                "        .container { background-color: #fff; border-radius: 5px; padding: 20px; max-width: 600px; margin: auto; }" +
                "        .header { background-color: #333; padding: 10px; color: #fff; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }" +
                "        .content { padding: 20px; font-size: 16px; line-height: 1.6; }" +
                "        .button { display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #28a745; color: #fff; text-decoration: none; border-radius: 5px; }" +
                "        .footer { font-size: 12px; color: #777; text-align: center; margin-top: 20px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h1>Ticotees Store</h1>" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <p>Hola,</p>" +
                "            <p>Te informamos que tu contraseña ha sido cambiada exitosamente.</p>" +
                "            <p>Si tú no realizaste este cambio, por favor contacta de inmediato a nuestro equipo de soporte.</p>" +
                "            <p>Para iniciar sesión, haz clic en el siguiente enlace:</p>" +
                "            <p style=\"text-align: center;\">" +
                "                <a href=\"http://localhost:8080/login\" class=\"button\">Iniciar Sesión</a>" +
                "            </p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <p>© 2025 Ticotees Store. Todos los derechos reservados.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        sendEmail(to, subject, content);
    }



    /**
     * Envía un correo en formato HTML.
     *
     * @param to Dirección de correo del destinatario.
     * @param subject Asunto del correo.
     * @param htmlContent Contenido del correo en formato HTML.
     */
    public void sendEmail(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ticoteesstore@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indica que el contenido es HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            // Manejar la excepción, ya sea logueándola o lanzando una excepción personalizada
            e.printStackTrace();
        }
    }

}
