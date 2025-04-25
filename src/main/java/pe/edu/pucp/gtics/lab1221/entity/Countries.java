package pe.edu.pucp.gtics.lab1221.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity

@Table(name="countries")
public class Countries {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "country_id", nullable = false)
    private int countryId;

    @Column(name = "country_name")
    private String countryName;

    @ManyToOne
    @JoinColumn(name="region_id")
    private Regions regions;
}
