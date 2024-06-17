# title: insert test data
# desc: password - test123
INSERT INTO ADMINS (EMAIL, PASSWD) VALUES ('test@test.com', '$2y$10$HIAHCALHoK.ocMRW6EK7IOq6EOA0WxJmMDfNrffNrxZUEx8YEq/4u');

# title:
INSERT INTO ITEMS (ITEM_DESCRIPTION, ITEM_NAME, ITEM_PRICE, ITEM_STOCK_QUANTITY) VALUES ('아이템', 'item1', 100.00, 200);

# title: generate item mock data
DELIMITER $$
CREATE PROCEDURE generateItemData(IN numRows INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= numRows DO
        INSERT INTO items (item_name, item_description, item_price, item_stock_quantity, create_at, updated_at)
        VALUES (
            CONCAT('Item ', i),
            CONCAT('Description for Item ', i),
            RAND() * 1000, -- 0부터 1000 사이의 임의 가격
            FLOOR(RAND() * 1000), -- 0부터 999 사이의 임의 재고량
            NOW(),
            NOW()
        );
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

CALL generateItemData(1000000);


# title: insert member test data
INSERT INTO MEMBERS (EMAIL, NAME) VALUES ('member@test.com', 'tester');

# title: generate member mock data
DELIMITER $$
CREATE PROCEDURE generateMemberData(IN numRows INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT DEFAULT 1;
    DECLARE email VARCHAR(255);
    DECLARE name VARCHAR(255);

    WHILE i <= numRows DO
            SET email = CONCAT('user', LPAD(i, 5, '0'), '@example.com');
            SET name = CONCAT('User ', LPAD(j, 2, '0'));

            INSERT INTO members (email, name, create_at, updated_at)
            VALUES (email, name, SYSDATE(), SYSDATE());

            SET i = i + 1;
            SET j = j + 1;

            IF j > 10 THEN
                SET j = 1;
            END IF;
        END WHILE;
END$$
DELIMITER ;

CALL generateMemberData(10);

# title: generate order mock data
DELIMITER $$
CREATE PROCEDURE generateOrderData(IN numRows INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE randomItemId BIGINT;
    DECLARE randomMemberId BIGINT;
    DECLARE randomOrderTotalPrice DECIMAL(38, 2);
    DECLARE randomItemQuantity INT;
    DECLARE randomItemPrice DECIMAL(38, 2);
    DECLARE itemInsertCount INT DEFAULT 0;

    WHILE i < numRows DO
            SET randomItemId = FLOOR(RAND() * 1000000) + 1;
            SET randomMemberId = FLOOR(RAND() * 10) + 1;
            SET randomOrderTotalPrice = FLOOR(RAND() * 100000);
            SET itemInsertCount = FLOOR(RAND() * 5) + 1;

            INSERT INTO orders (order_total_price, member_id, create_at, updated_at)
            VALUES (randomOrderTotalPrice, randomMemberId, SYSDATE(), SYSDATE());
            SET @order_id = LAST_INSERT_ID();

            WHILE itemInsertCount > 0 DO
                    SET itemInsertCount = itemInsertCount - 1;
                    INSERT INTO order_items (item_price, item_quantity, item_id, order_id, create_at, updated_at)
                    VALUES (randomOrderTotalPrice, itemInsertCount, randomItemId, @order_id, SYSDATE(), SYSDATE());
                END WHILE;

            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

CALL generateOrderData(1000000);