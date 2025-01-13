package com.ed.orderservice.infrastructure.external.fegin.domain.product.dto;

public record ProductReservationResponse(
    String transactionId
) {

  public static ProductReservationResponse from(String transactionId) {

    return new ProductReservationResponse(transactionId);
  }
}
