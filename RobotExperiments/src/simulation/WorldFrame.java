package simulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * 
 * This is Game of Life implementation
 * 
 * @author Risto
 * 
 */
public class WorldFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6335159977828760402L;
	private JPanel contentPane;
	private int columns = 50;
	private int rows = 50;

	public Model[][] space;
	private int speed = 200;
	private AnimationThread animationThread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					WorldFrame frame = new WorldFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WorldFrame() throws java.lang.IllegalArgumentException {
		this(null);
	}

	public WorldFrame(Model[][] space)
			throws java.lang.IllegalArgumentException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		initSpace(space);
		JMenuBar menuBar = new JMenuBar();
		JMenu mnPlayback = new JMenu();
		mnPlayback.setText("Playback");
		JMenuItem mnitPlay = new JMenuItem("Play");
		mnitPlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				playAction();
			}
		});
		mnPlayback.add(mnitPlay);
		JMenuItem mnitStop = new JMenuItem("Stop");
		mnitStop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				stopAction();
			}
		});
		mnPlayback.add(mnitStop);
		JMenuItem mnitReset = new JMenuItem("Reset");
		mnitReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				resetAction();
			}
		});
		mnPlayback.add(mnitReset);
		menuBar.add(mnPlayback);

		setJMenuBar(menuBar);
	}

	private void playAction() {
		stopAction();
		animationThread = new AnimationThread(this);
		animationThread.start();
	}

	private void stopAction() {
		if (animationThread != null) {
			animationThread.stopAnimation();
			animationThread = null;
		}
	}

	private void resetAction() {
	}

	private void initSpace(Model[][] space) {
		if (space == null) {
			space = new Model[getColumns()][getRows()];
			for (int column = 0; column < getColumns(); column++) {
				for (int row = 0; row < getRows(); row++) {
					space[column][row] = new Model(new Point(column, row), this);
				}
			}
		}
		this.space = space;
		initGrid();

	}

	private void initGrid() {
		GridLayout gl = new GridLayout(getColumns(), getRows(), 2, 2);
		JPanel content = new JPanel(gl);
		setContentPane(content);
		for (int column = 0; column < getColumns(); column++) {
			for (int row = 0; row < getRows(); row++) {
				content.add(space[column][row]);
			}
		}
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Model[][] getSpace() {
		return space;
	}

	public void setSpace(Model[][] space) {
		this.space = space;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
