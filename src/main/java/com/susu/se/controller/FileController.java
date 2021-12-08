package com.susu.se.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.susu.se.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("file")
@Slf4j
public class
FileController {
    @Value("${file.upload.dir}")
    private String uploadFilePath;

    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "upload";
    }

    @ResponseBody
    @PostMapping("/uploadFile")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile file) throws JSONException {
        JSONObject result = new JSONObject();
        if (file.isEmpty()) {
            result.put("error", "空文件!");
            return Result.wrapErrorResult(result.toString());
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传文件名称为:{}, 后缀名为:{}!", fileName, suffixName);

        File fileTempObj = new File(uploadFilePath + "/" + fileName);
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

        result.put("success", "文件上传成功!");
        return Result.wrapSuccessfulResult(result.toString());
    }

//    /**
//     * 多个文件上传
//     *
//     * @param files
//     * @return
//     * @throws JSONException
//     */
//    @ResponseBody
//    @PostMapping("/uploadFiles")
//    public String fileUploads(@RequestParam("files") MultipartFile files[]) throws JSONException {
//        JSONObject result = new JSONObject();
//
//        for (int i = 0; i < files.length; i++) {
//            String fileName = files[i].getOriginalFilename();
//            File dest = new File(uploadFilePath + '/' + fileName);
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }
//            try {
//                files[i].transferTo(dest);
//            } catch (Exception e) {
//                log.error("发生错误: {}", e);
//                result.put("error", e.getMessage());
//                return result.toString();
//            }
//        }
//        result.put("success", "文件上传成功!");
//        return result.toString();
//    }
//
//    /**
//     * 多个文件上传
//     *
//     * @param files
//     * @return
//     * @throws JSONException
//     */
//    @ResponseBody
//    @PostMapping("/uploadFiles02")
//    public String fileUploads(String name, @RequestParam("files") MultipartFile files[]) throws JSONException {
//        System.out.println(name);
//        JSONObject result = new JSONObject();
//
//        for (int i = 0; i < files.length; i++) {
//            String fileName = files[i].getOriginalFilename();
//            File dest = new File(uploadFilePath + '/' + fileName);
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }
//            try {
//                files[i].transferTo(dest);
//            } catch (Exception e) {
//                log.error("发生错误: {}", e);
//                result.put("code", 400);
//                result.put("error", e.getMessage());
//                return result.toString();
//            }
//        }
//        result.put("code", 200);
//        result.put("success", "文件上传成功!");
//        return result.toString();
//    }


    // 下载到了默认的位置
    @ResponseBody
    @GetMapping("/downloadFile/{fileName}")
    public Result<String> fileDownload(HttpServletResponse response, @PathVariable String fileName) throws JSONException, IOException {
        JSONObject result = new JSONObject();

        File file = new File(uploadFilePath + '/' + fileName);
        if (!file.exists()) {
            result.put("error", "下载文件不存在!");
            return Result.wrapErrorResult(result.toString());
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 原生的方式
        // try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
        //     byte[] buff = new byte[1024];
        //     OutputStream os = response.getOutputStream();
        //     int i = 0;
        //     while ((i = bis.read(buff)) != -1) {
        //         os.write(buff, 0, i);
        //         os.flush();
        //     }
        // } catch (IOException e) {
        //     log.error("发生错误: {}", e);
        //     result.put("error", e.getMessage());
        //     return result.toString();
        // }
        // 简单方式: 方式1
        // byte[] bytes = FileCopyUtils.copyToByteArray(file);
        // 简单方式: 方式2
        byte[] readBytes = FileUtil.readBytes(file);
        OutputStream os = response.getOutputStream();
        os.write(readBytes);
        result.put("success", "下载成功!");
        return Result.wrapSuccessfulResult(result.toString());
    }


    @ResponseBody
    @PostMapping("/deleteFile/{fileName}")
    public Result<String> deleteFile(HttpServletResponse response, @PathVariable String fileName) throws JSONException {
        JSONObject result = new JSONObject();

        File file = new File(uploadFilePath + '/' + fileName);
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
        result.put("success", "删除成功!");
        return Result.wrapSuccessfulResult(result.toString());
    }

}
