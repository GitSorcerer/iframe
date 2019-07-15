package com.zccs.assets_management.service;

import com.alibaba.fastjson.JSONObject;
import com.zccs.assets_management.dao.ContractDao;
import com.zccs.assets_management.dao.CustomerDao;
import com.zccs.assets_management.dao.PictureDao;
import com.zccs.assets_management.exception.BusinessException;
import com.zccs.assets_management.pojo.ContractModel;
import com.zccs.assets_management.pojo.CustomerModel;
import com.zccs.assets_management.utils.BaseUtils;
import com.zccs.assets_management.utils.CryptographyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author gaoh
 * @version 1.0
 * @date 2019/7/11 13:06
 */
@Service
public class ContractService {
    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PapersService papersService;

    @Autowired
    private PictureDao pictureDao;

    /**
     * 上传
     * @param files
     * @param request
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void contractUpload(MultipartFile[] files, HttpServletRequest request) throws Exception {
        BaseUtils.loggerDebug("合同上传!");

        ContractModel contractModel = new ContractModel();

        BaseUtils.request2Model(contractModel, request);

        //UUid+时间戳  确保唯一性
        String uuid = CryptographyUtils.getUUID() + System.currentTimeMillis();

        System.out.println("len:" + uuid.length());

        //将保存合同信息
        contractModel.setCreatedate(new Date());
        contractModel.setUuid(uuid);
        contractDao.insertSelective(contractModel);


        //修改客户的状态  改为已签
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(contractModel.getCustomerid());
        customerModel.setCustomerstatus("02");
        customerDao.updateByPrimaryKeySelective(customerModel);


        papersService.uploadFile(files, "operate", uuid, contractModel.getReserve1());

        BaseUtils.loggerDebug(JSONObject.toJSONString(contractModel));

    }

    /**
     * 查询所以信息
     * @param request
     * @return
     */
    public List<ContractModel> contractList(HttpServletRequest request) {
        String typeId = request.getParameter("typeId");
        Example example = new Example(ContractModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(typeId)) {
            criteria.andEqualTo("contracttypeid", typeId);
            return contractDao.selectByExample(example);
        }
        return contractDao.selectAll();
    }

    //根据主键查询
    public ContractModel contractById(ContractModel contract) {
        return contractDao.selectByPrimaryKey(contract);
    }


    /**
     * 修改
     * @param files
     * @param request
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void contractUpdate(MultipartFile[] files, HttpServletRequest request) throws Exception {
        BaseUtils.loggerDebug("合同修改!");

        ContractModel contractModel = new ContractModel();

        BaseUtils.request2Model(contractModel, request);

        contractDao.updateByPrimaryKeySelective(contractModel);

      /*  String sql = "SELECT p.* FROM papers p where p.objId='" + contractModel.getUuid() + "'";
        List<Map<String, Object>> maps = pictureDao.selectListBySQL(sql);*/

        //修改客户的状态  改为已签
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(contractModel.getCustomerid());
        customerModel.setCustomerstatus("02");
        customerDao.updateByPrimaryKeySelective(customerModel);

        //如果有文件
        if (!(files.length == 0)) {
            //删除之前的文件
            papersService.delFile(contractModel.getUuid());
            //上传新的文件
            papersService.uploadFile(files, "operate", contractModel.getUuid(), contractModel.getReserve1());
        }

        BaseUtils.loggerDebug(JSONObject.toJSONString(contractModel));
    }

    /**
     * 删除合同
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void contractDel(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            ContractModel contractModel = new ContractModel();
            contractModel.setId(Integer.parseInt(id));
            String sql = "SELECT t.uuid FROM contract t where t.id='" + id + "'";
            List<Map<String, Object>> maps = pictureDao.selectListBySQL(sql);
            if (maps.size() > 0) {
                String uuid = maps.get(0).get("uuid").toString();
                //删除文件
                papersService.delFile(uuid);
            }
            contractDao.deleteByPrimaryKey(contractModel);
        }else {
            throw new BusinessException("请选择需要删除的数据!");
        }
    }

    /**
     * 按照时间查询  签署时间 datesign
     *
     * @param request
     * @return
     */
    public List<ContractModel> contractFindByTime(HttpServletRequest request) throws Exception {
        String type = request.getParameter("type");
        String begin = request.getParameter("begin");
        String end = request.getParameter("end");
        if (StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {
            throw new BusinessException("请选择需要查询的条件!");
        } else {
            Example example = new Example(ContractModel.class);
            Example.Criteria criteria = example.createCriteria();

            criteria.andEqualTo("contracttypeid", type);

            if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
                criteria.andBetween("datesign", begin, end);
            } else if (StringUtils.isNotBlank(begin) && StringUtils.isBlank(end)) {
                criteria.andCondition("datesign>=", begin);
            } else if (StringUtils.isBlank(begin) && StringUtils.isNotBlank(end)) {
                criteria.andCondition("datesign<=", end);
            }

            List<ContractModel> contractModels = contractDao.selectByExample(example);

            //过滤
            if (StringUtils.isNotEmpty(request.getParameter("filter"))) {
                Iterator<ContractModel> iterator = contractModels.iterator();
                while (iterator.hasNext()) {
                    ContractModel contract = iterator.next();
                    //预警时间  在 当前时间之后就移除
                    if (contract.getDatewarning().after(new Date())) {
                        iterator.remove();
                    }
                }
            }
            return contractModels;
        }

    }

    public List<ContractModel> contractAlarmList(HttpServletRequest request) {
        List<ContractModel> contractModels = contractDao.selectAll();
        Iterator<ContractModel> iterator = contractModels.iterator();
        while (iterator.hasNext()) {
            ContractModel contract = iterator.next();
            //预警时间  在 当前时间之后就移除
            if (contract.getDatewarning().after(new Date())) {
                iterator.remove();
            }
        }
        return contractModels;

    }
}
