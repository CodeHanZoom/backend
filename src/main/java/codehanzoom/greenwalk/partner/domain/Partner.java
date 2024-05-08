package codehanzoom.greenwalk.partner.domain;

import codehanzoom.greenwalk.donation.domain.Donation;
import codehanzoom.greenwalk.partner.dto.PartnerRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    private String name;

    private String introduction;

    private int totalDonationAmount;

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private List<Donation> donations = new ArrayList<>();

    // 기부액 감소
    public void removeDonationAmount(int donateAmount) {
        this.totalDonationAmount -= donateAmount;
    }

    // 기부액 증가
    public void addDonationAmount(int donateAmount){
        this.totalDonationAmount += donateAmount;
    }
}
