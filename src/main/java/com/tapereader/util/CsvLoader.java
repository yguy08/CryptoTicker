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
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.num.BigDecimalNum;
import org.ta4j.core.num.DoubleNum;


public class CsvLoader {

    public static TimeSeries load(String dateFmt, String filename, String seriesName) {
        
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(dateFmt);

        List<Bar> bars = new ArrayList<>();
        try {
            ResultSet rs = new Csv().read(filename, null, null);
            while (rs.next()) {
                ZonedDateTime date = LocalDate.parse(rs.getString(1), DATE_FORMAT).atStartOfDay(ZoneId.systemDefault());
                double open = Double.parseDouble(rs.getString(4));
                double high = Double.parseDouble(rs.getString(2));
                double low = Double.parseDouble(rs.getString(3));
                double close = Double.parseDouble(rs.getString(5));
                double volume = Double.parseDouble(rs.getString(6));

                bars.add(new BaseBar(date, open, high, low, close, volume, BigDecimalNum::valueOf));
            }
        } catch (SQLException ioe) {
            Logger.getLogger(BuyHighSellLow.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(BuyHighSellLow.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }

        return new BaseTimeSeries(seriesName, bars);
    }
}
