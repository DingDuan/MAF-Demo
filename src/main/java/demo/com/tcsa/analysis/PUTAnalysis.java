package demo.com.tcsa.analysis;

import demo.dao.MUTModelDao;
//import demo.com.tcsa.daoImpl.MUTModelDaoImpl;
import demo.entity.MUTModel;
import demo.com.tcsa.util.FileUtil;
import demo.com.tcsa.util.ParenthesisUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class PUTAnalysis {

//    private static MUTModelDao putMethodDao = MUTModelDaoImpl.getInstance();

    public static List<MUTModel> mutModelList = new ArrayList<>();

    public static List<MUTModel> analyze(String rootPath) {
        MUTModel mutModel = new MUTModel();
        File rootDirectory = new File(rootPath);
        if (!rootDirectory.exists()) {
            System.err.println("The root directory does not exist.");
            return mutModelList;
        }
        String[] fileNames = {"Argument.java","Datalog.java","Fact.java","Predict.java","Program.java","Rule.java","Substitution.java","Value.java","Variable.java"};
        traverseSubjectDirectory(rootDirectory,fileNames);
        //List<File> directories = FileUtil.traverseRootDirectory(rootDirectory, "subject");
        //for (File directory : directories) {
        //    traverseSubjectDirectory(directory);
        //}
        return mutModelList;
    }

    /**
     * 遍历subject目录
     *
     * @param subjectDirectory subject目录路径
     * @author sunzesong
     */
    private static void traverseSubjectDirectory(File subjectDirectory) {
        File[] subjectDirectories = subjectDirectory.listFiles();
        if (subjectDirectories != null && subjectDirectories.length > 0) {
            for (File directory : subjectDirectories) {
                if (".DS_Store".equals(directory.getName())) {
                    continue;
                }

                List<File> FUTList = getAllFUTPathFromPUT(directory);
                if (FUTList != null) {
                    for (File file : FUTList) {
                        String fileContent = FileUtil.readFileContentToString(file);
                        if (!ParenthesisUtil.judgeParenthesisMatchAmongString(fileContent)) {
                            System.err.println("The parenthesis in the test file \"" + file.getName() + "\" is mismatched!");
                            continue;
                        }
                        analyzeSubjectFileContentString(fileContent);
                    }
                }
            }
        } else {
            System.err.println("The directory \"" + subjectDirectory.getAbsolutePath() + "\" is empty.");
        }
    }

    /**
     * 新的直接找到待测程序并分析
     *
     * @param PUTRootDirectory 待测程序所在目录
     * @param PUTNames 待测程序所有文件名称
     * @author duanding
     *
     */
    private static void traverseSubjectDirectory(File PUTRootDirectory,String[] PUTNames){
        MUTModel mutModel = new MUTModel();
        File[] files = PUTRootDirectory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                for(String PUTName : PUTNames){
                    if(fileName.equals(PUTName)){
                        String fileContent = FileUtil.readFileContentToString(file);
                        if (!ParenthesisUtil.judgeParenthesisMatchAmongString(fileContent)) {
                            System.err.println("The parenthesis in the test file \"" + file.getName() + "\" is mismatched!");
                            continue;
                        }
                        analyzeSubjectFileContentString(fileContent);
                    }
                }
            }
        }else {
            System.err.println("The directory \"" + PUTRootDirectory.getAbsolutePath() + "\" is empty.");
        }
