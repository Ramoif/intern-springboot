package com.ycsx.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ycsx.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

// 文件请求
@RestController
@RequestMapping("/files")
public class FileController {
    // 获取ip
    @Value("${server.port}")
    private String port;

    // 本地localhost 服务器ip地址
    private static final String ip = "http://localhost";
    private static final String onlineIp = "http://localhost";

    /**
     * 1、上传接口
     *
     * @param file
     * @return
     * @throws IOException
     */
    // 记住 - MultipartFile用于对前台上传数据进行处理。
    // @CrossOrigin解决跨域
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        // 1.1、根据文件名称进行存储(思考解决重名文件问题)，这里获取上传文件的名称
        String originalFilename = file.getOriginalFilename();
        // 1.2、定义文件唯一标识，解决重名问题，使用Hutool的唯一ID工具类
        String flag = IdUtil.fastSimpleUUID();
        // 2、获取上传的存储(绝对)路径，我们这里存到resources目录下的files文件夹内
        String rootFilePath = System.getProperty("user.dir")
                + "/src/main/resources/files/"
                + flag
                + "_"
                + originalFilename;
        // 3、使用Hutool工具类代替Java的IO工具类，把文件写入到上述路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        // 4、返回下载ip地址
        return Result.success(ip + ":" + port + "/files/" + flag);
    }

    /**
     * 2、下载接口，获取图片展示路径
     *
     * @param flag
     * @param response
     */

    // 根据唯一标识flag找文件
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        // 新建一个输出流对象
        OutputStream os;
        // 文件上传的根路径
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";
        // 获取所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(basePath);
        // 找到跟参数一致的文件
        String fileName = fileNames.stream()
                .filter(name -> name.contains(flag))
                .findAny()
                .orElse("");
        try {
            // 如果找到了，则说明可以下载
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                // 通过文件的路径读取文件字节流
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                // 通过输出流返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }


    /**
     * 3、富文本文件上传接口
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir")
                + "/src/main/resources/files/"
                + flag
                + "_"
                + originalFilename;
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        // 自定义一个Json来返回给富文本编辑器
        String url = ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        return json;
    }

}
