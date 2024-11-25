package com.oocl.springbootemployee.model;

import java.util.List;
import java.util.Objects;

public class Companies {
    private int registrationNumber;
    private String companyName;

    public Companies(int registrationNumber, String companyName) {
        this.registrationNumber = registrationNumber;
        this.companyName = companyName;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Companies companies = (Companies) o;
        return registrationNumber == companies.registrationNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registrationNumber);
    }
}
