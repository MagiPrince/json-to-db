CREATE DATABASE orders;
USE orders;

CREATE TABLE `customer` (
  `id` varchar(16) NOT NULL UNIQUE,
  `pseudo` varchar(100) NOT NULL,
  `created` varchar(100) NOT NULL,
  `postalCode` smallint NOT NULL,
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8mb4;


CREATE TABLE `vendor` (
  `slug` varchar(150) NOT NULL UNIQUE,
  PRIMARY KEY (slug)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `category` (
  `name` varchar(100) NOT NULL UNIQUE,
  PRIMARY KEY (name)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order_table` (
  `oid` int(32) NOT NULL UNIQUE,
  `when` varchar(100) NOT NULL,
  `bags` varchar(40) NULL,
  `customer_id` varchar(16) NOT NULL,
  PRIMARY KEY (oid),
  FOREIGN KEY (customer_id) REFERENCES customer(id)
) DEFAULT CHARSET=utf8mb4;


CREATE TABLE `item` (
  `title` varchar(150) NOT NULL,
  `sku` int(32) NOT NULL UNIQUE,
  `image` varchar(100) NOT NULL,
  `estimatedprice` decimal(6,2) NOT NULL,
  `slug` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  FOREIGN KEY (slug) REFERENCES vendor(slug),
  FOREIGN KEY (category) REFERENCES category(name),
  PRIMARY KEY (sku)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order_item` (
  `sku` int(32) NOT NULL,
  `oid` int(32) NOT NULL,
  `qty` int(8) NOT NULL,
  `status` varchar(40) NOT NULL,
  `finalprice` decimal(6,2) NOT NULL,
  PRIMARY KEY (sku,oid),
  FOREIGN KEY (oid) REFERENCES order_table(oid),
  FOREIGN KEY (sku) REFERENCES item(sku)
) DEFAULT CHARSET=utf8mb4;
