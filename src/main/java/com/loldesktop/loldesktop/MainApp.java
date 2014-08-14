/*
 * For Dialog : http://code.makery.ch/blog/javafx-8-dialogs/
 */
package com.loldesktop.loldesktop;

import com.loldesktop.loldesktop.controllers.AppsController;
import com.loldesktop.loldesktop.controllers.LoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Miloune
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private AppsController appsController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LolDesktop");

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                if(UserSingleton.getUserSingleton().isConnected())
                    UserSingleton.getUserSingleton().getChatAPI().disconnectChat();
            }
        });
        UserSingleton.getUserSingleton().setMainApp(this);
        
        initRootLayout();

        showLoginOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);

            //Image applicationIcon = new Image(getClass().getResourceAsStream("images/logo.png"));
            //primaryStage.getIcons().add(applicationIcon);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the login overview inside the root layout.
     */
    public void showLoginOverview() {
        try {
            // Load login overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
            AnchorPane loginOverview = (AnchorPane) loader.load();

            // Set login overview into the center of root layout.
            rootLayout.setCenter(loginOverview);

            // Give the controller access to the main app
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the apps overview inside the root layout
     */
    public void showAppsOverview() {
        try {
            // Load Apps overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Apps.fxml"));
            AnchorPane appsOverview = (AnchorPane) loader.load();

            // Set login overview into the center of root layout.
            rootLayout.setCenter(appsOverview);
            
            // Give the controller access to the main app
            AppsController appsController = loader.getController();
            appsController.setMainApp(this);
            this.appsController = appsController;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    
    public AppsController getAppsController() {
        return this.appsController;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
