package exeSystem;

/*
*
*
*   字段没有多态性， 这个可以由father和son的调用进行验证。
* */
public class FieldHasNoPolymorphic {
    static class Father {
        public int money = 1;

        public Father() {
            money = 2;
            showMeTheMoney();
        }

        public void showMeTheMoney() {
            System.out.println("I am Father, I have $" + money);
        }
    }

    static class Son extends Father {
        public int money = 3;
        public Son() {

        }
    }
}
