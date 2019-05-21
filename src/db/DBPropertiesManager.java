
package db;

import dbconfig.ConfigEnum;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DBPropertiesManager {
    
    private DBPropertiesManager() {
    }
    
    public static DBPropertiesManager getInstance() {
        return DBPropertiesManagerHolder.INSTANCE;
    }
    
    private static class DBPropertiesManagerHolder {

        private static final DBPropertiesManager INSTANCE = new DBPropertiesManager();
    }
    
    //Start der Instanz
    public final static String CONFIGPATH = "src";
    private final Map<String,String> propertiesMap = new HashMap<>();
    public void createProperties(ConfigEnum config) throws Exception{
        load(CONFIGPATH + File.separator + config.getFilename());
        //todo: ensureAllKeysAvailable(): //tipp: Verwende eine eigene RequiredKeysEnum(DRIVER,URL,...) 
    }
    
    public void load(String filename) throws Exception{
        
        File file = new File(filename);
        String filepath = file.getAbsolutePath();
        try
            (
               BufferedReader buffy = new BufferedReader(new FileReader(new File(filepath)));
            )
            //Manche Klassen haben ein Autoclose 
            //d.h. keine explizites close notwendig
        {
            String line;
            while((line=buffy.readLine()) != null){
                String[] token = line.split("=");
                propertiesMap.put(token[0], token[1]);
            }
            
            
        }catch(IOException e){
            throw new Exception("Problem by read Properties!");
        }
    }
    
    //Tipp: verwende wie oben die RequiredKeysEnum(URL ("db.url"),...
    public String getUrl(){
        return propertiesMap.get("db.url");
    }
    
    public String getDriver(){
        return propertiesMap.get("db.driver");
    }
    
    public String getUsername(){
        return propertiesMap.get("db.username");
    }
    
    public String getPassword(){
        return propertiesMap.get("db.password");
    }

    @Override
    public String toString() {
        return "DBPropertiesManager{" + "propertiesMap=" + propertiesMap + '}';
    }
    
    
    //Unit Test
    public static void main(String[] args){
        try{
            DBPropertiesManager dbpm = DBPropertiesManager.getInstance();
            dbpm.createProperties(ConfigEnum.DERBY);
            System.out.println("Properties:" + dbpm.toString());
        }catch(Exception e){
            System.err.println("" + e.getMessage());
        }
    }
}
