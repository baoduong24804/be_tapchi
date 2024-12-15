package com.be.tapchi.pjtapchi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {
//    String baseUrl = "http://localhost:9000";

    private String productName;
    private String description;
    private int price;
    private String token;
    private Integer hopdong_id;
    private Long banggiaqc_id;
    //    private String returnUrl = baseUrl + "/payOS/success";
//    private String cancelUrl = baseUrl + "/cancel";

}