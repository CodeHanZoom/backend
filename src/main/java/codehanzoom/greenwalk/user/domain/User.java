package codehanzoom.greenwalk.user.domain;

import codehanzoom.greenwalk.donation.domain.Donation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private int totalPoint;

    private int totalDonation;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Donation> donations = new ArrayList<>();

    //== 비밀번호 암호화 메소드 ==//
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    //== 유저 필드 업데이트 ==//
    public void updateName(String updatename) {
        this.name = updatename;
    }

    public void updatePassword(String updatePassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(updatePassword);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    /**
     * 포인트 증가
     */
    public void addTotalPoint(int point) {
        this.totalPoint += point;
    }

    /**
     * 포인트 감소
     */
    public void removeTotalPoint(int point) {
        int restPoint = this.totalPoint - point;
//        if (restPoint < 0) {
//            throw new NotEnoughStockException("need more stock");
//        }
        this.totalPoint  = restPoint;
    }

    /**
     * 총 기부금액 증가
     */

    /**
     * 총 기부금액 감소
     */
}
