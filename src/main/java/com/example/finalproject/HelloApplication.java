package com.example.finalproject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloApplication extends Application {
    static int speed = 3;
    static int color = 0;
    static int width = 20;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornerSize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Direction direction = Direction.left;
    static boolean gameOver = false;
    static Random random = new Random();

    public void start(Stage stage) {
        try {
            newFood();
            VBox root = new VBox();
            Canvas canvas = new Canvas(width * cornerSize, height * cornerSize);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(graphicsContext);
                        return;
                    }
                    if ((now - lastTick) > (1000000000 / speed)) {
                        lastTick = now;
                        tick(graphicsContext);
                    }
                }

            }.start();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(true); // Установите полноэкранный режим при запуске
            stage.setFullScreenExitHint(""); // Опционально: убирает подсказку при выходе из полноэкранного режима

            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.ESCAPE) {
                    stage.setFullScreen(false); // При нажатии на ESC выходите из полноэкранного режима
                }
                if (key.getCode() == KeyCode.UP && direction != Direction.down) {
                    direction = Direction.up;
                }
                if (key.getCode() == KeyCode.LEFT && direction != Direction.right) {
                    direction = Direction.left;
                }
                if (key.getCode() == KeyCode.DOWN && direction != Direction.up) {
                    direction = Direction.down;
                }
                if (key.getCode() == KeyCode.RIGHT && direction != Direction.left) {
                    direction = Direction.right;
                }
            });

            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));

            stage.setTitle("Snake Game");
            stage.setFullScreen(false); // Отключить полноэкранный режим
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tick(GraphicsContext graphicsContext) {
        if (gameOver) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("", 50));
            graphicsContext.fillText("Game over", 100, 250);
            return;
        }
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    gameOver = true;
                    break;
                }
                break;

            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= height) {
                    gameOver = true;
                    break;
                }
                break;

            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    gameOver = true;
                    break;
                }
                break;

            case right:
                snake.get(0).x++;
                if (snake.get(0).x >= width) {
                    gameOver = true;
                    break;
                }
                break;
        }

        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, width * cornerSize, height * cornerSize);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("", 30));
        graphicsContext.fillText("Score: " + (speed-4), 10, 30); // Начальный балл теперь 0

        Color cc = Color.WHITE;

        switch (color) {
            case 0:
                cc = Color.PURPLE;
                break;
            case 1:
                cc = Color.LIGHTBLUE;
                break;
            case 2:
                cc = Color.YELLOW;
                break;
            case 3:
                cc = Color.PINK;
                break;
            case 4:
                cc = Color.ORANGE;
                break;
        }
        graphicsContext.setFill(cc);
        graphicsContext.fillOval(foodX * cornerSize, foodY * cornerSize, cornerSize, cornerSize);

        for (Corner c : snake) {
            graphicsContext.setFill(Color.LIGHTBLUE);
            graphicsContext.fillRect(c.x * cornerSize, c.y * cornerSize, cornerSize - 1, cornerSize);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(c.x * cornerSize, c.y * cornerSize, cornerSize - 2, cornerSize - 2);
        }
    }

    public static void newFood() {
        start: while (true) {
            foodX = random.nextInt(width);
            foodY = random.nextInt(height);

            for (Corner corner : snake) {
                if (corner.x == foodX && corner.y == foodY) {
                    continue start;
                }
            }
            color = random.nextInt(5);
            speed++;
            break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
