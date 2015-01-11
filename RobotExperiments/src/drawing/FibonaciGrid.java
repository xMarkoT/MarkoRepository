package drawing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class FibonaciGrid extends AnimationGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4436155640599290644L;
	private boolean randomColor;

	public FibonaciGrid() {
	}

	public void drawShapeRandomColor(Graphics2D g2d) {

		float alpha = 0.20f;

		// g2d.set opacity
		int width = getWidth();
		int height = getHeight();
		double currentPositionWidth = width;
		g2d.setComposite(makeComposite(alpha));

		g2d.setColor(new Color(random.nextInt(254), random.nextInt(254), random
				.nextInt(254)));
		while ((currentPositionWidth = (currentPositionWidth / 2)) > 1) {
			g2d.setColor(new Color(random.nextInt(254), random.nextInt(254),
					random.nextInt(254)));
			g2d.fillRect(0, 0, (int) Math.round(currentPositionWidth), height);

		}
		g2d.setColor(new Color(random.nextInt(254), random.nextInt(254), random
				.nextInt(254)));
		double currentPositionHeight = height;
		while ((currentPositionHeight = (currentPositionHeight / 2)) > 1) {
			g2d.fillRect(0, 0, (int) width,
					(int) Math.round(currentPositionHeight));
			g2d.setColor(new Color(random.nextInt(254), random.nextInt(254),
					random.nextInt(254)));
		}

	}

	public void drawShapeFibonaciColor(Graphics2D g2d) {

		float alpha = 0.20f;

		// g2d.set opacity
		int width = getWidth();
		int height = getHeight();
		double currentPositionWidth = width;
		g2d.setComposite(makeComposite(alpha));

		Color color = Color.black;
		boolean up = true;
		while ((currentPositionWidth = (currentPositionWidth / 2)) > 1) {
			if (up) {
				if (color.getRGB() / 2 <= Color.white.getRGB()) {
					color = new Color(color.getRGB() / 2);
				} else {
					up = false;
				}
			} else {
				if (color.getRGB() * 2 >= Color.black.getRGB()) {
					color = new Color(color.getRGB() * 2);
				} else {
					up = true;
				}
			}
			g2d.setColor(color);
			g2d.fillRect(0, 0, (int) Math.round(currentPositionWidth), height);

		}
		color = Color.black;
		up = true;
		double currentPositionHeight = height;
		while ((currentPositionHeight = (currentPositionHeight / 2)) > 1) {
			if (up) {
				if (color.getRGB() / 2 <= Color.white.getRGB()) {
					color = new Color(color.getRGB() / 2);
				} else {
					up = false;
				}
			} else {
				if (color.getRGB() * 2 >= Color.black.getRGB()) {
					color = new Color(color.getRGB() * 2);
				} else {
					up = true;
				}
			}
			g2d.setColor(color);
			g2d.fillRect(0, 0, (int) width,
					(int) Math.round(currentPositionHeight));
		}

	}

	public void drawShape(Graphics2D g2d) {
		if (randomColor) {
			drawShapeRandomColor(g2d);
		} else {
			drawShapeFibonaciColor(g2d);
		}
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

}
