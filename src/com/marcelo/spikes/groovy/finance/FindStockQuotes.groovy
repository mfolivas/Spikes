package com.marcelo.spikes.groovy.finance

import java.util.Map;

import groovy.sql.Sql

public class FindStockQuotes {
	public def getFromYahoo(Map map) {
		String quote = ""
		map.keySet().each { key ->
			quote += key + "+" 
		}
		String format = "snpx"
		def yahooMap = [:]
		/**
		 * In the url &f=snl1c1p2 specifies the format string for the following fields
		 * Symbol                            s
		 * Name                              n
		 * Last close value	                 p
		 * Exchange name	                 x
		 */
		def page = new URL("http://download.finance.yahoo.com/d/?s=${quote}&f=${format}").eachLine{ line ->
			def q = Quote.getInstance(line)
			yahooMap.put(q.getSymbol(), q)
		}
		yahooMap
	}
	
	public def getQuotes() {
		def db = [url:'jdbc:sqlserver://app04:1433;databaseName=gts', user:'dbuser', password:'bloody1!', driver:'com.microsoft.sqlserver.jdbc.SQLServerDriver']
		def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
		def map = [:]
		sql.eachRow "select * from tblsecuritymaster where symbol IN ('GOOG', 'AAPL', 'IBM', 'ORCL')", { row ->
			def quote = new Quote(symbol: row.symbol, name: row.name, exchange : row.primaryExchange, lastClose: row.lastClose)
			map.put(quote.symbol, quote)
		}
		return map
	}
	
	
	public static boolean validate(Map db, Map yahoo) {
		boolean isValid = false
		db.keySet().each{ key ->
			def fromDb = db.get(key)
			def fromYahoo = yahoo.get(key)
			isValid = (fromDb.getLastClose() == fromYahoo.getLastClose())
			if(!isValid) {
				println "Symbol: " + fromDb.getSymbol() + ", last close: " + fromYahoo + ", is not valid"
			}
			
		}
		return isValid
	}
}
