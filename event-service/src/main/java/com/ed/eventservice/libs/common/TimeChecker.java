package com.ed.eventservice.libs.common;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TimeChecker {

  private long startTime;
  private long step;
  private long beforeTime;
  private long currTime;
  private UUID uuid;

  public void start() {
    startTime = System.currentTimeMillis();
    beforeTime = startTime;
    uuid = UUID.randomUUID();
    step = 1;
    log.info("[TimeChecker] [start] uuid ::: {}, startTime ::: {}", uuid, startTime);
  }

  public void check(String message) {
    currTime = System.currentTimeMillis();
    log.info(
        "[TimeChecker] [curr] uuid ::: {}, step ::: {}, durationBefore ::: {}, durationStart ::: {}, message ::: {}",
        uuid, step++, currTime - beforeTime, currTime - startTime, message);
    beforeTime = currTime;
  }

  public void end() {
    currTime = System.currentTimeMillis();
    log.info(
        "[TimeChecker] [end] uuid ::: {}, step ::: {}, durationBefore ::: {}, durationStart ::: {}",
        uuid, step++, currTime - beforeTime, currTime - startTime);
  }
}
