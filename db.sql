# 데이터 베이스 생성
DROP DATABASE IF EXISTS lolHi;
CREATE DATABASE lolHi;
USE lolHi;



# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    `name` CHAR(100) NOT NULL
);

# 회원 데이터 추가
INSERT INTO `member` SET
regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = 'test1';

INSERT INTO `member` SET
regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = 'test2';



# 게시판 테이블 생성
Create table board (
    id int(10) unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    `name` char(20) unique not null,
    `code` char(20) unique not null
);

# 게시판 데이터 추가
Insert into board set
regDate = now(),
updateDate = now(),
`name` = '공지사항',
`code` = 'notice';

INSERT INTO board SET
regDate = NOW(),
updateDate = NOW(),
`name` = '자유게시판',
`code` = 'free';



# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 데이터 추가
INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4';



# 게시물 테이블에 memberId 칼럼 추가
alter table article add column memberId int(10) unsigned not null after updateDate;
# 기존 게시물의 작성자가 1번 회원이라고 정한다
update article set memberId = 1 where memberId = 0;



# 게시물 데이터 추가
INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
memberId = 2,
title = '제목5',
`body` = '내용5';

INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
memberId = IF(RAND() > 0.5, 2, 1),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND());



# 게시물 테이블에 boardId 칼럼 추가
alter table article add column boardId int(10) unsigned not null after updateDate;
update article set boardId = 1 where id <= 2;
update article set boardId = 2 where id > 2;



#댓글 테이블 생성
create table reply (
    id int(10) unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    relTypeCode CHAR(50) NOT NULL, # 관련 데이터 타입
    relId INT(10) UNSIGNED NOT NULL, # 관련 ID
    memberId int(10) unsigned not null,
    `body` text not null
);



# 댓글 데이터 추가
insert into reply set
regDate = now(),
updateDate = now(),
relTypeCode = 'article',
relId = 1,
memberId = 1,
`body` = '댓글1';

INSERT INTO reply SET
regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
memberId = 1,
`body` = '댓글2';

INSERT INTO reply SET
regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
memberId = 2,
`body` = '댓글3';

INSERT INTO reply SET
regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 2,
memberId = 2,
`body` = '댓글4';


# 회원 테이블 조회
SELECT * FROM `member`;

# 게시판 테이블 조회
Select * from board;

# 게시물 테이블 조회(내림차순)
SELECT * From article order by id desc;

# 댓글 테이블 조회
SELECT * FROM reply;
