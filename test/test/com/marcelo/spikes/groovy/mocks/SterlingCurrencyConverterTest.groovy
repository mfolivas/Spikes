package test.com.marcelo.spikes.groovy.mocks;

import static org.junit.Assert.*
import groovy.mock.interceptor.*

import org.junit.Test

import com.marcelo.spikes.groovy.mocks.Currency
import com.marcelo.spikes.groovy.mocks.ExchangeRate
import com.marcelo.spikes.groovy.mocks.ExchangeRateService
import com.marcelo.spikes.groovy.mocks.SterlingCurrencyConverter

class SterlingCurrencyConverterTest {
	//Mocking using map coercion

	@Test
	public void should_return_service_with_map_cohersion() {
		def service = [retrieveRate:{ new ExchangeRate(1.45, 0.57) }] as ExchangeRateService //map
		def sterlingConverter = new SterlingCurrencyConverter(service)
		double convertedAmount = sterlingConverter.convertFromSterling(10.0, Currency.USD);
		assert convertedAmount == 14.50
	}

	@Test
	public void should_return_a_service_with_closure_cohersion() {
		def service = { new ExchangeRate(1.55, 0.56) } as ExchangeRateService //closure
		def sterlingConverter = new SterlingCurrencyConverter(service)
		double convertedAmount = sterlingConverter.convertFromSterling(10.0, Currency.USD);
		assert convertedAmount == 15.50
	}

	@Test
	public void should_return_a_mock_service_with_mockFor() {
		def mockContext1 = new MockFor(ExchangeRateService)
		mockContext1.demand.retrieveRate { new ExchangeRate(1.75, 0.54) }
		def dummyService1 = mockContext1.proxyInstance()
		def sterlingConverter1 = new SterlingCurrencyConverter(dummyService1)
		def convertedAmount1 = sterlingConverter1.convertFromSterling(10.0, Currency.USD)
		mockContext1.verify dummyService1
		assert convertedAmount1 == 17.50
		
		def mockContext2 = new MockFor(ExchangeRateService)
		mockContext2.demand.retrieveRate(1){ new ExchangeRate(1.85, 0.53) }
		def dummyService2 = mockContext2.proxyInstance()
		def sterlingConverter2 = new SterlingCurrencyConverter(dummyService2)
		def convertedAmount2 = sterlingConverter2.convertFromSterling(10.0, Currency.USD)
		mockContext2.verify dummyService2
		assert convertedAmount2 == 18.50
	}
}
