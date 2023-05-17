import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class ConferenceRoom {
    private String name;
    private int capacity;
    Map<LocalDate, String> reservations;

    public ConferenceRoom(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.reservations = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable(LocalDate date) {
        return !reservations.containsKey(date);
    }

    public void reserve(LocalDate date, String user) {
        reservations.put(date, user);
    }

    public void cancel(LocalDate date) {
        reservations.remove(date);
    }
}

class ConferenceRoomBookingSystem {
    private List<ConferenceRoom> conferenceRooms;
    private DateTimeFormatter dateFormatter;

    public ConferenceRoomBookingSystem() {
        this.conferenceRooms = new ArrayList<>();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void addConferenceRoom(ConferenceRoom conferenceRoom) {
        conferenceRooms.add(conferenceRoom);
    }

    public void displayConferenceRooms() {
        System.out.println("Dostępne sale konferencyjne:");
        for (int i = 0; i < conferenceRooms.size(); i++) {
            ConferenceRoom room = conferenceRooms.get(i);
            System.out.println((i + 1) + ". " + room.getName() + " (Pojemność: " + room.getCapacity() + ")");
        }
    }

    public void makeReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wybierz numer sali konferencyjnej:");
        displayConferenceRooms();
        int roomIndex = scanner.nextInt() - 1;

        ConferenceRoom conferenceRoom = conferenceRooms.get(roomIndex);

        System.out.print("Podaj datę rezerwacji (dd-MM-yyyy): ");
        String dateString = scanner.next();
        LocalDate date = LocalDate.parse(dateString, dateFormatter);

        if (conferenceRoom.isAvailable(date)) {
            scanner.nextLine(); // Clear the input buffer
            System.out.print("Podaj swoje imię i nazwisko: ");
            String userName = scanner.nextLine();

            conferenceRoom.reserve(date, userName);
            System.out.println("Rezerwacja potwierdzona: " + conferenceRoom.getName() + " na " + dateString +
                    " dla " + userName);
        } else {
            System.out.println("Wybrana sala konferencyjna jest niedostępna w podanym terminie.");
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wybierz numer sali konferencyjnej:");
        displayConferenceRooms();
        int roomIndex = scanner.nextInt() - 1;

        ConferenceRoom conferenceRoom = conferenceRooms.get(roomIndex);

        System.out.print("Podaj datę anulowania rezerwacji (dd-MM-yyyy): ");
        String dateString = scanner.next();
        LocalDate date = LocalDate.parse(dateString, dateFormatter);

        if (conferenceRoom.isAvailable(date)) {
            System.out.println("Brak rezerwacji do anulowania w podanym terminie.");
        } else {
            String userName = conferenceRoom.reservations.get(date);
            conferenceRoom.cancel(date);
            System.out.println("Anulowano rezerwację dla " + userName + " na " +
                    dateString + " w sali " +
                    conferenceRoom.getName());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ConferenceRoomBookingSystem bookingSystem = new ConferenceRoomBookingSystem();
        // Dodaj przykładowe sale konferencyjne
        ConferenceRoom room1 = new ConferenceRoom("Sala A", 50);
        ConferenceRoom room2 = new ConferenceRoom("Sala B", 30);
        ConferenceRoom room3 = new ConferenceRoom("Sala C", 20);
        bookingSystem.addConferenceRoom(room1);
        bookingSystem.addConferenceRoom(room2);
        bookingSystem.addConferenceRoom(room3);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SYSTEM REZERWACJI SAL KONFERENCYJNYCH ---");
            System.out.println("1. Wyświetl dostępne sale");
            System.out.println("2. Dokonaj rezerwacji");
            System.out.println("3. Anuluj rezerwację");
            System.out.println("0. Wyjście");
            System.out.print("Wybierz opcję: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Clear the input buffer

            switch (option) {
                case 1:
                    bookingSystem.displayConferenceRooms();
                    break;
                case 2:
                    bookingSystem.makeReservation();
                    break;
                case 3:
                    bookingSystem.cancelReservation();
                    break;
                case 0:
                    System.out.println("Dziękujemy za skorzystanie z systemu rezerwacji sal konferencyjnych. Do widzenia!");
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja. Wybierz ponownie.");
            }
        }
    }
}