-- ALTER TABLE Orders AUTO_INCREMENT = 2023123456789;
select item_id, books.* from books;

select * from order_item;
select item_id, count(*) from order_item
group by item_id;

select item_id, order_id from order_item where order_id in (select id from orders)
and item_id = 583083;

select item_id from order_item 
where order_id in (select order_id from order_item where item_id = 583083)
and item_id != 583083;




-- 도서 재고 처리
select * from order_stock;
select id from order_stock where item_id = 1;
select id from books where item_id = 1;
-- UPDATE order_stock SET book_stock = book_stock + 2 WHERE item_id = 1;
-- INSERT INTO order_stock( book_stock, item_id, book_id) values (?, ?, (select id from books where item_id =?))

SELECT COUNT(*) FROM order_stock WHERE order_stock.item_id = 583083;

SELECT * FROM cart;
SELECT * FROM cart_item;

delete FROM cart_item;
delete FROM cart;

select * from cart join cart_item on cart.id = cart_item.cart_id where cart.profile_id = 1;

DELETE FROM cart_item WHERE (cart_item.cart_id = 26) AND (cart_item.item_id = 521683);

delete FROM cart_item;
delete FROM cart;

select count(*) from cart
join cart_item on cart.id = cart_item.cart_id
where cart.profile_id = 1
and cart_item = "";

SELECT COUNT(*) FROM cart 
INNER JOIN cart_item ON cart_item.cart_id = cart.id 
WHERE (cart.profile_id = 1) AND (cart_item.item_id = 583083);

SELECT cart.id FROM cart WHERE cart.profile_id = 1;

select * from identity;
select * from profile;



select item_id from books where item_id in (select item_id from cart_item);

update books set item_id = "907991" where id=2 and item_id = '907990';
update books set price_standard = "20000", price_sales="10000" where item_id = '521683';

update cart_item set item_id = "521683" where id = 6;
-- 2023123456794
select * from orders where order_date >= '20231001' and order_date >= '20231022';
select * from orders where id = '2023123456797';
select * from order_item where order_id = '2023123456797';
select * from order_address where order_id = '2023123456797';

SELECT * FROM cart where profile_id = 1;
SELECT * FROM cart_item where cart_id = (SELECT id FROM cart where profile_id = 1);

-- delete FROM cart where profile_id = 1;
-- delete FROM cart_item where cart_id = (SELECT id FROM cart where profile_id = 1);

ALTER TABLE Orders AUTO_INCREMENT = 2023123456789;


SELECT orders.id, orders.payment_method, orders.payment_price, orders.order_status, orders.order_date 
FROM orders 
WHERE (orders.profile_id = 1)
and DATE(orders.order_date) <= '2023-10-23';

AND (orders.order_date >= 2023-07-23) AND (orders.order_date <= 2023-10-23);
ORDER BY orders.id DESC LIMIT 5;

select * from new_books;