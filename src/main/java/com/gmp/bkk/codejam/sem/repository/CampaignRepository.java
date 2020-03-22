package com.gmp.bkk.codejam.sem.repository;

import com.gmp.bkk.codejam.sem.domain.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignRepository extends MongoRepository<Campaign, String> {
}
