package simulation;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class Model extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6513635204698397797L;

	private boolean alive;
	private boolean clone;
	private Point point;

	public Model(Point point, final WorldFrame worldFrame) {
		setPoint(point);
		setAlive(false);
		setOpaque(!isOpaque());
		addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseClicked(MouseEvent arg0) {

				if (arg0.getButton() == MouseEvent.BUTTON2
						|| arg0.getButton() == MouseEvent.BUTTON3) {
					System.out.println(AnimationThread.getNumAliveNeighbor(
							getPoint().x, getPoint().y, worldFrame));
				} else {
					setAlive(!isAlive());
					repaint();
				}

			}
		});
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
		setBackground(isAlive() ? Color.yellow : Color.gray);
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

}
