import java.util.Comparator;

public class stockDailyComparator implements Comparator<StockDaily> {
    @Override
    public int compare(StockDaily o1, StockDaily o2) {
        return Double.compare(o1.value, o2.value);
    }
}
