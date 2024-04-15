package codehanzoom.greenwalk.photo.controller;

import codehanzoom.greenwalk.photo.dto.TrashCountDto;
import codehanzoom.greenwalk.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    // 사진이랑 걸음수, 걸은 거리 반환 받아야 됨.
    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> photoUpload(@RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            if (image == null) {
                // 이미지가 전송되지 않은 경우에 대한 처리
                return ResponseEntity.badRequest().body("이미지 파일이 필요합니다.");
            }
            // flask에 사진 전달
            int trashCount = photoService.uploadImage(image);

            return ResponseEntity.ok(new TrashCountDto(trashCount));

        } catch (IOException e) {
            // IOException 발생 시 처리
            e.printStackTrace(); // 또는 로그에 기록하거나 예외를 다시 throw할 수 있습니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드를 실패하였습니다.");
        }
    }
}