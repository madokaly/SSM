package com.madoka.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.madoka.mapper.ProductInfoMapper;
import com.madoka.pojo.ProductInfo;
import com.madoka.pojo.ProductInfoExample;
import com.madoka.pojo.vo.ProductInfoVo;
import com.madoka.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        List<ProductInfo> list = productInfoMapper.selectByExample(new ProductInfoExample());
        return list;
    }

    @Override
    public PageInfo<ProductInfo> splitPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        ProductInfoExample example = new ProductInfoExample();
        example.setOrderByClause("p_id desc");
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo productInfo) {
        int num = productInfoMapper.insert(productInfo);
        return num;
    }

    @Override
    public ProductInfo getById(int pid) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(pid);
        return productInfo;
    }

    @Override
    public int update(ProductInfo productInfo) {
        int num = productInfoMapper.updateByPrimaryKey(productInfo);
        return num;
    }

    @Override
    public int delete(int pid) {
        int num = productInfoMapper.deleteByPrimaryKey(pid);
        return num;
    }

    @Override
    public int deleteBatch(String[] ids) {
        int num = productInfoMapper.deleteBatch(ids);
        return num;
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return list;
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getIspage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        PageInfo<ProductInfo> info = new PageInfo<>(list);
        return info;
    }
}
