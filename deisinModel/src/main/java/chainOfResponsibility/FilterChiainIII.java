package chainOfResponsibility;


import java.util.ArrayList;
import java.util.List;

/*
*
*   可以一个链条3，加上另外一个链条
* */
public class FilterChiainIII {
    public static void main(String[] args) {
        Message msg = new Message("你好 <script>, 大家都喜欢ssm");

        FilterChain3 fc = new FilterChain3();
        fc.add(new HtmlFilter());
        FilterChain3 fc2 = new FilterChain3();
        // 先进性敏感词的过滤，在进行html的过滤,也可以反过来。
        fc2.add(new SensitiveFilter()).add(fc);
        fc.doFilter(msg);
        System.out.println(msg.getMsg());
    }
}


class FilterChain3 implements Filter{
    List<Filter> filters;
    FilterChain3() {
        filters = new ArrayList<>();
    }


    public FilterChain3 add(Filter filter) {
        filters.add(filter);
        return this;
    }
    @Override
    public void doFilter(Message s) {
        for (Filter f: filters) {
            f.doFilter(s);
        }
    }
}

