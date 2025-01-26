package com.ed.payment.infrastructure.out.batch.chunk.reader;

import com.ed.payment.application.port.out.persistence.GetDailySettlementPort;
import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.util.CollectionUtils;

public class AggregateDailySettlementCustomItemReader extends
    AbstractPagingItemReader<AggregatedDailySettlement> {

    private final Map<String, Object> jpaPropertyMap = new HashMap<>();
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private final GetDailySettlementPort getDailySettlementPort;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final int pageSize;
    private String currentId = null;

    public AggregateDailySettlementCustomItemReader(
        EntityManagerFactory emf, GetDailySettlementPort getDailySettlementPort,
        LocalDateTime startDateTime, LocalDateTime endDateTime, int pageSize) {
        this.entityManagerFactory = emf;
        this.getDailySettlementPort = getDailySettlementPort;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.pageSize = pageSize;
        setPageSize(pageSize);
    }

    @Override
    protected void doOpen() throws Exception {
        super.doOpen();
        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
    }

    @Override
    protected void doReadPage() {
        EntityTransaction tx = getTransaction();

        initResults();

        List<AggregatedDailySettlement> aggregateLastMonthDailySettlements = getDailySettlementPort
            .aggregateLastMonthDailySettlements(startDateTime, endDateTime, currentId, pageSize);
        results.addAll(aggregateLastMonthDailySettlements);

        nextCurrentId();
        tx.commit();
    }

    private void initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
    }

    private void nextCurrentId() {
        if (isEmptyResult()) {
            currentId = null;
        } else {
            currentId = results.getLast().getBrandPublicId();
        }
    }

    private boolean isEmptyResult() {
        return CollectionUtils.isEmpty(results) || results.getFirst() == null;
    }

    private EntityTransaction getTransaction() {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        entityManager.flush();
        entityManager.clear();

        return tx;
    }
}
