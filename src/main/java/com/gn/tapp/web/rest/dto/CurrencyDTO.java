package com.gn.tapp.web.rest.dto;

public class CurrencyDTO implements Comparable<CurrencyDTO> {

    private String code;

    private String name;

    public CurrencyDTO() {
    }

    public CurrencyDTO(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int compareTo(final CurrencyDTO o) {
        if (o.getName() == null || this.getName() == null) {
            return 0;
        }
        return this.getName().compareTo(o.getName());
    }
}
