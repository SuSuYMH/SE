package com.susu.se.controller.Board;

import com.susu.se.model.SysNotice;
import com.susu.se.service.Board.SysNoticeService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysnotices")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

    //查询单个sysnotice
    @GetMapping(path="/{id}")
    public Result<SysNotice> getAllTest(@PathVariable Integer id){
        return sysNoticeService.getSysNotice(id);
    }

    //查询所有sysnotice
    @GetMapping
    public Result<List<SysNotice>> getAll(){
        return sysNoticeService.getAllSysNotice();
    }

    //添加sysnotice
    @PostMapping(path = "/add")
    public Result<String> addNotice(@RequestParam("category") String category,@RequestParam("title") String title,@RequestParam("content") String content){
        return sysNoticeService.addSysNotice(category, title, content);
    }

    //删除sysnotice
    @DeleteMapping("/{sysNoticeId}")
    public Result<String> deleteSysNotice(@PathVariable Integer sysNoticeId){
        return sysNoticeService.deleteSysNotice(sysNoticeId);
    }

    //更改sysNotice
    @PostMapping("/alter/{sysNoticeId}")
    public Result<String> alterSysNotice(@PathVariable Integer sysNoticeId, @RequestParam("category") String category,@RequestParam("title") String title,@RequestParam("content") String content){
        return sysNoticeService.alterSysNotice(sysNoticeId, category, title, content);
    }
}
