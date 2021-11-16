package com.io.yy.merchant.service;

import com.io.yy.merchant.entity.CsTearoom;
import com.io.yy.common.service.BaseService;
import com.io.yy.merchant.param.CsTearoomQueryParam;
import com.io.yy.merchant.vo.CsTearoomQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 茶室管理 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
public interface CsTearoomService extends BaseService<CsTearoom> {

    /**
     * 保存
     *
     * @param csTearoom
     * @return
     * @throws Exception
     */
    boolean saveCsTearoom(CsTearoom csTearoom) throws Exception;

    /**
     * 修改
     *
     * @param csTearoom
     * @return
     * @throws Exception
     */
    boolean updateCsTearoom(CsTearoom csTearoom) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsTearoom(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsTearooms(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsTearoomQueryVo getCsTearoomById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csTearoomQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsTearoomQueryVo> getCsTearoomPageList(CsTearoomQueryParam csTearoomQueryParam) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csTearoomQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsTearoomQueryVo> getCsTearoomObjPageList(CsTearoomQueryParam csTearoomQueryParam) throws Exception;

    /**
     * 获取分页根据sort排序
     *
     * @param csTearoomQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsTearoomQueryVo> getCsTearoomPageListOrderBySort(CsTearoomQueryParam csTearoomQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csTearoomQueryParam
     * @return
     */
     boolean updateStatus(CsTearoomQueryParam csTearoomQueryParam);

    /**
     * 更新营业状态
     *
     * @param csTearoomQueryParam
     * @return
     */
    boolean updateReleaseStatus(CsTearoomQueryParam csTearoomQueryParam);

    /**
     * 导出
     * @param csTearoomQueryParam
     * @throws Exception
     */
    void exportList(CsTearoomQueryParam csTearoomQueryParam) throws Exception;
}