//        return mutModel;

    }

    /**
     * 分析subject文件内容字符串, 切割类(包括内部类)
     *
     * @param subjectFileContentString subject文件内容字符串
     */
    private static void analyzeSubjectFileContentString(String subjectFileContentString) {
        MUTModel mutModel = new MUTModel();
        int leftBracketsNum = 0;
        int rightBracketsNum = 0;
        int innerLeftBracketsNum = 0;
        int innerRightBracketsNum = 0;

        boolean hasInnerClass = false;
        List<String> words = Arrays.asList(subjectFileContentString.split(" "));

        String className = "";
        String innerClassName = "";
        StringBuilder classContentString = new StringBuilder();
        StringBuilder innerClassContentString = new StringBuilder();

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            if (!hasInnerClass) {
                if (word.contains("{")) {
                    for (int j = 0; j < word.length(); j++) {
                        if (word.charAt(j) == '{') {
                            leftBracketsNum++;
                        }
                    }
                }

                if (word.contains("}")) {
                    for (int j = 0; j < word.length(); j++) {
                        if (word.charAt(j) == '}') {
                            rightBracketsNum++;
                        }
                    }
                }

                if (word.equals("class")) {
                    // 判断内部类
                    if (leftBracketsNum != rightBracketsNum) {
                        hasInnerClass = true;
                    }

                    String newClassName;
                    // 类名与"{"未分开, 如: class Test{ ... }
                    if (words.get(i + 1).contains("{")) {
                        newClassName = words.get(i + 1).split("\\{")[0];
                        words.set(i + 1, "{");
                    } else {
                        newClassName = words.get(++i);
                    }

                    if (hasInnerClass) {
                        innerClassName = newClassName;
                    } else {
                        className = newClassName;
                    }
                    continue;
                }

                // 类结束
                if (leftBracketsNum == rightBracketsNum) {
                    leftBracketsNum = 0;
                    rightBracketsNum = 0;
                    if (!className.equals("") && !classContentString.toString().equals("")) {
                        analyzeClassContentString(className, classContentString.toString());
                    }
                    classContentString = new StringBuilder();
                } else {
                    classContentString.append(word).append(" ");
                }
            } else {
                if (word.contains("{")) {
                    for (int j = 0; j < word.length(); j++) {
                        if (word.charAt(j) == '{') {
                            innerLeftBracketsNum++;
                        }
                    }
                }

                if (word.contains("}")) {
                    for (int j = 0; j < word.length(); j++) {
                        if (word.charAt(j) == '}') {
                            innerRightBracketsNum++;
                        }
                    }
                }

                // 类结束
                if (innerLeftBracketsNum == innerRightBracketsNum) {
                    innerLeftBracketsNum = 0;
                    innerRightBracketsNum = 0;
                    hasInnerClass = false;
                    if (!className.equals("") && !innerClassContentString.toString().equals("")) {
                        analyzeClassContentString(innerClassName, innerClassContentString.toString());
                    }
                    innerClassContentString = new StringBuilder();
                } else {
                    innerClassContentString.append(word).append(" ");
                }
            }
        }

    }

    /**
     * 分析切割出的每一个类的内容字符串, 切割方法
     *
     * @param className          类名
     * @param classContentString 类的内容字符串
     * @author sunzesong
     */
    private static void analyzeClassContentString(String className, String classContentString) {
        MUTModel putMethod = new MUTModel();

        List<String> words = Arrays.asList(classContentString.split(" "));
        int bracketsNum = 0; // 未匹配的"{"个数
        StringBuilder resString = new StringBuilder(); // 处理后的字符串

        for (int i = 1; i < words.size(); i++) { // 忽略第一个"{"
            String word = words.get(i);
            if (word.contains("{")) {
                for (int j = 0; j < word.length(); j++) {
                    if (word.charAt(j) == '{') {
                        bracketsNum++;
                    }
                }
            }

            if (word.contains("}")) {
                for (int j = 0; j < word.length(); j++) {
                    if (word.charAt(j) == '}') {
                        bracketsNum--;
                    }
                }
            }

            if (bracketsNum == 0 && !word.equals("}")) {
                resString.append(word).append(" ");
            }

            if (word.contains(";")) {
                resString = new StringBuilder();
            } else if (word.contains(")") && !resString.toString().equals("")) {
                if (!resString.toString().contains("@")) {
                    analyzeMethodContentString(className, resString.toString());
                }
                resString = new StringBuilder();
            }
        }
//        return putMethod;
    }

    /**
     * 分析切割出的每一个方法的内容字符串, 保存到数据库
     *
     * @param className           类名
     * @param methodContentString 方法的内容字符串
     */
    private static void analyzeMethodContentString(String className, String methodContentString) {
        MUTModel putMethod = new MUTModel();
        putMethod.setClassName(className);
        putMethod.setAccess("");

        List<String> words = Arrays.asList(methodContentString.split(" "));
        List<String> arguments = new ArrayList<>();
        int judgeIndex = 0; // 判断类名前是否为返回值类型, 以判断是否为构造函数

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.equals("public") || word.equals("private") || word.equals("protected")) {
                putMethod.setAccess(word);
            } else if (word.equals("(")) {
                putMethod.setMethodName(words.get(i - 1));
                arguments.add(words.get(i + 1));
                judgeIndex = i - 2;
            } else if (word.endsWith("(")) {
                putMethod.setMethodName(word.split("\\(")[0]);
                arguments.add(words.get(i + 1));
                judgeIndex = i - 1;
            } else if (word.startsWith("(")) {
                putMethod.setMethodName(words.get(i - 1));
                judgeIndex = i - 2;
            } else if (word.contains("(") && !word.endsWith("(")) {
                putMethod.setMethodName(word.split("\\(")[0]);
                judgeIndex = i - 1;

                if (!word.split("\\(")[1].equals(")")) {
                    arguments.add(word.split("\\(")[1]);
                }
            } else if (word.equals(",") || word.endsWith(",")) {
                arguments.add(words.get(i + 1));
            } else if (word.contains(",") && !word.endsWith(",")) {
                arguments.add(word.split(",")[1]);
            }
        }

        StringBuilder argumentsWithComa = new StringBuilder();
        StringBuilder argumentsWithoutComa = new StringBuilder();

        for (String argument : arguments) {
            argumentsWithComa.append(argument).append(",");
            argumentsWithoutComa.append(argument);
        }

        if (argumentsWithComa.toString().length() == 0) {
            putMethod.setArguments("");
        } else {
            putMethod.setArguments(argumentsWithComa.toString().substring(0, argumentsWithComa.toString().length() - 1));
        }

        putMethod.setIsConstructor("");
        if (judgeIndex < 0 || words.get(judgeIndex).equals(putMethod.getAccess())
                || words.get(judgeIndex).equals("static")) {
            putMethod.setIsConstructor("constructor");
        }

        String midString = className + putMethod.getMethodName() + argumentsWithoutComa;
        putMethod.setMethodId(midString.hashCode());
        mutModelList.add(putMethod);
