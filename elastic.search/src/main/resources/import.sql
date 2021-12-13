insert into user (dtype, id, firstname, lastname, username, password, blocked, address) values ('Administrator', 1, 'Elena', 'Krunic' , 'Elekru', '123', false, 'Valenatina Vodnika 21a')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address) values ('Buyer', 2, 'Jelena', 'Stokic' , 'JecaPereca', 'jecajeca', false, 'Slovacka14')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Seller', 3, 'Lele', 'Krunic' , 'Lele', '222', false, 'Gavrila Principa 4', 'elenakrunic@gmail.com', '2021-11-23', 'ElenaByz')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Seller', 4, 'Mirka', 'Petrovec' , 'Mirka', '333', false, 'Slovacka 24', 'mirkeckermi@gmail.com', '2021-01-01', 'MirkecShopz')

insert into action (id, from_date, percentage, text, to_date, seller_id) values (1, '2022-02-02', 10,  'Akcijaakcija', '2022-02-05', 3)
insert into action (id, from_date, percentage, text, to_date, seller_id) values (2, '2022-03-02', 40,  'Veliko snizenje', '2022-03-09', 3)

insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (1, false, true, 'Komentar koji je arhiviran', 4, true, '2021-11-29', 2)
insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (2, false, true, 'Komentar koji je isto arhiviran', 6, true, '2021-11-25', 2)
insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (3, false, true, 'Komentar koji je isto arhiviran', 6, false, '2021-11-25', 2)
		 	
insert into product (id, description, name, path, price, seller_id) values (1, '3.0GHz Dual-core Haswell Intel Core i5 Turbo Boost up to 3.2 GHz, 3MB L3 cache 8GB (two 4GB SO-DIMMs) of 1600MHz DDR3 SDRAM', 'Macbook Retina 13.3 inch ME662 (2013)' , 'https://www.dropbox.com/s/wbwqjy22lqjc3jl/img8.jpg?raw=1', 999, 3)
insert into product (id, description, name, path, price, seller_id) values (2,  'Macbook Pro 13.3" Retina MF841LL/A Model 2015 Option Ram Care 12/2016' , 'Macbook Pro 13.3 inch Retina MF841LL/A', 'http://media.bizwebmedia.net//sites/72783/data/images/2015/11/3220113retina13.jpg',  1020 , 3)
insert into product (id, description, name, path, price, seller_id) values (3, '3.0GHz Dual-core Haswell Intel Core i5 Turbo Boost up to 3.2 GHz, 3MB L3 cache 8GB (two 4GB SO-DIMMs) of 1600MHz DDR3 SDRAM', 'Macbook Pro 15.4 inch Retina MC975LL/A Model 2012 ', 'http://media.bizwebmedia.net//sites/72783/data/images/2015/7/2913337mf841_13_inch_2_9ghz_with_retina_display_early_2015.png', 1000, 4)
insert into product (id, description, name, path, price, seller_id) values (4, '2.9 Ghz Dual-Core Intel Core i5 Broadwell Tubro boost up to 3.3 GHz with L3 3MB cache', 'Retina MacBook Pro 13 inch MF841', 'https://www.dropbox.com/s/rjj1vtdx79xptu0/img6.jpeg?raw=1', 1000, 4)
insert into product (id, description, name, path, price, seller_id) values (5, '2.9 Ghz Dual-Core Intel Core i5 Broadwell Tubro boost up to 3.3 GHz with L3 3MB cache', 'Retina', 'https://www.dropbox.com/s/rjj1vtdx79xptu0/img6.jpeg?raw=1', 1000, 4)
insert into product (id, description, name, path, price, seller_id) values (6, '2.9 Ghz Dual-Core Intel Core i5 Broadwell Tubro boost up to 3.3 GHz with L3 3MB cache', 'Retina 1', 'https://www.dropbox.com/s/rjj1vtdx79xptu0/img6.jpeg?raw=1', 1200.22, 4)
insert into product (id, description, name, path, price, seller_id) values (7, '2.9 Ghz Dual-Core Intel Core i5 Broadwell Tubro boost up to 3.3 GHz with L3 3MB cache', 'Retina 2', 'https://www.dropbox.com/s/rjj1vtdx79xptu0/img6.jpeg?raw=1', 1322.2, 4)

insert into item (id, quantity, errand_id, product_id) values (1, 1, 1, 1)
insert into item (id, quantity, errand_id, product_id) values (2, 2, 2, 2)