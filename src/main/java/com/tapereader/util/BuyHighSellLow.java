package com.tapereader.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.tools.Csv;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.TradingRecord;
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
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.indicators.helpers.MaxPriceIndicator;
import org.ta4j.core.indicators.helpers.MinPriceIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.trading.rules.IsHighestRule;
import org.ta4j.core.trading.rules.IsLowestRule;

public class BuyHighSellLow {
    
    // We assume that there were at least one trade every 5 minutes during the whole week
    private static final int BARS_25 = 25;
    
    /**
     * @param series a time series
     * @return a global extrema strategy
     */
    public static Strategy buildStrategy(TimeSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        ClosePriceIndicator closePrices = new ClosePriceIndicator(series);

        // Getting the max price over the past week
        MaxPriceIndicator maxPrices = new MaxPriceIndicator(series);
        HighestValueIndicator dayMaxPrice = new HighestValueIndicator(maxPrices, BARS_25);
        // Getting the min price over the past week
        MinPriceIndicator minPrices = new MinPriceIndicator(series);
        LowestValueIndicator dayMinPrice = new LowestValueIndicator(minPrices, BARS_25);

        // Going long if the close price goes below the min price
        Rule buyingRule = new IsHighestRule(closePrices, BARS_25);
        
        // Going short if the close price goes above the max price
        Rule sellingRule = new IsLowestRule(closePrices, 11);

        return new BaseStrategy(buyingRule, sellingRule);
    }
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");

    /**
     * @return a time series from Apple Inc. bars.
     */
    public static TimeSeries loadNxt() {

        List<Bar> bars = new ArrayList<>();
        try {
            ResultSet rs = new Csv().read("src/main/resources/ETHBTC.txt", null, null);
            while (rs.next()) {
                ZonedDateTime date = LocalDate.parse(rs.getString(1), DATE_FORMAT).atStartOfDay(ZoneId.systemDefault());
                double open = Double.parseDouble(rs.getString(4));
                double high = Double.parseDouble(rs.getString(2));
                double low = Double.parseDouble(rs.getString(3));
                double close = Double.parseDouble(rs.getString(5));
                double volume = Double.parseDouble(rs.getString(6));

                bars.add(new BaseBar(date, open, high, low, close, volume, DoubleNum::valueOf));
            }
        } catch (SQLException ioe) {
            Logger.getLogger(BuyHighSellLow.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(BuyHighSellLow.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }

        return new BaseTimeSeries("nxt_bars", bars);
    }
    
    public static void main(String[] args) {
      // Getting the time series
      TimeSeries series = BuyHighSellLow.loadNxt();
    
      // Building the trading strategy
      Strategy strategy = buildStrategy(series);
    
      // Running the strategy
      TimeSeriesManager seriesManager = new TimeSeriesManager(series);
      TradingRecord tradingRecord = seriesManager.run(strategy);
        
      /*
        Analysis criteria
      */
    
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
      System.out.println("Total transaction cost (from $1000): " + new LinearTransactionCostCriterion(2, 0.005).calculate(series, tradingRecord));
      // Buy-and-hold
      System.out.println("Buy-and-hold: " + new BuyAndHoldCriterion().calculate(series, tradingRecord));
      // Total profit vs buy-and-hold
      System.out.println("Custom strategy profit vs buy-and-hold strategy profit: " + new VersusBuyAndHoldCriterion(totalProfit).calculate(series, tradingRecord));
    }

}
