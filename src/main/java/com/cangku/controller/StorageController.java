package com.cangku.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.common.QueryPageParam;
import com.cangku.common.Result;
import com.cangku.entity.Menu;
import com.cangku.entity.Storage;
import com.cangku.entity.Storage;
import com.cangku.entity.User;
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
 * @since 2023-11-04
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){


        return storageService.save(storage)?Result.suc():Result.fails();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage){
        return storageService.updateById(storage)?Result.suc():Result.fails();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return storageService.removeById(id)?Result.suc():Result.fails();
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String name){
        List list=storageService.lambdaQuery().eq(Storage::getName,name).list();
        return list.size()>0?Result.suc(list):Result.fails();
    }

    @PostMapping("/listPageS")
    public Result listPageS(@RequestBody QueryPageParam query){


        HashMap param=query.getParam();
        String name=(String)param.get("name");

        Page<Storage> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Storage> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Storage::getName,name);
        }

//        IPage result=StorageService.pageC(page);
        IPage result=storageService.pageCC(page,lambdaQueryWrapper);


        return Result.suc(result.getRecords(),result.getTotal());
    }

    @PostMapping("/listPageSS")
    public Result listPageSS(@RequestBody QueryPageParam query){


        HashMap param=query.getParam();
        Page<Storage> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Storage> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Storage::getIsproblem,1);
//        IPage result=StorageService.pageC(page);
        IPage result=storageService.pageCC(page,lambdaQueryWrapper);


        return Result.suc(result.getRecords(),result.getTotal());
    }

    @PostMapping("/isProblem")
    public Result isProblem(@RequestBody QueryPageParam query){
        System.out.println("p");

        HashMap param=query.getParam();
        Page<Storage> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Storage> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Storage::getIsproblem,1);
//        IPage result=StorageService.pageC(page);
        IPage result=storageService.pageCC(page,lambdaQueryWrapper);


        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list=storageService.list();
        return Result.suc(list);
    }
}
