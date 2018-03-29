package com.tickercash.tapereader.clerk.cmc;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.tickercash.tapereader.clerk.HistoricalDataClerk;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.DateTimePatterns;

public class CMCHistoricalDataClerk implements HistoricalDataClerk {
    
    @Override
    public List<Tick> getHistoricalTicks(String symbol, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        String start = startDate.format(DateTimePatterns.YYYYMMDD);
        String end = endDate.format(DateTimePatterns.YYYYMMDD);
        String url = "https://coinmarketcap.com/currencies/"+symbol+"/historical-data/?start="+start+"&end="+end;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementById("historical-data").getElementsByTag("tr");
        return  elements.stream().skip(1)
                .map(td -> td.getElementsByTag("td"))
                .map(t -> new Tick(symbol, "CMCHistorical", cmcDateToEpochSeconds(t.get(0).text()), Double.parseDouble(t.get(4).text())))
                .collect(Collectors.toList());
    }
    
    /**
     * Returns epoch seconds from CMC historical format date (MMM dd, yyyy) 
     * @param date in MMM dd, yyyy format
     * @return epoch seconds
     */
    private static long cmcDateToEpochSeconds(String dateStr){
        LocalDate localDate = LocalDate.parse(dateStr, DateTimePatterns.MMM_dd_COMMA_yyyy);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

}
