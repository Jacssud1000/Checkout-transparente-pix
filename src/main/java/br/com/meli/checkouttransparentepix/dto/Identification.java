package br.com.meli.checkouttransparentepix.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Identification {

  @NotNull
  private String type;
  @NotNull
  private String number;

  public Identification(String type, String number) {
    this.type = type;
    this.number = number;
  }
}
