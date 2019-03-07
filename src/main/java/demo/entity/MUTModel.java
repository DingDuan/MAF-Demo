package demo.entity;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mut_model")
public class MUTModel implements Persistable<Integer> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "access")
    private String access;

    @Column(name = "arguments")
    private String arguments;

    @Column(name = "class_name")
    private String className;

    @Column(name = "is_constructor")
    private String isConstructor;

    @Column(name = "method_id")
    private Integer methodId;

    @Column(name = "method_name")
    private String methodName;

    @Override
    public boolean isNew() {
        return null == id;
    }
}
