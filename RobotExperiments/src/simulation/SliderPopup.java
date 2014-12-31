package simulation;

import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderPopup extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 239810139847101406L;
	private WorldFrame worldFrame;
	private JSlider slider;

	public SliderPopup(WorldFrame worldFrame) {
		this.setWorldFrame(worldFrame);
		slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(200);
		slider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				SliderPopup.this.worldFrame.setSpeed(200 - slider.getValue());

			}
		});
		add(slider);

	}

	public WorldFrame getWorldFrame() {
		return worldFrame;
	}

	public void setWorldFrame(WorldFrame worldFrame) {
		this.worldFrame = worldFrame;
	}

}
