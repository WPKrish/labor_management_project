package com.example.labor_management_project.controller;

import com.example.labor_management_project.model.JobRole;
import com.example.labor_management_project.model.Site;
import com.example.labor_management_project.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    SiteService siteService;


    @GetMapping("{siteID}")
    public Optional<Site> getSiteDetails(@PathVariable("siteID") int siteID){
        return siteService.getSite(siteID);
    }

    // Read All User Role Details from DB
    @GetMapping()
    public List<Site> getAllSiteDetails(){
        return siteService.getAllSite();
    }

    @PostMapping
    public String createSiteDetails (@RequestBody Site site){
        siteService.createSite(site);
        return "Site created successfully";
    }

    @PutMapping
    public String updateSiteDetails (@RequestBody Site site){
        siteService.updateSite(site);
        return "Site Updated successfully";
    }

    @DeleteMapping("{siteID}")
    public String deleteUserDetails(@PathVariable("siteID") int siteID){
        siteService.deleteSite(siteID);
        return "Site Deleted successfully";
    }



}

