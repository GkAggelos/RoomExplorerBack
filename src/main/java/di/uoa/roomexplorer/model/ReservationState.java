package di.uoa.roomexplorer.model;

public enum ReservationState {

    PENDING("Reservation request pending"),
    ACCEPTED("Reservation request accepted"),
    REJECTED("Reservation request rejected");

    final String state;

    ReservationState(String state) {
        this.state = state;
    }
}
