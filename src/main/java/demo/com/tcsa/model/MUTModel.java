package demo.com.tcsa.model;

import javax.persistence.*;

@Entity
@Table(name = "MUTModel", schema = "tcsa")
public class MUTModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int methodId;

    private String className;

    private String methodName;

    private String arguments;

    private String access;

    private String isConstructor;

    public MUTModel() {
    }

    public MUTModel(int id, int methodId, String className, String methodName, String arguments, String access, String isConstructor) {
        this.id = id;
        this.methodId = methodId;
        this.className = className;
        this.methodName = methodName;
        this.arguments = arguments;
        this.access = access;
        this.isConstructor = isConstructor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getIsConstructor() {
        return isConstructor;
    }

    public void setIsConstructor(String isConstructor) {
        this.isConstructor = isConstructor;
    }

    @Override
    public String toString() {
        return "MUTModel{" +
                "id=" + id +
                ", methodId=" + methodId +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", arguments='" + arguments + '\'' +
                ", access='" + access + '\'' +
                ", isConstructor='" + isConstructor + '\'' +
                '}';
    }
}
