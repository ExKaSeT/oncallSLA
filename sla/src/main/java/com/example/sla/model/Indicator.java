package com.example.sla.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="indicators")
public class Indicator {
    @EmbeddedId
    @AttributeOverride(name="name", column=@Column(name="name"))
    @AttributeOverride(name="created_at", column=@Column(name="created_at"))
    private IndicatorId id;

    @Column(name = "slo")
    private double slo;

    @Column(name = "value")
    private double value;

    @Column(name = "is_bad")
    private boolean isBad;
}
