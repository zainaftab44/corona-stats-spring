package com.zainaftab.coronavirustracker.controllers;

import com.zainaftab.coronavirustracker.models.CoronaStat;
import com.zainaftab.coronavirustracker.services.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class CoronaCasesController {

    @Autowired
    CoronaDataService dataService ;
    @GetMapping("/")
    public String index(Model model){
        ArrayList<CoronaStat> confirmedStats = dataService.getConfirmedStats();
        model.addAttribute("stats", confirmedStats);
        model.addAttribute("totalCases",confirmedStats.stream().mapToLong(stat -> stat.getCurrentToll()).sum());
        model.addAttribute("totalNewCases",confirmedStats.stream().mapToLong(stat -> stat.getCurrentTollDiff()).sum());
        return "index";
    }
}
