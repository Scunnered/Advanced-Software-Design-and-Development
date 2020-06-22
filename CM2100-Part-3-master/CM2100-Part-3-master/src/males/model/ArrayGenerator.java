package males.model;

import java.util.Random;

public abstract class ArrayGenerator {

	public static Integer[] generateArray(int min, int max, int arrayLength) {
		
		Integer[] array = new Integer[arrayLength];
		
		Random rand = new Random();
	    for (int i = 0; i < array.length; ++i) {
	       array[i] = rand.nextInt(max - min) + min;
	    }
		
	    return array;
	    
	}
	
}
