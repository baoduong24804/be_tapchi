package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.repository.QuangCaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuangCaoService {

    @Autowired
    private QuangCaoRepository quangCaoRepository;

    public List<QuangCao> getAllQuangCaos() {
        return quangCaoRepository.findAll();
    }

    public Optional<QuangCao> getQuangCaoById(Long id) {
        return quangCaoRepository.findById(id);
    }

    public QuangCao saveQuangCao(QuangCao quangCao) {
        return quangCaoRepository.save(quangCao);
    }

    public void deleteQuangCao(Long id) {
        quangCaoRepository.deleteById(id);
    }
}
