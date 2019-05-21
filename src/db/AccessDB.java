package db;

import data.Song;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sql.SongSQL;

public class AccessDB {

    private AccessDB() {
    }

    public static AccessDB getInstance() {
        return AccessDBHolder.INSTANCE;
    }

    private static class AccessDBHolder {

        private static final AccessDB INSTANCE = new AccessDB();
    }

    /*Instanz Methoden*/
    private final DBManager dbm = DBManager.getInstance();

    // Model Testdaten
    public List<Song> createTestdata() throws Exception {
        return Arrays.asList(new Song("C:\\Users\\Rucki\\Desktop\\Songs\\Running in the 90s.mp3", "Running in the 90s"),
                new Song("C:\\Users\\Rucki\\Desktop\\Songs\\Black Rover.mp3", "Black Rover"));
    }

    public List<Song> insertTestdata(List<Song> liste) throws Exception {
        for (Song song : liste) {
            persistEntity(song);
        }
        return liste; //mit id != null
    }

    public List<Song> findAllEmployee() throws Exception {
        List<Song> liste = new ArrayList<>();
        Statement stmt = dbm.createStatement();
        ResultSet rs = stmt.executeQuery(SongSQL.STMT_FIND_ALL.getSql());
        while (rs.next()) { //ist Datensatz vorhanden und setzt auf nächsten Datensatz
            Song e = new Song(rs); //Entity mit Key-Id
            liste.add(e);
        }
        stmt.close(); //Resource
        return liste;
    }

    public void clear() throws Exception {
        Statement stmt = dbm.createStatement();
        int affectedRows = stmt.executeUpdate(" DELETE FROM Employee ");
        System.out.println("Clear affectedRows: " + affectedRows);
    }

    /* CRUD Operation*/
    //C.. persist..Objekt (Entity= wird in der DB persistiert
    //R..load..Datensatz wird von der DB gelesen und als Objekt (Entity) erstellt
    //U--update..aktueller Datensatz (PrimaryKey bleibt gleich) wird geändert
    //D..delete..aktueller Datensatz wird gelöscht => Entity(Objekt) wird null
    public void persistEntity(Song e) throws Exception {
        PreparedStatement pstmt = dbm.createPreparedStatement(SongSQL.PSTMT_INSERT.getSql(), Statement.RETURN_GENERATED_KEYS);
        //Employee-Objekt als Datensatz setzen
        pstmt.setString(1, e.getFilePath()); //?-Parameter-Index
        pstmt.setString(2, e.getTitle());

        int affectedRows = pstmt.executeUpdate(); //Parameter in die Datenbank übertragen
        System.out.println("Persist-affectedRows:" + affectedRows);
        int keyId = dbm.readGeneratedKey(pstmt);
        e.setId(keyId); //the one and only setter for Key-ID
        System.out.println("persist-keyId:" + keyId);
        pstmt.close();

    }

    public Song loadEntity(int index) throws Exception {
        PreparedStatement pstmt = dbm.createPreparedStatement(SongSQL.FIND_BY_ID.getSql());
        pstmt.setInt(1, index);

        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            throw new Exception("ResultSet is null or empty!");
        }
        pstmt.close();
        return new Song(rs);
    }

    public void updateEntity(Song e) throws Exception {
        PreparedStatement pstmt = dbm.createPreparedStatement(SongSQL.PSTMT_UPDATE.getSql());
        //Employee-Objekt als Datensatz setzen
        pstmt.setString(1, e.getFilePath()); //?-Parameter-Index
        pstmt.setString(2, e.getTitle());
        pstmt.setInt(3, e.getId());
        int affectedRows = pstmt.executeUpdate(); //Parameter in die Datenbank übertragen
        System.out.println("update affectedRows: " + affectedRows);
    }

    /**
     * Unit-Test
     */
    public static void main(String[] args) {
        try {
            AccessDB adb = AccessDB.getInstance();
            Song e = new Song("C:\\Users\\Rucki\\Desktop\\Songs\\Running in the 90s.mp3", "Running in the 90s");
            System.out.println("e bevor persist: " + e.toString());
            adb.persistEntity(e);
            System.out.println("e bevor persist: " + e.toString());

//            Employee clone = adb.loadEntity(1);
//            System.out.println("clone load:" + clone.toString());
            //Massentest
            Song clone = new Song(e.getFilePath(), e.getTitle());
            clone.setId(e.getId());
            adb.updateEntity(e);

            List<Song> liste = adb.insertTestdata(adb.createTestdata());
            System.out.println("Testdaten persisted: " + liste);

            liste = adb.findAllEmployee();
            System.out.println("Testdaten loaded: " + liste);
        } catch (Exception e) {
            System.err.println("" + e.getMessage());
        }
    }
}
