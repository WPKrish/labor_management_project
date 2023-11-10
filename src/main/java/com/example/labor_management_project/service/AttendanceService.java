package com.example.labor_management_project.service;

import com.example.labor_management_project.dto.AttendanceDTO;
import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.Attendance;
import com.example.labor_management_project.model.JobRole;
import com.example.labor_management_project.model.User;
import com.example.labor_management_project.repository.AttendanceRepository;
import com.example.labor_management_project.repository.JobRoleRepository;
import com.example.labor_management_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    JobRoleRepository jobRoleRepository;


    public ResponseEntity<String> createAttendance(AttendanceDTO attendanceDTO) {

        // Check if the User exists
        Optional<User> checkEmployee = userRepository.findById(attendanceDTO.getEmployeeID());

        if (checkEmployee.isEmpty()){
            return new ResponseEntity<>("Requested User not exist", HttpStatus.NOT_FOUND);
        }


        else{

            // Check if an attendance record already exists for the employee on the current day
            if (attendanceRecordExists(attendanceDTO.getEmployeeID())) {
                return new ResponseEntity<>("This Attendance was Marked already", HttpStatus.CONFLICT);
            }
            else{
                Attendance tempAttendance = new Attendance();
                tempAttendance.setInTime(LocalDateTime.now());
                //tempAttendance.setOutTime(LocalDateTime.now().with(LocalTime.MAX)); // set out Time as 00.00.00 and it can update finally
                tempAttendance.setOutTime(LocalDateTime.now().plusHours(8)); // set out Time as inTime+8 and it can update finally
                tempAttendance.setDailyJob(attendanceDTO.getDailyJob());

                float workingHours = calculateWorkingHours(tempAttendance.getInTime(), tempAttendance.getOutTime());
                tempAttendance.setWorkingHours(workingHours);

                tempAttendance.setUser(userRepository.getReferenceById(attendanceDTO.getEmployeeID()));
                attendanceRepository.save(tempAttendance);
                return new ResponseEntity<>("Attendance Mark successfully", HttpStatus.OK);
            }
        }

    }
    // Helper method to check if an attendance record already exists for the employee on the current day
    private boolean attendanceRecordExists(int employeeID) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        // Check if an attendance record exists for the employee and within the current day's time range
        return attendanceRepository.existsByUserAndInTimeBetween(
                userRepository.getReferenceById(employeeID),
                startOfDay,
                endOfDay
        );
    }




    public String updateAttendance(AttendanceDTO attendanceDTO) {
        int updatableAttID = attendanceDTO.getAttID();

        // Check if the attendance exists
        Optional<Attendance> checkAttendance = attendanceRepository.findById(updatableAttID);
        if (checkAttendance.isEmpty()) {
            throw new UserNotFoundException("Requested Attendance not exist");
        }

        // Now you can safely retrieve the attendance
        Attendance availableAttendance = checkAttendance.get();
        int availableAttendanceANo = availableAttendance.getAttID();

        availableAttendance.setAttID(availableAttendanceANo);
        availableAttendance.setInTime(LocalDateTime.now());
        //availableAttendance.setOutTime(LocalDateTime.now());
        availableAttendance.setDailyJob(attendanceDTO.getDailyJob());

        //If use below code, it's value should be given as an input
        //availableAttendance.setEmployeeID(userRepository.getReferenceById(attendanceDTO.getEmployeeID()));
        attendanceRepository.save(availableAttendance);
        return "Success";
    }


