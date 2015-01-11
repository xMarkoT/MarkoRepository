package simulation;

import java.io.File;

public interface iSoundAnalytics {

	/**
	 * This method will return the frequency in kHz on a given song file and at
	 * specific time
	 * 
	 * @param song
	 *            The song file (mp3 or wave)
	 * @param timeS
	 *            time in seconds
	 * @return Frequency in kHz
	 */
	public long getFrequencyKHz(File song, int timeS);

	/**
	 * 
	 * @param song
	 * @param duration
	 * @return
	 */
	public int[][] getSecondFrequency(File song, int duration);

}
