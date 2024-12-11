package com.be.tapchi.pjtapchi.controller.payos;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.type.CreatePaymentLinkRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final PayOS payOS;
    private final HoaDonService hoaDonService;
    String baseURL = "http://localhost:9000";

    @Autowired
    public OrderController(PayOS payOS, HoaDonService hoaDonService) {
        super();
        this.payOS = payOS;
        this.hoaDonService = hoaDonService;
    }

    @PostMapping(path = "/create")
    public ObjectNode createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            final String productName = requestBody.getProductName();
            final String description = requestBody.getDescription();
            final String returnUrl = baseURL + "/order/success";
            final String cancelUrl = baseURL + "/order/cancel";
            final int price = requestBody.getPrice();
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            Long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            if (productName == null) {
                response.put("error", -1);
                response.put("message", "Product name cannot be null");
                response.set("data", null);
                return response;
            }
            ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();

            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                    .item(item).returnUrl(returnUrl).cancelUrl(cancelUrl).build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            response.put("error", 0);
            response.put("message", "success");
            response.set("data", objectMapper.valueToTree(data));
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "fail");
            response.set("data", null);
            return response;

        }
    }

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<HoaDon>> PayOSSuccess(
            @RequestParam("code") Long code,
            @RequestParam("id") String id,
            @RequestParam("cancel") boolean cancel,
            @RequestParam("status") String status,
            @RequestParam("orderCode") Long orderCode) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            // Query order details using orderCode
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);

            // Retrieve total amount and payment date
            float totalAmount = order.getAmount();
            Date paymentDate = new Date();

            // Save to HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(totalAmount);
            hoaDon.setStatus(status);
            hoaDon.setNgayTao(new java.sql.Date(paymentDate.getTime()));
            hoaDonService.save(hoaDon);

            return ResponseEntity.ok().body(new ApiResponse<>(true, "Payment success", hoaDon));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return ResponseEntity.ok().body(new ApiResponse<>(false, "Payment failed", null));
        }
    }


    @GetMapping(path = "/{orderId}")
    public ObjectNode getOrderById(@PathVariable("orderId") long orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);

            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }

    }

    @PutMapping(path = "/{orderId}")
    public ObjectNode cancelOrder(@PathVariable("orderId") long orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);
            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @PostMapping(path = "/confirm-webhook")
    public ObjectNode confirmWebhook(@RequestBody Map<String, String> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            String str = payOS.confirmWebhook(requestBody.get("webhookUrl"));
            response.set("data", objectMapper.valueToTree(str));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @GetMapping("/cancel")
    public ObjectNode getCanceledById(@RequestParam(value = "orderCode", required = false) Long orderCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            if (orderCode == null) {
                response.put("error", -1);
                response.put("message", "Order code is required");
                response.set("data", null);
                return response;
            }
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);

            // Save canceled order to HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(Float.valueOf(order.getAmount()));
            hoaDon.setStatus("CANCELED");
            hoaDon.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
            hoaDonService.save(hoaDon);

            response.set("data", objectMapper.valueToTree(order));
            response.put("message", "Canceled Thành Công");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }
}