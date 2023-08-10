package com.deliktas.internshipproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String unvan;

    private String  mkkSicilNo;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="share_id")
    @JsonIgnore
    private Share pay;

    // One-to-one relationship with VerdictDetails entity
    @ManyToMany(fetch = FetchType.LAZY, cascade=  CascadeType.ALL)
    @JoinTable(
            name = "verdict_share",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "verdict_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<VerdictDetails> verdictDetails = new HashSet<>();


    public VerdictDetails verdictDetailsSetToVerdictDetails() {
        return verdictDetails.iterator().next();
    }

    public Set<VerdictDetails> verdictDetailsToVerdictDetailsSet(VerdictDetails verdictDetails) {

        if (verdictDetails == null ) {
            return null;
        }
        this.verdictDetails.add(verdictDetails);
        return this.verdictDetails;
    }

}
