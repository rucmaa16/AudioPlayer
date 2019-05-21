
package dbconfig;


public enum ConfigEnum {
    DERBY("dbconfig/derby.db.properties"),
    POSTGRES("dbconfig/postgres.db.properties");
    
    String filename;

    private ConfigEnum(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
    
    
}
