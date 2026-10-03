package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="T_ADDRESS_SHARE")
@Data
public class AddressShare {


    @Id
    @Column(name="OID")
    private String oid;


    @Column(name="FOLIO_BO")
    private String folioBo;


    @Column(name="ADD1")
    private String add1;


    @Column(name="ADD2")
    private String add2;


    @Column(name="ADD3")
    private String add3;


    @Column(name="ADD4")
    private String add4;


    @Column(name="COUNTRY")
    private String country;


    @Column(name="COUNTRY_NAME")
    private String countryName;


}