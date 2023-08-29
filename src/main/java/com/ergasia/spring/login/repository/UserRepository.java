package com.ergasia.spring.login.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ergasia.spring.login.models.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query(value="select * from users", nativeQuery = true)
  List<User> findAllUsers();

  @Query(value = "SELECT u.* FROM users u " +
          "JOIN user_roles ur ON u.id = ur.user_id " +
          "JOIN roles r ON ur.role_id = r.id " +
          "WHERE r.name = 'ROLE_AGORASTIS'", nativeQuery = true)
  List<User> findAllBuyers();

  @Query(value="select * from users where id=:id", nativeQuery = true)
  User getAccount(@Param("id") Long id);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO users (username, email, firstname, lastname, afm, password) VALUES (:#{#user.username}, :#{#user.email}, :#{#user.firstname},:#{#user.lastname},:#{#user.afm},:#{#user.password})", nativeQuery = true)
  void insert(@Param("user") User user);

  @Modifying
  @Transactional
  @Query(value = "UPDATE users SET " +
          "username = CASE WHEN :#{#user.username} IS NOT NULL AND :#{#user.username} <> '' THEN :#{#user.username} ELSE username END, " +
          "email = CASE WHEN :#{#user.email} IS NOT NULL AND :#{#user.email} <> '' THEN :#{#user.email} ELSE email END, " +
          "firstname = CASE WHEN :#{#user.firstname} IS NOT NULL AND :#{#user.firstname} <> '' THEN :#{#user.firstname} ELSE firstname END, " +
          "lastname = CASE WHEN :#{#user.lastname} IS NOT NULL AND :#{#user.lastname} <> '' THEN :#{#user.lastname} ELSE lastname END, " +
          "afm = CASE WHEN :#{#user.afm} IS NOT NULL AND :#{#user.afm} <> '' THEN :#{#user.afm} ELSE afm END, " +
          "password = CASE WHEN :#{#user.password} IS NOT NULL AND :#{#user.password} <> '' THEN :#{#user.password} ELSE password END " +
          "WHERE id = :#{#user.id}", nativeQuery = true)
  void updateAccount(@Param("user") User user);


}
