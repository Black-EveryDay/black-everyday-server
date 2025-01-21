DROP TABLE IF EXISTS ED_ORDER_EVENT_STATUS_HISTORY;
DROP TABLE IF EXISTS ED_ORDER_STATUS_HISTORY;
DROP TABLE IF EXISTS ED_ORDER_EVENT;
DROP TABLE IF EXISTS ED_ORDER_DELIVERY;
DROP TABLE IF EXISTS ED_ORDER_ITEM;
DROP TABLE IF EXISTS ED_ORDER_COUPON;
DROP TABLE IF EXISTS ED_ORDER;

CREATE TABLE ED_ORDER (
    ORDER_ID               BIGINT AUTO_INCREMENT,
    USER_ID                VARCHAR(255)    NOT NULL                COMMENT '사용자 식별자',
    ORDER_PUBLIC_ID        VARCHAR(255)    NOT NULL                COMMENT '주문 공개 식별자',
    ORDER_PUBLIC_NAME      VARCHAR(255)    NOT NULL                COMMENT '주문 공개 이름',
    ORDER_NAME             VARCHAR(255)    NOT NULL                COMMENT '주문자',
    ORDER_STATUS           CHAR(2)         NOT NULL                COMMENT '주문 상태',
    PHONE_NUMBER           VARCHAR(255)    NOT NULL                COMMENT '전화번호',
    TOTAL_AMOUNT           BIGINT          NOT NULL                COMMENT '총 주문 금액',
    TOTAL_QUANTITY         BIGINT          NOT NULL                COMMENT '총 주문 수량',
    ORDER_DATE             TIMESTAMP(6)    NOT NULL                COMMENT '주문 일시',
    ORDER_CANCEL_DEADLINE  TIMESTAMP(6)    NOT NULL                COMMENT '주문 취소 마감 시간',
    PRODUCT_TRANSACTION_ID VARCHAR(255)    NOT NULL                COMMENT '상품 트랜잭션 ID',
    PAID_AT                TIMESTAMP(6)    NULL                    COMMENT '결제 완료 시간',
    PAYMENT_ID             VARCHAR(255)    NULL                    COMMENT '결제 식별자',
    PAYMENT_DEADLINE       TIMESTAMP(6)    NOT NULL                COMMENT '결제 마감 시간',
    IS_DELETED             BOOL            NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_BY             CHAR(36)                                COMMENT '생성자',
    CREATED_AT             TIMESTAMP       NOT NULL                COMMENT '생성 일시',
    UPDATED_BY             CHAR(36)                                COMMENT '수정자',
    UPDATED_AT             TIMESTAMP       NOT NULL                COMMENT '수정 일시',
    DELETED_BY             CHAR(36)                                COMMENT '삭제자',
    DELETED_AT             TIMESTAMP                               COMMENT '삭제 일시',
    PRIMARY KEY (ORDER_ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '주문';

CREATE TABLE ED_ORDER_COUPON (
    ORDER_COUPON_ID        BIGINT AUTO_INCREMENT,
    ORDER_COUPON_PUBLIC_ID VARCHAR(36)    NOT NULL                COMMENT '주문 쿠폰 공개 식별자',
    COUPON_NAME            VARCHAR(255)   NULL                    COMMENT '쿠폰 이름',
    COUPON_TEMPLATE_ID     VARCHAR(255)   NULL                    COMMENT '쿠폰 템플릿 식별자',
    DISCOUNT_AMOUNT        DECIMAL(38, 2) NULL                    COMMENT '할인 금액',
    DISCOUNT_TYPE          TINYINT        NULL                    COMMENT '할인 유형',
    ORDER_ITEM_ID          BIGINT         NOT NULL                COMMENT '주문 항목 식별자',
    IS_DELETED             BOOL           NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_BY             CHAR(36)                               COMMENT '생성자',
    CREATED_AT             TIMESTAMP      NOT NULL                COMMENT '생성 일시',
    UPDATED_BY             CHAR(36)                               COMMENT '수정자',
    UPDATED_AT             TIMESTAMP      NOT NULL                COMMENT '수정 일시',
    DELETED_BY             CHAR(36)                               COMMENT '삭제자',
    DELETED_AT             TIMESTAMP                              COMMENT '삭제 일시',
    PRIMARY KEY (ORDER_COUPON_ID),
    CHECK (`DISCOUNT_TYPE` BETWEEN 0 AND 1)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '주문 쿠폰 정보';

CREATE TABLE ED_ORDER_DELIVERY (
    ORDER_ID               BIGINT       NOT NULL                COMMENT '주문 ID',
    ORDER_DELIVERY_ID      BIGINT AUTO_INCREMENT,
    DELIVERY_COMPANY_CODE  TINYINT      NULL                    COMMENT '배송 회사 코드',
    INVOICE_NUMBER         VARCHAR(255) NULL                    COMMENT '송장 번호',
    ORDER_DELIVERY_STATUS  VARCHAR(255) NOT NULL                COMMENT '주문 배송 상태',
    RECEIVER_ADDRESS       VARCHAR(255) NOT NULL                COMMENT '수령인 주소',
    RECEIVER_MOBILE_NUMBER VARCHAR(255) NOT NULL                COMMENT '수령인 휴대폰 번호',
    RECEIVER_NAME          VARCHAR(255) NOT NULL                COMMENT '수령인 이름',
    RECEIVER_PHONE_NUMBER  VARCHAR(255) NULL                    COMMENT '수령인 전화번호',
    REQUIREMENT            VARCHAR(255) NULL                    COMMENT '배송 요구사항',
    ROAD_ZIPCODE           VARCHAR(255) NOT NULL                COMMENT '도로명 우편번호',
    ZIPCODE                VARCHAR(255) NOT NULL                COMMENT '지번 우편번호',
    IS_DELETED             BOOL         NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_BY             CHAR(36)                             COMMENT '생성자',
    CREATED_AT             TIMESTAMP    NOT NULL                COMMENT '생성 일시',
    UPDATED_BY             CHAR(36)                             COMMENT '수정자',
    UPDATED_AT             TIMESTAMP    NOT NULL                COMMENT '수정 일시',
    DELETED_BY             CHAR(36)                             COMMENT '삭제자',
    DELETED_AT             TIMESTAMP                            COMMENT '삭제 일시',
    PRIMARY KEY (ORDER_DELIVERY_ID),
    CHECK (`DELIVERY_COMPANY_CODE` BETWEEN 0 AND 15)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci  COMMENT '주문 배송 정보';

CREATE TABLE ED_ORDER_ITEM (
    ORDER_ID             BIGINT                 NOT NULL COMMENT '주문 ID',
    ORDER_ITEM_ID        BIGINT AUTO_INCREMENT,
    BRAND_ID             VARCHAR(255)           NOT NULL               COMMENT '브랜드 ID',
    ORDER_ITEM_PUBLIC_ID VARCHAR(36)            NOT NULL               COMMENT '주문 항목 공개 ID',
    PRODUCT_CATEGORY     ENUM ('BOTTOM', 'TOP') NOT NULL               COMMENT '제품 카테고리',
    PRODUCT_ID           VARCHAR(255)           NOT NULL               COMMENT '제품 ID',
    PRODUCT_NAME         VARCHAR(255)           NOT NULL               COMMENT '제품명',
    QUANTITY             INT                    NOT NULL               COMMENT '수량',
    SIZE                 VARCHAR(255)           NOT NULL               COMMENT '사이즈',
    UNIT_PRICE           BIGINT                 NOT NULL               COMMENT '단가',
    IS_DELETED                    BOOL         NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_BY           CHAR(36)                                      COMMENT '생성자',
    CREATED_AT           TIMESTAMP              NOT NULL               COMMENT '생성 일시',
    UPDATED_BY           CHAR(36)                                      COMMENT '수정자',
    UPDATED_AT           TIMESTAMP              NOT NULL               COMMENT '수정 일시',
    DELETED_BY           CHAR(36)                                      COMMENT '삭제자',
    DELETED_AT           TIMESTAMP                                     COMMENT '삭제 일시',
    PRIMARY KEY (ORDER_ITEM_ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '주문 항목 정보';

CREATE TABLE ED_ORDER_STATUS_HISTORY (
    ORDER_ID                 BIGINT    NOT NULL                COMMENT '주문 ID',
    ORDER_STATUS_HISTORY_ID  BIGINT AUTO_INCREMENT,
    ORDER_STATUS             CHAR(2)   NOT NULL                COMMENT '주문 상태',
    IS_DELETED               BOOL      NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_AT               TIMESTAMP NOT NULL                COMMENT '생성 일시',
    UPDATED_AT               TIMESTAMP NOT NULL                COMMENT '수정 일시',
    DELETED_AT               TIMESTAMP                         COMMENT '삭제 일시',
    PRIMARY KEY (ORDER_STATUS_HISTORY_ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '주문 상태 이력';

CREATE TABLE ED_ORDER_EVENT (
    ORDER_EVENT_ID          BIGINT AUTO_INCREMENT               COMMENT '주문 이벤트 고유 식별자',
    ORDER_EVENT_PUBLIC_ID   VARCHAR(36) NOT NULL                COMMENT '주문 이벤트 공개 식별자',
    ORDER_ID                BIGINT                              COMMENT '관련 주문 ID',
    ORDER_EVENT_STATUS      VARCHAR(50) NOT NULL                COMMENT '주문 이벤트 상태',
    PLAY_LOAD               BLOB                                COMMENT '이벤트 페이로드 데이터',
    IS_DELETED              BOOL        NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_BY              CHAR(36)                            COMMENT '생성자',
    CREATED_AT              TIMESTAMP   NOT NULL                COMMENT '생성 일시',
    UPDATED_BY              CHAR(36)                            COMMENT '수정자',
    UPDATED_AT              TIMESTAMP   NOT NULL                COMMENT '수정 일시',
    DELETED_BY              CHAR(36)                            COMMENT '삭제자',
    DELETED_AT              TIMESTAMP                           COMMENT '삭제 일시',

    PRIMARY KEY (ORDER_EVENT_ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '주문 이벤트';

CREATE TABLE ED_ORDER_EVENT_STATUS_HISTORY (
    ORDER_EVENT_ID                BIGINT       NOT NULL                COMMENT '주문 이벤트 ID',
    ORDER_EVENT_STATUS_HISTORY_ID BIGINT AUTO_INCREMENT ,
    ORDER_EVENT_STATUS            VARCHAR(50)  NOT NULL                COMMENT '주문 이벤트 상태',
    IS_DELETED                    BOOL         NOT NULL  DEFAULT FALSE COMMENT '삭제 여부',
    CREATED_AT                    TIMESTAMP    NOT NULL                COMMENT '생성 일시',
    UPDATED_AT                    TIMESTAMP    NOT NULL                COMMENT '수정 일시',
    DELETED_AT                    TIMESTAMP                            COMMENT '삭제 일시',

    PRIMARY KEY (ORDER_EVENT_STATUS_HISTORY_ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '주문 이벤트 상태 이력';
