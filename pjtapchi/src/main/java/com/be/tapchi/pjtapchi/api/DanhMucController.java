//package com.be.tapchi.pjtapchi.api;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.be.tapchi.pjtapchi.model.Baibao;
//import com.be.tapchi.pjtapchi.model.DanhMuc;
//import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
//import com.be.tapchi.pjtapchi.service.BaibaoService;
//
//@RestController
//@RequestMapping("/api")
//public class DanhMucController {
//    @Autowired
//    private DanhMucRepository dmrep;
//    @Autowired
//    BaibaoService bbser;
//
//    @GetMapping("/danhmuc")
//    public List<DanhMuc> getAllDanhMuc() {
//        return dmrep.findAll();
//    }
//
//    @GetMapping("/baibao")
//    public List<Baibao> getAllBaiBao() {
//        return bbser.getAllBaibaos();
//    }
//
//    // @PostMapping("danhmuc")
//    // public ResponseEntity<DanhMuc> createDanhMuc(@RequestBody DanhMuc danhMuc) {
//    // DanhMuc savedDanhMuc = dmrep.save(danhMuc);
//    // return ResponseEntity.ok(savedDanhMuc);
//    // }
//
//    // Add other methods (like for PUT, DELETE) as needed
//}
