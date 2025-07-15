CREATE TABLE patient (
  id BIGINT NOT NULL,
   name VARCHAR(255) NULL,
   flag_deleted BIT(1) NOT NULL,
   CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE kind_of_medicine (
  id BIGINT NOT NULL,
   code VARCHAR(255) NULL,
   name VARCHAR(255) NULL,
   flag_deleted BIT(1) NOT NULL,
   CONSTRAINT pk_kind_of_medicine PRIMARY KEY (id)
);

CREATE TABLE medicine (
  id BIGINT AUTO_INCREMENT NOT NULL,
   code VARCHAR(255) NULL,
   name VARCHAR(255) NULL,
   price DOUBLE NULL,
   quantity BIGINT NULL,
   vat FLOAT NULL,
   note VARCHAR(255) NULL,
   maker VARCHAR(255) NULL,
   origin VARCHAR(255) NULL,
   retail_profit FLOAT NULL,
   kind_of_medicine_id BIGINT NULL,
   flag_deleted BIT(1) NOT NULL,
   active_element VARCHAR(255) NULL,
   CONSTRAINT pk_medicine PRIMARY KEY (id)
);

ALTER TABLE medicine ADD CONSTRAINT FK_MEDICINE_ON_KIND_OF_MEDICINE FOREIGN KEY (kind_of_medicine_id) REFERENCES kind_of_medicine (id);


CREATE TABLE prescription (
  id BIGINT AUTO_INCREMENT NOT NULL,
   code VARCHAR(255) NULL,
   name VARCHAR(255) NULL,
   symptoms VARCHAR(255) NULL,
   note VARCHAR(255) NULL,
   duration INT NULL,
   flag_deleted BIT(1) NOT NULL,
   patient_id BIGINT NULL,
   CONSTRAINT pk_prescription PRIMARY KEY (id)
);

ALTER TABLE prescription ADD CONSTRAINT FK_PRESCRIPTION_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id);


CREATE TABLE indication (
  id BIGINT AUTO_INCREMENT NOT NULL,
   dosage INT NULL,
   frequency INT NULL,
   flag_deleted BIT(1) NOT NULL,
   medicine_id BIGINT NULL,
   perscription_id BIGINT NULL,
   CONSTRAINT pk_indication PRIMARY KEY (id)
);

ALTER TABLE indication ADD CONSTRAINT FK_INDICATION_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicine (id);

ALTER TABLE indication ADD CONSTRAINT FK_INDICATION_ON_PERSCRIPTION FOREIGN KEY (perscription_id) REFERENCES prescription (id);


CREATE TABLE image_medicine (
  id BIGINT AUTO_INCREMENT NOT NULL,
   image_path VARCHAR(255) NULL,
   flag_deleted BIT(1) NOT NULL,
   medicine_id BIGINT NULL,
   CONSTRAINT pk_image_medicine PRIMARY KEY (id)
);

ALTER TABLE image_medicine ADD CONSTRAINT FK_IMAGE_MEDICINE_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicine (id);



CREATE TABLE unit (
  id BIGINT NOT NULL,
   name VARCHAR(255) NULL,
   flag_deleted BIT(1) NOT NULL,
   CONSTRAINT pk_unit PRIMARY KEY (id)
);


CREATE TABLE unit_detail (
  id BIGINT AUTO_INCREMENT NOT NULL,
   flag_deleted BIT(1) NOT NULL,
   conversion_unit BIGINT NULL,
   medicine_id BIGINT NULL,
   unit_id BIGINT NULL,
   CONSTRAINT pk_unit_detail PRIMARY KEY (id)
);

ALTER TABLE unit_detail ADD CONSTRAINT FK_UNIT_DETAIL_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicine (id);

ALTER TABLE unit_detail ADD CONSTRAINT FK_UNIT_DETAIL_ON_UNIT FOREIGN KEY (unit_id) REFERENCES unit (id);



CREATE TABLE cart_details (
  id BIGINT AUTO_INCREMENT NOT NULL,
   current_price DOUBLE NULL,
   app_user_id BINARY(16) NULL,
   medicine_id BIGINT NULL,
   quantity BIGINT NULL,
   CONSTRAINT pk_cart_details PRIMARY KEY (id)
);

ALTER TABLE cart_details ADD CONSTRAINT FK_CART_DETAILS_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

ALTER TABLE cart_details ADD CONSTRAINT FK_CART_DETAILS_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicine (id);


CREATE TABLE invoice_details (
  id BIGINT AUTO_INCREMENT NOT NULL,
   discount FLOAT NULL,
   medicine_quantity INT NULL,
   lot VARCHAR(255) NULL,
   flag_deleted BIT(1) NOT NULL,
   invoice_id BINARY(16) NULL,
   medicine_id BIGINT NULL,
   CONSTRAINT pk_invoice_details PRIMARY KEY (id)
);

ALTER TABLE invoice_details ADD CONSTRAINT FK_INVOICE_DETAILS_ON_INVOICE FOREIGN KEY (invoice_id) REFERENCES invoice (id);

ALTER TABLE invoice_details ADD CONSTRAINT FK_INVOICE_DETAILS_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicine (id);


CREATE TABLE order_details (
  id BIGINT AUTO_INCREMENT NOT NULL,
   current_price DOUBLE NULL,
   order_id BINARY(16) NULL,
   medicine_id BIGINT NULL,
   quantity BIGINT NULL,
   CONSTRAINT pk_order_details PRIMARY KEY (id)
);

ALTER TABLE order_details ADD CONSTRAINT FK_ORDER_DETAILS_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicine (id);

ALTER TABLE order_details ADD CONSTRAINT FK_ORDER_DETAILS_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);
