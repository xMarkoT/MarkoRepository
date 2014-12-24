package rb;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import rb.ActionController.Actions;
import rb.gui.ActionTypeName;

public class ActionController {

	private List<ActionPoint> aps;
	private ActionPoint currentActionPoint;
	private List<ActionControllerChangeListener> changeListeners;

	public List<ActionPoint> getAps() {
		if (aps == null) {
			aps = new ArrayList<ActionController.ActionPoint>();
		}
		return aps;
	}

	public void setAps(List<ActionPoint> aps) {
		this.aps = aps;
	}

	public ActionPoint getCurrentActionPoint() {
		return currentActionPoint;
	}

	public void setCurrentActionPoint(ActionPoint currentActionPoint) {
		this.currentActionPoint = currentActionPoint;
		updateChanngeListeners();
	}

	public List<ActionControllerChangeListener> getChangeListeners() {
		if (changeListeners == null) {
			changeListeners = new ArrayList<ActionControllerChangeListener>();
		}
		return changeListeners;
	}

	public void addChangeListener(ActionControllerChangeListener accl) {
		if (!getChangeListeners().contains(accl)) {
			getChangeListeners().add(accl);
		}
	}

	private void updateChanngeListeners() {
		for (ActionControllerChangeListener accl : getChangeListeners()) {
			accl.updateAll();
		}
	}

	public void setChangeListeners(
			List<ActionControllerChangeListener> changeListeners) {
		this.changeListeners = changeListeners;
	}

	public static class ActionPoint {
		private String name;
		private int actionIndex = 0;
		private List<PointHolder> points;
		private List<Actions> actions;
		private int duration;
		private Robot robot;

		public void doAction() {
			while (next()) {
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
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

		@ActionTypeName(name = "Click", value = 0)
		public static final int ACTION_CLICK = 0;;
		@ActionTypeName(name = "Text", value = 1)
		public static final int ACTION_TEXT = 1;
		@ActionTypeName(name = "Right Click", value = 3)
		public static final int ACTION_RIGHT_CLICK = 3;
		private static List<ActionTypeName> atns;
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

		public static Actions getFromValue(int value) {
			return new Actions(value);
		}

		private static List<ActionTypeName> getAtns() {
			if (atns == null) {
				atns = new ArrayList<ActionTypeName>();
				for (Field field : Actions.class.getFields()) {
					Annotation[] annotations = field.getAnnotations();

					for (Annotation annotation : annotations) {
						if (annotation instanceof ActionTypeName) {
							atns.add((ActionTypeName) annotation);
						}
					}
				}
			}
			return atns;
		}
	}
}
