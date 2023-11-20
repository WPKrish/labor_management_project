package com.example.labor_management_project.repository;

import com.example.labor_management_project.dto.DailyJobDTO;
import com.example.labor_management_project.dto.NamePasswordDTO;
import com.example.labor_management_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select user from User user")
    List<User> findAllUsers();

    @Query("select user from User user where (jobRole.roleID = 2 OR jobRole.roleID=1) AND activeUser = true" )
    List<User> findAllSupervisors();

    @Query("select user from User user where jobRole.roleID = 3 AND activeUser = true" )
    List<User> findAllLabors();


    @Query("select user.name from User user")
    List<?> findAllUserNames();

    @Query("SELECT new com.example.labor_management_project.dto.NamePasswordDTO(user.employeeID, user.name) FROM User user")
    List<NamePasswordDTO> findNameWithPassword();

    @Query("SELECT new com.example.labor_management_project.dto.DailyJobDTO(u.employeeID, u.name, a.attID, a.dailyJob) " +
            "FROM User u " +
            "JOIN Attendance a ON u.employeeID = a.user.employeeID")
    List<DailyJobDTO> findDailyJob();




//    // Create Login Repository
//        Optional<User> findOneByEmployeeIDAndPassword(int employeeID, String password);
//        User findByEmployeeID(int employeeID);

    // Create Login Repository
    Optional<User> findOneByUsernameAndPassword(String username, String password);
//    User findByUsername(String username);

    Optional<User> findByUsername(String username);
    User findByEmployeeID(int employeeID);












}

