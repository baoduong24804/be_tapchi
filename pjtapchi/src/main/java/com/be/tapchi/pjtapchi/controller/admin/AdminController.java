package com.be.tapchi.pjtapchi.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOAdmin;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOBaibao;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOGoiQC;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOQuangCao;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTORoles;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOTaikhoan;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOTaikhoanCQ;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOTheloai;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOUser;
import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.baibao.model.KiemduyetAT;
import com.be.tapchi.pjtapchi.controller.baibao.model.KiemduyetED;
import com.be.tapchi.pjtapchi.controller.baibao.model.TaikhoanED;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.model.Vaitro;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.HopDongRepository;
import com.be.tapchi.pjtapchi.repository.QuangCaoRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    @Autowired
    private QuangCaoRepository quangCaoRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private HopDongRepository hopDongRepository;

    @PostMapping("get/user")
    public ResponseEntity<?> postMethodName(@RequestBody(required = false) DTOAdmin entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Taikhoan> list = taiKhoanRepository.findAll(pageable);

        Map<Object, Object> data = new HashMap<>();
        if (list.getContent().size() <= 0) {
            data.put("data", null);
            ApiResponse<?> response = new ApiResponse<>(true, "Ko co du lieu", null);
            return ResponseEntity.ok().body(response);
        }

        List<DTOUser> users = new ArrayList<>();
        for (Taikhoan taikhoan : list.getContent()) {
            DTOUser u = new DTOUser();
            u.setTaikhoanId(taikhoan.getTaikhoan_id() + "");
            u.setEmail(taikhoan.getEmail());
            u.setHovaten(taikhoan.getHovaten());
            if (taikhoan.getGoogleId() == null) {
                u.setGoogle(false);
            } else {
                if (taikhoan.getGoogleId().isEmpty()) {
                    u.setGoogle(false);
                }
                u.setGoogle(true);
            }

            u.setSdt(taikhoan.getSdt() + "");
            u.setStatus(taikhoan.getStatus() + "");
            u.setUrl(taikhoan.getUrl());
            u.setUsername(taikhoan.getUsername());
            Set<DTORoles> roles = new HashSet<>();
            for (Vaitro vt : taikhoan.getVaitro()) {
                DTORoles r = new DTORoles();
                r.setVaitroId(vt.getVaitroId() + "");
                r.setRoles(vt.getTenrole());
                roles.add(r);
            }
            u.setRoles(roles);

            users.add(u);
        }

        data.put("data", users);

        Map<String, Object> phantrang = new HashMap<>();
        phantrang.put("totalPage", String.valueOf(list.getTotalPages()));
        phantrang.put("pageNumber", String.valueOf(list.getNumber()));
        phantrang.put("pageSize", String.valueOf(list.getSize()));
        phantrang.put("totalElements", String.valueOf(list.getTotalElements()));
        data.put("phantrang", phantrang);

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("update/user/status")
    public ResponseEntity<?> postMethodName(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getStatus() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getStatus().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getTaikhoanId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getTaikhoanId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int status = Integer.valueOf((entity.getStatus() + "").trim());
        if (status < -1 || status > 1) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status khong hop le",
                    "-1: khoa tk, 0: chua kich hoat, 1: hoat dong");
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        if (svaitro != null) {
            for (Vaitro vaitro : svaitro) {
                if (vaitro.getTenrole().equalsIgnoreCase(ManageRoles.getADMINRole()) || vaitro.getVaitroId() == 6) {
                    ApiResponse<?> response = new ApiResponse<>(false, "Ko duoc chinh admin", "");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }

        if (tk.getStatus() == status) {
            ApiResponse<?> response = new ApiResponse<>(true, "Status ko doi :>>", status);
            return ResponseEntity.ok().body(response);
        } else {
            tk.setStatus(status);
            taiKhoanRepository.save(tk);
            ApiResponse<?> response = new ApiResponse<>(true, "Cap nhat thanh cong", status);
            return ResponseEntity.ok().body(response);
        }

    }

    @PostMapping("add/user/role")
    public ResponseEntity<?> updateRole(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getRole() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getRole().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getTaikhoanId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getTaikhoanId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int role = Integer.valueOf((entity.getRole() + "").trim());
        if (role < 1 || role > 6) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role khong hop le",
                    "1: customer, 2:author, 3:censor,4:partner,5:editor,6:admin");
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        for (Vaitro vt : tk.getVaitro()) {
            if (vt.getVaitroId() == Integer.valueOf(entity.getRole())) {
                ApiResponse<?> response = new ApiResponse<>(true, "Role ko doi vi da co role nay :>>", null);
                return ResponseEntity.ok().body(response);
            }
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        Vaitro addvt = vaiTroRepository.findById(Integer.valueOf(entity.getRole())).orElse(null);
        if (addvt == null) {
            ApiResponse<?> response = new ApiResponse<>(true, "Co loi khi them vai tro", null);
            return ResponseEntity.badRequest().body(response);
        }
        svaitro.add(addvt);
        tk.setVaitro(svaitro);
        taiKhoanRepository.save(tk);
        ApiResponse<?> response = new ApiResponse<>(true, "Them vai tro thanh cong", null);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("remove/user/role")
    public ResponseEntity<?> remove(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getRole() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getRole().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getTaikhoanId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getTaikhoanId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int role = Integer.valueOf((entity.getRole() + "").trim());
        if (role < 1 || role > 6) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role khong hop le",
                    "1: customer, 2:author, 3:censor,4:partner,5:editor,6:admin");
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        Vaitro addvt = vaiTroRepository.findById(Integer.valueOf(entity.getRole())).orElse(null);

        if (addvt == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Co loi khi xoa vai tro", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (addvt.getTenrole().equalsIgnoreCase(ManageRoles.getADMINRole()) || addvt.getVaitroId() == 6) {
            ApiResponse<?> response = new ApiResponse<>(false, "Ko duoc xoa quyen admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        Set<Vaitro> newvaitro = new HashSet<>();

        for (Vaitro vaitro : svaitro) {
            if (vaitro.getTenrole().equalsIgnoreCase(addvt.getTenrole())
                    || vaitro.getVaitroId() == addvt.getVaitroId()) {
                continue;
            }
            newvaitro.add(vaitro);
        }

        tk.setVaitro(newvaitro);
        taiKhoanRepository.save(tk);
        ApiResponse<?> response = new ApiResponse<>(true, "Xoa vai tro thanh cong", null);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("get/role")
    public ResponseEntity<?> getRole(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        List<Vaitro> list = vaiTroRepository.findAll();
        if (list.size() <= 0) {

            ApiResponse<?> response = new ApiResponse<>(false, "Ko co role nao", null);
            return ResponseEntity.badRequest().body(response);

        }

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", list);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("get/thongke")
    public ResponseEntity<?> getThongKe(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        long slbaibao = baiBaoRepository.count();
        long sltaikhoan = taiKhoanRepository.count();
        long slquangcao = quangCaoRepository.count();
        double doanhthu = 0.0;

        Map<Object, Object> data = new HashMap<>();

        data.put("slbaibao", slbaibao);
        data.put("sltaikhoan", sltaikhoan);
        data.put("slquangcao", slquangcao);
        data.put("doanhthu", doanhthu);

        Map<Object, Object> result = new HashMap<>();

        result.put("data", data);
        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("get/baibao")
    public ResponseEntity<?> getBaibao(@RequestBody(required = false) DTOAdmin entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> list = baiBaoRepository.findAll(pageable);

        Map<Object, Object> data = new HashMap<>();
        if (list.getContent().size() <= 0) {
            data.put("data", null);
            ApiResponse<?> response = new ApiResponse<>(true, "Ko co du lieu", null);
            return ResponseEntity.ok().body(response);
        }

        List<DTOBaibao> baibaos = new ArrayList<>();
        for (Baibao baibao : list.getContent()) {
            if (baibao.getStatus() != 0 && baibao.getStatus() != 7) {
                continue;
            }
            DTOBaibao u = new DTOBaibao();

            DTOTaikhoan tk = new DTOTaikhoan();
            tk.setTaikhoanId(baibao.getTaikhoan().getTaikhoan_id() + "");
            tk.setHovaten(baibao.getTaikhoan().getHovaten());

            u.setTaikhoan(tk);

            DTOTheloai tl = new DTOTheloai();

            tl.setTheloaiId(baibao.getTheloai().getId() + "");
            tl.setTentheloai(baibao.getTheloai().getTenloai());

            u.setTheloai(tl);

            u.setId(baibao.getId() + "");
            u.setTieude(baibao.getTieude());
            u.setNoidung(baibao.getNoidung());
            u.setNgaytao(baibao.getNgaytao() + "");
            u.setNgaydang(baibao.getNgaydang() + "");
            u.setStatus(baibao.getStatus() + "");
            u.setUrl(baibao.getUrl());
            u.setFile(baibao.getFile());

            int slike = 0;
            for (Thich thich : baibao.getThichs()) {
                if (thich.getStatus() == 1) {
                    slike++;
                }
            }
            u.setLuotthich(slike + "");

            int sxem = 0;
            if (baibao.getThichs() != null) {
                if (baibao.getThichs().size() > 0) {
                    sxem = baibao.getThichs().size();
                }
            }

            u.setLuotxem(sxem + "");

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

            u.setKiemduyet(lKiemduyets);

            baibaos.add(u);
        }

        data.put("data", baibaos);

        Map<String, Object> phantrang = new HashMap<>();
        phantrang.put("totalPage", String.valueOf(list.getTotalPages()));
        phantrang.put("pageNumber", String.valueOf(list.getNumber()));
        phantrang.put("pageSize", String.valueOf(list.getSize()));
        phantrang.put("totalElements", String.valueOf(list.getTotalElements()));
        data.put("phantrang", phantrang);

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("update/baibao/status")
    public ResponseEntity<?> updateBaibao(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getStatus() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getStatus().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getBaibaoId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "BaibaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getBaibaoId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "BaibaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int status = Integer.valueOf((entity.getStatus() + "").trim());
        if (status < -1 || status > 7) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status khong hop le",
                    null);
            return ResponseEntity.badRequest().body(response);
        }

        Baibao bb = null;
        bb = baiBaoRepository.findById(Integer.valueOf((entity.getBaibaoId() + "").trim())).orElse(null);
        if (bb == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        if (bb.getStatus() == status) {
            ApiResponse<?> response = new ApiResponse<>(true, "Status ko doi :>>", status);
            return ResponseEntity.ok().body(response);
        } else {
            bb.setStatus(status);
            baiBaoRepository.save(bb);
            ApiResponse<?> response = new ApiResponse<>(true, "Cap nhat thanh cong", status);
            return ResponseEntity.ok().body(response);
        }

    }

    public static String formatToVND(double amount) {
        // Tạo định dạng tiền tệ Việt Nam
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Trả về chuỗi định dạng số tiền
        return vndFormat.format(amount);
    }

    public static String formatDateTime(String inputDateTime) {
        // Định dạng đầu vào (phù hợp với chuỗi ban đầu)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        // Định dạng đầu ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Chuyển chuỗi đầu vào thành LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);

        // Định dạng lại và trả kết quả
        return dateTime.format(outputFormatter);
    }

    @PostMapping("get/quangcao")
    public ResponseEntity<?> getquanfcao(@RequestBody(required = false) DTOAdmin entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);

        Page<HopDong> list = hopDongRepository.findByStatus(1, pageable);
        Map<Object, Object> data = new HashMap<>();
        if (list.getContent().size() <= 0) {
            data.put("data", null);
            ApiResponse<?> response = new ApiResponse<>(true, "Ko co du lieu", null);
            return ResponseEntity.ok().body(response);
        }
        List<DTOQuangCao> listqc = new ArrayList<>();
        for (HopDong hopDong : list.getContent()) {
            // System.out.println(hopDong.getQuangCao()+"awdo;jawdwajdojo");
            if (hopDong.getQuangCao() == null) {
                continue;
            }
            for (QuangCao qcc : hopDong.getQuangCao()) {
                DTOQuangCao item = new DTOQuangCao();
                item.setQuangcaoId(qcc.getQuangcao_id() + "");
                item.setTieude(qcc.getTieude());
                item.setStatus(qcc.getStatus() + "");
                item.setUrl(qcc.getUrl());
                item.setLink(qcc.getLink());
                item.setNgaybd(formatDateTime(qcc.getHopDong().getNgayBatDauHD() + ""));
                item.setNgaykt(formatDateTime(qcc.getHopDong().getNgayKetThucHD() + ""));
                DTOGoiQC dtoGoiQC = new DTOGoiQC();
                dtoGoiQC.setBgqc_id(qcc.getBgqc().getBanggiaqc_id() + "");
                dtoGoiQC.setTengoi(qcc.getBgqc().getTengoi());
                dtoGoiQC.setSongay(qcc.getBgqc().getSongay() + "");
                dtoGoiQC.setGiagoi(formatToVND(qcc.getBgqc().getGiatien()));
                item.setBgqc(dtoGoiQC);
                DTOTaikhoan dtoTaikhoan = new DTOTaikhoan();
                dtoTaikhoan.setTaikhoanId(qcc.getTaikhoan().getTaikhoan_id() + "");
                dtoTaikhoan.setHovaten(qcc.getTaikhoan().getHovaten());
                item.setTaikhoan(dtoTaikhoan);
                listqc.add(item);
            }

        }

        data.put("data", listqc);

        Map<String, Object> phantrang = new HashMap<>();
        phantrang.put("totalPage", String.valueOf(list.getTotalPages()));
        phantrang.put("pageNumber", String.valueOf(list.getNumber()));
        phantrang.put("pageSize", String.valueOf(list.getSize()));
        phantrang.put("totalElements", String.valueOf(list.getTotalElements()));
        data.put("phantrang", phantrang);

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", data);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("update/quangcao/status")
    public ResponseEntity<?> updateqc(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getStatus() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getStatus().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getQuangcaoId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getQuangcaoId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int status = Integer.valueOf((entity.getStatus() + "").trim());
        if (status < 0 || status > 3) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status khong hop le",
                    "0 la het han, 1 cho duyet, 2 admin da duyet");
            return ResponseEntity.badRequest().body(response);
        }

        QuangCao qc = quangCaoRepository.findById(Long.valueOf(entity.getQuangcaoId())).orElse(null);
        if (qc == null) {
            ApiResponse<?> response = new ApiResponse<>(true, "Ko tim thay qc", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (qc.getStatus() == status) {
            ApiResponse<?> response = new ApiResponse<>(true, "Cap nhat thanh cong :>>", null);
            return ResponseEntity.ok().body(response);
        }

        qc.setStatus(status);
        quangCaoRepository.save(qc);
        ApiResponse<?> response = new ApiResponse<>(true, "Cap nhat thanh cong", null);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("get/user/capquyen")
    public ResponseEntity<?> capquyen(@RequestBody(required = false) DTOAdmin entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Taikhoan> list = taiKhoanRepository.findByStatus(2, pageable);
        if (list == null) {
            ApiResponse<?> response = new ApiResponse<>(true, "Data null", null);
            return ResponseEntity.ok().body(response);
        }

        Map<Object, Object> data = new HashMap<>();
        if (list.getContent().size() <= 0) {
            data.put("data", null);
            ApiResponse<?> response = new ApiResponse<>(true, "Ko co du lieu", null);
            return ResponseEntity.ok().body(response);
        }

        List<DTOTaikhoanCQ> ldata = new ArrayList<>();

        for (Taikhoan taikhoan : list) {
            DTOTaikhoanCQ tkcq = new DTOTaikhoanCQ();
            tkcq.setTaikhoanId(taikhoan.getTaikhoan_id() + "");
            tkcq.setHovaten(taikhoan.getHovaten());
            tkcq.setEmail(taikhoan.getEmail());
            tkcq.setStatus(taikhoan.getStatus() + "");
            ldata.add(tkcq);
        }

        data.put("data", ldata);

        Map<String, Object> phantrang = new HashMap<>();
        phantrang.put("totalPage", String.valueOf(list.getTotalPages()));
        phantrang.put("pageNumber", String.valueOf(list.getNumber()));
        phantrang.put("pageSize", String.valueOf(list.getSize()));
        phantrang.put("totalElements", String.valueOf(list.getTotalElements()));
        data.put("phantrang", phantrang);

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", data);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("update/user/capquyen")
    public ResponseEntity<?> xacnhancapquyen(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getTaikhoanId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getTaikhoanId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTaikhoanId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getStatus() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getStatus().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int status = Integer.valueOf((entity.getStatus() + "").trim());
        if (status < 0 || status > 1) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status khong hop le",
                    "0: ko chap nhan, 1: chap nhan");
            return ResponseEntity.badRequest().body(response);
        }



        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        if (status == 0) {
            tk.setStatus(1);
            taiKhoanRepository.save(tk);
            ApiResponse<?> response = new ApiResponse<>(true, "Ko cho phep quyen", null);
            return ResponseEntity.ok().body(response);
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        Vaitro vt = vaiTroRepository.findBytenrole(ManageRoles.getAUTHORRole().toString());
        if (vt != null) {
            svaitro.add(vt);
        }
        tk.setStatus(1);
        tk.setVaitro(svaitro);



        taiKhoanRepository.save(tk);

        ApiResponse<?> response = new ApiResponse<>(true, "Cap quyen thanh cong", null);
        return ResponseEntity.ok().body(response);

    }

}
