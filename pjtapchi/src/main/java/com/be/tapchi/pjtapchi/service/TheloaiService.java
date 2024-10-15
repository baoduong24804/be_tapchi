package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Theloai> getTheloaiById(Long id) {
        return theloaiRepository.findById(id);
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
    public void deleteTheloai(Long id) {
        theloaiRepository.deleteById(id);
    }
}
