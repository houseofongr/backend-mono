insert into HOUSE(TITLE, AUTHOR, DESCRIPTION, BASIC_IMAGE_FILE_ID, BORDER_IMAGE_FILE_ID, WIDTH, HEIGHT,
                  CREATED_TIME, UPDATED_TIME)
values ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ( 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('cozy house', 'leaf', 'my cozy house', 1, 2, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ROOM(ID, NAME, X, Y, Z, WIDTH, HEIGHT, IMAGE_FILE_ID, HOUSE_ID)
values (1, "거실", 0, 0, 0, 5000, 0, 5, 1),
       (2, "주방", 0, 1000, 0, 5000, 1000, 6, 1);

insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, TERMS_OF_USE_AGREEMENT, PERSONAL_INFORMATION_AGREEMENT)
values (10, '남상엽', 'NOT_SET', 'leaf', false, false);

insert into HOME(ID, USER_ID, HOUSE_ID, NAME, CREATED_TIME, UPDATED_TIME)
values (1, 10, 1, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 10, 1, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 10, 1, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 10, 1, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 10, 1, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 10, 1, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ITEM(ID, NAME, HOME_ID, ROOM_ID, USER_ID, DTYPE)
values (1, '설이', 1, 1, 10, 'RECTANGLE');

insert into RECTANGLE_ITEM(ID, X, Y, WIDTH, HEIGHT, ROTATION)
values (1, 100, 100,  10, 10, 5);
