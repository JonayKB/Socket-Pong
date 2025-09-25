package es.jonaykb.pong_socket.model;

public class Positions {

    private Ball ball;
    private double paddle1Y;
    private double paddle2Y;

    public Positions() {
        this.ball = new Ball();
        this.paddle1Y = (Game.TABLE_HEIGHT - Game.PADDLE_HEIGHT) / 2;
        this.paddle2Y = (Game.TABLE_HEIGHT - Game.PADDLE_HEIGHT) / 2;
    }

    // Getters and Setters
    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public double getPaddle1Y() {
        return paddle1Y;
    }

    public void setPaddle1Y(double paddle1Y) {
        this.paddle1Y = paddle1Y;
    }

    public double getPaddle2Y() {
        return paddle2Y;
    }

    public void setPaddle2Y(double paddle2Y) {
        this.paddle2Y = paddle2Y;
    }

}
