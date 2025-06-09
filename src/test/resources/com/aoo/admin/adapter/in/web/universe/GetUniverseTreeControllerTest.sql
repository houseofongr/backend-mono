insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, EMAIL, TERMS_OF_USE_AGREEMENT,
                     PERSONAL_INFORMATION_AGREEMENT, CREATED_TIME, UPDATED_TIME)
values (1, '남상엽', 'NOT_SET', 'leaf', 'test@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


insert into UNIVERSE(UNIVERSE.ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION,
                     UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID,
                     universe.INNER_IMAGE_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT, UNIVERSE.USER_ID)
values (1, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '공공기관 관련 유니버스입니다.', 'PUBLIC', 1, 2, 3, '정책 유니버스', 1, 1);

insert into SPACE(SPACE.ID, SPACE.INNER_IMAGE_FILE_ID, SPACE.TITLE, SPACE.DESCRIPTION, SPACE.PARENT_SPACE_ID,
                  SPACE.UNIVERSE_ID, SPACE.SX, SPACE.SY, SPACE.EX, SPACE.EY, SPACE.CREATED_TIME,
                  SPACE.UPDATED_TIME)
values (1, 4, 'space1', '유니버스의 스페이스-1', null, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00'),
       (2, 5, 'space2', '유니버스의 스페이스-2', null, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00'),
       (3, 6, 'space3', '스페이스1의 스페이스-1', 1, 1, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00'),
       (4, 7, 'space4', '스페이스2의 스페이스-1', 2, 1, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00'),
       (5, 8, 'space5', '스페이스2의 스페이스-2', 2, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00');

insert into PIECE(PIECE.ID, PIECE.INNER_IMAGE_FILE_ID, PIECE.TITLE, PIECE.DESCRIPTION, PIECE.PARENT_SPACE_ID,
                  PIECE.UNIVERSE_ID, PIECE.SX, PIECE.SY, PIECE.EX, PIECE.EY,
                  PIECE.CREATED_TIME, PIECE.UPDATED_TIME)
values (1, 9, 'PIECE1', '유니버스의 엘리먼트-1', null, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00'),
       (2, 10, 'PIECE2', '스페이스1의 엘리먼트-1', 1, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00'),
       (3, 11, 'PIECE3', '스페이스3의 엘리먼트-1', 3, 1, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00'),
       (4, 12, 'PIECE4', '스페이스4의 엘리먼트-1', 4, 1, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00'),
       (5, 13, 'PIECE5', '스페이스4의 엘리먼트-2', 4, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00'),
       (6, 14, 'PIECE6', '스페이스5의 엘리먼트-1', 5, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00'),
       (7, 15, 'PIECE7', '스페이스5의 엘리먼트-2', 5, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00');
