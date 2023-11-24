package com.example.labor_management_project.service;

import com.example.labor_management_project.dto.PointDTO;
import com.example.labor_management_project.model.Point;
import com.example.labor_management_project.model.User;
import com.example.labor_management_project.repository.PointRepository;
import com.example.labor_management_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    UserRepository userRepository;


    // create points
    public ResponseEntity<String> createPoints(PointDTO pointDTO) {

        // Check if the User exists
        Optional<User> checkEmployee = userRepository.findById(pointDTO.getEmployeeID());

        if (checkEmployee.isEmpty()){
            return new ResponseEntity<>("Requested User not exist", HttpStatus.NOT_FOUND);
        }

        else{
            Point points = new Point();
            points.setPointGiveTime(LocalDateTime.now());
            points.setPoints(pointDTO.getPoints());
            points.setUser(userRepository.getReferenceById(pointDTO.getEmployeeID()));
            pointRepository.save(points);
            return new ResponseEntity<>("Point Mark successfully", HttpStatus.OK);
        }

    }

    // Get Points
    public List<PointDTO> getAllLaborsPoints(int year, int month){
        return  pointRepository.findAllLaborsPoints(year, month);

    }
}
