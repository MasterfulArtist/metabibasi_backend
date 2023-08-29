package com.ergasia.spring.login.repository;

import com.ergasia.spring.login.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

  @Query(value="select * from oxima", nativeQuery = true)
  List<Vehicle> findAllVehicles();

  @Query(value="select * from oxima where pinakida=:pinakida", nativeQuery = true)
  Vehicle findByPinakida(@Param("pinakida") String pinakida);

  @Query(value="select * from oxima where id=:id", nativeQuery = true)
  Vehicle findByID(@Param("id") Long id);

  @Query(value = "select * from oxima where user_id = :user_id", nativeQuery = true)
  List<Vehicle> findVehiclesOfUser(@Param("user_id") Long user_id);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO oxima (pinakida, user_id) VALUES (:#{#vehicle.pinakida}, :#{#vehicle.user_id})", nativeQuery = true)
  void insert(@Param("vehicle") Vehicle vehicle);


  @Query(value ="SELECT * FROM oxima v WHERE v.user_id=:userId AND v.id NOT IN (SELECT s.oxima_id FROM sinalagi s WHERE s.seller_id=:userId)", nativeQuery = true)
  List<Vehicle> findVehiclesNotInSinalagiBySellerID(@Param("userId") Long userId);


  @Modifying
  @Transactional
  @Query(value = "UPDATE oxima SET user_id=:user_id WHERE pinakida=:pinakida", nativeQuery = true)
  void updateOximaOwner(@Param("user_id") Long user_id, @Param("pinakida") String pinakida);

}
