package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.CreateDailySettlementPort;
import com.ed.payment.domain.DailySettlement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class DailySettlementJdbcAdapter implements CreateDailySettlementPort {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void bulkCreateDailySettlement(List<DailySettlement> dailySettlements) {
      String sql = "INSERT INTO ED_DAILY_SETTLEMENT (ORDER_PUBLIC_ID, BRAND_PUBLIC_ID, PRODUCT_PUBLIC_ID, NET_REVENUE, DISCOUNT_AMOUNT, COMMISSION, SETTLED_AT, CREATED_AT, UPDATED_AT, IS_DELETED) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      jdbcTemplate.batchUpdate(sql, dailySettlements, dailySettlements.size(),
          (PreparedStatement ps, DailySettlement data) -> {
              ps.setString(1, data.getOrderPublicId());
              ps.setString(2, data.getBrandPublicId());
              ps.setString(3, data.getProductPublicId());
              ps.setBigDecimal(4, data.getNetRevenue());
              ps.setBigDecimal(5, data.getDiscountAmount());
              ps.setBigDecimal(6, data.getCommission());
              ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
              ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
              ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
              ps.setBoolean(10, false);
          });
  }
}
