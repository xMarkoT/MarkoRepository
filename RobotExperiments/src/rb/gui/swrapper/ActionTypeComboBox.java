package rb.gui.swrapper;

import java.awt.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import rb.ActionController;
import rb.ActionController.ActionPoint;
import rb.ActionController.Actions;
import rb.gui.ActionTypeName;

public class ActionTypeComboBox extends JComboBox<Actions> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1786028112573671066L;
	private ActionController actionController;

	public ActionTypeComboBox(ActionController actionController) {
		this.actionController = actionController;
		setModel(new ActionTypeComboBoxModel(actionController));
		setRenderer(new ActionTypeComboRenderer());
	}

	public static class ActionTypeComboRenderer extends DefaultListCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4570589939555080669L;

		public ActionTypeComboRenderer() {
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// I know DefaultListCellRenderer always returns a JLabel
			// super setups up all the defaults
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);

			// "value" is whatever object you put into the list, you can use it
			// however you want here

			// I'm going to prefix the label text to demonstrate the point

			if (index < 0) {
				label.setText(getTextFromActionType(((Actions) list.getModel()
						.getElementAt(list.getSelectedIndex()))));
			} else {
				label.setText(getTextFromActionType(((Actions) list.getModel()
						.getElementAt(index))));
			}

			return label;

		}

		private List<ActionTypeName> atns;

		private List<ActionTypeName> getAtns() {
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

		private String getTextFromActionType(Actions a) {

			if (a != null) {
				List<ActionTypeName> atns2 = getAtns();
				for (ActionTypeName atn : atns2) {
					if (atn.value() == a.getActionType()) {
						return atn.name();
					}
				}
			}
			return "no action selected " + a;
		}
	}

	public static class ActionTypeComboBoxModel extends
			DefaultComboBoxModel<Actions> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -907627845845105249L;
		private List<ActionTypeName> atns;

		public ActionTypeComboBoxModel(ActionController actionController) {
			for (ActionTypeName a : getAtns()) {
				addElement(Actions.getFromValue(a.value()));
			}
		}
		
		private List<ActionTypeName> getAtns() {
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
