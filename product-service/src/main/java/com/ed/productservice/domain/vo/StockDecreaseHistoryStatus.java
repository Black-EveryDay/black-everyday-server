package com.ed.productservice.domain.vo;

public enum StockDecreaseHistoryStatus {
    DECREASED("재고 차감 완료"),
    COMMITTED("재고 차감 확정"),
    ROLLBACK("재고 롤백"),
    SCHEDULING_ROLLBACK("스케줄링 재고 롤백");


    private final String description;

    StockDecreaseHistoryStatus(String description) {
        this.description = description;
    }
}
