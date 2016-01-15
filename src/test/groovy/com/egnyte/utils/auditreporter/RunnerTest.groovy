package com.egnyte.utils.auditreporter

import spock.lang.Specification

/**
 * Created by jsulaiman on 1/15/2016.
 */
class RunnerTest extends Specification {

    def "Both -c and --top are parsed by parseOptionalArgs" () {
        when:
        def runner = new Runner()
        String[] args = ["","", "-c", "--top", "5"]
        runner.parseOptionalArgs(args)

        then:
        runner.printStrategy instanceof TopLargestFilesCsvPrintStrategy
        ((TopLargestFilesCsvPrintStrategy)runner.printStrategy).numberOfFilesToPrint == 5
    }

    def "Both -c and --top are parsed in different order by parseOptionalArgs" () {
        when:
        def runner = new Runner()
        String[] args = ["","", "--top", "5", "-c"]
        runner.parseOptionalArgs(args)

        then:
        runner.printStrategy instanceof TopLargestFilesCsvPrintStrategy
        ((TopLargestFilesCsvPrintStrategy)runner.printStrategy).numberOfFilesToPrint == 5
    }

    def "--top are parsed by parseOptionalArgs" () {
        when:
        def runner = new Runner()
        String[] args = ["","", "--top", "5"]
        runner.parseOptionalArgs(args)

        then:
        runner.printStrategy instanceof TopLargestFilesPrintStrategy
        ((TopLargestFilesPrintStrategy)runner.printStrategy).numberOfFilesToPrint == 5
    }

    def "-c is parsed by parseOptionalArgs" () {
        when:
        def runner = new Runner()
        String[] args = ["","", "-c", "5"]
        runner.parseOptionalArgs(args)

        then:
        runner.printStrategy instanceof CsvReportPrintStrategy
    }

    def "No extra args is parsed by parseOptionalArgs" () {
        when:
        def runner = new Runner()
        String[] args = ["","", "5"]
        runner.parseOptionalArgs(args)

        then:
        runner.printStrategy instanceof StandardReportPrintStrategy
    }

    def "Test parseFile "() {
        def runner = new Runner();
        runner.parseFile("5974448e-9afd-4c9a-ac5a-9b1e84227820,5372274,pic.jpg,2")
    }

    def "Test parseUser "() {
        def runner = new Runner();
        runner.parseUser("1,jpublic")
    }
}
