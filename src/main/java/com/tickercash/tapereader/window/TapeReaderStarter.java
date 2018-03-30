package com.tickercash.tapereader.window;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.config.TRConfig;

public class TapeReaderStarter {
    
    public static void main(String[] args) throws Exception {
        if( args.length != 1 ) {
            System.out.println( "Usage: <file.yml>" );
            return;
        }
  
        Yaml yaml = new Yaml();  
        try(InputStream in = Files.newInputStream(Paths.get(args[0]))) {
            TRConfig config = yaml.loadAs(in, TRConfig.class);
            TapeReader reader = new TapeReader();
            reader.setConfig(config);
            reader.readTheTape();
        }
    }

}
