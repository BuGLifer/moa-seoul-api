package kr.buglifer.moaseoul.landmark;

import kr.buglifer.moaseoul.entity.Landmark;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LandmarkController {

    private final LandmarkService landmarkService;

    @GetMapping("/api/landmarks")
    public ResponseEntity<List<Landmark>> getLandmarks() {
        return ResponseEntity.ok(landmarkService.readLandmarks());
    }
}
