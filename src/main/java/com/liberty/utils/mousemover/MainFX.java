/**
 * 
 */
package com.liberty.utils.mousemover;

import java.awt.AWTException;
import java.awt.Robot;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author swanandm
 *
 */
public class MainFX extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Button start = new Button("Start");
		Button stop = new Button("Stop");
		Button exit = new Button("Exit");
		
		stop.setDisable(true);
		
		start.setOnAction((event)-> {
			start.setDisable(true);
			stop.setDisable(false);
			stop.requestFocus();
			try {
				Robot robot = new Robot();
				Thread watchdog = WatchDog.getThread(robot);				
				watchdog.start();
			}catch (AWTException e) {
				e.printStackTrace();
			}

		});
		
		stop.setOnAction((event)-> {
			start.setDisable(false);
			stop.setDisable(true);
			start.requestFocus();
			WatchDog.destroy();
		});
		
		exit.setOnAction((event) -> {
			try {
				start.setDisable(false);
				stop.setDisable(true);
				start.requestFocus();
				WatchDog.destroy();
				stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		HBox box = new HBox(10, start, stop, exit);		
		box.setPadding(new Insets(12, 10, 12, 10));
		StackPane root = new StackPane(box);
		root.setAlignment(Pos.CENTER);
		
		Scene screen = new Scene(root, 160, 50);

		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Mouse Mover");		
		stage.setScene(screen);
		stage.show();
	}
	
	
}
