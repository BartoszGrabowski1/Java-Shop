ALTER TABLE IF EXISTS ONLY public.product
    DROP CONSTRAINT IF EXISTS fk_category_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.product
    DROP CONSTRAINT IF EXISTS fk_supplier_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.cart
    DROP CONSTRAINT IF EXISTS fk_product_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.cart
    DROP CONSTRAINT IF EXISTS fk_user_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.order
    DROP CONSTRAINT IF EXISTS fk_user_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.ordered_products
    DROP CONSTRAINT IF EXISTS fk_product_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.ordered_products
    DROP CONSTRAINT IF EXISTS fk_order_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.user_billing
    DROP CONSTRAINT IF EXISTS fk_user_id CASCADE;
DROP TABLE IF EXISTS public.user;
DROP TABLE IF EXISTS public.user_billing;
CREATE TABLE public.user_billing(
        id serial NOT NULL PRIMARY KEY,
        phone integer NOT NULL,
        city text NOT NULL,
        street text NOT NULL,
        zip_code integer NOT NULL,
        user_id integer NOT NULL
);
DROP TABLE IF EXISTS public.shop_user;
CREATE TABLE public.shop_user (
                                   id serial NOT NULL PRIMARY KEY,
                                   name text NOT NULL,
                                   mail text NOT NULL,
                                   password text NOT NULL

);

DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product (
                                id serial NOT NULL PRIMARY KEY,
                                name text NOT NULL,
                                price decimal Not NULL,
                                currency text NOT NULL,
                                description text NOT NULL,
                                category_id integer NOT NULL,
                                supplier_id integer NOT NULL

);

DROP TABLE IF EXISTS public.supplier;
CREATE TABLE public.supplier(
                             id serial NOT NULL PRIMARY KEY,
                             name text NOT NULL,
                             description text NOT NULL
);
DROP TABLE IF EXISTS public.category;
CREATE TABLE public.category(
                                id serial NOT NULL PRIMARY KEY,
                                name text NOT NULL,
                                department text NOT NULL,
                                description text NOT NULL
);
DROP TABLE IF EXISTS public.cart;
CREATE TABLE public.cart(
                                id serial NOT NULL PRIMARY KEY,
                                product_id integer NOT NULL,
                                user_id integer NOT NULL
);
DROP TABLE IF EXISTS public.order;
CREATE TABLE public.order(
                            id serial NOT NULL PRIMARY KEY,
                            ordered_at timestamp without time zone,
                            status text NOT NULL,
                            user_id integer NOT NULL
);
DROP TABLE IF EXISTS public.ordered_products;
CREATE TABLE public.ordered_products(
                            id serial NOT NULL PRIMARY KEY,
                            product_id integer NOT NULL,
                            order_id integer NOT NULL
);
ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES public.category(id);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES public.supplier(id);

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.ordered_products
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.ordered_products
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES public.order(id);
ALTER TABLE ONLY public.user_billing
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.shop_user(id);

INSERT INTO supplier (name, description) VALUES ('Apple', 'Smartphone');
INSERT INTO supplier (name, description) VALUES ('Amazon','Digital content and services');
INSERT INTO supplier (name, description) VALUES ('Lenovo', 'Computers');
INSERT INTO supplier (name, description) VALUES ('Asus', 'Laptops');

INSERT INTO category (name, department, description) VALUES ('Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO category (name, department, description) VALUES ('Phone', 'Mobile-Phone', 'Phone is good');
INSERT INTO category (name, department, description) VALUES ('Laptop', 'SmallComputer', 'Good laptops');

INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Amazon Fire', 49.9, 'USD', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 1, 2);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Lenovo IdeaPad Miix 700', 479, 'USD', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 1, 3);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Amazon Fire HD 8', 89, 'USD', 'Amazon''s latest Fire HD 8 tablet is a great value for media consumption.Beneficial price', 1, 2);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Iphone 20 pro', 999, 'USD', 'Iphone 20 pro is goood really goood', 2, 1);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Iphone 20 MAX', 899, 'USD', 'Iphone 20 pro is goood really goood too', 2, 1);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Ultra Phone', 499, 'USD', 'This phone is good very good', 2, 1);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Laptop lenovo 9000',1300, 'USD', 'Very good laptop', 3, 3);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Laptop lenovo 3500', 1000, 'USD', 'Good laptop too', 3, 3);
INSERT INTO product (name, price, currency, description, category_id, supplier_id) VALUES ('Laptop asus 500', 800, 'USD', 'Good asus laptop too', 3, 4);

