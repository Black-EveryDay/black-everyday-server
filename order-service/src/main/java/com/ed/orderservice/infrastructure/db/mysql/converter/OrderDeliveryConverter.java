package com.ed.orderservice.infrastructure.db.mysql.converter;


import com.ed.orderservice.domain.enums.OrderDeliveryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;


@Converter(autoApply = true)
@Slf4j
public class OrderDeliveryConverter implements AttributeConverter<OrderDeliveryStatus, String> {
  @Override
  public String convertToDatabaseColumn(OrderDeliveryStatus orderDeliveryStatus) {
    return orderDeliveryStatus.name();
  }

  @Override
  public OrderDeliveryStatus convertToEntityAttribute(String dbData) {
    return OrderDeliveryStatus.valueOf(dbData);
  }
}

