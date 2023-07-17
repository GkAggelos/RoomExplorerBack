package di.uoa.roomexplorer.model;

public enum RoomType {

    PRIVATE("Private room"),
    SHARED("Shared room"),
    HOUSE("Whole building");

    private final String type;

    RoomType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
