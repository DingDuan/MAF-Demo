package demo.com.tcsa.analysis;

/**
  * @Author sunweisong
  * @Date 2018/7/31 上午9:33
  */
public class DistanceCalculate {

    /**
      * Get minimum of three values
      * @param a,b,c
      * @return 三个值中的最小值
      * @throws
      * @date 2018/7/31 上午9:39
      * @author sunweisong
      */
    private static int getMinimum(int a, int b, int c) {
        int mi;
        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }
        return mi;
    }

    /**
      * Compute Levenshtein distance
      * @param sourceStr 原字符串
      * @param targetStr 目标符串
      * @return int 最小编辑距离
      * @date 2018/7/31 上午9:37
      * @author sunweisong
      */
    public static int calculateLD(String sourceStr, String targetStr) {
        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1
        n = sourceStr.length ();
        m = targetStr.length ();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n+1][m+1];

        // Step 2
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = sourceStr.charAt (i - 1);
            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = targetStr.charAt (j - 1);
                // Step 5
                if (s_i == t_j) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }
                // Step 6
                d[i][j] = getMinimum(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);
            }
        }
        
        // Step 7
        return d[n][m];
    }

    public static void test(String[] args) {
        // shorter string 8
//        String sourceStr = "Variable var1 = new Variable(\"variable\");";
//        String targetStr = "Variable variable1=new Variable(\"variable1\");";

        // remove base words 6
//        String sourceStr = "var1=(\"variable\");";
//        String targetStr = "variable1=(\"variable1\");";

        // 123
        String sourceStr = "var1=(\"variable\");" +
                "ag1=.variable(var1);" +
                "va1=(\"value0001\");" +
                "ag1=.value(va1);"+
                "assertNotNull(ag1.toString());";
        String targetStr = "var1=(\"variable\");" +
                "ag1=.variable(var1);" +
                "va1=(\"value0001\");" +
                "ag1=.value(va1);"+
                "assertNotNull(ag1.toString());";
        String targetStr1 = "variable1=(\"variable1\");" +
                "value1=(\"value1\");" +
                "argument;" +
                "argument=.variable(variable1);" +
                "ag1=.value(value1);" +
                "assertEquals(\"value1\",argument.toString());" +
                "assertNotNull(\"VAR:variable1\",argument.toString());";


        int ld = calculateLD(targetStr, sourceStr);
        System.out.println(ld);
    }
}
