package com.be.tapchi.pjtapchi.controller.baibao;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.baibao.model.BaibaoResponseDTO;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOBaiBao;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOBaiBaoAuthor;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOBaiBaoEditor;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOKeyword;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOToKen;
import com.be.tapchi.pjtapchi.controller.baibao.model.KiemduyetAT;
import com.be.tapchi.pjtapchi.controller.baibao.model.KiemduyetED;
import com.be.tapchi.pjtapchi.controller.baibao.model.TaikhoanED;
import com.be.tapchi.pjtapchi.controller.danhmuc.DanhMucController;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOBaiBaoDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOBaiBaoDanhMuc;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOBinhluan;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOTaiKhoanDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOTheLoaiDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOThich;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import com.be.tapchi.pjtapchi.repository.KiemduyetRepository;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.service.KiemduyetService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/baibao")
public class baibaoController {

    @Autowired
    BinhluanService BinhluanService;

    @Autowired
    private KiemduyetRepository kiemduyetRepository;

    @Autowired
    private KiemduyetService kiemduyetService;

    @Autowired
    private BaibaoService bbService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TheloaiRepository theloaiRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    public boolean checkToken(String token) {
        try {
            if (token.isBlank() || token == null) {

                return false;
            }

            if (!jwtUtil.checkRolesFromToken(token, ManageRoles.getEDITORRole())) {

                return false;
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(token);
            if (tk == null) {

                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @PostMapping("/get/baibao/all/editor")
    public ResponseEntity<?> getAllBaibao(
            @RequestBody(required = false) DTOToKen entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            if (entity.getToken().isBlank() || entity.getToken() == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getEDITORRole())) {
                ApiResponse<?> response = new ApiResponse<>(false, "Không được phép truy cập", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (tk == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi tài khoản không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<Baibao> pageResult = bbService.findAllBaibao(pageable);
            Map<String, Object> data = new HashMap<>();

            List<DTOBaiBaoEditor> list = new ArrayList<>();
            for (Baibao baibao : pageResult.getContent()) {
                DTOBaiBaoEditor dBaoEditor = new DTOBaiBaoEditor();
                dBaoEditor.setId(String.valueOf(baibao.getId()));
                dBaoEditor.setTieude(baibao.getTieude());
                dBaoEditor.setFile(baibao.getFile());
                dBaoEditor.setUrl(baibao.getUrl());
                dBaoEditor.setNgaytao(baibao.getNgaytao());
                dBaoEditor.setNgaydang(baibao.getNgaydang());
                dBaoEditor.setNoidung(baibao.getNoidung());
                dBaoEditor.setTukhoa(baibao.getKeyword());
                TaikhoanED tked = new TaikhoanED();
                tked.setId(String.valueOf(baibao.getTaikhoan().getTaikhoan_id()));
                tked.setHovaten(baibao.getTaikhoan().getHovaten());
                dBaoEditor.setTaikhoans(tked);
                dBaoEditor.setStatus(baibao.getStatus());
                List<KiemduyetED> lKiemduyets = new ArrayList<>();
                for (Kiemduyet kditem : baibao.getKiemduyets()) {
                    KiemduyetED item = new KiemduyetED();
                    item.setId(String.valueOf(kditem.getId()));
                    item.setGhichu(kditem.getGhichu());
                    item.setStatus(kditem.getStatus());
                    TaikhoanED tEd = new TaikhoanED();
                    tEd.setId(String.valueOf(kditem.getTaikhoan().getTaikhoan_id()));
                    tEd.setHovaten(kditem.getTaikhoan().getHovaten());

                    item.setTaikhoan(tEd);

                    lKiemduyets.add(item);
                }
                dBaoEditor.setKiemduyet(lKiemduyets);
                list.add(dBaoEditor);
            }
            data.put("baibaos", list);
            Map<String, Object> phantrang = new HashMap<>();

            // phantrang.put("trang", pageResult.getNumber());
            // phantrang.put("kichthuoc", pageResult.getSize());
            // phantrang.put("tongbaibao", pageResult.getTotalElements());
            // phantrang.put("tongtrang", pageResult.getTotalPages());

            phantrang.put("totalPage", String.valueOf(pageResult.getTotalPages()));
            phantrang.put("pageNumber", String.valueOf(pageResult.getNumber()));
            phantrang.put("pageSize", String.valueOf(pageResult.getSize()));
            phantrang.put("totalElements", String.valueOf(pageResult.getTotalElements()));
            // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
            data.put("phantrang", phantrang);
            ApiResponse<?> response = new ApiResponse<>(true, "Fetch bai bao successful",
                    data);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Page<?>> response = new ApiResponse<>(true, "Loi lay dsbaibao theo tai khoan",
                    null);
            return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping("get/baibao/{id}")
    public ResponseEntity<ApiResponse<?>> getExample(@PathVariable("id") Integer id) {
        Baibao bb = bbService.getBaibaoById(id);
        if (bb != null) {
            // BaibaoResponseDTO responseDTO = convertToDTO(bb);
            ApiResponse<?> response = new ApiResponse<>(true, "Fetch bai bao successful", bb);
            return ResponseEntity.ok().body(response);
        } else {
            ApiResponse<BaibaoResponseDTO> response = new ApiResponse<>(true, "Fetch bai bao successful", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByTacGiaId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTacGiaId(id, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    public String formatStatus(int status) {
        // Lấy ngày giờ hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        return "[" + status + ":" + currentDateTime + "],";

    }

    // public Map<String,Object> getStausLichSu(Baibao bb){
    // Map<String,Object> map = new HashMap<>();
    // String[] list = bb.getLichsu().split(",");
    // for (int i = 0; i < list.length; i++) {
    // if(list[i].contains("[") || list[i].contains("]")){

    // }
    // }
    // }

    @PostMapping("/get/baibao/author")
    public ResponseEntity<ApiResponse<?>> getBaiBaoFromToken(
            @RequestBody(required = false) DTOToKen entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            if (entity.getToken().isBlank() || entity.getToken() == null) {
                ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token", null);
            return ResponseEntity.badRequest().body(response);
        }
        Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
        if (tk == null) {
            ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getAUTHORRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Không được phép truy cập", "Yêu cầu AUTHOR");
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTacGiaId(tk.getTaikhoan_id(), pageable);
        Map<String, Object> data = new HashMap<>();

        List<DTOBaiBaoAuthor> list = new ArrayList<>();
        for (Baibao baibao : pageResult.getContent()) {
            DTOBaiBaoAuthor dBaoEditor = new DTOBaiBaoAuthor();
            dBaoEditor.setId(String.valueOf(baibao.getId()));
            dBaoEditor.setTieude(baibao.getTieude());
            dBaoEditor.setFile(baibao.getFile());
            dBaoEditor.setUrl(baibao.getUrl());
            dBaoEditor.setNgaytao(baibao.getNgaytao());
            dBaoEditor.setNgaydang(baibao.getNgaydang());
            dBaoEditor.setNoidung(baibao.getNoidung());
            dBaoEditor.setTukhoa(baibao.getKeyword());
            // TaikhoanED tked = new TaikhoanED();
            // tked.setId(String.valueOf(baibao.getTaikhoan().getTaikhoan_id()));
            // tked.setHovaten(baibao.getTaikhoan().getHovaten());
            // dBaoEditor.setTaikhoans(tked);
            dBaoEditor.setStatus(baibao.getStatus());
            List<KiemduyetAT> lKiemduyets = new ArrayList<>();
            for (Kiemduyet kditem : baibao.getKiemduyets()) {
                KiemduyetAT item = new KiemduyetAT();
                item.setId(String.valueOf(kditem.getId()));
                item.setGhichu(kditem.getGhichu());
                item.setStatus(kditem.getStatus());
                TaikhoanED tEd = new TaikhoanED();
                tEd.setId(String.valueOf(kditem.getTaikhoan().getTaikhoan_id()));
                tEd.setHovaten(kditem.getTaikhoan().getHovaten());

                lKiemduyets.add(item);
            }
            dBaoEditor.setKiemduyet(lKiemduyets);
            list.add(dBaoEditor);
        }
        data.put("baibaos", list);
        Map<String, Object> phantrang = new HashMap<>();

        phantrang.put("trang", pageResult.getNumber());
        phantrang.put("kichthuoc", pageResult.getSize());
        phantrang.put("tongbaibao", pageResult.getTotalElements());
        phantrang.put("tongtrang", pageResult.getTotalPages());
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        data.put("phantrang", phantrang);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<?> response = new ApiResponse<>(true, "Fetch bai bao successful",
                data);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/theloai/{id}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByTheLoaiID(
            @PathVariable("id") Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTheLoaiID(id, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{postDate}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByNgayDang(
            @PathVariable("postDate") LocalDate postDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByNgayDang(postDate, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{date1}/{date2}")
    public ResponseEntity<ApiResponse<?>> getBaibaiByNgayDangBetween(
            @PathVariable("date1") LocalDate date1,
            @PathVariable("date2") LocalDate date2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaiByNgayDangBetween(date1, date2, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByTrangThai(
            @PathVariable("status") Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTrangThai(status, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBaibao(@PathVariable("id") Integer id) {
        bbService.deleteBaibao(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Delete bai bao successful", null);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createBaibao(@Valid @RequestBody DTOBaiBao entity,
            BindingResult bindingResult) {
        // Extract token from request body
        // kiem tra du lieu bi trong
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            ApiResponse<?> response = new ApiResponse<>(false, "Lỗi trống dữ liệu", errorMessage);
            return ResponseEntity.badRequest().body(response);
        }
        try {
            // check token va quyen han
            try {
                if (entity.getToken() == null) {

                    ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }

                if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getAUTHORRole())) {

                    ApiResponse<?> response = new ApiResponse<>(false, "Không có quyền", null);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
                Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
                if (tk == null) {

                    ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            } catch (Exception e) {
                // TODO: handle exception
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token", e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            Theloai tl = theloaiRepository.findTheloaiById(Integer.valueOf(entity.getTheloaiId()));
            if (tl == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi khi tìm thể loại", null);
                return ResponseEntity.badRequest().body(response);
            }
            try {
                if (entity.getBaibaoId() != null) {

                    Baibao bb = baiBaoRepository.findById(Integer.valueOf(entity.getBaibaoId())).orElse(null);
                    if (bb == null) {
                        ApiResponse<?> response = new ApiResponse<>(false, "Lỗi khi tim bai bao", null);
                        return ResponseEntity.badRequest().body(response);
                    }

                    Integer[] kdid = bb.getKiemduyets().stream().map(Kiemduyet::getId).toArray(Integer[]::new);
                    if (kdid.length > 0) {
                        for (Integer id : kdid) {
                            try {
                                kiemduyetRepository.deleteByIdKd(Long.valueOf(id));
                                System.out.println("Da xoa kd co ID: " + id);
                            } catch (Exception e) {
                                System.err.println(
                                        "Loi xoa kd co ID: " + id + ". Loi: " + e.getMessage());
                            }
                        }

                        System.out.println("Da xoa kiem duyet");
                    }

                    // kiemduyetService.deleteKiemduyets(bb);

                    // kiemduyetRepository.deleteById(Long.valueOf(14));

                    bb.setTieude(entity.getTieude());
                    bb.setNoidung(entity.getNoidung());
                    bb.setUrl(entity.getUrl());
                    bb.setFile(entity.getFile());
                    bb.setKeyword(entity.getTukhoa());
                    bb.setTheloai(tl);
                    bb.setNgaytao(LocalDate.now());

                    bb.setStatus(1);

                    bbService.saveBaibao(bb);

                    ApiResponse<Baibao> response = new ApiResponse<>(true, "Cập nhật bài báo thành công", null);
                    return ResponseEntity.ok().body(response);
                }
            } catch (Exception e) {
                // TODO: handle exception
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi khi tim bai bao", e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
            // cap nhat neu baibaoid != null

            Baibao baibao = new Baibao();
            baibao.setTieude(entity.getTieude());
            baibao.setNoidung(entity.getNoidung());
            baibao.setUrl(entity.getUrl());
            baibao.setFile(entity.getFile());
            baibao.setKeyword(entity.getTukhoa());

            baibao.setTheloai(tl);
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            baibao.setTaikhoan(tk);

            // Set default values
            baibao.setNgaytao(LocalDate.now());
            baibao.setStatus(1);
            baibao.setLichsu(formatStatus(baibao.getStatus()));
            Baibao bb = bbService.saveBaibao(baibao);
            ApiResponse<Baibao> response = new ApiResponse<>(true, "Tạo bài báo thành công", bb);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Baibao> response = new ApiResponse<>(true, "Lỗi khi tạo bài báo", null);
            System.out.println("Loi tao bb: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("search/keyword")
    public ResponseEntity<?> postMethodName(@RequestBody DTOKeyword entity, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        try {
            if (entity.getKeyword() == null) {
                // keyword null
                return ResponseEntity.ok()
                        .body(new ApiResponse<>(true, "Không tìm thấy bài báo phù hợp", null));
            } else {
                if (entity.getKeyword().isEmpty() || entity.getKeyword().trim().isEmpty()) {
                    // de trong keyword
                    return ResponseEntity.ok().body(new ApiResponse<>(true, "Vui lòng nhập từ khóa", null));
                } else {
                    // tim kiem theo keyword
                    Pageable pageable = PageRequest.of(page, size);
                    Page<DanhMuc> result = danhMucRepository.findDanhmucByKeyword(entity.getKeyword(), pageable);
                    ///
                    Map<String, Object> map = new HashMap<>();
                    List<DTOBaiBaoDanhMuc> listdata = new ArrayList<>();
                    for (DanhMuc danhMuc : result.getContent()) {
                        DTOBaiBaoDanhMuc bbDM = new DTOBaiBaoDanhMuc();
                        bbDM.setDanhmucId(String.valueOf(danhMuc.getDanhmucId()));
                        bbDM.setTieude(danhMuc.getTieude());
                        bbDM.setMota(danhMuc.getMota());
                        bbDM.setTuan(String.valueOf(danhMuc.getTuan()));
                        bbDM.setSo(String.valueOf(danhMuc.getSo()));
                        bbDM.setUrl(danhMuc.getUrl());
                        bbDM.setStatus(danhMuc.getStatus() + "");
                        bbDM.setNgaytao(danhMuc.getNgaytao());
                        List<DTOBaiBaoDM> listbbDM = new ArrayList<>();
                        for (Danhmucbaibao dmbb : danhMuc.getDanhmucbaibaos()) {
                            if (dmbb.getBaibao().getStatus() == null) {
                                continue;
                            }
        
                            if (dmbb.getBaibao().getStatus() != 7) {
                                continue;
                            }
        
                            DTOTaiKhoanDM tk1 = new DTOTaiKhoanDM();
                            //tk1.setTaikhoanId(String.valueOf(dmbb.getBaibao().getTaikhoan().getTaikhoan_id()));
                            tk1.setHovaten(dmbb.getBaibao().getTaikhoan().getHovaten());
                            DTOTheLoaiDM tl1 = new DTOTheLoaiDM();
                            tl1.setTheloaiId(String.valueOf(dmbb.getBaibao().getTheloai().getId()));
                            tl1.setTen(dmbb.getBaibao().getTheloai().getTenloai());
                            DTOBaiBaoDM bb1 = new DTOBaiBaoDM();
                            bb1.setBaibaoId(String.valueOf(dmbb.getBaibao().getId()));
                            bb1.setTieude(dmbb.getBaibao().getTieude());
                            bb1.setNoidung(dmbb.getBaibao().getNoidung());
                            bb1.setUrl(dmbb.getBaibao().getUrl());
                            bb1.setFile(dmbb.getBaibao().getFile());
                            bb1.setKeyword(dmbb.getBaibao().getKeyword());
                            bb1.setNgaydang(dmbb.getBaibao().getNgaydang());
                            bb1.setStatus(String.valueOf(dmbb.getBaibao().getStatus()));
                            bb1.setTaikhoan(tk1);
                            bb1.setTheloai(tl1);
                            bb1.setNgaytao(dmbb.getBaibao().getNgaytao());
                            int slike = 0;
                            if(dmbb.getBaibao().getThichs() != null){
                                if(dmbb.getBaibao().getThichs().size() > 0){
                                    slike = dmbb.getBaibao().getThichs().size();
                                }
                            }
                            DTOThich thich = new DTOThich();
                            thich.setThich(String.valueOf(slike));
                            bb1.setThich(thich);
                            // bl
                            List<DTOBinhluan> list = new ArrayList<>();
                            for (Binhluan bl : dmbb.getBaibao().getBinhluans()) {
                                DTOBinhluan dtoBinhluan = new DTOBinhluan();
                                dtoBinhluan.setHovaten(bl.getTaikhoan().getHovaten());
                                dtoBinhluan.setNoidung(bl.getNoidung());
                                dtoBinhluan.setThoigian(DanhMucController.formatDateTime(bl.getThoigianbl()+""));
                                list.add(dtoBinhluan);
                            }
        
                            bb1.setBinhluans(list);
                            
                            listbbDM.add(bb1);
        
                        }
                        bbDM.setBaibao(listbbDM);
                        listdata.add(bbDM);
                    }
                    map.put("data", listdata);
                    Map<String, Object> phantrang = new HashMap<>();
                    phantrang.put("totalPage", String.valueOf(result.getTotalPages()));
                    phantrang.put("pageNumber", String.valueOf(result.getNumber()));
                    phantrang.put("pageSize", String.valueOf(result.getSize()));
                    phantrang.put("totalElements", String.valueOf(result.getTotalElements()));
                    map.put("phantrang", phantrang);
                    ///
                    return ResponseEntity.ok().body(new ApiResponse<>(true, "2", map));
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Lỗi khi tìm kiếm bài báo", e.getMessage()));
        }

    }

    // private BaibaoResponseDTO convertToDTO(Baibao baibao) {
    // String tieuDe = null;
    // Integer so = null;
    // Integer tuan = null;

    // List<Danhmucbaibao> danhmucbaibaos = baibao.getDanhmucbaibaos();
    // for (Danhmucbaibao dmbb : danhmucbaibaos) {
    // DanhMuc dm = dmbb.getDanhmuc();
    // if (dm != null) {
    // tieuDe = dm.getTieuDe();
    // so = dm.getSo();
    // tuan = dm.getTuan();
    // break; // Assuming one-to-one relationship for simplicity
    // }
    // }

    // return new BaibaoResponseDTO(baibao, tieuDe, so, tuan);
    // }

}