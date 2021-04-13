package aeroplane;

public enum PassengerType {
  CREW("Crew"), BUSINESS("Business Class"), ECONOMY("Economy Class");

  private String typeName;

  PassengerType(String typeName) {
    this.typeName = typeName;
  }

  @Override
  public String toString() {
    return typeName;
  }
}
