package com.be.tapchi.pjtapchi.controller.payos;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
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
    private Long tk_id;
    private Integer hopdong_id;

    @Autowired
    public OrderController(PayOS payOS, HoaDonService hoaDonService, HopDongService hopDongService,
                           QuangCaoService quangCaoService) {
        super();
        this.payOS = payOS;
        this.hoaDonService = hoaDonService;
        this.hopDongService = hopDongService;
        this.quangCaoService = quangCaoService;
    }

    /**
     * Tạo liên kết thanh toán.
     *
     * @param requestBody Thông tin yêu cầu tạo liên kết thanh toán.
     * @return ObjectNode chứa thông tin phản hồi từ PayOS.
     */
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
            hopdong_id = requestBody.getHopdong_id();

            // Get taikhoan_id from token
            Taikhoan tk = null;
            if (requestBody.getToken() != null) {
                if (!requestBody.getToken().trim().isEmpty()) {
                    tk = jwtUtil.getTaikhoanFromToken(requestBody.getToken() + "".trim());
                    tk_id = tk.getTaikhoan_id();
                    hopdong_id = hopdong_id;
                    System.out.println(tk_id);
                    System.out.println(hopdong_id);
                }
            }
            // Tạo mã đơn hàng
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

            //Create hoadon
            // Query order details using orderCode
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);

            // Retrieve total amount and payment date
            float totalAmount = order.getAmount();
            Date paymentDate = new Date();

            // Save to HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(totalAmount);
            hoaDon.setStatus(0);
            hoaDon.setNgayTao(new java.sql.Date(paymentDate.getTime()));
            hoaDon.setTaikhoan(new Taikhoan(tk_id));
            hoaDon.setHopDong(new HopDong(hopdong_id));
            hoaDonService.save(hoaDon);
            Long hoadon_id = hoaDon.getHoadonId();


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
            // Query order details using orderCode
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);

            // Retrieve total amount and payment date
            float totalAmount = order.getAmount();
            Date paymentDate = new Date();

            // Update to HoaDon status = 1
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(totalAmount);
            hoaDon.setStatus(1);
            hoaDon.setNgayTao(new java.sql.Date(paymentDate.getTime()));
            hoaDon.setTaikhoan(new Taikhoan(tk_id));
            hoaDon.setHopDong(new HopDong(hopdong_id));
            hoaDonService.save(hoaDon);
            Long hoadon_id = hoaDon.getHoadonId();
            // Create new QuangCao
            QuangCao quangCao = new QuangCao();
            quangCao.setTaikhoan(new Taikhoan(tk_id));
            quangCao.setStatus(1);
            quangCaoService.saveQuangCao(quangCao);
            Long qc_id = quangCao.getQuangcaoId();

            // Update HopDong status = 1, HoaDon, quangcao_id by id
            hopDongService.updateStatusAndHoaDonAndQuangCaoById(Long.valueOf(hopdong_id), 1, hoadon_id, qc_id);
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

            // Save canceled order to HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTongTien(Float.valueOf(order.getAmount()));
            hoaDon.setStatus(2);
            hoaDon.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
            hoaDon.setTaikhoan(new Taikhoan(tk_id));
            hoaDon.setHopDong(new HopDong(hopdong_id));
            hoaDonService.save(hoaDon);

            // Update HopDong status = 3 and HoaDon by id
            hopDongService.updateStatusAndHoaDonById(Long.valueOf(hopdong_id), 3, hoaDon);

            // Return response
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