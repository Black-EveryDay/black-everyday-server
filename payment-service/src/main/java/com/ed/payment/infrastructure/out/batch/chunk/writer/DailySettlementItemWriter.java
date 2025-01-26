package com.ed.payment.infrastructure.out.batch.chunk.writer;

import com.ed.payment.application.port.out.feign.GetOrderSettlementsPort;
import com.ed.payment.application.port.out.feign.dtos.OrderSettlementResponse;
import com.ed.payment.application.port.out.persistence.CreateDailySettlementPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
import com.ed.payment.domain.DailySettlement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailySettlementItemWriter implements ItemWriter<SettleablePaymentResponse> {

  private final GetOrderSettlementsPort getOrderSettlementsPort;
  private final CreateDailySettlementPort createDailySettlementPort;
  private final UpdatePaymentPort updatePaymentPort;

  @Override
  public void write(Chunk<? extends SettleablePaymentResponse> chunk) {

    List<OrderSettlementResponse> orderSettlementResponses =
        getOrderSettlementsPort.getOrderSettlements(getOrderPublicIds(chunk));

    List<DailySettlement> dailySettlements = DailySettlement.from(orderSettlementResponses);

    createDailySettlementPort.bulkCreateDailySettlement(dailySettlements);

    updatePaymentPort.bulkUpdatePaymentStatusByIds(getPaymentsIds(chunk));
  }

  private List<String> getOrderPublicIds(Chunk<? extends SettleablePaymentResponse> chunk) {
    return chunk.getItems().stream()
        .map(SettleablePaymentResponse::getOrderPublicId)
        .toList();
  }

  private List<Long> getPaymentsIds(Chunk<? extends SettleablePaymentResponse> chunk) {
    return chunk.getItems().stream()
        .map(SettleablePaymentResponse::getPaymentId)
        .toList();
  }
}
