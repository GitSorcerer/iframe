package com.zccs.assets_management.controller;

import com.zccs.assets_management.pojo.ContractModel;
import com.zccs.assets_management.pojo.PapersModel;
import com.zccs.assets_management.service.ContractService;
import com.zccs.assets_management.service.PapersService;
import com.zccs.assets_management.utils.BaseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaoh
 * @version 1.0
 * @date 2019/7/11 13:06
 *
 * 合同
 */
@Controller
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @Autowired
    private PapersService papersService;


    /**
     * 合同上传
     * @param files
     * @param request
     * @return
     */
    @RequestMapping("/contractUpload")
    @ResponseBody
    public Map<String, Object> contractUpload(@RequestPart("files") MultipartFile[] files, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            contractService.contractUpload(files, request);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }


    /**
     * 查询列表
     * @param request
     * @return
     */
    @RequestMapping("/contractList")
    @ResponseBody
    public Map<String, Object> contractList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            List<ContractModel> contractModels = contractService.contractList(request);
            resultMap.put("data", contractModels);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }


    /**
     * 根据Id查询
     * @param request
     * @return
     */
    @RequestMapping("/contractById")
    @ResponseBody
    public Map<String, Object> contractById(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            ContractModel contract = new ContractModel();
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                contract.setId(Integer.parseInt(id));
            }
            contract  = contractService.contractById(contract);
            resultMap.put("data", contract);

            List<PapersModel> papersModelList = papersService.papersByObj(contract.getUuid());
            resultMap.put("papers", papersModelList);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }


    /**
     * 合同修改
     * @param files
     * @param request
     * @return
     */
    @RequestMapping("/contractUpdate")
    @ResponseBody
    public Map<String, Object> contractUpdate(@RequestPart("files") MultipartFile[] files, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            contractService.contractUpdate(files, request);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }


    /**
     * 合同删除
     * @param request
     * @return
     */
    @RequestMapping("/contractDel")
    @ResponseBody
    public Map<String, Object> contractDel(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            contractService.contractDel(request);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }


    /**
     * 时间查询
     * @param request
     * @return
     */
    @RequestMapping("/contractFindByTime")
    @ResponseBody
    public Map<String, Object> contractFindByTime(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            List<ContractModel> contractModels = contractService.contractFindByTime(request);
            resultMap.put("data", contractModels);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }


    //*************************经营合同预警**********************************************

    @RequestMapping("/contractAlarmList")
    @ResponseBody
    public Map<String, Object> contractAlarmList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(6);
        try {
            List<ContractModel> contractModels = contractService.contractAlarmList(request);
            resultMap.put("data", contractModels);
        } catch (Exception e) {
            resultMap.put("msg", BaseUtils.loggerError(e));
        }
        return resultMap;
    }




}
