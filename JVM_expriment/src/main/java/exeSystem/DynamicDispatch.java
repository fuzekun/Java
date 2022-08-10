package exeSystem;


public class DynamicDispatch {
    static abstract class Human {
        protected abstract void sayHello () ;
    }

    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("man say Hello");
        }
    }

    static class Women extends Human {
        @Override
        protected void sayHello() {
            System.out.println("women say Hello");
        }
    }

    public static void main(String[] args) {
        Human men = new Man();
        Human woman = new Women();

        woman.sayHello();
        men.sayHello();

        men = new Women();
        men.sayHello();
    }
}
