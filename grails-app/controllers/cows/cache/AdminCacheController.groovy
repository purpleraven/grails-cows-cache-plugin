package cows.cache

import net.sf.ehcache.CacheManager
import net.sf.ehcache.Ehcache

//import org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin
//import org.hibernate.cache.spi.CacheKey

class AdminCacheController {
  static layout = "main"

  def sessionFactory
  def grailsCacheManager

  def index() {
    def names = cacheManager.cacheNames.sort()
    log.error("Registered caches "+names)
    def instanceMapSize
    try{
//      instanceMapSize = DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP.get().size()
    } catch (all){
      log.error(all)
    }

    def ehcaches = names.collect { name -> cacheManager.getEhcache(name)}

    [caches: ehcaches , instanceMapSize:instanceMapSize]
  }

  def cleanCache(){
    log.error("Cleaning EHCACHE cache "+params.name)
    findCache(params.name)?.flush()
    redirect (action:'index')
  }

  def showCache(){
    [regions: sessionFactory.allSecondLevelCacheRegions]
  }

  def showCacheKeys(String key){
//    def region = params.key == 'org.hibernate.cache.StandardQueryCache'  ?
//      sessionFactory.queryCache.region :
//      sessionFactory.getSecondLevelCacheRegion(params.key)
    def keys = cacheManager.getEhcache(key).getKeys()
    if (keys.size()>1000){
      def array = keys.toArray()
      array = Arrays.copyOfRange(array, 0, 1000)
      keys = Arrays.asList(array)
    }

    def firstKey = keys?.find{it}
    [keys: keys, firstKey:firstKey]
  }

  def showCacheValue(String name, String key ){
    def firstKey = cacheManager.getEhcache(name).getKeys().find{it}
    def typedKey
    if (firstKey instanceof String){
      typedKey = key
    } else if (firstKey instanceof Long){
      typedKey = new Long(key)
    } else {
      log.warn "firstKey type is ${firstKey.class}"
    }/*else if (firstKey instanceof CacheKey) {
      def id = key.substring(key.indexOf('#')+1)
      if (firstKey.key instanceof Long){
        id = new Long(id)
      }

      typedKey = sessionFactory.currentSession.generateCacheKey(id, firstKey.type, firstKey.entityOrRoleName)
    }*/


    def value = cacheManager.getEhcache(name).get(typedKey)
    [value:value?.objectValue, presentedInCache: value!=null]
  }

  def calcSize(){
    def memorySize = findCache(params.id).calculateInMemorySize().intdiv(1024)
    render(contentType:'text/plain', text: "${memorySize} Kb")
  }

  def private CacheManager getCacheManager(){
    grailsCacheManager.cacheManager
  }

  def private findCache(name){
    cacheManager.getEhcache(name)
  }

  def updateCacheSizes(){
    def names = cacheManager.cacheNames
    def enableAll = params['enable.all']
    boolean cleanAll = params['clean.all'] != null
    names.each {name ->
      def newSize = params["value.$name"] as int
      def cache = findCache(name)
      def cacheConf = cache.cacheConfiguration
      if (cacheConf.maxEntriesLocalHeap != newSize) {
        log.warn("Updating size of cache region: $name, new size: $newSize")
        cacheConf.maxEntriesLocalHeap = newSize
      }
      boolean enabled = params["enable.$name"] != null
      boolean clean = params["clean.$name"] != null
      if(clean || cleanAll) {
        println("Cleaning stats: $name")
//        cache.clearStatistics()
      }
      if(enableAll != 'Same'){
        enabled = enableAll == 'Enable'
      }


//      if(enabled != cache.statistics.isticsEnabled) {
//        println("Updating : $name, stats enabled: $enabled")
//        cache.statisticsEnabled = enabled
//      }
    }
    redirect(action: "index")
  }

  def cleanHibernateCache(){
    log.error("Cleaning HIBERNATE cache")
    def regionMap = sessionFactory.allSecondLevelCacheRegions
    regionMap.each {key, value ->
      if (params[key]) {
        log.warn("Clearing cache region: $key")
        if (key == 'org.hibernate.cache.StandardQueryCache') {
          sessionFactory.queryCache.clear()
        } else {
          try {
            sessionFactory.evictEntity(key)
          } catch (all) {}
          try {
            sessionFactory.evictCollection(key)
          } catch (all) {}
        }
      }
    }
    redirect(action: 'showCache')
  }

  def cleanAllCaches(){
    log.error("Cleaning ALL caches ")
    cacheManager.cacheNames.each {String name->
      cacheManager.getEhcache(name)?.flush()
    }
    redirect (action:'index')
  }
}
