package kr.buglifer.moaseoul.landmark;

import kr.buglifer.moaseoul.entity.Landmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;

    public List<Landmark> readLandmarks() {
        return landmarkRepository.findAll();
    }
}
