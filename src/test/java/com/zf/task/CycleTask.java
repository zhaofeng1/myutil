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

	public void startTask() {
		if (executorService == null) {
			executorService = Executors.newScheduledThreadPool(1);
		}
		executorService.scheduleAtFixedRate(new MyThread(), 3, 3, TimeUnit.SECONDS);

	}

	public static void main(String[] args) {
		CycleTask task = new CycleTask();
		task.startTask();

		try {
			Thread.sleep(5 * 60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
