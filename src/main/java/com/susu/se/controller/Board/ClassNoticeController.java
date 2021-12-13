package com.susu.se.controller.Board;

import com.susu.se.model.ClassNotice;
import com.susu.se.service.Board.ClassNoticeService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("classnotices")
public class ClassNoticeController {
    @Autowired
    private ClassNoticeService classNoticeService;

//    //查询单个classnotice
//    @GetMapping(path="/{id}")
//    public Result<ClassNotice> getOne(@PathVariable Integer id){
//        return classNoticeService.getClassNotice(id);
//    }

    //查询所有classnotice
    @GetMapping
    public Result<List<ClassNotice>> getAll(){
        return classNoticeService.getAllClassNotice();
    }

    //添加classnotice
    @PostMapping(path = "/add")
    public Result<String> addNotice(@RequestParam("classid") Integer classId, @RequestParam("category") String category,@RequestParam("title") String title,@RequestParam("content") String content){
        return classNoticeService.addClassNotice( category, title, content, classId);
    }

    //删除classnotice
    @DeleteMapping("/{sysNoticeId}")
    public Result<String> deleteSysNotice(@PathVariable Integer sysNoticeId){
        return classNoticeService.deleteClassNotice(sysNoticeId);
    }

    //更改classNotice
    @PostMapping("/alter/{sysNoticeId}")
    public Result<String> alterSysNotice(@PathVariable Integer sysNoticeId, @RequestParam("category") String category,@RequestParam("title") String title,@RequestParam("content") String content){
        return classNoticeService.alterClassNotice(sysNoticeId, category, title, content);
    }

    //获取某一班级的所有notice
    @GetMapping("/{classId}")
    public Result<List<ClassNotice>> getAllClassNoticesOfClass(@PathVariable Integer classId){
        return classNoticeService.getAllClassNoticeOfClass(classId);
    }
}
