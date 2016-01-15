package com.egnyte.utils.auditreporter;

import javafx.collections.transformation.SortedList;

import java.util.*;

/**
 * Created by jsulaiman on 1/14/2016.
 */
public interface PrintStrategy {
    List<String> print(List<User> users, List<File> files);
}

class StandardReportPrintStrategy implements PrintStrategy {
    @Override
    public List<String> print(List<User> users, List<File> files) {
        List<String> list = new ArrayList<>();
        list.addAll(printHeader());
        for (User user : users) {
            list.addAll(printUserHeader(user.userName));
            for (File file : files) {
                if (file.ownerUserId == user.userId) {
                    list.addAll(printFile(file.fileName, file.size));
                }
            }
        }
        return list;
    }

    private List<String> printHeader() {
        List<String> list = new ArrayList<>();
        list.add("Audit Report");
        list.add("============");
        return list;
    }

    private List<String> printUserHeader(String userName) {
        List<String> list = new ArrayList<>();
        list.add("## User: " + userName);
        return list;
    }

    private List<String> printFile(String fileName, long fileSize) {
        List<String> list = new ArrayList<>();
        list.add("* " + fileName + " ==> " + fileSize + " bytes");
        return list;
    }
}

class CsvReportPrintStrategy implements PrintStrategy {
    @Override
    public List<String> print(List<User> users, List<File> files) {
        List<String> list = new ArrayList<>();
        for (User user : users) {
            for (File file : files) {
                if (file.ownerUserId == user.userId) {
                    list.add(user.userName + "," + file.fileName + "," + file.size);
                }
            }
        }
        return list;
    }
}

class TopLargestFilesPrintStrategy implements PrintStrategy {

    private int numberOfFilesToPrint;

    TopLargestFilesPrintStrategy(int numberOfFilesToPrint) {
        this.numberOfFilesToPrint = numberOfFilesToPrint;
    }

    @Override
    public List<String> print(List<User> users, List<File> files) {
        List<String> list = new ArrayList<>();
        list.addAll(printHeader());
        List<File> sortedList = Util.getSortedFilesList(files);
        for (int i = 0; i < numberOfFilesToPrint; i++) {
            File file = sortedList.get(i);
            String username = "";
            for (User user: users) {
                if (user.userId == file.ownerUserId) {
                    username = user.userName;
                    break;
                }
            }
            list.add("* " + file.fileName + " ==> user " + username + ", " + file.size + " bytes");
        }
        return list;
    }

    private List<String> printHeader() {
        List<String> list = new ArrayList<>();
        list.add("Top #" + numberOfFilesToPrint + " Report");
        list.add("=============");
        return list;
    }

}

class TopLargestFilesCsvPrintStrategy implements PrintStrategy {

    private int numberOfFilesToPrint;

    TopLargestFilesCsvPrintStrategy(int numberOfFilesToPrint) {
        this.numberOfFilesToPrint = numberOfFilesToPrint;
    }

    @Override
    public List<String> print(List<User> users, List<File> files) {
        List<String> list = new ArrayList<>();
        List<File> sortedList = Util.getSortedFilesList(files);
        for (int i = 0; i < numberOfFilesToPrint; i++) {
            File file = sortedList.get(i);
            String username = "";
            for (User user: users) {
                if (user.userId == file.ownerUserId) {
                    username = user.userName;
                    break;
                }
            }
            list.add(file.fileName + "," + username + "," + file.size);
        }
        return list;
    }
}