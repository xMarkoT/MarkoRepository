package rb.gui.swrapper;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import rb.ActionController;
import rb.ActionController.ActionPoint;

public class ActionList extends JList<ActionPoint> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1851254462281498900L;
	private ActionController actionController;

	public ActionList(ActionController actionController) {
		this.actionController = actionController;
		setModel(new ActionPointListModel(actionController.getAps()));
		setCellRenderer(new ActionPointListCellRenderer());
		addListeners();
	}

	public void refresh() {
		((ActionPointListModel)getModel()).refresh(actionController.getAps());
	}

	private void addListeners() {
		addMouseListener();

	}

	private void addMouseListener() {
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
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent arg0) {
				ActionPoint ap = getModel().getElementAt(getSelectedIndex());
				System.out
						.println("Mouse clicked in list. \nSelected Element is: "
								+ (ap).getName());
				ActionList.this.actionController.setCurrentActionPoint(ap);

			}
		});
	}

	public ActionController getActionController() {
		return actionController;
	}

	public void setActionController(ActionController actionController) {
		this.actionController = actionController;
	}

	public static class ActionPointListModel extends
			DefaultListModel<ActionPoint> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3148740809443212446L;
		private List<ActionPoint> aps;

		public ActionPointListModel(List<ActionPoint> aps) {
			this.aps = aps;
			initAps();
		}

		private void initAps() {
			for (ActionPoint ap : aps) {
				addElement(ap);
			}
		}

		public void refresh(List<ActionPoint> aps) {
			while (size() > 0) {
				remove(0);
			}
			this.aps = aps;
			initAps();
		}

	}

	public class ActionPointListCellRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2187064947773506028L;

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// I know DefaultListCellRenderer always returns a JLabel
			// super setups up all the defaults
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);

			// "value" is whatever object you put into the list, you can use it
			// however you want here

			// I'm going to prefix the label text to demonstrate the point

			label.setText(((ActionPoint) list.getModel().getElementAt(index))
					.getName());

			label.setBackground((index % 2 == 0 ? getBackground().darker()
					: getBackground().darker().darker()));

			return label;

		}
	}

}
