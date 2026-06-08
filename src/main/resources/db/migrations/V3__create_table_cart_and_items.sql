CREATE TABLE tb_cart (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES tb_users(id)
);


CREATE TABLE tb_cart_item (
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER NOT NULL,
    price NUMERIC(38,2) DEFAULT NULL,
    cart_id BIGINT,
    product_id BIGINT,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES tb_cart(id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES tb_product(id)
);