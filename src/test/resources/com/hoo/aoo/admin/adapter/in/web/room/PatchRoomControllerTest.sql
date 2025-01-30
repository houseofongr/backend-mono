insert into HOUSE(ID, TITLE, AUTHOR, DESCRIPTION, BASIC_IMAGE_FILE_ID, BORDER_IMAGE_FILE_ID, WIDTH, HEIGHT,
                  CREATED_TIME, UPDATED_TIME)
values (1, 'cozy houseInfo', 'leaf', 'my cozy houseInfo', 1, 2, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ROOM(ID, NAME, X, Y, Z, WIDTH, HEIGHT, IMAGE_FILE_ID, HOUSE_ID)
values (1, "거실", 0, 0, 0, 5000, 1000, 5, 1),
(2, "주방", 0, 1000, 0, 5000, 1000, 6, 1);
