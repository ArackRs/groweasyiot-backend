package com.groweasy.groweasyapi.report.model.entities;

import com.groweasy.groweasyapi.loginregister.model.entities.UserEntity;
import com.groweasy.groweasyapi.report.model.enums.RecommendationEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate generationDate;

    @Lob // En caso de que los datos sean largos
    private String data;

    @Enumerated(EnumType.STRING)
    private RecommendationEnum recommendation;

    @OneToOne(cascade = CascadeType.ALL)
    private StatisticalAnalysis statisticalAnalysis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
