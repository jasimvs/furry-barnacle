package com.egnyte.utils.auditreporter;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private List<User> users = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    private PrintStrategy printStrategy = new StandardReportPrintStrategy();

    public static void main(String[] args) throws IOException {
        Runner r = new Runner();
        r.loadData(args[0], args[1]);
        r.parseOptionalArgs(args);
        r.run();
    }

    private void setPrintStrategy(PrintStrategy printStrategy) {
        this.printStrategy = printStrategy;
    }

    private void parseOptionalArgs(String[] args) {
        if (args.length > 2) {
            int top = 0;
            boolean isCsv = false;
            for (int i = 2; i < args.length; i++) {
                switch (args[i]) {
                    case "-c":
                        isCsv = true;
                        break;
                    case "--top":
                        if (i + 1 < args.length) {
                            try {
                                top = Integer.parseInt(args[i + 1]);
                            } catch (NumberFormatException nfe) {
                              // TODO log unable to parse top's arg
                            }
                        }
                        break;
                }
            }
            decidePrintStrategy(top, isCsv);
        }
    }

    private void decidePrintStrategy(int top, boolean isCsv) {
        if (isCsv) {
            if (top > 0) {
                setPrintStrategy(new TopLargestFilesCsvPrintStrategy(top));
            } else {
                setPrintStrategy(new CsvReportPrintStrategy());
            }
        } else {
            if (top > 0) {
                setPrintStrategy(new TopLargestFilesPrintStrategy(top));
            } else {
                setPrintStrategy(new StandardReportPrintStrategy());
            }
        }
    }

    private void loadData(String userFn, String filesFn) throws IOException {
        String line;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(userFn));
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                parseUser(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        reader = null;
        try {
            reader = new BufferedReader(new FileReader(filesFn));
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                parseFile(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void parseFile(String line) {
        try {
            String[] fileRow = line.split(",");
            String fileId = fileRow[0];
            long size = Long.parseLong(fileRow[1]);
            String fileName = fileRow[2];
            long ownerUserId = Long.parseLong(fileRow[3]);
            files.add(new File(fileId, size, fileName, ownerUserId));
        } catch (NumberFormatException nfe) {
            // TODO log cannot parse file on line
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // TODO log cannot parse file on line
        }
    }

    private void parseUser(String line) {
        try {
            String[] userRow = line.split(",");
            long userId = Long.parseLong(userRow[0]);
            String userName = userRow[1];
            users.add(new User(userId, userName));
        } catch (NumberFormatException nfe) {
            // TODO log cannot parse user on line
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // TODO log cannot parse user on line
        }
    }

    private void run() {
        List<String> list = printStrategy.print(users, files);
        for (String string: list) {
            System.out.println(string);
        }
    }

}
