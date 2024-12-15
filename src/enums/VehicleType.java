package enums;

public enum VehicleType {
    TWO_WHEELER(2),
    FOUR_WHEELER(4);

    private final int vehicleType;

    VehicleType(final int type) {
        this.vehicleType = type;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public static VehicleType fromType(final int vehicleType) {
        for(final VehicleType type: VehicleType.values()) {
            if(type.getVehicleType() == vehicleType) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported Vehicle Type");
    }
}
