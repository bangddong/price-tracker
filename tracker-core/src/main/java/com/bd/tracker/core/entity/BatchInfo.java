package com.bd.tracker.core.entity;

import com.bd.tracker.core.constant.ScrapCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@DynamicInsert // searchYn 디폴트 Y를 위해 설정
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class BatchInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(nullable = false)
    private String cssQuery;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScrapCategory category;

    @Column(nullable = false)
    private String productNm;

    @Column(nullable = false, columnDefinition = "varchar(1) default 'Y'")
    private String searchYn;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private BatchInfo(
            String url,
            String cssQuery,
            ScrapCategory category,
            String productNm
    ) {
        this.url = url;
        this.cssQuery = cssQuery;
        this.category = category;
        this.productNm = productNm;
    }

    public static BatchInfo of(
            String url,
            String cssQuery,
            ScrapCategory category,
            String productNm
    ) {
        return new BatchInfo(url, cssQuery, category, productNm);
    }

}
