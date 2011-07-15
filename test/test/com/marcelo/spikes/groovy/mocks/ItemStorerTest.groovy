package test.com.marcelo.spikes.groovy.mocks;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.marcelo.spikes.groovy.mocks.storer.JavaStorer;
import com.marcelo.spikes.groovy.mocks.storer.Reverser;

public class ItemStorerTest {
	private Reverser mockReverser
	private JavaStorer storer

	@Before
	public void init() {
		mockReverser = createMock(Reverser.class)
		storer = new JavaStorer(mockReverser)
	}
	

	@Test
	public void test_should_return_the_reverse_input_given() {
		expectReverse(123.456, -123.456)
		expectReverse('hello', 'olleh')
		replay(mockReverser)
		checkReverse(123.456, -123.456)
		checkReverse('hello', 'olleh')
		verify(mockReverser)
	}

	def expectReverse(input, output) {
		expect(mockReverser.reverse(input)).andReturn(output);
	}

	def checkReverse(value, reverseValue) {
		storer.put(value)
		assert value == storer.get()
		assert reverseValue == storer.getReverse()
	}
}
