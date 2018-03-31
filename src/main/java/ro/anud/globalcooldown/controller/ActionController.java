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

    @PostMapping()
    public void add(@RequestBody final ActionOnPawnInputModel actionOnPawnInputModel) {
        actionService.queue(actionOnPawnInputModel);
    }

    @DeleteMapping()
    public void delete(@RequestBody final Long id) {
        actionService.deleteById(id);
    }
}
