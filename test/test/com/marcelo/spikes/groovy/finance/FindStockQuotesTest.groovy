package test.com.marcelo.spikes.groovy.finance;

import static org.junit.Assert.*;

import org.junit.Test;

import com.marcelo.spikes.groovy.finance.FindStockQuotes;

class FindStockQuotesTest {

	@Test
	public void shouldReturnRecordsFromTheDatabase() {
		def quotes = new FindStockQuotes()
		
		def fromDb = quotes.getQuotes()
		assert fromDb != null
		assert fromDb.size() > 0
		assert true == fromDb.containsKey("GOOG")
		def fromYahoo = quotes.getFromYahoo(fromDb)
		assert fromYahoo != null
		assert true == fromYahoo.containsKey("GOOG")
		
		assert quotes.validate(fromDb, fromYahoo)
	}
}
