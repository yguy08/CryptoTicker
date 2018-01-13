package com.tickercash.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Server;

import com.tickercash.model.Tick;
import com.tickercash.tapereader.FakeTicker;
import com.tickercash.tapereader.TapeReaderClerk;

public class CMCPersistantTicker extends TapeReaderClerk {
    
    private final Logger LOGGER = LogManager.getLogger("TapeReaderClerk");
    private Server server;    
    private String connection = "jdbc:h2:tcp://localhost:8089/" + System.getProperty("user.home") + "/tickercash-master/ticks";
    private String driver = "org.h2.Driver";
    
    public CMCPersistantTicker() throws Exception {
        dbSetUp();        
        setName("CMCPersistantTicker");
        setTicker(new FakeTicker());        
    }
    
    public void run(){
        readTheTape();
    }
    
    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("New Tick: {}", event);
        save(event);
    }
    
    private void dbSetUp() throws SQLException{
        server = Server.createTcpServer("-tcpPort", "8089", "-tcpAllowOthers");
        server.start();
        createTable();
    }

    private void save(Tick event) {
        String sqlCommand = "INSERT INTO ticks(SYMBOL,TIMESTAMP,LAST) VALUES(?,?,?)";
        try {
            Connection connection = connect();
            PreparedStatement tmpStatement = connection.prepareStatement(sqlCommand);
            tmpStatement.setString(1,event.getSymbol());
            tmpStatement.setLong(2,event.getTimestamp());
            tmpStatement.setDouble(3, event.getLast());
            tmpStatement.execute();
            tmpStatement.close();
            connection.close();
        } catch (SQLException ex) {
            
        }
    }
    
    private void createTable(){
        String strSql = "CREATE TABLE IF NOT EXISTS Ticks (\n"
                + "Symbol character NOT NULL,\n"
                + "Timestamp long NOT NULL,\n"
                + "Last double NOT NULL,\n"
                + "Volume int,\n"
                + ");";
        try {
            Connection connection = connect();
            Statement tmpStatement = connection.createStatement();
            tmpStatement.executeUpdate(strSql);
            tmpStatement.close();
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Error creating table");
        }
    }
    
    private Connection connect(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connection);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException("Error Connecting to DB");
        }
        return conn;
    }
    
    public static void main(String[] args){
        try {
            new CMCPersistantTicker().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
