ALTER TABLE medicine
DROP
FOREIGN KEY FK_MEDICINE_ON_KIND_OF_MEDICINE;

ALTER TABLE kind_of_medicine
    MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE medicine
    ADD CONSTRAINT FK_MEDICINE_ON_KIND_OF_MEDICINE
        FOREIGN KEY (kind_of_medicine_id)
            REFERENCES kind_of_medicine (id);

-- Drop the foreign key constraint from unit_detail table first
ALTER TABLE unit_detail
DROP FOREIGN KEY FK_UNIT_DETAIL_ON_UNIT;

-- Now it's safe to modify the id column in the unit table
ALTER TABLE unit
    MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

-- Re-add the foreign key constraint
ALTER TABLE unit_detail
    ADD CONSTRAINT FK_UNIT_DETAIL_ON_UNIT
        FOREIGN KEY (unit_id)
            REFERENCES unit (id);

INSERT INTO kind_of_medicine (code, name, flag_deleted)
VALUES ('763452', 'Thuốc kháng sinh', false),
       ('124578', 'Thuốc giảm đau', false),
       ('985632', 'Thuốc hạ sốt', false),
       ('456123', 'Thuốc kháng viêm', false),
       ('789456', 'Thuốc dị ứng', false),
       ('321654', 'Thuốc tim mạch', false),
       ('654987', 'Thuốc tiêu hóa', false),
       ('147258', 'Thuốc thần kinh', false),
       ('258369', 'Thuốc xương khớp', false),
       ('369147', 'Thuốc da liễu', false),
       ('852963', 'Thuốc mắt', false),
       ('963852', 'Thuốc tai mũi họng', false),
       ('741852', 'Thuốc hô hấp', false),
       ('852741', 'Thuốc nội tiết', false),
       ('963741', 'Thuốc tiểu đường', false),
       ('159357', 'Thuốc huyết áp', false),
       ('357159', 'Thuốc gan', false),
       ('456789', 'Thuốc thận', false),
       ('987654', 'Thuốc bổ', false),
       ('654321', 'Vitamin tổng hợp', false),
       ('123987', 'Khoáng chất', false),
       ('789123', 'Thực phẩm chức năng', false),
       ('321789', 'Thuốc tẩy giun', false),
       ('987321', 'Thuốc chống nấm', false),
       ('147963', 'Thuốc kháng virus', false),
       ('258147', 'Thuốc chống trầm cảm', false),
       ('369258', 'Thuốc an thần', false),
       ('741369', 'Thuốc chống co giật', false),
       ('852147', 'Thuốc giãn cơ', false),
       ('963258', 'Thuốc chống đông máu', false),
       ('159753', 'Thuốc cầm máu', false),
       ('357951', 'Thuốc lợi tiểu', false),
       ('456123', 'Thuốc chống táo bón', false),
       ('789456', 'Thuốc trị tiêu chảy', false),
       ('321654', 'Thuốc chống nôn', false),
       ('654321', 'Thuốc kháng acid', false),
       ('987654', 'Thuốc trị loét dạ dày', false),
       ('123456', 'Thuốc giảm mỡ máu', false),
       ('654789', 'Thuốc bổ não', false),
       ('321987', 'Thuốc tăng cường miễn dịch', false),
       ('789321', 'Thuốc giảm cân', false),
       ('987123', 'Thuốc tăng cân', false),
       ('147852', 'Thuốc trị mất ngủ', false),
       ('258963', 'Thuốc trị gút', false),
       ('369852', 'Thuốc trị hen suyễn', false),
       ('741963', 'Thuốc trị viêm khớp', false),
       ('852369', 'Thuốc trị đau nửa đầu', false),
       ('963147', 'Thuốc trị zona', false),
       ('159456', 'Thuốc trị vẩy nến', false),
       ('357789', 'Thuốc trị mụn', false);