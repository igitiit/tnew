package controllers;

import java.sql.ResultSet;

import DAO.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminController extends DBConnection {
    //decLare objects to relate to controls in your fxml file
	@FXML private javafx.scene.control.Button btnClose;

 	public void close() {
		 // get a handle to the stage
	    Stage stage = (Stage) btnClose.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void open() throws Exception {  
	    
	         @SuppressWarnings({ "rawtypes" })
	    	
	    //FOR TABLE VIEW AND DATA

	    	TableView tableview = new TableView();
	    	ObservableList<ObservableList> data;
	
	    	//CONNECT TO DATABASE
	 
	          data = FXCollections.observableArrayList();
	          try{
	            
	            //SQL FOR SELECTING ALL OF CUSTOMER
	            String SQL = "SELECT * from jpapa_tickets";
	            //ResultSet
	            ResultSet rs = connection.createStatement().executeQuery(SQL);

	            /**********************************
	             * TABLE COLUMN ADDED DYNAMICALLY *
	             **********************************/
	            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
	                //We are using non property style for making dynamic table
	                final int j = i;                
	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
	                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                    }                    
	                });
	               
	                tableview.getColumns().addAll(col); 
	                System.out.println("Column ["+i+"] ");
	            }

	            /********************************
	             * Data added to ObservableList *
	             ********************************/
	            while(rs.next()){
	                //Iterate Row
	                ObservableList<String> row = FXCollections.observableArrayList();
	                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
	                    //Iterate Column
	                    row.add(rs.getString(i));
	                }
	                System.out.println("Row [1] added "+row );
	                data.add(row);
	            }

	            //FINALLY ADD TO TableView
	            tableview.setItems(data);
	          }catch(Exception e){
	              e.printStackTrace();
	              System.out.println("Error on Building Data");             
	          }
	       
	        //Create Main Scene (pop up)
	        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        Scene scene = new Scene(tableview);
	        scene.getStylesheets().add("application/application.css");
	        Stage stage = new Stage();
	        stage.setWidth(500);
	        stage.setScene(scene);
	        stage.show();
  }
}
