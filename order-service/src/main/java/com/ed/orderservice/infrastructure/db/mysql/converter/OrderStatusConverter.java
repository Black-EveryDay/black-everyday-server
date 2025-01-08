package com.ed.orderservice.infrastructure.db.mysql.converter;


import com.ed.orderservice.domain.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;


@Converter(autoApply = true)
@Slf4j
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
  @Override
  public String convertToDatabaseColumn(OrderStatus orderStatus) {
    return orderStatus.name();
  }

  @Override
  public OrderStatus convertToEntityAttribute(String dbData) {
    return OrderStatus.valueOf(dbData);
  }
}

