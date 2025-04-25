package pe.edu.pucp.gtics.lab1221.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="departments")
public class Departments {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "department_id", nullable = false)
        private int departmentId;

        @Column(name = "department_name")
        private String departmentName;

        @ManyToOne
        @JoinColumn(name="manager_id")
        private Employees employees;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Locations locations;
    }

