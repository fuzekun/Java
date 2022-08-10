package component;

/**
 * @author: Zekun Fu
 * @date: 2022/7/30 15:52
 * @Description:
 */
public interface Company {

    public void add(Company cm);
    public void remove(Company c);
    public void display(Company c);
    public void LineOfDuty();           // 旅行职责
}
