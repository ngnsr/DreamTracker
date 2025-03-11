package com.rr.dreamtracker.controller;

import com.rr.dreamtracker.dto.DreamDto;
import com.rr.dreamtracker.entity.Dream;
import com.rr.dreamtracker.service.DreamService;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DreamController {
    private final DreamService dreamService;
    public DreamController(DreamService dreamService) {
        this.dreamService = dreamService;
    }

    @GetMapping("/api/dream")
    public List<Dream> getAll(){
        return dreamService.getAll();
    }

    @GetMapping("/api/dream/{id}")
    public Dream getDreamById(@PathVariable Long id){
        return dreamService.get(id);
    }
    @PostMapping("/api/dream")
    public Dream create(@RequestBody DreamDto dreamDto){
        return dreamService.create(dreamDto);
    }

    @DeleteMapping("/api/dream/{id}")
    public void deleteBook(@PathVariable Long id) {
        dreamService.delete(id);
    }
}
