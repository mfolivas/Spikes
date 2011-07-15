package test.com.marcelo.spikes.groovy.presentation;

import static org.junit.Assert.*;

import org.junit.Test;
import groovy.sql.Sql
class GroovySpike {

	@Test
	public void arrayAndListExample() {
		def list = [4,6,11,3,5,6,7]
		assert [3,4,5,6,6,7,11] == list.sort()
		assert 11 == list.max()
		assert 3 == list.min()
		assert 42 == list.sum()
		assert [3,4,5,6,7,11] == list.unique()
		list.each{ num ->
			println num
		}
	}
	
	@Test
	public void shouldReturnValidValue() {
		def plus2 = {a -> a +2}
		assert 4 == plus2(2)
		def times3 = {it * 3}
		assert 6 == times3(2)

		def times3plus2 = times3 << plus2
		assert 12 == times3plus2(2)
	}

	@Test
	public void shouldReturnAFactorialFunction() {
		def factorial
		factorial = {int n, BigInteger accu = 1G ->
			if(n<2) return accu
			factorial.trampoline(n-1, n*accu)//Closures are wrapped in a TrampolineClosure
		}
		factorial = factorial.trampoline()
		assert 1 == factorial(1)
		assert 1*2*3 == factorial(3)
		assert 7*6*5*4*3*2*1 == factorial(7)
	}
	
	@Test
	public void memoizeExample() {
		def averagePrice = {price, shares -> sleep 1000; price*shares/shares}.memoize()
		println new Date()
		assert 5 == averagePrice(5,100)
		println new Date()
		assert 5 == averagePrice(5,100)
		println new Date()
		assert 10 == averagePrice(10,100)
		println new Date()
	}
	
	@Test
	public void mapWithDefaultValue() {
		def words = "one two two three three three".split()
		def freq = [:].withDefault { k -> 0 }
		words.each {
				freq[it] += 1
		}
		assert ["one":1, "two":2, "three":3] == freq
	}

	@Test
	public void shouldReturnDBRecords() {
		def db = [url:'jdbc:sqlserver://gts10:1433;databaseName=intranet', user:'dbuser', password:'bloody', driver:'com.microsoft.sqlserver.jdbc.SQLServerDriver']
		def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
		sql.eachRow('select * from tbltrades', 2, 2) { row -> 
			println "${row.symbol.padRight(10)} ($row.tradedate)" 
		}
	}
}
