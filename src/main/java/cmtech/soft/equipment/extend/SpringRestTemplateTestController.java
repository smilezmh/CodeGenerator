package cmtech.soft.equipment.extend;

import cmtech.soft.equipment.utils.HttpResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;

import java.util.HashMap;

@Api("测试调用第三方接口")
@RestController
@RequestMapping("/extend/SpringRestTemplate")
public class SpringRestTemplateTestController {
    @Autowired
    @Qualifier(value="apiTemplete")
    private RestTemplate restTemplate;

    @GetMapping("/testGetApi")
    public Object getJson(){
        String url="http://localhost:8020/base/dic/GetPageList";
        HashMap<String,Object> map=new HashMap<>();
        map.put("limit",5);
        map.put("page",1);
//        HttpResult json =restTemplate.getForObject(url, HtObjecttpResult.class,map);
        Object json =restTemplate.getForObject(url, Object.class,map);
//        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//        String json = results.getBody();
        return json;
    }

    /**********HTTP POST method**************/
//    @PostMapping(value = "/testPost")
    public Object postJson(@RequestBody JSONObject param) {
        System.out.println(param.toJSONString());
        param.put("code", "ceshi");
        param.put("name", "C测试");
        return param;
    }

    @PostMapping(value = "/testPostApi")
    public Object testPost() {
        String url = "http://localhost:8020/base/dic/insert";
        JSONObject postData = new JSONObject();
        postData.put("code", "ceshi");
        postData.put("name", "C测试");
        JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
        return json;
    }
}
