# grails-cows-cache-plugin

The plugin provides possibility to use Spring Cache together with Hibernate second level cache and EHCache 

Interesting things:
* Provided SimpleCacheKeyGenerator can be used as Spring customCacheKeyGenerator. Just add `customCacheKeyGenerator(SimpleCacheKeyGenerator)` in resource.groovy
* The plugin includes EHCache administrative UI with cache size manipulation possiblities and listing cache content
* CowsDBService has method for direct sql quering inside current Hibernate transaction
      
      
Add following limitation in runtime.groovy 
```
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/adminCache/**', access:["hasAuthority('YOUR_ADMIN_PERMISSION')"]]
]
```
      