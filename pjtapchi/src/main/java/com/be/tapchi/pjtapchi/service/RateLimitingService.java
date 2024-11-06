package com.be.tapchi.pjtapchi.service;


import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // Phương thức để lấy bucket dựa trên key (có thể là IP hoặc email)
    public Bucket resolveBucket(String key) {
        // Sử dụng lambda thay vì method reference
        return buckets.computeIfAbsent(key, k -> createNewBucket());
    }

    // Tạo bucket mới với các giới hạn
    private Bucket createNewBucket() {
        // Tạo refill với 5 token mỗi phút
        Refill refill = Refill.greedy(3, Duration.ofMinutes(1)); // 5 requests / phut
        // Tạo bandwidth với refill và limit
        Bandwidth limit = Bandwidth.classic(3, refill); // 5 requests / phut

        // Tạo bucket với các giới hạn
        return Bucket.builder()
                .addLimit(limit) // giới hạn theo phút
                .build();
    }

}
