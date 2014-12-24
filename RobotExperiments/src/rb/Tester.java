package rb;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import rb.ActionController.ActionPoint;
import rb.ActionController.Actions;
import rb.ActionController.PointHolder;

public class Tester {

	public static void main(String[] args) {

		for (int i = 0; i < 50; i++) {
			System.out.println(MouseInfo.getPointerInfo().getLocation());

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<ActionPoint> generateTestActionPoints() {
		List<ActionPoint> aps = new ArrayList<ActionController.ActionPoint>();

		ActionPoint ap1 = new ActionPoint();
		ap1.setName("test 1");
		ap1.addAction(new Actions(Actions.ACTION_CLICK));
		ap1.addPoint(new PointHolder(new Point(200, 200)));
		aps.add(ap1);

		ActionPoint ap2 = new ActionPoint();
		ap2.setName("test 2");
		ap2.addAction(new Actions(Actions.ACTION_TEXT));
		ap2.addPoint(new PointHolder(new Point(300, 200)));
		aps.add(ap2);

		ActionPoint ap3 = new ActionPoint();
		ap3.setName("test 3");
		ap3.addAction(new Actions(Actions.ACTION_RIGHT_CLICK));
		ap3.addPoint(new PointHolder(new Point(300, 200)));
		aps.add(ap3);

		ActionPoint ap4 = new ActionPoint();
		ap4.setName("test 4");
		ap4.addAction(new Actions(Actions.ACTION_CLICK));
		ap4.addPoint(new PointHolder(new Point(300, 200)));
		aps.add(ap4);

		return aps;
	}

	public void test1() {
		System.out.println("test1");
	}

	public void test() {
		System.out.println("test");

	}

	public void test2() {
		System.out.println("kekere");
	}

}
