package br.com.meli.checkouttransparentepix.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class PayerDto {
  private String first_name;
  private String last_name;
  @NotNull
  private String email;
  @NotNull
  private Identification identification;

  public PayerDto(String first_name, String last_name, String email, Identification identification) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.identification = identification;
  }
}