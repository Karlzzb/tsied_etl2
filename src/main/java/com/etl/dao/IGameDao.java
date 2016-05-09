package com.etl.dao;

import java.util.List;

import com.etl.dao.entity.GameMysql;

public interface IGameDao {
	List<GameMysql> findGameByGame(GameMysql game);
}
