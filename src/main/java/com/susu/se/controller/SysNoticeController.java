package com.susu.se.controller;

import com.susu.se.model.SysNotice;
import com.susu.se.service.SysNoticeService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //查询所有nnotice
    @GetMapping
    public Result<List<SysNotice>> getAll(){
        return sysNoticeService.getAllSysNotice();
    }
}
