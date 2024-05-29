package br.com.meli.checkouttransparentepix.dto;

import lombok.Data;

@Data
public class ResponsePaymentPixDto {
  private Long id;
  private String status;
  private String detail;

  public ResponsePaymentPixDto(Long id, String status, String detail, String qrCodeBase64, String ticketUrl, String qrCode) {
    this.id = id;
    this.status = status;
    this.detail = detail;
  }
}
