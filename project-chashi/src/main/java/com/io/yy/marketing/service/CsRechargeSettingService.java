package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsRechargeSetting;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsRechargeSettingQueryParam;
import com.io.yy.marketing.vo.CsRechargeSettingQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 充值设置 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
public interface CsRechargeSettingService extends BaseService<CsRechargeSetting> {

    /**
     * 保存
     *
     * @param csRechargeSetting
     * @return
     * @throws Exception
     */
    boolean saveCsRechargeSetting(CsRechargeSetting csRechargeSetting) throws Exception;

    /**
     * 修改
     *
     * @param csRechargeSetting
     * @return
     * @throws Exception
     */
    boolean updateCsRechargeSetting(CsRechargeSetting csRechargeSetting) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsRechargeSetting(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsRechargeSettings(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsRechargeSettingQueryVo getCsRechargeSettingById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csRechargeSettingQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsRechargeSettingQueryVo> getCsRechargeSettingPageList(CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csRechargeSettingQueryParam
     * @return
     */
     boolean updateStatus(CsRechargeSettingQueryParam csRechargeSettingQueryParam);


    /**
     * 获取启用的列表
     *
     * @param csRechargeSettingQueryParam
     * @return
     * @throws Exception
     */
    List<CsRechargeSettingQueryVo> getSettingListForWx(CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception;

}
