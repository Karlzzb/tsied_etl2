package com.etl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.etl.dao.entity.GameMysql;
import com.etl.service.IGameService;

/**
 * @author Rainisic
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../applicationContext.xml")
public class GameMysqlTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private IGameService gameService;

	public IGameService getGameMysqlService() {
		return gameService;
	}

	public void setTestMysqlService(IGameService gameService) {
		this.gameService = gameService;
	}

	// @Test
	// public void saveTest() {
	// TestMysql test = new TestMysql();
	// test.setTestKey("啊伯特");
	// test.setTestValue(19);
	// //testMysqlService.saveTest(test);
	// }

	@Test
	public void findTest() {
		GameMysql game = new GameMysql();
		// game.setGid(0);
		List<GameMysql> gameList = gameService.findGameByGame(game);
		for (GameMysql gameMysql : gameList) {
			System.out.println("============" + gameMysql.getTitle());
			System.out.println("============" + gameMysql.getGid());
		}
	}
}
