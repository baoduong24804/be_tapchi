package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dịch vụ cho thực thể Theloai.
 */
@Service
public class TheloaiService {

    @Autowired
    private TheloaiRepository theloaiRepository;

    /**
     * Lấy tất cả các thể loại.
     *
     * @return Danh sách các thể loại.
     */
    public List<Theloai> getAllTheloais() {
        return theloaiRepository.findAll();
    }

    /**
     * Lấy thể loại theo ID.
     *
     * @param id ID của thể loại.
     * @return Thể loại tương ứng với ID.
     */
    public Theloai getTheloaiById(Integer id) {
        return theloaiRepository.findTheloaiById(id);
    }

    /**
     * Lưu thể loại.
     *
     * @param theloai Thể loại cần lưu.
     * @return Thể loại đã được lưu.
     */
    public Theloai saveTheloai(Theloai theloai) {
        return theloaiRepository.save(theloai);
    }

    /**
     * Xóa thể loại theo ID.
     *
     * @param id ID của thể loại cần xóa.
     */
    public void deleteTheloai(Integer id) {
        theloaiRepository.deleteById(id);
    }

    /**
     * Xóa thể loại theo tên thể loại.
     *
     * @param tentheloai Tên thể loại cần xóa.
     */

    public void deleteTheloaibyName(String tentheloai) {
        theloaiRepository.deleteTheloaiByTenloai(tentheloai);
    }
}
