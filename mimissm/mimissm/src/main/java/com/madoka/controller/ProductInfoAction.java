package com.madoka.controller;

import com.github.pagehelper.PageInfo;
import com.madoka.pojo.ProductInfo;
import com.madoka.pojo.vo.ProductInfoVo;
import com.madoka.service.ProductInfoService;
import com.madoka.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    public static final int PAGE_SIZE = 5;

    private String saveFileName = "";

    @Autowired
    private ProductInfoService pis;

    @RequestMapping("/getAll")
    public ModelAndView getAll() {
        ModelAndView mv = new ModelAndView();
        List<ProductInfo> list = pis.getAll();
        mv.addObject("prod",list);
        mv.setViewName("product");
        return mv;
    }

    @RequestMapping("/split")
    public String split(HttpServletRequest request) {
        PageInfo<ProductInfo> info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo != null) {
            info = pis.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else {
            info = pis.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info",info);
        return "product";
    }

    @RequestMapping("/product")
    public String product(int ispage,HttpServletRequest request) {
        PageInfo<ProductInfo> info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo != null) {
            info = pis.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else {
            info = pis.splitPage(ispage, PAGE_SIZE);
        }
        request.setAttribute("info",info);
        return "product";
    }

    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        PageInfo<ProductInfo> pageInfo = pis.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",pageInfo);
    }

    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        String path = request.getServletContext().getRealPath("/image_big");
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        obj.put("imgurl",saveFileName);
        return obj.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo productInfo,HttpServletRequest request) {
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());
        int num = -1;
        try {
            num = pis.save(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg","添加成功");
        }else {
            request.setAttribute("msg","添加失败");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public ModelAndView getById(int pid,ProductInfoVo vo,HttpSession session) {
        ModelAndView mv = new ModelAndView();
        ProductInfo productInfo = pis.getById(pid);
        mv.addObject("prod",productInfo);
        mv.setViewName("update");
        session.setAttribute("prodVo",vo);
        saveFileName = "";
        return mv;
    }

    @RequestMapping("/update")
    public String update(ProductInfo productInfo,HttpServletRequest request) {
        if(!"".equals(saveFileName)) {
            productInfo.setpImage(saveFileName);
        }
        int num = -1;
        try {
            num = pis.update(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0) {
            request.setAttribute("msg","修改成功");
        }else {
            request.setAttribute("msg","修改失败");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request) {
        int num = -1;
        try {
            num = pis.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0) {
            request.setAttribute("msg","删除成功");
        }else {
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        PageInfo<ProductInfo> pageInfo = pis.splitPage(1,PAGE_SIZE);
        request.getSession().setAttribute("info",pageInfo);
        return request.getAttribute("msg");
    }

    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request) {
        String[] ids = pids.split(",");
        int num = -1;
            num = pis.deleteBatch(ids);
        try {
            if(num > 0) {
                request.setAttribute("msg","删除成功");
            }else {
                request.setAttribute("msg","删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping("/condition")
    public void selectCondition(ProductInfoVo vo,HttpSession session) {
        List<ProductInfo> list = pis.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
