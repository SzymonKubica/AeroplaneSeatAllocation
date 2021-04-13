package aeroplane;

public abstract class Passenger {
  private String firstName;
  private String lastName;
  private PassengerType type;

  public Passenger(String firstName, String lastName, PassengerType type) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.type = type;
  }

  public abstract boolean isAdult();

  @Override
  public String toString() {
    return type + " " + firstName + " " + lastName;
  }

}
