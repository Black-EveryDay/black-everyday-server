package com.ed.productservice.presentation.web.response;

import com.ed.productservice.domain.DecreaseStockResponse;
import com.ed.productservice.domain.DecreaseStockResponse.ProductBrandInfo;
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
        private List<ProductBrandInfoV2> productBrandInfoList;

        public static StockDecreaseResponse from(DecreaseStockResponse response) {
            return new StockDecreaseResponse(response.transactionId(),
                response.productBrandInfoList().stream().map(
                    ProductBrandInfoV2::from
                ).toList());
        }

        @Getter
        @AllArgsConstructor
        public static class ProductBrandInfoV2 {

            private Long brandId;
            private Long productId;

            public static ProductBrandInfoV2 from(ProductBrandInfo productBrandInfo) {
                return new ProductBrandInfoV2(productBrandInfo.getBrandId(),
                    productBrandInfo.getProductId());
            }
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




