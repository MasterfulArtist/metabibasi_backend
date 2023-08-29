package com.ergasia.spring.login.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "sinalagi")
public class Sinalagi {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name = "seller_id",nullable = false)
  private Long seller_id;


  @NotBlank
  @Column(name = "buyer_id",nullable = false)
  private Long buyer_id;

  @NotBlank
  @Column(name = "oxima_id",nullable = false)
  private Long oxima_id;

  @NotBlank
  @Column(name = "status", nullable = false)
  private String status;

  @NotBlank
  @Column(name = "periferia", nullable = false)
  private String periferia;

  public Sinalagi() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSeller_id() {
    return seller_id;
  }

  public void setSeller_id(Long seller_id) {
    this.seller_id = seller_id;
  }

  public Long getBuyer_id() {
    return buyer_id;
  }

  public void setBuyer_id(Long buyer_id) {
    this.buyer_id = buyer_id;
  }

  public Long getOxima_id() {
    return oxima_id;
  }

  public void setOxima_id(Long oxima_id) {
    this.oxima_id = oxima_id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPeriferia() {
    return periferia;
  }

  public void setPeriferia(String periferia) {
    this.periferia = periferia;
  }
}
