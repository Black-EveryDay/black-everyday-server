package com.ed.orderservice.domain.enums;

import lombok.Getter;

@Getter
public enum DeliveryCompanyCode {
  CJGLS("CJGLS", "CJ대한통운"),
  HYUNDAI("HYUNDAI", "롯데택배"),
  KGB("KGB", "로젠택배"),
  HANJIN("HANJIN", "한진택배"),
  DAESIN("DAESIN", "대신택배"),
  EPOST("EPOST", "우체국택배"),
  GTX("GTX", "GTX로지스"),
  KDEXP("KDEXP", "경동택배"),
  IL_YANG("ILYANG", "일양택배"),
  EMS("POST_EMS", "우체국 EMS"),
  DHL("DHL", "DHL"),
  FEDEX("FEDEX", "FEDEX"),
  LOGEN("LOGEN", "로젠택배"),
  PANTOS("PANTOS", "범한판토스"),
  SLX("SLX", "SLX택배"),
  WIZWA("WIZWA", "WIZWA");

  private final String code;
  private final String name;

  DeliveryCompanyCode(String code, String name) {
    this.code = code;
    this.name = name;
  }
}

