package model;

import javafx.scene.control.ProgressBar;

public class ShowProgressBar extends Thread {
	private ProgressBar bar;
	private float step;
	
	public ShowProgressBar(ProgressBar bar, int fractions) {
		this.bar = bar;
		this.step = 1/fractions;
	}
	
	@Override
	public void run() {
		ProgressBar progress = new ProgressBar(0);
		progress.setProgress(0);
	}
	
	public void step(int counter) {
		bar.setProgress(step * counter);
	}
}
