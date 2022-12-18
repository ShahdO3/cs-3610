import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DuplicateComparator implements Comparator<StockDaily>{

    @Override
    public int compare(StockDaily x, StockDaily y) {
        return Double.compare(x.getValue(),y.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockDaily)) return false;
        StockDaily s = (StockDaily) o;
        return Objects.equals(((StockDaily) o).value, s.value) ;

    }
}

