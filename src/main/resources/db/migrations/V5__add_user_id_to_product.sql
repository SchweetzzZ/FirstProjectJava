ALTER TABLE tb_product ADD COLUMN user_id BIGINT;
ALTER TABLE tb_product ADD CONSTRAINT fk_product_user FOREIGN KEY (user_id) REFERENCES tb_users(id);
