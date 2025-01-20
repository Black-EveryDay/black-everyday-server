DROP TABLE if EXISTS ED_PAYMENT;
DROP TABLE if EXISTS ED_PAYMENT_HISTORY;

CREATE TABLE `ED_PAYMENT`
(
    `PAYMENT_ID`                BIGINT                                                                              NOT NULL AUTO_INCREMENT                     COMMENT '결제 PK',
    `PAYMENT_PUBLIC_ID`         VARCHAR(36)                                                                         DEFAULT NULL                                COMMENT '결제 외부 공개 ID',
    `PAYMENT_KEY`               VARCHAR(200)                                                                        DEFAULT NULL                                COMMENT 'TossPayments 식별자',
    `IDEMPOTENCY_KEY`           VARCHAR(36)                                                                         DEFAULT NULL                                COMMENT '멱등키',
    `USER_PUBLIC_ID`            VARCHAR(36)                                                                         NOT NULL                                    COMMENT '사용자의 외부 공개 ID',
    `PAYMENT_STATUS`            ENUM('READY','VERIFY_FAILED', 'DONE', 'PARTIAL_CANCELED', 'CANCELED', 'ABORTED')    NOT NULL                                    COMMENT '결제 상태',
    `ORDER_PUBLIC_ID`           CHAR(18)                                                                            NOT NULL                                    COMMENT '주문 외부 공개 ID',
    `ORDER_NAME`                VARCHAR(255)                                                                        NOT NULL                                    COMMENT '주문명',
    `TOTAL_AMOUNT`              BIGINT                                                                              DEFAULT NULL                                COMMENT '총 결제 금액',
    `BALANCE_AMOUNT`            BIGINT                                                                              DEFAULT NULL                                COMMENT '취소 가능 금액',
    `CONFIRM_DEADLINE`          TIMESTAMP                                                                           NOT NULL                                    COMMENT '결제 승인 유효기한',
    `CANCEL_DEADLINE`           TIMESTAMP                                                                           NOT NULL                                    COMMENT '결제 취소 유효기한',
    `CREATED_AT`                TIMESTAMP                                                                           NOT NULL                                    COMMENT '결제 데이터 생성일',
    `UPDATED_AT`                TIMESTAMP                                                                           DEFAULT NULL                                COMMENT '결제 데이터 수정일',
    `DELETED_AT`                TIMESTAMP                                                                           DEFAULT NULL                                COMMENT '결제 데이터 삭제일',
    `IS_DELETED`                BIT(1)                                                                              NOT NULL                                    COMMENT '결제 데이터 삭제 여부',
    PRIMARY KEY (`PAYMENT_ID`),
    UNIQUE KEY `UKMBQSCLI8W5457J9WLLJ1IJP30` (`PAYMENT_PUBLIC_ID`),
    UNIQUE KEY `UK2M21C5PKJISKFQHNXSL3U5L3V` (`PAYMENT_KEY`),
    UNIQUE KEY `UKDM29Y305P71XAP9LQ3UITWI25` (`ORDER_PUBLIC_ID`)
) ENGINE=INNODB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin                                                                                   COMMENT='업데이트된 결제 정보를 저장하는 테이블';

CREATE TABLE `ED_PAYMENT_HISTORY`
(
    `PAYMENT_HISTORY_ID`        BIGINT                                                                              NOT NULL AUTO_INCREMENT                     COMMENT '결제 히스토리 PK',
    `PAYMENT_HISTORY_PUBLIC_ID` VARCHAR(36)                                                                         NOT NULL                                    COMMENT '결제 히스토리 외부 공개 ID',
    `PAYMENT_ID`                BIGINT                                                                              NOT NULL                                    COMMENT '결제 PK',
    `PAYMENT_STATUS`            ENUM('READY','VERIFY_FAILED', 'DONE', 'PARTIAL_CANCELED', 'CANCELED', 'ABORTED')    NOT NULL                                    COMMENT '결제 상태',
    `CANCEL_AMOUNT`             BIGINT                                                                              DEFAULT NULL                                COMMENT '취소 금액',
    `CANCEL_REASON`             VARCHAR(255)                                                                        DEFAULT NULL                                COMMENT '취소 이유',
    `CREATED_AT`                TIMESTAMP                                                                           NOT NULL                                    COMMENT '결제 히스토리 데이터 생성일',
    `UPDATED_AT`                TIMESTAMP                                                                           DEFAULT NULL                                COMMENT '결제 히스토리 데이터 수정일',
    `DELETED_AT`                TIMESTAMP                                                                           DEFAULT NULL                                COMMENT '결제 히스토리 데이터 삭제일',
    `IS_DELETED`                BIT(1)                                                                              NOT NULL                                    COMMENT '결제 히스토리 데이터 삭제 여부',
    PRIMARY KEY (`PAYMENT_HISTORY_ID`),
    UNIQUE KEY `UK9T6O3DCIN5TSFFKR69GIINPO5` (`PAYMENT_HISTORY_PUBLIC_ID`)
) ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin                                                                                   COMMENT='모든 결제 히스토리 정보를 저장하는 테이블';