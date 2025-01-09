package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.enums.DeliveryCompanyCode;
import com.ed.orderservice.domain.enums.OrderDeliveryStatus;
import com.ed.orderservice.infrastructure.db.mysql.converter.OrderDeliveryConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_ORDER_DELIVERY")
@Getter
@NoArgsConstructor
public class OrderDeliveryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_DELIVERY_ID")
  private Long orderDeliveryId;

  @Column(name = "RECEIVER_NAME", nullable = false)
  private String receiverName;

  @Column(name = "RECEIVER_ADDRESS", nullable = false)
  private String receiverAddress;

  @Column(name = "RECEIVER_PHONE_NUMBER")
  private String receiverPhoneNumber;

  @Column(name = "RECEIVER_MOBILE_NUMBER", nullable = false)
  private String receiverMobileNumber;

  @Column(name = "ZIPCODE", nullable = false)
  private String zipcode;

  @Column(name = "ROAD_ZIPCODE", nullable = false)
  private String roadZipCode;

  @Column(name = "REQUIREMENT")
  private String requirement;

  @OneToOne
  @JoinColumn(name = "ORDER_ID", nullable = false)
  private OrderEntity orderEntity;

  @Convert(converter = OrderDeliveryConverter.class)
  @Column(name = "ORDER_DELIVERY_STATUS", nullable = false)
  private OrderDeliveryStatus orderDeliveryStatus = OrderDeliveryStatus.ORDER_CONFIRMED;

  @Column(name = "DELIVERY_COMPANY_CODE")
  private DeliveryCompanyCode deliveryCompanyCode = null;

  @Column(name = "INVOICE_NUMBER")
  private String invoiceNumber = null;

  @Builder
  private OrderDeliveryEntity(String receiverName, String receiverAddress,
      String receiverPhoneNumber, String receiverMobileNumber, String zipcode, String roadZipCode,
      String requirement, OrderDeliveryStatus orderDeliveryStatus,
      DeliveryCompanyCode deliveryCompanyCode, String invoiceNumber, OrderEntity orderEntity) {
    this.receiverName = receiverName;
    this.receiverAddress = receiverAddress;
    this.receiverPhoneNumber = receiverPhoneNumber;
    this.receiverMobileNumber = receiverMobileNumber;
    this.zipcode = zipcode;
    this.roadZipCode = roadZipCode;
    this.requirement = requirement;
    this.orderDeliveryStatus = orderDeliveryStatus;
    this.deliveryCompanyCode = deliveryCompanyCode;
    this.invoiceNumber = invoiceNumber;
  }

  public void updateOrder(OrderEntity orderEntity) {
    this.orderEntity = orderEntity;
  }

}
