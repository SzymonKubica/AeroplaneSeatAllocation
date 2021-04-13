package aeroplane;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.println("Program should be invoked with exactly one argument, the name of the data file");
      System.exit(1);
    }

    SeatAllocator allocator = new SeatAllocator();

    try {
      allocator.allocate(args[0]);
    } catch (IOException e) {
      System.out.println("An IO exception occurred");
      System.exit(1);
    } catch (AeroplaneFullException e) {
      System.out.println("Unable to allocate all passengers");
    }

    System.out.println(allocator);

    // TODO: Section A, Task 5 - use upgrade method
    allocator.upgrade();
    System.out.println(allocator);

    Passenger crewMember = new CrewMember("", "");
    Passenger economyPassenger = new EconomyClassPassenger("a", "b", 15 );
    Passenger businessPassenger = new BusinessClassPassenger("c", "c", 22, Luxury.CHAMPAGNE);

    Set<Passenger> test = Set.of(crewMember, economyPassenger, businessPassenger);

    System.out.println(countAdults(test));

    EconomyClassPassenger economyPassenger2 = new EconomyClassPassenger("a", "b", 15 );

    Set<EconomyClassPassenger> test2 = Set.of(economyPassenger2);

    System.out.println(countAdults(test2));


  }

  public static int countAdults(Set<? extends Passenger> passengers) {
    return passengers.stream().filter(passenger -> passenger.isAdult()).collect(Collectors.toSet()).size();
  }

}
