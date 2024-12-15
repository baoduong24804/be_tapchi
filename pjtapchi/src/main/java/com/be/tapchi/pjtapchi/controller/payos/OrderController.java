package com.be.tapchi.pjtapchi.controller.payos;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.*;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.service.HopDongService;
import com.be.tapchi.pjtapchi.service.QuangCaoService;
import com.be.tapchi.pjtapchi.type.CreatePaymentLinkRequestBody;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;
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
    private final BangGiaQCService bgqcService;
    String baseURL = "http://localhost:9000";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public OrderController(PayOS payOS, HoaDonService hoaDonService, HopDongService hopDongService,
                           QuangCaoService quangCaoService, BangGiaQCService bgqcService) {
        super();
        this.payOS = payOS;
        this.hoaDonService = hoaDonService;
        this.hopDongService = hopDongService;
        this.quangCaoService = quangCaoService;
        this.bgqcService = bgqcService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody) {
        try {
            final String productName = requestBody.getProductName();
            final String description = requestBody.getDescription();
            final String returnUrl = baseURL + "/order/success";
            final String cancelUrl = baseURL + "/order/cancel";
            final int price = requestBody.getPrice();
            final String token = requestBody.getToken();
            final Long banggiaqc_id = requestBody.getBanggiaqc_id();
            Long hopdongId = Long.valueOf(requestBody.getHopdong_id());

            if (!jwtUtil.checkTokenAndTaiKhoan(requestBody.getToken())) {
                ApiResponse<Map<String, Object>> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }

            if (!jwtUtil.checkRolesFromToken(requestBody.getToken(), ManageRoles.getPARTNERRole())) {
                ApiResponse<Map<String, Object>> response = new ApiResponse<>(false, "Bạn Không Có Quyền Tạo QR Thanh Toán", null);
                return ResponseEntity.badRequest().body(response);
            }

            Taikhoan tk = null;
            if (token != null && !token.trim().isEmpty()) {
                tk = jwtUtil.getTaikhoanFromToken(token.trim());
                if (tk != null) {
                    System.out.println("Userid is : " + tk.getTaikhoan_id());
                } else {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid token", null));
                }
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Token is required", null));
            }

            String currentTimeString = String.valueOf(new Date().getTime());
            Long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            if (productName == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Tên sản phẩm không được để trống", null));
            }
            ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();

            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                    .item(item).returnUrl(returnUrl).cancelUrl(cancelUrl).build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            //retrieve BangGiaQC
            BangGiaQC bangGiaQC = bgqcService.findBangGiaQCByBanggiaqc_id(banggiaqc_id);

            // Retrieve hopdong
            HopDong hopDong = hopDongService.getHopDongById(hopdongId);

            // Save hoadon to database
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(Float.valueOf(price));
            hoaDon.setStatus(0);
            hoaDon.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
            hoaDon.setTaikhoan(tk);
            hoaDon.setHopDong(hopDong);
            hoaDon.setOrderCode(orderCode.toString());
            hoaDon.setBanggiaqc_id(banggiaqc_id);
            hoaDonService.save(hoaDon);

            // Update hopdong
            hopDongService.updateStatusAndHoaDonById(hopdongId, 0, hoaDon);

            Map<String, Object> responseData = Map.of(
                    "checkoutData", data,
                    "hoaDon", hoaDon,
                    "hopDong", hopDong
            );

            return ResponseEntity.ok(new ApiResponse<>(true, "thành công", responseData));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "thất bại", null));
        }
    }

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<Map<String, Object>>> PayOSSuccess(
            @RequestParam("code") Long code,
            @RequestParam("id") String id,
            @RequestParam("cancel") boolean cancel,
            @RequestParam("status") String status,
            @RequestParam("orderCode") Long orderCode) {

        try {
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);
            float totalAmount = order.getAmount();
            Date paymentDate = new Date();

            // update hoa status = 1 (đã thanh toán) by orderCode
            hoaDonService.updateStatusByOrderCode(orderCode.toString(), 1);
            HoaDon hoaDon = hoaDonService.findHoaDonByOrderCode(orderCode.toString());

            // Find hopdong by orderCode
            Long hopdongId = hoaDonService.findHopDongByOrderCode(orderCode.toString());

            // update hopdong status = 1 (đã thanh toán) by hopdongId
            hopDongService.updateStatusById(hopdongId, 1);
            HopDong hopDong = hopDongService.getHopDongById(hopdongId);

            // Retrieve banggiaqc
            BangGiaQC bangGiaQC = bgqcService.findBangGiaQCByBanggiaqc_id(hoaDon.getBanggiaqc_id());

            QuangCao qc = new QuangCao();
            qc.setTieude("Quảng cáo " + bangGiaQC.getSongay() + " ngày");
            qc.setLink("https://www.google.com");
            qc.setStatus(1);
            qc.setBgqc(bangGiaQC);
            qc.setLuotxem(0);
            qc.setLuotclick(0);
            qc.setHopDong(hopDong);
            qc.setTaikhoan(hoaDon.getTaikhoan());
            quangCaoService.saveQuangCao(qc);

            Map<String, Object> data = Map.of(
                    "order", order,
                    "hoaDon", hoaDon,
                    "hopDong", hopDong
            );

            return ResponseEntity.ok(new ApiResponse<>(true, "Payment success", data));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Payment failed", null));
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCanceledById(@RequestParam(value = "orderCode", required = false) Long orderCode) {
        try {
            if (orderCode == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Order code is required", null));
            }
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);

            // Update hoadon status = 2 (đã hủy) by orderCode
            hoaDonService.updateStatusByOrderCode(orderCode.toString(), 2);
            HoaDon hoaDon = hoaDonService.findHoaDonByOrderCode(orderCode.toString());

            // Find hopdong by orderCode
            Long hopdongId = hoaDonService.findHopDongByOrderCode(orderCode.toString());

            // Update hopdong status = 0 (chưa thanh toán) by hopdongId
            hopDongService.updateStatusById(hopdongId, 0);

            Map<String, Object> data = Map.of(
                    "order", order,
                    "hoaDon", hoaDon,
                    "hopDong", hopDongService.getHopDongById(hopdongId)
            );

            return ResponseEntity.ok(new ApiResponse<>(true, "Canceled Thành Công", data));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null));
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