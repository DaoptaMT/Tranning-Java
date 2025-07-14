CREATE DATABASE IF NOT EXISTS pharmacy;

CREATE TABLE roles
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    nguoi_tao  VARCHAR(255)          NULL,
    ngay_tao   datetime              NULL,
    ngay_sua   datetime              NULL,
    nguoi_sua  VARCHAR(255)          NULL,
    name       VARCHAR(255)          NULL,
    is_deleted BIT(1)                NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         BINARY(16)   NOT NULL,
    nguoi_tao  VARCHAR(255) NULL,
    ngay_tao   datetime     NULL,
    ngay_sua   datetime     NULL,
    nguoi_sua  VARCHAR(255) NULL,
    full_name  VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    phone      VARCHAR(255) NULL,
    status     VARCHAR(255) NULL,
    is_deleted BIT(1)       NULL,
    role_id    BIGINT       NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_role UNIQUE (role_id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);