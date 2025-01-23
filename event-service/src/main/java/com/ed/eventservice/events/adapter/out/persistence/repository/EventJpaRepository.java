package com.ed.eventservice.events.adapter.out.persistence.repository;

import com.ed.eventservice.events.adapter.out.persistence.entity.EventJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventJpaEntity, Long> {

  //  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<EventJpaEntity> findByPublicId(String eventId);
}
