package com.marcelo.spikes.groovy.mocks.storer

class GroovyReverser implements Reverser {
    def reverse(item) {
        if (item instanceof Number) return -item
        return item.reverse()
    }
}