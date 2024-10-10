<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<c:url value='/admin/video/update'></c:url>" method="post" enctype="multipart/form-data">
    <input type="hidden" id="videoId" name="videoId" value="${video.videoId}"><br>
    
    <label for="title">Video Title:</label><br>
    <input type="text" id="title" name="title" value="${video.title}"><br>
    
    <label for="views">View Count:</label><br>
    <input type="number" id="views" name="views" value="${video.views}"><br>
    
    <label for="category">Category:</label><br>
    <select id="category" name="categoryId">
        <c:forEach var="cate" items="${listCategories}">
            <option value="${cate.categoryId}" 
                <c:if test="${cate.categoryId == video.category.categoryId}">selected</c:if>>
                ${cate.categoryname}
            </option>
        </c:forEach>
    </select><br>
    
    <label for="description">Description:</label><br>
    <textarea id="description" name="description">${video.description}</textarea><br>
    
    <label for="poster">Choose Poster Image:</label><br>
    <img height="150" width="200" src="${video.poster}" alt="Current Poster" /><br>
    <input type="file" id="poster" name="poster"><br><br>
    
    <p style="display: inline;">Status:</p>
    <input type="radio" id="active" name="status" value="1" 
        <c:if test="${video.active == 1}">checked</c:if>>
    <label for="active">Hoạt động</label>
    
    <input type="radio" id="inactive" name="status" value="0" 
        <c:if test="${video.active == 0}">checked</c:if>>
    <label for="inactive">Khóa</label><br><br>
    
    <input type="submit" value="Update Video">
</form>
