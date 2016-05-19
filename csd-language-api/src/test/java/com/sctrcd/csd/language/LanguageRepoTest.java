package com.sctrcd.csd.language;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sctrcd.csd.language.Language;
import com.sctrcd.csd.language.LanguageApiApplication;
import com.sctrcd.csd.language.LanguageRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LanguageApiApplication.class)
public class LanguageRepoTest {

    @Autowired
    private LanguageRepository languageRepo;

    @Test
    public void shouldFindAllLanguages() {
        List<Language> languages = languageRepo.findAll();

        assertNotNull(languages);
        assertThat(languages.size(), greaterThan(0));

        System.out.println("\n\nFound languages: \n" + languages + "\n\n");
    }

    @Test
    public void shouldFindOneLanguage() {
        Language language = languageRepo.findOne("fr");

        assertNotNull("Should have found a language.", language);

        assertEquals("ISO code of the language found should be 'fr'.", "fr", language.getIsoCode());
        assertEquals("The English name of the language found should be 'French'", "French", language.getName());
        assertEquals("The French name of the language found should be 'Français'", "Français", language.getNativeName());

        System.out.println("\n\nFound language: \n" + language + "\n\n");
    }

}
