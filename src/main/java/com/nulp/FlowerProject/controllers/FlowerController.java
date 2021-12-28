package com.nulp.FlowerProject.controllers;

import com.nulp.FlowerProject.form.FlowerForm;
import com.nulp.FlowerProject.models.Bouquet;
import com.nulp.FlowerProject.models.FindFromData;
import com.nulp.FlowerProject.models.Flower;
import com.nulp.FlowerProject.service.BouquetService;
import com.nulp.FlowerProject.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class FlowerController {

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private BouquetService bouquetService;

    @Autowired
    public FlowerController(FlowerService flowerService){
        this.flowerService = flowerService;
    }

    @GetMapping("/{id}/addingFlowers/{quantity}")
    public String showForms(Model model, @PathVariable long id, @PathVariable int quantity){
        Bouquet bouquet = bouquetService.findById(id).orElse(null);
        FlowerForm flowerForm = new FlowerForm();
        if(quantity < 1) return "redirect:/";
        for (int i = 1; i <= quantity; i++) {
            Flower tempFlower = new Flower();
            tempFlower.setBouquet(bouquet);
            flowerForm.addFlower(tempFlower);
        }

        model.addAttribute("form", flowerForm);
        return "addFlowers";
    }

    @PostMapping("/addedFlowers/{id}")
    public String addFlowers(@ModelAttribute("flowerForm") FlowerForm flowerForm, @PathVariable long id) {
        Bouquet bouquet = bouquetService.findById(id).orElse(null);
        int newPrice = flowerForm.returnPriceFromList(flowerForm.getFlowers());
        bouquet.addPrice(newPrice);
        flowerService.saveAll(flowerForm.getFlowers());
        return "redirect:/";
    }

    @GetMapping("/{id}/listFlowers")
    public String index(Model model, @PathVariable long id){
        model.addAttribute("find", new FindFromData());
        Iterable<Flower> flowers = flowerService.findFlowersByBouquetID(id);
        model.addAttribute("flowers", flowers);
        if(!(flowers.iterator().hasNext())) return "redirect:/";
        return "listFlower";
    }

    @GetMapping("/{id}/listFlowers/sorted")
    public String listOfSortedFlowers(Model model, @PathVariable long id){
        model.addAttribute("find", new FindFromData());
        Iterable<Flower> flowers = flowerService.sortFlowersByLevel(id);
        model.addAttribute("flowers", flowers);
        return "listFlower";
    }

    @PostMapping("/{id}/listFlowers/found")
    public String listOfFoundFlowers(Model model, @PathVariable long id,@ModelAttribute("find") FindFromData findFromData){
        Iterable<Flower> flowers = flowerService.findFlowersByLength(findFromData.getMinLength(), findFromData.getMaxLength(), id);
        if(!(flowers.iterator().hasNext())){
            flowers = flowerService.findFlowersByBouquetID(id);
        }
        model.addAttribute("flowers", flowers);
        return "listFlower";
    }

    @GetMapping("/editFlower/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        Flower flowerID = flowerService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid flower Id:" + id));
        model.addAttribute("flower", flowerID);
        return "editFlower";
    }

    @PostMapping("/editedFlower/{id}/{bouquetID}")
    public String update(@ModelAttribute("flower") Flower flower, BindingResult bindingResult, @PathVariable("id") long id, @PathVariable("bouquetID") long bouquetID) {
        if (bindingResult.hasErrors())
            return "editFlower";
        Flower flowerID = flowerService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid flower Id:" + id));
        flowerID.setLengthOfPedicel(flower.getLengthOfPedicel());
        flowerID.setLevelOfFreshness(flower.getLevelOfFreshness());
        flowerService.save(flowerID);
        return "redirect:/" + bouquetID + "/listFlowers";
    }

    @GetMapping("/deleteFlower/{id}")
    public String deleteFlower(@PathVariable("id") long id) {
        Flower flowerID = flowerService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid flower Id:" + id));
        Bouquet bouquet = bouquetService.findById(flowerID.getBouquet().getId()).orElse(null);

        int minusPrice = flowerService.returnPrice(flowerID.getType());
        bouquet.setPrice(bouquet.getPrice() - minusPrice);
        flowerService.delete(flowerID);
        return "redirect:/";
    }
}
