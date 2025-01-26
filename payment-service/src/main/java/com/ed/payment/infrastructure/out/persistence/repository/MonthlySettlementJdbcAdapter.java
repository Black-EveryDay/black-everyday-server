package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.CreateMonthlySettlementPort;
import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class MonthlySettlementJdbcAdapter implements CreateMonthlySettlementPort {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void bulkCreateMonthlySettlement(List<AggregatedDailySettlement> aggregatedDailySettlements) {
    String sql = "INSERT INTO ED_MONTHLY_SETTLEMENT (BRAND_PUBLIC_ID, TOTAL_NET_REVENUE, TOTAL_DISCOUNT_AMOUNT, TOTAL_COMMISSION, SETTLED_AT, CREATED_AT, UPDATED_AT, IS_DELETED) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    jdbcTemplate.batchUpdate(sql, aggregatedDailySettlements, aggregatedDailySettlements.size(),
        (PreparedStatement ps, AggregatedDailySettlement data) -> {
          ps.setString(1, data.getBrandPublicId());
          ps.setBigDecimal(2, data.getTotalNetRevenue());
          ps.setBigDecimal(3, data.getTotalDiscountAmount());
          ps.setBigDecimal(4, data.getTotalCommission());
          ps.setTimestamp(5, now);
          ps.setTimestamp(6, now);
          ps.setTimestamp(7, now);
          ps.setBoolean(8, false);
        });
  }
}