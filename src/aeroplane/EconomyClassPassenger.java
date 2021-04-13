package aeroplane;

public class EconomyClassPassenger extends Passenger{
  private int age;
  private static int ADULT_AGE_THRESHOLD = 18;

  public EconomyClassPassenger(String firstName, String surname, int age) {
    super(firstName, surname, PassengerType.ECONOMY);
    assert isValidAge(age);
    this.age = age;
  }

  private boolean isValidAge(int age) {
    return age >= 0;
  }

  @Override
  public boolean isAdult() {
    return age >= ADULT_AGE_THRESHOLD;
  }

  @Override
  public String toString() {
    return super.toString() + " age: " + age;
  }
}
