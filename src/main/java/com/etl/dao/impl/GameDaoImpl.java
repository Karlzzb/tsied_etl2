package com.etl.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.etl.dao.IGameDao;
import com.etl.dao.entity.GameMysql;

@Repository
public class GameDaoImpl extends SqlSessionDaoSupport implements IGameDao{

	@Override
	public List<GameMysql> findGameByGame(GameMysql game) {
		return this.getSqlSession().selectList("selectGame", game);
		
	}

}
