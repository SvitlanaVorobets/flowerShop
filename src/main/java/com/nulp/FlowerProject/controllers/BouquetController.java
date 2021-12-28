package com.nulp.FlowerProject.controllers;

import com.nulp.FlowerProject.models.Bouquet;
import com.nulp.FlowerProject.models.FindFromData;
import com.nulp.FlowerProject.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BouquetController {

    private BouquetService bouquetService;

    @Autowired
    public BouquetController(BouquetService bouquetService){
        this.bouquetService = bouquetService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("bouquets", bouquetService.findAll());;
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Bouquet bouquetID = bouquetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bouquet Id:" + id));
        model.addAttribute("bouquet", bouquetID);
        return "show";
    }

    @GetMapping("/create")
    public String newBouquet(@ModelAttribute("bouquet") Bouquet bouquet, @ModelAttribute("find") FindFromData findFromData){
        return "create";
    }

    @PostMapping("/created/{id}")
    public String create(@ModelAttribute("bouquet") @Valid Bouquet bouquet, BindingResult bindingResult, @PathVariable("id") long id, @ModelAttribute("find") FindFromData findFromData) {
        if (bindingResult.hasErrors())
            return "create";
        bouquet.setPrice(bouquetService.returnPrice(bouquet.getAccessory()));
        bouquetService.save(bouquet);
        return "redirect:/"+ bouquet.getId() +"/addingFlowers/" + findFromData.getQuantityOfFlowers();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        Bouquet bouquetID = bouquetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bouquet Id:" + id));
        model.addAttribute("bouquet", bouquetID);
        return "edit";
    }

    @PostMapping("/updated/{id}")
    public String update(@ModelAttribute("bouquet") @Valid Bouquet bouquet, BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "edit";

        Bouquet bouquetID = bouquetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bouquet Id:" + id));
        bouquetID.setName(bouquet.getName());
        int tempPrice = bouquetID.getPrice();
        int minusPrice = bouquetService.returnPrice(bouquetID.getAccessory());
        bouquetID.setAccessory(bouquet.getAccessory());
        bouquetID.setPrice(tempPrice - minusPrice + bouquetService.returnPrice(bouquetID.getAccessory()));
        bouquetService.save(bouquetID);
        return "redirect:/";
    }

    @GetMapping("/deleted/{id}")
    public String deleteBouquet(@PathVariable("id") long id) {
        Bouquet bouquetID = bouquetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bouquet Id:" + id));
        bouquetService.delete(bouquetID);
        return "redirect:/";
    }
}
