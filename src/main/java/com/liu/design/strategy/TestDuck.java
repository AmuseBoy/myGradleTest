package com.liu.design.strategy;

public class TestDuck {

	
	public static void main(String[] args) {
//		Duck mallard = new MallardDuck();
//		mallard.performFly();
//		mallard.performQuack();
//		mallard.flyBehavior.fly();
//		mallard.quackBehavior.quack();
		
		Duck model = new ModelDuck();
		model.performFly();
		model.setFlyBehavior(new FlyRocketPowered());
		model.performFly();
		
	}
}
