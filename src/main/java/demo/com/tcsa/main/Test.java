package demo.com.tcsa.main;

import demo.com.tcsa.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author weisongsun
 * @Date ${date} ${time}
 */
public class Test {

    public static void test(String[] args) {
        String temp = " String name = \"hao\"; Predicate predicate = new Predicate(name) ; valu=\"qw\"; identifier = \"as\";";
        String regex = "(\\s*\\w+\\s\\w+\\s*\\=\\s*\\\"\\w+\\\"\\s*\\;)|(\\s*\\w+\\s\\w+\\s*\\=\\s*(new)\\s\\w+\\(.*?\\)\\s*\\;)";
        Matcher m = Pattern.compile(regex).matcher(temp);
        while (m.find()) {
            System.out.println(m.group());
        }
    }


    public static void test1(String[] args) {
        int allLevel = 3;
        int currentLevel = 0;
        String[] typeArray = new String[]{"String", "Integer", "Student"};
        List<String> tempList = temp(allLevel, currentLevel, typeArray);
        for (String temp :tempList) {
            System.out.println(temp);
        }
    }

    private static List<String> temp (int allLevel
            , int currentLevel, String[] typeArray) {
        if (currentLevel == allLevel) {
            return null;
        }
        List<String> typeList = new ArrayList<>();
        currentLevel++;
        System.out.println(currentLevel);
        List<String> tempList = null;
        for (int i = 0; i < 2; i++) {
            StringBuffer stringBuffer = null;
            if (i == 0) {
                stringBuffer = new StringBuffer("Object");
                tempList = temp(allLevel, currentLevel, typeArray);
            } else {
                stringBuffer = new StringBuffer(typeArray[currentLevel - 1]);
            }
            String type = stringBuffer.toString();
            if (tempList != null) {
                for (String temp : tempList) {
                    StringBuffer tempBuffer = new StringBuffer(type);
                    tempBuffer.append("," + temp);
                    typeList.add(tempBuffer.toString());
                }
            } else {
                typeList.add(type);
            }
        }
        return typeList;
    }


    public static void test2(String[] args) {
        String string = "d.getArguments()[0], f.getArguments()[0]);";
        String regex = "\\s*\\w+\\.\\w+\\(.*?\\)";
        Matcher m = Pattern.compile(regex).matcher(string);
        while (m.find()) {
            System.out.println(m.group());
        }
    }

    public static void test3(String[] args) {
        String string = "s.extend(new Variable(\"var\"), new Value(\"v1\"));";
        String regex = "\\s*(s)\\.\\w+\\(.*?\\)";
        Matcher m = Pattern.compile(regex).matcher(string);
        while (m.find()) {
            System.out.println(m.group());
        }
    }

    public static void test5(String[] args) {
        String sentence = "sunwei \"un)\" song \"(dj)\" tt";
        String regex = "\\\"(.*?)\\\"";
        Matcher matcher = Pattern.compile(regex).matcher(sentence);
        while (matcher.find()) {
            String find = matcher.group();
            if (find.contains("(")) {
                find = find.replace("(", "$[$");
            }
            if (find.contains(")")) {
                find = find.replace(")", "$]$");
            }
            sentence = sentence.replace(matcher.group(), find);
        }
        System.out.println(sentence);
    }

    public static void test6(String[] args) {
        String string = "test=new Substitution(); Substitution test,test2, test1; LinkedList<Substitution> res;";
        String regex = "\\s*\\w+\\s(\\w+\\,\\s*)+\\w+\\;";
        Matcher m = Pattern.compile(regex).matcher(string);
        while (m.find()) {
            System.out.println(m.group());
        }
    }

    public static void main(String[] args) {
        String temp = "if (index != -1) { if (!to.get(index).equals(val)) { Assert.assertEquals(index, val);";
        String regex = "((\\s\\w+)|(\\s\\w+\\s\\w+)|(\\s*\\w+\\.\\w+))(\\s*\\=\\s*new\\s\\w+(\\<.*?\\>)*\\(.*?\\)\\s*\\;)|"
                + "(\\s\\w+\\s\\w+)(\\<.*?\\>)*(\\;|\\s*\\=\\s*\\w+\\;)|"
                + "(\\s\\w+\\s*\\=\\s*\\w+\\;)|"
                + "(\\s\\w+\\s*\\=\\s*(.*?)\\;)|"
                + "(\\s*\\w+\\s\\w+\\s*\\=\\s*\\w+\\.\\w+\\(.*?\\)\\;)|"
                + "(\\s*\\w+\\s*\\=\\s*\\w+\\.\\w+\\(.*?\\)\\;)|"
                + "(\\s*\\w+\\[\\]\\s\\w+\\s*\\=((\\s*new\\s\\w+\\[\\])|(\\s*))\\{.*?\\}\\;)|"
                + "(((\\s\\w+)|(\\s*\\w+\\.\\w+))(\\.\\w+\\(.*?\\)\\;))|"
                + "(\\s\\w+\\[\\]\\s\\w+\\s*\\=\\s*\\w+\\.\\w+\\(.*?\\)\\;)|"
                + "(\\s\\w+\\[\\]\\s\\w+\\;)|"
                + "((\\s\\w+\\[\\]\\s\\w+\\s*\\=\\s*new\\s\\w+\\[(.*?)\\]\\;))|"
                + "(\\s*\\w+\\[\\]\\s\\w+\\s*\\=\\s*new\\s\\w+\\(.*?\\)\\;)|"
                + "(\\s*\\w+\\<.*?\\>\\s\\w+(.*?)\\;)|"
                + "(\\s*\\w+\\s\\w+\\s*\\;)|"
                + "(\\s*\\w+\\s\\w+\\s*\\=\\s*\\\"\\w+\\\"\\s*\\;)|"
                + "(\\s*\\w+\\s\\w+\\s*\\=\\s*(new)\\s\\w+\\(.*?\\)\\s*\\;)|"
                + "(\\s*\\w+\\s(\\w+\\,\\s*)+\\w+\\;)|"
                + "(\\s\\w+\\s*\\(.*?\\)\\;)";
        Matcher m = Pattern.compile(regex).matcher(temp);
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}
