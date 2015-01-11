package simulation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundAnalytics implements iSoundAnalytics {

	private File waveFile;

	public SoundAnalytics() {
		this(null);
	}

	public SoundAnalytics(File waveFile) {
		this.waveFile = waveFile;
	}

	@Override
	public long getFrequencyKHz(File song, int timeS) {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem
					.getAudioInputStream(new BufferedInputStream(
							new FileInputStream(song)));

			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = song.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));

			byte[] bytes = new byte[(int) (audioInputStream.getFrameLength())
					* (audioInputStream.getFormat().getFrameSize())];
			try {
				audioInputStream.read(bytes);
				// Get amplitude values for each audio channel in an array.
				int[][] graphData = this.getUnscaledAmplitude(bytes, 1);
				for (int row = 0; row < graphData.length; row++) {
					float data = (graphData[row].length);
					float valueAt = (data / durationInSeconds) * timeS;
					return graphData[row][(int) valueAt];
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return 0;
	}

	@Override
	public int[][] getSecondFrequency(File song, int duration) {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem
					.getAudioInputStream(new BufferedInputStream(
							new FileInputStream(song)));

			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = song.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));

			byte[] bytes = new byte[(int) (audioInputStream.getFrameLength())
					* (audioInputStream.getFormat().getFrameSize())];
			try {
				audioInputStream.read(bytes);
				// Get amplitude values for each audio channel in an array.
				int[][] graphData = this.getUnscaledAmplitude(bytes, 1);
//				for (int row = 0; row < graphData.length; row++) {
//					float data = (graphData[row].length);
//					for (int i = 0; i < duration; i++) {
//						float valueAt = (data / durationInSeconds) * i;
//						System.out.println(graphData[row][(int) valueAt]);
//					}
//
//				}
				return graphData;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	public int[][] getUnscaledAmplitude(byte[] eightBitByteArray, int nbChannels) {
		int[][] toReturn = new int[nbChannels][eightBitByteArray.length
				/ (2 * nbChannels)];
		int index = 0;

		for (int audioByte = 0; audioByte < eightBitByteArray.length;) {
			for (int channel = 0; channel < nbChannels; channel++) {
				// Do the byte to sample conversion.
				int low = (int) eightBitByteArray[audioByte];
				audioByte++;
				int high = (int) eightBitByteArray[audioByte];
				audioByte++;
				int sample = (high << 8) + (low & 0x00ff);

				toReturn[channel][index] = sample;
			}
			index++;
		}

		return toReturn;
	}

	public File getWaveFile() {
		return waveFile;
	}

	public void setWaveFile(File songFile) {
		this.waveFile = songFile;
	}

}
