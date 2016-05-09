package com.etl.service;

import java.util.List;

import com.etl.dao.entity.GameMysql;

public interface IGameService {
	List<GameMysql> findGameByGame(GameMysql game);
}
