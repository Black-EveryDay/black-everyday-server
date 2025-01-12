CREATE TABLE ed_coupon_template
(
    ID                       BIGINT           NOT NULL,
    COUPON_NAME              VARCHAR(255)     NOT NULL,
    PUBLIC_ID                VARCHAR(36)      NOT NULL,
    COUPON_ISSUANCE_TYPE     VARCHAR(255)     NOT NULL,
    COUPON_ISSUER_TYPE       VARCHAR(255)     NOT NULL,
    COUPON_ISSUER_ID         VARCHAR(36)      NULL,
    COUPON_USAGE_TARGET_TYPE VARCHAR(255)     NOT NULL,
    COUPON_USAGE_TARGET_ID   VARCHAR(36)      NULL,
    DISCOUNT_TYPE            VARCHAR(255)     NOT NULL,
    DISCOUNT_VALUE           DECIMAL(10, 2)   NOT NULL,
    IS_ISSUABLE              BIT(1)           NOT NULL,
    EXPIRATION_DAYS          timestamp         NULL,
    FIXED_EXPIRATION_DATE    timestamp         NULL,
    MAX_ISSUANCE             INT              NULL,
    IS_DELETED               BIT(1) DEFAULT 0 NULL,
    CREATED_AT               timestamp         NULL,
    CREATED_BY               VARCHAR(36)      NULL,
    UPDATED_AT               timestamp         NULL,
    UPDATED_BY               VARCHAR(36)      NULL,
    DELETED_AT               timestamp         NULL,
    DELETED_BY               VARCHAR(36)      NULL,
    CONSTRAINT pk_ed_coupon_template PRIMARY KEY (id)
);

CREATE TABLE ed_coupon
(
    ID                 BIGINT           NOT NULL,
    COUPON_TEMPLATE_ID VARCHAR(36)      NOT NULL,
    PUBLIC_ID          VARCHAR(36)      NOT NULL,
    USER_ID            VARCHAR(36)      NULL,
    STATE              VARCHAR(255)     NOT NULL,
    EXPIRATION_DATE    timestamp         NOT NULL,
    ISSUED_AT          timestamp         NOT NULL,
    IS_DELETED         BIT(1) DEFAULT 0 NULL,
    CREATED_AT         timestamp         NULL,
    CREATED_BY         VARCHAR(36)      NULL,
    UPDATED_AT         timestamp         NULL,
    UPDATED_BY         VARCHAR(36)      NULL,
    DELETED_AT         timestamp         NULL,
    DELETED_BY         VARCHAR(36)      NULL,
    CONSTRAINT pk_ed_coupon PRIMARY KEY (id)
);

CREATE TABLE ed_coupon_status_change_log
(
    ID            BIGINT           NOT NULL,
    BEFORE_STATUS VARCHAR(255)     NOT NULL,
    AFTER_STATUS  VARCHAR(255)     NOT NULL,
    REASON        VARCHAR(255)     NOT NULL,
    CHANGED_AT    timestamp         NOT NULL,
    ORDER_ID      VARCHAR(36)       NULL,
    COUPON_ID     BIGINT           NOT NULL,
    IS_DELETED    BIT(1) DEFAULT 0 NULL,
    CREATED_AT    timestamp         NULL,
    CREATED_BY    VARCHAR(36)      NULL,
    UPDATED_AT    timestamp         NULL,
    UPDATED_BY    VARCHAR(36)      NULL,
    DELETED_AT    timestamp         NULL,
    DELETED_BY    VARCHAR(36)      NULL,
    CONSTRAINT pk_ed_coupon_status_change_log PRIMARY KEY (id)
);

ALTER TABLE ed_coupon
    ADD CONSTRAINT uc_ed_coupon_publicid UNIQUE (public_id);

ALTER TABLE ed_coupon_template
    ADD CONSTRAINT uc_ed_coupon_template_publicid UNIQUE (public_id);