package com.egnyte.utils.auditreporter

import spock.lang.Specification

/**
 * Created by jsulaiman on 1/15/2016.
 */
class PrintStrategyTest extends Specification {

    List<User> users = [new User(1, 'jpublic'), new User(2, 'atester')]
    List<File> files = [new File('5974448e-9afd-4c9a-ac5a-9b1e84227820', 5372274, 'pic.jpg', 2),
                        new File('fab16fa4-8251-4394-a673-c961a65eb1d2', 1638232, 'audit.xlsx', 1)]


    def "Print using StandardReportPrintStrategy"() {

        when:
        def strategy = new StandardReportPrintStrategy()
        def list = strategy.print(users, files)

        then:
        list.size() == 6
        list.get(0).equals('Audit Report')
        list.get(1).equals('============')
        list.get(2).equals('## User: jpublic')
        list.get(3).equals('* audit.xlsx ==> 1638232 bytes')
        list.get(4).equals('## User: atester')
        list.get(5).equals('* pic.jpg ==> 5372274 bytes')
    }

    def "Print using CsvReportPrintStrategy"() {

        when:
        PrintStrategy strategy = new CsvReportPrintStrategy() {}
        def list = strategy.print(users, files)

        then:
        list.size() == 2
        list.get(0) == 'jpublic,audit.xlsx,1638232'
        list.get(1) == 'atester,pic.jpg,5372274'
    }

    def "Print using TopLargestFilesPrintStrategy"() {

        when:
        PrintStrategy strategy = new TopLargestFilesPrintStrategy(2)
        def list = strategy.print(users, files)

        then:
        list.size() == 4
        list.get(0) == 'Top #2 Report'
        list.get(1) == '============='
        list.get(2) == '* pic.jpg ==> user atester, 5372274 bytes'
        list.get(3) == '* audit.xlsx ==> user jpublic, 1638232 bytes'
    }

    def "Print using TopLargestFilesCsvPrintStrategy"() {

        when:
        PrintStrategy strategy = new TopLargestFilesCsvPrintStrategy(2)
        def list = strategy.print(users, files)

        then:
        list.size() == 2
        list.get(0) == 'pic.jpg,atester,5372274'
        list.get(1) == 'audit.xlsx,jpublic,1638232'
    }

}
