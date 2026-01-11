package core.basesyntax;

public class Main {
    public static void main(String[] names, String[] data, String dateFrom, String dateTo) {

        SalaryInfo salaryInfo = new SalaryInfo();

        salaryInfo.getSalaryInfo(names, data, dateFrom, dateTo);

        salaryInfo.getDataSorted(data, dateFrom, dateTo);

    }
}
