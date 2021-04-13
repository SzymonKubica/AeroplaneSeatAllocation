package aeroplane;

public class BusinessClassPassenger extends Passenger{
  public Luxury luxury;
  private int age;
  private static int ADULT_AGE_THRESHOLD = 18;

  public BusinessClassPassenger(String firstName, String surname, int age, Luxury luxury) {
    super(firstName, surname, PassengerType.BUSINESS);
    this.age = age;
    this.luxury = luxury;
  }

  @Override
  public boolean isAdult() {
    return age >= ADULT_AGE_THRESHOLD;
  }

  @Override
  public String toString() {
    return super.toString() + " luxury: " + luxury;
  }
}
