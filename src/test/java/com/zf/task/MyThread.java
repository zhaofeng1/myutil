package com.zf.task;

/**
 * 线程
 * 
 * @author ZhaoFeng
 * @date 2017年1月21日
 */
public class MyThread implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		//		try {
		//			Thread.sleep(10 * 1000);
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

}
