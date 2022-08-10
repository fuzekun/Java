package chainOfResponsibility;


import javafx.collections.transformation.FilteredList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
*
*
*   实现方式2：链式添加
* */
public class FilterChainII {

    public static void main(String[] args) {
        Message msg = new Message("你好 <script>, 大家都喜欢ssm");

        FilterChain2 fc = new FilterChain2();
        fc.add(new HtmlFilter());
        fc.add(new SensitiveFilter());
        fc.doFilter(msg);
        System.out.println(msg.getMsg());
    }
}

class FilterChain2 {
    List<Filter> filters = new ArrayList<>();
    public void add(Filter f) {
        filters.add(f);
    }
    public void doFilter(Message s) {
        for (Filter f : filters) {
            f.doFilter(s);
        }
    }
}
