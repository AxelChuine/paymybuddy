create database pay_my_buddy;

use pay_my_buddy;

create table if not exists account
(
    identifier int auto_increment
        primary key,
    email      varchar(255)   null,
    password   varchar(255)   not null,
    name       varchar(255)   null,
    balance    decimal(38, 2) null,
    username   varchar(255)   null,
    constraint account_uk
        unique (email)
);

create table if not exists connection
(
    account    int not null,
    connection int not null
);

create table if not exists transaction
(
    identifier       int not null auto_increment primary key,
    name             varchar(255),
    amount           numeric,
    sender           int not null,
    receiver         int not null,
    transaction_date timestamp
);

alter table connection
    add constraint connection_account_fk
        foreign key (account) references account (identifier);

alter table connection
    add constraint connection_account_connection_fk
        foreign key (connection) references account (identifier);

alter table transaction
add constraint transaction_sender_fk
foreign key (sender) references account (identifier);

alter table transaction
    add constraint transaction_receiver_fk
        foreign key (receiver) references account (identifier);