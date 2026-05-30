package com.firstContact.projetoUm.entity.Dto;

import com.firstContact.projetoUm.entity.Order;

public class OrderPaymentResponseDTO {

    private Order order;
    private String checkoutUrl;

    public OrderPaymentResponseDTO(Order order, String checkoutUrl){
        this.order = order;
        this.checkoutUrl = checkoutUrl;
    }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public String getCheckoutUrl() { return checkoutUrl; }
    public void setCheckoutUrl(String checkoutUrl) { this.checkoutUrl = checkoutUrl; }
}
