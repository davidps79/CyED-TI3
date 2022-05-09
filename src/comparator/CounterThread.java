package comparator;

import controller.MenuViewController;

public class CounterThread extends Thread {
	private MenuViewController controller;
	
	public CounterThread(MenuViewController controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		try {
			sleep(1000);
			if (!controller.getFlag())
				controller.showProgressBar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
