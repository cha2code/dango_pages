drop table if exists role_master;
create table role_master
(
    role_code            varchar(40) primary key            comment '권한코드',
    role_name            varchar(40) not null               comment '권한명칭',
    memo                 varchar(200)                       comment '비고',
    create_user          varchar(40) not null               comment '생성자',
    create_date          datetime default current_timestamp comment '생성일자',
    modify_user          varchar(40)                        comment '수정자',
    modify_date          datetime                           comment '수정일자'
) COMMENT '권한 정보 테이블';

drop table if exists user_master;
create table user_master
(
    user_id              varchar(40) primary key            comment '사용자 ID',
    user_password        varchar(256) not null              comment '사용자 비밀번호',
    nickname             varchar(20)  not null              comment '닉네임',
    email                varchar(256) not null              comment '이메일',
    password_modified_at datetime                           comment '비밀번호 변경일시',
    create_user          varchar(40)  not null              comment '생성자',
    create_date          datetime default current_timestamp comment '생성일자',
    modify_user          varchar(40)                        comment '수정자',
    modify_date          datetime                           comment '수정일자'
) COMMENT '사용자 정보 테이블';

drop table if exists user_role;
create table user_role
(
    user_id              varchar(40) primary key            comment '사용자 ID',
    role_code            varchar(40) not null               comment '권한코드',
    create_user          varchar(40) not null               comment '생성자',
    create_date          datetime default current_timestamp comment '생성일자',
    modify_user          varchar(40)                        comment '수정자',
    modify_date          datetime                           comment '수정일자',
    constraint fk_user_role_user_id foreign key (user_id) references user_master (user_id) on delete cascade,
    constraint fk_user_role_role_code foreign key (role_code) references role_master (role_code) on delete cascade
) COMMENT '사용자 권한 테이블';

drop table if exists item_master;
create table item_master
(
    item_id              bigint auto_increment primary key  comment '상품 번호',
    category_id          integer not null                   comment '메뉴 ID',
    nickname             varchar(20)  not null              comment '닉네임',
    image_url            varchar(200) not null              comment '이미지 URL',
    title                varchar(120) not null              comment '상품 제목',
    contents             text not null                      comment '상품 내용',
    price                integer not null                   comment '상품 가격',
    create_date          datetime default current_timestamp comment '생성일자',
    modify_date          datetime                           comment '수정일자'
) COMMENT '게시판 테이블';

drop table if exists item_user;
create table item_user
(
    user_id              varchar(40) primary key               comment '판매자 ID',
    item_id              bigint auto_increment                 comment '상품 번호',
    constraint fk_product_user_user_id foreign key (user_id) references user_master (user_id) on delete cascade,
    constraint fk_product_user_product_id foreign key (item_id) references item_master (item_id) on delete cascade
) COMMENT '사용자 게시글 테이블';