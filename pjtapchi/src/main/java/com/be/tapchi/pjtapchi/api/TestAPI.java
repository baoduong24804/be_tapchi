package com.be.tapchi.pjtapchi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.service.HopDongService;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;




@RestController
@RequestMapping("api")
public class TestAPI {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private BangGiaQCService bangGiaQCService;

    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HopDongService hpService;
    @GetMapping("users")
    public ResponseEntity<ApiResponse<List<Taikhoan>>> getExample() {
        List<Taikhoan> list = taiKhoanService.getAllTaiKhoans();
        ApiResponse<List<Taikhoan>> response = new ApiResponse<>(true, "Fetch successful", list);

        if (list.isEmpty()) {
            
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @GetMapping("advertisement")
    public List<BangGiaQC> getAll(){
        return bangGiaQCService.findAll();
    }

    @GetMapping("hoadon")
    public List<HoaDon> getAll2() {
        List<HoaDon> ls = hoaDonService.findAll();
        try {
           if(ls != null){
            System.out.println("Có dữ liệu");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    @GetMapping("hopdong")
    public List<HopDong> getAll3() {
        return hpService.getAllHopDongs();
    }
    
    
    

}
