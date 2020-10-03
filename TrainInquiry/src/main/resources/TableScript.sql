drop database if exists TrainsRouteDB;

create database TrainsRouteDB;

use TrainsRouteDB;

drop table if exists Route;

create table Route(
	id INT primary key AUTO_INCREMENT check(id between 100 and 999),
	source varchar(50),
	destination varchar(50)
);
Alter table Route AUTO_INCREMENT =100;

drop table if exists Train;

create table Train(
	id INT primary key,
	trainname varchar(50) not null,
	arrivaltime Time not null,
	departuretime Time not null,
	fare float(6,2) not null,
	route_id INT,
	foreign key(route_id) references Route(id)
);

