package com.zccs.assets_management.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.zccs.assets_management.exception.BusinessException;
import net.sf.json.util.JSONTokener;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: GH
 * @Date: 2019/6/30 10:34
 * @Version 1.0
 *
 * 通用方法
 */
public class BaseUtils {
    // slf4j-Logger
    private static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);


    /**
     * 日志输出(普通)
     * @param str
     */
    public static void loggerDebug(String str) {
        logger.debug(str);
    }

    /**
     * 日志输出(占位符)
     * @param str
     * @param obj
     */
    public static void loggerDebug(String str, Object[] obj) {
        logger.debug(str, obj);
    }

    /**
     * 警告日志输出(普通)
     * @param str
     */
    public static void loggerWarn(String str) {
        logger.warn(str);
    }

    /**
     * 警告日志输出(占位符)
     * @param str
     */
    public static void loggerWarn(String str, Object[] obj) {
        logger.warn(str, obj);
    }

    /**
     * 异常日志输出
     * @param e
     * @return
     */
    public static String loggerError(Throwable e) {
        String msg = "";
        if (e instanceof NullPointerException) {
            msg = e.toString();
        } else {
            msg = e.getMessage();
        }
        logger.error(msg, e);
        return msg;
    }

    /**
     * 将序列化的表单转化为model
     * @param model
     * @param data
     */
    public static void serializeArray2Model(Object model, String data)  {
        Map dataMap = jsonArrStr2Map(data);
        try {
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0); // 解决Bigdecimal为null报错问题
            //ConvertUtils.register(new DateLocaleConverter(), Date.class); // Date注册的转化器
            //2019-04-08 17:27:56
            ConvertUtils.register(new Converter(){
                public Object convert(Class arg0, Object arg1){
                    if(!arg0.getName().equals("java.util.Date")){
                        return null;
                    }
                    BaseUtils.loggerDebug("注册字符串转换date类型转换器");
                    if(arg1 == null){
                        return null;
                    }
                    // 毫秒数处理
                    if(arg1 instanceof Long && (((Long)arg1).toString()).length() == 13){
                        // 强制转毫秒数
                        Date timeMillis = new Date();
                        try{
                            timeMillis.setTime((Long)arg1);
                            return timeMillis;
                        }catch(Exception e4){
                            BaseUtils.loggerDebug("非时间戳字符串或暂未支持的日期字符串类型：{}", new Object[]{arg1});
                            return arg1;
                        }
                    }
                    if(!(arg1 instanceof String)){
                        return arg1;
                    }else{
                        String str = (String)arg1;
                        if(StringUtils.isBlank(str)){
                            return null;
                        }
                        // 开始转换日期
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try{
                            return sd.parse(str);
                        }catch(ParseException e){
                            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try{
                                return sd1.parse(str);
                            }catch(ParseException e1){
                                SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
                                try{
                                    return sd2.parse(str);
                                }catch(ParseException e2){
                                    SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd");
                                    try{
                                        return sd3.parse(str);
                                    }catch(ParseException e3){
                                        BaseUtils.loggerDebug("非日期字符串或暂未支持的日期字符串类型：{}", new Object[]{str});
                                        return arg1;
                                    }
                                }
                            }
                        }
                    }
                }
            }, Date.class); // Date注册的转化器（扩展）

            org.apache.commons.beanutils.BeanUtils.populate(model, dataMap);
        } catch (Exception e) {
            loggerError(e);
            throw new BusinessException("serializeArray转换model失败，"+e.getMessage());
        }
    }


    /**
     * 将request参数转化为model
     * @param request
     * @return
     */
    public static void request2Model(Object model, HttpServletRequest request) { // 返回值为随意的类型传入数为request和该随意类型
        try {
            Enumeration en = request.getParameterNames(); //获得参数的一个列举
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);// 解决Bigdecimal为null报错问题
            //ConvertUtils.register(new DateLocaleConverter(), Date.class); // Date注册的转化器

            ConvertUtils.register(new Converter(){
                public Object convert(Class arg0, Object arg1){
                    if(!arg0.getName().equals("java.util.Date")){
                        return null;
                    }
                    BaseUtils.loggerDebug("注册字符串转换date类型转换器");
                    if(arg1 == null){
                        return null;
                    }
                    // 毫秒数处理
                    if(arg1 instanceof Long && (((Long)arg1).toString()).length() == 13){
                        // 强制转毫秒数
                        Date timeMillis = new Date();
                        try{
                            timeMillis.setTime((Long)arg1);
                            return timeMillis;
                        }catch(Exception e4){
                            BaseUtils.loggerDebug("非时间戳字符串或暂未支持的日期字符串类型：{}", new Object[]{arg1});
                            return arg1;
                        }
                    }
                    if(!(arg1 instanceof String)){
                        return arg1;
                    }else{
                        String str = (String)arg1;
                        if(StringUtils.isBlank(str)){
                            return null;
                        }
                        // 开始转换日期
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try{
                            return sd.parse(str);
                        }catch(ParseException e){
                            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try{
                                return sd1.parse(str);
                            }catch(ParseException e1){
                                SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
                                try{
                                    return sd2.parse(str);
                                }catch(ParseException e2){
                                    SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd");
                                    try{
                                        return sd3.parse(str);
                                    }catch(ParseException e3){
                                        BaseUtils.loggerDebug("非日期字符串或暂未支持的日期字符串类型：{}", new Object[]{str});
                                        return arg1;
                                    }
                                }
                            }
                        }
                    }
                }
            }, Date.class); // Date注册的转化器（扩展）

            while (en.hasMoreElements()) { //遍历列举来获取所有的参数
                String name = (String) en.nextElement();
                String value = BaseUtils.encodeUTF8(request.getParameter(name));
                org.apache.commons.beanutils.BeanUtils.setProperty(model, name, value.trim());
            }
        } catch (Exception e) {
            BaseUtils.loggerError(e);
            throw new BusinessException("request转换model失败，"+e.getMessage());
        }
    }



    /**
     * 将JSONArray字符串转成map(支持JSON对象，数组只支持name： value：形式)，前台jquery serializeArray()方法专用
     * @param data
     * @return
     */
    public static Map<String, Object> jsonArrStr2Map(String data) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 获得你字符串所属的对象
        Object json = new JSONTokener(data).nextValue();
        if(json instanceof net.sf.json.JSONObject){
            JSONObject jsonObj = JSONObject.parseObject(data);
            resultMap = JSONObject.parseObject(jsonObj.toJSONString(), new TypeReference<Map<String, Object>>(){});
        }else if (json instanceof net.sf.json.JSONArray){
            JSONArray jsonArray = JSONObject.parseArray(data);

            for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObj = (JSONObject)iterator.next();
                resultMap.put(jsonObj.getString("name"), jsonObj.getString("value"));
            }
        }else{
            throw new BusinessException("无效的JSON字符串！");
        }
        return resultMap;
    }



    /**
     * URL UTF-8 转码
     *
     * @param str
     * @return
     */
    public static String encodeUTF8(String str) {
        try {
            if (StringUtils.isBlank(str)) {
                return "";
            }
            if ("undefined".equals(str)) {
                return "";
            }
            if (str.equals(new String(str.getBytes("ISO8859-1"), "ISO8859-1"))) {
                return new String(str.getBytes("ISO8859-1"), "UTF-8").trim();
            } else {
                return str;
            }
        } catch (UnsupportedEncodingException e) {
            BaseUtils.loggerError(e);
            return "";
        }
    }


    /**
     * 获取 文件上传本地磁盘路径  图片
     * @return
     */
    public static String getLocalPath() {
//        String localPath = "C:ZZCS" + File.separator + "~usr" + File.separator + "local" + File.separator + "ZCCS";
        String localPath = "D:"+ File.separator +"assets_management"+ File.separator +"upload"+ File.separator +"picture"+ "~usr" + File.separator + "local" + File.separator + "ZCCS";
        if(StringUtils.isBlank(localPath)){
            throw new BusinessException("未找到文件上传本地磁盘路径配置！");
        }
        String[] localPathArr = localPath.split("~");
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return localPathArr[0];
        } else {
            if(localPathArr.length < 2){
                throw new BusinessException("未找到文件上传本地磁盘Linux路径配置！");
            }
            return localPathArr[1];
        }
    }

    /**
     * 删除文件
     * @param path
     * @throws Exception
     */
    public static void deleteFile(String path) {
        if (StringUtils.isNotBlank(path)) {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 删除目录下所有文件及文件夹
     * @param path
     */
    public static void deleteDirectory(String path) {
        if (StringUtils.isNotBlank(path)) {
            File file = new File(path);
            //1級文件刪除
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                //2級文件列表
                String[] filelist = file.list();
                //获取新的二級路徑
                for (int j = 0; j < filelist.length; j++) {
                    File filessFile = new File(path + "\\" + filelist[j]);
                    if (!filessFile.isDirectory()) {
                        filessFile.delete();
                    } else if (filessFile.isDirectory()) {
                        //递归調用
                        deleteDirectory(path + "\\" + filelist[j]);
                    }
                }
                file.delete();
            }
        }
    }


    /**
     * 求两个日期的时间差
     *
     * @param startTime
     * @param endTime
     * @return 相差的天数
     */
    public static int getDistanceTime(Date startTime, Date endTime) {
        int days = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    /**
     * 计算时间差，以小时为单位。如：2018-08-08 和 2018-08-07 相差24h
      * @param startTime
     * @param endTime
     * @return
     */
    public double getDistanceTime2(Date startTime, Date endTime) {
        double hour = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        hour = (diff / (60 * 60 * 1000));
        return hour;
    }


    /**
     * 返回日期前    天/月的时间
     * @param date   日期
     * @param days   前多少天/月
     * @param param   天/月
     * @return
     */
    public static Date getDate(Date date,Integer days,String param) {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        if ("DATE".equalsIgnoreCase(param)) {//日
            calendar.add(Calendar.DATE, -days);
        }else if("MONTH".equalsIgnoreCase(param)) {//月
            calendar.add(Calendar.MONTH, -days);
        }
        return calendar.getTime();
    }



    /**
     * 获取 文件上传本地磁盘路径  文件
     * @return
     */
    public static String getFilePath() {
        String localPath = "D:"+ File.separator +"assets_management"+ File.separator +"upload"+ File.separator +"contract"+ "~usr" + File.separator + "local" + File.separator + "ZCCS";
        if(StringUtils.isBlank(localPath)){
            throw new BusinessException("未找到文件上传本地磁盘路径配置！");
        }
        String[] localPathArr = localPath.split("~");
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return localPathArr[0];
        } else {
            if(localPathArr.length < 2){
                throw new BusinessException("未找到文件上传本地磁盘Linux路径配置！");
            }
            return localPathArr[1];
        }
    }




    /**
     * 获取客户端浏览器类型、编码下载文件名
     *
     * @param request
     * @param fileName
     * @return String
     */
    public static String encodeFileName(HttpServletRequest request, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        String rtn = "";
        try {
            String new_filename = URLEncoder.encode(fileName, "UTF8");
            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            rtn = "filename=\"" + new_filename + "\"";
            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();
                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("msie") != -1) {
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("opera") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("safari") != -1) {
                    rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
                }
                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.indexOf("applewebkit") != -1) {
                    new_filename = MimeUtility.encodeText(fileName, "UTF8", "B");
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("mozilla") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
