package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Controller {

    String s1;
    String s2;
    String s3;

    @FXML
    GridPane tablegridpane;

    @FXML
    GridPane s3gridpane;

    @FXML
    TextField s1textfield;

    @FXML
    TextField s2textfield;

    @FXML
    TextField s3textfield;

    @FXML
    Button createtablebtn;

    @FXML
    Button filltablebtn;

    @FXML
    public void createtableonClick(ActionEvent event){

        s1 = s1textfield.getText();
        s2 = s2textfield.getText();
        s3 = s3textfield.getText();


        tablegridpane.getChildren().clear();
        if(s1textfield.getText().length()<=8 && s2textfield.getText().length()<=8){

            int no_column = 0;
            int no_rows = 0;

            try{
                no_column = s2textfield.getText().length();
                no_rows = s1textfield.getText().length();
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }

            int k = 0;
            for(int i=0; i<=no_rows+1; i++){
                for(int j=0; j<=no_column+1; j++){

                    Rectangle rectangle = new Rectangle(80, 45);
                    if(i == 0 && j==0)
                        rectangle.setFill(Color.WHITE);
                    else if(i == 0)
                        rectangle.setFill(Color.rgb(90, 155, 213));
                    else if(j == 0)
                        rectangle.setFill(Color.rgb(90, 155, 213));
                    else if(i%2 != 0)
                        rectangle.setFill(Color.rgb(210, 221, 239));
                    else
                        rectangle.setFill(Color.rgb(234, 238, 239));

                    rectangle.setStrokeWidth(1);
                    rectangle.setStroke(Color.WHITE);


                    //Text text = new Text("("+i+","+j+")"+" "+k);k++;
                    Text text = new Text();
                    if(i == 0 && j > 1)
                        text.setText(s2.charAt(j-2)+"");
                    else if(i > 1 && j == 0)
                        text.setText(s1.charAt(i-2)+"");

                    //text.setText("("+i+","+j+")"+" ");

                    text.setFill(Color.BLACK);
                    text.setFont(Font.font(24));
                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(rectangle, text);
                    tablegridpane.add(stack, j, i);

                }
            }

            if(s3.length() <=16){

                for(int i=0; i<s3.length(); i++){
                    Rectangle s3rectangle = new Rectangle(60, 45);
                    s3rectangle.setFill(Color.rgb(255, 192, 0));
                    s3rectangle.setStrokeWidth(1);
                    s3rectangle.setStroke(Color.WHITE);
                    Text s3text = new Text(s3.charAt(i)+"");
                    s3text.setFill(Color.BLACK);
                    s3text.setFont(Font.font(22));
                    StackPane s3stack = new StackPane();
                    s3stack.getChildren().addAll(s3rectangle, s3text);
                    s3gridpane.add(s3stack, i, 0);

                }

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Length of s3 can't be greater than 16.");
                alert.showAndWait();
            }

        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("Length of s1 and s2 can't be greater than 8.");
            alert1.showAndWait();
        }

    }


    @FXML
    public void filltableonClick(ActionEvent event){


        if (s3.length() != s1.length() + s2.length()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Length of s1 and s2 is not equal to s3.\nStrings are not interleaving.");
            alert.showAndWait();
        }
        else{

            Text text;
            Rectangle rectangle;
            boolean dp[][] = new boolean[s1.length() + 1][s2.length() + 1];
            for (int i = 0; i <= s1.length(); i++) {
                for (int j = 0; j <= s2.length(); j++) {
                    if (i == 0 && j == 0) {
                        dp[i][j] = true;

                    } else if (i == 0) {
                        dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);

                    } else if (j == 0) {
                        dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);

                    } else {
                        dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));

                    }

                    text = getstackpaneText((StackPane) getgridpaneNode(tablegridpane, i+1, j+1));
                    if(dp[i][j] == true){
                        text.setText("T");
                        rectangle = getstackPaneRectangle((StackPane) getgridpaneNode(tablegridpane, i+1, j+1));
                        rectangle.setFill(Color.rgb(146, 209, 79));

                    }
                    else{
                        text.setText("F");
                    }

                }
            }

            //Print table...
            System.out.println("Dynamic Programming Table");
            for (int i = 0; i <= s1.length(); i++) {
                for (int j = 0; j <= s2.length(); j++) {
                    System.out.print(dp[i][j] + "\t");
                }
                System.out.println("");
            }

        }

    }

    public Node getgridpaneNode(GridPane gridPane, int row, int col){
        for(Node node : gridPane.getChildren()){
            if(GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row){
                return node;
            }
        }

        return null;
    }

    public Text getstackpaneText(StackPane stackPane){
        return (Text)stackPane.getChildren().get(1);
    }

    public Rectangle getstackPaneRectangle(StackPane stackPane){
        return (Rectangle)stackPane.getChildren().get(0);
    }

}
