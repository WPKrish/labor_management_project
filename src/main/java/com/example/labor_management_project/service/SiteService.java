package com.example.labor_management_project.service;

import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.Site;
import com.example.labor_management_project.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    @Autowired
    SiteRepository siteRepository;


    public String createSite(Site site) {
        siteRepository.save(site);
        return "Success";
    }


    public String updateSite(Site site) {
        // More Business Logic
        siteRepository.save(site);
        return "Success";
    }


    public String deleteSite(int siteID) {
        // More Business Logic
        siteRepository.deleteById(siteID);
        return "Success";
    }


    public Optional<Site> getSite(int siteID) {
        // More Business Logic
        if(siteRepository.findById(siteID).isEmpty())
            throw new UserNotFoundException("Requested Site not exist");

        return siteRepository.findById(siteID);
    }


    public List<Site> getAllSite() {
        // More Business Logic
        return siteRepository.findAll();
    }
}

