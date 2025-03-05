package com.example.teatru.controller;

import com.example.teatru.service.LocService;
import com.example.teatru.model.Loc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/loc")
public class LocController {
    private final LocService locService;

    @Autowired
    public LocController(LocService locService) {
        this.locService = locService;
    }

    @GetMapping
    public List<Loc> getLocuri() {
        return locService.getLocuri();
    }

    @PostMapping
    public void registerNewLoc(@RequestBody Loc loc) {
        locService.addNewLoc(loc);
    }

    @DeleteMapping(path = "{locId}")
    public void deleteLoc(@PathVariable("locId") Integer locId) {
        locService.deleteLoc(locId);
    }
}
