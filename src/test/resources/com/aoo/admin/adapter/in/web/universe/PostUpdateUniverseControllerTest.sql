set foreign_key_checks = 0;
truncate table AAR_USER;
truncate table UNIVERSE;
truncate table HASHTAG;
truncate table UNIVERSE_HASHTAG;
truncate table FILE;
set foreign_key_checks = 1;

insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, EMAIL, TERMS_OF_USE_AGREEMENT,
                     PERSONAL_INFORMATION_AGREEMENT, CREATED_TIME, UPDATED_TIME)
values (1, '남상엽', 'NOT_SET', 'leaf', 'test@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into UNIVERSE(ID, USER_ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION, UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID, universe.INNER_IMAGE_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT) values
    (1,1, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'GOVERNMENT_AND_PUBLIC_INSTITUTION', '유니버스는 우주입니다.', 'PUBLIC', 1, 1, 1,'우주', 0);

insert into HASHTAG(ID, TAG, CREATED_TIME, UPDATED_TIME) values
                                                             ('2', '우주', current_timestamp, current_timestamp),
                                                             ('3', '행성', current_timestamp, current_timestamp),
                                                             ('4', '지구', current_timestamp, current_timestamp),
                                                             ('5', '별', current_timestamp, current_timestamp);

insert into UNIVERSE_HASHTAG(ID, HASHTAG_ID, UNIVERSE_ID) values
                                                              ('1', '2', '1'),
                                                              ('2', '3', '1'),
                                                              ('3', '4', '1'),
                                                              ('4', '5', '1');
