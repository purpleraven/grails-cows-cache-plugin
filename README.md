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

Add the following dependencies in `build.gradle`
```
repositories {
...
  maven { url "http://dl.bintray.com/purpleraven/plugins" }
...
}
dependencies {
...
    compile 'org.grails.plugins:cows-cache:2.0'
...
}
```

      
Add following spring security configuration runtime.groovy to protect UI from access 
```
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/adminCache/**', access:["hasAuthority('YOUR_ADMIN_PERMISSION')"]]
]
```

Add `customCacheKeyGenerator(SimpleCacheKeyGenerator)` in resource.groovy (optional)
      
License
-------
Apache 2     