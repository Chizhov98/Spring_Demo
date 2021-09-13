package com.chizhov.spring.controllers;

import com.chizhov.spring.dao.HorseDao;
import com.chizhov.spring.dao.RaceDao;
import com.chizhov.spring.dao.RaceListDao;
import com.chizhov.spring.entity.Horse;
import com.chizhov.spring.entity.Race;
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

    @GetMapping("/race/{id}")
    public String getRaceInfo(@PathVariable(name = "id") int id, Model model) {
        List<RaceList> list = RaceService.getRaceInfo(id);
        LocalDate date = RaceService.getRaceById(id).getDate();
        model.addAttribute("date", date);
        model.addAttribute("list", list);
        model.addAttribute("id", id);
        return "raceInfo";
    }

    @PostMapping("race/start/{id}")
    public void startRace(@PathVariable(name = "id") int id) throws InterruptedException {
        RaceService.startNewRace(id);
    }

    @GetMapping("race/start")
    public String startRacePage(Model model) {
        List<Horse> horses = RaceService.getAllHorses();
        model.addAttribute("horses", horses);
        return "startRace";
    }

    @GetMapping("/stats")
    public String getStats(Model model) {
        ProfileInfo info = RaceService.getProfileInfo();
        model.addAttribute("info", info);
        return "stats";
    }


}
