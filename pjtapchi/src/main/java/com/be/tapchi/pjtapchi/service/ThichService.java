package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.repository.ThichRepository;

@Service
public class ThichService {
    @Autowired
    private final ThichRepository thichRepository;

    
    public ThichService(ThichRepository thichRepository) {
        this.thichRepository = thichRepository;
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
