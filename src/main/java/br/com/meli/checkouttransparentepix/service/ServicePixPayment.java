package br.com.meli.checkouttransparentepix.service;

import br.com.meli.checkouttransparentepix.dto.PaymentPixDto;
import br.com.meli.checkouttransparentepix.dto.ResponsePaymentPixDto;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ServicePixPayment {

  public ResponsePaymentPixDto paymentPix(PaymentPixDto paymentPixDto) {
    try {
      Map<String, String> customHeaders = new HashMap<>();

      customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

      MPRequestOptions requestOptions = MPRequestOptions.builder()
          .customHeaders(customHeaders)
          .accessToken("APP_USR-5879859661431869-050816-63fe2574c50c3369bae624b038a5c0c4-1259153998")
          .build();

      MercadoPagoConfig.setAccessToken("APP_USR-5879859661431869-050816-63fe2574c50c3369bae624b038a5c0c4-1259153998");

      PaymentClient client = new PaymentClient();

      OffsetDateTime dateOfExpiration = OffsetDateTime.now(ZoneOffset.UTC).plusHours(24);

      PaymentCreateRequest paymentCreateRequest =
          PaymentCreateRequest.builder()
              .transactionAmount(paymentPixDto.getTransactionAmount())
              .description(paymentPixDto.getDescription())
              .paymentMethodId(paymentPixDto.getPaymentMethodId())
              .dateOfExpiration(dateOfExpiration)
              .payer(
                  PaymentPayerRequest.builder()
                      .email(paymentPixDto.getPayerDto().getEmail())
                      .firstName(paymentPixDto.getPayerDto().getFirst_name())
                      .lastName(paymentPixDto.getPayerDto().getLast_name())
                      .identification(
                          IdentificationRequest.builder()
                              .type(paymentPixDto.getPayerDto().getIdentification().getType())
                              .number(paymentPixDto.getPayerDto().getIdentification().getNumber())
                              .build())
                      .build())
              .build();

      Payment createdPayment = client.create(paymentCreateRequest, requestOptions);

      String qrCodeBase64 = createdPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64();
      String ticketUrl = createdPayment.getPointOfInteraction().getTransactionData().getTicketUrl();
      String qrCode = createdPayment.getPointOfInteraction().getTransactionData().getQrCode();

      return new ResponsePaymentPixDto(
          createdPayment.getId(),
          createdPayment.getStatus(),
          createdPayment.getStatusDetail(),
          qrCodeBase64,
          ticketUrl,
          qrCode
          );

    } catch (MPApiException apiException) {
      System.out.println(apiException.getApiResponse().getContent());
      return new ResponsePaymentPixDto(1L, "Status error",
          "detail error","qr error","ticketUrl error","qrCode error");
    } catch (MPException e) {
      throw new RuntimeException(e);
    }
  }
}
