# grails-cows-cache-plugin
[ ![Download](https://api.bintray.com/packages/purpleraven/plugins/grails-cows-cache-plugin/images/download.svg) ](https://bintray.com/purpleraven/plugins/grails-cows-cache-plugin/_latestVersion)

The plugin provides possibility to use Spring Cache together with Hibernate L2 cache and EHCache 

Grails 3.3 supported 

Usage
-----
Interesting things:
* Provided SimpleCacheKeyGenerator can be used as Spring customCacheKeyGenerator
* The plugin includes EHCache administrative UI with cache size manipulation possibilities and listing cache content (/adminCache/index)
* CowsDBService has method for direct sql quering inside current Hibernate transaction: sqlUpdate, sqlExecute, sqlFirstRow, sqlEachRow, sqlRows
   
      
Installation
------------
      
Add following limitation in runtime.groovy 
```
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/adminCache/**', access:["hasAuthority('YOUR_ADMIN_PERMISSION')"]]
]
```

Add `customCacheKeyGenerator(SimpleCacheKeyGenerator)` in resource.groovy
      
License
-------
Apache 2     