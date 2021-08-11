package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsMemberCard;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsMemberCardQueryParam;
import com.io.yy.marketing.vo.CsMemberCardQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 会员卡 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-09
 */
public interface CsMemberCardService extends BaseService<CsMemberCard> {

    /**
     * 保存
     *
     * @param csMemberCard
     * @return
     * @throws Exception
     */
    boolean saveCsMemberCard(CsMemberCard csMemberCard) throws Exception;

    /**
     * 修改
     *
     * @param csMemberCard
     * @return
     * @throws Exception
     */
    boolean updateCsMemberCard(CsMemberCard csMemberCard) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMemberCard(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsMemberCards(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsMemberCardQueryVo getCsMemberCardById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMemberCardQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMemberCardQueryVo> getCsMemberCardPageList(CsMemberCardQueryParam csMemberCardQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csMemberCardQueryParam
     * @return
     */
     boolean updateStatus(CsMemberCardQueryParam csMemberCardQueryParam);


}
