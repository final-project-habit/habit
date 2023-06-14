# [카테고리 테이블]
CREATE TABLE cate (
    cate_no      varchar(10)  NOT NULL  primary key,
    cate_large   varchar(50)  NOT NULL,
    cate_middle  varchar(50)  NOT NULL
);

insert into cate (cate_no, cate_large, cate_middle)
values ('s_1','스포츠', '서핑');

insert into cate (cate_no, cate_large, cate_middle)
values ('s_2', '스포츠', '요트');

insert into cate (cate_no, cate_large, cate_middle)
values ('s_3', '스포츠', '요가');

insert into cate (cate_no, cate_large, cate_middle)
values ('t_1', '상담', '심리상담');

insert into cate (cate_no, cate_large, cate_middle)
values ('c_1', '요리', '베이킹');



# [콘텐츠 테이블]
CREATE TABLE cont (
    cont_no        int	         NOT NULL	 auto_increment  primary key, #콘텐츠 코드
    cate_no        varchar(10) 	 NOT NULL,                                #카테고리 코드
    host_id        varchar(25)	 NOT NULL,                                #호스트 아이디
    cont_name      varchar(50)	 NOT NULL,                                #콘텐츠 명
    cont_place     varchar(30)	 NOT NULL,                                #콘텐츠 장소
    cont_img       varchar(100)	 NOT NULL,                                #대표이미지
    cont_size      int	         NULL  DEFAULT 0,                         #이미지 사이즈
    cont_content   Text(6500)    NOT NULL,                                #콘텐츠 설명
    cont_view      int	         NOT NULL	 DEFAULT 0,                   #조회수
    cont_stdate    datetime	     NOT NULL  DEFAULT now(),                 #콘텐츠 등록일
    cont_endate    datetime	     NOT NULL,                                #콘텐츠 종료일
    cont_hashtag1  varchar(35)	 NOT NULL,                                #해시태그1
    cont_hashtag2  varchar(35)	 NOT NULL,                                #해시태그2
    cont_hashtag3  varchar(35)	 NOT NULL,                                #해시태그3
    cont_hashtag4  varchar(35)	 NOT NULL,                                #해시태그4
    cont_hashtag5  varchar(35)	 NOT NULL,                                #해시태그5
    cont_status    char(1)	     NOT NULL  DEFAULT 'Y'                    #콘텐츠 판매 상태
);

insert into cont (cate_no, host_id, cont_name, cont_place, cont_stdate, cont_endate, cont_img, cont_content, cont_hashtag1, cont_hashtag2, cont_hashtag3, cont_hashtag4, cont_hashtag5,cont_status)
values ('s_1', 'user-1', '[서핑]원데이클래스', '강릉', '2022-04-10 14:00:00', '2022-05-10 00:00:00', 'surfing.jpg', '서핑가보자고', 'N','20|30', 'OUT', 'WC|WF|WA', 'P5','N');

insert into cont (cate_no, host_id, cont_name, cont_place, cont_stdate, cont_endate, cont_img, cont_content, cont_hashtag1, cont_hashtag2, cont_hashtag3, cont_hashtag4, cont_hashtag5,cont_status)
values ('t_1', 'user-2', '[심리상담] 1:1 상담 회차권 판매', '여의도', '2022-07-20 12:00:00', '2022-08-20 00:00:00', 'talk.jpg', '너의 마음 건강 체크', 'N','20|30|40|50', 'IN', 'WA', 'P3' ,'N');

insert into cont (cate_no, host_id, cont_name, cont_place, cont_stdate, cont_endate, cont_img, cont_content, cont_hashtag1, cont_hashtag2, cont_hashtag3, cont_hashtag4, cont_hashtag5,cont_status)
values ('c_1', 'user-2', '[베이킹] 휘낭시에 만들기 원데이클래스', '강남', '2022-08-01 00:00:00', '2022-09-01 00:00:00', 'talk.jpg', '구워보자', 'N','20|30', 'IN', 'WC|WF|WA', 'P5','N' );



# [원데이클래스 테이블]
CREATE TABLE one (
    pro_no      varchar(35)	 NOT NULL  primary key, #상품코드
    cont_no     int	         NOT NULL,              #콘텐츠 코드
    one_date    varchar(35)	 NOT NULL,              #상품명(수업일시)
    one_maxqty  int	         NOT NULL  DEFAULT 0,   #최대모집인원(총수량)
    one_price   int	         NOT NULL  DEFAULT 0,   #가격
    one_status  char(2)	     NOT NULL  DEFAULT 'OO' #상품 상태(OO:예약가능, OC:예약불가, OS:품절)
);
# 원데이 클래스 시퀀스(상품코드에 사용)
create sequence habit.sq_one
start with 1
increment by 1
maxvalue 999999
cycle;

insert into one(pro_no, cont_no, one_date, one_maxqty, one_price,one_status)
values(concat('o_no',nextval(habit.sq_one)), 1, '2022-04-17 14:00:00', 5, 30000,'OS');

insert into one(pro_no, cont_no, one_date, one_maxqty, one_price,one_status)
values(concat('o_no',nextval(habit.sq_one)), 1, '2022-04-24 14:00:00', 5, 30000,'OC');

insert into one(pro_no, cont_no, one_date, one_maxqty, one_price,one_status)
values(concat('o_no',nextval(habit.sq_one)), 3, '2022-08-10 15:00:00', 20, 30000,'OC');



# [회원권/회차권 테이블]
CREATE TABLE prod (
    pro_no       varchar(35)  NOT NULL  primary key, #상품코드
    cont_no      int          NOT NULL,              #콘텐츠 코드
    prod_name    varchar(50)  NOT NULL,              #상품명
    prod_qty     int          NOT NULL  DEFAULT 0,   #판매수량
    prod_price   int          NOT NULL  DEFAULT 0,   #가격
    prod_status  char(2)      NOT NULL  DEFAULT 'PO' #상품상태(PO:구매가능, PC:구매불가, PS:품절)
);
# 회원권/회차권 시퀀스(상품코드에 사용)
create sequence habit.sq_prod
start with 1
increment by 1
maxvalue 999999
cycle;

insert into prod(pro_no, cont_no, prod_name, prod_qty, prod_price,prod_status)
values(concat('p_no',nextval(habit.sq_prod)), 2, '1회권', 10, 10000, 'PC');

insert into prod(pro_no, cont_no, prod_name, prod_qty, prod_price,prod_status)
values(concat('p_no',nextval(habit.sq_prod)), 2, '2회권', 10, 18000, 'PC');

insert into prod(pro_no, cont_no, prod_name, prod_qty, prod_price,prod_status)
values(concat('p_no',nextval(habit.sq_prod)), 2, '3회권', 10, 24000, 'PC');

select *
from prod c join
     (select b.*
      from cate a join cont b
                       on a.cate_no = b.cate_no) d
 on c.cont_no = d.cont_no;
