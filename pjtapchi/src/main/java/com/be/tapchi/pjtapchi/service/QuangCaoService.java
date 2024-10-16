// package com.be.tapchi.pjtapchi.service;

// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.be.tapchi.pjtapchi.model.QuangCao;
// import com.be.tapchi.pjtapchi.repository.QuangCaoRepository;

// @Service
// @Transactional
// public class QuangCaoService {

// @Autowired
// private QuangCaoRepository quangCaoRepository;

// public List<QuangCao> getAllQuangCaos() {
// return quangCaoRepository.findAll();
// }

// public QuangCao getQuangCaoById(Long id) {
// return quangCaoRepository.findById(id).orElse(null);
// }

// public QuangCao saveQuangCao(QuangCao quangCao) {
// return quangCaoRepository.save(quangCao);
// }

// public void deleteQuangCao(Long id) {
// quangCaoRepository.deleteById(id);
// }
// }
