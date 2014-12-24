package rb;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.ActionController.ActionPoint;
import rb.ActionController.Actions;
import rb.ActionController.PointHolder;

public class RBFileUtil {

	public static List<ActionPoint> loadFromDir(File dir) {
		if (dir.isDirectory()) {
			return loadFromDir(dir.listFiles());
		} else {
			List<ActionPoint> aps = new ArrayList<ActionController.ActionPoint>();
			List<ActionPoint> ap = loadFromFile(dir);
			if (ap != null) {
				aps.addAll(ap);
				return aps;
			}
		}
		return null;
	}

	private static List<ActionPoint> loadFromFile(File file) {
		List<ActionPoint> aps = new ArrayList<ActionController.ActionPoint>();
		String json = readLine(file);
		JSONObject root = new JSONObject(json);
		JSONArray apsArray = root.getJSONArray("aps");
		for (int i = 0; i < apsArray.length(); i++) {
			ActionPoint ap = new ActionPoint();
			JSONObject apObject = apsArray.getJSONObject(i);
			ap.setDuration(Integer.valueOf(unwrappedJsonString(apObject
					.get("duration"))));
			ap.setName(escapeQuotes(unwrappedJsonString(apObject.get("name"))));
			List<Actions> actions = new ArrayList<ActionController.Actions>();
			JSONArray actionsArray = apObject.getJSONArray("actions");
			System.out.println(actionsArray.length());
			for (int j = 0; j < actionsArray.length(); j++) {
				JSONObject actionObject = new JSONObject(unwrappedJsonString(actionsArray.get(j)));
				Actions a = new Actions(
						(Integer) actionObject.get("actionType"));
				//a.setText((String) actionObject.get("text"));
				actions.add(a);
			}
			ap.setActions(actions);
			List<PointHolder> points = new ArrayList<ActionController.PointHolder>();
			JSONArray pointsArray = apObject.getJSONArray("points");
			for (int j = 0; j < pointsArray.length(); j++) {
				JSONObject pointObject = new JSONObject(unwrappedJsonString(pointsArray.get(j).toString()));
				points.add(new PointHolder(new Point(new Integer(
						(Integer) pointObject.get("x")), new Integer(
						(Integer) pointObject.get("y")))));
			}
			ap.setPoints(points);
			aps.add(ap);
		}

		return aps;
	}

	private static String unwrappedJsonString(Object object) {
		return object.toString().replaceAll(Pattern.quote("["), "")
				.replaceAll(Pattern.quote("]"), "");
	}
	
	private static String escapeQuotes(String source){
		return source.replaceAll("\"", "");
	}

	private static String readLine(File file) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			try {
				return br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<ActionPoint> loadFromDir(File[] files) {
		List<ActionPoint> aps = new ArrayList<ActionController.ActionPoint>();
		for (File file : files) {
			aps.addAll(loadFromFile(file));
		}
		return aps;
	}

	private static JSONObject getJsonObject(ActionController actionController,
			File file) {
		JSONObject root = new JSONObject();
		root.append("name", file.getName());
		JSONArray aps = null;
		for (ActionPoint ap : actionController.getAps()) {
			if (aps == null) {
				aps = new JSONArray();
			}
			JSONObject apObject = new JSONObject();
			apObject.append("actionIndex", ap.getActionIndex());
			apObject.append("duration", ap.getDuration());
			apObject.append("name", ap.getName());

			JSONArray points = new JSONArray();
			for (PointHolder ph : ap.getPoints()) {
				JSONObject pointObject = new JSONObject();
				pointObject.append("x", ph.getPoint().x);
				pointObject.append("y", ph.getPoint().y);
				points.put(pointObject);
			}
			apObject.append("points", points);

			JSONArray actions = new JSONArray();
			for (Actions a : ap.getActions()) {
				JSONObject actionObject = new JSONObject();
				actionObject.append("actionType", a.getActionType());
				actionObject.append("text", a.getText());
				actions.put(actionObject);
			}
			apObject.append("actions", actions);

			aps.put(apObject);
		}

		if (aps != null) {
			root.put("aps", aps);
		}

		return root;
	}

	public static void saveToFile(File file, ActionController actionController) {
		file.delete();
		file = new File(file.getAbsolutePath() + ".rbt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(file);
			System.out
					.println(getJsonObject(actionController, file).toString());
			fileWriter.write(getJsonObject(actionController, file).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
