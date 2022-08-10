package compiler.JIT;
/*
*
*   参数:-XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:+PrintInlining
*
*   -XX:+PrintAssembly -XX:+UnlockDiagnosticVMOptions. 不能使用
*
*   -XX:+PrintIdealGraphFile
* */
public class testJIT {
    private static final int NUM = 15000;
    public static int doubleValue(int i) {
        for (int j = 0; j < 100000; j++);
        return i *= 2;
    }

    public static long calSum() {
        long sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM;i++) {
            calSum();
        }
    }
}
