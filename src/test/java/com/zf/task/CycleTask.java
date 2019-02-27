package com.zf.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 周期任务
 * 
 * @author ZhaoFeng
 * @date 2017年1月21日
 */
public class CycleTask {

	// 周期任务执行器
	private ScheduledExecutorService executorService;

	/**
	 * 开始任务2
	 */
	public void startTask() {
		if (executorService == null) {
			executorService = Executors.newScheduledThreadPool(1);
		}
		long interval = 60 * 60 * 1000;//1 小时
		long delay = 0;
		executorService.scheduleAtFixedRate(new MyThread(), 3, 1, TimeUnit.MILLISECONDS);


	}

	public long getDelay() {

		return 0;
	}

	public static void main(String[] args) {
		CycleTask task = new CycleTask();
		task.startTask();

		try {
			Thread.sleep(100 * 60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
