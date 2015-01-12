package drawing;

import java.awt.Graphics2D;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

public class FGrid extends AnimationGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2347929027904703894L;

	public FGrid() {
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

	@Override
	public void setVisible(boolean arg0) {
		super.setVisible(arg0);
	}

	@Override
	public void drawShape(Graphics2D g2d) {
		drawShapeFib(g2d);
	}

	public void drawShapeFib(Graphics2D g2d) {
		alpha = 0.20f;
		g2d.setComposite(makeComposite(alpha));
		int width = getWidth();
		int height = getHeight();
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
				g2d.fillRect(0, 0, fX, fY);
			}
		}

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
		// TODO Auto-generated method stub

	}

}
