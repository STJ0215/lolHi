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

# 회원 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = 'test1';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = 'test2';



# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 데이터 생성
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



# 회원 테이블 조회
SELECT * FROM `member`;

# 게시물 테이블 조회(내림차순)
SELECT * FROM article ORDER BY id DESC;

# 댓글 테이블 조회
SELECT * FROM reply;
