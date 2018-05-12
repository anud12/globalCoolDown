package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.anud.globalcooldown.geometry.Area;
import ro.anud.globalcooldown.service.AreaService;
import ro.anud.globalcooldown.service.PawnService;

@RestController
@AllArgsConstructor
public class AreaController {
    private PawnService pawnService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private AreaService areaService;

    @SubscribeMapping("/map")
    public Area pawnSubscription() {
        System.out.println("map subscription");

        return areaService.getTotalArea();
    }
}
