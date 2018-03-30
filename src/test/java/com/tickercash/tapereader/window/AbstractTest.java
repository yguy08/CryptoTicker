package com.tickercash.tapereader.window;

import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.config.TRConfig;

public class AbstractTest {

    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();
        TRConfig config = new TRConfig();
        config.setPreFeed(true);
        config.setQuoteBoy(QuoteBoyType.CMC);
        System.out.println(yaml.dump(config));
    }

}
