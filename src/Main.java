import model.ParkingLot;
import utils.ParkingLotManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLotManager.createParkingLot(createParkingLotList());
        ParkingLotManager.printParkingLot(parkingLot);
        System.out.println("Parking Lot Created");
        ParkingLotManager.printParkingLotFloorInfo(parkingLot);

        parkingLot.park(2, "4860", "11", 1);
        ParkingLotManager.printParkingLotFloorInfo(parkingLot);
        parkingLot.park(2, "4861", "12", 0);

        ParkingLotManager.printParkingLotFloorInfo(parkingLot);
        String spotId4860 = parkingLot.searchVehicle("4860");
        System.out.printf("Vehicle Found: 4860, spot: %s\n", spotId4860);
        spotId4860 = parkingLot.searchVehicle("11");
        System.out.printf("Vehicle Found: 4860, spot: %s\n", spotId4860);
        String spotId4861 = parkingLot.searchVehicle("4861");
        System.out.printf("Vehicle Found: 4861, spot: %s\n", spotId4861);

        parkingLot.removeVehicle("1-0-1");

        spotId4860 = parkingLot.searchVehicle("4860");
        System.out.printf("Vehicle Found: 4860, spot: %s\n", spotId4860);
        spotId4860 = parkingLot.searchVehicle("11");
        System.out.printf("Vehicle Found: 4860, spot: %s\n", spotId4860);
        System.out.printf("Vehicle Found: 4861, spot: %s\n", spotId4861);
        parkingLot.getFreeSpotsCount(1, 3);
    }

    private static List<List<List<Integer>>> createParkingLotList() {
        List<Integer> parkingLotRow1 = List.of(4, 4, 2, 2);
        List<Integer> parkingLotRow2 = List.of(2, 0, 2, 0);
        List<Integer> parkingLotRow3 = List.of(0, 2, 2, 2);
        List<Integer> parkingLotRow4 = List.of(4, 4, 4, 0);

        List<List<Integer>> parkingLotFloor1 = List.of(parkingLotRow1, parkingLotRow2, parkingLotRow3, parkingLotRow4);
        List<List<Integer>> parkingLotFloor2 = List.of(parkingLotRow3, parkingLotRow2, parkingLotRow3, parkingLotRow4);

        List<List<List<Integer>>> parkingLotList = List.of(parkingLotFloor1, parkingLotFloor2);
//        List<List<List<Integer>>> parkingLotList = List.of(parkingLotFloor1);
        return parkingLotList;
    }
}
