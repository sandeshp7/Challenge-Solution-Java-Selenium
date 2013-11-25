package soundCloudPack;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;


/**
* This is a automated functional test using selenium webdriver (2.37.0).
* @author Sandesh Prasanna Kumar 
*/

public class soundCloudTest {

	/*
	 * Create a new instance of the firefox driver  
	 */
	static WebDriver driver =  new FirefoxDriver();
	
	
	/*
	 * Test data
	 */
	
	String path = "C:/lucia.mp3";  //Absolute path for the mp3 file is to be given 
	String fileTitle = "SoundCloud_Test.mp3";
	static int noOfTestsExecuted = 0;

	
	
	
	/*
	 * Web elements on AUT
	 */
	@FindBy(name = "title")
	 private WebElement titleBox;  // Title box to input file name
	@FindBy(name = "file")
	 private WebElement browseButton;  // File upload browse button
	@FindBy(name = "submit")
	 private WebElement uploadButton; // File upload button or Form submit button
	@FindBy(id = "progress")
	 private WebElement progressBar; // Progress bar area
	@FindBy(id = "uploads")
	 private WebElement uploadedFile; // Uploaded file placeholder
	
	
	
	/* @Test_Name - File Upload
	 * @Description - This method is used to test if a file can be uploaded.
	 * 				  This also verifies if the saved path is displayed when the file upload is completed. 
	 */
	
    public boolean fileUpload() {
      
    	//Keeping the count of tests executed
    	noOfTestsExecuted +=1;
    	//Using .sendKeys("File path"), we are able to upload the file path using selenium. This only works if control input type = file
    	browseButton.sendKeys("file:"+path);;
    	uploadButton.click();

    	// This is used to make sure the test execution is on hold until the upload is completed.
    	Boolean isUploaded = false;
    	while(!isUploaded){
    		//This is used to check if the upload is completed.
    		isUploaded = progressBar.getCssValue("background-color").equals("rgba(255, 255, 255, 1)");
    	}
    	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    	
    	//Checking if the saved path is displayed after the upload. This will return True or False based on the outcome.
    	return progressBar.getText().contains("saved as:");
    }
    
    
    
    /* @Test_Name  - Check file upload progress
	 * @Description - This method is used to test if the progress bar is displayed and is moving when a file is being uploaded.
	 */
    
    public boolean checkProgress() {
    	//Keeping the count of number of tests executed
    	noOfTestsExecuted +=1;
    	
    	//Using .sendKeys("File path"), we are able to upload the file path using selenium. This only works if control input type = file
    	browseButton.sendKeys("file:"+ path);;
    	uploadButton.click();

    	
    	Boolean isUploaded = false;
    	String currentPercentage = progressBar.getText();
    	
    	/*This is used to check the progress of file upload at every 2 secs. 
    	 * If there is no change in the progress after every 2 secs, then this will return false
    	 */
    	while(!isUploaded){
    		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    		
    		if(currentPercentage==progressBar.getText()){
    			return false;
    		}
    		currentPercentage=progressBar.getText();
    		isUploaded = progressBar.getCssValue("background-color").equals("rgba(255, 255, 255, 1)");
    		
    		
    	}
    	return progressBar.getText().contains("saved as:");
    }
    
    
    
    /* @Test_Name - Change file title
	 * @Description - This  method is used to test if file title can be changed when the file is being uploaded.
	 * 	              This also verifies if the changed file name is saved when the file upload is completed
	 */ 
	
    public boolean changeFileTitle() {
    	//Keeping the count of number of tests executed
    	noOfTestsExecuted +=1;
    	
    	// Initiating file upload
    	browseButton.sendKeys("file:"+ path);;
    	uploadButton.click();

    	Boolean isUploaded = false;
    	
    	//Clearing the text box and entering a new file title
    	titleBox.clear();
    	titleBox.sendKeys(fileTitle);
    	
    	while(!isUploaded){
    		
    		isUploaded = progressBar.getCssValue("background-color").equals("rgba(255, 255, 255, 1)");
     		
    	}
    	
    	//Checking if the file title is saved for the uploaded file 
    	return uploadedFile.getText().equals(fileTitle);
    }
    
   
    
    /*
     * This is the main method from where the execution starts
     */
    public static void main(String[] args)   {
    	
    	boolean testResult = false;
    	String testStatus = "Fail";
    	
    	// Navigate to AUT URL
     	
        driver.get("http://localhost:3000/");
        
     
       //Create a new instance of the class and initialize any WebElement fields in it.
 
        
        soundCloudTest page = PageFactory.initElements(driver, soundCloudTest.class);


        System.out.println("Test Result");
        
        
        // @Test_Name - File Upload
        testResult = page.fileUpload();
        testStatus = (testResult) ? "Pass" : "Fail";
        System.out.println("File Upload - " + testStatus);
       
        
        // @Test_Name - Check file upload progress
        testResult = page.checkProgress();
        testStatus = (testResult) ? "Pass" : "Fail";
        System.out.println("Check file upload progress - " + testStatus);
        
        // @Test_Name - Change file title
        testResult = page.changeFileTitle();
        testStatus = (testResult) ? "Pass" : "Fail";
        System.out.println("Change file title - " + testStatus);
        
        System.out.println("Total Number of tests executed - " + noOfTestsExecuted);
       
        //Clean up 
        driver.close();
        driver.quit();
    }

}
