package com.be.tapchi.pjtapchi.controller.paypal;


import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pay")
public class paypalController {

    private final PaypalService paypalService;

    @Autowired
    HoaDonService hoaDonService;

    @PostMapping("/create")
    public RedirectView createPayment(@RequestBody com.be.tapchi.pjtapchi.model.Payment paymentReq) {
        try {
            String cancelUrl = "http://localhost:9000/pay/cancel";
            String successUrl = "http://localhost:9000/pay/success";

            Payment payment = paypalService.createPayment(
                    paymentReq.getTotal(),
                    paymentReq.getCurrency(),
                    paymentReq.getMethod(),
                    paymentReq.getIntent(),
                    paymentReq.getDescription(),
//                    100.0,
//                    "USD",
//                    "paypal",
//                    "sale",
//                    "Thanh toan don hang",
                    cancelUrl,
                    successUrl
            );
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error : ", e.getMessage());
        }
        return new RedirectView("/pay/error");
    }

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<HoaDon>> paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.excutePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setTongTien(Float.parseFloat(payment.getTransactions().get(0).getAmount().getTotal()));
                hoaDon.setPaymentId(paymentId);
                hoaDon.setPayerId(payerId);
                hoaDon.setStatus("approved");
                hoaDon.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
                hoaDonService.save(hoaDon);

                return ResponseEntity.ok().body(new ApiResponse<>(true, "Payment success", hoaDon));

            }

        } catch (PayPalRESTException e) {
            log.error("Error : ", e.getMessage());
        }
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Payment success", null));
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        return "cancel";
    }


    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
