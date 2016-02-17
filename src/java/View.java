import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
 
public class View extends Application {
    private GridPane grid = new GridPane();
    private static Circle[][] circles;
    private static Board board;
    public static int x = -1;
    public static int y = -1;

    public static void main(String[] args) {
        launch(args);
    }

    public static void hue(Board boarde) {
        board = boarde;
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connect Four");    
        x = y = -1;   
        circles = new Circle[board.board.length][board.board[0].length];

        for(int i = 0; i < board.board.length; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < board.board[0].length; i++) {
            RowConstraints row = new RowConstraints(100);
            grid.getRowConstraints().add(row);
        }

        for(int i = board.board.length-1; i > -1; i--){
            for(int j = 0; j < board.board[0].length; j++){
                Circle circle = new Circle();
                circle.setFill(Color.GRAY);
                int owner = board.board[i][j];
                if(owner==1){
                    circle.setFill(Color.RED);
                }
                if(owner==2){
                    circle.setFill(Color.GREEN);
                }
                circle.setCenterX(50.0f);
                circle.setCenterY(50.0f);
                circle.setRadius(50.0f);
                circles[i][j]=circle;
                final int finalX = i;
                final int finalY = j;
                circle.setOnMouseClicked((event) -> {
                    update(finalX,finalY);
                });
                grid.add(circle,i,board.board[0].length-j-1);
            }
        }

        Scene scene = new Scene(grid, (board.board.length)*100, (board.board[0].length)*100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void refresh(int column, int player) {
      for (int i = 0; i < circles[column].length; i++) {
        Circle circle = circles[column][i];
        int[] line = board.board[column];
        if (line[i] == Board.PLAYER_ONE)
          circle.setFill(Color.RED);
        else if (line[i] == Board.PLAYER_TWO) 
          circle.setFill(Color.GREEN);
      }
    }
}
