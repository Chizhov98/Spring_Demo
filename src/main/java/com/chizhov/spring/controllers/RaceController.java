package com.chizhov.spring.controllers;

import com.chizhov.spring.entity.RaceList;
import com.chizhov.spring.service.RaceService;
import com.chizhov.spring.tdo.ProfileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RaceController {
    @Autowired
    RaceService raceService;

    @GetMapping("/race/{id}")
    public String getRaceInfo(@PathVariable(name = "id") int id, Model model) {
        List<RaceList> list = raceService.getRaceInfo(id);
        LocalDate date = raceService.getRaceById(id).getDate();
        model.addAttribute("date", date);
        model.addAttribute("list", list);
        model.addAttribute("id", id);
        return "raceInfo";
    }

    @PostMapping("race/start/{id}")
    public void startRace(@PathVariable(name = "id") int id) throws InterruptedException {
        raceService.firstInit();
        raceService.startNewRace(id);
    }

    @GetMapping("/stats")
    public String getStats(Model model) {
        ProfileInfo info = raceService.getProfileInfo();
        model.addAttribute("info", info);
        return "stats";
    }
}
