package com.liu.design.singleton;

public class ChocolateBoiler2 {

	private boolean empty;
	private boolean boiled;
	
	private static ChocolateBoiler2 uniqueInstance;
	
	private ChocolateBoiler2(){
		empty = true;
		boiled = false;
	}
	
	public static synchronized ChocolateBoiler2 getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new ChocolateBoiler2();
		}
		return uniqueInstance;
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
