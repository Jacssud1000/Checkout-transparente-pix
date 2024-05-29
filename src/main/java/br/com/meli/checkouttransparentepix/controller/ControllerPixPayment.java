package br.com.meli.checkouttransparentepix.controller;

import br.com.meli.checkouttransparentepix.dto.PaymentPixDto;
import br.com.meli.checkouttransparentepix.dto.ResponsePaymentPixDto;
import br.com.meli.checkouttransparentepix.service.ServicePixPayment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:63342")
@RequestMapping("/v1/payments")
public class ControllerPixPayment {

  @Autowired
  private ServicePixPayment servicePixPayment;

  @PostMapping
  public ResponseEntity<ResponsePaymentPixDto> paymentRequest(@RequestBody @Valid PaymentPixDto paymentPixDto){
  ResponsePaymentPixDto payment = servicePixPayment.paymentPix(paymentPixDto);
  return ResponseEntity.status(HttpStatus.OK).body(payment);
  }
}