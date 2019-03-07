package demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class IndexDisplayVO {

    private List<List<SimValueVO>> simValueList;

    private String method_name;

    private int tfid1;

    private int tfid2;
}
