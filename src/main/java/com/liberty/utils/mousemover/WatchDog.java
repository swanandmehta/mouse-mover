/**
 * 
 */
package com.liberty.utils.mousemover;

import java.awt.MouseInfo;
import java.awt.Robot;

/**
 * @author swanandm
 *
 */
public class WatchDog implements Runnable {
	
	private static volatile boolean isOpen = true;
	private static Thread instance;
	private final Robot robot;
	
	private WatchDog(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		while(isOpen) {
			try {
				int posX = MouseInfo.getPointerInfo().getLocation().x;
				int posY = MouseInfo.getPointerInfo().getLocation().y;
				robot.mouseMove(posX + 1, posY + 1);
				Thread.sleep(1000);				
			} catch (InterruptedException e) {
				WatchDog.isOpen = false;
			}
		}
	}

	public synchronized static Thread getThread(Robot robot) {
		if(WatchDog.instance == null) {
			WatchDog.instance = new Thread(new WatchDog(robot)); 			
			WatchDog.instance.setName("Watchdog");
			WatchDog.isOpen = true;
		}

		return WatchDog.instance;
	}
	
	
	public synchronized static boolean destroy(){
		if(WatchDog.instance == null) {
			return false;
		}else {
			WatchDog.isOpen = false;			
			WatchDog.instance.interrupt();
			WatchDog.instance = null;
		}
		
		return true;
	}
}
