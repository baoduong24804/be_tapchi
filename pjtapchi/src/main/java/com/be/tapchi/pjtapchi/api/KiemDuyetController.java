package com.be.tapchi.pjtapchi.api;

import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.service.KiemduyetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kiemduyet")
public class KiemDuyetController {

    @Autowired
    private KiemduyetService kiemduyetService;

    @GetMapping
    public List<Kiemduyet> getAllKiemduyets() {
        return kiemduyetService.getAllKiemduyets();
    }

    // Other CRUD methods can be added here
}
