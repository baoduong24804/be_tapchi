package com.be.tapchi.pjtapchi.controller.danhmuc;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOAddBBDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOBaiBaoDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOBaiBaoDanhMuc;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOBinhluan;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOCreateDanhMuc;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTODanhMucBaiBao2;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOTaiKhoanDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOTheLoaiDM;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.DTOThich;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucBaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.DanhMucService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/danhmuc")
public class DanhMucController {

    @Autowired
    DanhMucService danhMucService;

    @Autowired
    DanhMucBaiBaoRepository danhMucBaiBaoRepository;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    @Autowired
    private BaibaoService baibaoService;

    @Autowired
    DanhMucRepository danhMucRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    public static String formatDateTime(String inputDateTime) {
        try {
            // Định dạng của chuỗi đầu vào
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            // Định dạng của chuỗi đầu ra
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            // Parse chuỗi đầu vào sang LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);
            // Định dạng lại chuỗi đầu ra
            return dateTime.format(outputFormatter);
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu chuỗi không đúng định dạng
            return "Invalid date format: " + e.getMessage();
        }
    }

    @PostMapping("/get/week")
    public ResponseEntity<ApiResponse<?>> getDanhmucInCurrentWeek(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        try {
            ApiResponse<?> api = new ApiResponse<>();
            api.setSuccess(true);
            api.setMessage("Thành công lấy dữ liệu danh mục theo tuần");
            Map<String, Object> map = new HashMap<>();
            Page<DanhMuc> dm = danhMucService.getDanhmucInCurrentWeek(page, size);
            //System.out.println(dm.getContent().size() + "sizeeeeeeeee");
            List<DTOBaiBaoDanhMuc> listdata = new ArrayList<>();
            for (DanhMuc danhMuc : dm.getContent()) {
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
                        dtoBinhluan.setThoigian(formatDateTime(bl.getThoigianbl()+""));
                        list.add(dtoBinhluan);
                    }

                    bb1.setBinhluans(list);
                    
                    listbbDM.add(bb1);

                }
                bbDM.setBaibao(listbbDM);
                listdata.add(bbDM);
            }
            map.put("data", listdata);

