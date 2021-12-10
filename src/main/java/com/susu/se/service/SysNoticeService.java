package com.susu.se.service;

import com.susu.se.model.Course;
import com.susu.se.model.Experiment;
import com.susu.se.model.SysNotice;
import com.susu.se.repository.SysNoticeRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SysNoticeService {

    @Autowired
    private SysNoticeRepository sysNoticeRepository;

    //通过id获取notice
    public Result<SysNotice> getSysNotice(Integer id) {
        Optional<SysNotice> sysNotice = sysNoticeRepository.findById(id);
        if (!sysNotice.isPresent()) {
            return Result.wrapErrorResult("SysNotice is not exist!");
        }
        return Result.wrapSuccessfulResult(sysNotice.get());
    }

    //获取所有notice
    public Result<List<SysNotice>> getAllSysNotice() {
        List<SysNotice> sysNoticeslist = sysNoticeRepository.findAll();
        return Result.wrapSuccessfulResult(sysNoticeslist);
    }

    //新增系统公告
    public Result<String> addSysNotice(String category, String title, String content){
        SysNotice sysNotice = new SysNotice();
        sysNotice.setCategory(category);
        sysNotice.setTitle(title);
        sysNotice.setContent(content);
        sysNotice.setUploadTime(new Date());
        sysNoticeRepository.save(sysNotice);

        return Result.wrapSuccessfulResult("系统公告创建成功！");
    }

    //删除某一系统公告
    public Result<String> deleteSysNotice(Integer sysNoticeId){
        sysNoticeRepository.deleteById(sysNoticeId);
        return Result.wrapSuccessfulResult("系统公告删除成功！");
    }

    //修改某一课程
    public Result<String> alterSysNotice(Integer sysNoticeId, String category, String title, String content){
        SysNotice sysNotice = sysNoticeRepository.getById(sysNoticeId);
        sysNotice.setCategory(category);
        sysNotice.setTitle(title);
        sysNotice.setContent(content);
        sysNotice.setUploadTime(new Date());
        sysNoticeRepository.save(sysNotice);

        return Result.wrapSuccessfulResult("系统公告更新成功！");
    }
}
