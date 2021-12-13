package com.susu.se.service;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import com.susu.se.model.Course;
import com.susu.se.model.CourseFile;
import com.susu.se.model.users.Teacher;
import com.susu.se.repository.CourseFileRepository;
import com.susu.se.repository.CourseRepository;
import com.susu.se.repository.TeacherRepository;
import com.susu.se.utils.Return.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourseFileService {
    //在配置文件中获取上传文件的位置
    @Value("${file.upload.dir}")
    private String uploadFilePath;

    @Autowired
    private CourseFileRepository courseFileRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    //获取所有的文件
    public Result<List<CourseFile>> getAll(){
        List<CourseFile> CourseFilelist = (List<CourseFile>) courseFileRepository.findAll();

        Boolean nullornot = CourseFilelist.isEmpty();
        return new Result<>(nullornot, CourseFilelist);
    }

    //根据课程（course）id查课程所有的文件
    public Result<List<CourseFile>> getAllByCourseId(Integer id){
        Optional<Course> byId = courseRepository.findById(id);

        List<CourseFile> CourseFilelist = (List<CourseFile>) courseFileRepository.findByCourse(byId.get());

        Boolean nullornot = CourseFilelist.isEmpty();
        return new Result<>(nullornot, CourseFilelist);
    }

    //上传文件
    public Result<String> uploadFile(MultipartFile file, Integer courseId, Integer teacherId){
        //这个参数   file   就是文件流
        JSONObject result = new JSONObject();
        if (file.isEmpty()) {
            result.put("error", "空文件!");
            return Result.wrapErrorResult(result.toString());
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传文件名称为:{}, 后缀名为:{}!", fileName, suffixName);

        String pathname = uploadFilePath + "/" +courseId+"/"+ fileName;
        File fileTempObj = new File(pathname);
        // 检测目录是否存在
        if (!fileTempObj.getParentFile().exists()) {
            fileTempObj.getParentFile().mkdirs();
        }
        // 使用文件名称检测文件是否已经存在
        if (fileTempObj.exists()) {
            result.put("error", "文件已经存在!");
            return Result.wrapErrorResult(result.toString());
        }

        try {
            // 写入文件:方式1
            // file.transferTo(fileTempObj);
            // 写入文件:方式2
            FileUtil.writeBytes(file.getBytes(), fileTempObj);
        } catch (Exception e) {
            log.error("发生错误: {}", e);
            result.put("error", e.getMessage());
            return Result.wrapErrorResult(result.toString());
        }

        //创建CourseFile Entity对象
        CourseFile courseFile = new CourseFile();
        //这里要根据courseid查course
        Optional<Course> course = courseRepository.findById(courseId);
        courseFile.setCourse(course.get());
        //根据teacherid查teacher
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        courseFile.setTeacher(teacher.get());

        courseFile.setDownloadTimes(0);
        courseFile.setFileName(fileName);
        courseFile.setType(suffixName);
        courseFile.setUploadTime(new Date());
        courseFile.setPath(pathname);

        //注意这里还要改一改！！！返回标准格式
        courseFileRepository.save(courseFile);
        return Result.wrapSuccessfulResult("upload Success！！！");
    }

    //删除文件
    public Result<String> deleteFile(Integer courseId, String fileName){
        JSONObject result = new JSONObject();
        System.out.println("------------------------------------");
        System.out.println("uploadFilePath + '/' +courseId+\"/\"+ fileName");
        File file = new File(uploadFilePath + '/' +courseId+"/"+ fileName);
        // 判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            result.put("success", "文件不存在!");
            return Result.wrapErrorResult(result.toString());
        }
        try {
            if (file.isFile()) file.delete();
            else {
                // 文件夹, 需要先删除文件夹下面所有的文件, 然后删除
                for (File temp : file.listFiles()) {
                    temp.delete();
                }
                file.delete();
            }
        } catch (Exception e) {
            log.error("发生错误: {}", e);
            result.put("error", e.getMessage());
            return Result.wrapErrorResult(result.toString());
        }

        CourseFile byFileName = courseFileRepository.findByFileName(fileName);
        courseFileRepository.delete(byFileName);

        return Result.wrapSuccessfulResult("删除成功！");

    }

    //下载次数加1,根据file的name去查的，方便
    public Result<String> downloadTimesPlusOne(String fileName){
        CourseFile courseFile = courseFileRepository.findByFileName(fileName);
        Integer downloadTimes = courseFile.getDownloadTimes()+1;
        courseFile.setDownloadTimes(downloadTimes);
        courseFileRepository.save(courseFile);
        return Result.wrapSuccessfulResult("成功下载一次！");
    }


}
