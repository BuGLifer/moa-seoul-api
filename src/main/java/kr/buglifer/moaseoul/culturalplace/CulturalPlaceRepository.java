package kr.buglifer.moaseoul.culturalplace;

import kr.buglifer.moaseoul.entity.CulturalPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CulturalPlaceRepository extends JpaRepository<CulturalPlace, UUID> {
}
