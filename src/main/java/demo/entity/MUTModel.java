package demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "mutmodel")
public class MUTModel extends BaseEntity{

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
}
