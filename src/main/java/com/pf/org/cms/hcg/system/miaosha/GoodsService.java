package com.pf.org.cms.hcg.system.miaosha;

public interface GoodsService {

    /**
     * 通过lua脚本实现的秒杀
     * @param skuCode 商品编码
     * @param buyNum 购买数量
     * @return 购买数量
     */
    Long flashSellByLuaScript(String skuCode,int buyNum);
    /**
     * 通过redis 事务 实现的秒杀
     * @param skuCode 商品编码
     * @param buyNum 购买数量
     * @return 购买数量
     */
    Long flashSellByRedisWatch(String skuCode,int buyNum);





}
