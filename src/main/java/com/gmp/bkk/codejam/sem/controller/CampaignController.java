package com.gmp.bkk.codejam.sem.controller;

import com.gmp.bkk.codejam.sem.domain.Campaign;
import com.gmp.bkk.codejam.sem.service.CampaignService;
import com.sun.deploy.security.CertStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CampaignController {
    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/campaigns")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) throws URISyntaxException {
        final Campaign savedCampaign = campaignService.save(campaign);
        return ResponseEntity
                .created(new URI("/api/campaign/"))
                .body(savedCampaign);
    }

    @GetMapping("/campaigns")
    public ResponseEntity<List<Campaign>> createCampaign() throws URISyntaxException {
        final List<Campaign> campaigns = campaignService.findAll();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/campaigns/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable String id) throws URISyntaxException {
        final Optional<Campaign> campaign = campaignService.findById(id);
        campaign.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(campaign.get());
    }

    @PutMapping("/campaigns")
    public ResponseEntity<Campaign> updateCampaign(@RequestBody Campaign campaign) throws URISyntaxException {
        Campaign updatedCampaign = campaignService.save(campaign);
        return ResponseEntity.ok(updatedCampaign);
    }

    @DeleteMapping("/campaigns/{id}")
    public ResponseEntity<Campaign> deleteCampaign(@PathVariable String id) throws URISyntaxException {
        campaignService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
