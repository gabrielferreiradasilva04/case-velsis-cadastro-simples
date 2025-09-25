package com.backend.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String document;

    //variáveis do endereço
    @Column(nullable = false)
    private String addressLine;
    private int addressNumber;
    @Column(length = 155, nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String zip;

    //variáveis para auditoria
    @CreatedDate
    @Column(nullable = false)
    private LocalDate createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public UserEntity(UUID id, String name, LocalDate birthDate, String document, String addressLine, int addressNumber,
                      String city, String state, String zip, LocalDate createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
        this.addressLine = addressLine;
        this.addressNumber = addressNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public int getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", document='" + document + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", addressNumber=" + addressNumber +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}