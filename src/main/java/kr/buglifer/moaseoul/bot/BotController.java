package kr.buglifer.moaseoul.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BotController {

    private final BotService botService;

    @PostMapping("bot/course")
    public ResponseEntity<BotService.BotCourseResponse> handleGenerateBotCourseRequest(@RequestBody BotService.BotCourseRequest request) {
        return ResponseEntity.ok(botService.generateBotCourse(request));
    }
}
