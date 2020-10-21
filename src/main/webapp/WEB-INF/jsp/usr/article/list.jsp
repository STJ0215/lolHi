<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>게시물 리스트</title>
    </head>
    
    <body>
        <h1>게시물 리스트</h1>
        
        <c:forEach var="article" items="${articles}">
            ID : ${article.id} <br>
            등록일 : ${article.regDate} <br>
            수정일 : ${article.updateDate} <br>
            제목 : ${article.title} <br>
            내용 : ${article.body} <br>
            <hr>
        </c:forEach>
    </body>
</html>
