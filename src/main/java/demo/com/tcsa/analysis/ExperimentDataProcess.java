package demo.com.tcsa.analysis;

import demo.com.tcsa.common.Constant;
import demo.com.tcsa.dao.MUTModelDao;
import demo.com.tcsa.daoImpl.MUTModelDaoImpl;
import demo.com.tcsa.model.*;
import demo.com.tcsa.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  * @Date 2018/7/30 下午10:23
  */
public class ExperimentDataProcess {

    private static List<String> FUTNameList;

    private static List<MUTModel> mutModelList;

    private static MUTModelDao mutModelDao = MUTModelDaoImpl.getInstance();

    public static void analyze(String rootPath) {

        //get all MUT from mysql database;
        //mutModelList = mutModelDao.getMUTModelList();

        File rootDirectory = new File(rootPath);
        if (!rootDirectory.exists()) {
            System.err.println("The root directory does not exist.");
            return;
        }
        List<File> directories = FileUtil.traverseRootDirectory(rootDirectory, "junit");
        for (File directory : directories) {
            traverseJUnitDirectory(directory);
        }
    }

    /**
     * 遍历junit目录
     *
     * @param junitDirectory
     * @date 2018/4/4 上午10:21
     * @author sunweisong
     */
    private static void traverseJUnitDirectory(File junitDirectory) {
        File[] subjectDirectories = junitDirectory.listFiles();
        if (subjectDirectories != null && subjectDirectories.length > 0) {
            StringBuffer buffer = null;
            for (File subjectDirectory : subjectDirectories) {
                String subjectName = subjectDirectory.getName();
                if (".DS_Store".equals(subjectName)) {
                    continue;
                }
                String subjectDirectoryAbsolutePath = subjectDirectory.getAbsolutePath();
                buffer = new StringBuffer(subjectDirectoryAbsolutePath);

                int junitStringIndex = buffer.indexOf("junit");
                buffer.replace(junitStringIndex, junitStringIndex + 5, "subject");
                String PUTRootDirectoryAbsolutePath = buffer.toString();
                File PUTRootDirectory = new File(PUTRootDirectoryAbsolutePath);
                if (!PUTRootDirectory.exists()) {
                    System.err.println("The path \"" + PUTRootDirectoryAbsolutePath + "\" doesn't exists!");
                    return;
                }

                FUTNameList = PUTAnalysis.getAllFUTNameFromPUT(PUTRootDirectory);
                int subjectStringIndex = buffer.indexOf("subject");
                buffer.replace(junitStringIndex, subjectStringIndex + 7, "junit");
                int subjectIndex = buffer.indexOf(subjectName);
                buffer.replace(subjectIndex, subjectIndex + subjectName.length(), subjectName + "_clean");
                String targetDirectoryPath = buffer.toString();
                traverseSubjectDirectory(subjectDirectory, targetDirectoryPath);
                buffer = null;
            }
        } else {
            System.err.println("The directory \"" + junitDirectory.getAbsolutePath() + "\" is empty.");
            return;
        }
    }

    private static void traverseSubjectDirectory(File subjectDirectory
            , String targetDirectory) {
        StringBuffer targetPathBuffer = new StringBuffer(targetDirectory);
        File[] contestantIdDirectories = subjectDirectory.listFiles();
        if (contestantIdDirectories != null && contestantIdDirectories.length > 0) {
            for (File contestantIdDirectory : contestantIdDirectories) {
                String contestantId = contestantIdDirectory.getName();
                if (".DS_Store".equals(contestantId)) {
                    continue;
                }
                String tempCidDirectory = File.separator + contestantId + File.separator;
                targetPathBuffer.append(tempCidDirectory);
                traverseContestantIdDirectory(contestantIdDirectory, targetPathBuffer.toString());
                int start = targetPathBuffer.indexOf(tempCidDirectory);
                targetPathBuffer.replace(start, targetPathBuffer.length(), "");
            }
        } else {
            System.err.println("The directory \"" + subjectDirectory.getAbsolutePath() + "\" is empty.");
        }
        targetPathBuffer = null;
    }

    private static void traverseContestantIdDirectory(File contestantIdDirectory
            , String targetFilePath) {
       File[] submitDirectories = contestantIdDirectory.listFiles();
        if (submitDirectories != null && submitDirectories.length > 0) {
            for (File submitDirectory : submitDirectories) {
                if (submitDirectory.isFile()) {
                    continue;
                }
                String submitDirectoryName = submitDirectory.getName();
                if (".DS_Store".equals(submitDirectoryName)) {
                    continue;
                }
                traverseSubmitDirectory(submitDirectory, targetFilePath);
            }
        } else {
            System.err.println("The directory \"" + contestantIdDirectory.getAbsolutePath() + "\" is empty.");
        }
    }

