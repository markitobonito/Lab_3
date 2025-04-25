package pe.edu.pucp.gtics.lab1221.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="employees")
public class Employees {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "employee_id", nullable = false)
    private int employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private LocalDateTime hireDate;

    @Column(name = "salary")
    private double salary;

    @Column(name = "comission_pct")
    private double comissionPct;

    @Column(name = "enabled")
    private double enabled;


    @ManyToOne
    @JoinColumn(name="manager_id")
    private Employees employees;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Departments departments;

    @ManyToOne
    @JoinColumn(name="job_id")
    private Jobs jobId;
}
