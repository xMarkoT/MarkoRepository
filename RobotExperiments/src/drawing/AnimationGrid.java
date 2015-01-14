package drawing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

public abstract class AnimationGrid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2107738661861689676L;
	private TimedRepaint timedRepaint;
	private Map<String, Boolean> animationOptions = new HashMap<String, Boolean>();
	protected float alpha;

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
				System.out.print("panel Size: " + getSize().width + " "
						+ getSize().height);
				System.out.println(" frame Size: "
						+ getParent().getParent().getParent().getSize().width
						+ " "
						+ getParent().getParent().getParent().getSize().height);

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

	public abstract void optionsChange();

	protected AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

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

	public Map<String, Boolean> getAnimationOptions() {
		return animationOptions;
	}

	public void setAnimationOptions(Map<String, Boolean> animationOptions) {
		this.animationOptions = animationOptions;
	}

	public static class TimedRepaint implements Runnable {
		private int delay = 2;
		private AnimationGrid animationGrid;
		private boolean running;

		public TimedRepaint(AnimationGrid ag) {
			this.animationGrid = ag;
		}

		boolean delayUp = true;
		int index = 0;

		@Override
		public void run() {
			while (running) {
				getAnimationGrid().setBackground(
						(new Color(random.nextInt(254), random.nextInt(254),
								random.nextInt(254))));
				getAnimationGrid().repaint();
				try {
					setDelay(FGrid.getXFromFibonacci(index));
					Thread.sleep(getDelay());
					if (delayUp) {
						if (index > 0) {
							index--;
						} else {
							delayUp = false;
						}
					} else {
						// 987
						if (FGrid.getXFromFibonacci(index) < 377) {
							index++;
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
