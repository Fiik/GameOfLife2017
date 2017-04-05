package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller extends GameOfLife implements Initializable{

    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider speedModifier;
    @FXML private Slider gameZoom;
    @FXML private TextField inputY;
    @FXML private TextField inputX;
    private double cellSize = 10;
    private Color dynamicColor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //gameZoom.setMin(1);
        //gameZoom.setMax(10);

        byte[][] testBrett = new byte [65][80];

        /*byte[][] testBrett = {
                { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
                { 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
                { 0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 1, 1, 1, 0, 0, 0, 0, 1, 1, 0},
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        };

        */

        setCanvas(canvas);
        setBoard(testBrett, this);
        animation();
        draw();
        grid();

    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void draw() {

        if(getBoard() != null) {
            canvas.setHeight(getBoard().length * cellSize);
            canvas.setWidth(getBoard()[0].length * cellSize);

            if (canvas != null) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(dynamicColor);
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                for (int i = 0; i < getBoard().length; i++) {
                    for (int j = 0; j < getBoard()[0].length; j++) {
                        if (getBoard()[i][j] == 1) {
                            gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    }
                }
            }
        }
        grid();
    }

    public void grid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for(double row=0;row<getBoard().length + 1;row++){
            gc.setStroke(Color.GREY);
            gc.setLineWidth(1);
            gc.strokeLine(0, row*cellSize, canvas.getWidth(), row*cellSize);

        }

        for(double column=0;column<getBoard()[0].length + 1;column++){
            gc.setStroke(Color.GRAY); //se om begge farger er lik.
            gc.setLineWidth(1);
            gc.strokeLine(column*cellSize, 0, column*cellSize, canvas.getHeight());
        }
    }

    public void clearEvent(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        deleteBoard();

        byte[][] testBrett = new byte [65][90];

        setCanvas(canvas);
        setBoard(testBrett, this);
        draw();
        grid();

    }

    public void playGame(ActionEvent actionEvent) {
        startGame();
    }

    public void nextEvent(ActionEvent actionEvent){
        nextGeneration();
        draw();
    }

    public void pause(ActionEvent actionEvent) {
        getTimeline().stop();
    }

    public void changeColor(ActionEvent actionEvent) {
        this.dynamicColor = colorPicker.getValue();
        draw();
    }

    public void speedMod(MouseEvent mouseEvent) {
        getTimeline().setRate(speedModifier.getValue()/10);
    }

    public void zoomScale(MouseEvent mouseEvent) {
        this.cellSize = gameZoom.getValue();
        draw();
    }

    public void drawCustomBord(ActionEvent actionEvent) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setTitle("Invalid input");
        error.setHeaderText(null);
        error.setContentText("To create a random board please use numbers between 1 - 500");

            try {
                if(Integer.valueOf(inputY.getText()) <= 500 && Integer.valueOf(inputX.getText()) <= 500){

                    createRandomBoard(Integer.valueOf(inputY.getText()), Integer.valueOf(inputX.getText()));
                    draw();
                }
                else{
                    error.showAndWait();
                }

            } catch (Exception e) {
                error.showAndWait();
            }
    }

    public void createRandomBoard(int inputY, int inputX){
        Random Generate = new Random();
        byte[][] customBoard = new byte[inputY][inputX];

        for (int y = 0; y < inputY; y++) {
            for (int x = 0; x < inputX; x++) {
                customBoard[y][x] = (byte)Generate.nextInt(2);
            }
        }
        setBoard(customBoard, this);
    }


    public void openFile(){
        FileHandler fileHandler = new FileHandler();
        fileHandler.openFile();
        byte[][] newBoard = fileHandler.getNewBoard();
        if(newBoard != null) {
            setBoard(newBoard, this);
            draw();
            grid();
        }

    }


    // Metoden tillater bruker å dra musen for å tegne nye celler.
    @FXML
    public void mouseDrag() {
        canvas.setOnMouseDragged(e -> {
            byte x = (byte) (e.getY() / cellSize);
            byte y = (byte) (e.getX() / cellSize);

            if(getBoard()[x][y] == 1) {
                getBoard()[x][y] = 1; // Beholder 1 da vi ikke ønsker å "fjerne" celler ved drafunksjon.
                draw();
            } else {
                getBoard()[x][y] = 1;
                draw();
            }
        }
        );
    }

    // tillater bruker å opprette/fjerne celler ved museklikk.
    @FXML
    public void mouseClick() {
        canvas.setOnMouseClicked(e -> {
                    byte x = (byte) (e.getY() / cellSize);
                    byte y = (byte) (e.getX() / cellSize);

                    if(getBoard()[x][y] == 1) {
                        getBoard()[x][y] = 0;
                        draw();
                    } else {
                        getBoard()[x][y] = 1;
                        draw();
                    }
                }
        );
    }
}

