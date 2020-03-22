package com.gmp.bkk.codejam.sem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmp.bkk.codejam.sem.domain.Campaign;
import com.gmp.bkk.codejam.sem.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SemApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CampaignRepository campaignRepository;

	@Test
	void createCampaign() throws Exception {
		int beforeSize = campaignRepository.findAll().size();

		Campaign campaign = new Campaign();
		campaign.setTitle("Corona");
		campaign.setDescription("Fight with Corona");
		campaign.setPrice(100.0);

		assertThat(beforeSize).isEqualTo(beforeSize);
		mockMvc.perform(post("/api/campaigns")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(campaign)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(notNullValue()))
				.andExpect(jsonPath("$.title").value("Corona"))
				.andExpect(jsonPath("$.description").value("Fight with Corona"))
				.andExpect(jsonPath("$.price").value("100.0"));

		int afterSize = campaignRepository.findAll().size();
		assertThat(afterSize).isEqualTo(beforeSize + 1);
	}

	@Test
	void listCampaigns() throws Exception {
		Campaign campaign = new Campaign();
		campaign.setTitle("Corona");
		campaign.setDescription("Fight with Corona");
		campaign.setPrice(100.0);

		campaignRepository.save(campaign);

		mockMvc.perform(get("/api/campaigns"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].title").value(hasItem("Corona")))
				.andExpect(jsonPath("$.[*].description").value(hasItem("Fight with Corona")))
				.andExpect(jsonPath("$.[*].price").value(hasItem(100.0)));
	}

	@Test
	void getCampaignDetails() throws Exception {
		Campaign campaign = new Campaign();
		campaign.setTitle("Corona");
		campaign.setDescription("Fight with Corona");
		campaign.setPrice(100.0);

		campaignRepository.save(campaign);

		mockMvc.perform(get("/api/campaigns/" + campaign.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Corona"))
				.andExpect(jsonPath("$.description").value("Fight with Corona"))
				.andExpect(jsonPath("$.price").value(100.0));
	}

	@Test
	void updateCampaignDetails() throws Exception {
		Campaign campaign = new Campaign();
		campaign.setTitle("Corona");
		campaign.setDescription("Fight with Corona");
		campaign.setPrice(100.0);

		campaignRepository.save(campaign);

		Campaign updatedCampaign = new Campaign();
		updatedCampaign.setId(campaign.getId());
		updatedCampaign.setTitle("COVID19");
		updatedCampaign.setDescription("Fight with COVID19");
		updatedCampaign.setPrice(1000.0);

		final String content = objectMapper.writeValueAsString(updatedCampaign);
		mockMvc.perform(put("/api/campaigns")
		.contentType(MediaType.APPLICATION_JSON)
		.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(updatedCampaign.getId()))
				.andExpect(jsonPath("$.title").value("COVID19"))
				.andExpect(jsonPath("$.description").value("Fight with COVID19"))
				.andExpect(jsonPath("$.price").value(1000.0));
	}

	@Test
	void deleteCampaign() throws Exception {

		Campaign campaign = new Campaign();
		campaign.setTitle("Corona");
		campaign.setDescription("Fight with Corona");
		campaign.setPrice(100.0);

		campaignRepository.save(campaign);
		int beforeSize = campaignRepository.findAll().size();

		mockMvc.perform(delete("/api/campaigns/" + campaign.getId()))
				.andExpect(status().isOk());

		int afterSize = campaignRepository.findAll().size();
		assertThat(afterSize).isEqualTo(beforeSize - 1);
	}
}
