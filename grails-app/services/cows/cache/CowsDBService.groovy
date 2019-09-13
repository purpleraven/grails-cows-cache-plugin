package cows.cache

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.hibernate.jdbc.Work
import org.springframework.transaction.CannotCreateTransactionException

import javax.annotation.PostConstruct
import java.sql.SQLRecoverableException

class CowsDBService {
  static transactional = false

  def sessionFactory

  def int sqlUpdate(query, params = []){
    int result = 0
    doWork ({connection -> result = new Sql(connection).executeUpdate(query, params) } )
    result
  }

  def boolean sqlExecute(query, params=[]){
    def result = false
    doWork({connection -> result = new Sql(connection).execute(query, params)})
    result
  }

  def GroovyRowResult sqlFirstRow(query, params = []){
    def result = null
    doWork ({connection -> result = new Sql(connection).firstRow(query, params)})
    result
  }

  def void sqlEachRow(String query, params, Closure cl){
    doWork ({connection -> new Sql(connection).eachRow(query, params, cl) })
  }

  def List<GroovyRowResult> sqlRows(String query, params = [], int offset = 0, int maxRows = 0){
    def result = null
    doWork ({connection -> result = new Sql(connection).rows(query, params, offset+1 , maxRows) }) // offset starts from 1
    result
  }

  private doWork(c){
    sessionFactory.currentSession.doWork (c as Work)
  }
}
