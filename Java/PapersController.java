package com.zccs.assets_management.controller;

import com.zccs.assets_management.pojo.PapersModel;
import com.zccs.assets_management.service.PapersService;
import com.zccs.assets_management.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author gaoh
 * @version 1.0
 * @date 2019/7/12 13:38
 *
 * 文件管理
 */
@Controller
public class PapersController {
    @Autowired
    private PapersService papersService;

    /**
     * 文件下载
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/download")
    public String download(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        PapersModel papersModel = new PapersModel();
        papersModel.setId(Integer.parseInt(id));

        papersModel = papersService.selectByPrimaryKey(papersModel);

        String fileName = papersModel.getFilename();
        String filePath = BaseUtils.getFilePath() + papersModel.getFileurl();
                response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        String downLoadPath = filePath;  //注意不同系统的分隔符
        //	String downLoadPath =filePath.replaceAll("/", "\\\\\\\\");   //replace replaceAll区别 *****
        BaseUtils.loggerDebug(downLoadPath);

        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
//            String decode = URLDecoder.decode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  new String(fileName.getBytes("utf-8"), "ISO8859-1"));

            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }
}
