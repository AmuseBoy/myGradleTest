package com.liu.design.singleton;

public class ChocolateBoiler {

	private boolean empty;
	private boolean boiled;
	
	public ChocolateBoiler(){
		empty = true;
		boiled = false;
	}
	
	//加原料
	public void fill(){
		if(isEmpty()){
			System.out.println("加原料");
			empty = false;
			boiled = false;
		}
	}
	
	//煮沸
	public void boil(){
		if(!isEmpty() && !isBoiled()){
			System.out.println("煮沸");
			boiled = true;
		}
	}
	
	//排出成品
	public void drain(){
		if(!isEmpty() && isBoiled()){
			System.out.println("排出成品");
			empty = true;
		}
	}
	
	public boolean isEmpty(){
		return empty;
	}
	
	public boolean isBoiled(){
		return boiled;
	}
}
