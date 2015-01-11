package simulation;

import java.io.File;
import java.io.ObjectOutputStream.PutField;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		File mp3 = new File(
				"C:\\Users\\Risto\\Desktop\\quick\\20140802_uloshkiSelfJam.wav");
		iSoundAnalytics sa = new SoundAnalytics();
		System.out.println(mp3.exists());
		System.out.println(sa.getFrequencyKHz(mp3, 12));
		// sa.getFrequencyKHz(mp3, timeS);
		int[][] secondFrequency = sa.getSecondFrequency(mp3, 200);
		Map<Integer, Integer> freqMap = new HashMap<>();
		for (int[] is : secondFrequency) {
			int high = 0;
			int low = 0;
			int numOfTakts = 0;
			int gap = 0;
			System.out.println("lenght "+is.length);
			for (int i : is) {
				high = high > i ? high : i;
				low = low < i ? low : i;
				numOfTakts = i > 13000 ? numOfTakts+1 : numOfTakts;
//				if (i > 13000) {
//					System.out.println(" g:"+gap);
//					gap = 0;
//				} else {
//					gap++;
//				}
				if (freqMap.containsKey(new Integer(i))) {
					freqMap.put(i, freqMap.get(i) + 1);
				} else {
					freqMap.put(i, 1);
				}
			}
			System.out.println("higest " + high);
			System.out.println("lowest " + low);
			System.out.println("number of unique numbers "
					+ freqMap.keySet().size());
			System.out.println("Number of takts: " + numOfTakts);

		}

	}

}
