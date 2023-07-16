package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "loger", schema = "webshop_ip", catalog = "")
public class LogerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "poruka")
    private String poruka;
    @Basic
    @Column(name = "level")
    private String level;
    @Basic
    @Column(name = "datum")
    private Date datum;
    @Basic
    @Column(name = "log")
    private String log;

}
