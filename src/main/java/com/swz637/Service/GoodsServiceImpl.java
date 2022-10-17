package com.swz637.Service;

import com.swz637.Bean.Goods;
import com.swz637.Dao.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpRetryException;
import java.util.List;

/**
 * @ author: lsq637
 * @ since: 2022-09-21 16:22:39
 * @ describe:
 */
@Component
//@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
public class GoodsServiceImpl implements GoodsService {
    private static int page = 0;
    public static final String PRE = "pre";
    public static final String NXT = "nxt";

    public static final String TOP = "top";
    public static final String TAIL = "tail";

    private static int defaultLimit = 10;
    private static Boolean[] isExpiredCache = null;

    public static void reducePage() {
        page--;
    }

    public static void increasePage() {
        page++;
    }

    public static int getDefaultLimit() {
        return defaultLimit;
    }

    public static void setDefaultLimit(int defaultLimit) {
        GoodsServiceImpl.defaultLimit = defaultLimit;
    }

    @Autowired
//    @Qualifier(value = "goodsMapper")
    private GoodsMapper goodsMapper;

    @Override
    public void addGoods(Goods goods) {
        goodsMapper.addGoods(goods);
    }

    @Override
    public List<Goods> selectGoodsLimit(String flag, Boolean isExpired) throws Exception {
        if (isExpiredCache == null) {//上一次过期或未过期页面的缓存，用来知道这次请求是否变换了页面
            isExpiredCache = new Boolean[1];
            isExpiredCache[0] = isExpired;
        } else {
            if (isExpiredCache[0] ^ isExpired) {//若与缓存的不同,说明换种类了
                isExpiredCache[0] = isExpired;
                page = 0;//将page归位
            }
        }

        int goodsNum = goodsMapper.countGoods(isExpired);
        if (goodsNum==0){//如果知道本页没有物品直接返回空
            return null;
        }
        if (flag != null) {

            int maxPage = 0;
            if (goodsNum % defaultLimit == 0) {
                maxPage = goodsNum / defaultLimit - 1;
            } else {
                maxPage = goodsNum / defaultLimit;
            }
            if (PRE.equals(flag)) {
                if (page == 0) {
                    throw new Exception("没有前一页了");
                }
                page--;
            } else if (NXT.equals(flag)) {
                if (maxPage == page) {
                    throw new Exception("没有下一页了");
                }
                page++;

            } else if (TOP.equals(flag)) {
                page = 0;
            } else if (TAIL.equals(flag)) {
                page = maxPage;
            } else {
                throw new HttpRetryException("请求参数错误", 400);
            }
        }

        return goodsMapper.selectGoodsLimit(page * defaultLimit, defaultLimit, isExpired);
    }

    @Override
    public void deleteById(int id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public Goods selectById(int id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public void update(Goods goods) {
        goodsMapper.update(goods);
    }


}
