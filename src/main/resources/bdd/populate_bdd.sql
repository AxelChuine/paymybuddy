insert into account (first_name, last_name, email, password, name, balance)
values ('Jean', 'Dubois', 'jdubois@test.com', '1234', 'compte personnel', 50.0),
 ('Marc', 'Dubois', 'mdubois@test.com', '1234', 'compte personnel', 50.0),
 ('Isabelle', 'Duschmoll', 'iduschmoll@test.com', '1234', 'compte personnel', 50.0),
 ('Antoine', 'Pontieu', 'apontieu@test.com', '1234', 'compte personnel', 50.0),
 ('Pierre', 'Lefevre', 'plefevre@test.com', '1234', 'compte personnel', 50.0),
 ('Lisa', 'Duschmoll', 'lduschmoll@test.com', '1234', 'compte personnel', 50.0),
 ('Emilie', 'Dugenou', 'edugenou@test.com', '1234', 'compte personnel', 50.0),
 ('Ida', 'Dubois', 'idubois@test.com', '1234', 'compte personnel', 50.0);

insert into connection (account, connection)
values (1,2),
 (1,2),
 (2,3),
 (3,4),
 (2,5),
 (4,3),
 (5,6),
 (6,2),
 (4,2);

insert into transaction (name, amount, sender, receiver, transaction_date)
values ('restaurant', 23.0, 1, 2, '2024-02-01'),
 ('restaurant', 50.0, 3, 2, '2024-02-01'),
 ('bar', 23.0, 4, 1, '2024-02-01'),
 ('vêtements', 32.0, 4, 5, '2024-02-01'),
 ('restaurant', 56.0, 3, 6, '2024-02-01'),
 ('courses', 80.0, 4, 1, '2024-02-01'),
 ('cadeaux', 40.0, 3, 4, '2024-02-01'),
 ('nouveau processeur', 230.0, 5, 6, '2024-02-01');