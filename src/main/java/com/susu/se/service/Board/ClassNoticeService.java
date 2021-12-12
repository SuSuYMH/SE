package com.susu.se.service.Board;

import com.susu.se.model.Class;
import com.susu.se.model.ClassNotice;
import com.susu.se.model.SysNotice;
import com.susu.se.repository.ClassNoticeRepository;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.SysNoticeRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClassNoticeService {

    @Autowired
    private ClassNoticeRepository classNoticeRepository;

    @Autowired
    private ClassRepository classRepository;

    //通过id获取notice
    public Result<ClassNotice> getClassNotice(Integer id) {
        Optional<ClassNotice> sysNotice = classNoticeRepository.findById(id);
        if (!sysNotice.isPresent()) {
            return Result.wrapErrorResult("SysNotice is not exist!");
        }
        return Result.wrapSuccessfulResult(sysNotice.get());
    }

    //获取所有notice
    public Result<List<ClassNotice>> getAllClassNotice() {
        List<ClassNotice> sysNoticeslist = classNoticeRepository.findAll();
        return Result.wrapSuccessfulResult(sysNoticeslist);
    }

    //新增系统公告
    public Result<String> addClassNotice(String category, String title, String content, Integer classId){
        Class aClass = classRepository.findById(classId).get();
        ClassNotice classNotice = new ClassNotice();
        classNotice.setCategory(category);
        classNotice.setTitle(title);
        classNotice.setContent(content);
        classNotice.setUploadTime(new Date());
        classNotice.setKecheng(aClass);
        classNoticeRepository.save(classNotice);

        return Result.wrapSuccessfulResult("课程公告创建成功！");
    }

    //删除某一系统公告
    public Result<String> deleteClassNotice(Integer sysNoticeId){
        classNoticeRepository.deleteById(sysNoticeId);
        return Result.wrapSuccessfulResult("课程公告删除成功！");
    }

    //修改某一课程
    public Result<String> alterClassNotice(Integer sysNoticeId, String category, String title, String content){
        ClassNotice classNotice = classNoticeRepository.getById(sysNoticeId);
        classNotice.setCategory(category);
        classNotice.setTitle(title);
        classNotice.setContent(content);
        classNotice.setUploadTime(new Date());
        classNoticeRepository.save(classNotice);

        return Result.wrapSuccessfulResult("课程公告更新成功！");
    }

    //通过班级id获取所有班级的notice
    public Result<List<ClassNotice>> getAllClassNoticeOfClass(Integer classId){
        Class aClass = classRepository.findById(classId).get();
        List<ClassNotice> classNoticesByAClass = classNoticeRepository.findClassNoticesByKecheng(aClass);
        return Result.wrapSuccessfulResult(classNoticesByAClass);

    }
}
