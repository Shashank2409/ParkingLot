package model;

import enums.VehicleType;
import utils.ParkingLotManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    List<ParkingLotFloor> parkingLotFloors;
    Map<String, String> spotIDFromVehicleNumberMap;
    Map<String, String> spotIDFromTicketNumberMap;
    Map<String, String> vehicleNumberFromSpotIDMap;
    Map<String, String> ticketNumberFromSpotIDMap;

    public ParkingLot(final List<ParkingLotFloor> parkingLotFloors) {
        this.parkingLotFloors = parkingLotFloors;
        this.spotIDFromVehicleNumberMap = new HashMap<>();
        this.spotIDFromTicketNumberMap = new HashMap<>();
        this.vehicleNumberFromSpotIDMap = new HashMap<>();
        this.ticketNumberFromSpotIDMap = new HashMap<>();
    }

    public List<ParkingLotFloor> getParkingLotFloors() {
        return parkingLotFloors;
    }

    public String park(final int vehicleType, final String vehicleNumber, final String ticketId,
                       final int parkingStrategy) {
        final String spotId;
        try {
            //TODO: Try to use DI here
            System.out.printf("Trying to park vehicleNumber: %s, vehicleType: %s using strategy: %s\n", vehicleNumber, vehicleType, parkingStrategy);
            final Vehicle vehicle = new Vehicle(vehicleNumber, vehicleType);

            switch (parkingStrategy) {
                case 0:
                    spotId = ParkingLotManager.parkVehicleForStrategy0(vehicle, ticketId, parkingLotFloors);
                    break;
                case 1:
                    spotId = ParkingLotManager.parkVehicleForStrategy1(vehicle, ticketId, parkingLotFloors);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported Parking Strategy");
            }
            spotIDFromVehicleNumberMap.put(vehicleNumber, spotId);
            spotIDFromTicketNumberMap.put(ticketId, spotId);
            vehicleNumberFromSpotIDMap.put(spotId, vehicleNumber);
            ticketNumberFromSpotIDMap.put(spotId, ticketId);
            System.out.printf("Vehicle: %s parked at spot ID: %s\n", vehicleNumber, spotId);
        } catch (Exception e) {
            System.out.printf("Could not park vehicle to the parking lot due to exception: %s\n", e.getMessage());
            return "";
        }
        return spotId;
    }

    public void removeVehicle(String spotId) {
        try {
            int parkingLotFloorNo = ParkingLotManager.getFloorFromSpotId(spotId);
            int parkingLotFloorRow = ParkingLotManager.getRowFromSpotId(spotId);
            int parkingLotFloorColumn = ParkingLotManager.getColumnFromSpotId(spotId);

            if(parkingLotFloorNo > parkingLotFloors.size()) {
                throw new IllegalArgumentException("Invalid spot ID");
            }
            String vehicleNo = null, ticketNo = null;
            if(vehicleNumberFromSpotIDMap.containsKey(spotId)) {
                vehicleNo = vehicleNumberFromSpotIDMap.get(spotId);
            }
            if(ticketNumberFromSpotIDMap.containsKey(spotId)) {
                ticketNo = ticketNumberFromSpotIDMap.get(spotId);
            }

            if(vehicleNo == null || ticketNo == null) {
                throw new IllegalArgumentException("Invalid spot ID");
            }

            ParkingLotFloor parkingLotFloor = parkingLotFloors.get(parkingLotFloorNo);
            parkingLotFloor.freeParkingLotSpot(parkingLotFloorRow, parkingLotFloorColumn);

            vehicleNumberFromSpotIDMap.remove(spotId);
            ticketNumberFromSpotIDMap.remove(spotId);
            spotIDFromTicketNumberMap.remove(ticketNo);
            spotIDFromVehicleNumberMap.remove(vehicleNo);
            System.out.printf("Vehicle at spot ID: %s successfully removed from floor: %s, row: %s, column: %s\n", spotId,
                    parkingLotFloor, parkingLotFloorRow, parkingLotFloorColumn);
        } catch (Exception e) {
            System.out.printf("Could not remove vehicle due to exception: %s\n", e.getMessage());
        }
    }

    public String searchVehicle(String queryString) {
        String spotId = null;
        try {
            if(spotIDFromVehicleNumberMap.containsKey(queryString)) {
                spotId = spotIDFromVehicleNumberMap.get(queryString);
            }
            if(spotIDFromTicketNumberMap.containsKey(queryString)) {
                spotId = spotIDFromTicketNumberMap.get(queryString);
            }
            if(spotId == null) {
                throw new RuntimeException("Vehicle Not Parked In Parking Lot");
            }
        } catch (Exception e) {
            System.out.printf("Could not search vehicle with query: %s due to exception: %s\n", queryString, e.getMessage());
            return "";
        }
        return spotId;
    }

    public int getFreeSpotsCount(int floor, int vehicleType) {
        try {
            if(floor >= parkingLotFloors.size()) {
                throw new IndexOutOfBoundsException("Floor does not exist");
            }
            return parkingLotFloors.get(floor).getNoOfAvailableSlots(VehicleType.fromType(vehicleType));
        } catch (Exception e) {
            System.out.printf("Could not find free spots on floor due to exception: %s\n", e.getMessage());
            return -1;
        }
    }
}
