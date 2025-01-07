package com.ed.productservice.presentation.web.request;

import java.util.List;

public record InventoryReservationRequest(
    List<ProductReservationInfo> items
) {

    public record ProductReservationInfo(
        Long productId,
        int quantity,
        String size
    ) {
    }
}