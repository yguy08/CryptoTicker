package com.tapereader.gui;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.tapereader.util.BuyAndSellSignalsToChart;
import com.tapereader.util.CsvLoader;

/* TopLevelDemo.java requires no other files.*/
public class TopLevelDemo {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(Color.BLUE);
        //greenMenuBar.setPreferredSize(new Dimension(1280, 800));
        
        //CHART ----
        // Getting the time series
        TimeSeries series = CsvLoader.load("EEE MMM dd HH:mm:ss z yyyy", "src/main/resources/BTCUSDT.txt", "BTCUSD");
        series = series.getSubSeries(series.getEndIndex()-365, series.getEndIndex());
        // Building the trading strategy
        Strategy strategy = BuyAndSellSignalsToChart.buildStrategy(series);

        /*
          Building chart datasets
         */
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(BuyAndSellSignalsToChart.buildChartTimeSeries(series, new ClosePriceIndicator(series), "Bitcoin $USD"));

        /*
          Creating the chart
         */
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Title", // title
                "Date", // x-axis label
                "Price", // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
                );
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-yy"));
        /*
          Running the strategy and adding the buy and sell signals to plot
         */
        BuyAndSellSignalsToChart.addBuySellSignals(series, strategy, plot);

        /*
          Displaying the chart
         */
        ChartPanel panel = BuyAndSellSignalsToChart.newJFreeAppFrame(chart);        
        //Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(LineBorder.createGrayLineBorder());
        contentPane.add(panel, BorderLayout.WEST); 

        //Create a yellow label to put in the content pane.
        //Create and set up the content pane.
        DopeBookTable dopeBookTable = new DopeBookTable();
        dopeBookTable.setOpaque(true);
        contentPane.add(dopeBookTable, BorderLayout.EAST);
        
        ApplicationFrame frame = new ApplicationFrame("chart"); 
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(contentPane);

        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(greenMenuBar);

        //Display the window.
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    } 
}
