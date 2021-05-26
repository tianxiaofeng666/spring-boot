package cn.chinaunicom.person.controller.restful;

import cn.chinaunicom.person.bean.RestfulResponse;
import cn.chinaunicom.person.entity.GuestInfoEntity;
import cn.chinaunicom.person.model.query.GuestInfoQueryModel;
import cn.chinaunicom.person.request.GuestReq;
import cn.chinaunicom.person.service.GuestInfoService;
import cn.chinaunicom.person.utils.Common;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
@Api("guestInfo")
@RestController
@RequestMapping("/api/guestInfo")
public class GuestInfoController {

    @Autowired
    GuestInfoService guestInfoService;

    @PostMapping("/add")
    @ApiOperation(value = "add", notes = "新增")
    public RestfulResponse add(@RequestBody GuestInfoEntity entity) {
        try {
            boolean b = guestInfoService.save(entity);
            if (!b) {
                throw new Exception("add异常");
            }
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("add", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value = "delete", notes = "删除")
    public RestfulResponse delete(@RequestBody GuestInfoQueryModel queryModel) {
        try {
            Common.letFieldNullIfEmpty(queryModel);
            guestInfoService.removeByModel(queryModel);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("delete ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "update", notes = "更新")
    public RestfulResponse update(@RequestBody GuestInfoEntity entity) {
        try {
            guestInfoService.updateById(entity);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("update ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "saveOrUpdate", notes = "新增或更新")
    public RestfulResponse saveOrUpdate(@RequestBody GuestInfoEntity entity) {
        try {
            guestInfoService.saveOrUpdate(entity);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("saveOrUpdate ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    /**
     * 根据条件查询并分页，使用QueryWrapperUtil工具类来构建QueryWrapper
     * @param queryModel
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(value = "query", notes = "查询")
    public RestfulResponse query(@RequestBody GuestInfoQueryModel queryModel) {
        try {
            Common.letFieldNullIfEmpty(queryModel);
            Page<GuestInfoEntity> guestInfoEntityPage = guestInfoService.queryByModel(queryModel);
            return RestfulResponse.success(guestInfoEntityPage);
        } catch (Exception ex) {
            Common.logger.error("query", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    /**
     * 查询当日访客数
     */
    @PostMapping("/todayCount")
    public RestfulResponse queryTodayGuestCount(){
        int cont = guestInfoService.queryTodayGuestCount();
        return RestfulResponse.success(cont);
    }

    /**
     * 访客统计
     */
    @PostMapping("/guestCount")
    public RestfulResponse guestCount(@RequestBody JSONObject json){
        int dimension = json.getInteger("dimension");
        List<JSONObject> list = guestInfoService.guestCount(dimension);
        return RestfulResponse.success(list);
    }

    /**
     * 根据条件查询并分页,自定义QueryWrapper
     */
    @PostMapping("/queryConditionPage")
    public RestfulResponse queryConditionPage(@RequestBody GuestReq req){
        return RestfulResponse.success(guestInfoService.queryConditionPage(req));
    }

    /**
     * 根据条件查询所有
     */
    @PostMapping("/queryConditionAll")
    public RestfulResponse queryConditionAll(@RequestBody GuestReq req){
        return RestfulResponse.success(guestInfoService.queryConditionAll(req));
    }

}
