package com.cangku.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.common.QueryPageParam;
import com.cangku.common.Result;
import com.cangku.entity.*;
import com.cangku.service.ApplysService;
import com.cangku.service.GoodsService;
import com.cangku.service.RecordService;
import com.cangku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cangku
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/applys")
public class ApplysController {
    @Autowired
    private ApplysService applysService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Applys applys){
        System.out.println("applys.save");
        System.out.println(applys);
        Goods goods=goodsService.getById(applys.getGoods());

        int n=applys.getCount();
        System.out.println(n);
        int num=goods.getCount()+n;
        goods.setCount(num);
        goodsService.updateById(goods);
        int s;
        s=2;
        applys.setState(s);
//        int x=applys.getState();
//        System.out.println(x);

            User user=userService.getById(applys.getAdminId());
            int a=user.getId();
            applys.setAdminId(a);
            return applysService.updateById(applys)?Result.suc():Result.fails();





    }
    @PostMapping("/saveU")
    public Result saveU(@RequestBody Applys applys){
        System.out.println(applys);
        int s;
        s=0;
        applys.setState(s);
        User user=userService.getById(applys.getUserid());
        int u=user.getId();
        int n=applys.getCount();
        n=-n;
        applys.setCount(n);
        applys.setUserid(u);
        return applysService.save(applys)?Result.suc():Result.fails();




    }
    @PostMapping("/del")
    public Result del(@RequestBody Applys applys){

        int s;
        s=1;
        applys.setState(s);
        int x=applys.getState();
        System.out.println(x);

        return applysService.updateById(applys)?Result.suc():Result.fails();
    }
    //删除
    @GetMapping("/cancel")
    public Result cancel(@RequestParam String id){
        return applysService.removeById(id)?Result.suc():Result.fails();
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param=query.getParam();
        System.out.println(param);
        String name=(String)param.get("name");
        String goodstype=(String)param.get("goodstype");
        String storage=(String)param.get("storage");
        String roleId=(String)param.get("roleId");
        String userId=(String)param.get("userId");
        String state=(String)param.get("state");

        Page<Applys> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        QueryWrapper<Applys> queryWrapper=new QueryWrapper();
        queryWrapper.apply(" a.goods=b.id and b.storage=c.id and b.goodsType=d.id");
        if("2".equals(roleId)){
            System.out.println(3);
            queryWrapper.apply(
                    " a.userId="+userId);

        }

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            System.out.println(4);
            queryWrapper.apply("b.name like '%" + name+"%'");
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){

            queryWrapper.apply("d.id="+goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.apply("c.id="+storage);

        }
        if(StringUtils.isNotBlank(state) && !"null".equals(state)){
            queryWrapper.apply("a.state="+state);

        }
        queryWrapper.orderByDesc("a.id");
        IPage result=applysService.pageCC(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
    @PostMapping("/listPageA")
    public Result listPageA(@RequestBody QueryPageParam query){
        HashMap param=query.getParam();
        Page<Applys> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        QueryWrapper<Applys> queryWrapper=new QueryWrapper();
        queryWrapper.apply(" a.goods=b.id and b.storage=c.id and b.goodsType=d.id");
        queryWrapper.apply("a.state=0");
        queryWrapper.orderByDesc("a.id");
        IPage result=applysService.pageCC(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
