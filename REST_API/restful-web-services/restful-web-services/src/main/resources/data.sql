INSERT INTO user_details(id,birth_date,name)
VALUES(1001,CURRENT_DATE,'Doaa Tawfik');
--
INSERT INTO User_Details(id,birth_date,name)
VALUES(1002,CURRENT_DATE-22,'Dalia Tawfik');

INSERT INTO Post(id,description,user_id)
VALUES(20001,'you can not take engineer out of engineering f', 1001);


INSERT INTO Post(id,description,user_id)
VALUES(20002,'study python', 1001);


INSERT INTO Post(id,description,user_id)
VALUES(20003,'dance', 1002);

--select * FROM user_details