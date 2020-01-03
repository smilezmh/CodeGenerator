package cmtech.soft.equipment.utils.Api;

import cmtech.soft.equipment.utils.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cmmes-admin")
public interface AdminDataService {
    // 查找部门树
    @GetMapping("/dept/findTree")
    HttpResult findTree();
    // 根据部门id找到人员信息
    @GetMapping("/user/findUsersByDeptId")
    HttpResult findUsersByDeptId(@RequestParam(value = "deptId") Long deptId);
}
