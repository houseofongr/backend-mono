set foreign_key_checks = 0;
truncate table AAR_USER;
truncate table SNS_ACCOUNT;
set foreign_key_checks = 1;

insert into SNS_ACCOUNT(ID, REAL_NAME, NICKNAME, EMAIL, SNS_ID, SNS_DOMAIN, CREATED_TIME, UPDATED_TIME) values
    ('1', '남상엽', 'leaf', 'leaf@example.com', 'SNS_ID', 'KAKAO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into SNS_ACCOUNT(ID, REAL_NAME, NICKNAME, EMAIL, SNS_ID, SNS_DOMAIN, CREATED_TIME, UPDATED_TIME) values
    ('2', '남엽돌', 'spearoad', 'spearoad@example.com', 'SNS_ID_2', 'KAKAO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);