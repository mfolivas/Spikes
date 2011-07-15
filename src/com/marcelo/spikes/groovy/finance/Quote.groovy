package com.marcelo.spikes.groovy.finance
import groovy.transform.Immutable

@Immutable
class Quote {
	String symbol, name, exchange
	Double lastClose
	
	public static Quote getInstance(String line) {
		def quotes = line.split(",")
		if(quotes != null && quotes.size() == 4) {
			return new Quote(symbol: getCleanString(quotes[0]), name: getCleanString(quotes[1]), 
				exchange: getCleanString(quotes[3]), lastClose: Double.valueOf(quotes[2]))
		}
		return null
	}
	
	private static String getCleanString(String value) {
		return value.trim().replaceAll("\"", "")
	}
}
