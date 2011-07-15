package com.marcelo.spikes.groovy.mocks

class SterlingCurrencyConverter implements CurrencyConverter {
    private ExchangeRateService exchangeRateService

    public SterlingCurrencyConverter(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    private double convert(double amount, double rate) throws InvalidAmountException {
        if (amount < 0) {
            throw new InvalidAmountException("amount must be non-negative")
        }
        return amount * rate
    }

    public double convertFromSterling(double amount, Currency toCurrency) throws InvalidAmountException {
        return convert(amount, exchangeRateService.retrieveRate(toCurrency).fromRate)
    }

    public double convertToSterling(double amount, Currency fromCurrency) throws InvalidAmountException {
        return convert(amount, exchangeRateService.retrieveRate(fromCurrency).toRate)
    }

}
