insert into user (dtype, id, firstname, lastname, username, password, blocked, address) values ('Buyer', 1, 'Elena', 'Krunic' , 'Elekru', '123', false, 'Valenatina Vodnika 21a')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address) values ('Buyer', 2, 'Jelena', 'Stokic' , 'JecaPereca', 'jecajeca', false, 'Slovacka14')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Seller', 3, 'Lele', 'Krunic' , 'Lele', '222', false, 'Gavrila Principa 4', 'elenakrunic@gmail.com', '2021-11-23', 'ElenaByz')

insert into action (id, from_date, percentage, text, to_date, seller_id) values (1, '2022-02-02', 10,  'Akcijaakcija', '2022-02-05', 3)
insert into action (id, from_date, percentage, text, to_date, seller_id) values (2, '2022-03-02', 40,  'Veliko snizenje', '2022-03-09', 3)

insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (1, false, true, 'Komentar koji je arhiviran', 4, true, '2021-11-29', 1)
insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (2, false, true, 'Komentar koji je isto arhiviran', 6, true, '2021-11-25', 2)

insert into product (id, description, name, path, price, seller_id) values (1, 'Black based colorway of the retro basketball shoe worn by former Boston Celtics guard Dee Brown during the 91 NBA Slam Dunk Contest', 'Pump Omni Zone || Dee Brown ', 'C:/Users/lenovo/Desktop/UES_Project/DeliveryApp/elastic.search/src/main/resources/templates/ThePump.jpg',  123, 1)
insert into product (id, description, name, path, price, seller_id) values (2, 'This shoe is collaboration between the food-related YouTube series and Reebok on a scorchin hot colorway of Allen Iversons first signature shoe.', 'Question Mid Hot Ones ', 'C:/Users/lenovo/Desktop/UES_Project/DeliveryApp/elastic.search/src/main/resources/templates/AlenIversonShoe.jpg',  105, 2)

insert into item (id, quantity, errand_id, product_id) values (1, 1, 1, 1)
insert into item (id, quantity, errand_id, product_id) values (2, 2, 2, 2)