package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    // Pegamos as chaves do application.properties para não deixar exposto no código público
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.url.success}")
    private String successUrl;

    @Value("${stripe.url.cancel}")
    private String cancelUrl;

    @PostConstruct
    public void init() {
        // Inicializa a Stripe com a sua chave secreta assim que o serviço subir
        Stripe.apiKey = stripeApiKey;
    }

    public String createCheckoutSession(Order order) throws StripeException {
        // 1. Convertemos o valor do pedido para centavos (A Stripe exige inteiros: R$ 10,00 vira 1000)
        long totalAmountInCents = Math.round(order.getTotal() * 100);

        // 2. Colocamos o ID do pedido nos metadados para sabermos quem pagar quando o webhook disparar
        Map<String, String> metadata = new HashMap<>();
        metadata.put("order_id", order.getId().toString());

        // 3. Montamos a requisição da sessão de checkout
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // Aceita cartão de crédito
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}") // Para onde vai se der bom
                .setCancelUrl(cancelUrl) // Para onde vai se o usuário desistir
                .putAllMetadata(metadata) // O pulo do gato de segurança!
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("brl") // Moeda brasileira
                                                .setUnitAmount(totalAmountInCents)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Pedido #" + order.getId())
                                                                .setDescription("Pagamento seguro do seu carrinho de compras")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        // 4. Cria a sessão na Stripe e retorna o link gerado por eles
        Session session = Session.create(params);
        return session.getUrl();
    }
}