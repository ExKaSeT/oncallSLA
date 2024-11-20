package com.example.sla.dao;

import com.example.sla.model.Indicator;
import com.example.sla.model.IndicatorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicatorRepository extends JpaRepository<Indicator, IndicatorId> {
}