            map.put("totalPage", String.valueOf(dm.getTotalPages()));
            map.put("pageNumber", String.valueOf(dm.getNumber()));
            map.put("pageSize", String.valueOf(dm.getSize()));
            map.put("totalElements", String.valueOf(dm.getTotalElements()));
            api.setData(map);
            return ResponseEntity.ok().body(api);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllDanhMuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<DanhMuc> danhMucPage = danhMucService.getAllDanhMuc(pageable);
            if (danhMucPage == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Map<String, Object> data = new HashMap<>();
            List<DTODanhMucBaiBao2> list = new ArrayList<>();
            for (DanhMuc danhMuc : danhMucPage.getContent()) {
                DTODanhMucBaiBao2 et = new DTODanhMucBaiBao2();
                et.setDanhmucId(danhMuc.getDanhmucId() + "");
                et.setTieude(danhMuc.getTieude());
                et.setMota(danhMuc.getMota());
                et.setTuan(danhMuc.getTuan() + "");
                et.setSo(danhMuc.getSo() + "");
                et.setStatus(danhMuc.getStatus() + "");
                et.setUrl(danhMuc.getUrl());
                et.setNgaytao(danhMuc.getNgaytao());
                List<DTOBaiBaoDM> baibaos = new ArrayList<>();
                for (Danhmucbaibao dmbb : danhMuc.getDanhmucbaibaos()) {
                    DTOBaiBaoDM et2 = new DTOBaiBaoDM();
                    et2.setBaibaoId(dmbb.getBaibao().getId() + "");
                    et2.setTieude(dmbb.getBaibao().getTieude());
                    et2.setNoidung(dmbb.getBaibao().getNoidung());
                    et2.setFile(dmbb.getBaibao().getFile());
                    et2.setKeyword(dmbb.getBaibao().getKeyword());
                    et2.setNgaydang(dmbb.getBaibao().getNgaydang());
                    et2.setNgaytao(dmbb.getBaibao().getNgaytao());
                    et2.setStatus(dmbb.getBaibao().getStatus() + "");
                    et2.setUrl(dmbb.getBaibao().getUrl());
                    DTOTaiKhoanDM tk = new DTOTaiKhoanDM();
                    //tk.setTaikhoanId(dmbb.getBaibao().getTaikhoan().getTaikhoan_id() + "");
                    tk.setHovaten(dmbb.getBaibao().getTaikhoan().getHovaten());
                    et2.setTaikhoan(tk);
                    DTOTheLoaiDM tl = new DTOTheLoaiDM();
                    tl.setTheloaiId(dmbb.getBaibao().getTheloai().getId() + "");
                    tl.setTen(dmbb.getBaibao().getTheloai().getTenloai());
                    et2.setTheloai(tl);
                    baibaos.add(et2);
                }
                et.setBaibaos(baibaos);
                list.add(et);
            }
            data.put("data", list);

            Map<String, Object> phantrang = new HashMap<>();

            phantrang.put("totalPage", String.valueOf(danhMucPage.getTotalPages()));
            phantrang.put("pageNumber", String.valueOf(danhMucPage.getNumber()));
            phantrang.put("pageSize", String.valueOf(danhMucPage.getSize()));
            phantrang.put("totalElements", String.valueOf(danhMucPage.getTotalElements()));
            // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
            data.put("phantrang", phantrang);
            ApiResponse<?> response = new ApiResponse<>(true, "Danh sách danh mục", data);
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi lấy danh mục.", null));
        }
    }

    // @GetMapping("/all")
    // public ResponseEntity<ApiResponse<Page<DanhMuc>>> getAllDanhMuc(
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "6") int size) {
    // try {
    // Pageable pageable = PageRequest.of(page, size);
    // Page<DanhMuc> danhMucPage = danhMucService.getAllDanhMuc(pageable);
    // ApiResponse<Page<DanhMuc>> response = new ApiResponse<>(true, "Danh sách danh
    // mục", danhMucPage);

    // if (danhMucPage.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    // } else {
    // return ResponseEntity.ok().body(response);
    // }
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi lấy danh mục.", null));
    // }
    // }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createDanhMuc(@Valid @RequestBody(required = false) DTOCreateDanhMuc danhMuc,
            BindingResult bindingResult) {
        try {
            try {
                if (danhMuc.getToken() == null) {
                    ApiResponse<?> response = new ApiResponse<>(false, "Lỗi trống", null);
                    return ResponseEntity.badRequest().body(response);
                }
                Taikhoan tk = jwtUtil.getTaikhoanFromToken(danhMuc.getToken());
                if (tk == null) {
                    ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                    return ResponseEntity.badRequest().body(response);
                }
                if (!jwtUtil.checkRolesFromToken(danhMuc.getToken(), ManageRoles.getEDITORRole())) {
                    ApiResponse<?> response = new ApiResponse<>(false, "Không được phép truy cập", "Yêu cầu EDITTOR");
                    return ResponseEntity.badRequest().body(response);
                }
            } catch (Exception e) {
                // TODO: handle exception
                ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token", null);
                return ResponseEntity.badRequest().body(response);
            }
            // kiem tra du lieu bi trong
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                ApiResponse<?> response = new ApiResponse<>(false, "Không được để trống dữ liệu", errorMessage);
                return ResponseEntity.badRequest().body(response);
            }
            if (danhMuc.getDanhmucId() == null) {
                List<DanhMuc> list = danhMucRepository.findByTuanAndSo(Integer.valueOf(danhMuc.getTuan()),
                        Integer.valueOf(danhMuc.getSo()));
                if (list.size() > 0) {
                    ApiResponse<?> response = new ApiResponse<>(false, "Danh mục có tuần và số đã tồn tại", null);
                    return ResponseEntity.ok().body(response);
                }
            }

            DanhMuc dMuc = new DanhMuc();
            if (danhMuc.getDanhmucId() == null) {
                dMuc.setTieude(danhMuc.getTieude());
                dMuc.setMota(danhMuc.getMota());
                dMuc.setNgaytao(LocalDate.now());
                dMuc.setSo(Integer.valueOf(danhMuc.getSo()));
                dMuc.setTuan(Integer.valueOf(danhMuc.getTuan()));
                dMuc.setUrl(danhMuc.getUrl());
                dMuc.setStatus(0);
                danhMucService.saveDanhMuc(dMuc);
                ApiResponse<?> response = new ApiResponse<>(true, "them danh muc successful", null);
                return ResponseEntity.ok().body(response);
            } else {
                DanhMuc dMuc2 = danhMucRepository.findById(Long.valueOf(danhMuc.getDanhmucId())).orElse(null);
                if(dMuc2 == null){
                    ApiResponse<?> response = new ApiResponse<>(true, "ko tim thay danh muc", null);
                    return ResponseEntity.badRequest().body(response);
                }
                dMuc2.setTieude(danhMuc.getTieude());
                dMuc2.setMota(danhMuc.getMota());
                // dMuc.setNgaytao(LocalDate.now());
                dMuc2.setSo(Integer.valueOf(danhMuc.getSo()));
                dMuc2.setTuan(Integer.valueOf(danhMuc.getTuan()));
                if (dMuc2.getUrl() != null) {
                    dMuc2.setUrl(danhMuc.getUrl());
                }
                dMuc2.setStatus(0);
                danhMucService.saveDanhMuc(dMuc2);
                ApiResponse<?> response = new ApiResponse<>(true, "cap nhat danh muc successful", null);
                return ResponseEntity.ok().body(response);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Số và tuần không hợp lệ", null));
        }
    }

    // @PostMapping("/create")
    // public ResponseEntity<ApiResponse<?>> updateDanhMuc(@Valid
    // @RequestBody(required = false) DTOCreateDanhMuc danhMuc,
    // BindingResult bindingResult) {
    // try {
    // try {
    // if (danhMuc.getToken() == null) {
    // ApiResponse<?> response = new ApiResponse<>(false, "Lỗi trống", null);
    // return ResponseEntity.badRequest().body(response);
    // }
    // Taikhoan tk = jwtUtil.getTaikhoanFromToken(danhMuc.getToken());
    // if (tk == null) {
    // ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp
    // lệ", null);
    // return ResponseEntity.badRequest().body(response);
    // }
    // if (!jwtUtil.checkRolesFromToken(danhMuc.getToken(),
    // ManageRoles.getEDITORRole())) {
    // ApiResponse<?> response = new ApiResponse<>(false, "Không được phép truy
    // cập", "Yêu cầu EDITTOR");
    // return ResponseEntity.badRequest().body(response);
    // }
    // } catch (Exception e) {
    // // TODO: handle exception
    // ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token", null);
    // return ResponseEntity.badRequest().body(response);
    // }

    // // kiem tra du lieu bi trong
    // if (bindingResult.hasErrors()) {
    // String errorMessage = bindingResult.getFieldErrors().stream()
    // .map(error -> error.getDefaultMessage())
    // .collect(Collectors.joining(", "));
    // ApiResponse<?> response = new ApiResponse<>(false, "Không được để trống dữ
    // liệu", errorMessage);
    // return ResponseEntity.badRequest().body(response);
    // }

    // List<DanhMuc> list =
    // danhMucRepository.findByTuanAndSo(Integer.valueOf(danhMuc.getTuan()),
    // Integer.valueOf(danhMuc.getSo()));
    // if (list.size() > 0) {
    // ApiResponse<?> response = new ApiResponse<>(false, "Danh mục có tuần và số đã
    // tồn tại", null);
    // return ResponseEntity.ok().body(response);
    // }
    // DanhMuc dMuc = new DanhMuc();
    // dMuc.setTieude(danhMuc.getTieude());
    // dMuc.setMota(danhMuc.getMota());
    // dMuc.setNgaytao(LocalDate.now());
    // dMuc.setSo(Integer.valueOf(danhMuc.getSo()));
    // dMuc.setTuan(Integer.valueOf(danhMuc.getTuan()));
    // dMuc.setUrl(danhMuc.getUrl());
    // dMuc.setStatus(0);
    // danhMucService.saveDanhMuc(dMuc);

    // ApiResponse<?> response = new ApiResponse<>(true, "Create danh muc
    // successful", null);
    // return ResponseEntity.ok().body(response);
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ApiResponse<>(false, "Số và tuần không hợp lệ", null));
    // }
    // }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDanhMuc(@PathVariable("id") Long id) {
        try {
            DanhMuc danhMuc = danhMucService.getDanhMucById(id);
            if (danhMuc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
            }
            danhMucService.deleteDanhMuc(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(true, "Delete danh muc successful", "Đã xóa danh mục với id = " + id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi xóa danh mục.", null));
        }
    }

    @GetMapping("/findbyID/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> findById(@PathVariable("id") Long id) {
        try {
            DanhMuc dm = danhMucService.getDanhMucById(id);
            if (dm == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
            }
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Find danh muc by ID successful", dm));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi tìm kiếm danh mục.", null));
        }
    }

    @PostMapping("/add/baibao/danhmuc")
    public ResponseEntity<?> addBaiBaoToDanhMuc(@RequestBody(required = false) DTOAddBBDM entity) {
        // TODO: process POST request

        try {
            if (entity.getToken() == null || entity.getBaibaoId() == null || entity.getDanhmucId() == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi trống", null);
                return ResponseEntity.badRequest().body(response);
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if (tk == null) {
                ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getEDITORRole())) {
                ApiResponse<?> response = new ApiResponse<>(false, "Không được phép truy cập", "Yêu cầu EDITTOR");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token", null);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Baibao bb = baiBaoRepository.findById(Integer.valueOf(entity.getBaibaoId())).orElse(null);
            DanhMuc dm = danhMucRepository.findById(Long.valueOf(entity.getDanhmucId())).orElse(null);
            if (bb == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Không tìm thấy bb", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (dm == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Không tìm thấy dm", null);
                return ResponseEntity.badRequest().body(response);
            }

            Danhmucbaibao dmbb = new Danhmucbaibao();
            bb.setStatus(7);
            bb.setNgaydang(LocalDate.now());
            dmbb.setBaibao(bb);
            dmbb.setDanhmuc(dm);
            Danhmucbaibao sdmbb = danhMucBaiBaoRepository.save(dmbb);

            ApiResponse<?> response = new ApiResponse<>(true, "Thêm bb vào dm thành công", sdmbb);
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<?> response = new ApiResponse<>(false, "Lỗi khi thêm bb vào dm", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        // return entity;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> updateDanhMuc(@PathVariable("id") Long id,
            @RequestBody DanhMuc newDanhMuc) {
        try {
            DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(id, newDanhMuc);
            if (updatedDanhMuc != null) {
                return ResponseEntity.ok().body(new ApiResponse<>(true, "Update danh muc successful", updatedDanhMuc));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh muc not found", null));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi cập nhật danh mục.", null));
        }
    }

    // tìm baibao where danhmucid = {danhmucid}
    @GetMapping("/{danhmucId}/listBb")
    public ResponseEntity<ApiResponse<?>> getBaibaosByDanhMuc(@PathVariable Long danhmucId) {
        try {
            // tim ban ghi danh muc
            DanhMuc dmBaibao = danhMucRepository.findById(danhmucId).orElse(null);
            if (dmBaibao == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
            }
            List<Danhmucbaibao> listDm = dmBaibao.getDanhmucbaibaos();
            List<Baibao> baibaos = new ArrayList<>();

            for (Danhmucbaibao danhmucbaibao : listDm) {
                try {

                    Baibao baibao = danhmucbaibao.getBaibao();
                    // kiemtra taikhoanid co ton tai
                    if (baibao.getTaikhoan() == null
                            || !taiKhoanRepository.existsById(baibao.getTaikhoan().getTaikhoan_id())) {
                        System.out.println(
                                "<<< taikhoanid khong ton tai : " + baibao.getTaikhoan().getTaikhoan_id() + " >>>");
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                baibaos.add(danhmucbaibao.getBaibao());
            }
            List<Baibao> baibaoList = new ArrayList<>();
            for (Baibao baibao : baibaos) {
                Baibao bb = new Baibao();
                bb.setId(baibao.getId());
                bb.setStatus(baibao.getStatus());
                bb.setTaikhoan(baibao.getTaikhoan());
                bb.setNoidung(baibao.getNoidung());
                bb.setNgaydang(baibao.getNgaydang());
                bb.setTieude(baibao.getTieude());
                bb.setUrl(baibao.getUrl());
                baibaoList.add(bb);
            }
            if (baibaoList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Không có bài báo hợp lệ trong danh mục này.", null));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("baibaoList", baibaoList);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(true, "Danh sách bài báo", map));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi xử lý yêu cầu.", null));
        }
    }

}
