package cn.klmb.demo.module.system.controller.admin.notify;

import static cn.klmb.demo.framework.common.pojo.CommonResult.success;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.demo.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessagePageReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageRespVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageScrollPageReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageUpdateReqVO;
import cn.klmb.demo.module.system.convert.notify.SysNotifyMessageConvert;
import cn.klmb.demo.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyMessageDO;
import cn.klmb.demo.module.system.service.notify.SysNotifyMessageService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 站内信 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0117.系统管理-站内信")
@RestController
@RequestMapping("/sys/notify-message")
@Validated
public class SysNotifyMessageController {

    private final SysNotifyMessageService sysNotifyMessageService;

    public SysNotifyMessageController(SysNotifyMessageService sysNotifyMessageService) {
        this.sysNotifyMessageService = sysNotifyMessageService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('sys:notify-message:save')")
    public CommonResult<String> save(@Valid @RequestBody SysNotifyMessageSaveReqVO saveReqVO) {
        SysNotifyMessageDO saveDO = SysNotifyMessageConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysNotifyMessageService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('sys:notify-message:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysNotifyMessageService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('sys:notify-message:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody SysNotifyMessageUpdateReqVO updateReqVO) {
        SysNotifyMessageDO updateDO = SysNotifyMessageConvert.INSTANCE.convert(updateReqVO);
        sysNotifyMessageService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('sys:notify-message:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysNotifyMessageService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('sys:notify-message:query')")
    public CommonResult<SysNotifyMessageRespVO> getByBizId(@PathVariable String bizId) {
        SysNotifyMessageDO saveDO = sysNotifyMessageService.getByBizId(bizId);
        return success(SysNotifyMessageConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('sys:notify-message:query')")
    public CommonResult<KlmbPage<SysNotifyMessageRespVO>> page(
            @Valid SysNotifyMessagePageReqVO reqVO) {
        KlmbPage<SysNotifyMessageDO> klmbPage = KlmbPage.<SysNotifyMessageDO>builder()
                .pageNo(reqVO.getPageNo()).pageSize(reqVO.getPageSize()).build();
        SysNotifyMessageQueryDTO queryDTO = SysNotifyMessageConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysNotifyMessageDO> page = sysNotifyMessageService.page(queryDTO, klmbPage);
        return success(SysNotifyMessageConvert.INSTANCE.convert(page));
    }


    @GetMapping({"/page-scroll"})
    @ApiOperation(value = "滚动分页查询", notes = "只支持根据bizId顺序进行正、倒序查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
    @PreAuthorize("@ss.hasPermission('sys:notify-message:query')")
    public CommonResult<KlmbScrollPage<SysNotifyMessageRespVO>> pageScroll(
            @Valid SysNotifyMessageScrollPageReqVO reqVO) {
        KlmbScrollPage<SysNotifyMessageDO> klmbPage = KlmbScrollPage.<SysNotifyMessageDO>builder()
                .lastBizId(reqVO.getLastBizId()).pageSize(reqVO.getPageSize()).asc(reqVO.getAsc())
                .build();
        SysNotifyMessageQueryDTO queryDTO = SysNotifyMessageConvert.INSTANCE.convert(reqVO);
        KlmbScrollPage<SysNotifyMessageDO> page = sysNotifyMessageService.pageScroll(queryDTO,
                klmbPage);
        KlmbScrollPage<SysNotifyMessageRespVO> respPage = new KlmbScrollPage<>();
        respPage = SysNotifyMessageConvert.INSTANCE.convert(page);
        return success(respPage);
    }

    @PutMapping("/update-read-status")
    @ApiOperation("标记为已读")
    @PreAuthorize("@ss.hasPermission('sys:notify-message:update')")
    public CommonResult<Boolean> updateReadStatus(
            @Valid @RequestBody SysNotifyMessageUpdateReqVO updateReqVO) {
        sysNotifyMessageService.update(
                new LambdaUpdateWrapper<SysNotifyMessageDO>().in(SysNotifyMessageDO::getBizId,
                        updateReqVO.getBizIds()).set(SysNotifyMessageDO::getReadStatus, true));
        return success(true);
    }

}
