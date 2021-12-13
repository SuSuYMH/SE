//package com.susu.se.service;
//
//import com.susu.se.model.Class;
//import com.susu.se.model.ConfrontPractice;
//import com.susu.se.model.TakeClass;
//import com.susu.se.repository.ClassRepository;
//import com.susu.se.repository.ConfrontPracticeRepository;
//import com.susu.se.repository.TakeClassRepository;
//import com.susu.se.utils.Return.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class ConfrontPracticeService {
//    @Autowired
//    private ConfrontPracticeRepository confrontPracticeRepository;
//
//    @Autowired
//    private ClassRepository classRepository;
//
//    @Autowired
//    private TakeClassRepository takeClassRepository;
//
//    //设置对抗练习
//    public Result<String> setConfrontPractice(Integer classId){
//        //随机选出班级内的三名学生
//        Optional<Class> byId = classRepository.findById(classId);
//        Class BanJi = byId.get();
//        List<TakeClass> takeClassList = takeClassRepository.findTakeClassesByKecheng(BanJi);
//        //要分成的组数量
//        Integer numOfGroup = takeClassList.size()/3;
//        //把顺序打乱
//
//
//        List<TakeClass> takeClassList1 = new ArrayList<>();
//        List<TakeClass> takeClassList2 = new ArrayList<>();
//        List<TakeClass> takeClassList0 = new ArrayList<>();
//
//        for(TakeClass takeClass:takeClassList){
//            if (takeClassList.indexOf(takeClass)%3==0) {
//                takeClassList0.add(takeClass);
//            }
//            if (takeClassList.indexOf(takeClass)%3==1) {
//                takeClassList1.add(takeClass);
//            }
//            if (takeClassList.indexOf(takeClass)%3==2) {
//                takeClassList2.add(takeClass);
//            }
//        }
//        //选出的三个人的题是一样的，但是得分互相没有关系
//        for(int i = 0;i<numOfGroup;i=i+1){
//            ConfrontPractice confrontPractice = new ConfrontPractice();
//            confrontPractice.setUploadTime(new Date());
//            confrontPractice.setTakeClass(takeClassList0.get(i));
//            //设置题，同一组的人选出的题是一样的
//            //////////。。。。。。
//            confrontPracticeRepository.save(confrontPractice);
//
//            ConfrontPractice confrontPractice1 = new ConfrontPractice();
//            confrontPractice1.setUploadTime(new Date());
//            confrontPractice1.setTakeClass(takeClassList0.get(i));
//            //设置题
//            //////////。。。。。。
//            confrontPracticeRepository.save(confrontPractice1);
//
//            ConfrontPractice confrontPractice2 = new ConfrontPractice();
//            confrontPractice2.setUploadTime(new Date());
//            confrontPractice2.setTakeClass(takeClassList0.get(i));
//            //设置题
//            //////////。。。。。。
//            confrontPracticeRepository.save(confrontPractice2);
//        }
//
//        //对于不是三的倍数的情况,还要将另外的一个或两个人分为一组
//        if(takeClassList.size()%3!=0){
//            if(takeClassList.size()%3==1){
//                ConfrontPractice confrontPractice = new ConfrontPractice();
//                confrontPractice.setUploadTime(new Date());
//                confrontPractice.setTakeClass(takeClassList0.get(numOfGroup));
//                //设置题，同一组的人选出的题是一样的
//                //////////。。。。。。
//                confrontPracticeRepository.save(confrontPractice);
//            }
//            if(takeClassList.size()%3==2){
//                ConfrontPractice confrontPractice = new ConfrontPractice();
//                confrontPractice.setUploadTime(new Date());
//                confrontPractice.setTakeClass(takeClassList0.get(numOfGroup));
//                //设置题，同一组的人选出的题是一样的
//                //////////。。。。。。
//                confrontPracticeRepository.save(confrontPractice);
//
//                ConfrontPractice confrontPractice1 = new ConfrontPractice();
//                confrontPractice1.setUploadTime(new Date());
//                confrontPractice1.setTakeClass(takeClassList0.get(numOfGroup));
//                //设置题
//                //////////。。。。。。
//                confrontPracticeRepository.save(confrontPractice1);
//            }
//        }
//
//
//
//
//    }
//}
