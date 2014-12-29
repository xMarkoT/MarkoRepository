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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import rb.gui.swrapper.ActionList;
import rb.gui.swrapper.ActionList.ActionPointListModel;
import rb.gui.swrapper.ActionTypeComboBox;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
	private JPanel westPanel;
	private JPanel eastPanel;
	private JButton btnRemove;
	private JPanel westButtons;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSaveAs;

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
		setTitle("Clicker");
		this.actionController = actionController;
		setName("Clicker");
		setSize(1100, 300);
		actionController.addChangeListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 4, 2));

		// TODO remove the TestComentWrapper
		// **test>
		// actionController.setAps(Tester.generateTestActionPoints());
		// **test<
		JScrollPane listPane;
		;

		westPanel = new JPanel();
		contentPane.add(westPanel);
		westPanel.setLayout(new BorderLayout(0, 0));

		actionList = new ActionList(actionController);

		listPane = new JScrollPane(actionList);
		westPanel.add(listPane, BorderLayout.CENTER);

		westButtons = new JPanel();
		westPanel.add(westButtons, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		westButtons.add(btnAdd);

		btnRemove = new JButton("Remove");
		westButtons.add(btnRemove);

		btnUpdate = new JButton("Update");
		westButtons.add(btnUpdate);

		btnLoadFromFile = new JButton("Load From File...");
		// westButtons.add(btnLoadFromFile);
		btnLoadFromFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				loadAction();
			}
		});
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
		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ActionPointListModel model = (ActionPointListModel) actionList
						.getModel();
				// 1. remove from list model
				model.removeElement(ClickStoreFrame.this.actionController
						.getCurrentActionPoint());
				// 2. remove the current AP from the controller
				ClickStoreFrame.this.actionController.getAps().remove(
						ClickStoreFrame.this.actionController
								.getCurrentActionPoint());
				// set new current
				if (ClickStoreFrame.this.actionController.getAps() != null
						&& !ClickStoreFrame.this.actionController.getAps()
								.isEmpty()) {
					ClickStoreFrame.this.actionController
							.setCurrentActionPoint(ClickStoreFrame.this.actionController
									.getAps().get(0));
				}
				ClickStoreFrame.this.updateAll();
			}

		});
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

		eastPanel = new JPanel();
		contentPane.add(eastPanel);
		eastPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblName = new JLabel("Name: ");
		eastPanel.add(lblName);
		lblName.setHorizontalTextPosition(JLabel.RIGHT);
		lblName.setHorizontalAlignment(JLabel.RIGHT);

		nameTextField = new JTextField();
		eastPanel.add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblActionType = new JLabel("Action Type: ");
		eastPanel.add(lblActionType);
		lblActionType.setHorizontalTextPosition(JLabel.RIGHT);
		lblActionType.setHorizontalAlignment(JLabel.RIGHT);

		actionTypeComboBox = new ActionTypeComboBox(actionController);
		eastPanel.add(actionTypeComboBox);

		JLabel lblDurationseconds = new JLabel(
				"Time between actions (mili seconds): ");
		eastPanel.add(lblDurationseconds);
		lblDurationseconds.setHorizontalTextPosition(JLabel.RIGHT);
		lblDurationseconds.setHorizontalAlignment(JLabel.RIGHT);

		textFieldDuration = new JTextField();
		eastPanel.add(textFieldDuration);
		textFieldDuration.setColumns(10);

		lblXmouseposition = new JLabel("xMousePosition");
		eastPanel.add(lblXmouseposition);
		lblXmouseposition.setHorizontalTextPosition(JLabel.CENTER);
		lblXmouseposition.setHorizontalAlignment(JLabel.CENTER);

		lblYmouseposition = new JLabel("yMousePosition");
		eastPanel.add(lblYmouseposition);
		lblYmouseposition.setHorizontalTextPosition(JLabel.CENTER);
		lblYmouseposition.setHorizontalAlignment(JLabel.CENTER);

		lblX = new JLabel("X: ");
		eastPanel.add(lblX);
		lblX.setHorizontalTextPosition(JLabel.RIGHT);
		lblX.setHorizontalAlignment(JLabel.RIGHT);

		xValue = new JTextField();
		eastPanel.add(xValue);
		xValue.setColumns(10);

		lblY = new JLabel("Y: ");
		eastPanel.add(lblY);
		lblY.setHorizontalTextPosition(JLabel.RIGHT);
		lblY.setHorizontalAlignment(JLabel.RIGHT);

		yValue = new JTextField();
		eastPanel.add(yValue);
		yValue.setColumns(10);

		lblPressspaceBar = new JLabel(
				"Press ' ctrl + Space Bar' to capture current mouse position.");
		lblPressspaceBar
				.setToolTipText("Press ' ctrl + Space Bar' to capture current mouse position.");
		eastPanel.add(lblPressspaceBar);

		btnSaveToFile = new JButton("Save To File...");
		// eastPanel.add(btnSaveToFile);

		btnStart = new JButton("Start!");
		eastPanel.add(btnStart);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				loadAction();

			}
		});
		mnFile.add(mntmOpen);

		mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				saveAction();

			}
		});
		mnFile.add(mntmSaveAs);
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				for (ActionPoint ap : ClickStoreFrame.this.actionController
						.getAps()) {
					ap.doAction();
				}
			}
		});
		btnSaveToFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				saveAction();
			}
		});

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
		setEnabledActionPropertyComponents(false);

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
		// System.out.println("size: " + actionList.getModel().getSize());
		setEnabledActionPropertyComponents(actionList.getModel().getSize() > 0);

	}

	public void setEnabledActionPropertyComponents(boolean enabled) {
		Component[] components = eastPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(enabled);
		}
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

	private void saveAction() {

		JFileChooser fc = new JFileChooser();
		int result = fc.showSaveDialog(ClickStoreFrame.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			RBFileUtil.saveToFile(file, ClickStoreFrame.this.actionController);
		} else {

		}

	}

	private void loadAction() {

		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Open");
		fc.setApproveButtonText("Open");
		// TODO change the button text from "Save" to "Open". (estimated time
		// 15 mins)
		int result = fc.showSaveDialog(ClickStoreFrame.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			List<ActionPoint> loadFromDir = RBFileUtil.loadFromDir(file);
			ClickStoreFrame.this.actionController.getAps().addAll(loadFromDir);
			ClickStoreFrame.this.actionController.updateChanngeListeners();

		} else {

		}

	}

}
