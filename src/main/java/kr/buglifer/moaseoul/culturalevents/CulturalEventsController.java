package kr.buglifer.moaseoul.culturalevents;

import kr.buglifer.moaseoul.entity.CulturalEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CulturalEventsController {

    private final CulturalEventsService culturalEventsService;

    @GetMapping("/api/health")
    public String health() {
        return "UP";
    }

    @GetMapping("/api/cultural-events")
    public ResponseEntity<List<CulturalEvents>> handleGetCulturalEvents() {
        return ResponseEntity.ok(culturalEventsService.readCultrualEvents());
    }
}
