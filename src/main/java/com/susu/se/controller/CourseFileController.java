package com.susu.se.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.susu.se.service.CourseFileService;
import com.susu.se.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("coursefiles")
@Slf4j
public class
CourseFileController {
    @Autowired
    private CourseFileService courseFileService;

    //在配置文件中获取上传文件的位置
    @Value("${file.upload.dir}")
    private String uploadFilePath;

    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "upload";
    }

    @ResponseBody
    @PostMapping("/uploadFile")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("courseid") Integer courseId,@RequestParam("teacherid") Integer teacherId) throws JSONException {
        return courseFileService.uploadFile(file,courseId,teacherId);
    }


    // 下载到了默认的位置
    //注意这个没写业务层，直接在这里全部实现了
    @ResponseBody
    @GetMapping("/downloadFile")
    public Result<String> fileDownload(HttpServletResponse response, @RequestParam("courseid") Integer courseId, @RequestParam("filename") String fileName) throws JSONException, IOException {
        JSONObject result = new JSONObject();
        System.out.println("----------------------------");
        System.out.println(uploadFilePath + '/' + courseId + "/" + fileName);
        File file = new File(uploadFilePath + '/' +courseId+"/"+ fileName);
        if (!file.exists()) {
            result.put("error", "下载文件不存在!");
            return Result.wrapErrorResult(result.toString());
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        byte[] readBytes = FileUtil.readBytes(file);
        OutputStream os = response.getOutputStream();
        os.write(readBytes);

        return courseFileService.downloadTimesPlusOne(fileName);
    }


    @ResponseBody
    @PostMapping("/deleteFile")
    public Result<String> deleteFile(HttpServletResponse response, @RequestParam("courseid") Integer courseId, @RequestParam("filename") String fileName) throws JSONException {
        return courseFileService.deleteFile(courseId, fileName);
    }

}
