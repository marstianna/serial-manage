package com.hotpot.dao;

import com.hotpot.domain.Setting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zoupeng on 15/12/28.
 */
@Repository
public interface SettingMapper {
    /**
     * 模糊查询
     * @param key
     * @return
     */
    List<Setting> blurredQuery(@Param("key") String key);

    Setting query(String key);

    Integer insertOrUpdate(Setting setting);

}
