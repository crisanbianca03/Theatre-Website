package com.example.teatru.model;

import jakarta.persistence.*;

@Entity
@Table
public class Administrator {
    @Id
    @SequenceGenerator(
            name = "administrator_sequence",
            sequenceName = "administrator_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "administrator_sequence"
    )
    private int idAdministrator;

    private String nume;
    private String prenume;



    public Administrator() {
    }

    public Administrator(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    public int getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(int idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }


    @Override
    public String toString() {
        return "Administrator{" +
                "idAdministrator=" + idAdministrator +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}
