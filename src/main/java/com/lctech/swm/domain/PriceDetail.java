package com.lctech.swm.domain;

public class PriceDetail {
    private Integer id;
    private Integer start;
    private Integer end;
    private Double price;
    private Double amount;
    private Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "PriceDetail{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
