package demo.com.tcsa.model;

import javax.persistence.*;

/**
  * @Date 2018/7/19 下午12:25
  */
@Entity
@Table(name = "PCLabelModel")
public class PCLabelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cid1;
    private int cid2;
    private int label;

    public PCLabelModel() {
    }

    public PCLabelModel(int cid1, int cid2, int label) {
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "PCLabelModel{" +
                "id=" + id +
                ", cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", label=" + label +
                '}';
    }
}
