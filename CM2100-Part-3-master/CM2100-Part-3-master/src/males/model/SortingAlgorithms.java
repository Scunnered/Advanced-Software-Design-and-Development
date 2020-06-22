package males.model;

public abstract class SortingAlgorithms {

	public static void countSort(Integer[] a) {
        
		resetComplexityCounters();
		
        int min = Integer.MAX_VALUE; 
        int max = Integer.MIN_VALUE; 
 
        //find the min and max value
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
        	++numberOfComparisons;
        	
            if (a[i] > max) {
                max = a[i];
            }
        	++numberOfComparisons;
        }
        //create an array to store frequencies for array elements
        //and store count for each character
        int[] frequencies = new int[max - min + 1];
        numberOfAdditionalArrayElements += frequencies.length;
        
        for (int i = 0; i < a.length; i++) {    
            frequencies[a[i] - min]++;   
        }
        int outPos = 0;
        //generate output array using frequencies (sorted from min to high)
        //of course the range of values fall in [min; max]
        for (int i = 0; i < frequencies.length; i++) {
            for (int j = 0; j < frequencies[i]; j++) {
                a[outPos++] = i + min;
                ++numberOfCopiesRequired;
            }
        }  
        
    }
	
	public static void selectionSort(Integer a[]) {
		
		resetComplexityCounters();
		
        int arrayLength = a.length;
        // One by one move boundary of unsorted subarray 
        // so from element 0 to element length-1
        for (int iteration = 0; iteration <  arrayLength - 1; iteration++) {
            // Find the minimum element in unsorted array 
            int min_index = iteration;
            for (int j = iteration + 1; j < arrayLength; j++) {   
                if (a[j] < a[min_index]) {   
                    min_index = j;
                }         
            	++numberOfComparisons;
            }
            // Swap the found minimum element with the first element 
            int temp = a[min_index];
            a[min_index] = a[iteration];
            a[iteration] = temp;
        }
        
    }
	
	public static void bubbleSort(Integer[] a) {
		
		resetComplexityCounters();
		
        Integer temp;
       //ex: a = new Integer[]{6,4,3,9,7,10,2};
        //we go through array and swap elements. need to iterate through array
        // if 6 > 4, then 6 becomes 4 and 4 becomes 6
        //since we are comparing an element of the array with the one below it 
        //we need to ensure iteration< a.length-1 i.e. minus 1 of the length of the array
        for (int iteration = 0; iteration< a.length-1; iteration++){
            //we go through the array once, then swap, then go through the array again and
            //swap again etc.  We have “-iteration” because we compare one less each time
            //so at first got to 10, then next iteration to 7, then 9 then 3 etc.
	//for array like: a = new Integer[]{6,4,3,9,7,10,2};
            for (int j = 0; j < a.length-1-iteration; j++){
                if (a[j] > a[j+1]){
                    temp = a[j];        
                    a[j] = a[j+1];
                    ++numberOfCopiesRequired;
                    a[j+1] = temp;
                    ++numberOfCopiesRequired;
                }
                ++numberOfComparisons;
            }
        }
        
    }
	
	public static int[] getComplexityCounters() {
		return new int[] { numberOfComparisons, numberOfCopiesRequired, numberOfAdditionalArrayElements };
	}
	
	private static void resetComplexityCounters() {
		numberOfComparisons = 0;
		numberOfCopiesRequired = 0;
		numberOfAdditionalArrayElements = 0;
	}
	
	// Time complexity
	private static int numberOfComparisons;
	private static int numberOfCopiesRequired;
	
	// Space complexity
	private static int numberOfAdditionalArrayElements;
	
}
