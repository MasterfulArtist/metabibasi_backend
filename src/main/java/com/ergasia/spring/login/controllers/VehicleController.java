package com.ergasia.spring.login.controllers;

import com.ergasia.spring.login.models.Vehicle;
import com.ergasia.spring.login.repository.SinalagiRepository;
import com.ergasia.spring.login.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    SinalagiRepository sinalagiRepository;

    @RequestMapping(value = "/vehicle_list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Vehicle> getVehicleList() {
        return vehicleRepository.findAllVehicles();
    }

    @RequestMapping(value = "/vehicle_list/vehicle/{pinakida}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle getVehicleByPinakida(@PathVariable("pinakida") String pinakida) {
        return vehicleRepository.findByPinakida(pinakida);
    }

    @RequestMapping(value = "/vehicle_list/vehicle_by_id/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle getVehicleByID(@PathVariable("id") Long id) {
        return vehicleRepository.findByID(id);
    }

    @RequestMapping(value = "/vehicle_list/user_vehicle_list/{user_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> getVehiclesOfUser(@PathVariable("user_id") Long user_id) {
        return vehicleRepository.findVehiclesOfUser(user_id);
    }

    @RequestMapping(value = "/vehicle_list/user_vehicle_list_available/{user_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> getAvailableVehiclesOfUser(@PathVariable("user_id") Long user_id) {
        return vehicleRepository.findVehiclesNotInSinalagiBySellerID(user_id);
    }

    @RequestMapping(value = "/vehicle_list/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Vehicle vehicle) {
        vehicleRepository.insert(vehicle);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/vehicle_list/my_vehicle/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        vehicleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
