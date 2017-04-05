package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.scene.control.Button;


public class GameOfLife {

    public Rules RuleType = new ConwaysRules();
    private byte[][] board;
    private Timeline timeline = new Timeline();
    private Controller context;
    @FXML private Button playButton;


    // GraphicsContext gc = canvas.getGraphicsContext2D(); Hvis den ligger ute kan den nas av alle metoder men ligger på minne hele tiden, best utenfor eller inni metodene?

    public void setBoard(byte[][] board, Controller context) {
        this.board = board;
        this.context = context;
    }

    public void animation() {
        //JAVA ANIMASJON
        KeyFrame frame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nextGeneration();
                context.draw();
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startGame() {
        //Checks if the animation is running
    if (timeline.getStatus() == Animation.Status.RUNNING) {
        timeline.stop();
        playButton.setText("Play");
    } else {
        timeline.play();
        playButton.setText("Pause");
    }
    }

    public void nextGeneration(){
        if(board != null){
            byte[][] nyttBrett = new byte[board.length][board[0].length];

            for(int y=0; y < board.length; y++){
                for (int x=0; x < board[0].length; x++){
                    nyttBrett[y][x] = (byte) RuleType.overlever(board[y][x], countNeighbours(y, x));
                }
            }
            this.board = nyttBrett;
        }
    }

    private int countNeighbours(int y, int x) {

        int livingNeighbours = 0;
            for (int i = -1; i <= 1; i++) {
                for (int k = -1; k <= 1; k++) {
                    if (k == 0 && i == 0) { // Remove self testing
                        continue;
                    }
                    try { // Catch outside borders
                        if (board[y + i][x + k] == 1) { // If cell state alive
                            livingNeighbours++;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {//Outside borders}
                    }
                }
            }
        return livingNeighbours;
    }

    public byte[][] getBoard(){
        return board;
    }

    public Timeline getTimeline(){
        return timeline;
    }

    public void deleteBoard(){
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            playButton.setText("Play");
        }
        board = null;

    }
}
