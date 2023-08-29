package com.ergasia.spring.login.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ergasia.spring.login.models.ERole;
import com.ergasia.spring.login.models.Role;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
  void insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user_roles SET role_id = :roleId WHERE user_id = :userId", nativeQuery = true)
  void updateRole(@Param("roleId") Long roleId, @Param("userId") Long userId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_roles WHERE user_id = :userId", nativeQuery = true)
  void deleteUserRole(@Param("userId") Long userId);


}
