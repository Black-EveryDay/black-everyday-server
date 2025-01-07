package com.ed.productservice.presentation.web.internal;

import com.ed.productservice.application.service.internal.ProductInternalService;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest;
import com.ed.productservice.presentation.web.response.ProductReservationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products/internal")
@RestController
public class ProductInternalController {

    private final ProductInternalService productInternalService;

    @PostMapping("/inventory/reservations")
    public ProductReservationResponse reservationStock(
        @RequestBody InventoryReservationRequest request
    ) {
        String reservationId = productInternalService.reservation(request);

        return ProductReservationResponse.from(reservationId);
    }

    @PostMapping("/inventory/reservations/{reservationId}/decrease")
    public ProductReservationResponse decreaseStock(@PathVariable("reservationId") String reservationId)
        throws JsonProcessingException {

        return ProductReservationResponse.from(productInternalService.decreaseStock(reservationId));
    }

    @PostMapping("/inventory/reservations/{reservationId}/increase")
    public ProductReservationResponse increaseStock(
        @PathVariable("reservationId") String reservationId) {

        return ProductReservationResponse.from(productInternalService.increaseStock(reservationId));
    }
}
