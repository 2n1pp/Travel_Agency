package com.noborderstravel.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.noborderstravel.models.Passanger;
import com.noborderstravel.models.SegmentInfo;
import com.noborderstravel.services.requests;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class travelerDetailController {
    @FXML private Label traveler_nr;
    @FXML private TextField traveler_name;
    @FXML private TextField traveler_surname;
    @FXML private DatePicker traveler_birthday;
    @FXML private ComboBox<String> traveler_gener;
    @FXML private TextField traveler_email;
    @FXML private TextField traveler_mobile;
    @FXML private VBox root_vbox;

    ArrayList<String>  travelers_list = new ArrayList<>();
    List<Passanger> listItems = new ArrayList<>();
    List<SegmentInfo> going_segmentList;
    List<SegmentInfo> coming_segmentList;
    private int nr_of_persons;
    private String departing_at;
    private String arrival_at;
    private String from_iata;
    private String  to_iata;
    private String token;

    public void setNr_of_persons(int nr_of_persons) {
        this.nr_of_persons = nr_of_persons;
    }
    public void setGoingSegmentList(List<SegmentInfo> segmentList){ this.going_segmentList = segmentList;}
    public void setComingSegmentList(List<SegmentInfo> segmentList){ this.coming_segmentList = segmentList;}

    public void setAts(String departing_at, String arrival_at, String from_iata, String to_iata, String token){
        this.token = token;
        this.departing_at = departing_at;
        this.arrival_at = arrival_at;
        this.from_iata = from_iata;
        this.to_iata = to_iata;
    }

    public void nextHandle() throws IOException, ParseException {
        int temp_nr = Integer.parseInt(traveler_nr.getText());
        Passanger passanger = new Passanger();
        temp_nr = temp_nr + 1;
        traveler_nr.setText(String.valueOf(temp_nr));
        String name = traveler_name.getText();
        String surname = traveler_surname.getText();
        String email = traveler_email.getText();
        String mobile = traveler_mobile.getText();
        LocalDate bday = traveler_birthday.getValue();
        String gener = traveler_gener.getValue();
        travelers_list.add(name+","+surname+","+bday+","+gener+","+email+","+mobile);
        passanger.setName(name);
        passanger.setSurname(surname);
        passanger.setBday(String.valueOf(bday));
        if(gener.equals("Male")){
            passanger.setTitle("Mr");
        }else{
            passanger.setTitle("MrS");
        }
        listItems.add(passanger);
        traveler_name.clear();
        traveler_surname.clear();
        traveler_birthday.setValue(null);
        traveler_gener.setValue(null);
        traveler_email.clear();
        traveler_mobile.clear();
        if(nr_of_persons == 1){
            System.out.println("we are done");
            for(String x :travelers_list){
                System.out.println(x);
            }
            System.out.println(listItems);
            Stage stage = (Stage) root_vbox.getScene().getWindow();
            stage.close();
            departing_at = departing_at.substring(0, 10) + " " + departing_at.substring(11, 16);
            arrival_at = arrival_at.substring(0, 10) + " " + arrival_at.substring(11, 16);
            String departing_city_data = requests.GET("http://127.0.0.1:5000/iatadetail/"+ from_iata + "?token="+token);
            String arrival_city_data = requests.GET("http://127.0.0.1:5000/iatadetail/"+ to_iata + "?token="+token);
            departing_city_data = departing_city_data.substring(7, departing_city_data.length()-4);
            arrival_city_data = arrival_city_data.substring(7, arrival_city_data.length()-4);
            String[] departing_city_list = departing_city_data.split(",");
            String[] arrival_city_list = arrival_city_data.split(",");
            String departing_city = departing_city_list[1] + " (" + from_iata + ")";
            String arrival_city = arrival_city_list[1] + " (" + to_iata + ")";
            Runnable task = () -> {
                /* Convert List to JRBeanCollectionDataSource */
                JRBeanCollectionDataSource passangerJRBean = new JRBeanCollectionDataSource(listItems);
                JRBeanCollectionDataSource going_segmentJRBean = new JRBeanCollectionDataSource(going_segmentList);
                JRBeanCollectionDataSource coming_segmentJRBean = new JRBeanCollectionDataSource(coming_segmentList);

                InputStream icon = null;
                try {
                    icon = travelerDetailController.class.getResourceAsStream("/images/no_borders.png");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error icon: " + e);
                }
                InputStream qr = null;
                try {
                    qr = travelerDetailController.class.getResourceAsStream("/images/qr_code.png");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error qr: " + e);
                }

                /* Map to hold Jasper report Parameters */
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("passangerCollectionBean", passangerJRBean);
                parameters.put("going_segmentCollectionBean", going_segmentJRBean);
                parameters.put("coming_segmentCollectionBean", coming_segmentJRBean);
                parameters.put("departing_from", departing_city);
                parameters.put("arriving_to", arrival_city);
                parameters.put("departing_date_time", departing_at);
                parameters.put("arriving_date_time", arrival_at);
                parameters.put("icon_image", icon);
                parameters.put("qr_code", qr);

                //read jrxml file and creating jasperdesign object
                InputStream input = null;
                if(coming_segmentList == null){
                    try {
                        input = travelerDetailController.class.getResourceAsStream("/jrxml/e-ticket.jrxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        input = travelerDetailController.class.getResourceAsStream("/jrxml/e-ticket-round.jrxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                JasperDesign jasperDesign = null;
                try {
                    jasperDesign = JRXmlLoader.load(input);
                } catch (JRException e) {
                    e.printStackTrace();
                }

                /*compiling jrxml with help of JasperReport class*/
                JasperReport jasperReport = null;
                try {
                    jasperReport = JasperCompileManager.compileReport(jasperDesign);
                } catch (JRException e) {
                    e.printStackTrace();
                }

                /* Using jasperReport object to generate PDF */
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
                } catch (JRException e) {
                    e.printStackTrace();
                }

                /*call jasper engine to display report in jasperviewer window*/
                JasperViewer.viewReport(jasperPrint, false);

                String home = System.getProperty("user.home");

                File theDir = new File(home + "\\NO BORDERS Travel Agency\\");

                // if the directory does not exist, create it
                if (!theDir.exists()) {
                    System.out.println("creating directory: " + theDir.getName());
                    boolean result = false;

                    try{
                        theDir.mkdir();
                        result = true;
                    }
                    catch(SecurityException se){
                        //handle it
                    }
                    if(result) {
                        System.out.println("DIR created");
                    }
                }

                /* outputStream to create PDF */
                String outputFile = home + "\\NO BORDERS Travel Agency\\" + departing_city
                        .replaceAll("(.*?)(\\()","").replace(")", "")
                        + "-" + arrival_city.replaceAll("(.*?)(\\()","")
                        .replace(")", "") + "-" + departing_at.replace("/","-")
                        .replace(":", "-").replace(" ", "-") + ".pdf" ;

                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(new File(outputFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                /* Write content to PDF file */
                try {
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                } catch (JRException e) {
                    e.printStackTrace();
                }

                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            Thread backgroundThread = new Thread(task);
            backgroundThread.setDaemon(true);
            backgroundThread.start();
        }
        nr_of_persons = nr_of_persons - 1;
    }
}
