package demo.entity;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tf_model")
public class TFModel implements Persistable<Integer> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Override
    public boolean isNew() {
        return null == id;
    }
}