//    public String deleteAttendance(int attID) {
//
//        attendanceRepository.deleteById(attID);
//        return "Success";
//    }

    // Before delete user should be null, because employee_id is a foreign key for this (foreign key can't delete directly)
    public String deleteAttendance(int attID) {
        // Find the attendance record by attID
        Attendance attendance = attendanceRepository.findById(attID).orElse(null);

        if (attendance != null) {
            // Set employee_id to null
            attendance.setUser(null);

            // Delete the attendance record
            attendanceRepository.delete(attendance);
            return "Success";
        } else {
            return "Attendance not found";
        }
    }


    public Attendance getAttendance(int attID) {
        // More Business Logic
        if(attendanceRepository.findById(attID).isEmpty())
            throw new UserNotFoundException("Requested User not exist");

        return attendanceRepository.findById(attID).get();
    }


    public List<Attendance> getAllAttendance() {
        // More Business Logic
        List<Attendance> attendances = attendanceRepository.findAll();

        return attendances;
    }




    // Update off time using employeeID
    public ResponseEntity<String> updateOutTimeAttendance(AttendanceDTO attendanceDTO) {

        List<AttendanceDTO> ob1 = attendanceRepository.findAttIDs();
        for (AttendanceDTO attendance : ob1) {
            if (attendance.getEmployeeID() == attendanceDTO.getEmployeeID()) {

                int updatableAttID = attendance.getAttID();

                // Check if the attendance exists
                Optional<Attendance> checkAttendance = attendanceRepository.findById(updatableAttID);
                if (checkAttendance.isEmpty()) {
                    throw new UserNotFoundException("Requested User not exist");
                }

                // Now you can safely retrieve the attendance
                Attendance availableAttendance = checkAttendance.get();
                int availableAttID = availableAttendance.getAttID();

                LocalDateTime inTime = availableAttendance.getInTime();
                LocalDateTime outTime = LocalDateTime.now();

                availableAttendance.setAttID(availableAttID);
                availableAttendance.setOutTime(outTime);

                // Calculate working hours
                float workingHours = calculateWorkingHours(inTime, outTime);
                availableAttendance.setWorkingHours(workingHours);

                attendanceRepository.save(availableAttendance);
                return new ResponseEntity<>("Out time marked Successfully", HttpStatus.OK);

            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    // Calculate Working Hours
    private Float calculateWorkingHours(LocalDateTime inTime, LocalDateTime outTime) {
        // Assuming you want to calculate working hours in floating-point format (hours)
        long seconds = Duration.between(inTime, outTime).getSeconds();
        float hours = seconds / 3600.0f; // Convert seconds to hours (1 hour = 3600 seconds)
        //float hours = Math.round((seconds / 3600.0f) * 10.0f) / 10.0f; // Round to one decimal place
        float roundedHours = roundToNearestHalfDown(hours);
        return roundedHours;
    }
    private float roundToNearestHalfDown(float value) {
        return (float) (Math.floor(value * 2) / 2);
    }




    // Find AttID and employeeID  ( As I remember, this is not used )
    public List<AttendanceDTO> getAttIDs(){
        List<AttendanceDTO> ob1 = attendanceRepository.findAttIDs();
        return ob1;
    }


    // Find Attendances which employees working on current date by constructed query (used)
    public List<AttendanceDTO> getTodayAttendance(){
        List<AttendanceDTO> attendances = attendanceRepository.findTodayAttendance();
        return attendances;
    }

    // Find Attendance according to the date(going)
    public List<AttendanceDTO> getLaborDailyAttendance(int year, int month, int date){
        return  attendanceRepository.findDailyAttendance(year, month, date);

    }

    // Find Labor Attendance in Current Month (used)
    public List<AttendanceDTO> getLaborCurrentMonthlyAttendance(int employeeID){
        return  attendanceRepository.findLaborCurrentMonthlyAttendance(employeeID);

    }

    // Find Current month salary (used)
    public List<AttendanceDTO> getLaborCurrentMonthSalary(int employeeID){
        return  attendanceRepository.findLaborCurrentMonthSalary(employeeID);

    }

    // Find Month Salary (used)
    public List<AttendanceDTO> getLaborMonthSalary(int employeeID,int year, int month){
        return  attendanceRepository.findLaborMonthSalary(employeeID,year, month);

    }

    // Find Labor Monthly Attendance (used)
    public List<AttendanceDTO> getLaborMonthlyAttendance(int employeeID,int year, int month){
        return  attendanceRepository.findLaborMonthlyAttendance(employeeID,year, month);

    }

    // Find All Labors Month Salary (used)
    public List<AttendanceDTO> getAllLaborsMonthSalary(int year, int month){
        return  attendanceRepository.findAllLaborsMonthSalary(year, month);

    }

    // Find All Labors Full Salary Cost (used)
    public Double getAllLaborsMonthSalarySum(int year, int month){
        return  attendanceRepository.findAllLaborsMonthSalarySum(year, month);

    }













}
