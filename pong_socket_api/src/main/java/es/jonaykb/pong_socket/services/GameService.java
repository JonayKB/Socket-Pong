package es.jonaykb.pong_socket.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import es.jonaykb.pong_socket.model.Game;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;

@Service
@Log
public class GameService {
    private static final int TICK_MS = 33;
    private final SimpMessagingTemplate messagingTemplate;
    List<Game> games = new ArrayList<>();

    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public List<Game> getAllGames() {
        return games;
    }

    public Game createGame() {
        Game game = new Game();
        games.add(game);
        return game;
    }

    public Game getGameById(String gameId) {
        return games.stream()
                .filter(game -> game.getId().equals(gameId))
                .findFirst()
                .orElse(null);
    }

    public Game getAvailableGame() {
        return games.stream()
                .filter(game -> !game.isActive())
                .findFirst()
                .orElse(null);
    }

    public void updateAllGamesPerTick() {
        for (Game game : games) {
            if (game.isActive()) {
                game.updatePerTick();
                String topic = "/topic/gameState/" + game.getId();
                messagingTemplate.convertAndSend(topic, game);
            }
        }
    }

    public Game joinGame(String gameId, String playerName) {
        Game game = getGameById(gameId);
        if (game != null) {
            if (game.getPlayer1() == null) {
                game.setPlayer1(playerName);
                return game;
            } else if (game.getPlayer2() == null) {
                game.setPlayer2(playerName);
                game.setActive(true);
            } else {
                return null; // El juego ya tiene dos jugadores
            }
            return game;
        }
        return null; // Juego no encontrado
    }

    public Game movePaddle(String gameId, double paddleY, String playerName) {
        Game game = getGameById(gameId);
        if (game != null && game.isActive()) {
            if (game.getPlayer1().equals(playerName)) {
                game.getPositions().setPaddle1Y(paddleY);
            } else if (game.getPlayer2().equals(playerName)) {
                game.getPositions().setPaddle2Y(paddleY);
            }
            return game;
        }
        return null; // Juego no encontrado
    }

    public void startGameLoop() {
        Thread gameLoopThread = new Thread(() -> {
            while (true) {
                updateAllGamesPerTick();
                try {
                    Thread.sleep(TICK_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        gameLoopThread.setDaemon(true);
        gameLoopThread.start();
    }

    @PostConstruct
    public void init() {
        startGameLoop();
    }

}
