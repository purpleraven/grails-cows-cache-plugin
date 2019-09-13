package cows.cache.key;

public class CacheKey implements Serializable {
  final Object simpleKey

    CacheKey(Object simpleKey) {
    this.simpleKey = simpleKey
  }

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    CacheKey cacheKey = (CacheKey) o

    if (simpleKey != cacheKey.simpleKey) return false

    return true
  }

  int hashCode() {
    return (simpleKey != null ? simpleKey.hashCode() : 0)
  }


  @Override
  public String toString() {
    return "CacheKey{simpleKey=" + simpleKey + '}'
  }
}