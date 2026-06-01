CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tb_category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE tb_product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE tb_product_category (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES tb_product(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES tb_category(id) ON DELETE CASCADE
);

CREATE TABLE tb_products_images (
    id BIGSERIAL PRIMARY KEY,
    image_key VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    position INTEGER,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES tb_product(id) ON DELETE CASCADE
);

CREATE TABLE tb_order (
    id BIGSERIAL PRIMARY KEY,
    moment TIMESTAMP WITH TIME ZONE NOT NULL,
    order_status INTEGER NOT NULL,
    client_id BIGINT,
    FOREIGN KEY (client_id) REFERENCES tb_users(id) ON DELETE SET NULL
);

CREATE TABLE tb_order_item (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES tb_order(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES tb_product(id)
);

CREATE TABLE tb_payment (
    order_id BIGINT PRIMARY KEY,
    moment TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES tb_order(id) ON DELETE CASCADE
);
