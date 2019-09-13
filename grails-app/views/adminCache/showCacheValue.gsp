<%--
  Created by IntelliJ IDEA.
  User: demon
  Date: 13.11.15
  Time: 18:38
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>${params.name}: Value of ${params.key} </title>
</head>

<body>
<g:form controller="adminCache" action="showCacheValue" params="[name:params.name]">
  Key: <g:textField name="key" value="${params.key}" style="width: 500px"/>
  <g:submitButton name="Show"/>
</g:form>
<g:link controller="adminCache" action="showCacheKeys" params="[key:params.name]">Return to list</g:link>

<h1>Value of ${params.key}</h1>
<g:if test="${!presentedInCache}">Not presented<br/></g:if>
<g:else>
  Class: ${value?.getClass()}<br/>
  Value: '<strong>${value}</strong>'


</g:else>


</body>
</html>