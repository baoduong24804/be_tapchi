// package com.be.tapchi.pjtapchi.fileservice;

// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.nio.file.DirectoryStream;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.util.UUID;
// import java.util.concurrent.CompletableFuture;

// @Service
// public class FileStorageService {

//     private final Path fileStorageLocation;

//     public Path getFileStorageLocation() {
//         return this.fileStorageLocation;
//     }

//     public FileStorageService(FileStorageProperties fileStorageProperties) {
//         this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//                 .toAbsolutePath().normalize();
//         try {
//             Files.createDirectories(this.fileStorageLocation);
//         } catch (Exception ex) {
//             throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
//         }
//     }

//     // @Async
//     // public String storeFile(MultipartFile file) {
//     // try {

//     // String UUID = uniqueIDCreate(file.getOriginalFilename());
//     // String UUIDImageName = UUID + file.getOriginalFilename();
//     // Path targetLocation = this.fileStorageLocation.resolve(UUIDImageName);
//     // System.out.println(targetLocation);
//     // Files.copy(file.getInputStream(), targetLocation,
//     // StandardCopyOption.REPLACE_EXISTING);
//     // return targetLocation.getFileName().toString();
//     // } catch (IOException ex) {
//     // throw new RuntimeException("Could not store file " +
//     // file.getOriginalFilename() + ". Please try again!",
//     // ex);
//     // }
//     // }
//     @Async
//     public CompletableFuture<String> storeFile(MultipartFile file) {
//         try {
//             // Tạo tên tệp UUID và đường dẫn
            
//             String fileName = file.getOriginalFilename();
//             if(fileName == null|| fileName.isEmpty() || fileName.isBlank()){
//                 return CompletableFuture.completedFuture(null);
//             }
//             int lastDotIndex = fileName.lastIndexOf('.');
//             String lastFileFormat = "";
//             if (lastDotIndex != -1) {
//                 // Cắt chuỗi từ vị trí cuối cùng của dấu '.'
//                 lastFileFormat = fileName.substring(lastDotIndex);
                
//             } else {
//                 return CompletableFuture.completedFuture(null);
//             }
           
//             String UUID = uniqueIDCreate(lastFileFormat);
//             String UUIDImageName = UUID + lastFileFormat;
//             Path targetLocation = this.fileStorageLocation.resolve(UUIDImageName);
    
//             // Lưu tệp vào đích
//             Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    
//             // Trả về tên tệp sau khi lưu thành công
//             return CompletableFuture.completedFuture(UUIDImageName);
//         } catch (IOException ex) {
//             // Xử lý ngoại lệ khi có lỗi
//             ex.printStackTrace();
//             return CompletableFuture.completedFuture(null); // hoặc trả về lỗi tùy chỉnh
//         }
//     }

//     @Async
//     public void deleteFile(String fileName) {
//         try {
//             Path filePath = fileStorageLocation.resolve(fileName);
//             Files.deleteIfExists(filePath); // Xóa file nếu tồn tại
//         } catch (IOException e) {
//             e.printStackTrace(); // Log lỗi để theo dõi
//         }
//     }
    

//     public void deleteAllFiles() throws IOException {
//         try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fileStorageLocation)) {
//             for (Path path : directoryStream) {
//                 Files.delete(path); // Xóa từng file trong thư mục
//             }
//         }
//     }

//     public String uniqueIDCreate(String fileFormat) {
//         // Tạo ID duy nhất cho file
//         String uniqueID = UUID.randomUUID().toString();
//         for (int i = 0; i < 99; i++) {
//             if (checkExistFile(uniqueID + fileFormat)) {
//                 // neu ton tai file
//                 uniqueID = UUID.randomUUID().toString();
//             } else {
//                 break;
//             }
//         }
//         return uniqueID;
//     }

//     public Boolean checkExistFile(String fileName) {
//         if (loadFile(fileName) == null) {
//             return false;
//         }
//         // Ton tai file
//         return true;
//     }

//     public Path loadFile(String fileName) {
//         return fileStorageLocation.resolve(fileName).normalize();
//     }
// }
