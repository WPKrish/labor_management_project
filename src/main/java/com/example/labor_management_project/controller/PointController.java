package com.example.labor_management_project.controller;

import com.example.labor_management_project.dto.PointDTO;
import com.example.labor_management_project.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/point")
public class PointController {
    @Autowired
    PointService pointService;

    // Insert details to the table
    @PostMapping
    public ResponseEntity<String> createPointDetails (@RequestBody PointDTO pointDTO){
        return pointService.createPoints(pointDTO);
    }

    //get details from the table
    @GetMapping("/labor/{year}/{month}")
    public ResponseEntity<List<PointDTO>> findAllLaborsPoints(@PathVariable("month") int month, @PathVariable("year") int year) {
        List<PointDTO> pointDetails = pointService.getAllLaborsPoints( year, month);
        if (!pointDetails.isEmpty()) {
            return ResponseEntity.ok(pointDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
