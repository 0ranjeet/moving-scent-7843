create database busticket;
use busticket;
create table login_data(username varchar(20),password varchar(20));
--Sample data login table

Insert into login_data(username,password) value
('Admin','12admin'),
('2admin','123admin'),
('23admin','admin23’);


Create table bus(
busname varchar(20),
Location varchar(20),
tow  varchar(20),
Bus_type varchar(12),
Seat int,
booked int,
Departure_time varchar(10),
Arrival_time varchar(10)
);

Create table customer(
cname varchar(20),
Bname varchar(20),
Location varchar(20),
Tol varchar(20),
Bus_type varchar(10),
Seats int,
Mob varchar(10),
Dtime varchar(10),
Atime varchar(10),
Confirmed boolean
);


