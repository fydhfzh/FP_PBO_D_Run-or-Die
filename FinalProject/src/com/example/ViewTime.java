package com.example;


public class ViewTime {
	int minutes, seconds;
	
	public ViewTime() {
		minutes = 1;
		seconds = 00;
	}
	
	public void decrementSecond() {
		if(seconds > 0) seconds--;
		else{
			decrementMinutes();
			seconds = 59;
		}
		
	}
	
	public void decrementMinutes() {
		if(minutes > 0)
		minutes--;
	}
	
	public String getStringMinutes() {
		return Integer.toString(minutes);
	}
	
	public String getStringSecond() {
		return Integer.toString(seconds);
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public int getMinutes() {
		return minutes;
	}
}
