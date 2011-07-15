package com.marcelo.spikes.groovy.mocks.storer;

public class JavaStorer {
	private Object stored;
	private final Reverser reverser;

	public JavaStorer(Reverser reverser) {
		this.reverser = reverser;
	}

	public void put(Object item) {
		stored = item;
	}

	public Object get() {
		return stored;
	}

	public Object getReverse() {
		return reverser.reverse(stored);
	}

	@Override
	public String toString() {
		return "JavaStorer [stored=" + stored + ", reverser=" + reverser + "]";
	}
	
	
}
