package demo.com.tcsa.analysis;

import demo.com.tcsa.util.PythonUtil;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class SimAnalysis {

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/31 下午7:01
      * @author sunweisong
      */
    public static int fuzzyRatio(String s1, String s2) {
        return FuzzySearch.ratio(s1, s2);
    }
  /**
    * 部分匹配
    * @param
    * @return
    * @throws
    * @date 2018/8/11 上午11:20
    * @author sunweisong
    */
    public static int fuzzyPartialRatio(String s1, String s2) {
        return FuzzySearch.partialRatio(s1, s2);
    }

    public static Integer getSimValue(String s1, String s2) {
        s1 = s1.replace(' ', '`');
        s2 = s2.replace(' ', '`');
        String res = PythonUtil.usePy("sim.py", s1 + " " + s2);
        return Integer.valueOf(res.trim());
    }
}
