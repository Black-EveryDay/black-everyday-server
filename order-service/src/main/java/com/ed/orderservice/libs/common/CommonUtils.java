package com.ed.orderservice.libs.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;

public class CommonUtils {

  private static final Random RANDOM = new Random();
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private CommonUtils() {
  }

  public static Random getRandom() {

    return RANDOM;
  }

  public static ObjectMapper getObjectMapper() {

    return OBJECT_MAPPER;
  }

}
