package data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Song {

    private String filePath;
    private String title;
    private int id;

    public Song(String filePath, String title) {
        this.filePath = filePath;
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Filepath: %s\nSongname: %s", filePath, title);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Song(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.title = rs.getString("title");
        this.filePath = rs.getString("filePath");
    }
}
