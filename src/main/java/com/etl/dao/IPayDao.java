package com.etl.dao;

import java.util.List;

import com.etl.dao.entity.AdPayStats;

public interface IPayDao {
	List<AdPayStats> findPayByPay(AdPayStats adpay);

	List<AdPayStats> findLoginUserByLoginUser(AdPayStats adpay);

	List<AdPayStats> findUserCntByLoginUserCnt(AdPayStats adpay);

	List<AdPayStats> findPayCntByPayCnt(AdPayStats adpay);

	List<AdPayStats> findRegUserCntByRegUserCnt(AdPayStats adpay);

	List<AdPayStats> findLoginUserCntByLoginUserCnt(AdPayStats adpay);

	List<AdPayStats> findRetRateByRetRate(AdPayStats adpay);
	
	List<AdPayStats> findSumRegUserCntBySumRegUserCnt(AdPayStats adpay);
	
	List<AdPayStats> findSumLoginUserCntBySumLoginUserCnt(AdPayStats adpay);

	void saveUser(List<AdPayStats> adpay);

	void saveRefer(List<AdPayStats> adpay);
	
	void updateLoginUser(AdPayStats adpay);
}
