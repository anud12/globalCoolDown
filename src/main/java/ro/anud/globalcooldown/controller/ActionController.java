package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalcooldown.model.action.ActionOnPawnInputModel;
import ro.anud.globalcooldown.service.ActionService;

@RestController
@AllArgsConstructor
@RequestMapping("/action")
@CrossOrigin
public class ActionController {

    private ActionService actionService;

    @PostMapping("/queue")
    public void queue(@RequestBody final ActionOnPawnInputModel actionOnPawnInputModel) {
        actionService.queue(actionOnPawnInputModel);
    }

    @PostMapping("/override")
    public void override(@RequestBody final ActionOnPawnInputModel actionOnPawnInputModel) {
        actionService.override(actionOnPawnInputModel);
    }

    @DeleteMapping()
    public void delete(@RequestBody final Long id) {
        actionService.deleteById(id);
    }
}
