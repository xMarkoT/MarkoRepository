package drawing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.reflections.Reflections;

import drawing.AnimationGrid.TimedRepaint;

public class GridCanvas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7140322982447654662L;
	private JPanel contentPane;
	private JMenuBar optionsBar;
	private JMenu optionsMenu;
	private JMenu animationsMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GridCanvas frame = new GridCanvas();
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
	public GridCanvas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, 716, 759);
		contentPane = new Animations();
		((AnimationGrid) contentPane).getTimedRepaint().start();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setJMenuBar(getOptionsBar());
	}

	public JMenuBar getOptionsBar() {
		if (optionsBar == null) {
			optionsBar = new JMenuBar();
			optionsBar.add(getAnimationsMenu());
			optionsBar.add(getOptionsMenu());
		}
		return optionsBar;
	}

	private void resetOptions() {
		((Container) getOptionsMenu()).removeAll();
		Map<String, Boolean> animationOptions = ((AnimationGrid) contentPane)
				.getAnimationOptions();
		for (final String option : animationOptions.keySet()) {
			final JCheckBoxMenuItem item = new JCheckBoxMenuItem(option);
			item.setSelected(animationOptions.get(option));
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					((AnimationGrid) contentPane).getAnimationOptions().put(
							option, item.isSelected());
					((AnimationGrid) contentPane).optionsChange();

				}
			});
			getOptionsMenu().add(item);
		}

	}

	public void setOptionsBar(JMenuBar optionsBar) {
		this.optionsBar = optionsBar;
	}

	public JMenu getOptionsMenu() {
		if (optionsMenu == null) {
			optionsMenu = new JMenu("Options");
			resetOptions();
		}
		return optionsMenu;
	}

	public void setOptionsMenu(JMenu optionsMenu) {
		this.optionsMenu = optionsMenu;
	}

	public JMenu getAnimationsMenu() {
		if (animationsMenu == null) {
			animationsMenu = new JMenu("Animations");
			Reflections reflections = new Reflections("drawing");
			Set<Class<? extends AnimationGrid>> subTypesOf = reflections
					.getSubTypesOf(AnimationGrid.class);
			ButtonGroup group = new ButtonGroup();
			for (final Class<? extends AnimationGrid> class1 : subTypesOf) {
				final JRadioButtonMenuItem item = new JRadioButtonMenuItem(
						class1.getCanonicalName());
				item.setSelected(contentPane.getClass().equals(class1));
				item.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						if (item.isSelected()) {
							TimedRepaint timedRepaint = ((AnimationGrid) contentPane)
									.getTimedRepaint();
							if (timedRepaint != null) {
								timedRepaint.stop();
							}
							try {
								contentPane = class1.newInstance();
								setContentPane(contentPane);
								TimedRepaint timedRepaint2 = ((AnimationGrid) contentPane)
										.getTimedRepaint();
								timedRepaint2.start();
								resetOptions();
								GridCanvas.this.repaint();
								contentPane.repaint();
								Dimension size = getSize();
								GridCanvas.this.setSize(new Dimension());
								setSize(size);
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				});
				group.add(item);
				animationsMenu.add(item);
			}
		}
		return animationsMenu;
	}

	public void setAnimationsMenu(JMenu animationsMenu) {
		this.animationsMenu = animationsMenu;
	}

}
