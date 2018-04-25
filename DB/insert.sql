-- Event default
insert into Event (name, status, start_date, end_date, route_img, owner_id, emergency_flag, emergency_info, created)
values ('Fairfield', 0, STR_TO_DATE('26-04-2018', '%d-%m-%Y'), STR_TO_DATE('27-04-2018', '%d-%m-%Y'), '', 1, 0, '', sysdate());

insert into Event (name, status, start_date, end_date, route_img, owner_id, emergency_flag, emergency_info, created)
values ('Ottumwa', 0, STR_TO_DATE('25-04-2018', '%d-%m-%Y'), STR_TO_DATE('26-04-2018', '%d-%m-%Y'), '', 1, 0, '', sysdate());

insert into Event (name, status, start_date, end_date, route_img, owner_id, emergency_flag, emergency_info, created)
values ('Mt Pleasant', 0, STR_TO_DATE('26-04-2018', '%d-%m-%Y'), STR_TO_DATE('27-04-2018', '%d-%m-%Y'), '', 1, 0, '', sysdate());

insert into Event (name, status, start_date, end_date, route_img, owner_id, emergency_flag, emergency_info, created)
values ('Iowa', 0, STR_TO_DATE('25-04-2018', '%d-%m-%Y'), STR_TO_DATE('26-04-2018', '%d-%m-%Y'), '', 1, 0, '', sysdate());

-- User default
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (1,'Maria','maria@yahoo.com','94aec9fbed989ece189a7e172c9cf41669050495152bc4c1dbf2a38d7fd85627','4056035565','Female','1990-11-20','2018-04-24 22:37:43');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (2,'Bob','bob@mum.edu','81b637d8fcd2c6da6359e6963113a1170de795e4b725b84d1e0b4cfd9ec58ce9','8025602020','Male','1997-06-24','2018-04-24 22:35:36');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (3,'Linda','linda@mum.edu','6bab3007f56e2a9175ff1222c2654ddcd08fa7981a1ddc42f1d95cfbd80ede47','7084565050','Female','1988-05-05','2018-04-24 22:34:18');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (4,'Ellen','ellen@gmail.com','7975b4132aaa77d75069a5d2ab3c501413eb91d11d046815158103d5628d7405','6046658888','Female','2005-03-01','2018-04-24 22:30:46');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (5,'Joe','joe@mum.edu','78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3','0012552558','Male','2000-01-01','2018-04-24 22:28:57');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (6,'Bayar','bayar@mum.edu','f82ef9ec3f842b694b0a59c80d3cc767954040df22e7f47c8e264cfb25fa1a13','5462020565','Male','1991-01-31','2018-04-24 22:40:17');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (7,'Deegii','deegii@mum.edu','95f4dbc6c4b1c30936a8abc3742e967c21b2d16473829d9f61f80604b7849013','7091225656','Female','1992-03-15','2018-04-24 22:41:14');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (8,'Enkhee','enkhee@mum.edu','44195a7ffb11a5c8b5e800d5487dda7583b80e3b6a5424883042021f1b1d6f16','5073648600','Male','2000-12-30','2018-04-24 22:42:28');
INSERT INTO `User` (`user_id`,`name`,`email`,`password`,`phone`,`sex`,`birth_date`,`created`) VALUES (9,'Saikhan','saikhan@mum.edu','da8399778bd70e79405ebbbe8981938f896c88acfb6ef12d22f7b1b36c149508','9760366588','Male','1998-08-06','2018-04-24 22:44:56');
