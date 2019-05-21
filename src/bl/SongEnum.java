package bl;

public enum SongEnum {

    TITLE("Title");

    private String name;

    private SongEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
