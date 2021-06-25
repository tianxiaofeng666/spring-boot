package cn.chinaunicom.person.mapper;

import cn.chinaunicom.person.entity.GuestInfoEntity;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
@Component
public interface GuestInfoMapper extends BaseMapper<GuestInfoEntity> {

    //int queryTodayGuestCount(String startDate, String endDate);

    List<JSONObject> guestCount(String startDate, String endDate);
}
