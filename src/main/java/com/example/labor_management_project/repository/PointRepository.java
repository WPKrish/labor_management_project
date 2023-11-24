package com.example.labor_management_project.repository;

import com.example.labor_management_project.dto.PointDTO;
import com.example.labor_management_project.model.Point;
import com.example.labor_management_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {

    // Check the user exit in this day
    boolean existsByUserAndPointGiveTimeBetween(User user, LocalDateTime startTime, LocalDateTime endTime);

    // All Labors Points According to the month (used)
    @Query("SELECT new com.example.labor_management_project.dto.PointDTO(u.employeeID, u.name, MONTHNAME(MAX(p.pointGiveTime)), YEAR(MAX(p.pointGiveTime)), SUM(p.points), COUNT(p.pointID)) " +
            "FROM User u " +
            "JOIN Point p ON u.employeeID = p.user.employeeID " +
            "WHERE u.jobRole.roleID = 3 AND YEAR(p.pointGiveTime) = :year AND MONTH(p.pointGiveTime) = :month "  +
            "GROUP BY u.employeeID, u.name " +
            "ORDER BY SUM(p.points) DESC")
    List<PointDTO> findAllLaborsPoints(@Param("year") int year, @Param("month") int month);

}
