select * from cart;
select * from cart_item;

SELECT cart_item.item_id, books.title, books.cover, books.author, books.price_standard, books.price_sales, books.category_name, cart_item.quantity 
FROM cart INNER JOIN cart_item ON cart.id = cart_item.cart_id 
INNER JOIN books ON cart_item.item_id = books.item_id 
INNER JOIN `identity` ON cart.profile_id = `identity`.id 
WHERE (cart_item.cart_id = cart.id) AND (`identity`.userid = 'kiara');

delete from cart_item;
delete from cart;

select * from books order by books.id DESC;

select * from identity;
select * from profile;


update cart_item set item_id = '324228036'
where cart_id = 1;

SELECT cart.id 
FROM cart, identity
where cart.id = identity.id
and identity.userid = 'kiara';

SELECT 
 t1.id, t1.cart_id,
 t2.item_id, t2.title, t2.cover, t2.author, t2.price_standard, t2.price_sales,
 t1.qty
FROM cart_item t1
JOIN books t2 ON t1.item_id = t2.item_id
where t1.cart_id = 1;
        
SELECT cart_item.item_id, books.title, books.cover, books.author, books.price_standard, books.price_sales, cart_item.qty 
FROM cart 
INNER JOIN cart_item ON cart.id = cart_item.cart_id 
INNER JOIN books ON cart_item.item_id = books.item_id 
INNER JOIN `identity` ON cart.id = `identity`.id
 WHERE (cart_item.cart_id = cart.id) AND (`identity`.userid = 'kiara');        
 
 
SELECT cart_item.item_id, books.title, books.cover, books.author, books.price_standard, books.price_sales, books.category_name, cart_item.quantity
 FROM cart INNER JOIN cart_item ON cart.id = cart_item.cart_id INNER JOIN books ON cart_item.item_id = books.item_id 
INNER JOIN `identity` ON cart.profile_id = `identity`.id WHERE (cart_item.cart_id = cart.id) AND (`identity`.userid = 'kiara2') ;


SELECT cart_item.item_id, books.title, books.cover, books.author, books.price_standard, books.price_sales, books.category_name, cart_item.quantity 
FROM cart INNER JOIN cart_item ON cart.id = cart_item.cart_id LEFT JOIN books ON cart_item.item_id = books.item_id 
INNER JOIN `identity` ON cart.profile_id = `identity`.id 
WHERE (cart_item.cart_id = cart.id) AND (`identity`.userid = 'kiara2');

select * from books where item_id = 327983178;