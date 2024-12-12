package com.be.tapchi.pjtapchi.controller.payos;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.ordercontext.OrderContext;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.service.HopDongService;
import com.be.tapchi.pjtapchi.service.QuangCaoService;
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
    private final HopDongService hopDongService;
    private final QuangCaoService quangCaoService;
    String baseURL = "http://localhost:9000";
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public OrderController(PayOS payOS, HoaDonService hoaDonService, HopDongService hopDongService,
                           QuangCaoService quangCaoService) {
        super();
        this.payOS = payOS;
        this.hoaDonService = hoaDonService;
        this.hopDongService = hopDongService;
        this.quangCaoService = quangCaoService;
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
            final String token = requestBody.getToken();
            Integer hopdongId = requestBody.getHopdong_id();

            if (hopdongId == null) {
                response.put("error", -1);
                response.put("message", "Hopdong ID is required");
                response.set("data", null);
                return response;
            }
            OrderContext.setHopdongId(hopdongId);

            Taikhoan tk = null;
            if (token != null && !token.trim().isEmpty()) {
                tk = jwtUtil.getTaikhoanFromToken(token.trim());
                if (tk != null) {
                    OrderContext.setTaikhoanId(tk.getTaikhoan_id());
                } else {
                    response.put("error", -1);
                    response.put("message", "Invalid token");
                    response.set("data", null);
                    return response;
                }
            } else {
                response.put("error", -1);
                response.put("message", "Token is required");
                response.set("data", null);
                return response;
            }

            String currentTimeString = String.valueOf(new Date().getTime());
            Long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            if (productName == null) {
                response.put("error", -1);
                response.put("message", "Tên sản phẩm không được để trống");
                response.set("data", null);
                return response;
            }
            ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();

            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                    .item(item).returnUrl(returnUrl).cancelUrl(cancelUrl).build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);
            float totalAmount = order.getAmount();
            Date paymentDate = new Date();

            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(totalAmount);
            hoaDon.setStatus(0);
            hoaDon.setNgayTao(new java.sql.Date(paymentDate.getTime()));
            hoaDon.setTaikhoan(new Taikhoan(OrderContext.getTaikhoanId()));
            hoaDon.setHopDong(new HopDong(OrderContext.getHopdongId()));
            hoaDonService.save(hoaDon);
            OrderContext.setHoadonId(hoaDon.getHoadonId());

            response.put("error", 0);
            response.put("message", "thành công");
            response.set("data", objectMapper.valueToTree(data));
            response.set("hoadon", objectMapper.valueToTree(hoaDon));
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "thất bại");
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
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);
            float totalAmount = order.getAmount();
            Date paymentDate = new Date();

            HoaDon hoaDon = hoaDonService.findById(OrderContext.getHoadonId()).orElseThrow(() -> new Exception("HoaDon not found"));
            hoaDon.setTongTien(totalAmount);
            hoaDon.setStatus(1);
            hoaDon.setNgayTao(new java.sql.Date(paymentDate.getTime()));
            hoaDonService.save(hoaDon);

            QuangCao quangCao = new QuangCao();
            quangCao.setTaikhoan(new Taikhoan(OrderContext.getTaikhoanId()));
            quangCao.setStatus(1);
            quangCaoService.saveQuangCao(quangCao);
            Long qc_id = quangCao.getQuangcaoId();

            hopDongService.updateStatusAndHoaDonAndQuangCaoById(Long.valueOf(OrderContext.getHopdongId()), 1, hoaDon.getHoadonId(), qc_id);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Payment success", hoaDon));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return ResponseEntity.ok().body(new ApiResponse<>(false, "Payment failed", null));
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

            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(Float.valueOf(order.getAmount()));
            hoaDon.setStatus(2);
            hoaDon.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
            hoaDon.setTaikhoan(new Taikhoan(OrderContext.getTaikhoanId()));
            hoaDon.setHopDong(new HopDong(OrderContext.getHopdongId()));
            hoaDonService.save(hoaDon);

            hopDongService.updateStatusAndHoaDonById(Long.valueOf(OrderContext.getHopdongId()), 3, hoaDon);

            ObjectNode dataNode = objectMapper.createObjectNode();
            dataNode.set("order", objectMapper.valueToTree(order));
            dataNode.set("hoaDon", objectMapper.valueToTree(hoaDon));
            response.set("data", dataNode);
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
}