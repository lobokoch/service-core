package br.com.kerubin.api.servicecore.util.stopwatch;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StopWatch {
	
	private List<TaskInfo> tasks = new ArrayList<>();
	private int index = -1;
	private TaskInfo task;
	
	public void start() {
		start(UUID.randomUUID().toString());
	}
	
	public void start(String taskName) {
		long time = System.currentTimeMillis();
		task = new TaskInfo(taskName, time, time, false);
		tasks.add(task);
		index++;		
	}
	
	public void stop() {
		long time = System.currentTimeMillis();
		task = tasks.get(index);
		
		if (task.isStoped()) {
			throw new IllegalStateException(MessageFormat.format("Task name: {0} already stopped.", task.getTaskName()));
		}
		
		task.setStopTime(time);
		task.setStoped(true);
		index--;		
	}
	
	public TaskInfo stop(String taskName) {
		long time = System.currentTimeMillis();
		TaskInfo taskToStop = tasks.stream().filter(it -> it.getTaskName().equalsIgnoreCase(taskName)).findFirst().orElse(null);
		if (taskToStop == null) {
			throw new IllegalStateException(MessageFormat.format("Task name: {0} not found to stop.", taskName));
		}
		
		if (taskToStop.isStoped()) {
			throw new IllegalStateException(MessageFormat.format("Task name: {0} already stopped.", task.getTaskName()));
		}
		
		taskToStop.setStopTime(time);
		taskToStop.setStoped(true);
		return taskToStop;
	}
	
	public String currentTaskName() {
		if (task == null) {
			throw new IllegalStateException("No tasks run: can't get last task info.");
		}
		return task.getTaskName();
	}
	
	public long getTimeMillis() {
		if (task == null) {
			throw new IllegalStateException("No tasks run: can't get last task info.");
		}
		return task.getTimeMillis();
	}
	
	public double getTimeSeconds() {
		if (task == null) {
			throw new IllegalStateException("No tasks run: can't get last task info.");
		}
		return task.getTimeSeconds();
	}
	
	public long getTotalTimeMillis() {
		checkAllTaskAreStopped();
		
		return tasks.stream().mapToLong(TaskInfo::getTimeMillis).sum();
	}
	
	public double getTotalTimeSeconds() {
		checkAllTaskAreStopped();
		
		return tasks.stream().mapToDouble(TaskInfo::getTimeSeconds).sum();
	}

	private void checkAllTaskAreStopped() {
		TaskInfo taskNotStoped = tasks.stream().filter(it -> !it.isStoped()).findFirst().orElse(null);
		if (taskNotStoped != null) {
			throw new IllegalStateException(MessageFormat.format("Task: {0} is running yet.", 
					taskNotStoped.getTaskName())) ;
		}
	}
	
	
}
