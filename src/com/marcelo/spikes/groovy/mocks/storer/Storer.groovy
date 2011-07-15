package com.marcelo.spikes.groovy.mocks.storer

class Storer {
    def stored
    Reverser reverser = new GroovyReverser()
    def put(item) {
        stored = item
    }
    def get() {
        return stored
    }
    def getReverse() {
        return reverser.reverse(stored)
    }
}