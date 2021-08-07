package com.io.yy.merchant.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.io.yy.merchant.entity.CsTearoom;
import com.io.yy.merchant.mapper.CsTearoomMapper;
import com.io.yy.merchant.service.CsTearoomService;
import com.io.yy.merchant.param.CsTearoomQueryParam;
import com.io.yy.merchant.vo.CsTearoomExportQueryVo;
import com.io.yy.merchant.vo.CsTearoomQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.util.HttpServletResponseUtil;
import com.io.yy.util.collect.ListUtils;
import com.io.yy.util.excel.ExcelExport;
import com.io.yy.util.excel.annotation.ExcelField;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <pre>
 * 茶室管理 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Slf4j
@Service
public class CsTearoomServiceImpl extends BaseServiceImpl<CsTearoomMapper, CsTearoom> implements CsTearoomService {

    @Autowired
    private CsTearoomMapper csTearoomMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsTearoom(CsTearoom csTearoom) throws Exception {
        return super.save(csTearoom);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsTearoom(CsTearoom csTearoom) throws Exception {
        return super.updateById(csTearoom);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsTearoom(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsTearooms(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsTearoomQueryVo getCsTearoomById(Serializable id) throws Exception {
        return csTearoomMapper.getCsTearoomById(id);
    }

    @Override
    public Paging<CsTearoomQueryVo> getCsTearoomPageList(CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Page page = setPageParam(csTearoomQueryParam, OrderItem.desc("create_time"));
        IPage<CsTearoomQueryVo> iPage = csTearoomMapper.getCsTearoomPageList(page, csTearoomQueryParam);
        return new Paging(iPage);
    }

    @Override
    public Paging<CsTearoomQueryVo> getCsTearoomObjPageList(CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Page page = setPageParam(csTearoomQueryParam, OrderItem.desc("create_time"));
        IPage<CsTearoomQueryVo> iPage = csTearoomMapper.getCsTearoomObjPageList(page, csTearoomQueryParam);
        return new Paging(iPage);
    }

    @Override
    public Paging<CsTearoomQueryVo> getCsTearoomPageListOrderBySort(CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Page page = setPageParam(csTearoomQueryParam, OrderItem.desc("sort"));
        IPage<CsTearoomQueryVo> iPage = csTearoomMapper.getCsTearoomPageList(page, csTearoomQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsTearoomQueryParam csTearoomQueryParam) {
        return csTearoomMapper.updateStatus(csTearoomQueryParam) > 0;
    }

    /**
     * 导出
     *
     * @param csTearoomQueryParam
     * @throws Exception
     */
    @Override
    public void exportList(CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Page page = setPageParam(csTearoomQueryParam, OrderItem.desc("create_time"));
        IPage<CsTearoomQueryVo> iPage = csTearoomMapper.getCsTearoomPageList(page, csTearoomQueryParam);

        List<CsTearoomQueryVo> list = iPage.getRecords();
        List<CsTearoomExportQueryVo> exportList = new ArrayList<CsTearoomExportQueryVo>();
        Iterator<CsTearoomQueryVo> iter = list.iterator();
        while(iter.hasNext()){
            CsTearoomExportQueryVo exportVo = new CsTearoomExportQueryVo();
            BeanUtils.copyProperties(iter.next(),exportVo);
            exportList.add(exportVo);
        }

        // 创建一个Sheet表，并导入数据
        ExcelExport ee = new ExcelExport("茶室列表",CsTearoomExportQueryVo.class, ExcelField.Type.ALL);
        ee.setDataList(exportList);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        ee.write(HttpServletResponseUtil.getResponse(), "茶室列表" + time + ".xlsx");
        // 清理销毁
        ee.close();
        log.debug("Export success.");

    }

}
