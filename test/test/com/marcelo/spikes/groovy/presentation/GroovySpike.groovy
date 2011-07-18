package test.com.marcelo.spikes.groovy.presentation;

import static org.junit.Assert.*;

import org.junit.Test;
import groovy.sql.Sql
class GroovySpike {

	@Test
	public void test_should_explain_list_and_its_api() {
		def list = [4, 6, 11, 3, 5, 6, 7]
		assert [3, 4, 5, 6, 6, 7, 11]== list.sort()
		assert 11 == list.max()
		assert 3 == list.min()
		assert 42 == list.sum()
		assert [3, 4, 5, 6, 7, 11]== list.unique()
		list.each{ num -> println num }
	}

	@Test
	public void test_should_explain_the_ranges() {
		// an inclusive range
		def range = 5..8
		assert range.size() == 4
		assert range.get(2) == 7
		assert range[2] == 7
		assert range instanceof java.util.List
		assert range.contains(5)
		assert range.contains(8)

		// lets use an exclusive range
		range = 5..<8
		assert range.size() == 3
		assert range.get(2) == 7
		assert range[2] == 7
		assert range instanceof java.util.List
		assert range.contains(5)
		assert ! range.contains(8)

		//get the end points of the range without using indexes
		range = 1..10
		assert range.from == 1
		assert range.to == 10
	}

	/**
	 *  Closure composition is about that: the ability to compose Closures together to form a new Closure
	 */
	@Test
	public void test_should_explain_closure_composition() {
		def plus2 = {a -> a +2}
		assert 4 == plus2(2)
		def times3 = {it * 3}
		assert 6 == times3(2)

		def times3plus2 = times3 << plus2
		assert 12 == times3plus2(2)
	}

	@Test
	public void test_should_return_a_factorial_using_trampoline() {
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
	public void test_should_return_a_valid_test_using_memization() {
		def averagePrice = {
			price, shares -> sleep 1000; price*shares/shares}.memoize()
		println new Date()
		assert 5 == averagePrice(5,100)
		println new Date()
		assert 5 == averagePrice(5,100)
		println new Date()
		assert 10 == averagePrice(10,100)
		println new Date()
	}
	
	/**
	 * Maps can be created using the following syntax. Notice that [:] is the empty map expression.
	 */
	@Test
	public void test_should_return_a_map_object() {
		def map = [name:"Gromit", likes:"cheese", id:1234]
		assert map.get("name") == "Gromit"
		assert map.get("id") == 1234
		assert map["name"] == "Gromit"
		assert map['id'] == 1234
		assert map instanceof java.util.Map
		
		def emptyMap = [:]
		assert emptyMap.size() == 0
		emptyMap.put("foo", 5)
		assert emptyMap.size() == 1
		assert emptyMap.get("foo") == 5
	}
	
	@Test
	public void test_should_return_words_greater_than_four_in_the_list() {
		def words = ['ant', 'buffalo', 'cat', 'dinosaur']
		assert words.findAll{ w -> w.size() > 4 } == ['buffalo', 'dinosaur']
	}
	
	@Test
	public void test_should_return_the_first_letter_of_all_objects() {
		def words = ['ant', 'buffalo', 'cat', 'dinosaur']
		assert words.collect{ it[0] } == ['a', 'b', 'c', 'd']
	}

	@Test
	public void test_should_return_a_map_with_default_value() {
		def words = "one two two three three three".split()
		def freq = [:].withDefault { k -> 0 }
		words.each { freq[it] += 1 }
		assert ["one":1, "two":2, "three":3] == freq
	}
	
	
	@Test
	public void test_should_return_pagination_application() {
		def db = [url:'jdbc:sqlserver://gts11:1433;databaseName=intranet', user:'dbread', password:'bloody', driver:'com.microsoft.sqlserver.jdbc.SQLServerDriver']
		def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
		sql.eachRow('select * from tbltrades', 2, 2) { row ->  println "${row.symbol.padRight(10)} ($row.tradedate)"  }
	}
}
