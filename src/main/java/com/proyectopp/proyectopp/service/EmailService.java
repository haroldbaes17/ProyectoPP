package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Direccion;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

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

    public void sendOrderConfirmationEmail(
            String to,
            String subject,
            Pedido pedido,
            Direccion direccion,
            Usuario usuario,
            List<DetallePedido> detalles
    ) {
        String nombreUsuario = usuario.getNombre() + usuario.getApellidos();
        String fechaPedido = pedido.getFecha().toString();

        // Formateadores para 2 decimales
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String subtotalFormatted = df.format(pedido.getSubtotal());
        String impuestosFormatted = df.format(pedido.getSubtotal().multiply(BigDecimal.valueOf(0.13)));
        String totalFormatted = df.format(pedido.getTotal());

        StringBuilder detallesTabla = new StringBuilder();
        for (DetallePedido detalle : detalles) {

            String precioUnitarioFormateado = df.format(detalle.getPrecioUnitario());
            String subtotalDetalleFormateado = df.format(detalle.getSubtotal());

            detallesTabla.append("<tr>")
                    .append("<td>").append(detalle.getProducto().getNombre()).append("</td>")
                    .append("<td>").append(detalle.getProducto().getEquipo()).append("</td>")
                    .append("<td>").append(detalle.getProducto().getTipoEquipacion()).append("</td>")
                    .append("<td>").append(detalle.getProducto().getLiga()).append("</td>")
                    .append("<td>").append(detalle.getProducto().getTemporada()).append("</td>")
                    .append("<td style=\"text-align:center;\">").append(detalle.getTalla()).append("</td>")
                    .append("<td style=\"text-align:center;\">").append(detalle.getCantidad()).append("</td>")
                    .append("<td>").append("₡").append(precioUnitarioFormateado).append("</td>")
                    .append("<td>").append("₡").append(subtotalDetalleFormateado).append("</td>")
                    .append("</tr>");
        }

        String content = "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                "        .container { background-color: #fff; border-radius: 5px; padding: 20px; max-width: 600px; margin: auto; }" +
                "        .header { background-color: #333; padding: 10px; color: #fff; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }" +
                "        .content { padding: 20px; font-size: 16px; line-height: 1.6; }" +
                "        .footer { font-size: 12px; color: #777; text-align: center; margin-top: 20px; }" +
                "        .order-details { width: 100%; border-collapse: collapse; margin-top: 20px; }" +
                "        .order-details th, .order-details td { border: 1px solid #ddd; padding: 8px; }" +
                "        .order-details th { background-color: #f2f2f2; }" +
                "        .total { text-align: left; margin-top: 20px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h1>Ticotees Store</h1>" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <p>Hola, " + nombreUsuario + ",</p>" +
                "            <p>¡Gracias por tu compra! Tu pedido se ha procesado correctamente.</p>" +
                "            <p><strong>Fecha de pedido:</strong> " + fechaPedido + "</p>" +
                "            <h3>Detalles del Pedido</h3>" +
                "            <table class=\"order-details\">" +
                "                <thead>" +
                "                    <tr>" +
                "                        <th>Nombre</th>" +
                "                        <th>Equipo</th>" +
                "                        <th>Equipación</th>" +
                "                        <th>Liga</th>" +
                "                        <th>Temporada</th>" +
                "                        <th>Talla</th>" +
                "                        <th>Cantidad</th>" +
                "                        <th>Precio Unit.</th>" +
                "                        <th>Subtotal</th>" +
                "                    </tr>" +
                "                </thead>" +
                "                <tbody>" +
                detallesTabla.toString() +
                "                </tbody>" +
                "            </table>" +
                "            <p class=\"total-left\"><strong>Subtotal:</strong> ₡" + subtotalFormatted + "</p>" +
                "            <p class=\"total-left\"><strong>Impuestos:</strong> ₡" + impuestosFormatted + "</p>" +
                "            <p class=\"total-left\"><strong>Total:</strong> ₡" + totalFormatted + "</p>" +
                "            <h3>Dirección de Envío</h3>" +
                "            <p>" + direccion.getProvincia() + "<br>" +
                "                " + direccion.getCanton() + ", " + direccion.getDistrito() + "<br>" +
                "                " + direccion.getDireccionExacta() + "<br>" +
                "            <p>En caso de tener alguna duda o consulta sobre tu pedido, por favor contáctanos.</p>" +
                "            <p>¡Gracias por comprar en Ticotees Store!</p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <p>© 2025 Ticotees Store. Todos los derechos reservados.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        // Finalmente, envías el correo con tu método genérico
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
