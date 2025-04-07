insert into UNIVERSE(UNIVERSE.ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION, UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT) values
    (12,CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'GOVERNMENT_AND_PUBLIC_INSTITUTION', '유니버스는 우주입니다.', 'PUBLIC', 2, 1, '우주', 0);


insert into HASHTAG(ID, TAG, CREATED_TIME, UPDATED_TIME) values
                                                             ('1', 'exist', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                             ('2', '우주', current_timestamp, current_timestamp),
                                                             ('3', '행성', current_timestamp, current_timestamp),
                                                             ('4', '지구', current_timestamp, current_timestamp),
                                                             ('5', '별', current_timestamp, current_timestamp);

insert into UNIVERSE_HASHTAG(ID, HASHTAG_ID, UNIVERSE_ID) values
                                                              ('1', '2', '12'),
                                                              ('2', '3', '12'),
                                                              ('3', '4', '12'),
                                                              ('4', '5', '12');

insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, TERMS_OF_USE_AGREEMENT, PERSONAL_INFORMATION_AGREEMENT) values
                                                                                                                        (10, '남상엽', 'NOT_SET', 'leaf', false, false),
                                                                                                                        (11, '남상엽', 'NOT_SET', 'leaf', false, false),
                                                                                                                        (12, '남상엽', 'NOT_SET', 'leaf', false, false),
                                                                                                                        (13, '남상엽', 'NOT_SET', 'leaf', false, false);

insert into UNIVERSE_LIKE(UNIVERSE_LIKE.ID, UNIVERSE_LIKE.CREATED_TIME, UNIVERSE_LIKE.UPDATED_TIME, UNIVERSE_LIKE.UNIVERSE_ID, UNIVERSE_LIKE.USER_ID) values
                                                                                                                                                          (1, current_timestamp, current_timestamp, 12, 10),
                                                                                                                                                          (2, current_timestamp, current_timestamp, 12, 11),
                                                                                                                                                          (3, current_timestamp, current_timestamp, 12, 12),
                                                                                                                                                          (4, current_timestamp, current_timestamp, 12, 13);
