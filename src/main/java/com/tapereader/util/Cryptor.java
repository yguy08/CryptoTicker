package com.tapereader.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.text.StrongTextEncryptor;

public class Cryptor {
    
    private static final StandardPBEStringEncryptor ENCRYPTOR = new StandardPBEStringEncryptor();
    
    public static String encryptText(String password, String plainText){
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(password);
        String myEncryptedText = textEncryptor.encrypt(plainText);
        return myEncryptedText;
    }
    
    public static String decryptText(String password, String encryptedText){
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(password);
        String plainText = textEncryptor.decrypt(encryptedText);
        return plainText;
    }
    
    public static String encryptProperty(String password, String plainText) {
        ENCRYPTOR.setPassword(password);
        String enc = ENCRYPTOR.encrypt(plainText);
        return enc;
    }
    
    public static String decryptProperty(String password, String cipherText) {
        ENCRYPTOR.setPassword(password);
        String plainText = ENCRYPTOR.decrypt(cipherText);
        return plainText;
    }
    
    public static Properties getEncryptableProperties(String password, Class<?> clazz, String propName) throws FileNotFoundException, IOException {
        ENCRYPTOR.setPassword(password);
        Properties props = new EncryptableProperties(ENCRYPTOR);
        props.load(new FileInputStream(clazz.getResource(propName).getPath()));
        return props;
    }
    
    public static Properties getEncryptableProperties(String evnVarPW, String propName) throws FileNotFoundException, IOException {
        ENCRYPTOR.setPassword(evnVarPW);
        Properties props = new EncryptableProperties(ENCRYPTOR);
        props.load(new FileInputStream(propName));
        return props;
    }

    public static void main(String[] args) throws Exception {
        // create the command line parser
        CommandLineParser parser = new DefaultParser();

        // create the Options
        Options options = new Options();
        options.addOption( "e", "encrypt", true, "encrypt" );
        options.addOption( "d", "decrypt", true, "decrypt" );
        options.addOption( "v", "envvar", true, "env var containing pw" );
        options.addOption( "p", "decprop", true, "test properties" );

        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            
            if (!line.hasOption("envvar")) {
                throw new Exception("Specify env var w/ pw");
            }
            
            String envPW = System.getenv(line.getOptionValue("envvar"));

            // validate that block-size has been set
            if( line.hasOption( "encrypt" ) ) {
                String plainTxt = line.getOptionValue("encrypt");
                System.out.println( "Plain Text: " + plainTxt );
                String encrypted = encryptProperty(envPW, plainTxt);
                System.out.println( "Cipher Text: " + encrypted );
            }
            
            if( line.hasOption("decrypt")){
                String encrypted = line.getOptionValue("decrypt");
                System.out.println( "Cipher Text: " + encrypted );
                String plainTxt = decryptProperty(envPW, encrypted);
                System.out.println( "Plain Text: " + plainTxt );
            }
            
            if( line.hasOption("decprop")){
                String fn = line.getOptionValue("decprop");
                Properties props = getEncryptableProperties(envPW, fn);
                for (Object obj : props.keySet()) {
                    String value = props.getProperty((String) obj);
                    System.out.println( "KEY: " + (String) obj);
                    System.out.println( "VALUE: " + value);
                }
            }
        } catch( ParseException exp ) {
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }
    }

}
