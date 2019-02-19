package demo.vo;

import lombok.Data;

@Data
public class MUTInfoVO {

    private Integer id;

    private String access;

    private String arguments;

    private String className;

    private String isConstructor;

    private Integer methodId;

    private String methodName;
}
