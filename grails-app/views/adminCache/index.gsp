<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Cache keys</title></head>
<body>
<h1>Caches</h1>

<h3>
  <g:link action="showCache">Show hibernate caches</g:link><br/>
%{--<g:link action="showCacheSize">Show cache size</g:link><br/>--}%
  <g:link action="cleanAllCaches">Clean ALL caches</g:link><br/>

</h3>
<g:form action="updateCacheSizes">
  <table class="table table-hover">
    <thead>
    <tr>
      <th>Name</th>
      <th>Max Size</th>
      <th>Cur Size</th>
      <th>Clear</th>
      <th>Mem Size</th>
      <th>Hits</th>
      <th>Miss</th>
      <th>Miss<br/> rate</th>
      %{--<th>Get<br/> Time</th>--}%
      <th>Stats<br/> Enabled</th>
      <th>Stats<br/> Clean</th>
    </tr>
    </thead>

    <g:each in="${caches}" var="cache">
      <g:set var="stat" value="${cache.statistics}"/>
      <g:set var="totalAccess" value="${stat.cacheMissCount() + stat.cacheHitCount()}"/>
      <tr>
        <td><g:link action="showCacheKeys" params="[key: cache.name]">${cache.name}</g:link></td>
        <td><g:textField name="value.${cache.name}" value="${cache.cacheConfiguration.maxEntriesLocalHeap}" style="width: 70px"/></td>
        <g:set var="occupation" value="${cache.size/cache.cacheConfiguration.maxEntriesLocalHeap}"/>
        <td title="${occupation*100}%, ${stat}" style="background-color: ${occupation>0.8? (occupation>0.98?'mistyrose':'#f0f0bd') :''}">
          <strong>${cache.size}</strong>
        </td>
        <td><g:link action="cleanCache" params="[name:cache.name]">Clear</g:link></td>
        <td>
          <span id="${cache.name.replaceAll('\\.', '-')}">
            <g:remoteLink id="${cache.name}" action="calcSize" update="${cache.name.replaceAll('\\.', '-')}">?</g:remoteLink>
          </span>
        </td>
        <td>${stat.cacheHitCount()}</td>
        <td>${stat.cacheMissCount()}</td>
        <td>
          <g:if test="${totalAccess}">
            <g:formatNumber number="${stat.cacheMissCount() / totalAccess}" format="##.#%"/>
          </g:if>
        </td>
        <%--<td> <g:formatNumber number="${stat.averageGetTime}" format="#.####"/> </td>--%>
        <td align="center">
          %{--<g:checkBox name="enable.${cache.name}" value="${cache.statisticsEnabled}"/>--}%
        </td>
        <td align="center">
          <g:checkBox name="clean.${cache.name}" value="${false}"/>
        </td>

      </tr>
    </g:each>
    <tr>
      <td colspan="8">All</td>
      <td align="center"><g:select from="${['Same', 'Enable', 'Disable']}" name="enable.all"/></td>
      <td align="center"><g:checkBox name="clean.all" value="${false}"/></td>
    </tr>
  </table>
  <br/>
  <g:submitButton name="submit" value="Update" class="btn btn-success"/>
%{--Statistics: <g:link action="activateStatistics" params="[enable:true]">activate</g:link>--}%
%{--<g:link action="activateStatistics">deactivate</g:link>--}%
</g:form>

%{--<p>DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP.get().size():<strong>${instanceMapSize}</strong></p>--}%
</body>
</html>
