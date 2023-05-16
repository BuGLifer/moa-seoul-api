package kr.buglifer.moaseoul.entity;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class CulturalPlace extends DomainEntity {

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String title;

    private String address;

    private String mainImgPath;

    private String fee;

    private String phone;

    private String homepageLinkPath;

    private String openDesc;

    private String closeDesc;

    private String category;

}
