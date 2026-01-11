package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalaryInfo {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {

        String[] salary = getSalary(names,
                getDataSorted(data,
                        dateFrom,dateTo));

        StringBuilder result = new StringBuilder();

        result.append(("Report for period "
                + dateFrom
                + " - "
                + dateTo));

        for (String line: salary) {
            result.append(System.lineSeparator()).append(line);
        }

        return result.toString();
    }

    public String[] getDataSorted(String[] data, String dateFrom, String dateTo) {

        LocalDate from = LocalDate.parse(dateFrom.trim(),formatter);
        LocalDate to = LocalDate.parse(dateTo.trim(),formatter);

        List<String> result = new ArrayList<>();

        for (String record : data) {
            LocalDate recordDate =
                    LocalDate.parse(record.trim().split(" ")[0],formatter);

            if (!recordDate.isBefore(from) && !recordDate.isAfter(to)) {
                result.add(record);
            }
        }
        return result.toArray(new String[0]);
    }
    // МЕТОД ПОВЕРТАЄ ВІДСОРТОВАННИЙ ПО ДАТІ СПИСОК

    public String[] getSalary(String[] names, String[] getDataSorted) {
        List<String> salary = new ArrayList<>();

        for (String name : names) {
            String trimmedName = name.trim();
            int totalMoney = 0;

            for (String record : getDataSorted) {
                String[] parts = record.trim().split(" ");
                String empName = parts[1];
                int money = Integer.parseInt(parts[3]);
                int hours = Integer.parseInt(parts[2]);

                if (empName.equals(trimmedName)) {
                    totalMoney += money * hours;
                }
            }

            salary.add(trimmedName + " - " + totalMoney);
        }

        return salary.toArray(new String[0]);
    }
}


