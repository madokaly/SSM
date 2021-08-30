package com.madoka;


import com.madoka.mapper.ProductInfoMapper;
import com.madoka.pojo.ProductInfo;
import com.madoka.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Test
    public void testSelectCondition() {
        ProductInfoVo vo = new ProductInfoVo();
        vo.setTypeid(2);
        vo.setPname("3");
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        list.forEach(info -> System.out.println(info));
    }
}
