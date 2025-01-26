package com.ed.orderservice.infrastructure.external.fegin.domain.product.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class ProductFeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      String responseBody = IOUtils.toString(response.body().asInputStream(),
          StandardCharsets.UTF_8);

      return switch (response.status()) {
        case 400 -> new BadRequestException(responseBody);
        case 404, 409 -> new NotFoundException(responseBody);
        case 500 -> new InternalServerErrorException(responseBody);
        default -> defaultErrorDecoder.decode(methodKey, response);
      };

    } catch (IOException e) {
      return new Exception("Failed to process response body");
    }
  }
}
