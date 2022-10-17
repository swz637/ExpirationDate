package com.swz637.Service;

import com.swz637.Bean.Goods;

import java.util.List;

/**
 * @ author: lsq637
 * @ since: 2022-09-21 16:22:17
 * @ describe:
 */
public interface GoodsService {
    void addGoods(Goods goods);
    List<Goods> selectGoodsLimit(String flag,Boolean isExpired) throws Exception;

    void deleteById(int id);
    Goods selectById(int id);

    void update(Goods goods);



}
