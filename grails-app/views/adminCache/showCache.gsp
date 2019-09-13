%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Hibernate cache regions</title></head>
<body>
<g:form action="cleanHibernateCache">
    <table border="1" width="100%">
        <tr>
            <th>Clean</th>
            <th>Region name</th>
            <th>Count</th>
            <th>Size</th>
        </tr>
        <g:each in="${regions}" var="region">
            <tr>
                <td><g:checkBox name="${region.key}"/></td>
                <td><g:link action="showCacheKeys" params="[key: region.key]">${region.key}</g:link></td>
                <td align="right">${region.value.elementCountInMemory}</td>
                <td align="right"><g:formatNumber number="${region.value.sizeInMemory}" format="###,###,###"/></td>
            </tr>
        </g:each>
        <tr>
            <th colspan="2">Total</th>
            <td align="right"><g:formatNumber number="${regions.values().elementCountInMemory.sum()}" format="###,###,###"/></td>
            <td align="right"><g:formatNumber number="${regions.values().sizeInMemory.sum()}" format="###,###,###"/></td>
        </tr>
    </table>
    <g:submitButton name="submit" value="Clean"/> 
</g:form>
</body>
</html>