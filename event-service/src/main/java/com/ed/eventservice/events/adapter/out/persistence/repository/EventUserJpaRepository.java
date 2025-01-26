package com.ed.eventservice.events.adapter.out.persistence.repository;

import com.ed.eventservice.events.adapter.out.persistence.entity.EventUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUserJpaRepository extends JpaRepository<EventUserEntity, Long> {

  boolean existsByEventIdAndUserId(String eventId, String userId);
}
