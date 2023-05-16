package kr.buglifer.moaseoul.entity;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class CulturalEvents extends DomainEntity {

    private BigDecimal latitude;

    private BigDecimal longitude;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean ended;

    private String mainImgPath;

    private String detailLinkPath;

    private String target;

    private String district;

    private String place;

    private String title;

    private String fee;

    private String category;

    private Boolean coordinateTrusted;

    private String content;
}
