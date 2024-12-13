package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BangGiaQCRepository extends JpaRepository<BangGiaQC, Long> {

    @Transactional
    @Query("SELECT b.songay FROM BangGiaQC b WHERE b.banggiaqc_id = :id")
    Integer findSoNgayByID(@Param("id") Long id);

    @Query("SELECT b FROM BangGiaQC b WHERE b.banggiaqc_id = :id")
    BangGiaQC findBybanggiaqc_id(@Param("id") Long id);


}