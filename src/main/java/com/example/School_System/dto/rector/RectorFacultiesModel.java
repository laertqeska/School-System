package com.example.School_System.dto.rector;

public class RectorFacultiesModel {
    private Long id;
    private String name;
    private String code;
    private String deanFirstName;
    private String deanLastName;

    public RectorFacultiesModel(Long id,String name, String code, String deanFirstName,String deanLastName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.deanFirstName = deanFirstName;
        this.deanLastName = deanLastName;
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDeanFirstName() {
        return deanFirstName;
    }

    public String getDeanLastName() {
        return deanLastName;
    }
}
