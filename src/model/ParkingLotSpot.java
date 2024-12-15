package model;

import enums.VehicleType;

public class ParkingLotSpot {
    private final int spotRow;
    private final int spotCol;
    private final boolean isActive;
    private final VehicleType vehicleType;
    private boolean isOccupied;
    private Vehicle vehicle;
    private String ticketId;

    public ParkingLotSpot(final int spotRow, final int spotCol, final boolean isOccupied, final boolean isActive,
                          final VehicleType vehicleType, final Vehicle vehicle, final String ticketId) {
        this.spotRow = spotRow;
        this.spotCol = spotCol;
        this.isActive = isActive;
        this.vehicleType = vehicleType;
        this.isOccupied = isOccupied;
        this.vehicle = vehicle;
        this.ticketId = ticketId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void printParkingLotSlotInfo() {
        System.out.printf("Parking Lot Spot - Row: %s, Col: %s, isActive: %s, isOccupied: %s, vehicleType: %s, vehicle: %s%n, ticketId: %s\n",
                spotRow, spotCol, isActive, isOccupied,
                vehicleType != null? vehicleType.getVehicleType(): "No vehicle type",
                vehicle != null? vehicle.getVehicleNumber(): "No vehicle parked",
                ticketId != null? ticketId: "No ticket found");
    }

    public boolean isSlotOccupied() {
        return isOccupied;
    }

    public String parkVehicleOnParkingSpot(final Vehicle vehicle, final String ticketId) {
        if(!isOccupied) {
            if(vehicle.getVehicleType().equals(this.vehicleType)) {
                isOccupied = true;
                this.vehicle = vehicle;
                this.ticketId = ticketId;
                return String.format("%s-%s", spotRow, spotCol);
            }
            throw new IllegalArgumentException(String.format(
                    "Vehicle Type %s cannot park in Type %s spot. Vehicle Number: %s", vehicle.getVehicleType(),
                    this.vehicleType, vehicle.getVehicleNumber()));
        }
        throw new IllegalArgumentException("Parking Spot Already Occupied");
    }

    public void freeParkingLotSpot() {
        isOccupied = false;
        this.vehicle = null;
        this.ticketId = null;
    }
}
