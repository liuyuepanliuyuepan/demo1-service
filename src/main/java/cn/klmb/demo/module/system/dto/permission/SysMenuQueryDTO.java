package cn.klmb.demo.module.system.dto.permission;

import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.module.system.enums.permission.SysMenuTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统角色
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysMenuQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 菜单名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 权限标识
     * <p>
     * 一般格式为：${系统}:${模块}:${操作} 例如说：system:admin:add，即 system 服务的添加管理员。
     * <p>
     * 当我们把该 MenuDO 赋予给角色后，意味着该角色有该资源： - 对于后端，配合 @PreAuthorize 注解，配置 API 接口需要该权限，从而对 API 接口进行权限控制。 -
     * 对于前端，配合前端标签，配置按钮是否展示，避免用户没有该权限时，结果可以看到该操作。
     */
    @DtoFieldQuery
    private String permission;
    /**
     * 菜单类型
     * <p>
     * 枚举 {@link SysMenuTypeEnum}
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 显示顺序
     */
    @DtoFieldQuery
    private Integer sort;
    /**
     * 父菜单ID
     */
    @DtoFieldQuery
    private String parentId;
    /**
     * 路由地址
     */
    @DtoFieldQuery
    private String path;
    /**
     * 菜单图标
     */
    @DtoFieldQuery
    private String icon;
    /**
     * 组件路径
     */
    @DtoFieldQuery
    private String component;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    @DtoFieldQuery
    private Integer status;
    /**
     * 是否可见
     * <p>
     * 只有菜单、目录使用 当设置为 true 时，该菜单不会展示在侧边栏，但是路由还是存在。例如说，一些独立的编辑页面 /edit/1 等等
     */
    @DtoFieldQuery
    private Boolean visible;
    /**
     * 是否缓存
     * <p>
     * 只有菜单、目录使用 是否使用 Vue 路由的 keep-alive 特性
     */
    @DtoFieldQuery
    private Boolean keepAlive;

}
