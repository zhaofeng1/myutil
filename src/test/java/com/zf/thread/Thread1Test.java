package com.zf.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程锁 以及condition 用法
 * @author ZhaoFeng
 * @date 2017年4月13日
 */
public class Thread1Test {

	private static Lock lock = new ReentrantLock();
	private static Condition notEmpty = lock.newCondition();
	//	private static Condition notFull = lock.newCondition();
	private static boolean flag = false;

	public static void main(String[] args) {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						System.out.println("t====flag:" + flag);
						if (!flag) {
							System.out.println("t进入等待");
							//							notFull.await();
							notEmpty.await();
						}
						System.out.println("t====flag1:" + flag);
						System.out.println("t======handle start");
						Thread.sleep(2 * 1000);
						System.out.println("t======handle end");
						flag = false;
						notEmpty.signal();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
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
						System.out.println("t1====flag:" + flag);
						if (flag) {
							System.out.println("t1进入等待");
							notEmpty.await();
						}
						System.out.println("t1====flag1:" + flag);
						System.out.println("t1======handle start");
						Thread.sleep(5 * 1000);
						System.out.println("t1======handle end");
						flag = true;
						//						notFull.signal();
						notEmpty.signal();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		};
		t1.start();

	}
}
