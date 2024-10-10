<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Add Video</h2>
<form action="<c:url value='/admin/video/insert'></c:url>" method="post" enctype="multipart/form-data">

	<label for="poster">Image:</label><br>
    <input type="file" id="poster" name="poster"><br><br>
    
    <label for="videoId">Video ID:</label><br>
    <input type="text" id="videoId" name="videoId"><br>
    
    <label for="title">Video Title:</label><br>
    <input type="text" id="title" name="title"><br>
    
    <label for="views">View Count:</label><br>
    <input type="number" id="views" name="views" value="0"><br>
    
    <label for="category">Category:</label><br>
    <select id="category" name="categoryId">
        <c:forEach var="cate" items="${listCategories}">
            <option value="${cate.categoryId}">${cate.categoryname}</option>
        </c:forEach>
    </select><br>
    
    <label for="description">Description:</label><br>
    <textarea id="description" name="description"></textarea><br><br>
    
    <p style="display: inline;">Status:</p>
	<input type="radio" id="active" name="status" value="1" checked>
	<label for="active">Hoạt động</label>

	<input type="radio" id="inactive" name="status" value="0">
	<label for="inactive">Khóa</label><br><br>

    
    <input type="submit" value="Create Video">
</form>

<hr> <!-- Horizontal line to separate add form and list -->

<h2>Video List</h2>
<table border="1">
    <tr>
        <th>STT</th>
        <th>Video ID</th>
        <th>Images</th>
        <th>Video Title</th>
        <th>Description</th>
        <th>Views</th>
        <th>Category</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${listVideos}" var="video" varStatus="STT">
        <tr>
            <td>${STT.index + 1}</td>
            <td>${video.videoId}</td>
            <td><img height="150" width="200" src="${video.poster}" /></td>
            <td>${video.title}</td>
            <td>${video.description}</td>
            <td>${video.views}</td>
            <td>${video.category != null ? video.category.categoryname : 'N/A'}</td>
            <td>${video.active == 1 ? 'Hoạt động' : 'Khóa'}</td>
            <td>
                <a href="<c:url value='/admin/video/edit?id=${video.videoId}'/>">Sửa</a>
                | <a href="<c:url value='/admin/video/delete?id=${video.videoId}'/>">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
