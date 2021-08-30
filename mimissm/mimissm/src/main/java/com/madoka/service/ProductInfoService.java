package com.madoka.service;

import com.github.pagehelper.PageInfo;
import com.madoka.pojo.ProductInfo;
import com.madoka.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    List<ProductInfo> getAll();

    PageInfo<ProductInfo> splitPage(int pageNum,int pageSize);

    int save(ProductInfo productInfo);

    ProductInfo getById(int pid);

    int update(ProductInfo productInfo);

    int delete(int pid);

    int deleteBatch(String[] ids);

    List<ProductInfo> selectCondition(ProductInfoVo vo);

    PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize);
}
