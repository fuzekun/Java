package otherTest;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 19:21
 * @Description:
 */
//@SpringBootTest(classes = {SpringBootDemoApplication.class})
//@RunWith(SpringRunner.class)
public class PathTest {

    @Test
    public void test() throws Exception{
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 注意resources和resource的区别。
        Resource[] resources = resolver.getResources("classpath:/mapper/bookStore/*.xml");
        System.out.println(resources.length);
        System.out.println(resources[0].getDescription());
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(resources[0].getFile())));
        String s;
        while ((s = in.readLine()) != null) {
            System.out.println(s);
        }
    }
}
