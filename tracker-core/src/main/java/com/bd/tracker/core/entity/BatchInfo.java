package com.bd.tracker.core.entity;

import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class BatchInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productNm;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String cssQuery;

    @Column(nullable = false, columnDefinition = "varchar(1) default 'Y'")
    private String searchYn;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
