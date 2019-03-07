package demo.entity;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sim_value_model")
public class SimValueModel implements Persistable<Integer> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Override
    public boolean isNew() {
        return null == id;
    }
}
