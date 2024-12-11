package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BangGiaQCRepository extends JpaRepository<BangGiaQC, Long> {

    @Query("SELECT b.soNgay FROM BangGiaQC b WHERE b.bangGiaQCId = :id")
    Integer findSoNgayByID(@Param("id") Long id);

    BangGiaQC findBangGiaQCByBangGiaQCId
            (Long id);

}
