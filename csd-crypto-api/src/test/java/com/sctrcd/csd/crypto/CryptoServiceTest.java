package com.sctrcd.csd.crypto;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctrcd.csd.crypto.BCryptHash;
import com.sctrcd.csd.crypto.BCryptHashValidation;
import com.sctrcd.csd.crypto.CryptoApiApplication;
import com.sctrcd.csd.crypto.CryptoService;
import com.sctrcd.csd.crypto.HashValidationRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CryptoApiApplication.class)
@WebAppConfiguration
public class CryptoServiceTest {

    public static final MediaType APPLICATION_JSON_UTF8 = 
            new MediaType(
                    MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),                        
                    Charset.forName("utf8")                     
            );
    
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext context;
    
    private CryptoService cryptoService;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    } 

    @Test
    public void shouldAcceptPreviouslyGeneratedHashX() {
        cryptoService = new CryptoService();
        
        BCryptHashValidation check = cryptoService.isCorrectBCrypt(
                "\"PortalCal3\"", 
                "$2a$10$uxWNssYD45hMnV4ajPH9GuGIv5aioSRg01x0DcAJohW4XvwC0/CK2");
        
        assertTrue("The service should confirm that the password and hash are a valid match.", 
                check.getIsCorrect());
    }
    
    @Test
    public void shouldAcceptPreviouslyGeneratedHash() {
        cryptoService = new CryptoService();
        
        BCryptHashValidation check = cryptoService.isCorrectBCrypt(
                "PortalCal3", 
                "$2a$10$7aLeSDz81EbsI9DEziKCFeE9umMVqdUD2g8t8CadM2dxgupb8GjTC");
        
        assertTrue("The service should confirm that the password and hash are a valid match.", 
                check.getIsCorrect());
    }
    
    @Test
    public void shouldHashText() {
        cryptoService = new CryptoService();
        BCryptHash hash = cryptoService.bcrypt("hello");
        System.out.println(hash);
        assertTrue(BCrypt.checkpw("hello", hash.getHashtext()));
        
        BCryptHashValidation check = cryptoService.isCorrectBCrypt(hash.getPlaintext(), hash.getHashtext());
        assertTrue("The service should confirm that the password and hash are a valid match.", 
                check.getIsCorrect());
    }
    
    @Test
    public void shouldHaveBCryptEndpoint() throws Exception {
        String response = this.mvc.perform(post("/bcrypt?plaintext=hello"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        System.out.println(response);
        
        ObjectMapper mapper = new ObjectMapper();
        BCryptHash responseHash = mapper.readValue(response, BCryptHash.class);
        
        assertEquals("hello", responseHash.getPlaintext());
        assertNotNull(responseHash.getHashtext());
        assertNotNull(responseHash.getSalt());
        
        // The salt is randomised, so we can't check that the response salt or
        // hash is a specific value. However, we can at least check that the
        // response salt/hash combo validates as a hash of the plaintext.
        assertTrue(BCrypt.checkpw("hello", responseHash.getHashtext()));
        
        String response2 = this.mvc.perform(post("/bcrypt")
                .param("plaintext", "hello")
                .param("salt", responseHash.getSalt()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        BCryptHash responseHash2 = mapper.readValue(response2, BCryptHash.class);
        
        assertEquals("hello", responseHash2.getPlaintext());
        assertEquals(responseHash.getSalt(), responseHash2.getSalt());
        assertEquals(responseHash.getHashtext(), responseHash2.getHashtext());
    }
    
    @Test
    public void shouldHavePasswordCheckEndpoint() throws Exception {
        HashValidationRequest request = new HashValidationRequest();
        request.setPlaintext("hello");
        request.setHashtext("$2a$10$LVZVbyTzXeiXnah/ZKE89ubtF0qH0ZDTvpyF1BwQuLtQsJstxw6PK");
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);
        
        this.mvc.perform(post("/bcryptcheck")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

}
