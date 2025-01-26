package com.ed.payment.infrastructure.out.batch.chunk.reader;

import com.ed.payment.application.port.out.persistence.GetDailySettlementPort;
import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.database.AbstractPagingItemReader;

public class AggregateDailySettlementCustomItemReader extends
    AbstractPagingItemReader<AggregatedDailySettlement> {

    private final GetDailySettlementPort getDailySettlementPort;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final int pageSize;
    private Long currentId = null;

    public AggregateDailySettlementCustomItemReader(GetDailySettlementPort getDailySettlementPort,
        LocalDateTime startDateTime, LocalDateTime endDateTime, int pageSize) {
        this.getDailySettlementPort = getDailySettlementPort;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

        List<AggregatedDailySettlement> aggregateLastMonthDailySettlements = getDailySettlementPort
            .aggregateLastMonthDailySettlements(startDateTime, endDateTime, currentId, pageSize);

        results.addAll(aggregateLastMonthDailySettlements);

        if (!results.isEmpty()) {
            currentId = results.getLast().getDailySettlementId();
        } else {
            currentId = null;
        }
    }
}
