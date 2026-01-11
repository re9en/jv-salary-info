package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SalaryInfo {

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {

        Map<String, Integer> salaryPerHour = getSalaryPerHour(names,
                getDataSorted(data,
                        dateFrom,dateTo));

        System.out.println(("Report for period "
                + dateFrom
                + " - "
                + dateTo));

        for (String name : names) {
            Integer salary = salaryPerHour.get(name.trim());
            if (salary != null) {
                System.out.println(name.trim()
                        + " - "
                        + salary);
            } else {
                System.out.println(name.trim()
                        + " - "
                        + 0);
            }
        }
        return "";
    }

    public String[] getDataSorted(String[] data, String dateFrom, String dateTo) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

    public Map<String, Integer> getSalaryPerHour(String[] names, String[] getDataSorted) {
        Set<String> requiredNames = new HashSet<>();
        for (String name : names) {
            requiredNames.add(name.trim());
        }

        // ГРОШІ ТА ГОДИНИ СПІВРОБІТНИКІВ
        Map<String, Integer> totalMoney = new HashMap<>();
        Map<String, Integer> totalHours = new HashMap<>();

        for (String record : getDataSorted) {
            String[] parts = record.trim().split(" ");
            String empName = parts[1];

            if (!requiredNames.contains(empName)) {
                continue;
            }

            int hours = Integer.parseInt(parts[2]);
            int money = Integer.parseInt(parts[3]);

            totalMoney.put(empName, totalMoney.getOrDefault(empName, 0) + money);
            totalHours.put(empName, totalHours.getOrDefault(empName, 0) + hours);
        }

        // РАХУВАННЯ ЗАРОБІТКУ
        Map<String, Integer> salaryPerHour = new HashMap<>();
        for (String name : requiredNames) {
            if (totalHours.containsKey(name)) {
                int money = totalMoney.get(name);
                int hours = totalHours.get(name);
                salaryPerHour.put(name, money / hours);
            }
        }

        return salaryPerHour;
    }
}


