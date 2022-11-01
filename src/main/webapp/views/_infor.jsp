<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

        <div style="min-height: 500px">        
        <h2 class="text-center">${message }</h2>
        <core:if test="${error != null }">
        	<h2 class="text-danger text-center">${error }</h2>
        </core:if>
        </div>