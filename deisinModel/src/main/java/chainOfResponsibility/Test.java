package chainOfResponsibility;

import java.util.LinkedList;
import java.util.Iterator;

public class Test {

    public static void main(String[] args) {
        LinkedList<String>s = new LinkedList<>();
        s.add("23");
        s.add("324");
        Iterator<String> it = s.iterator();
        while(it.hasNext()) {
            it.forEachRemaining((e)->{
                System.out.println("可以从里面输出值了：" + e);
            });
        }
    }
}
