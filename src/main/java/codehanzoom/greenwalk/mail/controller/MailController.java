package codehanzoom.greenwalk.mail.controller;


import codehanzoom.greenwalk.global.dto.ResponseDto;
import codehanzoom.greenwalk.mail.dto.RequestEmailDto;
import codehanzoom.greenwalk.mail.service.MailAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailAuthService mailAuthService;
    private final RedisTemplate<String, String> redisTemplate;

    // 이메일 인증번호 요청 컨트롤러
    @PostMapping("/auth/join/Verification_requests")
    public ResponseDto<String> getEmailAuthNumber(@RequestBody RequestEmailDto requestEmailDto){
        log.info("컨트롤러 진입 이메일 인증");
        mailAuthService.sendCodeToEmail(requestEmailDto.getEmail());
        return new ResponseDto<String>(HttpStatus.OK.value(),"인증번호 전송 완료");
    }

    // 이메일 인증번호 확인 컨트롤러(서버에서 인증번호 전송이 된 후에 실행되는 메서드)
    @PostMapping("/auth/join/Verification")
    public ResponseDto<Boolean> matchEmailAuthNumber(@RequestBody RequestEmailDto requestEmailDto){
        return new ResponseDto<Boolean>(HttpStatus.OK.value(),
                mailAuthService.verifiedCode(requestEmailDto.getEmail(), requestEmailDto.getAuthNumber()));
    }

    @GetMapping("/auth/redisTest")
    public String getRedisName(@RequestParam("email") String email){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email,email);
        String getEmail = valueOperations.get(email);
        return getEmail;
    }
}
