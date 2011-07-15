package com.marcelo.spikes.groovy.finance

class ValidateStockService {

	static main(args) {
		def quotes = new FindStockQuotes()
		def fromDb = quotes.getQuotes()
		def fromYahoo = quotes.getFromYahoo(fromDb)
		println "isValid: " + quotes.validate(fromDb, fromYahoo)
	}

}
