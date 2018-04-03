package com.tickercash.tapereader.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.tickercash.tapereader.clerk.QuoteBoyType;

public class ConfigTest {
    @Test
    public void testServerConfig() throws Exception {  
        Yaml yaml = new Yaml();  
        try(InputStream in = Files.newInputStream(Paths.get("src/test/resources/server-test-config.yml"))) {
            Config config = yaml.loadAs(in, Config.class);
            assertNotNull(config);
            assertEquals(QuoteBoyType.FAKE, config.getQuoteBoy());
            assertEquals(5, config.getQuoteThrottle());
            assertEquals("tcp://localhost:61616", config.getWireURL());
        }
    }
    
}
