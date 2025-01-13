DROP TABLE IF EXISTS ED_ORDER_STATUS_HISTORY;
DROP TABLE IF EXISTS ED_ORDER_DELIVERY;
DROP TABLE IF EXISTS ED_ORDER_ITEM;
DROP TABLE IF EXISTS ED_ORDER_COUPON;
DROP TABLE IF EXISTS ED_ORDER;

CREATE TABLE ED_ORDER
(
    ORDER_ID              BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    ORDER_CANCEL_DEADLINE TIMESTAMP(6)                                                                                                            NOT NULL COMMENT '주문 취소 마감 시간',
    ORDER_DATE            TIMESTAMP(6)                                                                                                            NOT NULL COMMENT '주문 일시',
    ORDER_NAME            VARCHAR(255)                                                                                                            NOT NULL COMMENT '주문명',
    ORDER_PUBLIC_ID       VARCHAR(255)                                                                                                            NOT NULL COMMENT '주문 공개 식별자',
    ORDER_STATUS          ENUM ('ORDER_CREATED', 'PAYMENT_CHECKING', 'PAYMENT_COMPLETED', 'PAYMENT_FAILED', 'PAYMENT_REQUEST', 'PAYMENT_WAITING') NOT NULL COMMENT '주문 상태',
    PAID_AT               TIMESTAMP(6)                                                                                                            NULL COMMENT '결제 완료 시간',
    PAYMENT_DEADLINE      TIMESTAMP(6)                                                                                                            NOT NULL COMMENT '결제 마감 시간',
    PAYMENT_ID            VARCHAR(255)                                                                                                            NULL COMMENT '결제 식별자',
    PHONE_NUMBER          VARCHAR(255)                                                                                                            NOT NULL COMMENT '전화번호',
    TOTAL_AMOUNT          BIGINT                                                                                                                  NOT NULL COMMENT '총 주문 금액',
    TOTAL_QUANTITY        BIGINT                                                                                                                  NOT NULL COMMENT '총 주문 수량',
    USER_ID               VARCHAR(255)                                                                                                            NOT NULL COMMENT '사용자 식별자'
);


CREATE TABLE ED_ORDER_COUPON
(
    ORDER_COUPON_ID        BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    ORDER_COUPON_PUBLIC_ID VARCHAR(36)    NOT NULL COMMENT '주문 쿠폰 공개 식별자',
    COUPON_NAME            VARCHAR(255)   NULL COMMENT '쿠폰 이름',
    COUPON_TEMPLATE_ID     VARCHAR(255)   NULL COMMENT '쿠폰 템플릿 식별자',
    DISCOUNT_AMOUNT        DECIMAL(38, 2) NULL COMMENT '할인 금액',
    DISCOUNT_TYPE          TINYINT        NULL COMMENT '할인 유형',
    ORDER_ITEM_ID          BIGINT         NOT NULL COMMENT '주문 항목 식별자',
    CONSTRAINT UKglybf19sahmjb1tb2emljp2e1
        UNIQUE (ORDER_ITEM_ID),
    CHECK (`DISCOUNT_TYPE` BETWEEN 0 AND 1)
) COMMENT '주문 쿠폰 정보';


CREATE TABLE ED_ORDER_DELIVERY
(
    ORDER_DELIVERY_ID      BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    DELIVERY_COMPANY_CODE  TINYINT      NULL COMMENT '배송 회사 코드',
    INVOICE_NUMBER         VARCHAR(255) NULL COMMENT '송장 번호',
    ORDER_DELIVERY_STATUS  VARCHAR(255) NOT NULL COMMENT '주문 배송 상태',
    RECEIVER_ADDRESS       VARCHAR(255) NOT NULL COMMENT '수령인 주소',
    RECEIVER_MOBILE_NUMBER VARCHAR(255) NOT NULL COMMENT '수령인 휴대폰 번호',
    RECEIVER_NAME          VARCHAR(255) NOT NULL COMMENT '수령인 이름',
    RECEIVER_PHONE_NUMBER  VARCHAR(255) NULL COMMENT '수령인 전화번호',
    REQUIREMENT            VARCHAR(255) NULL COMMENT '배송 요구사항',
    ROAD_ZIPCODE           VARCHAR(255) NOT NULL COMMENT '도로명 우편번호',
    ZIPCODE                VARCHAR(255) NOT NULL COMMENT '지번 우편번호',
    ORDER_ID               BIGINT       NOT NULL COMMENT '주문 ID',
    CONSTRAINT UKm1q829qpswqyw5clabqk9dt01
        UNIQUE (ORDER_ID),
    CHECK (`DELIVERY_COMPANY_CODE` BETWEEN 0 AND 15)
) COMMENT '주문 배송 정보';

CREATE TABLE ED_ORDER_ITEM
(
    ORDER_ITEM_ID        BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    BRAND_ID             VARCHAR(255)           NOT NULL COMMENT '브랜드 ID',
    ORDER_ITEM_PUBLIC_ID VARCHAR(36)            NOT NULL COMMENT '주문 항목 공개 ID',
    PRODUCT_CATEGORY     ENUM ('BOTTOM', 'TOP') NOT NULL COMMENT '제품 카테고리',
    PRODUCT_ID           VARCHAR(255)           NOT NULL COMMENT '제품 ID',
    PRODUCT_NAME         VARCHAR(255)           NOT NULL COMMENT '제품명',
    QUANTITY             INT                    NOT NULL COMMENT '수량',
    SIZE                 VARCHAR(255)           NOT NULL COMMENT '사이즈',
    UNIT_PRICE           BIGINT                 NOT NULL COMMENT '단가',
    ORDER_ID             BIGINT                 NOT NULL COMMENT '주문 ID'
) COMMENT '주문 항목 정보';


CREATE TABLE ED_ORDER_STATUS_HISTORY
(
    ORDER_HISTORY_ID BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    ORDER_STATUS     VARCHAR(255) NOT NULL COMMENT '주문 상태',
    ORDER_ID         BIGINT       NOT NULL COMMENT '주문 ID'
) COMMENT '주문 상태 이력';


