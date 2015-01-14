package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Calendar;

public class FGrid extends AnimationGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2347929027904703894L;

	public static final String OPTION_SQUARES2 = "Squares 2";
	public static final String OPTION_F1 = "F1";
	public static final String OPTION_F2 = "F2";
	public static final String OPTION_F3 = "F3";
	private boolean optionSquares2 = false;
	private boolean optionF1 = true;
	private boolean optionF2 = false;
	private boolean optionF3 = true;
	private boolean optionF4 = true;

	private int animationIndex;
	private boolean animationIndexUp;

	public FGrid() {
		getAnimationOptions().put(OPTION_SQUARES2, optionSquares2);
		getAnimationOptions().put(OPTION_F1, optionF1);
		getAnimationOptions().put(OPTION_F2, optionF2);
		getAnimationOptions().put(OPTION_F3, optionF3);
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent arg0) {
				if (getParent() != null) {
					if (getParent().getParent() != null) {
						if (getParent().getParent().getParent() != null) {
							getParent().getParent().getParent()
									.setSize(626, 669);
						}
					}
				}

			}
		});
	}

	private void procesAnimationIndex() {
		if (animationIndexUp) {
			if (animationIndex < getWidth()) {
				animationIndex++;
			} else {
				animationIndexUp = false;
			}
		} else {
			if (animationIndex > 0) {
				animationIndex--;
			} else {
				animationIndexUp = true;
			}
		}
	}

	@Override
	public void setVisible(boolean arg0) {
		super.setVisible(arg0);
	}

	@Override
	public void drawShape(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawShapeFib(g2d);
		g2d.setBackground(Color.black);
		procesAnimationIndex();

	}

	private int index = 0;
	private Color c = Color.white;
	private boolean cDir;

	public void drawShapeFib(Graphics2D g2d) {
		if (cDir) {
			c = c.brighter();
			if (c.getRGB() == Color.white.getRGB()) {
				cDir = false;
			}
		} else {
			c = c.darker();
			if (c.getRGB() == Color.black.getRGB()) {
				cDir = true;
			}
		}
		index++;
		alpha = 0.21f;
		g2d.setComposite(makeComposite(alpha));
		int width = getWidth();
		int height = getHeight();
		Color clr = Color.white;
		int prevX = 0;
		int fX = 1;
		for (int i = 0; i < width; i++) {
			// currentPositionX = getXFromFibonacci(i);
			int kX = prevX;
			prevX = fX;
			fX = fX + kX;
			if (fX > width) {
				break;
			}

			int prevY = 0;
			int fY = 1;
			for (int j = 0; j < height; j++) {
				// currentPositionY = getXFromFibonacci(j);
				int kY = prevY;
				prevY = fY;
				fY = fY + kY;
				if (fY > height) {
					break;
				}
				g2d.setColor(c);

				g2d.fillRect(0, 0, fX, fY);
				if (optionF4) {

					g2d.setColor(clr);
					if (index % 4 == 0) {
						g2d.drawLine(0, height / 2, (width - fX) / 2,
								height / 2);
						for (int k = 0; k < 5; k++) {
							g2d.drawLine(0, height / 2, (width - fX) / 2,
									(height / 2) + k);
							g2d.drawLine(0, height / 2, (width - fX) / 2,
									(height / 2) - k);
						}
					} else if (index % 3 == 0) {
						g2d.drawLine(width / 2, 0, width / 2, (height - fY) / 2);
						for (int k = 0; k < 5; k++) {
							g2d.drawLine(width / 2, 0, (width / 2) + k,
									(height - fY) / 2);
							g2d.drawLine(width / 2, 0, (width / 2) - k,
									(height - fY) / 2);
						}
					} else if (index % 2 == 0) {
						g2d.drawLine((width / 2) + ((width - fX) / 2),
								height / 2, (width / 2), (height / 2));

						for (int k = 0; k < 5; k++) {

							g2d.drawLine((width / 2) + ((width - fX) / 2),
									height / 2, width / 2, (height / 2) + k);
							g2d.drawLine((width / 2) + ((width - fX) / 2),
									height / 2, (width / 2), (height / 2) - k);
						}
					} else {
						g2d.drawLine(width / 2,
								((height / 2) + (height - fY) / 2), width / 2,
								height / 2);
						for (int k = 0; k < 5; k++) {
							g2d.drawLine(width / 2,
									((height / 2) + (height - fY) / 2),
									(width / 2) + k, height / 2);
							g2d.drawLine(width / 2,
									((height / 2) + (height - fY) / 2),
									(width / 2) - k, height / 2);
						}
					}
					clr = clr.darker();

				}
				if (optionSquares2) {
					g2d.setColor(Color.white);
					g2d.drawRect(0, 0, width - fX, height - fY);
				}
				if (optionF1) {
					int rX = random.nextInt(fX);
					int rY = random.nextInt(fY);
					int rWidth = random.nextInt(fX);
					int rHeight = random.nextInt(fY);
					int rStartAngle = random.nextInt(360);
					int rArcAngle = random.nextInt(360);
					g2d.setColor(Color.white);
					g2d.drawArc(rX, rY, rWidth, rHeight, rStartAngle, rArcAngle);

					for (int k = 1; k < 5; k++) {
						int absVar1 = Math.abs(255 - fX);
						while (absVar1 > 255) {
							absVar1 -= 255;

						}
						Color var1 = new Color(absVar1, absVar1, absVar1);
						int absVar2 = Math.abs(fX - 255);
						while (absVar2 > 255) {
							absVar2 -= 255;

						}
						Color var2 = new Color(absVar2, absVar2, absVar2);

						switch (k) {

						case 1:
							g2d.setColor(var1);
							break;
						case 2:
							g2d.setColor(var1.darker());
							break;
						case 3:
							g2d.setColor(var2);
							break;
						case 4:
							g2d.setColor(var2.darker());
							break;

						default:
							break;
						}
						g2d.drawArc(rX + k, rY + k, rWidth + k, rHeight + k,
								rStartAngle + k, rArcAngle + k);
						absVar1 = Math.abs(255 - fX);
						while (absVar1 > 255) {
							absVar1 -= 255;

						}
						var1 = new Color(absVar1, absVar1, absVar1);
						absVar2 = Math.abs(fX - 255);
						while (absVar2 > 255) {
							absVar2 -= 255;

						}
						var2 = new Color(absVar2, absVar2, absVar2);
						switch (k) {

						case 1:
							g2d.setColor(var1);
							break;
						case 2:
							g2d.setColor(var1.darker());
							break;
						case 3:
							g2d.setColor(var2);
							break;
						case 4:
							g2d.setColor(var2.darker());
							break;

						default:
							break;
						}
						g2d.drawArc(rX - k, rY - k, rWidth - k, rHeight + -k,
								rStartAngle - k, rArcAngle - k);
						g2d.drawLine(rX - k, rY - k, rWidth - k, rHeight + -k);
					}
				}
				if (optionF2) {
					g2d.setColor(Color.white);
					g2d.drawArc(fX + kX, fY - kY, width - fX, height - fY, 0,
							360);
				}
				if (optionF3) {
					g2d.setColor(new Color(random.nextInt(254), random
							.nextInt(254), random.nextInt(254)));
					int chunkX = fX - (fX - kX);
					int chunkY = fY - (fY - kY);
					int xSecond = (int) ((fX - (chunkX / 2)) + chunkX
							* Math.sin(Calendar.getInstance().get(
									Calendar.MILLISECOND)
									* (2 * Math.PI / 1000)));
					int ySecond = (int) (fY - chunkY
							* Math.cos(Calendar.getInstance().get(
									Calendar.MILLISECOND)
									* (2 * Math.PI / 1000)));
					g2d.drawLine(fX, fY, xSecond, ySecond);
					for (int l = 0; l < kX; l++) {
						g2d.setColor(g2d.getColor().brighter());
						g2d.drawLine(fX + l, fY + l, xSecond, ySecond);
						if (g2d.getColor().getRGB() == Color.white.getRGB()) {
							break;
						}
					}
				}

			}

		}

		g2d.setColor(Color.white);
		for (int j = 13; j > 0; j--) {
			g2d.fillOval((width / 2) - j, (height / 2) - j, j * 2, j * 2);
			g2d.setColor(g2d.getColor().darker());

		}

	}

	public static void main(String[] args) {
		System.out.println(getXFromFibonacci(6));
	}

	public static int getXFromFibonacci(int x) {
		int prev = 0;
		int f = 1;
		for (int i = 0; i < x; i++) {
			int k = prev;
			prev = f;
			f = f + k;

		}
		return f;
	}

	@Override
	public void optionsChange() {
		optionSquares2 = getAnimationOptions().get(OPTION_SQUARES2);
		optionF1 = getAnimationOptions().get(OPTION_F1);
		optionF2 = getAnimationOptions().get(OPTION_F2);
		optionF3 = getAnimationOptions().get(OPTION_F3);
	}

}
