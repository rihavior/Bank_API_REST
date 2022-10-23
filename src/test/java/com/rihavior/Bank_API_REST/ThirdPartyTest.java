package com.rihavior.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihavior.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import com.rihavior.Bank_API_REST.entities.users.ThirdParty;
import com.rihavior.Bank_API_REST.others.Address;
import com.rihavior.Bank_API_REST.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.Digits;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder accountHolder_Test;
    Address testAddress;
    Address testMailingAddress;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Create third party works")
    public void creteThirdParty() throws Exception {

        ThirdParty thirdParty = new ThirdParty("thirdPartyTest","thirdPartyTest","1sdf2asd3asd4asd5asd6asd7asd8asd9asd0asd11as12as13as14as15as16as");

        String body = objectMapper.writeValueAsString(thirdParty);

//        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_third_party").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(userRepository.findByUsername(accountHolderDTO.getUserName()).isPresent());

    }

}
