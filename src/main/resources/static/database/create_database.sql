CREATE TABLE IF NOT EXISTS pay_my_buddy.person(
                                                  `identifier` integer auto_increment NOT NULL,
                                                  first_name VARCHAR(5000),
    last_name VARCHAR(5000),
    `password` VARCHAR(5000),
    PRIMARY KEY(`identifier`)
    );

CREATE TABLE pay_my_buddy.`account`(
                                       `identifier` integer auto_increment NOT NULL,
                                       `name` VARCHAR(5000),
                                       `description` TEXT,
                                       person_id integer NOT NULL,
                                       PRIMARY KEY(`identifier`)
);

CREATE TABLE IF NOT EXISTS pay_my_buddy.`transaction`(
                                                         `identifier` integer auto_increment NOT NULL,
                                                         `name` VARCHAR(5000),
    amount FLOAT,
    account_id integer NOT NULL,
    PRIMARY KEY(`identifier`)
    );

ALTER TABLE pay_my_buddy.`account`
    ADD CONSTRAINT person_account
        FOREIGN KEY (person_uuid) REFERENCES pay_my_buddy.person (`identifier`);

ALTER TABLE pay_my_buddy.`transaction`
    ADD CONSTRAINT account_transaction
        FOREIGN KEY (account_uuid) REFERENCES pay_my_buddy.`account` (`identifier`);
