package com.cangku.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.common.QueryPageParam;
import com.cangku.common.Result;
import com.cangku.entity.Applys;
import com.cangku.entity.Goods;
import com.cangku.entity.Record;
import com.cangku.entity.Storage;
import com.cangku.service.ApplysService;
import com.cangku.service.GoodsService;
import com.cangku.service.RecordService;
import com.cangku.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cangku
 * @since 2023-11-05
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ApplysService applysService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Goods goods=goodsService.getById(record.getGoods());
        Storage storage=storageService.getById(goods.getStorage());
        int n=record.getCount();//入库量
        int a=storage.getCurrentcap();//仓库当前容量
        int max=storage.getTotalcap();
        if("2".equals(record.getAction())){
            n=-n;
            a=a+n;
            record.setCount(n);

        }else {
            a=a+n;
        }
        storage.setCurrentcap(a);
        if(a>=max){
            storage.setIsproblem(1);
        }else {
            storage.setIsproblem(0);
        }
        int num=goods.getCount()+n;
        goods.setCount(num);
        goodsService.updateById(goods);
        storageService.updateById(storage);
        return recordService.save(record)?Result.suc():Result.fails();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        Record record=recordService.getById(id);
        Goods goods=goodsService.getById(record.getGoods());
        Storage storage=storageService.getById(goods.getStorage());
        int a=record.getCount();//10ru -10chu

        int n=goods.getCount()-a;
        goods.setCount(n);
        int num=storage.getCurrentcap()-a;

        int max=storage.getTotalcap();
        storage.setCurrentcap(num);
        if(num>=max){
            storage.setIsproblem(1);
        }else {
            storage.setIsproblem(0);
        }
        if(record.getApplysid()!=null){
            Applys applys=applysService.getById(record.getApplysid());
            applys.setState(1);
            applysService.updateById(applys);
        }

        goodsService.updateById(goods);
        storageService.updateById(storage);
        return recordService.removeById(id)?Result.suc():Result.fails();
    }
    @PostMapping("/listPageS")
    public Result listPageS(@RequestBody QueryPageParam query){


        HashMap param=query.getParam();
        String name=(String)param.get("name");
        String goodstype=(String)param.get("goodstype");
        String storage=(String)param.get("storage");
        String roleId=(String)param.get("roleId");
        String userId=(String)param.get("userId");
        Page<Record> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        QueryWrapper<Record> queryWrapper=new QueryWrapper();
        queryWrapper.apply(" a.goods=b.id and b.storage=c.id and b.goodsType=d.id");
        if("2".equals(roleId)){
            System.out.println(3);
            queryWrapper.apply(
                    " a.userId="+userId);

        }

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            System.out.println(4);
            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){

            queryWrapper.eq("d.id",goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.eq("c.id",storage);

        }

        queryWrapper.orderByDesc("a.id");
//        IPage result=GoodsService.pageC(page);
        IPage result=recordService.pageCC(page,queryWrapper);


        return Result.suc(result.getRecords(),result.getTotal());
    }
}
