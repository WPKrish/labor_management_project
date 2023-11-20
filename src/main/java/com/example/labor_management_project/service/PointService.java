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

//            // Check if an attendance record already exists for the employee on the current day (this if else condition was not used, because employer can give points in same day also)
//            if (pointRecordExists(pointDTO.getEmployeeID())) {
//                return new ResponseEntity<>("This Point was Marked already", HttpStatus.CONFLICT);
//            }
//            else{
//                Point points = new Point();
//                points.setPointGiveTime(LocalDateTime.now());
//                points.setPoints(pointDTO.getPoints());
//                points.setUser(userRepository.getReferenceById(pointDTO.getEmployeeID()));
//                pointRepository.save(points);
//                return new ResponseEntity<>("Point Mark successfully", HttpStatus.OK);
//            }

            Point points = new Point();
            points.setPointGiveTime(LocalDateTime.now());
            points.setPoints(pointDTO.getPoints());
            points.setUser(userRepository.getReferenceById(pointDTO.getEmployeeID()));
            pointRepository.save(points);
            return new ResponseEntity<>("Point Mark successfully", HttpStatus.OK);
        }

    }
    // Helper method to check if an attendance record already exists for the employee on the current day (this method was not used, because employer can give points in same day also)
    private boolean pointRecordExists(int employeeID) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        // Check if an attendance record exists for the employee and within the current day's time range
        return pointRepository.existsByUserAndPointGiveTimeBetween(
                userRepository.getReferenceById(employeeID),
                startOfDay,
                endOfDay
        );
    }

    // Get Points
    public List<PointDTO> getAllLaborsPoints(int year, int month){
        return  pointRepository.findAllLaborsPoints(year, month);

    }
}
