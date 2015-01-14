package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Date;
import java.util.Calendar;

public class Animations extends AnimationGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5420009537544443224L;

	@Override
	public void drawShape(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawAnim1(g2d);

	}

	private void drawAnim1(Graphics2D g2d) {
		int chunkSize = 10;
		int chunk = getWidth() / chunkSize;
		int currentPositionX = chunk;
		int positionY = getHeight() / 2;

		int second = Calendar.getInstance().get(Calendar.SECOND);

		while (currentPositionX <= getWidth()) {
			g2d.setColor(Color.black);
			int sLength = (int) ((chunk / 2) * 0.8);
			int xSecond = (int) ((currentPositionX - (chunk / 2)) + sLength
					* Math.sin(getTimedRepaint().getDelay() * (2 * Math.PI / 377)));
			int ySecond = (int) (positionY - sLength
					* Math.cos(getTimedRepaint().getDelay() * (2 * Math.PI / 377)));
			g2d.setColor(Color.red);
			g2d.drawLine((currentPositionX - (chunk / 2)), positionY, xSecond,
					ySecond);
			System.out.println((currentPositionX - (chunk / 2)) + " "
					+ positionY + " " + xSecond + " " + ySecond);

			currentPositionX = currentPositionX + chunk;

		}

	}

	@Override
	public void optionsChange() {
		// TODO Auto-generated method stub

	}

}
