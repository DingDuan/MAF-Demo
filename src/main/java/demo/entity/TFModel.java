package demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tf_model")
public class TFModel extends BaseEntity{

    @Column(name = "fragment")
    private String fragment;

    @Column(name = "length")
    private Integer length;

    @Column(name = "state_num")
    private Integer stateNum;

    @Column(name = "mid")
    private Integer mid;

    @Column(name = "cid")
    private Integer cid;

}
