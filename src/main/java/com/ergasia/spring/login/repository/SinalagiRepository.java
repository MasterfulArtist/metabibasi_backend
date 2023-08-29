package com.ergasia.spring.login.repository;

import com.ergasia.spring.login.models.Sinalagi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface SinalagiRepository extends JpaRepository<Sinalagi, Long> {



  @Query(value="select * from sinalagi", nativeQuery = true)
  List<Sinalagi> findAllSinalages();

  @Query(value = "select * from sinalagi where seller_id=:seller_id", nativeQuery = true)
  List<Sinalagi> findSinalagiBySellerID(@Param("seller_id") Long seller_id);

  @Query(value = "select * from sinalagi where oxima_id=:oxima_id", nativeQuery = true)
  List<Sinalagi> findSinalagiByPinakida(@Param("oxima_id") String oxima_id);

  @Query(value = "select * from sinalagi where buyer_id=:buyer_id", nativeQuery = true)
  List<Sinalagi> findSinalagiByBuyerID(@Param("buyer_id") Long buyer_id);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO sinalagi (seller_id, buyer_id, oxima_id, status, periferia) VALUES (:seller_id, :buyer_id, :oxima_id, 'PENDING', :periferia)", nativeQuery = true)
  void insert(@Param("seller_id") Long seller_id,
              @Param("buyer_id") Long buyer_id,
              @Param("oxima_id") Long oxima_id,
              @Param("periferia") String periferia);

  @Modifying
  @Transactional
  @Query(value = "UPDATE sinalagi " +
          "SET status = :#{#sinalagi.status} " +
          "WHERE id = :#{#sinalagi.id}", nativeQuery = true)
  void updateStatus(@Param("sinalagi") Sinalagi sinalagi);


  @Modifying
  @Transactional
  @Query(value = "DELETE FROM sinalagi WHERE id = :sinallagi_id", nativeQuery = true)
  void deleteSinalagi(@Param("sinallagi_id") Long sinallagi_id);

}
