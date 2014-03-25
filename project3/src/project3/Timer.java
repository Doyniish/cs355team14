/*
 * Timer - class to time execution of Java code in milliseconds
 *
 * Created on Dec 2, 2003 - Paul J. Wagner
 */
package project3;

import java.util.Date;

/**
 * @author WAGNERPJ
 */
public class Timer {
	private long startTime;			// start time in absolute milliseconds
	private long stopTime;			// stop time in absolute milliseconds
	private long totalTime;			// difference of stop and start time
	
	// --- default constructor
	public Timer() {
		startTime = 0;
		stopTime = 0;
		totalTime = 0;
	}
	
	// --- startTimer - get a starting time
	void startTimer() {
		Date now = new Date();
		startTime = now.getTime();
	}

	// --- stopTimer - get an ending time
	void stopTimer() {
		Date now = new Date();
		stopTime = now.getTime();
	}

	// --- getTotal - return the elapsed time
	long getTotal() {
		totalTime = 0;
		if (stopTime > startTime) {
			totalTime = stopTime - startTime;
		}
		return totalTime;
	}
}	// end - class Timer
