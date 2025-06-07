insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, EMAIL, TERMS_OF_USE_AGREEMENT,
                     PERSONAL_INFORMATION_AGREEMENT, CREATED_TIME, UPDATED_TIME)
values (1, '남상엽', 'NOT_SET', 'leaf', 'test@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


insert into UNIVERSE(UNIVERSE.ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION,
                     UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID,
                     universe.INNER_IMAGE_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT, UNIVERSE.USER_ID)
values (1, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '공공기관 관련 유니버스입니다.', 'PUBLIC', 1, 2, 3, '정책 유니버스', 1, 1);

insert into SPACE(SPACE.ID, SPACE.INNER_IMAGE_FILE_ID, SPACE.TITLE, SPACE.DESCRIPTION, SPACE.PARENT_SPACE_ID,
                  SPACE.UNIVERSE_ID, SPACE.DEPTH, SPACE.DX, SPACE.DY, SPACE.SCALE_X, SPACE.SCALE_Y, SPACE.CREATED_TIME,
                  SPACE.UPDATED_TIME)
values (1, 4, 'space1', '유니버스의 스페이스-1', null, 1, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00'),
       (2, 5, 'space2', '유니버스의 스페이스-2', null, 1, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00'),
       (3, 6, 'space3', '스페이스1의 스페이스-1', 1, 1, 2, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00'),
       (4, 7, 'space4', '스페이스2의 스페이스-1', 2, 1, 2, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00'),
       (5, 8, 'space5', '스페이스2의 스페이스-2', 2, 1, 2, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00');

insert into ELEMENT(ELEMENT.ID, ELEMENT.INNER_IMAGE_FILE_ID, ELEMENT.TITLE, ELEMENT.DESCRIPTION, ELEMENT.PARENT_SPACE_ID,
                    ELEMENT.UNIVERSE_ID, ELEMENT.DEPTH, ELEMENT.DX, ELEMENT.DY, ELEMENT.SCALE_X, ELEMENT.SCALE_Y,
                    ELEMENT.CREATED_TIME, ELEMENT.UPDATED_TIME)
values (1, 9, 'element1', '유니버스의 엘리먼트-1', null, 1, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00'),
       (2, 10, 'element2', '스페이스1의 엘리먼트-1', 1, 1, 2, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00'),
       (3, 11, 'element3', '스페이스3의 엘리먼트-1', 3, 1, 3, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00'),
       (4, 12, 'element4', '스페이스4의 엘리먼트-1', 4, 1, 3, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00'),
       (5, 13, 'element5', '스페이스4의 엘리먼트-2', 4, 1, 3, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00'),
       (6, 14, 'element6', '스페이스5의 엘리먼트-1', 5, 1, 3, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00'),
       (7, 15, 'element7', '스페이스5의 엘리먼트-2', 5, 1, 3, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00');
