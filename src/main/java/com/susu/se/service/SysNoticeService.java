package com.susu.se.service;

import com.susu.se.model.SysNotice;
import com.susu.se.repository.SysNoticeRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysNoticeService {

    @Autowired
    private SysNoticeRepository sysNoticeRepository;

    public Result<SysNotice> getSysNotice(Integer id) {
        Optional<SysNotice> sysNotice = sysNoticeRepository.findById(id);
        if (!sysNotice.isPresent()) {
            return Result.wrapErrorResult("SysNotice is not exist!");
        }
        return Result.wrapSuccessfulResult(sysNotice.get());
    }

    public Result<List<SysNotice>> getAllSysNotice() {
        List<SysNotice> sysNoticeslist = (List<SysNotice>) sysNoticeRepository.findAll();

        Boolean nullornot = sysNoticeslist.isEmpty();
        return new Result<List<SysNotice>>(nullornot, sysNoticeslist);
    }
}
