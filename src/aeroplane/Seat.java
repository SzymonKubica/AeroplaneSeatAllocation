package aeroplane;

import java.util.NoSuchElementException;
import java.util.Set;

public class Seat {
  private int row;
  private Letter letter;

  public static final Seat FIRST_CREW_SEAT = new Seat(1, Letter.A);
  public static final Seat LAST_CREW_SEAT = new Seat(1, Letter.F);

  public static final Seat FIRST_BUSINESS_SEAT = new Seat(2, Letter.A);
  public static final Seat LAST_BUSINESS_SEAT = new Seat(15, Letter.F);

  public static final Seat FIRST_ECONOMY_SEAT = new Seat(16, Letter.A);
  public static final Seat LAST_ECONOMY_SEAT = new Seat(50, Letter.F);

  private Set<Integer> emergencyRowLocations = Set.of(1, 10, 30);

  public Seat(int row, Letter letter) {
    assert 1 <= row && row <= 50;
    this.row = row;
    this.letter = letter;
  }

  public boolean isEmergencyExit() {
    return emergencyRowLocations.contains(row);
  }

  public boolean hasNext() {
    return !isLastSeat();
  }

  public Seat next() throws NoSuchElementException {
    if (!hasNext()) {
      throw new NoSuchElementException("There is no next seat.");
    }
    return nextSeat();
  }

  private Seat nextSeat() {
    if (letter != Letter.F) {
      return new Seat(row, Letter.values()[letter.ordinal() + 1]);
    } else {
      return new Seat(row + 1, Letter.A);
    }
  }

  private boolean isLastSeat() {
    return row == 50 && letter == Letter.F;
  }

  @Override
  public String toString() {
    return Integer.toString(row) + letter.letter;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Seat) {
      return row == ((Seat) other).row && letter == ((Seat) other).letter;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return row * letter.hashCode();
  }

  private enum Letter {
    A('A'), B('B'), C('C'), D('D'), E('E'), F('F');

    private char letter;
    Letter(char letter) {
      this.letter = letter;
    }
  }
}
