package utils;

import model.ParkingLot;
import model.ParkingLotFloor;
import model.ParkingLotSpot;
import model.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotManager {

    public static ParkingLot createParkingLot(List<List<List<Integer>>> parkingLotSlots) {
        final List<ParkingLotFloor> parkingLotFloors = IntStream.range(0, parkingLotSlots.size())
                .mapToObj(index -> new ParkingLotFloor(parkingLotSlots.get(index), index)).collect(Collectors.toList());

        return new ParkingLot(parkingLotFloors);
    }

    public static String parkVehicleForStrategy0(final Vehicle vehicle, final String ticketId,
                                                 final List<ParkingLotFloor> parkingLotFloors) {
        ParkingLotFloor parkingLotFloorWithEmptyParkingSpace = parkingLotFloors.stream()
                .filter(parkingLotFloor -> parkingLotFloor.getNoOfAvailableSlots(vehicle.getVehicleType()) > 0)
                .findFirst()
                .orElse(null);

        if(parkingLotFloorWithEmptyParkingSpace != null) {
            final String spotId = parkingLotFloorWithEmptyParkingSpace.parkOnFirstAvailableSlot(vehicle, ticketId);
            return spotId;
        }
        throw new RuntimeException("No parking spot available in parking lot");
    }

    public static String parkVehicleForStrategy1(Vehicle vehicle, String ticketId,
                                                 final List<ParkingLotFloor> parkingLotFloors) {
        Optional<ParkingLotFloor> maximumAvailableSlotParkingLot = parkingLotFloors.stream().max(
                Comparator.comparingInt(parkingLotFloor -> parkingLotFloor.getNoOfAvailableSlots(vehicle.getVehicleType())));
        if(maximumAvailableSlotParkingLot.isPresent()) {
            System.out.println("maximumAvailableSlotParkingLot");
            ParkingLotFloor parkingLotWithMostParkingSpace = maximumAvailableSlotParkingLot.get();
            parkingLotWithMostParkingSpace.printParkingLotFloorInfo();
            final String spotId = parkingLotWithMostParkingSpace.parkOnFirstAvailableSlot(vehicle, ticketId);
            return spotId;
        }

        throw new RuntimeException("No parking spot available in parking lot");
    }

    public static void printParkingLot(final ParkingLot parkingLot) {
        parkingLot.getParkingLotFloors().forEach(parkingLotFloor ->
                parkingLotFloor.getParkingLotSpots().stream()
                        .flatMap(List::stream).forEach(ParkingLotSpot::printParkingLotSlotInfo));
    }

    public static void printParkingLotFloorInfo(final ParkingLot parkingLot) {
        parkingLot.getParkingLotFloors().forEach(ParkingLotFloor::printParkingLotFloorInfo);
    }

    public static int getFloorFromSpotId(String spotId) {
        String[] spotIdParts = spotId.split("-");
        if(spotIdParts.length == 3) {
            return Integer.parseInt(spotIdParts[0]);
        }
        throw new IllegalArgumentException("Invalid spot ID");
    }

    public static int getRowFromSpotId(String spotId) {
        String[] spotIdParts = spotId.split("-");
        if(spotIdParts.length == 3) {
            return Integer.parseInt(spotIdParts[1]);
        }
        throw new IllegalArgumentException("Invalid spot ID");
    }

    public static int getColumnFromSpotId(String spotId) {
        String[] spotIdParts = spotId.split("-");
        if(spotIdParts.length == 3) {
            return Integer.parseInt(spotIdParts[2]);
        }
        throw new IllegalArgumentException("Invalid spot ID");
    }
}
