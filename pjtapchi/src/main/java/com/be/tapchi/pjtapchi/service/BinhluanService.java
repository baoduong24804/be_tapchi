package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.repository.BinhluanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dịch vụ cho các thao tác liên quan đến Binhluan.
 */
@Service
public class BinhluanService {
    @Autowired
    private BinhluanRepository binhluanRepository;

    /**
     * Lấy tất cả các Binhluan.
     *
     * @return danh sách tất cả các Binhluan
     */
    public List<Binhluan> getAllBinhluans() {
        return binhluanRepository.findAll();
    }


    /**
     * Lưu một Binhluan.
     *
     * @param binhluan đối tượng Binhluan cần lưu
     * @return đối tượng Binhluan đã được lưu
     */
    public Binhluan saveBinhluan(Binhluan binhluan) {
        return binhluanRepository.save(binhluan);
    }

    /**
     * Xóa một Binhluan theo id.
     *
     * @param id id của Binhluan cần xóa
     */
    public void deleteBinhluan(Long id) {
        binhluanRepository.deleteById(id);
    }
}