    private static void traverseSubmitDirectory(File submitDirectory
            , String targetFilePath) {
        File[] directories = submitDirectory.listFiles();
        if (directories != null && directories.length > 0) {
            boolean noResultDirectory = false;
            for (File directory : directories) {
                String directoryName = directory.getName();
                if (".DS_Store".equals(directoryName)) {
                    continue;
                }
                if ("bin".equals(directoryName)) {
                    noResultDirectory = true;
                }
            }
            if (noResultDirectory) {
                System.err.println("There is no result***(execute) directory!");
                traverseExecuteDirectory(submitDirectory, targetFilePath);  // Notice:(parameter:submitDirectory)
            } else {
                for (File directory : directories) {
                    if (directory.isFile()) {
                        continue;
                    }
                    String directoryName = directory.getName();
                    if (".DS_Store".equals(directoryName)) {
                        continue;
                    }
                    if (directoryName.contains("result")) {
                        traverseExecuteDirectory(directory, targetFilePath);
                        break;
                    }
                }
            }
        } else {
            System.err.println("The directory \"" + submitDirectory.getAbsolutePath() + "\" is empty.");
        }
    }

    private static void traverseExecuteDirectory(File executeDirectory
            , String targetFilePath) {
        File[] directories = executeDirectory.listFiles();
        if (directories != null && directories.length > 0) {
            for (File directory : directories) {
                if (directory.isFile()) {
                    continue;
                }
                String directoryName = directory.getName();
                if (".DS_Store".equals(directoryName)) {
                    continue;
                }
                if (Constant.JUnit_Directory_List.contains(directoryName)) {
                    traverseTestFileDirectory(directory, targetFilePath);
                }
            }
        } else {
            System.err.println("The directory \"" + executeDirectory.getAbsolutePath() + "\" is empty.");
        }
    }

    private static void traverseTestFileDirectory(File testFileDirectory
            , String targetFilePath) {
        File targetDirectory = new File(targetFilePath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        targetDirectory = null;
        StringBuffer targetPathBuffer = new StringBuffer(targetFilePath);
        File[] testFiles = testFileDirectory.listFiles();
        if (testFiles != null && testFiles.length > 0) {
            String testFileDirectoryName = testFileDirectory.getName();
            if ("src".equals(testFileDirectoryName)) {
                for (File testFile : testFiles) {
                    if (testFile.isDirectory()) {
                        System.out.println(testFile.getAbsolutePath());
                        continue;
                    }
                    String testFileName = testFile.getName();
                    if (".DS_Store".equals(testFileName)
                            || FUTNameList.contains(testFileName)) {
                        continue;
                    }
//                    if (testFile.isDirectory()) {
//                        traverseTestFileDirectory(testFile, targetFilePath);
//                    } else {
//                        targetPathBuffer.append(testFileName);
//                        String targetAbsoluteFilePath = targetPathBuffer.toString();
//                        FileUtil.copyFileToTargetFilePath(testFile.getAbsolutePath(), targetAbsoluteFilePath);
//                        int fileNameIndex = targetPathBuffer.indexOf(testFileName);
//                        targetPathBuffer.replace(fileNameIndex, fileNameIndex + testFileName.length(), "");
//                    }
                    targetPathBuffer.append(testFileName);
                    String targetAbsoluteFilePath = targetPathBuffer.toString();
                    FileUtil.copyFileToTargetFilePath(testFile.getAbsolutePath(), targetAbsoluteFilePath);
                    int fileNameIndex = targetPathBuffer.indexOf(testFileName);
                    targetPathBuffer.replace(fileNameIndex, targetPathBuffer.length(), "");

                }
            } else {
                for (File testFile : testFiles) {
                    String testFileName = testFile.getName();
                    if (".DS_Store".equals(testFileName)) {
                        continue;
                    }
//                    if (testFile.isDirectory()) {
//                        traverseTestFileDirectory(testFile, targetFilePath);
//                    } else {
//                        targetPathBuffer.append(testFileName);
//                        String targetAbsoluteFilePath = targetPathBuffer.toString();
//                        FileUtil.copyFileToTargetFilePath(testFile.getAbsolutePath(), targetAbsoluteFilePath);
//                        int fileNameIndex = targetPathBuffer.indexOf(testFileName);
//                        targetPathBuffer.replace(fileNameIndex, fileNameIndex + testFileName.length(), "");
//                    }
                    targetPathBuffer.append(testFileName);
                    String targetAbsoluteFilePath = targetPathBuffer.toString();
                    FileUtil.copyFileToTargetFilePath(testFile.getAbsolutePath(), targetAbsoluteFilePath);
                    int fileNameIndex = targetPathBuffer.indexOf(testFileName);
                    targetPathBuffer.replace(fileNameIndex, targetPathBuffer.length(), "");

                }
            }
        } else {
            System.err.println("The directory \"" + testFileDirectory.getAbsolutePath() + "\" is empty.");
        }
        targetPathBuffer = null;
    }

}
