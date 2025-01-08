package com.ed.productservice.presentation.web.response;

public record ProductReservationResponse(
    String reservationId
) {

    public static ProductReservationResponse from(String reservationId) {
        return new ProductReservationResponse(reservationId);
    }
}
