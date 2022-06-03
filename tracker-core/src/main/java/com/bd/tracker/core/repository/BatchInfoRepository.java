package com.bd.tracker.core.repository;

import com.bd.tracker.core.constant.ScrapCategory;
import com.bd.tracker.core.entity.BatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchInfoRepository extends JpaRepository<BatchInfo, Long> {
    List<BatchInfo> findByCategory(ScrapCategory category);
}
