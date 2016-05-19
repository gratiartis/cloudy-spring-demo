package com.sctrcd.csd.web.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Language {

    private String isoCode;
    private String name;
    private String nativeName;

}