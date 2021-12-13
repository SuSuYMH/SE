package com.susu.se.service;

import com.susu.se.model.users.Assistant;
import com.susu.se.model.users.Teacher;
import com.susu.se.repository.AssistantRepository;
import com.susu.se.repository.TeacherRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssistantService {
    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public Result<String> deleteByID(Integer assistantId){
        assistantRepository.deleteById(assistantId);
        return Result.wrapSuccessfulResult("删除成功！");
    }

    //老师选择一个助教
    public Result<String> chooseTA(Integer teacherId, Integer assistantId){
        Optional<Teacher> byId = teacherRepository.findById(teacherId);
        Teacher teacher = byId.get();

        Optional<Assistant> byId1 = assistantRepository.findById(assistantId);
        Assistant assistant = byId1.get();

        assistant.setTeacher(teacher);
        assistantRepository.save(assistant);
        return Result.wrapSuccessfulResult("选择助教成功！");
    }
}
