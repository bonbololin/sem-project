package com.gmp.bkk.codejam.sem.service;

import com.gmp.bkk.codejam.sem.domain.Campaign;
import com.gmp.bkk.codejam.sem.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    private CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public Campaign save(Campaign campaign) {
        return this.campaignRepository.save(campaign);
    }

    public List<Campaign> findAll() {
        return this.campaignRepository.findAll();
    }

    public Optional<Campaign> findById(String id) {
        return this.campaignRepository.findById(id);
    }

    public void deleteById(String id) {
        this.campaignRepository.deleteById(id);
    }
}
