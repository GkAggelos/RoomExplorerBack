package di.uoa.roomexplorer.model;

public enum RoomType {

    PRIVATE("Private room"),
    SHARED("Shared room"),
    HOUSE("Whole building");

    final String type;

    RoomType(String type) { this.type = type; }
}
