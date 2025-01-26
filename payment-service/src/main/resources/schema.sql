DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;

DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_SEQ;

CREATE TABLE BATCH_JOB_INSTANCE  (
    JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT ,
    JOB_NAME VARCHAR(100) NOT NULL,
    JOB_KEY VARCHAR(32) NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION  (
    JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT  ,
    JOB_INSTANCE_ID BIGINT NOT NULL,
    CREATE_TIME DATETIME(6) NOT NULL,
    START_TIME DATETIME(6) DEFAULT NULL ,
    END_TIME DATETIME(6) DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED DATETIME(6),
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
      references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
    JOB_EXECUTION_ID BIGINT NOT NULL ,
    PARAMETER_NAME VARCHAR(100) NOT NULL ,
    PARAMETER_TYPE VARCHAR(100) NOT NULL ,
    PARAMETER_VALUE VARCHAR(2500) ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
     references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION  (
    STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT NOT NULL,
    STEP_NAME VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID BIGINT NOT NULL,
    CREATE_TIME DATETIME(6) NOT NULL,
    START_TIME DATETIME(6) DEFAULT NULL ,
    END_TIME DATETIME(6) DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    COMMIT_COUNT BIGINT ,
    READ_COUNT BIGINT ,
    FILTER_COUNT BIGINT ,
    WRITE_COUNT BIGINT ,
    READ_SKIP_COUNT BIGINT ,
    WRITE_SKIP_COUNT BIGINT ,
    PROCESS_SKIP_COUNT BIGINT ,
    ROLLBACK_COUNT BIGINT ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED DATETIME(6),
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
       references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
    STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
       references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
    JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
      references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
    ID BIGINT NOT NULL,
    UNIQUE_KEY CHAR(1) NOT NULL,
    constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
    ID BIGINT NOT NULL,
    UNIQUE_KEY CHAR(1) NOT NULL,
    constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_SEQ (
    ID BIGINT NOT NULL,
    UNIQUE_KEY CHAR(1) NOT NULL,
    constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);

DROP TABLE if EXISTS ED_PAYMENT;
DROP TABLE if EXISTS ED_PAYMENT_HISTORY;
DROP TABLE if EXISTS ED_DAILY_SETTLEMENT;
DROP TABLE if EXISTS ED_MONTHLY_SETTLEMENT;

CREATE TABLE `ED_PAYMENT`
(
    `PAYMENT_ID`                BIGINT                                                                                                      NOT NULL AUTO_INCREMENT                     COMMENT '결제 PK',
    `PAYMENT_PUBLIC_ID`         VARCHAR(36)                                                                                                 DEFAULT NULL                                COMMENT '결제 외부 공개 ID',
    `PAYMENT_KEY`               VARCHAR(200)                                                                                                DEFAULT NULL                                COMMENT 'TossPayments 식별자',
    `IDEMPOTENCY_KEY`           VARCHAR(36)                                                                                                 DEFAULT NULL                                COMMENT '멱등키',
    `USER_PUBLIC_ID`            VARCHAR(36)                                                                                                 NOT NULL                                    COMMENT '사용자의 외부 공개 ID',
    `PAYMENT_STATUS`            ENUM('READY','VERIFY_FAILED', 'DONE', 'PARTIAL_CANCELED', 'CANCELED', 'ABORTED', 'SETTLEMENT_COMPLETE')     NOT NULL                                    COMMENT '결제 상태',
    `ORDER_PUBLIC_ID`           CHAR(18)                                                                                                    NOT NULL                                    COMMENT '주문 외부 공개 ID',
    `ORDER_NAME`                VARCHAR(255)                                                                                                NOT NULL                                    COMMENT '주문명',
    `TOTAL_AMOUNT`              BIGINT                                                                                                      DEFAULT NULL                                COMMENT '총 결제 금액',
    `BALANCE_AMOUNT`            BIGINT                                                                                                      DEFAULT NULL                                COMMENT '취소 가능 금액',
    `CONFIRM_DEADLINE`          TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '결제 승인 유효기한',
    `CANCEL_DEADLINE`           TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '결제 취소 유효기한',
    `CREATED_AT`                TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '결제 데이터 생성일',
    `UPDATED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '결제 데이터 수정일',
    `DELETED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '결제 데이터 삭제일',
    `IS_DELETED`                BIT(1)                                                                                                      NOT NULL                                    COMMENT '결제 데이터 삭제 여부',
    PRIMARY KEY (`PAYMENT_ID`),
    UNIQUE KEY `UKMBQSCLI8W5457J9WLLJ1IJP30` (`PAYMENT_PUBLIC_ID`),
    UNIQUE KEY `UK2M21C5PKJISKFQHNXSL3U5L3V` (`PAYMENT_KEY`),
    UNIQUE KEY `UKDM29Y305P71XAP9LQ3UITWI25` (`ORDER_PUBLIC_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin                                                                                                                             COMMENT='업데이트된 결제 정보를 저장하는 테이블';

CREATE TABLE `ED_PAYMENT_HISTORY`
(
    `PAYMENT_HISTORY_ID`        BIGINT                                                                                                      NOT NULL AUTO_INCREMENT                     COMMENT '결제 히스토리 PK',
    `PAYMENT_HISTORY_PUBLIC_ID` VARCHAR(36)                                                                                                 NOT NULL                                    COMMENT '결제 히스토리 외부 공개 ID',
    `PAYMENT_ID`                BIGINT                                                                                                      NOT NULL                                    COMMENT '결제 PK',
    `PAYMENT_STATUS`            ENUM('READY','VERIFY_FAILED', 'DONE', 'PARTIAL_CANCELED', 'CANCELED', 'ABORTED', 'SETTLEMENT_COMPLETE')     NOT NULL                                    COMMENT '결제 상태',
    `CANCEL_AMOUNT`             BIGINT                                                                                                      DEFAULT NULL                                COMMENT '취소 금액',
    `CANCEL_REASON`             VARCHAR(255)                                                                                                DEFAULT NULL                                COMMENT '취소 이유',
    `CREATED_AT`                TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '결제 히스토리 데이터 생성일',
    `UPDATED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '결제 히스토리 데이터 수정일',
    `DELETED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '결제 히스토리 데이터 삭제일',
    `IS_DELETED`                BIT(1)                                                                                                      NOT NULL                                    COMMENT '결제 히스토리 데이터 삭제 여부',
    PRIMARY KEY (`PAYMENT_HISTORY_ID`),
    UNIQUE KEY `UK9T6O3DCIN5TSFFKR69GIINPO5` (`PAYMENT_HISTORY_PUBLIC_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin                                                                                                                             COMMENT='모든 결제 히스토리 정보를 저장하는 테이블';

CREATE TABLE `ED_DAILY_SETTLEMENT`
(
    `DAILY_SETTLEMENT_ID`       BIGINT                                                                                                      NOT NULL AUTO_INCREMENT                     COMMENT '일일 정산 PK',
    `ORDER_PUBLIC_ID`           VARCHAR(18)                                                                                                 NOT NULL                                    COMMENT '브랜드 외부 공개 ID',
    `BRAND_PUBLIC_ID`           VARCHAR(36)                                                                                                 NOT NULL                                    COMMENT '주문 외부 공개 ID',
    `PRODUCT_PUBLIC_ID`         VARCHAR(36)                                                                                                 NOT NULL                                    COMMENT '상품 외부 공개 ID',
    `NET_REVENUE`               DECIMAL(38, 2)                                                                                              NOT NULL                                    COMMENT '순수익',
    `DISCOUNT_AMOUNT`           DECIMAL(38, 2)                                                                                              NOT NULL                                    COMMENT '할인 금액',
    `COMMISSION`                DECIMAL(38, 2)                                                                                              DEFAULT NULL                                COMMENT '수수료',
    `SETTLED_AT`                TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '정산일',
    `CREATED_AT`                TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '일일 정산 데이터 생성일',
    `UPDATED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '일일 정산 데이터 수정일',
    `DELETED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '일일 정산 데이터 삭제일',
    `IS_DELETED`                BIT(1)                                                                                                      NOT NULL                                    COMMENT '일일 정산 데이터 삭제 여부',
    PRIMARY KEY (`DAILY_SETTLEMENT_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin                                                                                                                             COMMENT='일일 정산 정보를 저장하는 테이블';

CREATE TABLE `ED_MONTHLY_SETTLEMENT`
(
    `MONTHLY_SETTLEMENT_ID`     BIGINT                                                                                                      NOT NULL AUTO_INCREMENT                     COMMENT '정산 상세 PK',
    `BRAND_PUBLIC_ID`           VARCHAR(36)                                                                                                 NOT NULL                                    COMMENT '브랜드 외부 공개 ID',
    `TOTAL_NET_REVENUE`         DECIMAL(38, 2)                                                                                              NOT NULL                                    COMMENT '총 순수익',
    `TOTAL_DISCOUNT_AMOUNT`     DECIMAL(38, 2)                                                                                              NOT NULL                                    COMMENT '총 할인 금액',
    `TOTAL_COMMISSION`          DECIMAL(38, 2)                                                                                              NOT NULL                                    COMMENT '총 수수료',
    `SETTLED_AT`                TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '정산일',
    `CREATED_AT`                TIMESTAMP                                                                                                   NOT NULL                                    COMMENT '월간 정산 데이터 생성일',
    `UPDATED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '월간 정산 데이터 수정일',
    `DELETED_AT`                TIMESTAMP                                                                                                   DEFAULT NULL                                COMMENT '월간 정산 데이터 삭제일',
    `IS_DELETED`                BIT(1)                                                                                                      NOT NULL                                    COMMENT '월간 정산 데이터 삭제 여부',
    PRIMARY KEY (`MONTHLY_SETTLEMENT_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin                                                                                                                             COMMENT='월간 정산 정보를 저장하는 테이블';