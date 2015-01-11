package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

import javax.swing.JPanel;

public abstract class AnimationGrid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2107738661861689676L;
	private TimedRepaint timedRepaint;

	public AnimationGrid() {
		setTimedRepaint(new TimedRepaint(this));
		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				repaint();

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paint(arg0);
		// setBackground(Color.black);
		drawShape((Graphics2D) arg0);
	}

	static Random random = new Random();

	public abstract void drawShape(Graphics2D g2d);

	public TimedRepaint getTimedRepaint() {
		return timedRepaint;
	}

	public void setTimedRepaint(TimedRepaint timedRepaint) {
		this.timedRepaint = timedRepaint;
	}

	public static void main(String[] args) {
		System.out.println(Color.white.getRGB());
		System.out.println(Color.black.getRGB());
		Color color = Color.black;
		int currentRGB = color.getRGB();
		int count = 0;
		while ((currentRGB = color.getRGB() / 2) <= Color.white.getRGB()) {
			count++;
			color = new Color(currentRGB);
			System.out.println(color.getRed() + " " + color.getGreen() + " "
					+ color.getBlue());

		}
		System.out.println(count);
	}

	public static class TimedRepaint implements Runnable {
		private int delay = 2;
		private AnimationGrid animationGrid;
		private boolean running;

		public TimedRepaint(AnimationGrid ag) {
			this.animationGrid = ag;
		}

		boolean delayUp = true;

		@Override
		public void run() {
			while (running) {
				getAnimationGrid().setBackground(
						(new Color(random.nextInt(254), random.nextInt(254),
								random.nextInt(254))));
				getAnimationGrid().repaint();
				try {
					Thread.sleep(getDelay());
					if (delayUp) {
						if (getDelay() / 2 >= 2) {
							setDelay(getDelay() / 2);
						} else {
							delayUp = false;
						}
					} else {
						if (getDelay() * 2 <= 1024) {
							setDelay(getDelay() * 2);
						} else {
							delayUp = true;
						}
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public void start() {
			running = true;
			new Thread(this).start();
		}

		public void stop() {
			running = false;
		}

		public int getDelay() {
			return delay;
		}

		public void setDelay(int delay) {
			this.delay = delay;
		}

		public AnimationGrid getAnimationGrid() {
			return animationGrid;
		}

		public void setAnimationGrid(AnimationGrid animationGrid) {
			this.animationGrid = animationGrid;
		}
	}

}
