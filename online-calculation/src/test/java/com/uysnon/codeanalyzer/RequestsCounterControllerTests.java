package com.uysnon.codeanalyzer;

import com.uysnon.codeanalyzer.controllers.RequestsCounterController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RequestsCounterController.class)
public class RequestsCounterControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void firstRequest_one() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/requests"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }
}
