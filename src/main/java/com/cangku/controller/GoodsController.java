package com.cangku.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.common.QueryPageParam;
import com.cangku.common.Result;
import com.cangku.entity.Goods;
import com.cangku.entity.Storage;
import com.cangku.service.GoodsService;
import com.cangku.service.GoodsService;
import com.cangku.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cangku
 * @since 2023-11-05
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StorageService storageService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        System.out.println(goods);
        Storage storage=storageService.getById(goods.getStorage());
        int n=goods.getCount();
        int max=storage.getTotalcap();
        int num=storage.getCurrentcap()+n;

        if((max-num)<100){

            storage.setIsproblem(1);
        }else {
            storage.setIsproblem(0);
        }
        storage.setCurrentcap(num);
        storageService.updateById(storage);
        return goodsService.save(goods)?Result.suc():Result.fails();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        Storage storage=storageService.getById(goods.getStorage());
        int n=goods.getCount();

        int num=storage.getCurrentcap()+n;
        storage.setCurrentcap(num);
        storageService.updateById(storage);
        return goodsService.updateById(goods)?Result.suc():Result.fails();
    }
    //更新
    @PostMapping("/updateMod")
    public Result updateMod(@RequestBody Goods goods){
        Storage storage=storageService.getById(goods.getStorage());
        Goods goods1=goodsService.getById(goods.getId());
        int a=goods1.getCount();
        int n=goods.getCount();
        //a=1000,n=2000,a=3000,n=2000
            n=n-a;


        int num=storage.getCurrentcap()+n;
        storage.setCurrentcap(num);
        storageService.updateById(storage);
        return goodsService.updateById(goods)?Result.suc():Result.fails();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        Goods goods=goodsService.getById(id);
        Storage storage=storageService.getById(goods.getStorage());
        int n=goods.getCount();
        int num=storage.getCurrentcap()-n;
        int max=storage.getTotalcap();
        storage.setCurrentcap(num);
        if(num>=max){
            storage.setIsproblem(1);
        }else {
            storage.setIsproblem(0);
        }
        storageService.updateById(storage);
        return goodsService.removeById(id)?Result.suc():Result.fails();
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String name){
        List list=goodsService.lambdaQuery().eq(Goods::getName,name).list();
        return list.size()>0?Result.suc(list):Result.fails();
    }
    //库存量
    @GetMapping("/findCount")
    public Result findCount(@RequestParam Integer count,String id){
//        List list=goodsService.lambdaQuery().eq(Goods::getId,id).list();
//        System.out.println(list);
//        int oldCount=list.;
        Goods goods=goodsService.getById(id);
        System.out.println(goods);
        int oldCount=goods.getCount();
        if(count>oldCount){
            return Result.fails();
        }
        return Result.suc();


    }

    @PostMapping("/listPageS")
    public Result listPageS(@RequestBody QueryPageParam query){


        HashMap param=query.getParam();
        String name=(String)param.get("name");
        String goodstype=(String)param.get("goodstype");
        String storage=(String)param.get("storage");
        Page<Goods> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage,storage);
        }


//        IPage result=GoodsService.pageC(page);
        IPage result=goodsService.pageCC(page,lambdaQueryWrapper);


        return Result.suc(result.getRecords(),result.getTotal());
    }
}
