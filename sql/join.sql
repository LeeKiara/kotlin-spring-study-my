SELECT cart_item.item_id, books.title, books.cover, books.author, books.price_standard, books.price_sales, 
books.category_name, cart_item.quantity 
FROM cart 
INNER JOIN cart_item ON cart.id = cart_item.cart_id 
INNER JOIN books ON cart_item.item_id = books.item_id 
INNER JOIN `identity` ON cart.profile_id = `identity`.id 
WHERE (cart_item.cart_id = cart.id) AND (`identity`.userid = 'kiara');

select * from cart, cart_item
where cart.id = cart_item.id;

select * from cart_item, books
where cart_item.item_id = books.item_id;

select * from cart_item;



select * from identity where userid = 'kiara';
select * from cart where profile_id = 1;
select * from cart_item;
select * from books where item_id=907990;
select * from books where title like '%이보영의%';

select * from books where id = 1;
update books set item_id=907990, 
title = "이보영의 영어 만화 Aladdin's Magic Lamp (책 + 워크북 + CD 1장) - 알라딘의 요술 램프"
where id = 1;

-- update cart_item set quantity = 1;


