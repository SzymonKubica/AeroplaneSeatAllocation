package aeroplane;

public class CrewMember extends Passenger{
  public CrewMember(String firstName, String surname) {
    super(firstName, surname, PassengerType.CREW);
  }

  @Override
  public boolean isAdult() {
    return true;
  }

  @Override
  public String toString() {
    return "Crew " + super.toString();
  }
}
