// package com.be.tapchi.pjtapchi.controller.fileupload;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.Resource;
// import org.springframework.core.io.UrlResource;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import com.be.tapchi.pjtapchi.fileservice.FileStorageService;
// import com.be.tapchi.pjtapchi.jwt.JwtUtil;
// import com.be.tapchi.pjtapchi.userRole.ManageRoles;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.List;
// import java.util.concurrent.CompletableFuture;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// @RestController
// @RequestMapping("/api/upload")
// @CrossOrigin(origins = "*") // Cho phép tất cả các địa chỉ web
// public class FileController {

//     @Autowired
//     private JwtUtil jwtUtil;

//     private final FileStorageService fileStorageService;

//     public FileController(FileStorageService fileStorageService) {
//         this.fileStorageService = fileStorageService;
//     }

//     // @PostMapping("/upload")
//     // public ResponseEntity<String> uploadFile(@RequestParam("files")
//     // List<MultipartFile> file) {
//     // StringBuilder s = new StringBuilder();
//     // if(file.size()<=0){
//     // return ResponseEntity.badRequest().body(null);
//     // }
//     // s.append("["+fileStorageService.getFileStorageLocation()+"]");
//     // for (MultipartFile multipartFile : file) {

//     // String fileName = fileStorageService.storeFile(multipartFile);
//     // s.append(fileName).append(",");
//     // }

//     // return ResponseEntity.ok("File uploaded successfully: " + s);
//     // }

//     @PostMapping("/file")
//     public ResponseEntity<String> uploadFile(@RequestParam("files") List<MultipartFile> files) {
//         if (files.isEmpty()) {
//             return ResponseEntity.badRequest().body("No files provided");
//         }

//         String location = "[" + fileStorageService.getFileStorageLocation() + "]";
//         List<CompletableFuture<String>> futures = files.stream()
//                 .map(fileStorageService::storeFile)
//                 .collect(Collectors.toList());

//         // Chờ tất cả các tác vụ hoàn thành
//         List<String> fileNames = futures.stream()
//                 .map(CompletableFuture::join)
//                 .collect(Collectors.toList());

//         return ResponseEntity.ok("File uploaded successfully: " + location + String.join(",", fileNames));
//     }

//     @DeleteMapping("/delete/file")
//     public ResponseEntity<String> deleteFile(@RequestParam(required = false) String fileName, @RequestParam(required = false) String token) {
//         try {
//             if (jwtUtil.checkRolesFromToken(token, ManageRoles.getADMINRole())) {
//                 fileStorageService.deleteFile(fileName);
//                 return ResponseEntity.ok("File deleted successfully: " + fileName);
//             }
//             return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED.toString());
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST.toString());
//         }
//     }

//     @DeleteMapping("/delete-all/file")
//     public ResponseEntity<String> deleteAllFiles(@RequestParam(required = false) String token) {
//         try {
//             if (jwtUtil.checkRolesFromToken(token, ManageRoles.getADMINRole())) {

//                 fileStorageService.deleteAllFiles();
//                 return ResponseEntity.ok("All files deleted successfully.");
//             }
//             return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED.toString());
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST.toString());
//         }
//     }

//     @GetMapping("/download/file/{fileName}")
//     public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
//         Path filePath = fileStorageService.loadFile(fileName);
//         Resource resource = new UrlResource(filePath.toUri());

//         String contentType = Files.probeContentType(filePath);
//         if (contentType == null) {
//             contentType = "application/octet-stream";
//         }

//         return ResponseEntity.ok()
//                 .contentType(MediaType.parseMediaType(contentType))
//                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                 .body(resource);
//     }

//     @GetMapping("/view/file/{fileName}")
//     public ResponseEntity<Resource> viewImage(@PathVariable String fileName) throws IOException {
//         Path filePath = fileStorageService.loadFile(fileName);
//         Resource resource = new UrlResource(filePath.toUri());

//         String contentType = Files.probeContentType(filePath);
//         System.out.println(contentType);
//         if (contentType == null || !contentType.startsWith("image")) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }

//         return ResponseEntity.ok()
//                 .contentType(MediaType.parseMediaType(contentType))
//                 .body(resource);
//     }

//     @GetMapping("/view/file/all")
//     public ResponseEntity<List<String>> getAllFiles() {
//         try (Stream<Path> paths = Files.list(fileStorageService.getFileStorageLocation())) {
//             List<String> fileNames = paths
//                     .filter(Files::isRegularFile)
//                     .map(Path::getFileName)
//                     .map(Path::toString)
//                     .collect(Collectors.toList());

//             return ResponseEntity.ok().body(fileNames);
//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//         }
//     }
// }
