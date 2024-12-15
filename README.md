# Parking Lot LLD Question

https://codezym.com/question/7

## Overview

Write code for low level design of a parking lot.

* Parking lot has multiple floors
* Each floor has n rows
* Each row has m columns
* 2 types of parking - type = 2 for 2 wheelers and type = 4 for 4 wheelers

## Requirements

* park(int vehicleType, String vehicleNumber, String ticketId, int parkingStrategy)
    * returns spotId
    * This function assigns an empty parking spot to vehicle and maps vehicleNumber and ticketId to the assigned spotId
    * spotId is floor+"-"+row+"-"+column
    * parkingStrategy has two values, 0 and 1
    * parkingStrategy = 0 - Get the parking spot at lowest index i.e. lowest floor, row and column
    * parkingStrategy = 1 - Get the floor with maximum number of free spots for the given vehicle type. If multiple floors have maximum free spots then choose the floor at lowest index from them.
* removeVehicle(String spotId)
    * returns true if vehicle is removed
    * returns false if vehicle not found or any other error
* String searchVehicle(String query)
    * searches the latest parking details of a vehicle parked in previous park() method calls.
    * returns spotId e.g. 2-0-15 or empty string ""
    * Query will be either vehicleNumber or ticketId.
* int getFreeSpotsCount(int floor, int vehicleType)
    * At any point of time get the number of free spots of vehicle type (2 or 4 wheeler).

## Entities

* Vehicle
    * Vehicle Number
    * Vehicle Type
* Parking Lot
    * Floors
    * park()
    * removeVehicle()
    * searchVehicle()
    * getFreeSpotsCount()
* Parking Lot Floor
    * Rows
    * Columns
    * Parking Lot Spot
* Parking Lot Spot
    * Type
    * Vehicle

## Input

int [][][] parking
4 : 4 wheeler parking spot,
2 : 2 wheeler parking spot,
0 : inactive spot, no vehicle can be parked here
