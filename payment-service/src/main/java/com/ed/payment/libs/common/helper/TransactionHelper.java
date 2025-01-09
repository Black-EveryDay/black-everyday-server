package com.ed.payment.libs.common.helper;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionHelper {

  @Transactional(propagation = REQUIRES_NEW)
  public void executeInNewTransaction(Runnable runnable) {
    runnable.run();
  }
}
