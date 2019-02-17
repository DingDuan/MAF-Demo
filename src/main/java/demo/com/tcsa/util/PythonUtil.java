package demo.com.tcsa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonUtil {
    /**
     * 调用python代码
     *
     * @param pyName python脚本名（带.py）
     * @param argvs  参数
     * @return 所有命令行输出
     * @author sunzesong
     */
    public static String usePy(String pyName, String argvs) {
        StringBuilder res = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("python " + pyName + " " + argvs);

            InputStreamReader ins = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(ins);
            String s;
            while ((s = br.readLine()) != null) {
                res.append(s).append("\n");
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();

    }
}
