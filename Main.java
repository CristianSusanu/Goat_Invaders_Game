package GameMenu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
//import java.util.logging.Logger;
//import java.sql.*;


public class Main extends Application {
	private GameMenu gameMenu;
	Scene scene;
	public static final ObservableList data = 
	        FXCollections.observableArrayList();
	
	public static void main(String[] args) {
		//Main pro = new Main();
		//pro.createConnection();
		launch(args);
	}
	
/*void createConnection() {
		
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
		
			Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.157:3306/game", "root","Network33");
		
			System.out.println("Database Connection Success");
		
		} catch (ClassNotFoundException ex) {
		
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		
		} catch (SQLException ex) {
		
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null, ex);
		
		}
		
	}*/
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new Pane();
		root.setPrefSize(800, 600);
		
		//Read the image from the file
		InputStream is = Files.newInputStream(Paths.get("resources/space.jpg"));
		Image img = new Image(is);
		is.close();
		
		//Set the image as background for the menu, and resize it
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);
		
		gameMenu = new GameMenu();
		gameMenu.setVisible(true);
		
		root.getChildren().addAll(imgView, gameMenu);

		scene = new Scene(root);
		
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ESCAPE) {
				FadeTransition fade = new FadeTransition(Duration.seconds(0.5), gameMenu);
				fade.setFromValue(0);
				fade.setToValue(1);
				gameMenu.setVisible(true);
				fade.play();
			}
			else {
				FadeTransition fade = new FadeTransition(Duration.seconds(0.5), gameMenu);
				fade.setFromValue(1);
				fade.setToValue(0);
				gameMenu.setVisible(false);
				fade.play();
			}
		});
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private static class MenuButton extends StackPane{
		private Text text;
		
		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(text.getFont().font(20));
			text.setFill(Color.WHITE);
			
			Rectangle background = new Rectangle(250, 30);
			background.setOpacity(0.4);
			background.setFill(Color.BLACK);
			background.setEffect(new GaussianBlur(3.5));
			
			setAlignment(Pos.CENTER);
			setRotate(-0.3);
			getChildren().addAll(background, text);
			
			setOnMouseEntered(e -> {
				background.setTranslateX(10);
				text.setTranslateX(10);
				background.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			setOnMouseExited(e -> {
				background.setTranslateX(0);
				text.setTranslateX(0);
				background.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			setOnMousePressed(e -> setEffect(drop));
			setOnMouseReleased(e -> setEffect(null));			
		}
	}
	
	public class GameMenu extends Parent {
		public GameMenu() throws IOException {
			GridPane loginMenu = new GridPane();
			GridPane createAccountMenu = new GridPane();
			VBox mainMenu = new VBox(10);
			VBox settingsMenu = new VBox(10);
			VBox soundMenu = new VBox(10);
			VBox accountMenu = new VBox(10);
			VBox playMenu = new VBox(10);
			VBox game = new VBox(10);
			VBox customiseMenu = new VBox(10);
			
			loginMenu.setTranslateX(250);
			loginMenu.setTranslateY(250);
			
			createAccountMenu.setTranslateX(250);
			createAccountMenu.setTranslateY(250);
			
			mainMenu.setTranslateX(250);
			mainMenu.setTranslateY(190);
			
			settingsMenu.setTranslateX(400);
			settingsMenu.setTranslateY(200);
			
			soundMenu.setTranslateX(400);
			soundMenu.setTranslateY(200);
			
			accountMenu.setTranslateX(400);
			accountMenu.setTranslateY(200);
			
			playMenu.setTranslateX(400);
			playMenu.setTranslateY(200);
			
			customiseMenu.setTranslateX(350);
			customiseMenu.setTranslateY(200);
			
			final int offset = 400;
			
			//Login Menu
			MenuButton bLogin = new MenuButton("Login");
			bLogin.setTranslateX(-100);
			bLogin.setTranslateY(100);
			
			MenuButton bCreateAcc = new MenuButton("Create Account");
			bCreateAcc.setTranslateX(170);
			bCreateAcc.setTranslateY(98);	
			
			Label lUserName = new Label("User Name:");
			lUserName.setTextFill(Color.WHITESMOKE);
			lUserName.setFont(lUserName.getFont().font(18));
			lUserName.setTranslateY(0);
			lUserName.setTranslateX(-20);
			
			TextField userTextField = new TextField();
			userTextField.setTranslateY(0);
			userTextField.setTranslateX(100);	
			userTextField.setStyle("-fx-font-weight: bold");
			userTextField.setOpacity(0.75);
			userTextField.setFont(userTextField.getFont().font(13));
			userTextField.setMaxWidth(170);
			userTextField.setMaxHeight(45);
			
			Label lPassword = new Label("Password:");
			lPassword.setTextFill(Color.WHITESMOKE);
			lPassword.setFont(lPassword.getFont().font(18));
			lPassword.setTranslateX(-20);
			lPassword.setTranslateY(40);
			
			PasswordField pwBox = new PasswordField();
			pwBox.setTranslateX(100);
			pwBox.setTranslateY(40);
			pwBox.setOpacity(0.75);
			pwBox.setFont(pwBox.getFont().font(13));
			pwBox.setMaxWidth(170);
			pwBox.setMaxHeight(45);
			
			bLogin.setOnMouseClicked(e -> {
				Alert alert = new Alert(AlertType.ERROR);
				String checkUser = userTextField.getText().toString();
				String checkPassword = pwBox.getText();
				if(checkUser.isEmpty() && checkPassword.isEmpty()) {
					alert.setTitle("Login Error");
					alert.setHeaderText("No User name and Password introduced");
					alert.setContentText("Please input a valid username and password");
					alert.show();
				}else if(checkUser.isEmpty()) {
					alert.setTitle("Login Error");
					alert.setHeaderText("No username introduced");
					alert.setContentText("Please input a username");
					alert.show();
				} else if(checkPassword.isEmpty()) {
					alert.setTitle("Login Error");
					alert.setHeaderText("No Password introduced");
					alert.setContentText("Please input a valid password");
					alert.show();
				} else if(checkPassword.length() < 7) {
					alert.setTitle("Login Error");
					alert.setHeaderText("Invalid Password");
					alert.setContentText("Please input a Password longer than 6 characters.");
					alert.show();
				} else {
					getChildren().add(mainMenu);
					TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25), loginMenu);
					t1.setToX(loginMenu.getTranslateX() + offset);
					
					TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), mainMenu);
					t2.setToX(loginMenu.getTranslateX());
					
					//Play the  transition animations
					t1.play();
					t2.play();
					
					//Stop the animation
					t1.setOnFinished(event -> getChildren().remove(loginMenu));
				}
			});			
			
			loginMenu.getChildren().addAll(bLogin, bCreateAcc, lUserName, userTextField, lPassword, pwBox);
   			
			//Create Account Menu
			MenuButton bRegister = new MenuButton("Register");
			bRegister.setTranslateY(130);
			bRegister.setOnMouseClicked(e -> {
				getChildren().add(mainMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25), createAccountMenu);
				t1.setToX(createAccountMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), mainMenu);
				t2.setToX(createAccountMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(createAccountMenu));
				/*Connection conn = null;
				try {
					conn = DriverManager.getConnection("jdbc:mysql://192.168.1.157:3306/game", "root","Network33");
					System.out.println("Connected Database for insertation");
					Statement stmt = (Statement) conn.createStatement();
					int num = 1;
					String email = registerEmailTextField.getText();
					stmt.execute("INSERT INTO logins(username, password, email) VALUES('"+checkUser+"','"+checkPassword+"','"+email+"')");
				} catch (SQLException ex) {
					System.err.println(ex);
				}*/
				
			});
			
			bCreateAcc.setOnMouseClicked(e -> {
					getChildren().add(createAccountMenu);
					TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25), loginMenu);
					t1.setToX(loginMenu.getTranslateX() + offset);
					
					TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), createAccountMenu);
					t2.setToX(loginMenu.getTranslateX());
					
					//Play the  transition animations
					t1.play();
					t2.play();
					
					//Stop the animation
					t1.setOnFinished(event -> getChildren().remove(loginMenu));
			});
			
			MenuButton bRegisterBack = new MenuButton("Back");
			bRegisterBack.setTranslateY(170);
			bRegisterBack.setOnMouseClicked(e -> {
				getChildren().add(loginMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25),createAccountMenu);
				t1.setToX(createAccountMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), loginMenu);
				t2.setToX(createAccountMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(createAccountMenu));
			});
			
			Label lRegisterUserName = new Label("User Name:");
			lRegisterUserName.setTextFill(Color.WHITESMOKE);
			lRegisterUserName.setFont(lUserName.getFont().font(18));
			lUserName.setTranslateX(-20);
			lRegisterUserName.setTranslateY(0);
			
			TextField registerUserTextField = new TextField();
			registerUserTextField.setTranslateY(0);
			registerUserTextField.setTranslateX(100);	
			registerUserTextField.setStyle("-fx-font-weight: bold");
			registerUserTextField.setOpacity(0.75);
			registerUserTextField.setFont(registerUserTextField.getFont().font(13));
			registerUserTextField.setMaxWidth(170);
			registerUserTextField.setMaxHeight(45);
			
			Label lRegisterEmail = new Label("Email:");
			lRegisterEmail.setTextFill(Color.WHITESMOKE);
			lRegisterEmail.setFont(lRegisterEmail.getFont().font(18));
			lRegisterEmail.setTranslateX(40);
			lRegisterEmail.setTranslateY(40);
			
			TextField registerEmailTextField = new TextField();
			registerEmailTextField.setTranslateY(40);
			registerEmailTextField.setTranslateX(100);	
			registerEmailTextField.setStyle("-fx-font-weight: bold");
			registerEmailTextField.setOpacity(0.75);
			registerEmailTextField.setFont(registerEmailTextField.getFont().font(13));
			registerEmailTextField.setMaxWidth(170);
			registerEmailTextField.setMaxHeight(45);
			
			Label lRegisterPassword = new Label("Password:");
			lRegisterPassword.setTextFill(Color.WHITESMOKE);
			lRegisterPassword.setFont(lRegisterPassword.getFont().font(18));
			lRegisterPassword.setTranslateX(12);
			lRegisterPassword.setTranslateY(80);
			
			PasswordField pwRegisterBox = new PasswordField();
			pwRegisterBox.setTranslateX(100);
			pwRegisterBox.setTranslateY(80);
			pwRegisterBox.setOpacity(0.75);
			pwRegisterBox.setFont(pwBox.getFont().font(13));
			pwRegisterBox.setMaxWidth(170);
			pwRegisterBox.setMaxHeight(45);
			
			createAccountMenu.getChildren().addAll(lRegisterUserName, registerUserTextField, lRegisterEmail, registerEmailTextField, lRegisterPassword, pwRegisterBox, bRegister, bRegisterBack);
			
			//Main Menu
			MenuButton bPlay = new MenuButton("Play");
			bPlay.setOnMouseClicked(e -> {
				getChildren().add(playMenu);
				
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35), mainMenu);
				t1.setToX(mainMenu.getTranslateX() - offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), playMenu);
				t2.setToX(mainMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(mainMenu));
			});	
			//Play Menu
			MenuButton bSinglePlayer = new MenuButton("Single Player");
			bSinglePlayer.setOnMouseClicked(e -> {
				FadeTransition fade = new FadeTransition(Duration.seconds(0.5), this);
				fade.setFromValue(1);
				fade.setToValue(0);
				fade.setOnFinished(event -> setVisible(false));
				fade.play();
				Stage stage = new Stage();
				StartGame start = new StartGame();
				
				try {
					start.startgame(stage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			});
			MenuButton bMultiPlayer = new MenuButton("MultiPlayer");
			bMultiPlayer.setOnMouseClicked(e -> {
				FadeTransition fade = new FadeTransition(Duration.seconds(0.5), this);
				fade.setFromValue(1);
				fade.setToValue(0);
				fade.setOnFinished(event -> setVisible(false));
				fade.play();
			});	
			MenuButton bPlayBack = new MenuButton("Back");
			bPlayBack.setOnMouseClicked(e -> {
				getChildren().add(mainMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25), playMenu);
				t1.setToX(playMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), mainMenu);
				t2.setToX(playMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(playMenu));
			});
			
			MenuButton bLevel = new MenuButton("Level");
			
			// Customize menu
			InputStream is = Files.newInputStream(Paths.get("resources/sp1.png"));
	        Image sp = new Image(is);
	        is.close();
	        ImageView selectedImage = new ImageView(sp); 
	      
	        selectedImage.setTranslateX(155);
	        selectedImage.setTranslateY(-170);
	        selectedImage.setFitHeight(300);
	        selectedImage.setFitWidth(380);
	       
	        
	        
	        customiseMenu.getChildren().add(selectedImage);
	        
	        Text t = new Text ();
	        
	        t.setFill(Color.BLACK);
	        t.setFont(Font.font("Ariel", 14));
	        t.setTranslateX(-240);
	        t.setTranslateY(-60);
	        
	        
	        
	        //list of the new ships 
	        Image ship_1 = new Image(Files.newInputStream(Paths.get("resources/sp1.png")));
			Image ship_2 = new Image(Files.newInputStream(Paths.get("resources/sp2.png")));
			Image ship_3 = new Image(Files.newInputStream(Paths.get("resources/sp3.png")));
			Image ship_4 = new Image(Files.newInputStream(Paths.get("resources/sp4.png")));
			Image ship_5 = new Image(Files.newInputStream(Paths.get("resources/sp5.png")));
			Image[] listOfImages = {ship_1,ship_2,ship_3,ship_4,ship_5};
			ListView<String> list = new ListView<String>();
			list.setPrefHeight(400);
			list.setPrefWidth(-100);
			list.setTranslateX(-250);
			list.setTranslateY(-535);
			 ObservableList<String> items =FXCollections.observableArrayList (
		                "Ship1","Ship2", "Ship3", "Ship4", "Ship5");
			
			list.setEditable(true);
			list.setItems(items);
			list.setCellFactory(param -> new ListCell<String>() {
	        ImageView imageView = new ImageView();
	            @Override
	            public void updateItem(String name, boolean empty) {
	                super.updateItem(name, empty);
	              
	                if (empty) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                    if(name.equals("Ship1")) {
	                    	imageView.setImage(listOfImages[0]);
	                    	imageView.setFitHeight(100);
	                    	imageView.setFitWidth(100);
	                    	

	                    	
	                    } else if(name.equals("Ship2")) {
	                    	imageView.setImage(listOfImages[1]);
	                    	imageView.setFitHeight(100);
	                    	imageView.setFitWidth(100);
	                    	
	                    	
	                    } else if(name.equals("Ship3")) {
	                    	imageView.setImage(listOfImages[2]);
	                    	imageView.setFitHeight(100);
	                    	imageView.setFitWidth(100);
	                    	
	                    	
	                    } else if(name.equals("Ship4")) {
	                    	imageView.setImage(listOfImages[3]);
	                    	imageView.setFitHeight(100);
	                    	imageView.setFitWidth(100);
	                    	
	                    	
	                    } else if(name.equals("Ship5")) {
	                    	imageView.setImage(listOfImages[4]);
	                    	imageView.setFitHeight(100);
	                    	imageView.setFitWidth(100);
	                    	
	                    	
	                    }
	                    
	                     
	                    setText(name);
	                    setGraphic(imageView);
	                }
	            }
	                
	        });	
			customiseMenu.getChildren().add(t);
			customiseMenu.getChildren().addAll(list);
		
			
            list.setOnMouseClicked(e -> {
                if(list.getSelectionModel().getSelectedItem()=="Ship1") {
                	selectedImage.setImage(ship_1);
                	t.setText("Ship1 description");
                	}
                else if(list.getSelectionModel().getSelectedItem()=="Ship5") {
                	selectedImage.setImage(ship_5);
                	t.setText("Ship5 description");
                }
				else if(list.getSelectionModel().getSelectedItem()=="Ship4") {
					selectedImage.setImage(ship_4);
					t.setText("Ship4 description");
				}
				else if(list.getSelectionModel().getSelectedItem()=="Ship3") {
					selectedImage.setImage(ship_3);
					t.setText("Ship3 descripstion");
				}
				else if(list.getSelectionModel().getSelectedItem()=="Ship2") {
					selectedImage.setImage(ship_2);
					t.setText("Ship2 description");
				}
				
			});
        
	        //button
			MenuButton bCustomize = new MenuButton("Customize");
			bCustomize.setOnMouseClicked(e -> {
			
				getChildren().add(customiseMenu);
				
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35), mainMenu);
				t1.setToX(mainMenu.getTranslateX() - offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), customiseMenu);
				t2.setToX(mainMenu.getTranslateX());
				
				t1.play();
				t2.play();
				
				t1.setOnFinished(event -> getChildren().remove(mainMenu));
				
			});
			
			
			
	         
			 
			
			
			
			
			
			MenuButton bSettings = new MenuButton("Settings");			
			bSettings.setOnMouseClicked(e -> {
				getChildren().add(settingsMenu);
				
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35), mainMenu);
				t1.setToX(mainMenu.getTranslateX() - offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), settingsMenu);
				t2.setToX(mainMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(mainMenu));
			});	
			
			MenuButton bQuit = new MenuButton("Quit");
			bQuit.setOnMouseClicked(e -> System.exit(0));
			
			MenuButton bBackLogin = new MenuButton("Back");
			bBackLogin.setOnMouseClicked(e -> {
				getChildren().add(loginMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25), mainMenu);
				t1.setToX(mainMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), loginMenu);
				t2.setToX(mainMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(mainMenu));
			});
			
			//Settings Menu
			MenuButton bControls = new MenuButton("Controls");
			//Account Menu
			MenuButton bAccount = new MenuButton("Account");
			bAccount.setOnMouseClicked(e -> {
				getChildren().add(accountMenu);
				
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35), settingsMenu);
				t1.setToX(settingsMenu.getTranslateX() - offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), accountMenu);
				t2.setToX(settingsMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(settingsMenu));
			});
			MenuButton bAccountBack = new MenuButton("Back");
			bAccountBack.setOnMouseClicked(e -> {
				getChildren().add(settingsMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35),  accountMenu);
				t1.setToX(accountMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), settingsMenu);
				t2.setToX(accountMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(accountMenu));
			});
			MenuButton bAccountUsername = new MenuButton("Username");
			MenuButton bAccountPassword = new MenuButton("Password");
			
			//Sound Menu
			MenuButton bSound = new MenuButton("Sound");
			MenuButton bSoundOn = new MenuButton("On");
			MenuButton bSoundOff = new MenuButton("Off");
			bSound.setOnMouseClicked(e -> {
				getChildren().add(soundMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35), settingsMenu);
				t1.setToX(settingsMenu.getTranslateX() - offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), soundMenu);
				t2.setToX(settingsMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(settingsMenu));
			});
			MenuButton bSoundBack = new MenuButton("Back");
			bSoundBack.setOnMouseClicked(e -> {
				getChildren().add(settingsMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.35),  soundMenu);
				t1.setToX(soundMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), settingsMenu);
				t2.setToX(soundMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(soundMenu));
			});
			
			MenuButton bHelp = new MenuButton("Help");
			
			MenuButton bBackSettings = new MenuButton("Back");
			bBackSettings.setOnMouseClicked(e -> {
				getChildren().add(mainMenu);
				TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.25), settingsMenu);
				t1.setToX(settingsMenu.getTranslateX() + offset);
				
				TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), mainMenu);
				t2.setToX(settingsMenu.getTranslateX());
				
				//Play the  transition animations
				t1.play();
				t2.play();
				
				//Stop the animation
				t1.setOnFinished(event -> getChildren().remove(settingsMenu));
			});
			 StartGame start = new StartGame();
			mainMenu.getChildren().addAll(bPlay, bLevel, bSettings, bCustomize, bBackLogin, bQuit);
			settingsMenu.getChildren().addAll(bAccount, bControls, bSound, bHelp, bBackSettings);
			soundMenu.getChildren().addAll(bSoundOn, bSoundOff, bSoundBack);
			accountMenu.getChildren().addAll(bAccountUsername,  bAccountPassword,  bAccountBack);
			playMenu.getChildren().addAll(bSinglePlayer, bMultiPlayer, bPlayBack);
			//game.getChildren().addAll(start.playfieldLayer,start.scoreLayer);
			
			Rectangle background = new Rectangle(800, 600);
			background.setFill(Color.GREY);
			background.setOpacity(0.4);
			
			getChildren().addAll(background, loginMenu);
			}
	}
}
