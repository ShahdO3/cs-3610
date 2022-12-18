import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class dateComparator implements Comparator<StockDaily> {
    @Override
    public int compare(StockDaily o1, StockDaily o2) {
        try {
            Date date1 =new SimpleDateFormat("MM/dd/yyyy").parse(o1.getDate());
            Date date2 =new SimpleDateFormat("MM/dd/yyyy").parse(o2.getDate());
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

