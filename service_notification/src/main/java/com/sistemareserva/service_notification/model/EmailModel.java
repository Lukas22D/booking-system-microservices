package com.sistemareserva.service_notification.model;

import org.springframework.stereotype.Service;

@Service
public class EmailModel {

    public String buildReservationConfirmationEmail(String nomeHospede, String dataCheckin, String dataCheckout,
            String tipoQuarto, String numeroReserva, String valorTotal, String linkConfirmacao) {
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Confirmação de Reserva</title>
                    <style>
                        body { font-family: Arial, sans-serif; color: #333333; background-color: #f4f4f4; margin: 0; padding: 0; }
                        .container { width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
                        .header { text-align: center; background-color: #4CAF50; color: #ffffff; padding: 10px 0; border-radius: 8px 8px 0 0; }
                        .header h1 { margin: 0; }
                        .content { padding: 20px; line-height: 1.6; }
                        .content h2 { color: #4CAF50; }
                        .details { background-color: #f9f9f9; padding: 10px; border-radius: 8px; margin-top: 15px; }
                        .footer { text-align: center; font-size: 0.9em; color: #666666; margin-top: 20px; }
                        .button { display: inline-block; padding: 10px 20px; color: #ffffff; background-color: #4CAF50; border-radius: 4px; text-decoration: none; margin-top: 20px; }
                    </style>
                </head>
                <body>
                <div class="container">
                    <div class="header">
                        <h1>Confirmação de Reserva</h1>
                    </div>
                    <div class="content">
                        <p>Olá, <strong>{{nomeHospede}}</strong>!</p>
                        <p>Estamos felizes em confirmar sua reserva em nosso hotel. Aqui estão os detalhes da sua estadia:</p>
                        <div class="details">
                            <h2>Detalhes da Reserva</h2>
                            <p><strong>Nome do Hóspede:</strong> {{nomeHospede}}</p>
                            <p><strong>Data de Check-in:</strong> {{dataCheckin}}</p>
                            <p><strong>Data de Check-out:</strong> {{dataCheckout}}</p>
                            <p><strong>Tipo de Quarto:</strong> {{tipoQuarto}}</p>
                            <p><strong>Número da Reserva:</strong> {{numeroReserva}}</p>
                            <p><strong>Valor Total:</strong> {{valorTotal}}</p>
                        </div>
                        <p>Estamos ansiosos para recebê-lo e garantir que você tenha uma estadia agradável conosco. Caso tenha alguma dúvida, não hesite em entrar em contato conosco.</p>
                        <a href="{{linkConfirmacao}}" class="button">Ver Reserva</a>
                    </div>
                    <div class="footer">
                        <p>Atenciosamente,<br>Equipe do Hotel</p>
                        <p><small>Este é um e-mail automático, por favor, não responda.</small></p>
                    </div>
                </div>
                </body>
                </html>
                """;

        htmlContent = htmlContent.replace("{{nomeHospede}}", nomeHospede)
                .replace("{{dataCheckin}}", dataCheckin)
                .replace("{{dataCheckout}}", dataCheckout)
                .replace("{{tipoQuarto}}", tipoQuarto)
                .replace("{{numeroReserva}}", numeroReserva)
                .replace("{{valorTotal}}", valorTotal)
                .replace("{{linkConfirmacao}}", linkConfirmacao);

        return htmlContent;
    }

    public String buildPendingReservationEmail(String nomeHospede, String dataCheckin, String dataCheckout,
            String tipoQuarto, String numeroReserva, String valorTotal, String linkPagamento) {
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Reserva Pendente</title>
                <style>
                body { font-family: Arial, sans-serif; color: #333333; background-color: #f4f4f4; margin: 0; padding: 0; }
                .container { width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
                .header { text-align: center; background-color: #FFA500; color: #ffffff; padding: 10px 0; border-radius: 8px 8px 0 0; }
                .header h1 { margin: 0; }
                .content { padding: 20px; line-height: 1.6; }
                .content h2 { color: #FFA500; }
                .details { background-color: #f9f9f9; padding: 10px; border-radius: 8px; margin-top: 15px; }
                .footer { text-align: center; font-size: 0.9em; color: #666666; margin-top: 20px; }
                .button { display: inline-block; padding: 10px 20px; color: #ffffff; background-color: #FFA500; border-radius: 4px; text-decoration: none; margin-top: 20px; }
                </style>
                </head>
                <body>
                <div class="container">
                <div class="header">
                <h1>Reserva Pendente</h1>
                </div>
                <div class="content">
                <p>Olá, <strong>{{nomeHospede}}</strong>!</p>
                <p>Recebemos sua reserva. No entanto, seu pagamento está aguardando confirmação. Confira os detalhes da sua reserva abaixo:</p>
                <div class="details">
                <h2>Detalhes da Reserva</h2>
                <p><strong>Data de Check-in:</strong> {{dataCheckin}}</p>
                <p><strong>Data de Check-out:</strong> {{dataCheckout}}</p>
                <p><strong>Tipo de Quarto:</strong> {{tipoQuarto}}</p>
                <p><strong>Número da Reserva:</strong> {{numeroReserva}}</p>
                <p><strong>Valor Total:</strong> {{valorTotal}}</p>
                </div>
                <p>Por favor, finalize seu pagamento clicando no botão abaixo para garantir sua reserva.</p>
                <a href="{{linkPagamento}}" class="button">Pagar Agora</a>
                </div>
                <div class="footer">
                <p>Atenciosamente,<br>Equipe do Hotel</p>
                <p><small>Este é um e-mail automático, por favor, não responda.</small></p>
                </div>
                </div>
                </body>
                </html>
                """;

        htmlContent = htmlContent.replace("{{nomeHospede}}", nomeHospede)
                .replace("{{dataCheckin}}", dataCheckin)
                .replace("{{dataCheckout}}", dataCheckout)
                .replace("{{tipoQuarto}}", tipoQuarto)
                .replace("{{numeroReserva}}", numeroReserva)
                .replace("{{valorTotal}}", valorTotal)
                .replace("{{linkPagamento}}", linkPagamento);

        return htmlContent;
    }

    public String buildPaymentConfirmedEmail(String nomeHospede, String dataCheckin, String dataCheckout,
            String tipoQuarto, String numeroReserva, String valorTotal) {
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Pagamento Confirmado</title>
                    <style>
                        body { font-family: Arial, sans-serif; color: #333333; background-color: #f4f4f4; margin: 0; padding: 0; }
                        .container { width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
                        .header { text-align: center; background-color: #4CAF50; color: #ffffff; padding: 10px 0; border-radius: 8px 8px 0 0; }
                        .header h1 { margin: 0; }
                        .content { padding: 20px; line-height: 1.6; }
                        .content h2 { color: #4CAF50; }
                        .details { background-color: #f9f9f9; padding: 10px; border-radius: 8px; margin-top: 15px; }
                        .footer { text-align: center; font-size: 0.9em; color: #666666; margin-top: 20px; }
                    </style>
                </head>
                <body>
                <div class="container">
                    <div class="header">
                        <h1>Pagamento Confirmado</h1>
                    </div>
                    <div class="content">
                        <p>Olá, <strong>{{nomeHospede}}</strong>!</p>
                        <p>Temos o prazer de informar que seu pagamento foi confirmado e sua reserva está garantida.</p>
                        <div class="details">
                            <h2>Detalhes da Reserva</h2>
                            <p><strong>Data de Check-in:</strong> {{dataCheckin}}</p>
                            <p><strong>Data de Check-out:</strong> {{dataCheckout}}</p>
                            <p><strong>Tipo de Quarto:</strong> {{tipoQuarto}}</p>
                            <p><strong>Número da Reserva:</strong> {{numeroReserva}}</p>
                            <p><strong>Valor Total:</strong> {{valorTotal}}</p>
                        </div>
                        <p>Estamos ansiosos para recebê-lo. Se precisar de algo, estamos à disposição!</p>
                    </div>
                    <div class="footer">
                        <p>Atenciosamente,<br>Equipe do Hotel</p>
                        <p><small>Este é um e-mail automático, por favor, não responda.</small></p>
                    </div>
                </div>
                </body>
                </html>
                """;

        htmlContent = htmlContent.replace("{{nomeHospede}}", nomeHospede)
                .replace("{{dataCheckin}}", dataCheckin)
                .replace("{{dataCheckout}}", dataCheckout)
                .replace("{{tipoQuarto}}", tipoQuarto)
                .replace("{{numeroReserva}}", numeroReserva)
                .replace("{{valorTotal}}", valorTotal);

        return htmlContent;
    }
    
    public String buildPaymentFailedEmail(String nomeHospede, String dataCheckin, String dataCheckout,
                                      String tipoQuarto, String numeroReserva, String valorTotal, String linkPagamento) {
    String htmlContent = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Pagamento Recusado</title>
                <style>
                    body { font-family: Arial, sans-serif; color: #333333; background-color: #f4f4f4; margin: 0; padding: 0; }
                    .container { width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
                    .header { text-align: center; background-color: #D32F2F; color: #ffffff; padding: 10px 0; border-radius: 8px 8px 0 0; }
                    .header h1 { margin: 0; }
                    .content { padding: 20px; line-height: 1.6; }
                    .content h2 { color: #D32F2F; }
                    .details { background-color: #f9f9f9; padding: 10px; border-radius: 8px; margin-top: 15px; }
                    .footer { text-align: center; font-size: 0.9em; color: #666666; margin-top: 20px; }
                    .button { display: inline-block; padding: 10px 20px; color: #ffffff; background-color: #D32F2F; border-radius: 4px; text-decoration: none; margin-top: 20px; }
                </style>
            </head>
            <body>
            <div class="container">
                <div class="header">
                    <h1>Pagamento Recusado</h1>
                </div>
                <div class="content">
                    <p>Olá, <strong>{{nomeHospede}}</strong>!</p>
                    <p>Infelizmente, não conseguimos confirmar o pagamento da sua reserva.</p>
                    <div class="details">
                        <h2>Detalhes da Reserva</h2>
                        <p><strong>Data de Check-in:</strong> {{dataCheckin}}</p>
                        <p><strong>Data de Check-out:</strong> {{dataCheckout}}</p>
                        <p><strong>Tipo de Quarto:</strong> {{tipoQuarto}}</p>
                        <p><strong>Número da Reserva:</strong> {{numeroReserva}}</p>
                        <p><strong>Valor Total:</strong> {{valorTotal}}</p>
                    </div>
                    <p>Por favor, tente novamente clicando no botão abaixo ou entre em contato com a nossa equipe.</p>
                    <a href="{{linkPagamento}}" class="button">Tentar Novamente</a>
                </div>
                <div class="footer">
                    <p>Atenciosamente,<br>Equipe do Hotel</p>
                    <p><small>Este é um e-mail automático, por favor, não responda.</small></p>
                </div>
            </div>
            </body>
            </html>
            """;

    htmlContent = htmlContent.replace("{{nomeHospede}}", nomeHospede)
                             .replace("{{dataCheckin}}", dataCheckin)
                             .replace("{{dataCheckout}}", dataCheckout)
                             .replace("{{tipoQuarto}}", tipoQuarto)
                             .replace("{{numeroReserva}}", numeroReserva)
                             .replace("{{valorTotal}}", valorTotal)
                             .replace("{{linkPagamento}}", linkPagamento);

    return htmlContent;
}

}
