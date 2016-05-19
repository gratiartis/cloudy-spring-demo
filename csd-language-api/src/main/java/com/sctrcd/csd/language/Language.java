package com.sctrcd.csd.language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "language")
public class Language {

    @Id
    @Column(length = 2)
    private String isoCode;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String nativeName;

}
