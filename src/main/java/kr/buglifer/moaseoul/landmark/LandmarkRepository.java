package kr.buglifer.moaseoul.landmark;

import kr.buglifer.moaseoul.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LandmarkRepository extends JpaRepository<Landmark, UUID> {
}
