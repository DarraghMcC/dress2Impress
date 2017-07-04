package com.dress2Impress.common.dto.dress;

public class JacketCheckDTO {

    private boolean jacketNeeded;
    private String jacketDetails;

    public boolean isJacketNeeded() {
        return jacketNeeded;
    }

    public JacketCheckDTO setJacketNeeded(boolean jacketNeeded) {
        this.jacketNeeded = jacketNeeded;
        return this;
    }

    public String getJacketDetails() {
        return jacketDetails;
    }

    public JacketCheckDTO setJacketDetails(String jacketDetails) {
        this.jacketDetails = jacketDetails;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JacketCheckDTO that = (JacketCheckDTO) o;

        if (jacketNeeded != that.jacketNeeded) return false;
        return jacketDetails != null ? jacketDetails.equals(that.jacketDetails) : that.jacketDetails == null;
    }

    @Override
    public int hashCode() {
        int result = (jacketNeeded ? 1 : 0);
        result = 31 * result + (jacketDetails != null ? jacketDetails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JacketCheckDTO{" +
                "jacketNeeded=" + jacketNeeded +
                ", jacketDetails='" + jacketDetails + '\'' +
                '}';
    }
}
