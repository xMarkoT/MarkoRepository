package rb.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import rb.ActionController;
import rb.ActionController.ActionPoint;
import rb.ActionController.Actions;
import rb.ActionController.PointHolder;
import rb.ActionControllerChangeListener;
import rb.MouseLocationListener;
import rb.MouseLocationThread;
import rb.RBFileUtil;
import rb.Tester;
import rb.gui.swrapper.ActionList;
import rb.gui.swrapper.ActionTypeComboBox;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ClickStoreFrame extends JFrame implements
		ActionControllerChangeListener, MouseLocationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1189187460999575492L;
	private JPanel contentPane;
	private ActionController actionController;
	private JTextField nameTextField;
	private JTextField textFieldDuration;
	private ActionTypeComboBox actionTypeComboBox;
	private JLabel lblXmouseposition;
	private JLabel lblYmouseposition;
	private JLabel lblX;
	private JTextField xValue;
	private JLabel lblY;
	private JTextField yValue;
	private JLabel lblPressspaceBar;
	private JButton btnAdd;
	private ActionList actionList;
	private JButton btnUpdate;
	private JButton btnSaveToFile;
	private JButton btnLoadFromFile;
	private JButton btnStart;

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
					MouseLocationThread mlt = new MouseLocationThread();
					mlt.addListener(frame);
					mlt.start();
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
		setSize(1100, 300);
		actionController.addChangeListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 9, 4, 2));

		// TODO remove the TestComentWrapper
		// **test>
		// actionController.setAps(Tester.generateTestActionPoints());
		// **test<
		JScrollPane listPane;
		;

		actionList = new ActionList(actionController);

		listPane = new JScrollPane(actionList);

		contentPane.add(listPane);

		JLabel lblName = new JLabel("Name: ");
		lblName.setHorizontalTextPosition(JLabel.RIGHT);
		lblName.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(lblName);

		nameTextField = new JTextField();
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblActionType = new JLabel("Action Type: ");
		lblActionType.setHorizontalTextPosition(JLabel.RIGHT);
		lblActionType.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(lblActionType);

		actionTypeComboBox = new ActionTypeComboBox(actionController);
		contentPane.add(actionTypeComboBox);

		JLabel lblDurationseconds = new JLabel("Time between actions (mili seconds): ");
		lblDurationseconds.setHorizontalTextPosition(JLabel.RIGHT);
		lblDurationseconds.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(lblDurationseconds);

		textFieldDuration = new JTextField();
		contentPane.add(textFieldDuration);
		textFieldDuration.setColumns(10);

		lblXmouseposition = new JLabel("xMousePosition");
		lblXmouseposition.setHorizontalTextPosition(JLabel.CENTER);
		lblXmouseposition.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblXmouseposition);

		lblYmouseposition = new JLabel("yMousePosition");
		lblYmouseposition.setHorizontalTextPosition(JLabel.CENTER);
		lblYmouseposition.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblYmouseposition);

		lblX = new JLabel("X: ");
		lblX.setHorizontalTextPosition(JLabel.RIGHT);
		lblX.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(lblX);

		xValue = new JTextField();
		contentPane.add(xValue);
		xValue.setColumns(10);

		lblY = new JLabel("Y: ");
		lblY.setHorizontalTextPosition(JLabel.RIGHT);
		lblY.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(lblY);

		yValue = new JTextField();
		contentPane.add(yValue);
		yValue.setColumns(10);

		lblPressspaceBar = new JLabel(
				"Press ' ctrl + Space Bar' to capture current mouse position.");
		contentPane.add(lblPressspaceBar);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ActionPoint ap = new ActionPoint();
				ap.setName("new "
						+ ClickStoreFrame.this.actionController.getAps().size());

				List<PointHolder> phs = new ArrayList<ActionController.PointHolder>();
				phs.add(new PointHolder(new Point(0, 0)));
				ap.setPoints(phs);

				List<Actions> as = new ArrayList<ActionController.Actions>();
				as.add(new Actions(0));
				ap.setActions(as);

				ClickStoreFrame.this.actionController.getAps().add(ap);
				ClickStoreFrame.this.actionController.setCurrentActionPoint(ap);

			}
		});
		btnAdd.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(btnAdd);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ActionPoint cap = ClickStoreFrame.this.actionController
						.getCurrentActionPoint();
				while (!cap.getActions().isEmpty()) {
					cap.getActions().remove(0);

				}
				cap.addAction((Actions) (actionTypeComboBox.getSelectedItem()));
				while (!cap.getPoints().isEmpty()) {
					cap.getPoints().remove(0);

				}
				cap.addPoint(new PointHolder(new Point(Integer.valueOf(Math
						.round(Float.valueOf(xValue.getText()))), Integer
						.valueOf(Math.round(Float.valueOf(yValue.getText()))))));
				cap.setName(nameTextField.getText());
				cap.setDuration(Integer.valueOf(textFieldDuration.getText()));

			}
		});
		contentPane.add(btnUpdate);

		btnSaveToFile = new JButton("Save To File...");
		btnSaveToFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showSaveDialog(ClickStoreFrame.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					RBFileUtil.saveToFile(file,
							ClickStoreFrame.this.actionController);
				} else {

				}

			}
		});
		contentPane.add(btnSaveToFile);

		btnLoadFromFile = new JButton("Load From File...");
		btnLoadFromFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showSaveDialog(ClickStoreFrame.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					List<ActionPoint> loadFromDir = RBFileUtil
							.loadFromDir(file);
					ClickStoreFrame.this.actionController.getAps().addAll(
							loadFromDir);
					ClickStoreFrame.this.actionController
							.updateChanngeListeners();

				} else {

				}

			}
		});
		contentPane.add(btnLoadFromFile);

		btnStart = new JButton("Start!");
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				for (ActionPoint ap : ClickStoreFrame.this.actionController
						.getAps()) {
					ap.doAction();
				}
			}
		});
		contentPane.add(btnStart);

		addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent arg0) {
				lblXmouseposition.setText("Current x: " + arg0.getXOnScreen());
				lblYmouseposition.setText("Current y: " + arg0.getYOnScreen());

			}

			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		addKeyListenerToAllComponents(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
				System.out.println("keyboard " + arg0.getKeyCode());
				if (arg0.isControlDown()
						&& KeyEvent.VK_SPACE == arg0.getKeyCode()) {
					System.out.println("space is typed");

					updatePoint(MouseInfo.getPointerInfo().getLocation());
				}
				if (arg0.isControlDown() && 0 == arg0.getKeyCode()) {
					updatePoint(MouseInfo.getPointerInfo().getLocation());
				}

			}

			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		}, this);
		addComponentListener(new ComponentListener() {

			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void componentResized(ComponentEvent arg0) {
				// System.out.println(ClickStoreFrame.this.getSize());

			}

			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void addKeyListenerToAllComponents(KeyListener listener,
			Container container) {
		container.addKeyListener(listener);
		Component[] components = container.getComponents();
		if (components != null && components.length > 0) {
			for (Component component : components) {
				if (component instanceof Container) {
					addKeyListenerToAllComponents(listener,
							(Container) component);
				} else {
					component.addKeyListener(listener);
				}
			}
		}
	}

	public void updateAll() {
		if (actionController.getCurrentActionPoint() != null) {
			nameTextField.setText(actionController.getCurrentActionPoint()
					.getName());
			textFieldDuration.setText(actionController.getCurrentActionPoint()
					.getDuration() + "");
			updateActionTypeComboBox();
			xValue.setText(actionController.getCurrentActionPoint().getPoints()
					.get(0).getPoint().getX()
					+ "");
			yValue.setText(actionController.getCurrentActionPoint().getPoints()
					.get(0).getPoint().getY()
					+ "");

		}
		((ActionList) (actionList)).refresh();

	}

	private void updateActionTypeComboBox() {

		for (int i = 0; i < actionTypeComboBox.getModel().getSize(); i++) {
			Actions a = (Actions) actionTypeComboBox.getModel().getElementAt(i);
			if (a.getActionType() == actionController.getCurrentActionPoint()
					.getActions().get(0).getActionType()) {
				actionTypeComboBox.setSelectedItem(a);
				actionTypeComboBox.repaint();
			}
		}
	}

	private void updatePoint(Point p) {
		actionController.getCurrentActionPoint().getPoints().get(0).setPoint(p);
		actionController.updateChanngeListeners();
	}

	public void updateLocation(Point p) {
		lblXmouseposition.setText("Current x: " + p.getX());
		lblYmouseposition.setText("Current y: " + p.getY());

	}

}
