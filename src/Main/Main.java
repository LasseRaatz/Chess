package Main;

import Controller.MoveHistory;
import Controller.MoveHistoryImpl;
import Controller.SetupController;
import Controller.StandardSetup;
import Controller.Controller;
import Controller.MovementController;
import Controller.MovementControllerImpl;
import Controller.GameStateController;
import Controller.GameStateControllerImpl;
import Model.Model;
import View.View;
import View.ViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MoveHistory history = new MoveHistoryImpl();
        SetupController sc = new StandardSetup();
        Model model = new Model(sc, history);
        MovementController mc = new MovementControllerImpl(model.getBoard());
        GameStateController gc = new GameStateControllerImpl(model);
        Controller controller = new Controller(mc, gc, model);
        View view = new ViewImpl(model);

        primaryStage.setTitle("Skak");
        Button button = new Button();
        button.setText("Knap");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
