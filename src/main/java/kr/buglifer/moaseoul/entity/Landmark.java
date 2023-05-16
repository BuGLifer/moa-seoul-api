package kr.buglifer.moaseoul.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Landmark extends DomainEntity {

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String title;

    private String address;

    private String etc;

    private String content;

    @Enumerated(EnumType.STRING)
    private Concept concept;

    @Getter
    @RequiredArgsConstructor
    public enum Concept {
        ACTIVITY("액티비티"), FOOD("음식"), TOUR("투어"), REST("휴양"), ETC("기타");

        private final String description;
    }
}
