package com.etl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.dao.IPayDao;
import com.etl.dao.entity.AdPayStats;
import com.etl.service.IPayService;

@Service("payMysqlService")
public class PayMysqlServiceImpl implements IPayService {

	@Autowired
	private IPayDao payDao;

	@Override
	public List<AdPayStats> findPayByPay(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findPayByPay(adpay);
	}

	@Override
	public List<AdPayStats> findLoginUserByLoginUser(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findLoginUserByLoginUser(adpay);
	}

	@Override
	public List<AdPayStats> findUserCntByLoginUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findUserCntByLoginUserCnt(adpay);
	}

	@Override
	public List<AdPayStats> findPayCntByPayCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findPayCntByPayCnt(adpay);
	}

	@Override
	public List<AdPayStats> findRegUserCntByRegUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findRegUserCntByRegUserCnt(adpay);
	}

	@Override
	public List<AdPayStats> findLoginUserCntByLoginUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findLoginUserCntByLoginUserCnt(adpay);
	}

	@Override
	public List<AdPayStats> findRetRateByRetRate(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findRetRateByRetRate(adpay);
	}
	
	@Override
	public List<AdPayStats> findSumRegUserCntBySumRegUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findSumRegUserCntBySumRegUserCnt(adpay);
	}
	
	@Override
	public List<AdPayStats> findSumLoginUserCntBySumLoginUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return payDao.findSumLoginUserCntBySumLoginUserCnt(adpay);
	}

	@Override
	public void saveuser(List<AdPayStats> adpay) {
		payDao.saveUser(adpay);
	}
	
	@Override
	public void saverefer(List<AdPayStats> adpay) {
		payDao.saveRefer(adpay);
	}

	@Override
	public void updateLoginUser(AdPayStats adpay) {
		// TODO Auto-generated method stub
		payDao.updateLoginUser(adpay);
	}

}
