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



# 회원 테이블에 email 칼럼 추가
ALTER TABLE `member` ADD COLUMN email CHAR(100) AFTER `name`;
# 기존 회원의 email 정보 추가
UPDATE `member` SET email ='stj960215@gmail.com';

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(20) UNIQUE NOT NULL,
    `code` CHAR(20) UNIQUE NOT NULL
);

# 게시판 데이터 추가
INSERT INTO board SET
regDate = NOW(),
updateDate = NOW(),
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
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;
# 기존 게시물의 작성자가 1번 회원이라고 정한다
UPDATE article SET memberId = 1 WHERE memberId = 0;



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
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER updateDate;
UPDATE article SET boardId = 1 WHERE id <= 2;
UPDATE article SET boardId = 2 WHERE id > 2;



#댓글 테이블 생성
CREATE TABLE reply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    relTypeCode CHAR(50) NOT NULL, # 관련 데이터 타입
    relId INT(10) UNSIGNED NOT NULL, # 관련 ID
    memberId INT(10) UNSIGNED NOT NULL,
    `body` TEXT NOT NULL
);



# 댓글 데이터 추가
INSERT INTO reply SET
regDate = NOW(),
updateDate = NOW(),
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
SELECT * FROM board;

# 게시물 테이블 조회(내림차순)
SELECT * FROM article ORDER BY id DESC;

# 댓글 테이블 조회
SELECT * FROM reply;

# 현재 패스워드를 암호화
UPDATE `member` SET
loginPw = SHA2(loginPw, 256);
