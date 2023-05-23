package kr.buglifer.moaseoul.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BotController {

    private final BotService botService;

    @PostMapping("bot/course")
    public ResponseEntity<String> handleGenerateBotCourseRequest() {
        return ResponseEntity.ok(botService.generateBotCourse()
                                           .toString());
    }
}
