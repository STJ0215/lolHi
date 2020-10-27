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
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목5',
`body` = '내용5';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목6',
`body` = '내용6';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목7',
`body` = '내용7';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목8',
`body` = '내용8';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목9',
`body` = '내용9';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목10',
`body` = '내용10';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목11',
`body` = '내용11';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목12',
`body` = '내용12';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목13',
`body` = '내용13';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목14',
`body` = '내용14';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목15',
`body` = '내용15';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목16',
`body` = '내용16';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목17',
`body` = '내용17';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목18',
`body` = '내용18';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목19',
`body` = '내용19';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목20',
`body` = '내용20';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목21',
`body` = '내용21';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목22',
`body` = '내용22';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목23',
`body` = '내용23';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목24',
`body` = '내용24';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목25',
`body` = '내용25';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목26',
`body` = '내용26';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목27',
`body` = '내용27';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목28',
`body` = '내용28';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목29',
`body` = '내용29';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목30',
`body` = '내용30';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목31',
`body` = '내용31';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목32',
`body` = '내용32';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = 'title',
`body` = 'body';

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;
# 기존 게시물의 작성자가 1번 회원이라고 정한다
UPDATE article SET memberId = 1 WHERE memberId = 0;

# 회원 테이블 조회
SELECT * FROM `member`;

# 게시물 테이블 조회(내림차순)
SELECT * FROM article ORDER BY id DESC;