-- 1087, 327766544
select count(*) from books; 
select max(item_id) from books; 
select max(id) from books; 

select item_id, cover, books.* from books where id = 6182; 
select item_id, isbn, book.* from book where item_id = 317410509;

select * from books; 
select * from books order by books.id DESC; 

-- delete from order_sales;
-- delete from book_comments;
-- delete from books;

select item_id, title, cover, price_standard, price_sales from books where id = 6182; 
select item_id, title, cover, price_standard, price_sales from books where item_id=327766486;


select * from new_books;
select * from new_books
left join books on new_books.item_id = books.item_id;
-- https://image.aladin.co.kr/product/32192/97/cover200/k922834429_2.jpg
-- http://www.aladin.co.kr/shop/wprod 32192/97/50&amp;partner=openAPI&amp;start=api

-- https://image.aladin.co.kr/product/32192/97/cover200/K922834429.jpg
-- https://image.aladin.co.kr/product/32252/11/cover200/k612834746_1.jpg
-- http://www.aladin.co.kr/shop/wprod/32252/11/98&amp;partner=openAPI&amp;start=api				K612834746
-- http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=324233384&amp;partner=openAPI&amp;start=api				K322935098

-- https://image.aladin.co.kr/product/32423/33/cover200/K322935098_1.jpg

select item_id, title, link,isbn, 
concat("https://image.aladin.co.kr/product/",substr(item_id,1,5) ,"/", substr(item_id,6,2),"/cover200/",isbn,"_1.jpg") as cover from books;

-- SELECT cart_item.item_id, books.title, books.cover, books.author, books.price_standard, books.price_sales, books.category_name, cart_item.quantity 
SELECT cart_item.item_id, 
concat("https://image.aladin.co.kr/product/",substr(books.item_id,1,5) ,"/", substr(books.item_id,6,2),"/cover200/",isbn,"_1.jpg") as cover,
books.title, books.cover, books.author, books.price_standard, books.price_sales, books.category_name, cart_item.quantity 
FROM cart INNER JOIN cart_item ON cart.id = cart_item.cart_id 
INNER JOIN books ON cart_item.item_id = books.item_id 
INNER JOIN `identity` ON cart.profile_id = `identity`.id 
WHERE (cart_item.cart_id = cart.id) AND (`identity`.userid = 'kiara');

select item_id, title, cover, category_name, price_sales from books
where item_id = 125722399;


SELECT cart_item.id, cart_item.cart_id, cart_item.item_id, cart_item.qty, cart_item.created_date, cart_item.cart_status 
FROM cart_item, books WHERE cart_item.item_id = books.item_id ORDER BY cart_item.created_date DESC;
 
 -- concat('53','5','2','%')  
 -- 국내도서, 건강/취미, 골프
 select category_id, category_name, books.* 
 from books 
 where category_id like concat('53','5','2','%')  
 order by 2;
 
 -- concat('53','5','3','%')  
 -- 국내도서, 건강/취미, 건강운용
 select category_id, category_name, books.* 
 from books 
 where category_id like concat('53','5','3','%')  
 order by 2;
 
 -- concat('53','4','%')  
 -- 국내도서, 요리/살림, 골프
 select category_id, category_name, books.* 
 from books 
 where category_id like concat('53','4','%')  
 order by 2;
 
 
 select category_id, category_name, books.* 
 from books 
 where category_id like '53%';
 
  select * from books;