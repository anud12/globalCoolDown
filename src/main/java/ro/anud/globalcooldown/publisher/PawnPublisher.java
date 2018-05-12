package ro.anud.globalcooldown.publisher;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.mapper.PawnMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PawnPublisher {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void publish(List<Pawn> pawnList) {
        simpMessagingTemplate.convertAndSend("/app/pawn", pawnList
                .stream()
                .map(PawnMapper::toPawnOutputModel)
                .collect(Collectors.toList()));
    }
}
