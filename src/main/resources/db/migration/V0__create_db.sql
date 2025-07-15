CREATE DATABASE IF NOT EXISTS pharmacy;

CREATE TABLE app_role
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    name          VARCHAR(255)          NULL,
    flag_deleted  BIT(1)                NULL,
    CONSTRAINT pk_app_role PRIMARY KEY (id)
);

CREATE TABLE app_user
(
    id            BINARY(16)   NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime     NULL,
    modified_date datetime     NULL,
    modified_by   VARCHAR(255) NULL,
    username      VARCHAR(255) NULL,
    password      VARCHAR(255) NULL,
    flag_deleted  BIT(1)       NULL,
    flag_online   BIT(1)       NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    app_user_id   BINARY(16)            NULL,
    app_role_id   BIGINT                NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (id)
);

ALTER TABLE user_role
    ADD CONSTRAINT FK_USER_ROLE_ON_APP_ROLE FOREIGN KEY (app_role_id) REFERENCES app_role (id);

ALTER TABLE user_role
    ADD CONSTRAINT FK_USER_ROLE_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

CREATE TABLE customer
(
    id            BINARY(16)   NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime     NULL,
    modified_date datetime     NULL,
    modified_by   VARCHAR(255) NULL,
    code          VARCHAR(255) NULL,
    image         LONGTEXT     NULL,
    name          VARCHAR(255) NULL,
    address       VARCHAR(255) NULL,
    phone_number  VARCHAR(255) NULL,
    birth_date    date         NULL,
    email         VARCHAR(255) NULL,
    point         BIGINT       NULL,
    note          LONGTEXT     NULL,
    flag_deleted  BIT(1)       NULL,
    app_user_id   BINARY(16)   NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_app_user UNIQUE (app_user_id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

CREATE TABLE employee
(
    id            BINARY(16)   NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime     NULL,
    modified_date datetime     NULL,
    modified_by   VARCHAR(255) NULL,
    code          VARCHAR(255) NULL,
    image         LONGTEXT     NULL,
    name_employee VARCHAR(255) NULL,
    address       VARCHAR(255) NULL,
    phone_number  VARCHAR(255) NULL,
    birth_date    date         NULL,
    start_date    date         NULL,
    cart_id       VARCHAR(255) NULL,
    note          LONGTEXT     NULL,
    flag_deleted  BIT(1)       NULL,
    app_user_id   BINARY(16)   NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_app_user UNIQUE (app_user_id);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

CREATE TABLE `order`
(
    id            BINARY(16)   NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime     NULL,
    modified_date datetime     NULL,
    modified_by   VARCHAR(255) NULL,
    code          VARCHAR(255) NULL,
    date_time     datetime     NULL,
    note          LONGTEXT     NULL,
    flag_deleted  BIT(1)       NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE user_order
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    app_user_id   BINARY(16)            NULL,
    order_id      BINARY(16)            NULL,
    CONSTRAINT pk_user_order PRIMARY KEY (id)
);

ALTER TABLE user_order
    ADD CONSTRAINT FK_USER_ORDER_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

ALTER TABLE user_order
    ADD CONSTRAINT FK_USER_ORDER_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

CREATE TABLE supplier
(
    id            BINARY(16)   NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime     NULL,
    modified_date datetime     NULL,
    modified_by   VARCHAR(255) NULL,
    code          VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    address       VARCHAR(255) NULL,
    phone_number  VARCHAR(255) NULL,
    note          LONGTEXT     NULL,
    flag_deleted  BIT(1)       NULL,
    CONSTRAINT pk_supplier PRIMARY KEY (id)
);

CREATE TABLE invoice
(
    id              BINARY(16)   NOT NULL,
    created_by      VARCHAR(255) NULL,
    created_date    datetime     NULL,
    modified_date   datetime     NULL,
    modified_by     VARCHAR(255) NULL,
    code            VARCHAR(255) NULL,
    document_number VARCHAR(255) NULL,
    creation_date   datetime     NULL,
    paid            DOUBLE       NULL,
    note            LONGTEXT     NULL,
    flag_deleted    BIT(1)       NULL,
    app_user_id     BINARY(16)   NULL,
    supplier_id     BINARY(16)   NULL,
    CONSTRAINT pk_invoice PRIMARY KEY (id)
);

ALTER TABLE invoice
    ADD CONSTRAINT FK_INVOICE_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

ALTER TABLE invoice
    ADD CONSTRAINT FK_INVOICE_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

