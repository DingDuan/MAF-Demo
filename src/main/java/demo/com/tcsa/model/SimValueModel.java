package demo.com.tcsa.model;

import javax.persistence.*;

/**
  * @Date 2018/6/26 下午7:10
  */

@Entity
@Table(name = "SimValueModel")
public class SimValueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int mid;
    private int cid1;
    private int cid2;
    private double simValue;

    /**
     * 0: ratio, 1: partialRatio
     * 2018.08.14
     */
    private int category;

    public SimValueModel() {
    }

    public SimValueModel(int cid1, int cid2, int simValue) {
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.simValue = simValue;
    }

    public SimValueModel(int mid, int cid1, int cid2, int simValue) {
        this.mid = mid;
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.simValue = simValue;
    }

    public SimValueModel(int mid, int cid1, int cid2, double simValue, int category) {
        this.mid = mid;
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.simValue = simValue;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getCid1() {
        return cid1;
    }

    public void setCid1(int cid1) {
        this.cid1 = cid1;
    }

    public int getCid2() {
        return cid2;
    }

    public void setCid2(int cid2) {
        this.cid2 = cid2;
    }

    public double getSimValue() {
        return simValue;
    }

    public void setSimValue(double simValue) {
        this.simValue = simValue;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SimValueModel{" +
                "id=" + id +
                ", mid=" + mid +
                ", cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", simValue=" + simValue +
                ", category=" + category +
                '}';
    }
}
