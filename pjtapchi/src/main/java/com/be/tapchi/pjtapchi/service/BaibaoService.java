package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Dịch vụ Baibao để quản lý các thao tác liên quan đến Baibao.
 */
@Service
public class BaibaoService {
    @Autowired
    private BaiBaoRepository baiBaoRepository;

    /**
     * Lấy tất cả các Baibao.
     *
     * @return danh sách tất cả các Baibao
     */
    public List<Baibao> getAllBaibaos() {
        return baiBaoRepository.findAll();
    }

    /**
     * Lấy Baibao theo ID.
     *
     * @param id ID của Baibao
     * @return một Optional chứa Baibao nếu tìm thấy, ngược lại là Optional rỗng
     */
    public Optional<Baibao> getBaibaoById(Long id) {
        return baiBaoRepository.findById(id);
    }

    /**
     * Lưu một Baibao mới hoặc cập nhật Baibao hiện có.
     *
     * @param baibao đối tượng Baibao cần lưu
     * @return Baibao đã được lưu
     */
    public Baibao saveBaibao(Baibao baibao) {
        return baiBaoRepository.save(baibao);
    }


    /**
     * Xóa Baibao theo ID.
     *
     * @param id ID của Baibao cần xóa
     */
    public void deleteBaibao(Long id) {
        baiBaoRepository.deleteById(id);
    }

    /**
     * Lấy danh sách Baibao theo thể loại.
     *
     * @param TheLoai thể loại của Baibao
     * @return danh sách Baibao theo thể loại
     */
    public List<Baibao> getBaiBaoByTheLoai(String TheLoai) {
        return baiBaoRepository.findByTheLoai(TheLoai);
    }

    /**
     * Lấy danh sách Baibao theo tác giả.
     *
     * @param TacGia tác giả của Baibao
     * @return danh sách Baibao theo tác giả
     */
    public List<Baibao> getBaiBaoByTacGia(String TacGia) {
        return baiBaoRepository.findByTacGia(TacGia);
    }

    /**
     * Lấy danh sách Baibao theo tiêu đề.
     *
     * @param TieuDe tiêu đề của Baibao
     * @return danh sách Baibao theo tiêu đề
     */
    public List<Baibao> getBaiBaoByTieuDe(String TieuDe) {
        return baiBaoRepository.findByTieuDeContaining(TieuDe);
    }

    /**
     * Lấy danh sách Baibao theo trạng thái kiểm duyệt.
     *
     * @param KiemDuyet trạng thái kiểm duyệt của Baibao
     * @return danh sách Baibao theo trạng thái kiểm duyệt
     */
    public List<Baibao> getBaiBaoByKiemDuyet(Boolean KiemDuyet) {
        return baiBaoRepository.findByKiemDuyet(KiemDuyet);
    }

    /**
     * Lấy danh sách Baibao theo thể loại ID
     *
     * @param id của thể loại
     * @return danh sách Baibao theo thể loại ID
     */
    public List<Baibao> getBaiBaoByTheLoaiId(Long id) {
        return baiBaoRepository.findByTheLoaiID(id);
    }

    /**
     * Lấy danh sách Baibao theo tác giả id
     *
     * @param id của tác giả
     * @return danh sách Baibao theo tác giả id
     */
    public List<Baibao> getBaiBaoByTacGiaId(Long id) {
        return baiBaoRepository.findByTacGiaID(id);
    }

}