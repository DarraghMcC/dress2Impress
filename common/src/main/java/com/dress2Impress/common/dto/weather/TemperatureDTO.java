package com.dress2Impress.common.dto.weather;

public class TemperatureDTO {

    private Double maxTemp;
    private Double minTemp;

    public Double getMaxTemp() {
        return maxTemp;
    }

    public TemperatureDTO setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
        return this;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public TemperatureDTO setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureDTO that = (TemperatureDTO) o;

        if (maxTemp != null ? !maxTemp.equals(that.maxTemp) : that.maxTemp != null) return false;
        return minTemp != null ? minTemp.equals(that.minTemp) : that.minTemp == null;
    }

    @Override
    public int hashCode() {
        int result = maxTemp != null ? maxTemp.hashCode() : 0;
        result = 31 * result + (minTemp != null ? minTemp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TemperatureDTO{" +
                "maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                '}';
    }
}
