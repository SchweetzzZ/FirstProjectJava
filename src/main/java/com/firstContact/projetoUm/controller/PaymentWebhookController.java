package com.firstContact.projetoUm.controller;

import com.firstContact.projetoUm.services.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
public class PaymentWebhookController {

    @Autowired
    private OrderService orderService;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        // 1. Validação de Segurança: Garante que a requisição veio REALMENTE da Stripe
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("⚠️ Assinatura inválida detectada!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assinatura inválida");
        }

        // 2. Processa o evento enviado pela Stripe
        String eventType = event.getType();

        if ("checkout.session.completed".equals(eventType)) {

            // 🌟 Forma ultra segura e direta de pegar a sessão sem dar erro de desserialização por versão:
            Session session = (Session) event.getData().getObject();

            if (session != null) {
                // Pega o order_id dos metadados
                String orderIdStr = session.getMetadata().get("order_id");

                if (orderIdStr != null) {
                    Long orderId = Long.parseLong(orderIdStr);
                    System.out.println("💳 Pagamento aprovado para o Pedido #" + orderId);

                    // Atualiza o status no banco
                    orderService.updateStatusToPaid(orderId);
                }
            } else {
                System.out.println("⚠️ Objeto de sessão veio nulo.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        // 5. Responde à Stripe com 200 OK para ela saber que recebemos o aviso com sucesso
        return ResponseEntity.ok("Evento processado");
    }
}
