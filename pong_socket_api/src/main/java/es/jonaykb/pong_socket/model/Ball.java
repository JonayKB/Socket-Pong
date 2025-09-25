package es.jonaykb.pong_socket.model;

public class Ball {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    public Ball() {
        this.x = Game.TABLE_WIDTH / 2;
        this.y = Game.TABLE_HEIGHT / 2;
        this.velocityX = Math.random() < 0.5 ? -3 : 3; // Random velocity between -3 and 3 for X
        this.velocityY = Math.random() < 0.5 ? -3 : 3; // Random velocity between -3 and 3 for Y
        // Ensure velocity is not zero
        if (this.velocityX == 0) this.velocityX = 1;
        if (this.velocityY == 0) this.velocityY = 1;
    }

    // Getters and Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

}
