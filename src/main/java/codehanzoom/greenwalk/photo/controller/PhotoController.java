package codehanzoom.greenwalk.photo.controller;

import codehanzoom.greenwalk.photo.service.PhotoService;
import codehanzoom.greenwalk.plogging.dto.PloggingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "photos", description = "사진 API")
public class PhotoController {

    private final PhotoService photoService;

    @Operation(summary = "플로깅 후 사진 첨부")
    @ApiResponse(responseCode = "200", description = "성공적으로 처리됨",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PloggingResponse.class)))
    @PostMapping(value = "/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPlogging(@RequestPart(value = "image", required = false) MultipartFile image,
                                         @RequestParam("step") Long step,
                                         @RequestParam("walkingDistance") double walkingDistance) {
        try {
            if (image == null) {
                // 이미지가 전송되지 않은 경우에 대한 처리
                return ResponseEntity.badRequest().body("이미지 파일이 필요합니다.");
            }

            // S3에 사진 업로드 후 flask에 사진 전달
            PloggingResponse ploggingResponse = photoService.calculatePoints(image, step, walkingDistance);
            return ResponseEntity.ok(ploggingResponse);

        } catch (IOException e) {
            // IOException 발생 시 처리
            e.printStackTrace(); // 또는 로그에 기록하거나 예외를 다시 throw할 수 있습니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드를 실패하였습니다.");
        }
    }
}