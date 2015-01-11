package drawing;

import java.awt.Color;
import java.awt.Graphics2D;

public class OneThirdGrid extends AnimationGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8238330827870822609L;

	public OneThirdGrid() {
	}

	public void drawShape(Graphics2D g2d) {

		int width = getWidth();
		int height = getHeight();
		double currentPositionWidth = width;
		g2d.setColor(Color.black);
		while ((currentPositionWidth = (currentPositionWidth / 3) * 2) > 2) {
			g2d.fillRect(0, 0, (int) Math.round(currentPositionWidth), height);
			g2d.setColor(new Color(random.nextInt(254), random.nextInt(254),
					random.nextInt(254)));

		}
		g2d.setColor(Color.white);
		double currentPositionHeight = height;
		while ((currentPositionHeight = (currentPositionHeight / 3) * 2) > 2) {
			g2d.fillRect(0, 0, (int) width,
					(int) Math.round(currentPositionHeight));
			g2d.setColor(new Color(random.nextInt(254), random.nextInt(254),
					random.nextInt(254)));
		}

	}

	@Override
	public void optionsChange() {
		// TODO Auto-generated method stub

	}

}
