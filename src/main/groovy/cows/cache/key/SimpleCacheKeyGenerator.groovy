package cows.cache.key

import grails.plugin.cache.GrailsCacheKeyGenerator
import groovy.transform.CompileStatic
import org.springframework.cache.interceptor.KeyGenerator
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import java.lang.reflect.Method

@CompileStatic
class SimpleCacheKeyGenerator implements KeyGenerator, GrailsCacheKeyGenerator {
  @Override
  Serializable generate(String s, String s1, int i, Closure closure) {
    final Object simpleKey = closure.call()
    if (simpleKey instanceof Serializable) return simpleKey
    new CacheKey(simpleKey)
  }

  @Override
  Serializable generate(String s, String s1, int i, Map map) {
    throw new NotImplementedException()
  }

  @Override
  Object generate(Object target, Method method, Object... params) {
    throw new NotImplementedException()
  }
}

