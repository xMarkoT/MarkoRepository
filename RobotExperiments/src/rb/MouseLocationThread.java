package rb;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.List;

public class MouseLocationThread implements Runnable {

	private List<MouseLocationListener> listeners;
	private Thread thread;
	private boolean isRunning;
	private int delayMils = 500;

	public void start() {
		stop();
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (thread != null) {
			thread = null;
		}
	}

	public void addListener(MouseLocationListener listener) {
		if (!getListeners().contains(listener)) {
			getListeners().add(listener);
		}
	}

	private void updateListeners(Point point) {
		for (MouseLocationListener listener : listeners) {
			listener.updateLocation(point);
		}
	}

	public void run() {
		while (isRunning) {
			PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			updateListeners(pointerInfo.getLocation());
			try {
				Thread.sleep(getDelayMils());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<MouseLocationListener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<MouseLocationListener>();
		}
		return listeners;
	}

	public void setListeners(List<MouseLocationListener> listeners) {
		this.listeners = listeners;
	}

	public int getDelayMils() {
		return delayMils;
	}

	public void setDelayMils(int delayMils) {
		this.delayMils = delayMils;
	}

}
