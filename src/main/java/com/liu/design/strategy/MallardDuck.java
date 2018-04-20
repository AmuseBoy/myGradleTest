package com.liu.design.strategy;

/**
 * 绿头鸭
 * @author Administrator
 *
 */
public class MallardDuck extends Duck {

	public MallardDuck() {
		quackBehavior = new Quack();
		flyBehavior = new FlyWithWings();
	}
	
	
	
	public void display() {
		System.out.println("我是一个绿头鸭！");
		
	}

	
}
