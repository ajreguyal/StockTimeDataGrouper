package com.ajreguyal.builder;

import com.ajreguyal.model.Candle;

public class CandleBuilder {
	private float low = 0;
	private float high = 0;
	private float open = 0;
	private float close = 0;
	private float volume = 0;
	
	public CandleBuilder() {
		
	}
	
	public CandleBuilder setVolume(String volume) {
		try {
			setVolume(Float.parseFloat(volume));
		} catch (NumberFormatException ex) {
			System.out.println("invalid number volume-input: " + volume);
		}
		return this;
	}
	
	public CandleBuilder setVolume(float volume) {
		this.volume = volume;
		return this;
	}
	
	public CandleBuilder setLow(String price) {
		try {
			setLow(Float.parseFloat(price));
		} catch (NumberFormatException ex) {
			System.out.println("invalid number low-input: " + price);
		}
		return this;
	}
	
	public CandleBuilder setLow(float low) {
		this.low = low;
		return this;
	}
	
	public CandleBuilder setHigh(String price) {
		try {
			setHigh(Float.parseFloat(price));
		} catch (NumberFormatException ex) {
			System.out.println("invalid number high-input: " + price);
		}
		return this;
	}
	
	public void setHigh(float high) {
		this.high = high;
	}
	
	public CandleBuilder setOpen(String price) {
		try {
			setOpen(Float.parseFloat(price));
		} catch (NumberFormatException ex) {
			System.out.println("invalid number open-input: " + price);
		}
		return this;
	}

	public void setOpen(float open) {
		this.open = open;
	}
	
	public CandleBuilder setClose(String price) {
		try {
			setClose(Float.parseFloat(price));
		} catch (NumberFormatException ex) {
			System.out.println("invalid number close-input: " + price);
		}
		return this;
	}

	public void setClose(float close) {
		this.close = close;
	}
	
	public Candle build() {
		Candle candle = null;
		if (isAllDataValid()) {
			candle = new Candle();
			candle.setLow(low);
			candle.setOpen(open);
			candle.setHigh(high);
			candle.setClose(close);
			candle.setVolume(volume);
		}
		return candle;
	}

	private boolean isAllDataValid() {
		return (open > 0)
				&& (close > 0)
				&& (high > 0)
				&& (low > 0);
	}
}
