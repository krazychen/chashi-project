package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsMembercardConsumQueryParam;
import com.io.yy.marketing.vo.CsMembercardConsumQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 会员卡消费记录 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-24
 */
public interface CsMembercardConsumService extends BaseService<CsMembercardConsum> {

    /**
     * 保存
     *
     * @param csMembercardConsum
     * @return
     * @throws Exception
     */
    boolean saveCsMembercardConsum(CsMembercardConsum csMembercardConsum) throws Exception;

    /**
     * 修改
     *
     * @param csMembercardConsum
     * @return
     * @throws Exception
     */
    boolean updateCsMembercardConsum(CsMembercardConsum csMembercardConsum) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMembercardConsum(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsMembercardConsums(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsMembercardConsumQueryVo getCsMembercardConsumById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMembercardConsumQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMembercardConsumQueryVo> getCsMembercardConsumPageList(CsMembercardConsumQueryParam csMembercardConsumQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csMembercardConsumQueryParam
     * @return
     */
     boolean updateStatus(CsMembercardConsumQueryParam csMembercardConsumQueryParam);


}
