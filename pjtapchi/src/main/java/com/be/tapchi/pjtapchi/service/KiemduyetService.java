package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.repository.KiemduyetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Dịch vụ KiemduyetService cung cấp các phương thức để thao tác với đối tượng
 * Kiemduyet.
 */
@Service
public class KiemduyetService {

    @Autowired
    private KiemduyetRepository kiemduyetRepository;

    /**
     * Xóa một đối tượng Kiemduyet theo ID.
     *
     * @param id ID của đối tượng Kiemduyet cần xóa
     */
    public void deleteKiemduyet(Long id) {
        kiemduyetRepository.deleteById(id);
    }

    /**
     * Lấy danh sách tất cả các đối tượng Kiemduyet.
     *
     * @return danh sách các đối tượng Kiemduyet
     */
    public Page<Kiemduyet> getAllKiemduyets(Pageable pageable) {
        return kiemduyetRepository.findAll(pageable);
    }


    /**
     * Lấy một đối tượng Kiemduyet theo ID.
     *
     * @param id ID của đối tượng Kiemduyet cần lấy
     * @return đối tượng Kiemduyet nếu tìm thấy, hoặc Optional rỗng nếu không tìm
     * thấy
     */
    public Kiemduyet getKiemduyetById(Integer id) {
        return kiemduyetRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("Invalid KiemDuyet ID"));
    }

    /**
     * Lưu một đối tượng Kiemduyet.
     *
     * @param kiemduyet đối tượng Kiemduyet cần lưu
     * @return đối tượng Kiemduyet đã được lưu
     */
    public Kiemduyet saveKiemduyet(Kiemduyet kiemduyet) {
        return kiemduyetRepository.save(kiemduyet);
    }

}
