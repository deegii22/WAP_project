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
insert into user(name, email, password, phone, sex, birth_date, created)
values (
  "Leanne Graham",
  "admin@gmail.com",
  "asdf",
  "0646654489",
  "Female",
  "05/01/1995",
  now()
);

insert into user(name, email, password, phone, sex, birth_date, created)
values (
  "Tom Otson",
  "user@gmail.com",
  "123",
  "0703565050",
  "Male",
  "01/01/2000",
  now()
);