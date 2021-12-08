package com.susu.se.service;

import com.susu.se.model.Evaluator;
import com.susu.se.model.SysNotice;
import com.susu.se.repository.EvaluatorRepository;
import com.susu.se.repository.SysNoticeRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluatorService {
    @Autowired
    private EvaluatorRepository Repository;

    public Result<Evaluator> getById(Integer id) {
        Optional<Evaluator> evaluator = Repository.findById(id);
        if (!evaluator.isPresent()) {
            return Result.wrapErrorResult("SysNotice is not exist!");
        }
        return Result.wrapSuccessfulResult(evaluator.get());
    }

    public Result<List<Evaluator>> getAll() {
        List<Evaluator> list = (List<Evaluator>) Repository.findAll();

        Boolean nullornot = list.isEmpty();
        return new Result<List<Evaluator>>(nullornot, list);
    }
}
