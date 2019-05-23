package db;

import dbconfig.ConfigEnum;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

     private final DBPropertiesManager dbpm = DBPropertiesManager.getInstance();;
    private final Connection con;

    private DBManager() {
        try
        {
        //dbpm = DBPropertiesManager.getInstance();
            dbpm.createProperties(ConfigEnum.DERBY); //<= hier her, d.h. bevor getDriver verwendet wird !!
        Class.forName(dbpm.getDriver());
       con = DriverManager.getConnection(dbpm.getUrl(),
                dbpm.getUsername(), 
                dbpm.getPassword());
            System.out.println("Fertig! Geladen und Connection!!");
        }catch(Exception e)
        {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }

//    public Connection getConnection() {
//        return connection;
//    }
    public Statement createStatement() throws SQLException {
        return con.createStatement();
    }

    public PreparedStatement createPreparedStatement(String sql, int... params)
            throws SQLException {
        switch (params.length) {
            case 0:
                return con.prepareStatement(sql);
            default:
                return con.prepareStatement(sql, params[0]);
            //anderen Params werden ignoriert  
        }

    }
    
    public int readGeneratedKey(Statement stmt) throws Exception {
        ResultSet rs = stmt.getGeneratedKeys(); //ResultSet wie Iterator
        if (rs.next()) { //next() bringt Zeiger auf Ersten bzw. Nächsten Datensatz
            int id = rs.getInt(1); //Index startet bei 1!!!
            return id;
        }
        throw new Exception("No generated Key Id");
    }

    public void close() throws Exception {
        con.close();
    }
    //Transactionhandling

    public void setAutoCommit(boolean isAuto)
            throws Exception {
        con.setAutoCommit(isAuto);
    }

    public void commit()
            throws Exception {
        con.commit();
    }
}
