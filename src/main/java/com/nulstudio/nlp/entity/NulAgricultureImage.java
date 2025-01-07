package com.nulstudio.nlp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nul_agriculture_image")
public class NulAgricultureImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "crop_name", nullable = false, length = 16)
    private String cropName;

    @Column(name = "disease_name", nullable = false, length = 64)
    private String diseaseName;

    @Column(name = "disease_name_source", nullable = false, length = 64)
    private String diseaseNameSource;

    @Column(name = "disease_type", nullable = false, length = 32)
    private String diseaseType;

    @Column(name = "path", nullable = false, length = 512)
    private String path;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "sensible")
    private Boolean sensible;

    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getSensible() {
        return sensible;
    }

    public void setSensible(Boolean sensible) {
        this.sensible = sensible;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseNameSource() {
        return diseaseNameSource;
    }

    public void setDiseaseNameSource(String diseaseNameSource) {
        this.diseaseNameSource = diseaseNameSource;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}