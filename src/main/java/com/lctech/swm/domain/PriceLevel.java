package com.lctech.swm.domain;

/**
 * This domain for contain price of each level
 */
public class PriceLevel {
    private Integer start;
    private Integer end;
    private Double price;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PriceLevel(Integer start, Integer end, Double price) {
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public PriceLevel(){

    }
}
