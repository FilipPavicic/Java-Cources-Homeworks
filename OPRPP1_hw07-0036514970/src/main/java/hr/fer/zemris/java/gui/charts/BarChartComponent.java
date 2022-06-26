package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	final int PLOT_BORDER_LARGE = 40;
	final int PLOT_BORDER_SMALL = 10;
	final int SPACE = 5;
	final int HATCH_MARK = 8;
	final int TRIANGLE_HEIGHT = 10;
	
	Font fontNumber = new Font("Arial", Font.BOLD, 18);
	FontMetrics fontMetricsNumber = getFontMetrics(fontNumber);
	Integer maxX;

	BarChart barChart;

	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
		maxX = Collections.max(barChart.values.stream().map(e -> e.getX()).collect(Collectors.toList()));
		//setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(
				PLOT_BORDER_LARGE + PLOT_BORDER_SMALL + barChart.values.size() * (8 * SPACE +  fontMetricsNumber.stringWidth(maxX.toString())),
				(int) (PLOT_BORDER_LARGE + PLOT_BORDER_SMALL + (barChart.Ymax * 1.0 / barChart.Ydelta ) * (4 * SPACE + 10))
		);
	}

	@Override
	public Dimension getMinimumSize(){
		return new Dimension(
				PLOT_BORDER_LARGE + PLOT_BORDER_SMALL + barChart.values.size() * (2 * SPACE +  fontMetricsNumber.stringWidth(maxX.toString())),
				(int) (PLOT_BORDER_LARGE + PLOT_BORDER_SMALL + (barChart.Ymax * 1.0 / barChart.Ydelta ) * ( SPACE + 10))
		);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		
		Rectangle r = new Rectangle(ins.left, ins.top, dim.width-ins.left-ins.right, dim.height-ins.top-ins.bottom);
		Rectangle plotR = new Rectangle(
				r.x + PLOT_BORDER_LARGE, 
				r.y + PLOT_BORDER_SMALL, 
				r.width - PLOT_BORDER_LARGE -  PLOT_BORDER_SMALL, 
				r.height - PLOT_BORDER_LARGE -PLOT_BORDER_SMALL );
		
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		
		//text on axis
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		
		g2d.setTransform(at);
		int widthTextY =  g.getFontMetrics().stringWidth(barChart.yText);
		g2d.drawString(barChart.yText, (int) ((r.getHeight() / 2 + widthTextY - r.x - PLOT_BORDER_SMALL) * -1), (int) (PLOT_BORDER_LARGE / 1.5));
		
		g2d.setTransform(defaultAt);
		int widthTextX =  g.getFontMetrics().stringWidth(barChart.xText);
		g2d.drawString(barChart.xText, (int) (plotR.getWidth() /2 - widthTextX / 2 + r.y + PLOT_BORDER_LARGE), (int) (r.height - PLOT_BORDER_LARGE / 3));

		
		//obrisati
		//g2d.draw(plotR);
	
		//veličina koliko zauzima najveći broj
		int maxNumberWidth = fontMetricsNumber.stringWidth(Integer.valueOf(barChart.Ymax).toString());
		int maxNumberHeigth = fontMetricsNumber.getAscent();
		
		
		g2d.setColor(Color.GRAY);
		
		//axisY - početna točka je zapravo gore zato što Y os ide od gore prema dolje
		StartEndLine axisY = new StartEndLine(
				plotR.x + maxNumberWidth + SPACE + HATCH_MARK,
				plotR.y + TRIANGLE_HEIGHT,
				plotR.x + maxNumberWidth + SPACE + HATCH_MARK,
				plotR.y +  plotR.height - HATCH_MARK - SPACE - maxNumberHeigth
		);
		//linija
		g2d.drawLine(axisY.x1(),axisY.y1(), axisY.x2(), axisY.y2());
		//troktic
		List<DoublePoint> triangleY = triangle(new DoublePoint(axisY.x1(), axisY.y1()), TRIANGLE_HEIGHT, Math.PI);
		g2d.fillPolygon(
				triangleY.stream().mapToInt(t ->(int) Math.round(t.x)).toArray(), 
				triangleY.stream().mapToInt(t ->(int) Math.round(t.y)).toArray(),
				3);
		
		
		//axisX
		StartEndLine axisX = new StartEndLine(
				plotR.x + maxNumberWidth + SPACE + HATCH_MARK,
				plotR.y +  plotR.height - HATCH_MARK - SPACE - maxNumberHeigth,
				plotR.x + plotR.width - TRIANGLE_HEIGHT,
				plotR.y +  plotR.height - HATCH_MARK - SPACE - maxNumberHeigth
		);
		//linija
		g2d.drawLine(axisX.x1(), axisX.y1(), axisX.x2(), axisX.y2());
		//trokutic
		List<DoublePoint> triangleX = triangle(new DoublePoint(axisX.x2(), axisX.y2()), TRIANGLE_HEIGHT, Math.PI/2);
		g2d.fillPolygon(
				triangleX.stream().mapToInt(t ->(int) Math.round(t.x)).toArray(), 
				triangleX.stream().mapToInt(t ->(int) Math.round(t.y)).toArray(),
				3);
		
		g2d.setColor(Color.BLACK);
		
		
		int yAxisHeigthWithSpace = axisY.y2() - axisY.y1() - SPACE;
		
		int yAxisDelta = (int) (yAxisHeigthWithSpace  / (barChart.Ymax * 1.0 / barChart.Ydelta ));
		
		//koristi ce pri crtanju stupaca te oznaćava koliko piksela iznosi jedinična duljina
		double Delta = yAxisDelta * 1.0 / barChart.Ydelta;
		
		int yLine = axisY.y2();
		int yNumber = (int) (yLine + Math.round(maxNumberHeigth / 2.0) - 1);
		int xHatchStart = axisY.x1() - HATCH_MARK;
		int yMaxSize = Integer.valueOf(barChart.Ymax).toString().length();
		
		for (int i = barChart.Ymin; i <= barChart.Ymax; i += barChart.Ydelta ) {
			
			String iS = Integer.valueOf(i).toString();
			AttributedString number = new AttributedString(iS);
			number.addAttribute(TextAttribute.FONT, fontNumber);
			
			g2d.drawString(number.getIterator(), plotR.x - fontMetricsNumber.stringWidth("0") * (iS.length() - yMaxSize), yNumber);
			
			g2d.setColor(Color.GRAY);
			g2d.drawLine(xHatchStart, yLine, axisX.x1() ,yLine);
			
			if(i != barChart.Ymin) {
				g2d.setColor(Color.ORANGE);
				g2d.drawLine(axisY.x1(), yLine, axisX.x2(), yLine);
			}
			
			g2d.setColor(Color.BLACK);
			yLine -= yAxisDelta;
			yNumber -= yAxisDelta;
		}
		

		
		//Lista pocetnih kordinata za svaku vrijednost na x osi
		List<XYValue> starts = new ArrayList<XYValue>();
		starts.add(new XYValue(axisX.x1(), axisX.y1()));
		
		int xAsisWidthWithSpace = axisX.x2() - axisX.x1() - SPACE;
		int xAsisDelta = xAsisWidthWithSpace / barChart.values.size();
		int xLine = axisX.x1();
		int xNumber = xLine + xAsisDelta / 2;
		
		g2d.drawLine(xLine, axisX.y1(), xLine, axisX.y1() + HATCH_MARK);
		
		for (int i = 0; i < barChart.values.size(); i++) {
			
			String iS = Integer.valueOf(barChart.values.get(i).getX()).toString();
			AttributedString number = new AttributedString(iS);
			number.addAttribute(TextAttribute.FONT, fontNumber);
			int iSWidth = fontMetricsNumber.stringWidth(iS);
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(number.getIterator(),  xNumber - iSWidth / 2, plotR.y + plotR.height);
			
			xNumber += xAsisDelta;
			xLine += xAsisDelta;
			
			g2d.setColor(Color.GRAY);
			g2d.drawLine(xLine, axisX.y1(), xLine, axisX.y1() + HATCH_MARK);
			g2d.setColor(Color.ORANGE);
			g2d.drawLine(xLine, axisX.y1(), xLine, axisY.y1());
			
			starts.add(new XYValue(xLine, axisX.y1()));
		}
		
		
		for (int i = 0; i < barChart.values.size(); i++) {
			g2d.setColor(Color.BLUE);
			
			int[] xs = new int[] {
					starts.get(i).getX() + 1,
					starts.get(i +1).getX() ,
					starts.get(i +1).getX() ,
					starts.get(i).getX() + 1,
			};
			int[] ys = new int[] {
					starts.get(i).getY(),
					starts.get(i +1).getY(),
					(int) (starts.get(i).getY() - Delta * barChart.values.get(i).getY()),
					(int) (starts.get(i).getY() - Delta * barChart.values.get(i).getY())
			};
			g2d.fillPolygon(xs, ys, 4);
		}
		
		
	}
	
	private List<DoublePoint> triangle(DoublePoint start, int height, double rotation){
		List<DoublePoint> lista = Arrays.asList(new DoublePoint[] {
				new DoublePoint(height / Math.sqrt(3), 0),
				new DoublePoint(-height / Math.sqrt(3), 0),
				new DoublePoint(0, height)
				
		});
		lista.replaceAll(d -> d.rotateClockwise(rotation).sum(start));
		return lista;
	}



}
