package model;

import enums.VehicleType;

public class Vehicle {
    private final String vehicleNumber;
    private final VehicleType vehicleType;

    public Vehicle(final String vehicleNumber, final int vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = VehicleType.fromType(vehicleType);
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}
