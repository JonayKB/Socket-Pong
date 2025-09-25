package es.jonaykb.pong_socket.model;

import java.util.UUID;

public class Game {
    public static final int TABLE_WIDTH = 800;
    public static final int TABLE_HEIGHT = 400;
    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 80;
    public static final int BALL_SIZE = 10;

    private String id;
    private String player1;
    private String player2;
    private int score1;
    private int score2;
    private Positions positions;
    private boolean isActive;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.score1 = 0;
        this.score2 = 0;
        this.positions = new Positions();
        this.isActive = false;
    }

    public void updatePerTick() {
        // Actualizar la posición de la pelota
        Ball ball = positions.getBall();
        ball.setX(ball.getX() + ball.getVelocityX());
        ball.setY(ball.getY() + ball.getVelocityY());
        ball.setVelocityX(ball.getVelocityX() * 1.001); // Aumentar velocidad X
        ball.setVelocityY(ball.getVelocityY() * 1.001); // Aumentar velocidad Y

        // Rebotar en las paredes superior e inferior
        if (ball.getY() <= 0 || ball.getY() >= TABLE_HEIGHT - BALL_SIZE) {
            ball.setVelocityY(-ball.getVelocityY());
        }

        // Rebotar en las palas
        if (ball.getX() <= PADDLE_WIDTH) {
            double paddle1Y = positions.getPaddle1Y();
            if (ball.getY() + BALL_SIZE >= paddle1Y && ball.getY() <= paddle1Y + PADDLE_HEIGHT) {
                ball.setVelocityX(-ball.getVelocityX());
            }
        } else if (ball.getX() >= TABLE_WIDTH - PADDLE_WIDTH - BALL_SIZE) {
            double paddle2Y = positions.getPaddle2Y();
            if (ball.getY() + BALL_SIZE >= paddle2Y && ball.getY() <= paddle2Y + PADDLE_HEIGHT) {
                ball.setVelocityX(-ball.getVelocityX());
            }
        }
        if (ball.getX() <= 0) {
            score2++;
            resetMap();

        } else if (ball.getX() >= TABLE_WIDTH - BALL_SIZE) {
            score1++;
            resetMap();
        }
    }

    private void resetMap() {
        positions = new Positions();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;

    }

    public Positions getPositions() {
        return positions;

    }

    public void setPositions(Positions positions) {
        this.positions = positions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
