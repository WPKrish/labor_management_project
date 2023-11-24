package com.example.labor_management_project.repository;

import com.example.labor_management_project.dto.AttendanceDTO;
import com.example.labor_management_project.model.Attendance;
import com.example.labor_management_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    // Check the user exit in this day
    boolean existsByUserAndInTimeBetween(User user, LocalDateTime startTime, LocalDateTime endTime);



    // This is used for update Attendance outTime according to the date (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, a.attID, a.inTime) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID " +
            "WHERE DATE(a.inTime) = CURRENT_DATE")
    List<AttendanceDTO> findAttIDs();


    // This is used for find current date labor attendance (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name, a.attID, a.inTime, a.dailyJob ) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID "+
            "WHERE u.jobRole.roleID=3 AND DATE(a.inTime) = CURRENT_DATE")
    List<AttendanceDTO> findTodayAttendance();

    // This is using for find daily labor attendance (going)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name, a.attID, a.inTime, a.outTime, a.dailyJob ) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID "+
//            "WHERE u.jobRole.roleID=3 AND YEAR(a.inTime) = :year AND MONTH(a.inTime) = :month AND DATE(a.inTime) = :date")
            "WHERE u.jobRole.roleID=3 AND FUNCTION('YEAR', a.inTime) = :year AND FUNCTION('MONTH', a.inTime) = :month AND FUNCTION('DAY', a.inTime) = :date")
    List<AttendanceDTO> findDailyAttendance(@Param("year") int year, @Param("month") int month, @Param("date") int date);

    // This is used for find labors current month attendances with all details (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name, a.attID, a.inTime, a.dailyJob, a.workingHours, a.outTime ) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID "+
            "WHERE u.jobRole.roleID=3 AND MONTH(a.inTime) = MONTH(CURRENT_DATE())"+
            "AND u.employeeID = :employeeId")
    List<AttendanceDTO> findLaborCurrentMonthlyAttendance(@Param("employeeId") int employeeId);


    // This is using for find labors monthly attendances with all details (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name, MONTHNAME(MAX(a.inTime)), YEAR(MAX(a.inTime)), a.attID, a.inTime, a.dailyJob, a.workingHours, a.outTime ) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID " +
            "WHERE u.jobRole.roleID=3 AND YEAR(a.inTime) = :year AND MONTH(a.inTime) = :month " +
            "AND u.employeeID = :employeeId " +
            "GROUP BY u.employeeID, u.name, a.attID, a.inTime, a.dailyJob, a.workingHours, a.outTime")  // when used Group all non aggregate values should be used
    List<AttendanceDTO> findLaborMonthlyAttendance(@Param("employeeId") int employeeId, @Param("year") int year, @Param("month") int month);


    // Sum of working hours, worked days, no of salary allocated days and salary for current month (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name,MONTHNAME(MAX(a.inTime)), YEAR(MAX(a.inTime)), SUM(a.workingHours) as totalWorkingHours, COUNT(a.attID) as totalWorkingDays, SUM(a.workingHours) / 8, u.salaryRate.salaryPerDay, (SUM(a.workingHours) / 8) * u.salaryRate.salaryPerDay) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID "+
            "WHERE u.jobRole.roleID = 3 AND MONTH(a.inTime) = MONTH(CURRENT_DATE())"+
            "AND u.employeeID = :employeeId " +
            "GROUP BY u.employeeID, u.name")
    List<AttendanceDTO> findLaborCurrentMonthSalary(@Param("employeeId") int employeeId);

    // Salary According to the month (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name, MONTHNAME(MAX(a.inTime)), YEAR(MAX(a.inTime)), SUM(a.workingHours) as totalWorkingHours, COUNT(a.attID) as totalWorkingDays, SUM(a.workingHours) / 8, u.salaryRate.salaryPerDay, (SUM(a.workingHours) / 8) * u.salaryRate.salaryPerDay) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID " +
            "WHERE u.jobRole.roleID = 3 AND YEAR(a.inTime) = :year AND MONTH(a.inTime) = :month " +  // Removed the misplaced closing parenthesis
            "AND u.employeeID = :employeeId " +
            "GROUP BY u.employeeID, u.name")
    List<AttendanceDTO> findLaborMonthSalary(@Param("employeeId") int employeeId,  @Param("year") int year,  @Param("month") int month);


    // All Labors Salary According to the month (used)
    @Query("SELECT new com.example.labor_management_project.dto.AttendanceDTO(u.employeeID, u.name, MONTHNAME(MAX(a.inTime)), YEAR(MAX(a.inTime)), SUM(a.workingHours) as totalWorkingHours, COUNT(a.attID) as totalWorkingDays, SUM(a.workingHours) / 8, u.salaryRate.salaryPerDay, (SUM(a.workingHours) / 8) * u.salaryRate.salaryPerDay) as TotalSalary " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID " +
            "WHERE u.jobRole.roleID = 3 AND YEAR(a.inTime) = :year AND MONTH(a.inTime) = :month "  +
            "GROUP BY u.employeeID, u.name ")
    List<AttendanceDTO> findAllLaborsMonthSalary(@Param("year") int year,  @Param("month") int month);


    // All Labors Full Salary Cost According to the month (used)
    @Query("SELECT SUM(sub.totalSalary) " +
            "FROM (SELECT (SUM(a.workingHours) / 8) * u.salaryRate.salaryPerDay as totalSalary " +
            "      FROM User u " +
            "      JOIN Attendance a ON u.employeeID = a.user.employeeID " +
            "      WHERE u.jobRole.roleID = 3 AND YEAR(a.inTime) = :year AND MONTH(a.inTime) = :month " +
            "      GROUP BY u.employeeID) sub")
    Double findAllLaborsMonthSalarySum(@Param("year") int year, @Param("month") int month);







}
