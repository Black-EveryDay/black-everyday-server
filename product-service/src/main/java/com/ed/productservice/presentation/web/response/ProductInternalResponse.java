package com.ed.productservice.presentation.web.response;

import com.ed.productservice.domain.DecreaseStockResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record ProductInternalResponse(
    StockDecreaseResponse stockDecreaseResponse,
    StockIncreaseResponse stockIncreaseResponse

) {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockDecreaseResponse {

        private String transactionId;
        private List<Long> brandIdList;

        public static StockDecreaseResponse from(DecreaseStockResponse response) {
            return new StockDecreaseResponse(response.transactionId(),
                response.brandIdList());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockIncreaseResponse {

        private String transactionId;

        public static StockIncreaseResponse from(String transactionId) {
            return new StockIncreaseResponse(transactionId);
        }
    }

}
