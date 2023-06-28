package cn.klmb.demo.module.system.controller.admin.permission.vo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel("管理后台 - 赋予用户角色 Request VO")
@Data
public class SysPermissionAssignUserRoleReqVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    @NotNull(message = "用户编号不能为空")
    private String userId;

    @ApiModelProperty(value = "角色编号列表", example = "1,3,5")
    private Set<String> roleIds = Collections.emptySet(); // 兜底

}
