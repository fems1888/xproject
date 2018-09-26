package com.qbao.xproject.app.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/3/2.
 */

public class BillSection extends SectionEntity<BillResponseEntity.BillContentResponse> {
    public BillSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public BillSection(BillResponseEntity.BillContentResponse rechargeResponse) {
        super(rechargeResponse);
    }


}
