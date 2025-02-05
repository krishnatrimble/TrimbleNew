package com.trimble.mobile.pmobileapp.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.trimble.mobile.core.appiumcommandbase.AppiumCommandsPage;
import com.trimble.mobile.core.enums.Fields;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage extends AppiumCommandsPage {
	
	// Tool bar Automation Id
	@FindBy(id = "pnetpage_titletext_textview")
	private WebElement titleText;
		
	@FindBy(id = "pnetpage_backbutton_imagebutton")
	private WebElement backButton;
	
	@FindBy(id="alert_message")
	private WebElement alertMessage;
	
	@FindBy(xpath="//*[@text='OK']")
	private WebElement Okbtn;
	
	@FindAll(@FindBy(id="navigatoritem_label_textview"))
	private List<WebElement> moduleMenu;
  
	@FindBy(xpath="//*[@text='Messaging']")
    private WebElement messagingMenu;
	
	@FindBy(xpath="//*[@text='Vehicle']")
    private WebElement vehicleMenu;
	
	@FindBy(xpath="//*[@text='Driver']")
    private WebElement driverMenu;
	
	@FindBy(xpath="//*[@text='System']")
    private WebElement systemMenu;
	
	@FindBy(xpath="//*[@text='Roadside']")
    private WebElement roadsideMenu;
	
	@FindBy(xpath="//*[@text='Workflow']")
    private WebElement workflowMenu;
	
	@FindBy(xpath="//*[@text='Navigation']")
    private WebElement navigationMenu;
	
	@FindBy(xpath="//*[@text='Panic Alarm']")
    private WebElement panicAlarmMenu;
	
	@FindBy(xpath="//*[@text='O.E.R.']")
    private WebElement OERMenu;
	
	public HomePage(AppiumDriver<WebElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void waitTillHomePageLoaded() {
		while ((titleText.getText()).equalsIgnoreCase("Home")) {
			break;
		}
	}
	
	public void closeLoginAlert() {
		clickElement(Okbtn);
	}

	public void clickSubSection(Fields menu) {
		switch(menu) {
			case Messaging:
    			clickElement(messagingMenu);
				break;
			case Vehicle:
    			clickElement(vehicleMenu);
				break;
			case Driver:
    			clickElement(driverMenu);
				break;
			case System:
    			clickElement(systemMenu);
				break;
			case Roadside:
    			clickElement(roadsideMenu);
				break;
			case Workflow:
    			clickElement(workflowMenu);
				break;
			case Navigation:
    			clickElement(navigationMenu);
				break;
			case PanicAlarm:
    			clickElement(panicAlarmMenu);
				break;
			case OER:
    			clickElement(OERMenu);
				break;
		}
	}
}
