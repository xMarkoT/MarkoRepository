package rb;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

public class ActionController {

	public static class ActionPoint {
		private int actionIndex = 0;
		private List<PointHolder> points;
		private List<Actions> actions;
		private Robot robot;

		public boolean next() {
			if (getPoints().size() <= getActionIndex()) {
				return false;
			}
			System.out.println("move");
			getRobot().mouseMove(
					getPoints().get(getActionIndex()).getPoint().x,
					getPoints().get(getActionIndex()).getPoint().y);
			switch (getActions().get(getActionIndex()).getActionType()) {
			case 0:
				System.out.println("press");
				getRobot().mousePress(InputEvent.BUTTON1_DOWN_MASK);
				getRobot().mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				getRobot().mouseWheel(-20);
				break;
			case 2:

				break;
			default:
				break;
			}
			actionIndex++;
			System.out.println(actionIndex);
			return true;
		}

		public int getActionIndex() {
			return actionIndex;
		}

		public List<PointHolder> getPoints() {
			if (points == null) {
				points = new ArrayList<PointHolder>();
			}
			return points;
		}

		public void setPoints(List<PointHolder> points) {

			this.points = points;
		}

		public List<Actions> getActions() {
			if (actions == null) {
				actions = new ArrayList<Actions>();
			}
			return actions;
		}

		public void setActions(List<Actions> actions) {
			this.actions = actions;
		}

		public void addPoint(PointHolder point) {
			if (!getPoints().contains(point)) {
				getPoints().add(point);
			}
		}

		public void addAction(Actions action) {
			if (!getActions().contains(action)) {
				getActions().add(action);
			}
		}

		public void removePoint(PointHolder point) {
			if (getPoints().contains(point)) {
				getPoints().remove(point);
			}
		}

		public void removeAction(Actions action) {
			if (getActions().contains(action)) {
				getActions().remove(action);
			}
		}

		public Robot getRobot() {
			if (robot == null) {
				try {
					robot = new Robot();
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
			return robot;
		}

		public void setRobot(Robot robot) {
			this.robot = robot;
		}

	}

	public static class PointHolder {
		private Point point;

		public PointHolder(Point point) {
			this.point = point;
		}

		public Point getPoint() {
			return point;
		}

		public void setPoint(Point point) {
			this.point = point;
		}
	}

	public static class Actions {

		public static final int ACTION_CLICK = 0;;
		public static final int ACTION_TEXT = 1;
		private String text;
		private int actionType = 0;

		public Actions(int actionType) {
			this.actionType = actionType;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getActionType() {
			return actionType;
		}

		public void setActionType(int actionType) {
			this.actionType = actionType;
		}
	}
}
