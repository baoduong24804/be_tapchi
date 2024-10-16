package com.be.tapchi.pjtapchi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.service.HopDongService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hopdong")
public class HopdongController {

    @Autowired
    private HopDongService hopdongService;

    // API để lấy tất cả hợp đồng
    @GetMapping
    public List<HopDong> getAllHopdong() {
        return hopdongService.getAllHopdong();
    }

    // API để lấy hợp đồng theo ID
    @GetMapping("/{id}")
    public Optional<HopDong> getHopdongById(@PathVariable Long id) {
        return hopdongService.getHopdongById(id);
    }

    // API để tạo hợp đồng mới
    @PostMapping
    public HopDong createHopdong(@RequestBody HopDong hopdong) {
        return hopdongService.saveHopdong(hopdong);
    }

    // API để xoá hợp đồng theo ID
    @DeleteMapping("/{id}")
    public void deleteHopdong(@PathVariable Long id) {
        hopdongService.deleteHopdong(id);
    }
}
