package kr.buglifer.moaseoul.culturalplace;

import kr.buglifer.moaseoul.entity.CulturalPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CulturalPlaceController {

    private final CulturalPlaceService culturalPlaceService;

    @GetMapping("/cultural-places")
    public ResponseEntity<List<CulturalPlace>> getCulturalPlaces() {
        return ResponseEntity.ok(culturalPlaceService.readCulturalPlaces());
    }
}
