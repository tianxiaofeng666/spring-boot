package cn.chinaunicom.person.service;

import cn.chinaunicom.person.entity.GuestInfoEntity;
import cn.chinaunicom.person.mapper.GuestInfoMapper;
import cn.chinaunicom.person.model.query.GuestInfoQueryModel;
import cn.chinaunicom.person.request.GuestReq;
import cn.chinaunicom.person.utils.DateUtil;
import cn.chinaunicom.person.utils.QueryWrapperUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
@Service
public class GuestInfoService extends ServiceImpl<GuestInfoMapper, GuestInfoEntity> {

    @Autowired
    private GuestInfoMapper guestInfoMapper;

    public Page<GuestInfoEntity> queryByModel(GuestInfoQueryModel queryModel) {
         QueryWrapper<GuestInfoEntity> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
         Page<GuestInfoEntity> page = new Page<>(queryModel.getPageNum(), queryModel.getPageSize());
         Page<GuestInfoEntity> guestInfoEntityPage = guestInfoMapper.selectPage(page, queryWrapper);
         return guestInfoEntityPage;
    }

    public int queryCountByModel(GuestInfoQueryModel queryModel) {
        QueryWrapper<GuestInfoEntity> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        Integer count = guestInfoMapper.selectCount(queryWrapper);
        return Optional.ofNullable(count).orElse(0);
    }

    public List<GuestInfoEntity> queryAll() {
        return guestInfoMapper.selectList(null);
    }

    public int removeByModel(GuestInfoQueryModel queryModel) {
        QueryWrapper<GuestInfoEntity> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        int i = guestInfoMapper.delete(queryWrapper);
        return i;
    }

    public int queryTodayGuestCount(){
        LocalDateTime date = LocalDateTime.now();
        String dateStr = DateUtil.localDateTimeString(date,"yyyy-MM-dd");
        String startDate = dateStr + " 00:00:00";
        String endDate = dateStr + " 23:59:59";
        QueryWrapper<GuestInfoEntity> queryWrapper = new QueryWrapper<>();
        //大于等于
        queryWrapper.ge("create_time",startDate);
        //小于
        //queryWrapper.lt("create_time",endDate);
        //小于等于
        queryWrapper.le("create_time",endDate);
        return guestInfoMapper.selectCount(queryWrapper);
        //return guestInfoMapper.queryTodayGuestCount(startDate,endDate);
    }

    public List<JSONObject> guestCount(int dimension){
        LocalDateTime now = LocalDateTime.now();
        String stratDate = "";
        String endDate = DateUtil.localDateTimeString(now.plusDays(-1),"yyyy-MM-dd") + " 23:59:59";
        if(dimension == 0){
            //前7天
            stratDate = DateUtil.localDateTimeString(now.plusDays(-7),"yyyy-MM-dd") + " 00:00:00";
        }else{
            //前30天
            stratDate = DateUtil.localDateTimeString(now.plusDays(-30),"yyyy-MM-dd") + " 00:00:00";
        }
        return guestInfoMapper.guestCount(stratDate,endDate);
    }

    public Page<GuestInfoEntity> queryConditionPage(GuestReq req){
        Page<GuestInfoEntity> page = new Page<>(req.getPageNum(),req.getPageSize());

        QueryWrapper<GuestInfoEntity> queryWrapper = new QueryWrapper<GuestInfoEntity>();
        if(req.getId() != null){
            queryWrapper.eq("id",req.getId());
        }
        if(StringUtils.isNotBlank(req.getGuestName())){
            queryWrapper.like("guest_name",req.getGuestName());
        }
        if(StringUtils.isNotBlank(req.getIdCard())){
            queryWrapper.eq("guest_idcard",req.getIdCard());
        }
        queryWrapper.orderByDesc("id");

        Page<GuestInfoEntity> guestPage = guestInfoMapper.selectPage(page, queryWrapper);
        return guestPage;
    }

    public List<GuestInfoEntity> queryConditionAll(GuestReq req){
        /*Map<String, Object> map = new HashMap<>();
        map.put("guest_name",req.getGuestName());
        List<GuestInfoEntity> list = guestInfoMapper.selectByMap(map);*/

        QueryWrapper<GuestInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("guest_name",req.getGuestName());
        List<GuestInfoEntity> list = guestInfoMapper.selectList(queryWrapper);

        /*List<Long> idList = new ArrayList<>();
        idList.add(55L);
        List<GuestInfoEntity> list = guestInfoMapper.selectBatchIds(idList);*/
        return list;
    }
}
