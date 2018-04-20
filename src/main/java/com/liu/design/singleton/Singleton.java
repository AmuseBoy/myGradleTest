package com.liu.design.singleton;

/**
 * 单例模式
 * @author Administrator
 *
 */
public class Singleton {

	//同一时间，只能有一个线程调用
	
//	private static Singleton uniqueSingleton;
//	
//	private Singleton(){
//
//	}
//	
//	public static synchronized Singleton getInstance(){
//		if(uniqueSingleton == null){
//			uniqueSingleton = new Singleton();
//		}
//		return uniqueSingleton;
//	}
	
	
	//一开始就创建
	
//	private static Singleton uniqueSingleton = new Singleton();
//	
//	private Singleton(){
//
//	}
//	
//	public static Singleton getInstance(){
//		return uniqueSingleton;
//	}
	
	
	
	
	
	private volatile static Singleton uniqueSingleton;
	
	private Singleton(){

	}
	
	public static Singleton getInstance(){
		if(uniqueSingleton == null){
			synchronized (Singleton.class) {
				if(uniqueSingleton == null){
					uniqueSingleton = new Singleton();
				}
			}
		}
		return uniqueSingleton;
	}
	
	
	
	
}
