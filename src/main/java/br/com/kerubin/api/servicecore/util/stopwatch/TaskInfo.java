package br.com.kerubin.api.servicecore.util.stopwatch;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TaskInfo {
	
	private final String taskName;
	private final long startTime;
	private long stopTime;
	private boolean stoped;
	
	public long getTimeMillis() {
		return stopTime - startTime;
	}
	
	public double getTimeSeconds() {
		return getTimeMillis() / 1000.0;
	}

}
