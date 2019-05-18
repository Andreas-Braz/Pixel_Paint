package Pixel_Art;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paint extends Application {
	
	// gap between pixels.
	private static final int VGAP = 1; 
	private static final int HGAP = 1;
		
	//creation of the pixel canvas.
	private static final int PWIDTH = 32;
	private static final int PHEIGHT = 24;
	
	// width and height of the pixels
	private static final double RWIDTH = 24.0;
	private static final double RHEIGHT = 24.0;
	
    private static final Color INIT_COLOR = Color.WHITE;
    private static Color PAINT_COLOR = Color.BLACK;
    
    private static final double TOP_INSET = 10;
    private static final double RIGHT_INSET = 10;
    private static final double BOTTOM_INSET = 10;
    private static final double LEFT_INSET = 10;
       
    private static final String TITLE = "Pixel Paint";
    private static final String BNAME = "Erase";
    
	public static void main(String[] args) {

		// Start the JavaFX application by calling launch().  
		// IntellijIdea. Not Communuty, but Ultimate For students is free.
		launch(args);
	}
	 
	public void start(Stage myStage) {
		myStage.setTitle(TITLE);
      
	    // Create the HBox
	    HBox rootNode = new HBox();
		// Set border
        rootNode.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT, new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET))));
		       
        //create the grid pane and set in on the scene.
    	GridPane grid = new GridPane();
        Scene myScene = new Scene(rootNode, rootNode.getMinWidth(), rootNode.getMinHeight());
        
        //creation of the color change button.
        ColorPicker cp = new ColorPicker();
        cp.setValue(PAINT_COLOR); 
        
        Button button = new Button(BNAME);
    
        rootNode.getChildren().addAll(grid,cp,button);           
		myStage.setScene(myScene);
		myStage.sizeToScene();
		myStage.setResizable(false);
				
		grid.setVgap(VGAP); // gap between pixels.
		grid.setHgap(HGAP);
		
		grid.setAlignment(Pos.TOP_CENTER);
			
		//method to create the grid.
		createGrid(grid);
		
		//method to change the color
        cp.setOnAction(event -> handleColor(cp, event));
        
        //method for the erase button. It creates a new white grid.
        button.setOnAction (event -> createGrid(grid));
	
		//event handler for the mouse drag and draw.
		grid.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me) {
				handleEvent(grid, me); 
			}
		});
			
		// event handler for the mouse click.
		grid.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me) {
				handleEvent(grid, me);										
			}
		});
					
		myStage.show();		
	}
	
	// method to change the color of the panting.	
	private void handleColor (ColorPicker cp, Event event) {
		PAINT_COLOR = cp.getValue();
	};
	
	// method to handle mouse when the mouse is pressed and dragged. It gets the coordinates 
	//of the mouse (X and Y) and creates a pixel.
	private void handleEvent(GridPane grid, MouseEvent me) {
		if (me.isPrimaryButtonDown() || me.isSecondaryButtonDown()) {     
			int column = (int) ((me.getX())/(RHEIGHT+HGAP));
			int row = (int) ((me.getY())/(RWIDTH+VGAP));
			
			//column and row are the coordinates of X and Y respectively. To draw a Pixel, it must follow 2 conditions.
			// 1- it must be equal or bigger than 0, which is the first index of each column/row. This way, we avoid errors with
			//negative coordinates. 2 - it must be less than the maximum index of the columns and rows (PWIDTH and PHEIGHT). This 
			//way, we avoid painting outside the grid limits.
			
			if ((column >= 0 && row >= 0) && (column < PWIDTH && row < PHEIGHT)) { 
					Rectangle rectangle = new Rectangle(RWIDTH,RHEIGHT,PAINT_COLOR);
					grid.add(rectangle, column, row); 				
			}
		}
	} 
	
	// the 2 loops create a pixel in each row and column.
	private void createGrid(GridPane grid) {
		for (int column = 0; column < PWIDTH; column++) {
			for (int row = 0; row< PHEIGHT;row++) {       		
				Rectangle rectangle = new Rectangle(RWIDTH,RHEIGHT,INIT_COLOR);
				grid.add(rectangle, column, row);
			}		
		}
	}
}
