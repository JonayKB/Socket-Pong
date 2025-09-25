package es.jonaykb.pong_socket.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import es.jonaykb.pong_socket.dto.PaddleMoveMessage;
import es.jonaykb.pong_socket.model.Game;
import es.jonaykb.pong_socket.services.GameService;
import lombok.extern.java.Log;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game/{id}")
    public Game getGame(@PathVariable String id) {
        Game game = gameService.getGameById(id);
        return game;
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/game/available")
    public Game getAvailableGame() {
        return gameService.getAvailableGame();
    }

    @PostMapping("/create")
    public Game createGame() {
        return gameService.createGame();
    }

    @PostMapping("/join")
    public Game joinGame(@RequestParam String gameId, @RequestParam String playerName) {
        return gameService.joinGame(gameId, playerName);
    }

    @MessageMapping("/movePaddle")
    @SendTo("/topic/gameState")
    public Game movePaddle(PaddleMoveMessage message) throws Exception {
        Game game = gameService.movePaddle(message.getGameId(), message.getPaddleY(), message.getPlayerName());
        return game;
    }

}
