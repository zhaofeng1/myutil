package com.zf.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程锁 以及condition 用法
 * @author ZhaoFeng
 * @date 2017年4月13日
 */
public class ThreadLockTest {

	private static Lock lock = new ReentrantLock();
	//	private static Condition notEmpty = lock.newCondition();
	//	private static Condition notFull = lock.newCondition();
	private static boolean flag = false;

	public static void main(String[] args) {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						System.out.println("===thread1 start=====");
						Thread.sleep(1 * 1000);
						System.out.println("===thread1 end===");
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					try {
						Thread.sleep(2 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						System.out.println("===thread2 start=====");
						Thread.sleep(10 * 1000);
						System.out.println("===thread2 end===");
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();

	}
}
