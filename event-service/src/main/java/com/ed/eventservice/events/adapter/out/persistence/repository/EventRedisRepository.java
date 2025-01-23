package com.ed.eventservice.events.adapter.out.persistence.repository;

import com.ed.eventservice.events.application.port.out.EventOutPort;
import com.ed.eventservice.events.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRedisRepository implements EventOutPort {

  private final RedisTemplate<String, Long> redisTemplate;

  @Override
  public boolean checkParticipationPossible(Event event) {
    Long currentParticipants = redisTemplate.opsForValue()
        .increment(event.getPublicId().toString(), 1);

    if (currentParticipants <= event.getMaxParticipants()) {
      return true;
    }

    redisTemplate.opsForValue().decrement(event.getPublicId().toString(), 1);
    return false;
  }
}
