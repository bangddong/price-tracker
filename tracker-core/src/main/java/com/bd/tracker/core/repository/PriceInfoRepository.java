package com.bd.tracker.core.repository;

import com.bd.tracker.core.entity.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
}
