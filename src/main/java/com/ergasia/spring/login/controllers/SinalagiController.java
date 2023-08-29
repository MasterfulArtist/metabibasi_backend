package com.ergasia.spring.login.controllers;

import com.ergasia.spring.login.models.Sinalagi;
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
@RequestMapping("/api/sinalagi")
public class SinalagiController {

    @Autowired
    SinalagiRepository sinalagiRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @RequestMapping(value = "/sinalagi_list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Sinalagi> getSinalagiList() {
        return sinalagiRepository.findAllSinalages();
    }

    @RequestMapping(value = "/sinalagi_list/sinalagi/buyer/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sinalagi> getSinalagesOfBuyer(@PathVariable("id") Long id) {
        return sinalagiRepository.findSinalagiByBuyerID(id);
    }

    @RequestMapping(value = "/sinalagi_list/sinalagi/seller/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sinalagi> getSinalagesOfSeller(@PathVariable("id") Long id) {
        return sinalagiRepository.findSinalagiBySellerID(id);
    }

    @RequestMapping(value = "/sinalagi_list/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity crea(@PathVariable("id") long id) {
        sinalagiRepository.deleteSinalagi(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/sinalagi_list/update", method = RequestMethod.POST)
    public ResponseEntity updateSinalagi(@RequestBody Sinalagi sinalagi) {
        sinalagi.setStatus("COMPLETED");
        sinalagiRepository.updateStatus(sinalagi);
        Vehicle ixe = vehicleRepository.findByID(sinalagi.getOxima_id());
        vehicleRepository.updateOximaOwner(sinalagi.getBuyer_id(), ixe.getPinakida());
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/sinalagi_list/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Sinalagi sinalagi) {
        sinalagiRepository.insert(sinalagi.getSeller_id(),
                sinalagi.getBuyer_id(),
                sinalagi.getOxima_id(),
                sinalagi.getPeriferia());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/sinalagi_list/sinalagi_exists/{pinakida}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sinalagi> isInSinallagi(@PathVariable("pinakida") String pinakida) {
        return sinalagiRepository.findSinalagiByPinakida(pinakida);
    }

}
