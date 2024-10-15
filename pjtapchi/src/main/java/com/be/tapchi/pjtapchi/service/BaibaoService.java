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

   
}