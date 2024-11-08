package com.be.tapchi.pjtapchi.controller.theloai;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.service.TheloaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/theloai")
public class theloaiController {

    @Autowired
    TheloaiService TheloaiService;

    @PostMapping("/create/{tentheloai}")
    public ResponseEntity<ApiResponse<Theloai>> createTheloai(@PathVariable("tentheloai") String tentheloai) {
        Theloai theloai = new Theloai();
        theloai.setTenloai(tentheloai);

        Theloai savedTheloai = TheloaiService.saveTheloai(theloai);
        ApiResponse<Theloai> response = new ApiResponse<>(true, "Save the loai successful", savedTheloai);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTheloai(@PathVariable("id") Integer id) {
        TheloaiService.deleteTheloai(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete the loai successful", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Iterable<Theloai>>> getAllTheloais() {
        Iterable<Theloai> list = TheloaiService.getAllTheloais();
        ApiResponse<Iterable<Theloai>> response = new ApiResponse<>(true, "Fetch the loai successful", list);
        return ResponseEntity.ok().body(response);
    }
}
