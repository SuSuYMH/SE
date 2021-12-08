package com.susu.se.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "assistant")
public class Assistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assistantId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    @JsonBackReference
    private Evaluator evaluator;

}
