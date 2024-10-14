package com.be.tapchi.pjtapchi.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class TestAPI {
    @GetMapping("hello")
    public ResponseEntity<ApiResponse<String>> getExample(@RequestParam String text) {
        if (text.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>(200, "User found", "data");
            return ResponseEntity.ok().body(response);
        } else {
            ApiResponse<String> response = new ApiResponse<>(404, "User not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

}
