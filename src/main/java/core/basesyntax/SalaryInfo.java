package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalaryInfo {

    private static final int DATE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int MONEY_INDEX = 3;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        String[] salary = getSalary(names, data, dateFrom, dateTo);

        StringBuilder result = new StringBuilder();
        result.append("Report for period ").append(dateFrom)
                .append(" - ").append(dateTo);

        for (String line : salary) {
            result.append(System.lineSeparator()).append(line);
        }

        return result.toString();
    }

    public String[] getSalary(String[] names, String[] data, String dateFrom, String dateTo) {
        LocalDate from = LocalDate.parse(dateFrom.trim(), formatter);
        LocalDate to = LocalDate.parse(dateTo.trim(), formatter);

        List<String> salary = new ArrayList<>();

        for (String name : names) {
            String trimmedName = name.trim();
            int totalMoney = 0;

            for (String record : data) {
                String[] parts = record.trim().split(" ");
                LocalDate empDate = LocalDate.parse(parts[DATE_INDEX], formatter);
                String empName = parts[NAME_INDEX];

                if (empName.equals(trimmedName)
                        && !empDate.isBefore(from)
                        && !empDate.isAfter(to)) {
                    int hours = Integer.parseInt(parts[HOURS_INDEX]);
                    int rate = Integer.parseInt(parts[MONEY_INDEX]);
                    totalMoney += hours * rate;
                }
            }

            salary.add(trimmedName + " - " + totalMoney);
        }

        return salary.toArray(new String[0]);
    }
}
