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
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.CashFlow;
import org.ta4j.core.analysis.criteria.AverageProfitCriterion;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.BuyAndHoldCriterion;
import org.ta4j.core.analysis.criteria.LinearTransactionCostCriterion;
import org.ta4j.core.analysis.criteria.MaximumDrawdownCriterion;
import org.ta4j.core.analysis.criteria.NumberOfBarsCriterion;
import org.ta4j.core.analysis.criteria.NumberOfTradesCriterion;
import org.ta4j.core.analysis.criteria.RewardRiskRatioCriterion;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.IsHighestRule;
import org.ta4j.core.trading.rules.IsLowestRule;
import org.ta4j.core.trading.rules.StopLossRule;

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
        // Getting the time series
        TimeSeries series = CsvLoader.load("EEE MMM dd HH:mm:ss z yyyy", "src/main/resources/BTCUSDT.txt", "BTCUSD");
        series = series.getSubSeries(series.getEndIndex()-365, series.getEndIndex());
         /*
            Creating the OHLC dataset
         */
        OHLCDataset ohlcDataset = createOHLCDataset(series);
        
        // Building the trading strategy
        Strategy strategy = buildStrategy(series);
        /*
          Building chart datasets
         */
        //TimeSeriesCollection dataset = new TimeSeriesCollection();
        //dataset.addSeries(BuyAndSellSignalsToChart.buildChartTimeSeries(series, new ClosePriceIndicator(series), "Bitcoin $USD"));
        
        /*
          Creating the chart
         */
        JFreeChart chart = newCandleStickChart("Tape Reader", "Date", "Price", ohlcDataset);

        /*
          Running the strategy and adding the buy and sell signals to plot
         */
        addBuySellSignals(series, strategy, chart);
        
        ApplicationFrame frame = new ApplicationFrame("chart");
        //frame.setPreferredSize(new Dimension(2044, 600));
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());
        
        /*
          Displaying the chart
         */
        ChartPanel chartPanel = newJFreeAppFrame(chart);
        //chartPanel.setPreferredSize(new Dimension(frame.get, 800));
        contentPane.add(chartPanel, BorderLayout.CENTER);

        //Create a yellow label to put in the content pane.
        //Create and set up the content pane.
        DopeBookTable dopeBookTable = new DopeBookTable();
        dopeBookTable.setOpaque(true);
        contentPane.add(dopeBookTable, BorderLayout.EAST);

        frame.setContentPane(contentPane);

        //Set the menu bar and add the label to the content pane.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(Color.BLUE);
        frame.setJMenuBar(greenMenuBar);

        //Display the window.
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }
    
    @SuppressWarnings("unused")
    private static JFreeChart newTimeSeriesChart(String title, String xAxisLbl, String yAxisLbl, TimeSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                            title, // title
                            xAxisLbl, // x-axis label
                            yAxisLbl, // y-axis label
                            dataset, // data
                            true, // create legend?
                            true, // generate tooltips?
                            false // generate URLs?
                            );
            XYPlot plot = (XYPlot) chart.getPlot();
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat("MM-yy"));
            return chart;
    }
    
    private static JFreeChart newCandleStickChart(String title, String xAxisLbl, String yAxisLbl, OHLCDataset ohlcDataset) {
          JFreeChart chart = ChartFactory.createCandlestickChart(
                  title,
                  xAxisLbl,
                  yAxisLbl,
                  ohlcDataset,
                  true);
          // Candlestick rendering
          CandlestickRenderer renderer = new CandlestickRenderer();
          renderer.setUseOutlinePaint(true);
          renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);
          renderer.setAutoWidthFactor(1.0);
          renderer.setVolumePaint(Color.BLUE);
          XYPlot plot = chart.getXYPlot();
          plot.setRenderer(renderer);
          // Misc
          plot.setRangeGridlinePaint(Color.LIGHT_GRAY.brighter());
          NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
          numberAxis.setAutoRangeIncludesZero(false);
          plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
          plot.setRangeCrosshairLockedOnData(true);
          plot.setDomainPannable(true);
          plot.setBackgroundPaint(ChartColor.DARK_BLUE.darker().darker());
          plot.setBackgroundAlpha(0.9f);
          plot.setDomainMinorGridlinesVisible(false);
          plot.setDomainCrosshairLockedOnData(true);
          plot.setForegroundAlpha(0.9f);
          plot.setDomainGridlinePaint(ChartColor.LIGHT_GRAY.brighter());
          //Chart
          chart.setPadding(new RectangleInsets(2,2,2,2));
          return chart;
    }
    
    /**
     * Displays a chart in a frame.
     * @param chart the chart to be displayed
     */
    private static ChartPanel newJFreeAppFrame(JFreeChart chart) {
        // Chart panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setBackground(ChartColor.DARK_BLUE.darker().darker());
        panel.setOpaque(true);
        panel.setZoomOutlinePaint(Color.GREEN);
        panel.setDisplayToolTips(true);
        return panel;
    }
    
    private static Strategy buildStrategy(TimeSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        ClosePriceIndicator closePrices = new ClosePriceIndicator(series);

        // Going long if the close price goes below the min price
        Rule entryRule = new IsHighestRule(closePrices, 12);
        // Going short if the close price goes above the max price
        Rule exitRule = new IsLowestRule(closePrices, 11).or(new StopLossRule(closePrices, 50));
        return new BaseStrategy(entryRule, exitRule, 12);
    }
    
    /**
     * Builds a JFreeChart OHLC dataset from a ta4j time series.
     * @param series a time series
     * @return an Open-High-Low-Close dataset
     */
    private static OHLCDataset createOHLCDataset(TimeSeries series) {
        final int nbBars = series.getBarCount();

        Date[] dates = new Date[nbBars];
        double[] opens = new double[nbBars];
        double[] highs = new double[nbBars];
        double[] lows = new double[nbBars];
        double[] closes = new double[nbBars];
        double[] volumes = new double[nbBars];

        for (int i = 0; i < nbBars; i++) {
            Bar bar = series.getBar(i);
            dates[i] = new Date(bar.getEndTime().toEpochSecond() * 1000);
            opens[i] = bar.getOpenPrice().doubleValue();
            highs[i] = bar.getMaxPrice().doubleValue();
            lows[i] = bar.getMinPrice().doubleValue();
            closes[i] = bar.getClosePrice().doubleValue();
            volumes[i] = bar.getVolume().doubleValue();
        }

        return new DefaultHighLowDataset("btc", dates, highs, lows, opens, closes, volumes);
    }
    
    /**
     * Runs a strategy over a time series and adds the value markers
     * corresponding to buy/sell signals to the plot.
     * @param series a time series
     * @param strategy a trading strategy
     * @param plot the plot
     */
    public static void addBuySellSignals(TimeSeries series, Strategy strategy, JFreeChart chart) {
        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        List<Trade> trades = tradingRecord.getTrades();
        XYPlot plot = chart.getXYPlot();
        // Adding markers to plot
        for (Trade trade : trades) {
            // Buy signal
            double buySignalBarTime = new Day(Date.from(series.getBar(trade.getEntry().getIndex()).getEndTime().toInstant())).getFirstMillisecond();
            Marker buyMarker = new ValueMarker(buySignalBarTime);
            buyMarker.setPaint(ChartColor.DARK_GREEN);
            plot.addDomainMarker(buyMarker);
            // Sell signal
            double sellSignalBarTime = new Day(Date.from(series.getBar(trade.getExit().getIndex()).getEndTime().toInstant())).getFirstMillisecond();
            Marker sellMarker = new ValueMarker(sellSignalBarTime);
            sellMarker.setAlpha(1.0f);
            sellMarker.setPaint(ChartColor.DARK_RED);
            plot.addDomainMarker(sellMarker);
        }
        prtStrategyAnalysis(series, tradingRecord);
    }
    
    private static void prtStrategyAnalysis(TimeSeries series, TradingRecord tradingRecord) {
        //CASH FLOW
        CashFlow cashFlow = new CashFlow(series, tradingRecord);
        System.out.println("START CASH: " + cashFlow.getValue(series.getBeginIndex()));
        // Total profit
        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        System.out.println("Total profit: " + totalProfit.calculate(series, tradingRecord));
        // Number of bars
        System.out.println("Number of bars: " + new NumberOfBarsCriterion().calculate(series, tradingRecord));
        // Average profit (per bar)
        System.out.println("Average profit (per bar): " + new AverageProfitCriterion().calculate(series, tradingRecord));
        // Number of trades
        System.out.println("Number of trades: " + new NumberOfTradesCriterion().calculate(series, tradingRecord));
        // Profitable trades ratio
        System.out.println("Profitable trades ratio: " + new AverageProfitableTradesCriterion().calculate(series, tradingRecord));
        // Maximum drawdown
        System.out.println("Maximum drawdown: " + new MaximumDrawdownCriterion().calculate(series, tradingRecord));
        // Reward-risk ratio
        System.out.println("Reward-risk ratio: " + new RewardRiskRatioCriterion().calculate(series, tradingRecord));
        // Total transaction cost
        System.out.println("Total transaction cost (from $1000): " + new LinearTransactionCostCriterion(5, 0.005).calculate(series, tradingRecord));
        // Buy-and-hold
        System.out.println("Buy-and-hold: " + new BuyAndHoldCriterion().calculate(series, tradingRecord));
        // Total profit vs buy-and-hold
        System.out.println("Custom strategy profit vs buy-and-hold strategy profit: " + new VersusBuyAndHoldCriterion(totalProfit).calculate(series, tradingRecord));
        // End cash
        System.out.println("END CASH: " + cashFlow.getValue(series.getEndIndex()));
    }
    
    private static void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
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
