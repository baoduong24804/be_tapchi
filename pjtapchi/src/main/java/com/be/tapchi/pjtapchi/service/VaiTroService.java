package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.model.Vaitro;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;

@Service
public class VaiTroService {
    @Autowired
    private final VaiTroRepository vaiTroRepository;

    
    public VaiTroService(VaiTroRepository vaiTroRepository) {
        this.vaiTroRepository = vaiTroRepository;
    }

    public List<Vaitro> findAll() {
        return vaiTroRepository.findAll();
    }

    public Optional<Vaitro> findById(Long id) {
        return vaiTroRepository.findById(id);
    }

    public Vaitro save(Vaitro vaiTro) {
        return vaiTroRepository.save(vaiTro);
    }

    public void deleteById(Long id) {
        vaiTroRepository.deleteById(id);
    }

    public Vaitro findByName(String name){
        return vaiTroRepository.findBytenrole(name);
    }
}
