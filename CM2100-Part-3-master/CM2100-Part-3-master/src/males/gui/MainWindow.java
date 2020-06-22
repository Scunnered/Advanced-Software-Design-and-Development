package males.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public MainWindow(List<String> algorithmUsed, List<List<int[]>> algorithmData) {
		
		// Data
		this.algorithmUsed = algorithmUsed;
		this.algorithmData = algorithmData;
		
		// Window location and size
		this.setMinimumSize(new Dimension(1000, 800));
		this.setLocationRelativeTo(null);
		
		// Window title and icon
		this.setTitle("Algorithm Test");
		
		// Window states
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// UI Initialization
		initializeGraphs();
		initializeGraphUI();
		
	}
	
	private void initializeGraphs() {
		
		graphs = new ArrayList<JPanel>();
		
		for (int i = 0; i < algorithmUsed.size(); ++i) {

			DefaultCategoryDataset timeDataset = getTimeComplexityDataset(algorithmData.get(i));
			DefaultCategoryDataset spaceDataset = getSpaceComplexityDataset(algorithmData.get(i));
			
			JFreeChart firstLineChart = ChartFactory.createLineChart(
			         "Algorithm: " + algorithmUsed.get(i),
			         "Array size","Time complexity",
			         timeDataset,
			         PlotOrientation.VERTICAL,
			         true,true,false);
			         
			ChartPanel firstChartPanel = new ChartPanel(firstLineChart);
			
			JFreeChart secondLineChart = ChartFactory.createLineChart(
			         "Algorithm: " + algorithmUsed.get(i),
			         "Array size","Space complexity",
			         spaceDataset,
			         PlotOrientation.VERTICAL,
			         true,true,false);
			secondLineChart.setAntiAlias(true);
			secondLineChart.setTextAntiAlias(true);
			
			ChartPanel secondChartPanel = new ChartPanel(secondLineChart);
			
			JPanel graphPanel = new JPanel();
			graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
			
			graphPanel.add(firstChartPanel);
			graphPanel.add(secondChartPanel);
			
			graphs.add(graphPanel);
			
		}
		
	}
	
	private void initializeGraphUI() {
		
		/*
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		for (JPanel graph : graphs) {
			this.getContentPane().add(graph);
		}
		*/
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		JButton back = new JButton("Back");
		back.setFont(new Font("Arial", Font.PLAIN, 24));
		back.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JButton next = new JButton("Next");
		next.setFont(new Font("Arial", Font.PLAIN, 24));
		next.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		buttonPanel.add(back, BorderLayout.WEST);
		buttonPanel.add(next, BorderLayout.EAST);
		
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		this.revalidate();
		this.repaint();
		
		setGraph(graphIndex);
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int newIndex = graphIndex - 1;
				if (newIndex < 0) newIndex = graphs.size() - 1;
				setGraph(newIndex);
			}
			
		});
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int newIndex = graphIndex + 1;
				if (newIndex >= graphs.size()) newIndex = 0;
				setGraph(newIndex);
			}
			
		});
		
	}
	
	private void setGraph(int index) {
		
		this.getContentPane().remove(graphs.get(graphIndex));
		this.getContentPane().add(graphs.get(index), BorderLayout.CENTER);
		
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
		
		graphIndex = index;
		
	}
	
	private DefaultCategoryDataset getTimeComplexityDataset(List<int[]> data) {
	      
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (int i = 0; i < data.size(); ++i) {
			dataset.addValue(data.get(i)[1] + data.get(i)[2], "Time", "" + data.get(i)[0]);
		}
		
	    return dataset;
	
	}
	
	private DefaultCategoryDataset getSpaceComplexityDataset(List<int[]> data) {
	      
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (int i = 0; i < data.size(); ++i) {
			dataset.addValue(data.get(i)[3], "Space", "" + data.get(i)[0]);
		}
		
	    return dataset;
	
	}

	private List<String> algorithmUsed;
	private List<List<int[]>> algorithmData;
	private List<JPanel> graphs;
	private int graphIndex = 0;
	
}
