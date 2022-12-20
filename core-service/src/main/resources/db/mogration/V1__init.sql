create table categories
(
    id              bigserial primary key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table products
(
    id          bigserial primary key,
    title       varchar(255) not null ,
    price       numeric(8, 2) not null ,
    category_id bigint references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into categories (title) values ('Food');

insert into products (title, price, category_id) values
   ('Milk', 80.00, 1),
   ('Milk2', 85.00, 1),
   ('Milk3', 75.00, 1),
   ('Milk4', 78.00, 1),
   ('Milk5', 81.00, 1),
   ('Milk6', 90.00, 1),
   ('Milk7', 93.00, 1),
   ('Milk8', 89.00, 1),
   ('Milk9', 76.00, 1),
   ('Bread', 25.00, 1),
   ('Cheese', 300.00, 1),
   ('Kiwi', 75.00, 1),
   ('Banana', 50.00, 1);

create table orders (
        id          bigserial primary key ,
        username    varchar(255) not null ,
        total_price numeric(8, 2) not null ,
        created_at  timestamp default current_timestamp,
        updated_at  timestamp default current_timestamp
);

create table order_items (
        id          bigserial primary key ,
        product_id  bigint not null references products(id),
        order_id    bigint not null references orders(id) ,
        quantity    int,
        price       numeric(8, 2) not null ,
        total_price numeric(8, 2) not null ,
        created_at  timestamp default current_timestamp,
        updated_at  timestamp default current_timestamp
);
