package com.bd.tracker.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ScrapInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_info_id")
    BatchInfo batchInfo;

    @Column(nullable = false)
    private Integer price;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private ScrapInfo(BatchInfo batchInfo, Integer price) {
        this.batchInfo = batchInfo;
        this.price = price;
    }

    public static ScrapInfo of(
            BatchInfo batchInfo,
            Integer price
    ) {
        return new ScrapInfo(batchInfo, price);
    }

}
