package com.liu.design.singleton;

public class Test {

	
	public static void main(String[] args) {
		ChocolateBoiler boiler = new ChocolateBoiler();
		ChocolateBoiler boiler2 = new ChocolateBoiler();
		boiler2.fill();
		boiler.fill();
		boiler2.boil();
		boiler.boil();
		boiler.drain();
		boiler2.drain();
	}

	
}
