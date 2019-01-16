create table owner
(
   id integer not null auto_increment,
   name varchar(255) not null,
);

create table account
(
   id integer not null auto_increment,
   main_owner_id integer not null,
   amount decimal default 0,
   currency varchar(3) not null,
   creation_date date,
   primary key(id),
   FOREIGN KEY (main_owner_id)
   REFERENCES owner(id)
);

create table TRANSFER
(
   transfer_id UUID default random_uuid(),
   from_Account_id integer not null,
   to_Account_id integer not null,
   amount decimal default 0,
   currency varchar(3) not null,
   transfer_date date,
   primary key(transfer_id),
   FOREIGN KEY (from_Account_id)
   REFERENCES account(id),
   FOREIGN KEY (to_Account_id)
   REFERENCES account(id)
);