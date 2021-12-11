package com.susu.se.service;

import com.susu.se.repository.AdministratorRepository;
import com.susu.se.repository.AssistantRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {
    @Autowired
    private AssistantRepository assistantRepository;

    public Result<String> deleteByID(Integer assistantId){
        assistantRepository.deleteById(assistantId);
        return Result.wrapSuccessfulResult("删除成功！");
    }
}
