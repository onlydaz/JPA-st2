<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<c:url value='/admin/category/insert'></c:url>" method="post" enctype="multipart/form-data">
    <label for="fname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname"><br>
    
    <label for="lname">Images:</label><br>
    <img height="150" width="200" src="" id="imagess" />
    <input type="file" onchange="chooseFile(this)" id="images" name="images"><br>
    
    <p>Status:</p>
    <input type="radio" id="ston" name="status" value="1" checked>
    <label for="ston">Đang hoạt động</label><br>
    
    <input type="radio" id="stoff" name="status" value="0">
    <label for="stoff">Khóa</label><br>
    
    <input type="submit" value="Insert">
</form>
