set foreign_key_checks = 0;
TRUNCATE TABLE CATEGORY;
set foreign_key_checks = 1;

INSERT INTO CATEGORY(ID, NAME)
values (1, 'LIFE'),
       (2, 'PUBLIC'),
       (3, 'GOVERNMENT');