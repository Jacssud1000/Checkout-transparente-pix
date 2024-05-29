package br.com.meli.checkouttransparentepix.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class PaymentPixDto {
  @NotNull
  private BigDecimal transactionAmount;
  @NotNull
  private String description;
  @NotNull
  private String paymentMethodId;
  @NotNull
  PayerDto payerDto;
}
