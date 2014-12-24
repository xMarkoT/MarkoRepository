package rb.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import rb.ActionController;
import rb.ActionController.Actions;
import rb.ActionControllerChangeListener;
import rb.Tester;
import rb.ActionController.ActionPoint;
import rb.gui.swrapper.ActionList;
import rb.gui.swrapper.ActionTypeComboBox;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class ClickStoreFrame extends JFrame implements
		ActionControllerChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1189187460999575492L;
	private JPanel contentPane;
	private ActionController actionController;
	private JTextField nameTextField;
	private JTextField textFieldDuration;
	private ActionTypeComboBox actionTypeComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					ClickStoreFrame frame = new ClickStoreFrame(
							new ActionController());
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClickStoreFrame(ActionController actionController) {
		this.actionController = actionController;
		actionController.addChangeListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		// TODO remove the TestComentWrapper
		// **test>
		actionController.setAps(Tester.generateTestActionPoints());
		// **test<
		JList<ActionPoint> list = new ActionList(actionController);

		contentPane.add(list);

		JLabel lblName = new JLabel("Name: ");
		contentPane.add(lblName);

		nameTextField = new JTextField();
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblActionType = new JLabel("Action Type: ");
		contentPane.add(lblActionType);

		actionTypeComboBox = new ActionTypeComboBox(actionController);
		contentPane.add(actionTypeComboBox);

		JLabel lblDurationseconds = new JLabel("Duration (seconds): ");
		contentPane.add(lblDurationseconds);

		textFieldDuration = new JTextField();
		contentPane.add(textFieldDuration);
		textFieldDuration.setColumns(10);
	}

	public void updateAll() {
		if (actionController.getCurrentActionPoint() != null) {
			nameTextField.setText(actionController.getCurrentActionPoint()
					.getName());
			textFieldDuration.setText(actionController.getCurrentActionPoint()
					.getDuration() + "");
			updateActionTypeComboBox();

		}

	}

	private void updateActionTypeComboBox() {

		for (int i = 0; i < actionTypeComboBox.getModel().getSize(); i++) {
			Actions a = (Actions) actionTypeComboBox.getModel().getElementAt(i);
			if (a.getActionType() == actionController.getCurrentActionPoint()
					.getActions().get(0).getActionType()) {
				actionTypeComboBox.setSelectedItem(a);
			}
		}
	}

}
