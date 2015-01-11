package drawing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class FibonaciGrid extends AnimationGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4436155640599290644L;
	private boolean randomColor;

	private static final String OPTION_OVALS1 = "Ovals 1";
	private static final String OPTION_OVALS2 = "Ovals 2";
	private static final String OPTION_SPIRALS = "Spirals";

	public FibonaciGrid() {
		getAnimationOptions().put(OPTION_OVALS1, true);
		getAnimationOptions().put(OPTION_OVALS2, true);
		getAnimationOptions().put(OPTION_SPIRALS, true);
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

	public void drawOvals1(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g2d.setColor(Color.black);
		int width = getWidth();
		int height = getHeight();
		double currentPositionWidth = width;
		Color color = Color.white;
		boolean up = true;
		while ((currentPositionWidth = (currentPositionWidth / 2)) > 1) {
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

				g2d.fillOval((int) Math.round(currentPositionWidth),
						(int) Math.round(currentPositionHeight),
						(int) Math.round(currentPositionWidth) * 2,
						(int) Math.round(currentPositionHeight) * 2);

			}

		}
	}

	public void drawOvals2(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g2d.setColor(Color.black);
		int width = getWidth();
		int height = getHeight();
		double currentPositionWidth = width;
		Color color = Color.white;
		boolean up = true;
		while ((currentPositionWidth = (currentPositionWidth / 2)) > 1) {
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

				g2d.fillOval((int) Math.round(currentPositionWidth) * 2,
						(int) Math.round(currentPositionHeight) * 2,
						(int) Math.round(currentPositionWidth),
						(int) Math.round(currentPositionHeight));

			}

		}
	}

	public void drawSpirals(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g2d.setColor(Color.black);
		int width = getWidth();
		int height = getHeight();
		double currentPositionWidth = width;
		Color color = Color.white;
		boolean up = true;
		while ((currentPositionWidth = (currentPositionWidth / 2)) > 1) {
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

				g2d.fillOval((int) Math.round(currentPositionWidth) * 2,
						(int) Math.round(currentPositionHeight) * 2,
						(int) Math.round(currentPositionWidth),
						(int) Math.round(currentPositionHeight));

			}

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
		if (getAnimationOptions().containsKey(OPTION_OVALS1)
				&& getAnimationOptions().get(OPTION_OVALS1)) {
			drawOvals1(g2d);
		}
		if (getAnimationOptions().containsKey(OPTION_OVALS2)
				&& getAnimationOptions().get(OPTION_OVALS2)) {
			drawOvals2(g2d);
		}
		if (getAnimationOptions().containsKey(OPTION_SPIRALS)
				&& getAnimationOptions().get(OPTION_SPIRALS)) {
			drawSpirals(g2d);
		}
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	@Override
	public void optionsChange() {
		// TODO Auto-generated method stub

	}

}