//        putMethodDao.saveMUTModel(putMethod);
//        return putMethod;
    }

    /**
     * @param PUTRootDirectory PUT根目录路径
     * @return FUT文件名列表
     * @date 2018/4/9 下午7:53
     */
    static List<String> getAllFUTNameFromPUT(File PUTRootDirectory) {
        File[] directories = PUTRootDirectory.listFiles();
        if (directories != null) {
            for (File directory : directories) {
                if (directory.isFile()) {
                    continue;
                }
                String directoryName = directory.getName();
                if ("src".equals(directoryName)) {
                    File[] files = directory.listFiles();
                    if (files != null && files.length > 0) {
                        List<String> fileNameList = new ArrayList<>();
                        for (File file : files) {
                            String fileName = file.getName();
                            if (".DS_Store".equals(fileName)) {
                                continue;
                            }
                            int lastPointIndex = fileName.lastIndexOf(".");
                            String fileExtension = fileName.substring(lastPointIndex + 1, fileName.length());
                            if ("java".equals(fileExtension)) {
                                fileNameList.add(fileName);
                            }
                        }
                        return fileNameList;
                    } else {
                        System.err.println("The directory \"" + directory.getAbsolutePath() + "\" is empty.");
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param PUTRootDirectory PUT根目录路径
     * @return FUT路径列表
     * @author sunzesong
     */
    private static List<File> getAllFUTPathFromPUT(File PUTRootDirectory) {
        File[] directories = PUTRootDirectory.listFiles();
        List<File> fileList = new ArrayList<>();
        if (directories != null) {
            for (File directory : directories) {
                if (directory.isFile()) {
                    continue;
                }
                String directoryName = directory.getName();
                if ("src".equals(directoryName)) {
                    File[] files = directory.listFiles();
                    if (files != null && files.length > 0) {
                        for (File file : files) {
                            if (file.getName().endsWith(".java")) {
                                fileList.add(file);
                            }
                        }
                    } else {
                        System.err.println("The directory \"" + directory.getAbsolutePath() + "\" is empty.");
                    }
                }
            }
        }
        return fileList;
    }

}
