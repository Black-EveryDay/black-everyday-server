package com.ed.payment.infrastructure.out.batch.chunk.reader;

import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.database.AbstractPagingItemReader;

public class SettleablePaymentCustomItemReader extends
    AbstractPagingItemReader<SettleablePaymentResponse> {

    private final GetPaymentPort getPaymentPort;
    private final LocalDateTime requestDateTime;
    private final int pageSize;
    private Long currentId = null;

    public SettleablePaymentCustomItemReader(LocalDateTime requestDateTime, GetPaymentPort getPaymentPort, int pageSize) {
        this.getPaymentPort = getPaymentPort;
        this.requestDateTime = requestDateTime;
        this.pageSize = pageSize;
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {

        if (results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        List<SettleablePaymentResponse> settleablePayments = getPaymentPort
            .getSettleablePayments(requestDateTime, currentId, pageSize);

        results.addAll(settleablePayments);

        if (!results.isEmpty()) {
            currentId = results.getLast().getPaymentId();
        } else {
            currentId = null;
        }
    }
}
