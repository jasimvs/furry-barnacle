package com.egnyte.utils.auditreporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jsulaiman on 1/15/2016.
 */
public class Util {

    public static List<File> getSortedFilesList(List<File> files) {
        List<File> sortedList = new ArrayList<>(files);
        Collections.sort(sortedList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Long.compare(o2.size, o1.size);
            }
        });
        return sortedList;
    }
}
