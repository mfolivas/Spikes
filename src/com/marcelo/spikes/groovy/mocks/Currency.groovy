package com.marcelo.spikes.groovy.mocks

class Currency {
    public static final Currency USD = new Currency("USD")
    public static final Currency EUR = new Currency("EUR")
    private String currencyCode
    private Currency(String currencyCode) {
        this.currencyCode = currencyCode
    }
    public String toString() {
        return currencyCode
    }
}
