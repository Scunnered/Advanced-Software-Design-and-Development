package males.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import males.model.ArrayGenerator;
import males.model.SortingAlgorithms;

import males.gui.*;

public class Main {

	public static void main(String[] args) {

		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
		
		ArrayList<List<int[]>> tests = new ArrayList<List<int[]>>();
		tests.add(startTest(AlgorithmTask.COUNT_SORT, 1000, 30));
		tests.add(startTest(AlgorithmTask.SELECTION_SORT, 1000, 30));
		tests.add(startTest(AlgorithmTask.BUBBLE_SORT, 1000, 30));
		
		ArrayList<String> algNames = new ArrayList<String>();
		algNames.add("Count sort");
		algNames.add("Selection sort");
		algNames.add("Bubble sort");
		
		MainWindow mw = new MainWindow(algNames, tests);
		mw.setVisible(true);
		
	}
	
	private static List<int[]> startTest(AlgorithmTask at, int maxArraySize, int numberOfRepetitions) {
		
		List<int[]> result = taskStepThree(at, maxArraySize, numberOfRepetitions);
		
		System.out.println("[Array size, Number of comparisons, Number of copies req., Number of additional array elements]");
		for (int[] comp : result) {
			System.out.print("[");
			for (int i = 0; i < comp.length; ++i) {
				System.out.print(comp[i]);
				if (i < comp.length - 1) System.out.print(", ");
			}
			System.out.println("]");
		}
		
		return result;
		
	}
	
	private static List<int[]> taskStepThree(AlgorithmTask at, int maxArraySize, int numberOfRepetitions) {
		
		ArrayList<int[]> avgComplexities = new ArrayList<int[]>();
		
		int nextIncrement = 100, increment = 10;
		for (int i = 10; i < maxArraySize; ++i) {
			if (i >= nextIncrement) {
				increment = nextIncrement;
				nextIncrement = 10 * nextIncrement;
			}
			
			System.out.println("Calculating " + at + " : Array size of " + i + ", " + numberOfRepetitions + " times.");
			int[] avgComp = taskStepTwo(at, i, numberOfRepetitions);
			int[] res = new int[avgComp.length + 1];
			for (int cop = 1; cop < res.length; ++cop) {
				res[cop] = avgComp[cop - 1];
			}
			res[0] = i;
			
			avgComplexities.add(res);
		}
		
		return avgComplexities;
		
	}
	
	private static int[] taskStepTwo(AlgorithmTask at, int arrayLength, int numberOfRepetitions) {
		
		int[] averageComplexity = new int[SortingAlgorithms.getComplexityCounters().length];
		
		for (int i = 0; i < numberOfRepetitions; ++i) {
			int[] complexity = taskStepOne(at, arrayLength);
			for (int j = 0; j < averageComplexity.length; ++j) {
				averageComplexity[j] += complexity[j];
			}
		}
		
		for (int j = 0; j < averageComplexity.length; ++j) {
			averageComplexity[j] /= numberOfRepetitions;
		}
		
		return averageComplexity;
		
	}
	
	private static int[] taskStepOne(AlgorithmTask at, int arrayLength) {
		
		Integer[] arr = ArrayGenerator.generateArray(0, arrayLength, arrayLength);
		
		int[] complexity;
		switch (at) {
			case COUNT_SORT: 
				SortingAlgorithms.countSort(arr);
				break;
			case SELECTION_SORT:
				SortingAlgorithms.selectionSort(arr);
				break;
			case BUBBLE_SORT:
				SortingAlgorithms.bubbleSort(arr);
				break;
			default:
				complexity = null;
				break;
		}
		
		complexity = SortingAlgorithms.getComplexityCounters();
		
		return complexity;
		
	}
	
	private static enum AlgorithmTask {
		
		COUNT_SORT,
		SELECTION_SORT,
		BUBBLE_SORT
		
	};
}
