<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Cache keys</title></head>
<body>
<h1>Keys of ${params.key} <g:link controller="adminCache" action="index">Index</g:link></h1>
<g:form controller="adminCache" action="showCacheValue" params="[name:params.key]">
  Key: <g:textField name="key" value="" style="width: 300px"/>
  <g:submitButton name="Show"/>

  Class: ${firstKey?.getClass()}
</g:form>

<pre>
  <g:each in="${keys}" var="key">${key}<br/>
  </g:each>
</pre>
</body>
</html>