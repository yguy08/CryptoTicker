package com.tickercash.tapereader.window;

import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.ticker.QuoteBoyType;

public class AbstractTest {

    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();
        Config config = new Config();
        config.setPreFeed(true);
        System.out.println(yaml.dump(config));
    }

}
