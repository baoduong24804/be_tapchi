package com.be.tapchi.pjtapchi.controller.baibao.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOBaiBao {
    private String baibaoId;
    @NotBlank(message = "Thể loại trống")
    private String theloaiId;
    @NotBlank(message = "Tiêu đề trống")
    private String tieude;
    @NotBlank(message = "Nội dung trống")
    private String noidung;
    private String tukhoa;
    @NotBlank(message = "File trống")
    private String file;
    @NotBlank(message = "Ảnh trống")
    private String url;

    //@NotBlank(message = "Token trống")
    private String token;
}
