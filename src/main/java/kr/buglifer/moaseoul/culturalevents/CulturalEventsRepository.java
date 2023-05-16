package kr.buglifer.moaseoul.culturalevents;

import kr.buglifer.moaseoul.entity.CulturalEvents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CulturalEventsRepository extends JpaRepository<CulturalEvents, UUID> {
}
