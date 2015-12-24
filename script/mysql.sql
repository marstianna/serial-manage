create table admin(
  id int PRIMARY KEY auto_increment,
  login_name VARCHAR not null default '',
  login_password varchar not null default '',
)

create table owner(
  id int PRIMARY KEY auto_increment,
  name varchar(45) not null default '',
  phone varchar not null default '',
  mobilephone varchar not null default '',
  login_name varchar not null default '' UNIQUE KEY,
  login_password varchar not null default '',
  `identity` varchar(19) not null default '',
  email varchar not null default '',
  create_time int(11) not null default 0
)

create table staff(
  id int PRIMARY KEY auto_increment,
  name varchar(45) not null default '',
  mobilephone varchar not null default '',
  `identity` varchar(19) not null default '',
  email varchar not null default '',
  staff_level tinyint not null default 1,
  create_time int(11) not null default 0
)

create table vip_info(
  id int PRIMARY KEY auto_increment,
  name varchar(45) not null default '',
  store_id int not null default 0,
  mobilephone varchar not null default '',
  wechat varchar not null default '',
  email varchar not null default '',
  create_time int(11) not null default 0
)

create table points(
  vip_id int not null,
  `type` tinyint not null default 0,
  balance_points int not null default 0,
  PRIMARY KEY (vip_id,`type`)
)

create table points_history(
  id int PRIMARY KEY auto_increment,
  vip_id int not null default 0,
  operate tinyint not null default 0,
  points_account int not null default 0,
  store_id int not null default 0,
  create_time int(11) not null default 0,
  `type` tinyint not null default 0
)

create table points_gift(
  id int primary key auto_increment,
  cost int not null default 0,
  stock int not null default 0,
  `name` varchar not null default '',
  description varchar(255) not null default '',
)
create table store(
  id int PRIMARY key auto_increment,
  store_name varchar(45) not null default '',
  address varchar(255) not null default '',
  phone varchar not null default '',
  owner_id int not null default 0,
  create_time int(11) not null default 0
)

create table value_card(
  id int PRIMARY KEY auto_increment,
  card_id varchar not null default '',
  card_uuid varchar(63) not null default '',
  balance int not null default 0,
  vip_id int not null default 0,
  create_time int(11) not null default 0,
  UNIQUE KEY (card_id,card_uuid)
)

create table value_card_history(
  id int PRIMARY KEY auto_increment,
  card_id varchar not null default '',
  operate tinyint not null default 0,
  `type` tinyint not null default 0,
  store_id int not null default 0,
  account int not null default 0,
  price int not null default 0,
  create_time int(11) not null default 0
)

create table `order`(
  id int PRIMARY KEY auto_increment,
  vip_id int not null default 0,
  store_id int not null default 0,
  pay_type tinyint not null default 0,
  card_id int not null default 0,
  paper_price int not null default 0,
  actual_price int not null default 0,
  drink_price int not null default 0,
  food_price int not null default 0,
  create_time int(11) not null default 0,
  queue_up tinyint not null default 0,
  settle tinyint not null default 0
)