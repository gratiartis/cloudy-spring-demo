package com.sctrcd.csd.language;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sctrcd.csd.language.LanguageApiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LanguageApiApplication.class)
@WebAppConfiguration
public class RestEndpointTest {

    private static final Logger log = LoggerFactory.getLogger(RestEndpointTest.class);

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldReturnListOfLanguages() throws Exception {
        String response = this.mvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("All languages: " + response);
        assertNotNull(response);
    }

    @Test
    public void shouldFindLanguageByIsoCode() throws Exception {
        String response = this.mvc.perform(get("/languages/fr"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("Found language: " + response);
        assertNotNull(response);
    }

}
