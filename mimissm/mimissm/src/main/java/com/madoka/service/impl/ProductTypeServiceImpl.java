package com.madoka.service.impl;

import com.madoka.mapper.ProductTypeMapper;
import com.madoka.pojo.ProductType;
import com.madoka.pojo.ProductTypeExample;
import com.madoka.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        List<ProductType> list = productTypeMapper.selectByExample(new ProductTypeExample());
        return list;
    }
}
