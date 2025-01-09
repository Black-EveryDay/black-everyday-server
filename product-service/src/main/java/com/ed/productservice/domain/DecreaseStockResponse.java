package com.ed.productservice.domain;

import java.util.List;

public record DecreaseStockResponse (
    String transactionId,
    List<Long> brandIdList
){
}
