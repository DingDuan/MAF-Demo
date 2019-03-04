package demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sim_value_model")
public class SimValueModel extends BaseEntity{

    @Column(name = "mid")
    private int mid;

    @Column(name = "cid1")
    private int cid1;

    @Column(name = "cid2")
    private int cid2;

    @Column(name = "sim_value")
    private double simValue;

    @Column(name = "category")
    private int category;
}
