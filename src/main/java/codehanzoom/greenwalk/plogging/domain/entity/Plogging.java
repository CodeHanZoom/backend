package codehanzoom.greenwalk.plogging.domain.entity;

import codehanzoom.greenwalk.donation.domain.Donation;
import codehanzoom.greenwalk.partner.domain.Partner;
import codehanzoom.greenwalk.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Plogging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long step;

    private double walkingDistance;

    private int trashCount;

    private int point;

    private String imageUrl;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public final static Plogging createPlogging(User user, Long step, double walkingDistance, int trashCount, String imageUrl, int plogginPoint) {
        Plogging plogging = Plogging.builder()
                .user(user)
                .step(step)
                .walkingDistance(walkingDistance)
                .trashCount(trashCount)
                .imageUrl(imageUrl)
                .point(plogginPoint)
                .createDate(LocalDateTime.now())
                .build();

        user.addTotalPoint(plogginPoint); // user 포인트 증가
        user.addTotalStep(step); // user 총 걸음수 증가
        user.addTotalTrashCount(trashCount); // user 총 주운 쓰레기량 증가
        user.addTotalWalkingDistance(walkingDistance); // user 총 걸음거리 증가

        return plogging;
    }
}