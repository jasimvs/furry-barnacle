package com.egnyte.utils.auditreporter

import spock.lang.Specification

/**
 * Created by jsulaiman on 1/15/2016.
 */
class UtilTest extends Specification {

    def "Verify sorting of file objects"() {

        when:
        List<File> files = new ArrayList<>()
        //  String fileId, long size, String fileName, long ownerUserId
        def file1 = new File("456778", 2345657, "a.txt", 123456)
        def file2 = new File("456779", 1345657, "b.txt", 123456)
        def file3 = new File("456780", 3345657, "c.txt", 123456)

        files.add(file1)
        files.add(file2)
        files.add(file3)

        def sortedList = Util.getSortedFilesList(files)

        then:
        sortedList.get(0) == file3
        sortedList.get(1) == file1
        sortedList.get(2) == file2
    }
}
