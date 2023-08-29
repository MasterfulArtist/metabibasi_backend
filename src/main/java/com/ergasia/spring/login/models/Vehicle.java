package com.ergasia.spring.login.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "oxima")
public class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  @Column(name = "pinakida",nullable = false)
  private String pinakida;


  @NotBlank
  @Size(max = 20)
  @Column(name = "user_id",nullable = false)
  private Long user_id;

  public Vehicle() {
  }

  public Vehicle(String pinakida, Long user_id) {
    this.pinakida = pinakida;
    this.user_id = user_id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPinakida() {
    return pinakida;
  }

  public void setPinakida(String pinakida) {
    this.pinakida = pinakida;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }
}
