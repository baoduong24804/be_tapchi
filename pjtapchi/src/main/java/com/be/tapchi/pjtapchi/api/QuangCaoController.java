// package com.be.tapchi.pjtapchi.api;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.be.tapchi.pjtapchi.model.QuangCao;
// import com.be.tapchi.pjtapchi.service.QuangCaoService;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/quangcao")
// public class QuangCaoController {

// @Autowired
// private QuangCaoService quangCaoService;

// @GetMapping
// public List<QuangCao> getAllQuangCao() {
// return quangCaoService.getAllQuangCao();
// }

// @GetMapping("/{id}")
// public Optional<QuangCao> getQuangCaoById(@PathVariable Long id) {
// return quangCaoService.getQuangCaoById(id);
// }

// @PostMapping
// public QuangCao createQuangCao(@RequestBody QuangCao quangCao) {
// return quangCaoService.saveQuangCao(quangCao);
// }

// @DeleteMapping("/{id}")
// public void deleteQuangCao(@PathVariable Long id) {
// quangCaoService.deleteQuangCao(id);
// }
// }
