package com.ed.productservice.presentation.web.response;

public record ProductReservationResponse(
    String transactionId
) {

    public static ProductReservationResponse from(String transactionId) {
        return new ProductReservationResponse(transactionId);
    }
}
