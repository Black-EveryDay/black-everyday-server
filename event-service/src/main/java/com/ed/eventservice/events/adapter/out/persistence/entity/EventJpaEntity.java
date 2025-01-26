package com.ed.eventservice.events.adapter.out.persistence.entity;

import com.ed.eventservice.events.domain.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ED_EVENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "EVENT_PUBLIC_ID", nullable = false, updatable = false, unique = true, length = 36)
  private String publicId;

  @Column(name = "EVENT_NAME", nullable = false, length = 50)
  private String name;

  @Column(name = "EVENT_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private EventType type;

  @Column(name = "EVENT_START_AT", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime startAt;

  @Column(name = "EVENT_END_AT", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime endAt;

  @Column(name = "COUPON_TEMPLATE_ID", nullable = false, length = 36)
  private String couponTemplateId;

  @Column(name = "MAX_PARTICIPANTS", nullable = false)
  private Long maxParticipants;

  @Column(name = "CURRENT_PARTICIPANTS", nullable = false)
  private Long currentParticipants;

  @Builder
  private EventJpaEntity(
      Long id,
      String publicId,
      String name,
      EventType type,
      LocalDateTime startAt,
      LocalDateTime endAt,
      String couponTemplateId,
      Long maxParticipants,
      Long currentParticipants
  ) {

    this.id = id;
    this.publicId = publicId;
    this.name = name;
    this.type = type;
    this.startAt = startAt;
    this.endAt = endAt;
    this.couponTemplateId = couponTemplateId;
    this.maxParticipants = maxParticipants;
    this.currentParticipants = currentParticipants;
  }
}
