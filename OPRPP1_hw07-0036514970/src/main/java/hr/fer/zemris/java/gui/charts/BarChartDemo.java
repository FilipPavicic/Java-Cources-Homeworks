package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class BarChartDemo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BarChart barChart;
	Path path;

	public BarChartDemo(BarChart barChart, Path path) {
		super();
		this.barChart = barChart;
		this.path = path;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		//setSize(1000, 500);
		initGUI();
		pack();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		add(new JLabel(path.toAbsolutePath().toString(), JLabel.CENTER), BorderLayout.NORTH);
		BarChartComponent bar = new BarChartComponent(barChart);
		add(bar, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		Path path = Paths.get(args[0]);
		BarChart barChart = reader(path);
		if(barChart == null) return;

		SwingUtilities.invokeLater(()->{
			new BarChartDemo(barChart, path).setVisible(true);
		});
	}

	private static BarChart reader(Path path) {
		BarChart barChart = new BarChart();
		try(BufferedReader bufferedReader =Files.newBufferedReader(path)) {
			try {
				String xText = bufferedReader.readLine();
				String yText = bufferedReader.readLine();
				List<XYValue> values= Arrays.stream(bufferedReader.readLine().split(" "))
						.filter(s -> s.length() != 0)
						.map(s -> XYValue.parser(s))
						.collect(Collectors.toList());
				int ymin = (int) Integer.parseInt(bufferedReader.readLine());
				int ymax = (int) Integer.parseInt(bufferedReader.readLine());
				int ydelta = (int) Integer.parseInt(bufferedReader.readLine());
				barChart = new BarChart(values, xText, yText, ymin, ymax, ydelta);
			} catch (IOException e) {
				System.err.println("Datoteka nema dovoljan broj redaak.");
				return null;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				return null;
			}
			
		} catch (IOException e) {
			System.err.println("Datoteka: " + path + " nije pronađena.");
			return null;
		}
		return barChart;
	}
}
