INSERT INTO ED_BRANDS
(brand_address, brand_name, brand_type, created_at, is_deleted)
VALUES('서울특별시 서초구', '스파르타1', 'CASUAL', now(), '' );
가
                                        `UPDATED_BY` bigint DEFAULT NULL,
                                        `DELETED_AT` datetime(6) DEFAULT NULL,
                                        `DELETED_BY` bigint DEFAULT NULL,
                                        `IS_DELETED` bit(1) NOT NULL,
                                        PRIMARY KEY (`BOTTOM_SIZE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ED_BRANDS`;
CREATE TABLE `ED_BRANDS` (
                             `BRAND_ID` bigint NOT NULL AUTO_INCREMENT,
                             `BRAND_ADDRESS` varchar(255) NOT NULL,
                             `BRAND_NAME` varchar(50) NOT NULL,
                             `BRAND_TYPE` enum('CASUAL','CONTEMPORARY','DESIGNER','MINIMAL','OUTDOOR','SPORTS','STREET','VINTAGE') NOT NULL,
                             `CREATED_AT` datetime(6) NOT NULL,
                             `CREATED_BY` bigint DEFAULT NULL,
                             `UPDATED_AT` datetime(6) DEFAULT NULL,
                             `UPDATED_BY` bigint DEFAULT NULL,
                             `DELETED_AT` datetime(6) DEFAULT NULL,
                             `DELETED_BY` bigint DEFAULT NULL,
                             `IS_DELETED` bit(1) NOT NULL,
                             PRIMARY KEY (`BRAND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ED_PRODUCT`;
CREATE TABLE `ED_PRODUCT` (
                              `PRODUCT_ID` bigint NOT NULL AUTO_INCREMENT,
                              `PRODUCT_PUBLIC_ID` varchar(255) NOT NULL,
                              `BRAND_ID` bigint NOT NULL,
                              `CATEGORY` enum('BOTTOM','TOP') NOT NULL,
                              `COLOR` varchar(10) NOT NULL,
                              `DESCRIPTION` varchar(255) NOT NULL,
                              `IMAGE` varchar(255) NOT NULL,
                              `NAME` varchar(50) NOT NULL,
                              `PRICE` int NOT NULL,
                              `STATUS` enum('ACTIVE','INACTIVE','SOLD_OUT') NOT NULL,
                              `CREATED_AT` datetime(6) NOT NULL,
                              `CREATED_BY` bigint DEFAULT NULL,
                              `UPDATED_AT` datetime(6) DEFAULT NULL,
                              `UPDATED_BY` bigint DEFAULT NULL,
                              `DELETED_AT` datetime(6) DEFAULT NULL,
                              `DELETED_BY` bigint DEFAULT NULL,
                              `IS_DELETED` bit(1) NOT NULL,
                              PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ED_STOCK_DECREASE_HISTORY`;
CREATE TABLE `ED_STOCK_DECREASE_HISTORY` (
                                             `ID` bigint NOT NULL AUTO_INCREMENT,
                                             `PRODUCT_PUBLIC_ID` varchar(255) NOT NULL,
                                             `TRANSACTION_ID` varchar(255) NOT NULL,
                                             `PRODUCT_CATEGORY` enum('BOTTOM','TOP') NOT NULL,
                                             `QUANTITY` int NOT NULL,
                                             `SIZE` varchar(10) NOT NULL,
                                             `STATUS` enum('COMMITTED','DECREASED','ROLLBACK','SCHEDULING_ROLLBACK') NOT NULL,
                                             `CREATED_AT` datetime(6) NOT NULL,
                                             `CREATED_BY` bigint DEFAULT NULL,
                                             `UPDATED_AT` datetime(6) DEFAULT NULL,
                                             `UPDATED_BY` bigint DEFAULT NULL,
                                             `DELETED_AT` datetime(6) DEFAULT NULL,
                                             `DELETED_BY` bigint DEFAULT NULL,
                                             `IS_DELETED` bit(1) NOT NULL,
                                             PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ED_TOP_SIZE_STOCK`;
CREATE TABLE `ED_TOP_SIZE_STOCK` (
                                     `TOP_SIZE_ID` bigint NOT NULL AUTO_INCREMENT,
                                     `PRODUCT_ID` bigint NOT NULL,
                                     `TOP_SIZE` varchar(10) NOT NULL,
                                     `CHEST_WIDTH` decimal(5,1) NOT NULL,
                                     `SHOULDER_WIDTH` decimal(5,1) NOT NULL,
                                     `SLEEVE_LENGTH` decimal(5,1) NOT NULL,
                                     `TOTAL_LENGTH` decimal(5,1) NOT NULL,
                                     `QUANTITY` int NOT NULL,
                                     `CREATED_AT` datetime(6) NOT NULL,
                                     `CREATED_BY` bigint DEFAULT NULL,
                                     `UPDATED_AT` datetime(6) DEFAULT NULL,
                                     `UPDATED_BY` bigint DEFAULT NULL,
                                     `DELETED_AT` datetime(6) DEFAULT NULL,
                                     `DELETED_BY` bigint DEFAULT NULL,
                                     `IS_DELETED` bit(1) NOT NULL,
                                     PRIMARY KEY (`TOP_SIZE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;








