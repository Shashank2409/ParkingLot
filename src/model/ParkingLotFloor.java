package model;

import enums.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotFloor {
    private final int floorNo;
    private final int noOfRows;
    private final int noOfColumns;
    private final List<List<ParkingLotSpot>> parkingLotSpots;
    private final Map<VehicleType, Integer> availableSlotsForVehicleType;

    public ParkingLotFloor(List<List<Integer>> parkingLotFloor, final int floorNo) {
        if(parkingLotFloor.size() == 0) {
            throw new IllegalArgumentException("Empty parking lot floor");
        }
        this.floorNo = floorNo;
        this.noOfRows = parkingLotFloor.size();
        this.noOfColumns = parkingLotFloor.get(0).size();
        this.parkingLotSpots = new ArrayList<>();
        this.availableSlotsForVehicleType = new HashMap<>();

        for(int row = 0; row < noOfRows; row++) {
            List<ParkingLotSpot> rowList = new ArrayList<>();
            for(int column = 0; column < noOfColumns; column++) {
                boolean isActiveSlot = !parkingLotFloor.get(row).get(column).equals(0);
                VehicleType vehicleType = null;
                if(isActiveSlot) {
                    vehicleType = VehicleType.fromType(parkingLotFloor.get(row).get(column));
                }
                //TODO: Try to use DI here
                rowList.add(new ParkingLotSpot(row, column, false, isActiveSlot, vehicleType, null, null));
                updateParkingLotSlot(vehicleType, 1);
            }
            parkingLotSpots.add(rowList);
        }
    }

    public void printParkingLotFloorInfo() {
        System.out.printf("\nFloor No: %s, No of row, col: %s, %s\n", floorNo, noOfRows, noOfColumns);
        System.out.println("Available Slots Info: ");
        availableSlotsForVehicleType.forEach((vehicleType, availableSlots) ->
                System.out.printf("VehicleType: %s, Available Slots: %s\n", vehicleType, availableSlots));
    }

    public List<List<ParkingLotSpot>> getParkingLotSpots() {
        return parkingLotSpots;
    }

    public int getNoOfAvailableSlots(VehicleType vehicleType) {
        return availableSlotsForVehicleType.get(vehicleType);
    }

    public String parkOnFirstAvailableSlot(final Vehicle vehicle, final String ticketId) {
        ParkingLotSpot firstAvailableParkingLotSpot = parkingLotSpots.stream().flatMap(List::stream)
                .filter(parkingLotSpot -> vehicle.getVehicleType().equals(parkingLotSpot.getVehicleType()))
                .filter(parkingLotSpot -> !parkingLotSpot.isSlotOccupied()).findFirst().orElse(null);

        System.out.println("firstAvailableParkingLotSpot \n");
        firstAvailableParkingLotSpot.printParkingLotSlotInfo();

        if(firstAvailableParkingLotSpot != null) {
            final String spotId = firstAvailableParkingLotSpot.parkVehicleOnParkingSpot(vehicle, ticketId);
            updateParkingLotSlot(vehicle.getVehicleType(), -1);
            return String.format("%s-%s", floorNo, spotId);
        }
        throw new RuntimeException("No empty slots on this floor");
    }

    public void freeParkingLotSpot(int row, int column) {
        if(row >= parkingLotSpots.size()) {
            throw new IllegalArgumentException("Invalid spot ID");
        }
        if(column >= parkingLotSpots.get(row).size()) {
            throw new IllegalArgumentException("Invalid spot ID");
        }
        ParkingLotSpot parkingLotSpot = parkingLotSpots.get(row).get(column);

        parkingLotSpot.freeParkingLotSpot();
    }

    private void updateParkingLotSlot(VehicleType vehicleType, int updateBy) {
        availableSlotsForVehicleType.merge(vehicleType, updateBy, Integer::sum);
    }

}
