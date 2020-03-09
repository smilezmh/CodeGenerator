package cmtech.soft.equipment.extend.wxpush;

import cmtech.soft.equipment.utils.HttpResult;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "微信消息推送测试接口")
@RestController
@RequestMapping("/wxpush")
public class WXPushController {

    @ApiOperation("测试推送")
    @PostMapping(value = "sendMess")
    public void sendMess(){
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + "wx0915b519f4e2b0ae"
                + "&secret="
                + "89384a58c8f742357df53c7aeaa58f1b";
        String result = HttpUtil.sendGet(url);
        JSONObject object=JSON.parseObject(result);
        String Access_Token = object.getString("access_token");
        Template template=new Template();
        template.setTemplate_id("ds0fuiWg5LWM37P7N1jvWHfeJzkSDdV_b4inCDcOaOI");
        template.setTouser("oObN85aYaEFNyfCXhq1noVDXddCc");// 推送至某人de openid
        template.setPage("pages/equipment/equipment"); // 跳转至小程序页面
        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("number1","123"));// 推送内容
        paras.add(new TemplateParam("time2","2019-01-01"));
//        paras.add(new TemplateParam("date3","2015年01月05日"));
//        paras.add(new TemplateParam("thing4","请进入小程序查1看"));
        template.setTemplateParamList(paras);
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
        requestUrl=requestUrl.replace("ACCESS_TOKEN", Access_Token);

        System.out.println(template.toJSON());
        net.sf.json.JSONObject jsonResult=CommonUtil.httpsRequest(requestUrl, "POST", template.toJSON());
        if(jsonResult!=null){
            System.out.println(jsonResult);
            int errorCode=jsonResult.getInt("errcode");
            String errorMessage=jsonResult.getString("errmsg");
            if(errorCode==0){
                System.out.println("Send Success");
            }else{
                System.out.println("订阅消息发送失败:"+errorCode+","+errorMessage);
            }
        }

    }
}
