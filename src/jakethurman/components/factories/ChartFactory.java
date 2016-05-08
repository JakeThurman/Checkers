package jakethurman.components.factories;

import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

/* 
 * Handles the generation of complex charts using simplistic data. 
 * Really we're just creating a simple wrapper and helpers arround
 * the javafx charting API, but that is beautiful to me!
 */
public class ChartFactory implements Disposable {
	// Creates a new ChartDataSeries from a name and a point map<x, y>
	public ChartDataSeries createDataSeries(String name, Map<Double, Double> points) {
		return new ChartDataSeries(name, points);
	}
	
	// Creates 
	public SafeNode createLineChart(String chartTitle, String xLabel, String yLabel, ChartDataSeries...chartDataObjs) {
		// First, define each axis
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        // And set their labels as needed
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        
        // Create the base line chart object
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis,yAxis);
                
        // Set the chart title
        lineChart.setTitle(chartTitle);
        
        // Add each data series
        for (ChartDataSeries cds : chartDataObjs) {
        	// Map the input data to data usable by the charting API
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(cds.name); // Set the name of series!
            
            // Loop through the points in the input data series
            // and add them to the output data series.
            for (Double point : cds.points.keySet().toArray(new Double[0])) {
            	series.getData().add(new XYChart.Data<>(point, cds.points.get(point)));
            }
            
            // Add the series to the line chart
            lineChart.getData().add(series);
        }
		
        // Return a protected version of the chart's node
		return new SafeNode(lineChart);
	}
	
	// Helper class for chart generation
	public class ChartDataSeries {
		protected final String  name;
		protected final Map<Double, Double> points;
		
		public ChartDataSeries(String name, Map<Double, Double> points) {
			this.name   = name;
			this.points = points;
		}
	}
	
	@Override
	public void dispose() {
		//Nothing to dispose
	}
}
