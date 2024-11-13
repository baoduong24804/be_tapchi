package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.repository.ThichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThichService {
    @Autowired
    private final ThichRepository thichRepository;

    public ThichService(ThichRepository thichRepository) {
        this.thichRepository = thichRepository;
    }

    public Page<Thich> getAllThichs(Pageable pageable) {
        return thichRepository.findAll(pageable);
    }

    public List<Thich> findAll() {
        return thichRepository.findAll();
    }

    public Optional<Thich> findById(Long id) {
        return thichRepository.findById(id);
    }

    public Thich save(Thich thich) {
        return thichRepository.save(thich);
    }

    public void deleteById(Long id) {
        thichRepository.deleteById(id);
    }
}
