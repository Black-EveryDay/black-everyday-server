package com.ed.payment.infrastructure.out.batch.chunk.reader;

import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
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

public class SettleablePaymentCustomItemReader extends
    AbstractPagingItemReader<SettleablePaymentResponse> {

    private final Map<String, Object> jpaPropertyMap = new HashMap<>();
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private GetPaymentPort getPaymentPort;
    private LocalDateTime requestDateTime;
    private Long currentId = null;


    public SettleablePaymentCustomItemReader(EntityManagerFactory emf,
        GetPaymentPort getPaymentPort, LocalDateTime requestDateTime, int pageSize) {
        this.entityManagerFactory = emf;
        this.getPaymentPort = getPaymentPort;
        this.requestDateTime = requestDateTime;
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

        List<SettleablePaymentResponse> settleablePayments = getPaymentPort
            .getSettleablePayments(requestDateTime, currentId, getPageSize());
        results.addAll(settleablePayments);

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
            currentId = results.getLast().getPaymentId();
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