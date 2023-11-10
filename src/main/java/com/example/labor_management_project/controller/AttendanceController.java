package com.example.labor_management_project.controller;

import com.example.labor_management_project.dto.AttendanceDTO;
import com.example.labor_management_project.model.Attendance;
import com.example.labor_management_project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;


    // Read Specific Attendance Details from DB
    @GetMapping("{attID}")
    public Attendance getDetails(@PathVariable("attID") int salaryID){
        return attendanceService.getAttendance(salaryID);
    }

    // Read Attendance Details from DB
    @GetMapping()
    public List<Attendance> getAllAttendanceDetails(){
        return attendanceService.getAllAttendance();
    }

    @PostMapping
    public ResponseEntity<String> createAttendanceDetails (@RequestBody AttendanceDTO attendanceDTO){
        return attendanceService.createAttendance(attendanceDTO);
    }

    @PutMapping
    public String updateAttendanceDetails (@RequestBody AttendanceDTO attendanceDTO){
        attendanceService.updateAttendance(attendanceDTO);
        return "Attendance Updated successfully";
    }

    @PutMapping("/addOutTime")
    public ResponseEntity<String> updateOutTimeAttendanceDetails(@RequestBody AttendanceDTO attendanceDTO) {
        return attendanceService.updateOutTimeAttendance(attendanceDTO);
    }


    // Delete Attendance details
    @DeleteMapping("{attID}")
    public String deleteAttendanceDetails(@PathVariable("attID") int attID){
        attendanceService.deleteAttendance(attID);
        return "Attendance Deleted successfully";
    }


    // Get attendanceID, employeeID and inTime using Query ( As I remember, this is not used )
    @GetMapping("/aid&eid")
    public List<AttendanceDTO> findAttendance(){return attendanceService.getAttIDs();}


    // Get Labor Today Attendance(attendanceID, employeeID and inTime using Query) (used)
    @GetMapping("/today")
    public List<AttendanceDTO> findTodayAttendance(){return attendanceService.getTodayAttendance();}

    // Labors daily attendances (going)
    @GetMapping("/labor/daily/{year}/{month}/{date}")
    public ResponseEntity<List<AttendanceDTO>> findLaborDailyAttendance( @PathVariable("year") int year, @PathVariable("month") int month,  @PathVariable("date") int date) {
        List<AttendanceDTO> attendanceDetails = attendanceService.getLaborDailyAttendance(year, month,date);
        if (!attendanceDetails.isEmpty()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get labors current month attendances (used)
    @GetMapping("/labor/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> findLaborCurrentMonthlyAttendance(@PathVariable("employeeId") int employeeId) {
        List<AttendanceDTO> attendanceDetails = attendanceService.getLaborCurrentMonthlyAttendance(employeeId);

        if (!attendanceDetails.isEmpty()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Current month Working hours and salary (used)
    @GetMapping("/currentMonthSalary/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> findLaborCurrentMonthSalary(@PathVariable("employeeId") int employeeId) {
        List<AttendanceDTO> attendanceDetails = attendanceService.getLaborCurrentMonthSalary(employeeId);
        if (!attendanceDetails.isEmpty()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Month Working hours and salary (used)
    @GetMapping("/monthSalary/{employeeId}/{year}/{month}")
    public ResponseEntity<List<AttendanceDTO>> findLaborMonthSalary(@PathVariable("employeeId") int employeeId, @PathVariable("month") int month, @PathVariable("year") int year) {
        List<AttendanceDTO> attendanceDetails = attendanceService.getLaborMonthSalary(employeeId, year, month);
        if (!attendanceDetails.isEmpty()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Labors monthly attendances (used)
    @GetMapping("/labor/{employeeId}/{year}/{month}")
    public ResponseEntity<List<AttendanceDTO>> findLaborMonthlyAttendance(@PathVariable("employeeId") int employeeId, @PathVariable("month") int month, @PathVariable("year") int year) {
        List<AttendanceDTO> attendanceDetails = attendanceService.getLaborMonthlyAttendance(employeeId, year, month);
        if (!attendanceDetails.isEmpty()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // All Labors Salary Cost by Month (used)
    @GetMapping("/labor/{year}/{month}")
    public ResponseEntity<List<AttendanceDTO>> findAllLaborsMonthlySalary(@PathVariable("month") int month, @PathVariable("year") int year) {
        List<AttendanceDTO> attendanceDetails = attendanceService.getAllLaborsMonthSalary( year, month);
        if (!attendanceDetails.isEmpty()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // All labors full salary cost (Used)
    @GetMapping("/laborSum/{year}/{month}")
    public ResponseEntity<Double> findAllLaborsMonthlySalarySum(@PathVariable("month") int month, @PathVariable("year") int year) {
        Double attendanceDetails = attendanceService.getAllLaborsMonthSalarySum( year, month);
        if (!attendanceDetails.isNaN()) {
            return ResponseEntity.ok(attendanceDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}


