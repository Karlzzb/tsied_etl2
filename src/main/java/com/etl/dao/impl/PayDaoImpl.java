package com.etl.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.etl.dao.IPayDao;
import com.etl.dao.entity.AdPayStats;


@Repository
public class PayDaoImpl extends SqlSessionDaoSupport implements IPayDao{

	@Override
	public List<AdPayStats> findPayByPay(AdPayStats adpay) {
		return this.getSqlSession().selectList("selectpay", adpay);		
	}

	@Override
	public List<AdPayStats> findLoginUserByLoginUser(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectloginuserpaycnt", adpay);
	}

	@Override
	public List<AdPayStats> findUserCntByLoginUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectusercnt", adpay);
	}
	
	public List<AdPayStats> findPayCntByPayCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectpaycnt", adpay);
	}
	
	@Override
	public List<AdPayStats> findRegUserCntByRegUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectregusercnt", adpay);
	}
	
	@Override
	public List<AdPayStats> findLoginUserCntByLoginUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectloginusercnt", adpay);
	}
	
	@Override
	public List<AdPayStats> findRetRateByRetRate(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectretrate", adpay);
	}
	
	@Override
	public List<AdPayStats> findSumRegUserCntBySumRegUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectsumregusercnt", adpay);
	}
	
	@Override
	public List<AdPayStats> findSumLoginUserCntBySumLoginUserCnt(AdPayStats adpay) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("selectsumloginusercnt", adpay);
	}
	
	@Override
	public void saveUser(List<AdPayStats> adpay) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert("saveuser", adpay);
	}
	
	@Override
	public void saveRefer(List<AdPayStats> adpay) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert("saverefer", adpay);
	}
	
	@Override
	public void updateLoginUser(AdPayStats adpay) {
		// TODO Auto-generated method stub
		this.getSqlSession().update("updateloginuser", adpay);
	}

//	@Override
//	public List<AdPayStats> findLoginuserByLoginuser(AdPayStats adpay) {
//		// TODO Auto-generated method stub
//		return null;
//	}



}
