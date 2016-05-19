package com.sctrcd.csd.language;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    private final LanguageRepository languageRepo;

    @Autowired
    public LanguageController(LanguageRepository languageRepo) {
        this.languageRepo = languageRepo;
    }

    @RequestMapping(value = "/languages")
    public List<Language> findLanguages() {
        List<Language> languages = languageRepo.findAll();
        return languages;
    }

    @RequestMapping(value = "/languages/{isocode}")
    public Language findLanguages(@PathVariable String isocode) {
        Language language = languageRepo.findOne(isocode);
        return language;
    }

}
