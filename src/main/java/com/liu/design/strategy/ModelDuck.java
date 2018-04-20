package com.liu.design.strategy;

/**
 * 模型鸭
 * @author Administrator
 *
 */
public class ModelDuck extends Duck {

	public ModelDuck(){
		flyBehavior = new FlyNoWay();
		quackBehavior = new Quack();
	}
	
	public void display() {
		System.out.println("我是一个模型鸭！");
	}

	
}
