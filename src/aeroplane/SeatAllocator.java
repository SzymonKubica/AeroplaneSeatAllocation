package aeroplane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeatAllocator {
  private Map<Seat, Passenger> allocation;

  private static final String CREW = "crew";
  private static final String BUSINESS = "business";
  private static final String ECONOMY = "economy";


  public SeatAllocator() {
    allocation = new HashMap<Seat, Passenger>();
  }

  private void allocateInRange(Passenger passenger,
                               Seat first, Seat last) throws AeroplaneFullException {

    Seat firstFreeSeat = findFirstFreeSeat(first, last);

    if (!passenger.isAdult() && firstFreeSeat.isEmergencyExit()) {
      allocateInRange(passenger, firstFreeSeat.next(), last);
    } else {
      allocation.put(firstFreeSeat, passenger);
    }
  }

  private Seat findFirstFreeSeat(Seat current, Seat last) throws AeroplaneFullException {
    if (!allocation.containsKey(current)) {
      return current;
    } else {
      if (current.equals(last)) {
        throw new AeroplaneFullException();
      }
      Seat nextSeat = getNext(current);
      return findFirstFreeSeat(nextSeat, last);
    }
  }

  private Seat getNext(Seat current) throws AeroplaneFullException {
    if (current.hasNext()) {
      return current.next();
    } else {
      throw new AeroplaneFullException();
    }
  }

  private static String readStringValue(BufferedReader br) throws MalformedDataException, IOException {

    String result = br.readLine();

    if (result == null) {
      throw new MalformedDataException();
    }

    return result;

  }

  private static int readIntValue(BufferedReader br)
          throws MalformedDataException, IOException {
    try {
      return Integer.parseInt(readStringValue(br));
    } catch (NumberFormatException e) {
      throw new MalformedDataException();
    }
  }

  private static Luxury readLuxuryValue(BufferedReader br)
          throws MalformedDataException, IOException {
    try {
      return Luxury.valueOf(readStringValue(br));
    } catch (IllegalArgumentException e) {
      throw new MalformedDataException();
    }
  }


  public void allocate(String filename) throws IOException, AeroplaneFullException {

    BufferedReader br = new BufferedReader(new FileReader(filename));

    String line;
    while ((line = br.readLine()) != null) {
      try {
        if (line.equals(CREW)) {
          allocateCrew(br);
        } else if (line.equals(BUSINESS)) {
          allocateBusiness(br);
        } else if (line.equals(ECONOMY)) {
          allocateEconomy(br);
        } else {
          throw new MalformedDataException();
        }
      } catch (MalformedDataException e) {
        System.out.println("Skipping malformed line of input");
      }
    }

  }

  private void allocateCrew(BufferedReader br) throws IOException, MalformedDataException, AeroplaneFullException {
    String firstName = readStringValue(br);
    String lastName = readStringValue(br);

    Passenger crewMember = new CrewMember(firstName, lastName);
    allocateInRange(crewMember, Seat.FIRST_CREW_SEAT, Seat.LAST_CREW_SEAT);
  }

  private void allocateBusiness(BufferedReader br) throws IOException, MalformedDataException, AeroplaneFullException {
    String firstName = readStringValue(br);
    String lastName = readStringValue(br);
    int age = readIntValue(br);
    Luxury luxury = readLuxuryValue(br);

    Passenger businessPassenger = new BusinessClassPassenger(firstName, lastName, age, luxury);
    allocateInRange(businessPassenger, Seat.FIRST_BUSINESS_SEAT, Seat.LAST_BUSINESS_SEAT);
  }

  private void allocateEconomy(BufferedReader br) throws IOException, MalformedDataException, AeroplaneFullException {
    String firstName = readStringValue(br);
    String lastName = readStringValue(br);
    int age = readIntValue(br);

    Passenger economyPassenger = new EconomyClassPassenger(firstName, lastName, age);
    allocateInRange(economyPassenger, Seat.FIRST_ECONOMY_SEAT, Seat.LAST_ECONOMY_SEAT);
  }

  public void upgrade() {
    List<Passenger> economyPassengers = getEconomyPassengers();
    for (Passenger economyPassenger : economyPassengers) {
      try {
        Seat previousSeat = getPreviousSeat(economyPassenger);
        allocateInRange(economyPassenger, Seat.FIRST_BUSINESS_SEAT, Seat.LAST_BUSINESS_SEAT);
        allocation.remove(previousSeat);
      } catch (AeroplaneFullException e) {
        // Upgrade is impossible, no free business seats. Loop terminates
        break;
      }
    }
  }

  private List<Passenger> getEconomyPassengers() {
    Seat current = Seat.FIRST_ECONOMY_SEAT;
    List<Passenger> passengers = new LinkedList<>();
    if (allocation.containsKey(current)) {
      passengers.add(allocation.get(current));
    }
    while (current.hasNext()) {
      current = current.next();
      if (allocation.containsKey(current)) {
        passengers.add(allocation.get(current));
      }
    }
    return passengers;
  }

  private Seat getPreviousSeat(Passenger economyPassenger) {
    return allocation.entrySet().stream().filter(
            seatPassengerEntry -> seatPassengerEntry.getValue().equals(economyPassenger)
            ).collect(Collectors.toList()).get(0).getKey();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Seat current = Seat.LAST_CREW_SEAT;
    sb.append("Seat ").append(current).append(": ").append(allocation.get(current)).append("\n");
    while (current.hasNext()) {
      current = current.next();
      sb.append("Seat ").append(current).append(": ").append(allocation.get(current)).append("\n");
    }
    return sb.toString();
  }
}
