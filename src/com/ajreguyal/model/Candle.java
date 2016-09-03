package com.ajreguyal.model;

public class Candle {
	private float low;
	private float high;
	private float open;
	private float close;
	private float volume;
	
	public Candle() {
		
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public float getVolume() {
		return this.volume;
	}
	
	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}
	
	@Override
	public String toString() {
		return "Open: " + getOpen() + ". Close:" + getClose()
			+ ". High: " + getHigh() + ". Low: " + getLow() + ". Volume: " + getVolume();
	}
}
