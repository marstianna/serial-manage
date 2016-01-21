package com.hotpot.dao;

import com.hotpot.domain.RuntimeTable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zoupeng on 16/1/21.
 */
@Repository
public interface RuntimeTableMapper {
    List<RuntimeTable> getRuntimeTablesByStoreId(Integer storeId);

    Integer haveASeat(RuntimeTable runtimeTable);

    Integer delete(String tableCode,Integer storeId);
}
