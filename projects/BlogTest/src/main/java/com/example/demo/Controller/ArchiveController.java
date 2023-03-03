package com.example.demo.Controller;
import com.example.demo.Dao.ArchiveDao;
import com.example.demo.Service.JedisService;
import com.example.demo.Utils.RedisKeyUntil;
import com.example.demo.model.Archive;
import com.example.demo.model.ViewObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tuzhenyu on 17-8-18.
 * @author tuzhenyu
 */
@Controller
@Api(description = "热门文章接口")
public class ArchiveController {
    @Autowired
    private ArchiveDao archiveDao;

    @Autowired
    private JedisService jedisService;

    @ApiOperation("文章成就")
    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public String archive(Model model){

        //根据文章的时间进行
        List<Archive> archives = archiveDao.seletArticleGroupByTime();
        Map<String,List<Archive>> map = new HashMap<>();
        for (Archive archive : archives){
            String date = archive.getYear()+"-"+archive.getMonth();
            List<Archive> list = map.getOrDefault(date,new ArrayList<>());
            list.add(archive);
            map.put(date,list);
        }
        List<ViewObject> vos = new ArrayList<>();
        for (Map.Entry entry:map.entrySet()){
            ViewObject vo = new ViewObject();
            vo.set("date",entry.getKey());
            vo.set("list",entry.getValue());
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        //传递页面信息
        ViewObject pagination = new ViewObject();
        int count = archives.size();
        pagination.set("current",1);
        pagination.set("nextPage",2);
        pagination.set("lastPage",count % 4 == 0 ? count / 4 : count/4+1);
        model.addAttribute("pagination",pagination);
        //返回页脚信息
        ViewObject clickCount = new ViewObject();
        String currentPage = jedisService.get(RedisKeyUntil.getClickCountKey("/archive"));
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clickCount.set("currentPage",currentPage);
        clickCount.set("sumPage",sumPage);
        model.addAttribute("clickCount",clickCount);

        return "archive";
    }
}
