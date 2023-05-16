package kr.buglifer.moaseoul.culturalevents;

import kr.buglifer.moaseoul.entity.CulturalEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CulturalEventsService {

    private final CulturalEventsRepository culturalEventsRepository;

    public List<CulturalEvents> readCultrualEvents() {
        return culturalEventsRepository.findAll();
    }
}
