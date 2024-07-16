package org.java.spring_web4.web.controller;

import java.util.List;
import java.util.Optional;

import org.java.spring_web4.db.pojo.Farm;
import org.java.spring_web4.db.pojo.Farmer;
import org.java.spring_web4.db.serv.FarmService;
import org.java.spring_web4.db.serv.FarmerService;
import org.java.spring_web4.web.dto.FarmerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/farmers")
public class MainController {

     @Autowired
    private FarmerService farmerService;

    @Autowired
    private FarmService farmService;

    @GetMapping("/test/add")
    public ResponseEntity<Void> addTestEntity() {

        Farm farm1 = new Farm("Rifugio del Pulcino","Orvieto");
        Farm farm2 = new Farm("Favole Fertili","Orbetello");

        farmService.save(farm1);
        farmService.save(farm2);

        Farmer fa1 = new Farmer("Luca", "Terlizzi", 24, farm1);
        Farmer fa2 = new Farmer("Susanna", "Selli", 35, farm2);
        Farmer fa3 = new Farmer("Paulo", "Dybala", 31, farm1);

        farmerService.save(fa1);
        farmerService.save(fa2);
        farmerService.save(fa3);

        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Farmer>> getMethodName() {

        List<Farmer> farmers = farmerService.getAllFarmers();

        return ResponseEntity.ok(farmers);
    }

    @PostMapping("")
    public ResponseEntity<Farmer> addFarmer(
            @RequestBody FarmerDto farmerDto) {

        Farmer fa = new Farmer(farmerDto);

        Optional<Farm> optFaFarm = farmService.getFarmById(farmerDto.getFarmId());

        if (optFaFarm.isEmpty())
            return ResponseEntity.badRequest().build();

        Farm faFarm = optFaFarm.get();
        fa.setFarm(faFarm);

        farmerService.save(fa);

        return ResponseEntity.ok(fa);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFarmer(
            @PathVariable int id) {

        Optional<Farmer> optFa = farmerService.getFarmerById(id);

        if (optFa.isEmpty())
            return ResponseEntity.notFound().build();

        Farmer fa = optFa.get();
        farmerService.delete(fa);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Farmer> updateFarmer(
            @PathVariable int id,
            @RequestBody FarmerDto farmerDto) {

        Optional<Farmer> optFa = farmerService.getFarmerById(id);

        if (optFa.isEmpty())
            return ResponseEntity.notFound().build();

        Farmer fa = optFa.get();
        fa.update(farmerDto);

        Optional<Farm> optFaFarm = farmService.getFarmById(farmerDto.getFarmId());

        if (optFaFarm.isEmpty())
            return ResponseEntity.badRequest().build();

        Farm faFarm = optFaFarm.get();
        fa.setFarm(faFarm);

        farmerService.save(fa);

        return ResponseEntity.ok(fa);
    }

}
