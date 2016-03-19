package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

public class Controller {
    public GridPane rootGridPane;

    @FXML
    public void initialize(){
        Label source = new Label("source");
        Label target = new Label("target");

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("source.setOnDragDetected");
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });



        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("target.setOnDragOver");
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node
         * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });


        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("target.setOnDragEntered");
    /* the drag-and-drop gesture entered the target */
    /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    target.setStyle("-fx-text-fill: green");
                }

                event.consume();
            }
        });



        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("target.setOnDragExited");
        /* mouse moved away, remove the graphical cues */
                target.setStyle("-fx-text-fill: black");

                event.consume();
            }
        });


        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("target.setOnDragDropped");
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
                    success = true;
                }
        /* let the source know whether the string was successfully
         * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });


        source.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("source.setOnDragDone");
        /* the drag and drop gesture ended */
        /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                }
                event.consume();
            }
        });


        rootGridPane.add(source, 0, 0);
        rootGridPane.add(target, 1, 0);
    }
}
