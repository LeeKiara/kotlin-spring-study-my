select * from orders;
select * from order_item;
select * from order_address;
select * from order_sales;

-- delete from order_address;
-- delete from order_item;
-- delete from orders;


select id from orders where order_status = "0";

select * from orders where id in (2023123456807, 2023123456804);
-- UPDATE orders SET order_status = "0" where id in (2023123456803, 2023123456804);

-- 주문 후 books 정보가 없어진 내용 확인
select * from order_item t1
left join books t2 on t1.item_id = t2.item_id
where t2.item_id is null;

-- 주문 판매량 정보
select * from order_sales;
select * from order_sales
left join books on books.id = order_sales.book_id;

select * from orders where id = 2023123456789;
-- 60일 이전으로 업데이트
UPDATE orders
SET order_date = DATE_SUB(NOW(), INTERVAL 120 DAY)
WHERE id = 2023123456789;

UPDATE orders
SET order_status = "1";

select order_item.item_id, quantity, order_price , books.title
from order_item join books on order_item.item_id = books.item_id;

select sum(quantity), books.item_id, books.title, books.cover 
from order_item, books
where order_item.item_id = books.item_id
group by item_id,  books.title, books.cover 
order by 1 desc;

SELECT orders.id, orders.payment_method, orders.payment_price, orders.order_status, orders.order_date, 
order_address.delivery_name, order_address.delivery_phone, order_address.postcode, order_address.address, 
order_address.detail_address, order_address.delivery_memo 
FROM orders 
INNER JOIN order_address ON orders.id = order_address.order_id 
WHERE (orders.profile_id = 1) AND (orders.id = 2023123456789);

select order_item.item_id, order_item.order_price, order_item.quantity, 
books.title, books.cover
from orders, order_item, books
WHERE (orders.profile_id = 1) AND (orders.id = 2023123456789)
and orders.id = order_item.order_id -- foregin key 관계 
and books.item_id = order_item.item_id;

SELECT orders.id, orders.payment_method, orders.payment_price, order_item.order_price,
orders.order_status, orders.order_date, books.item_id, books.title, books.cover 
FROM orders
 INNER JOIN order_item ON orders.id = order_item.order_id 
 INNER JOIN books ON order_item.item_id = books.item_id 
WHERE (orders.id = order_item.order_id) 
AND (orders.profile_id = 1) 
order by id desc;


select * from books where item_id in (select item_id from order_item where order_id = 2023123456789);
select * from order_item where item_id in (select item_id from books);

SELECT orders.id, orders.payment_method, orders.payment_price, orders.order_status, orders.order_date,
books.item_id, books.title, books.cover
FROM orders, order_item, books
WHERE (orders.profile_id = 1)
AND (DATE(orders.order_date) >= '2023-07-23') AND (DATE(orders.order_date) <= '2023-10-23')
and orders.id = order_item.order_id
and order_item.item_id = books.item_id
-- and order_item.id = 1 
ORDER BY orders.id DESC; 


SELECT books.item_id, books.title, books.cover
FROM books
where books.item_id in (select item_id from order_item where order_id = 2023123456789);

-- ALTER TABLE Orders AUTO_INCREMENT = 2023123456789;

select order_item.item_id, books.title, books.cover 
from order_item 
left join books on order_item.item_id = books.item_id 
where order_item.item_id in (select item_id from order_item
group by item_id);

select item_id from order_item group by item_id;

select order_item.item_id, books.title, books.cover 
from order_item 
left join books on order_item.item_id = books.item_id 
where order_item.item_id = 325766444;

SELECT orders.id, orders.payment_method, orders.payment_price, orders.order_status, orders.order_date FROM orders 
WHERE (orders.profile_id = 2) 
AND (DATE(orders.order_date) >= '2023-07-31') AND (DATE(orders.order_date) <= '2023-10-31') 
ORDER BY orders.id DESC LIMIT 5;