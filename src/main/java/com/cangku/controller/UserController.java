package com.cangku.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.common.QueryPageParam;
import com.cangku.common.Result;
import com.cangku.entity.Menu;
import com.cangku.entity.User;
import com.cangku.service.MenuService;
import com.cangku.service.UserService;
import freemarker.core.JSONOutputFormat;
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
 * @since 2023-10-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list=userService.lambdaQuery().eq(User::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fails();
    }
    @GetMapping("/findByName")
    public Result findByName(@RequestParam String name){
        List list=userService.lambdaQuery().eq(User::getName,name).list();
        return list.size()>0?Result.suc(list):Result.fails();
    }
    //登录
//    @PostMapping("/login")
//    public Result login(@RequestBody User user){
//        List list=userService.lambdaQuery()
//                .eq(User::getNo,user.getNo())
//                .eq(User::getPassword,user.getPassword()).list();
//        return list.size()>0?Result.suc(list.get(0)):Result.fails();
//    }
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword()).list();


        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.suc(res);
        }
        return Result.fails();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.suc():Result.fails();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        System.out.println(user);
        List<User> list = userService.lambdaQuery().eq(User::getName,user.getName()).list();
        System.out.println(list);
        User user1=list.get(0);
        int id=user1.getId();
        System.out.println(id);
        user.setId(id);


//        getById(user.getName())
//        System.out.println(updateuser);
//        updateuser.setPassword(user.getPassword());

        return userService.updateById(user)?Result.suc():Result.fails();
//        return Result.fails();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.suc():Result.fails();
    }

    //修改
    @PostMapping("/mod")
    public boolean add(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }
    //查询(模糊，匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.suc(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        Page<User> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);
        IPage result=userService.page(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());
        return result.getRecords();
    }
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query){


        HashMap param=query.getParam();
        String name=(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));

        Page<User> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


//        IPage result=userService.pageC(page);
        IPage result=userService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC2")
    public Result listPageC2(@RequestBody QueryPageParam query){


        HashMap param=query.getParam();
        String name=(String)param.get("name");
        String sex=(String)param.get("sex");
        String roleId=(String)param.get("roleId");
        String id=(String)param.get("id");
//        System.out.println(param);
        Page<User> page=new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(roleId!="0"&&id!=null){
            lambdaQueryWrapper.eq(User::getId,id);
            System.out.println(1);

        }
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
            System.out.println(2);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
            System.out.println(3);
        }
//        if(StringUtils.isNotBlank(roleId)){
//            lambdaQueryWrapper.eq(User::getRoleId,roleId);
//            System.out.println(4);
//        }



//        IPage result=userService.pageC(page);
        IPage result=userService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
