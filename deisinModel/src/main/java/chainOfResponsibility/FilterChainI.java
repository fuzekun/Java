package chainOfResponsibility;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/*
*
* 实现方式0：在主类中进行过滤。
* 实现方式1：抽象出来一个过滤器，进行过滤，可以在某个时刻停止
*
*   filterChain的实现方式1，直接普通的filterChain
*   实现方式2，进行链式编程
*   实现方式3: 可以把链条加进来
*   实现方式5：sevlet中的过滤，递归的过程
* */
public class FilterChainI {

    public static void main(String[] args) {
        Message msg = new Message("你好 <script>, 大家都喜欢ssm");

        FilterChain fc = new FilterChain();
        fc.add(new HtmlFilter()).add(new SensitiveFilter());
        fc.doFilter(msg);
        System.out.println(msg.getMsg());
    }

}

class Message  {
    String msg;
    Message(String s) {
        this.msg = s;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

interface Filter {
    public void doFilter(Message s);
}

class  HtmlFilter implements Filter{
    @Override
    public void doFilter(Message s) {
        String t = s.getMsg().replaceAll("<", "[");
        t = t.replaceAll(">", "]");
        s.setMsg(t);
    }
}

class SensitiveFilter implements Filter {
    @Override
    public void doFilter(Message s) {
        String t = s.getMsg().replaceAll("ssm", "happy");
        s.setMsg(t);
    }
}

class FilterChain {
    List<Filter> filters;
    FilterChain() {
        filters = new ArrayList<>();
    }


    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }
    public void doFilter(Message s) {
        for (Filter f: filters) {
            f.doFilter(s);
        }
    }
}
