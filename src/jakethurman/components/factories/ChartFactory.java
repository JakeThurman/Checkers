package jakethurman.components.factories;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import jakethurman.components.Point;
import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

public class ChartFactory implements Disposable {
	public ChartDataSeries createDataSeries(String name, Point...points) {
		return new ChartDataSeries(name, points);
	}
	
	@SuppressWarnings("boxing")
	public SafeNode createLineChart(String chartTitle, String xLabel, String yLabel, ChartDataSeries...chartDataObjs) {
		//defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        
        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle(chartTitle);
        
        for (ChartDataSeries cds : chartDataObjs) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(cds.name);
            
            for (Point point : cds.points) {
            	series.getData().add(new XYChart.Data<>(point.x, point.y));
            }
            
            lineChart.getData().add(series);
        }
		
		return new SafeNode(lineChart);
	}
	
	public class ChartDataSeries {
		protected final String  name;
		protected final Point[] points;
		
		public ChartDataSeries(String name, Point...points) {
			this.name   = name;
			this.points = points;
		}
	}
	
	@Override
	public void dispose() {
		//Nothing to dispose
	}
}
