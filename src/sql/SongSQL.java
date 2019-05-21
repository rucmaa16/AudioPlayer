package sql;

public enum SongSQL {
    STMT_FIND_ALL("SELECT * from Songliste"),
    STMT_DELETE("DELETE from Songliste"),
    PSTMT_DELETE_BY_ID("DELETE from Songliste WHERE id=?"),
    PSTMT_INSERT(" INSERT INTO Songliste ( "
            + " filePath, title ) "
            + " VALUES (?, ? )"),
    FIND_BY_ID(" SELECT * FROM Songliste WHERE id = ? "),
    PSTMT_UPDATE(" UPDATE Songliste SET "
            + " filePath=?, title=?,  "
            + " WHERE id = ?");

    private String sql;

    private SongSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
