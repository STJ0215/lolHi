<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="title" value="게시물 리스트"/>
<%@ include file="../part/head.jspf"%>
        
        <div>
            총 게시물 갯수 : ${totalCount}
        </div>
        <div>
            <a href="write">글쓰기</a>
        </div>
        <hr>
        
        <c:forEach var="article" items="${articles}">
            ID : <a href="detail?id=${article.id}">${article.id}</a> <br>
            등록일 : ${article.regDate} <br>
            수정일 : ${article.updateDate} <br>
            작성자 : ${article.extra.writer} <br>
            제목 : <a href="detail?id=${article.id}">${article.title}</a> <br>
            <br>
            작업 : <a href="modify?id=${article.id}">수정</a>
                   <a href="doDelete?id=${article.id}" 
                       onclick="if (confirm('삭제하시겠습니까?') == false) return false;">삭제</a>
            <hr>
        </c:forEach>
        
        <div>
            <a href="write">글쓰기</a>
        </div>
        <br>
        
        <style>
            .selected {
                font-weight: bold;
                color: red;
            }
        </style>
        
        <span>
            페이지 : 
        </span>
        <div>
            <c:set var="goFirstBtnNeedToShow" value="${page > pageMenuArmSize + 1}"/>
            <c:set var="goLastBtnNeedToShow" value="true"/>
            
            <c:if test="${goFirstBtnNeedToShow}">
                <a href="?page=1">◀◀</a>
            </c:if>
            
            <c:forEach var="i" begin="${pageMenuStart}" end="${pageMenuEnd}">
                <c:set var="className" value="${i == page ? 'selected' : ''}"/>
                <a class="${className}" href="?page=${i}">${i}</a>
                
                <c:if test="${i == totalPage}">
                    <c:set var="goLastBtnNeedToShow" value="false"/>
                </c:if>
            </c:forEach>
            
            <c:if test="${goLastBtnNeedToShow}">
                <a href="?page=${totalPage}">▶▶</a>
            </c:if>
        </div>
<%@ include file="../part/foot.jspf"%>