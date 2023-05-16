package kr.buglifer.moaseoul.culturalplace;

import kr.buglifer.moaseoul.entity.CulturalPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CulturalPlaceService {

    private final CulturalPlaceRepository culturalPlaceRepository;

    public List<CulturalPlace> readCulturalPlaces() {
        return culturalPlaceRepository.findAll();
    }
}
