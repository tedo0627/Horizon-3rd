package tedo.GatyaSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GatyaRandom {

	public static boolean exe(int max, int count) {
		ArrayList<Integer> random = new ArrayList<Integer>();
		for (int i = 1; i <= max; i++) {
			random.add(i);
		}
		Collections.shuffle(random);
		Collections.shuffle(random);
		Collections.shuffle(random);
		Collections.shuffle(random);
		Collections.shuffle(random);
		int co = random.get(0);
		if (co <= count) {
			return true;
		}
		return false;
	}

	public static int select(int max) {
		ArrayList<Integer> random = new ArrayList<Integer>();
		for (int i = 1; i <= max; i++) {
			random.add(i);
		}
		Collections.shuffle(random);
		Collections.shuffle(random);
		return random.get(new Random().nextInt(max));
	}

	public static int getGatyRandom() {
		ArrayList<Integer> random = new ArrayList<Integer>();
		for (int i = 1; i <= 1000; i++) {
			random.add(i);
		}
		Collections.shuffle(random);
		Collections.shuffle(random);
		Collections.shuffle(random);
		Collections.shuffle(random);
		int r = random.get(new Random().nextInt(1000));
		if (r == 2) {
			return 1;
		} else if (20 < r && r <= 41) {
			return 2;
		} else if (100 < r && r <= 200) {
			return 3;
		} else {
			return 0;
		}
	}
}
