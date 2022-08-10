package geneticParadigm;





import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PairTest3 {


    private static Integer a1;
    class  Employ {
        String name;
    }
    class Employee extends Employ{

    }
    class Manager extends Employ{

    }

     private static class MyPair{
        private Employ first;
        private Employ second;
        public MyPair() {

        }

        public Employ getFirst() {
            return first;
        }

        public void setSecond(Employ second) {
            this.second = second;
        }

        public Employ getSecond() {
            return second;
        }

        public void setFirst(Employ first) {
            this.first = first;
        }
    }
    class Epair extends MyPair {

        public void setFirst(Manager first) {   // 并不是重写了这个方法
            super.setFirst(first);
        }
    }

    public static void main(String[] args) {
        MyPair myPair = new MyPair();
        a1++;
        ArrayList<Integer>list = new ArrayList<>();
    }
}

