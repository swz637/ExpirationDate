package com.swz637.Dao;

import com.swz637.Bean.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ author: lsq637
 * @ since: 2022-09-21 16:14:23
 * @ describe:
 */
@Mapper
@Repository
public interface GoodsMapper {
    void addGoods(Goods goods);

    /**
     * @param start 本次查询的页数*一页的物品数
     * @param limit 一页的物品数
     * @param isExpired 查询是否过期的物品
     * @return
     */
    List<Goods> selectGoodsLimit(int start,int limit,Boolean isExpired);
    int countGoods(Boolean isExpired);//根据参数统计已过期或未过期的物品的数量
    void deleteById(int id);
    Goods selectById(int id);
    void update(Goods goods);
}
