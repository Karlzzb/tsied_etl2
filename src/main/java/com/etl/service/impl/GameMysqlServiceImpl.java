package com.etl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.dao.IGameDao;
import com.etl.dao.entity.GameMysql;
import com.etl.service.IGameService;

@Service("gameMysqlService")
public class GameMysqlServiceImpl implements IGameService{

	@Autowired
	private IGameDao gameDao;
	
	public List<GameMysql> findGameByGame(GameMysql game) {
		return gameDao.findGameByGame(game);
	}
	
}
