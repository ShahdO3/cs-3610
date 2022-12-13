/**
 * @author Seif El-Bendary, Shahd Osman
 */
public class StockDaily {
    String date;
    double value;

    public StockDaily(String date,double value) {
        this.date = date;
        this.value= Math.round(value);
    }
    public StockDaily(double value) {

        this.value=value;
    }

    @Override
    public String toString() {
        return "StockDaily{" +
                "date='" + date + '\'' +
                ", value=" + value +
                '}';
    }
}
