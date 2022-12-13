import java.util.Objects;

/**
 * @author Seif El-Bendary, Shahd Osman
 */

public class Interval {
    StockDaily start, end;

    public Interval(StockDaily s, StockDaily e){
        start = s; end = e;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start.toString() +
                ", end=" + end.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return Objects.equals(start, interval.start) && Objects.equals(end, interval.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
