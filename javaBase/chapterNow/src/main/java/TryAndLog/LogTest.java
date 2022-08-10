package TryAndLog;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.*;

public class LogTest {
    // 创建一个默认记录器
    private static final Logger myLogger = Logger.getLogger("com.fuzekun.myLogger");
    @Test
    public void test1() {
        myLogger.info("第一个日志信息");
        Logger.getGlobal().info("File->Open menu item selected");
        Logger.getGlobal().setLevel(Level.OFF);
        Logger.getGlobal().warning("File is not exits");
    }

    @Test
    public void test2() {
        if (true) {
            myLogger.throwing("类名", "方法名", new Exception("没错的错误"));
            myLogger.log(Level.FINE, "读取", new Exception("没有错误的错误"));

        }
    }

    @Test
    public void test3() {
        /*
        *
        *   设置自己的处理器
        *   不用父亲的，就是setParentHadlers(false)
        *   否则就会出现两次信息，因为会把大于FINE的交给你亲处理
        * */

        Logger logger = Logger.getLogger("com.mycom.myapp");

        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false); //
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);

        logger.log(Level.WARNING, "这是一个新的logger");


    }

    @Test
    public void test4() throws IOException {
        FileHandler h = new FileHandler("log.txt", true);
        Logger logger = Logger.getLogger("com.mycom.myapp");
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false); //
        h.setLevel(Level.INFO);
        logger.addHandler(h);

        logger.log(Level.WARNING, "这是一个新的logger, 被记录到文件中");


    }

    @Test
    public void test5() throws IOException, FileNotFoundException{
        /*
        *
        *   测试格式化器
        * */
        FileHandler h = new FileHandler("log.txt", true);
        Formatter formatter = new Formatter() {
            @Override
            public String format(LogRecord record) {
                StringBuilder res = new StringBuilder(new SimpleFormatter().format(record));
                res.append("\n不管输出什么，都得有我的身影\n");
                return res.toString();
            }
        };
        h.setFormatter(formatter);

        Logger logger = Logger.getLogger("com.mycom.myapp");
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false); //
        h.setLevel(Level.INFO);
        logger.addHandler(h);

        logger.log(Level.WARNING, "这是一个被格式化的formatter");


    }
}
