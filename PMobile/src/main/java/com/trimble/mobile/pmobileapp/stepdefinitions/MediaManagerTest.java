package com.trimble.mobile.pmobileapp.stepdefinitions;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.trimble.mobile.core.enums.*;
import com.trimble.mobile.core.testcontext.TestContext;
import com.trimble.mobile.pmobileapp.pages.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MediaManagerTest {
	
	TestContext testContext;
	HomePage homePage;
	ApplicationToolBar toolBar;
	SystemPage systemPage;
	DriverPage driverPage;
	MediaManagerPage mediaManager;
	
	public MediaManagerTest(TestContext context) {
		testContext = context;
		homePage = testContext.getPageObjectManager().getHomePage();
		toolBar = testContext.getPageObjectManager().getToolBar();
		systemPage = testContext.getPageObjectManager().getSystemPage();
		driverPage = testContext.getPageObjectManager().getDriverPage();
		mediaManager = testContext.getPageObjectManager().getMediaManagerPage();
	}
	
	@Given("Driver is in Media Manager Page")
	public void driver_is_in_Media_Manager_Page() throws InterruptedException {
		toolBar.waitForPageTitle();
		if(toolBar.getPageTitle().equalsIgnoreCase("Media Manager - Downloads")||(toolBar.getPageTitle().equalsIgnoreCase("Media Manager - Pictures"))
				||  mediaManager.isSelectFolderPathPopUpDisplayed()==true){

		} else {
			toolBar.initialize();
			toolBar.waitTillPageTitleDisplayed("Home");
			homePage.clickSubSection(Fields.Driver);
			toolBar.waitTillPageTitleDisplayed("Driver");
			driverPage.clickSubSection(Fields.MediaManager);
		}
	}
	
	@Given("Driver taken picture and saved media using camera menu")
	public void driver_taken_picture_and_saved_media_using_camera_menu() throws InterruptedException {
		toolBar.waitTillPageTitleDisplayed("Home");
		homePage.clickSubSection(Fields.System);
		toolBar.waitTillPageTitleDisplayed("System");
		systemPage.clickSubSection(Fields.Camera);
		systemPage.takeSinglePicture();
		toolBar.waitTillPageTitleDisplayed("Home");
	}

	@When("Driver views Pnet Picture section in Media Manager Page")
	public void driver_views_Pnet_Picture_section_in_Media_Manager_Page() {
		mediaManager.selectFolderPath(Fields.Pictures);
		toolBar.waitTillPageTitleDisplayed("Media Manager - Pictures");
		mediaManager.validateMediaManagerScreenAttribute();
		testContext.getScenarioContext().setContext("Count", mediaManager.getListCount());
	}
	
	@Then("Newly taken picture should get updated in picture list view")
	public void newly_taken_picture_should_get_updated_in_picture_list_view() {
		int count = (int) testContext.getScenarioContext().getContext("Count");
		int count2 = mediaManager.getListCount();
		Assert.assertNotEquals(count, count2);
		Assert.assertEquals(count+1, count2);
	}
	
	
	@Given("Driver views download section in Media Manager Page")
	public void driver_views_download_section_in_Media_Manager_Page() {
		mediaManager.selectFolderPath(Fields.Download);
		toolBar.waitTillPageTitleDisplayed("Media Manager - Downloads");
		mediaManager.validateMediaManagerScreenAttribute();
	}
	

	@Given("Driver reboot PMobile application")
	public void driver_reboot_PMobile_application() {
		mediaManager.reLaunchApp();
		toolBar.waitTillPageTitleDisplayed("Home");
	}

	@When("Driver navigate to Media Manager Page")
	public void driver_navigate_to_Media_Manager_Page() {
		toolBar.waitTillPageTitleDisplayed("Home");
		homePage.clickSubSection(Fields.Driver);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
	}
	
	@When("Driver navigate back to picture view in Media Manager Page")
	public void driver_navigate_back_to_picture_view_in_Media_Manager_Page() {
		toolBar.waitTillPageTitleDisplayed("Home");
		homePage.clickSubSection(Fields.Driver);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		mediaManager.selectFolderPath(Fields.Pictures);
	}

	@Then("Driver will be prompted to select the folder path")
	public void driver_will_be_prompted_to_select_the_folder_path() {
		mediaManager.waitForFolderAlertTitle();
		mediaManager.verifySelectFolderPathPopUpUI();
		SoftAssert softAssertion= new SoftAssert();
		softAssertion.assertEquals(mediaManager.isMenuSelected(Fields.Download), "true");
		softAssertion.assertEquals(mediaManager.isMenuSelected(Fields.Pictures), "false");
		softAssertion.assertAll();
		mediaManager.selectFolderPath(Fields.Download);
	}
	
	@When("Driver navigate to some other page and return back to Media Manager page")
	public void driver_navigate_to_some_other_page_and_return_back_to_Media_Manager_page() {
		toolBar.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
	}
	
	@When("Driver change folder selection to Downloads")
	public void driver_change_folder_selection_to_Downloads() {
		mediaManager.selectChangeFolder();
		mediaManager.selectFolderPath(Fields.Download);
	}

	@Then("Driver has access to Download section in Media Manager")
	public void driver_has_access_to_Download_section_in_Media_Manager(){
	  toolBar.waitTillPageTitleDisplayed("Media Manager - Downloads");
	  Assert.assertEquals(toolBar.getPageTitle(),"Media Manager - Downloads");
	}
	
	@When("Driver change folder selection to Pictures")
	public void driver_change_folder_selection_to_Pictures() {
		mediaManager.selectChangeFolder();
		mediaManager.selectFolderPath(Fields.Pictures);
	}

	@Then("Driver has access to Pictures section in Media Manager")
	public void driver_has_access_to_Pictures_section_in_Media_Manager() {
		toolBar.waitTillPageTitleDisplayed("Media Manager - Pictures");
		Assert.assertEquals(toolBar.getPageTitle(),"Media Manager - Pictures");
	}
	
	@Given("There are existing pictures in the Pnet Picture folder section")
	public void there_are_existing_pictures_in_the_Pnet_Picture_folder_section() throws InterruptedException {
		mediaManager.selectFolderPath(Fields.Pictures);
		if(mediaManager.getListCount()>2) {
			testContext.getScenarioContext().setContext("listCount", mediaManager.getListCount());
		}else {
			toolBar.Back(2);
			toolBar.waitTillPageTitleDisplayed("Home");
			homePage.clickSubSection(Fields.System);
			systemPage.clickSubSection(Fields.Camera);
			systemPage.takeMultiplePicture();
			toolBar.waitTillPageTitleDisplayed("Home");
			homePage.clickSubSection(Fields.Driver);
			toolBar.waitTillPageTitleDisplayed("Driver");
			driverPage.clickSubSection(Fields.MediaManager);
			mediaManager.selectFolderPath(Fields.Pictures);
			testContext.getScenarioContext().setContext("listCount", mediaManager.getListCount());
		}
	}

	@When("Driver tries to delete single media from Pnet Picture folder")
	public void driver_tries_to_delete_single_media_from_Pnet_Picture_folder() {
		mediaManager.selectSingleImage();
		mediaManager.clickDelete();
		mediaManager.selectMenuFromDeleteAlert(Fields.Yes);
		
	}

	@Then("Media should get deleted successfully")
	public void media_should_get_deleted_successfully() {
		int count1 = (int) testContext.getScenarioContext().getContext("listCount");
		int count2 = mediaManager.getListCount();
		Assert.assertEquals(count1-1, count2);
	}
	
	@When("Driver hits delete button for deleting a media")
	public void driver_hits_delete_button_for_deleting_a_media() {
		mediaManager.selectSingleImage();
		mediaManager.clickDelete();
	}

	@Then("Delete confirmation GUF will be shown to the driver")
	public void delete_confirmation_GUF_will_be_shown_to_the_driver() {
		mediaManager.validateDeleteAlertUI();
		mediaManager.selectMenuFromDeleteAlert(Fields.Cancel);
	}
	
	@When("Driver tries to delete multiple files")
	public void driver_tries_to_delete_multiple_files() {
		mediaManager.selectMultipleImage();
		mediaManager.clickDelete();
		mediaManager.selectMenuFromDeleteAlert(Fields.Yes);
	}

	@Then("Selected multiple media got deleted successfully")
	public void selected_multiple_media_got_deleted_successfully() {
		int count1 = (int) testContext.getScenarioContext().getContext("listCount");
		int count2 = mediaManager.getListCount();
		Assert.assertEquals(count1-3, count2);
	}
	
	@When("Driver select multiple files to delete")
	public void driver_select_multiple_files_to_delete() {
		mediaManager.selectMultipleImage();
	}

	@When("Driver cancel delete operation")
	public void driver_cancel_delete_operation() {
		mediaManager.clickCancel();
	}

	@Then("Delete operation should be cancelled and selection will be cancelled")
	public void delete_operation_should_be_cancelled_and_selection_will_be_cancelled() {
		SoftAssert softAssertion = new SoftAssert();
		softAssertion.assertFalse(mediaManager.isCancelButtonDisplayed());
		softAssertion.assertFalse(mediaManager.isDeleteButtonDisplayed());
		int count1 = (int) testContext.getScenarioContext().getContext("listCount");
		int count2 = mediaManager.getListCount();
		softAssertion.assertEquals(count1, count2);
		softAssertion.assertAll();
	}
	
	@When("Driver tries to delete all files in the Pnet Picture section")
	public void driver_tries_to_delete_all_files_in_the_Pnet_Picture_section() {
		mediaManager.selectSingleImage();
		mediaManager.clickCheckAllCheckBox();
		mediaManager.clickDelete();
		mediaManager.selectMenuFromDeleteAlert(Fields.Yes);
	}

	@Then("All files should get deleted successfully")
	public void all_files_should_get_deleted_successfully() {
		Assert.assertEquals(mediaManager.getListCount(), 0);
	}
	
	@When("Driver hits delete button after deselecting all files")
	public void driver_hits_delete_button_after_deselecting_all_files() {
		mediaManager.deselectMultipleImage();
		mediaManager.clickDelete();
		mediaManager.selectMenuFromDeleteAlert(Fields.Yes);
		
	}

	@Then("None of the files got deleted")
	public void none_of_the_files_got_deleted() {
		int count1 = (int) testContext.getScenarioContext().getContext("listCount");
		int count2 = mediaManager.getListCount();
		Assert.assertEquals(count1, count2);
	}
	
	@When("Driver select all media using Check All check box")
	public void driver_select_all_media_using_Check_All_check_box() {
		mediaManager.selectSingleImage();
		mediaManager.clickCheckAllCheckBox();
	}

	@Then("Checkbox displayed to the left of all media will be selected")
	public void checkbox_displayed_to_the_left_of_all_media_will_be_selected() {
	    Assert.assertTrue(mediaManager.validateAllCheckBoxAreSelected());
	    mediaManager.clickCancel();
	}
	
	@When("Driver deselect all media using Check All check box")
	public void driver_deselect_all_media_using_Check_All_check_box() {
		mediaManager.clickCheckAllCheckBox();
	}
	
	@Then("Checkbox displayed to the left of all media will be deselected")
	public void checkbox_displayed_to_the_left_of_all_media_will_be_deselected() {
		Assert.assertFalse(mediaManager.validateAllCheckBoxAreSelected());
	    mediaManager.clickCancel();
	}
	
	@When("Driver views any image and select android back button")
	public void driver_views_any_image_and_select_android_back_button() {
		mediaManager.viewImage();
		mediaManager.waitTillImageLoaded();
		mediaManager.Back(1);
	}

	@Then("Driver should be returned to Pnet Picture - Media Manager page")
	public void driver_should_be_returned_to_Pnet_Picture_Media_Manager_page() {
		Assert.assertEquals(toolBar.getPageTitle(), "Media Manager - Pictures");
	}
	
	@When("Driver sort Picture view using Name field in ascending order")
	public void driver_sort_Picture_view_using_Name_field_in_ascending_order() {
		mediaManager.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		toolBar.waitForPageTitle();
	}

	@Then("All media should be sorted by Name in ascending order")
	public void all_media_should_be_sorted_by_Name_in_ascending_order() {
		Assert.assertTrue(mediaManager.isMediaListSorted(Fields.Name,SortingType.ascending));
		Assert.assertEquals(mediaManager.verifyListHeaderUI(Fields.Name), "Name ▲");
	}
	
	@When("Driver sort Picture view using Name field in descending order")
	public void driver_sort_Picture_view_using_Name_field_in_descending_order() {
		mediaManager.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		toolBar.waitForPageTitle();
		mediaManager.sortMenu(Fields.Name);
	}

	@Then("All media should be sorted by Name in descending order")
	public void all_media_should_be_sorted_by_Name_in_descending_order() {
		Assert.assertTrue(mediaManager.isMediaListSorted(Fields.Name,SortingType.descending));
		Assert.assertEquals(mediaManager.verifyListHeaderUI(Fields.Name), "Name ▼");
	}
	
	@When("Driver sort Picture view using Size field in ascending order")
	public void driver_sort_Picture_view_using_Size_field_in_ascending_order() {
		mediaManager.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		toolBar.waitForPageTitle();
		mediaManager.sortMenu(Fields.Size);
	}

	@Then("All media should be sorted by Size in ascending order")
	public void all_media_should_be_sorted_by_Size_in_ascending_order() {
		Assert.assertTrue(mediaManager.isMediaListSorted(Fields.Size,SortingType.ascending));
		Assert.assertEquals(mediaManager.verifyListHeaderUI(Fields.Size), "Size ▲");
	}

	@When("Driver sort Picture view using Size field in descending order")
	public void driver_sort_Picture_view_using_Size_field_in_descending_order() {
		mediaManager.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		toolBar.waitForPageTitle();
		mediaManager.sortMenu(Fields.Size);
		mediaManager.sortMenu(Fields.Size);
	}

	@Then("All media should be sorted by Size in descending order")
	public void all_media_should_be_sorted_by_Size_in_descending_order() {
		Assert.assertTrue(mediaManager.isMediaListSorted(Fields.Size,SortingType.descending));
		Assert.assertEquals(mediaManager.verifyListHeaderUI(Fields.Size), "Size ▼");
	}
	
	@When("Driver sort Picture view using Date field in ascending order")
	public void driver_sort_Picture_view_using_Date_field_in_ascending_order() {
		mediaManager.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		toolBar.waitForPageTitle();
		mediaManager.sortMenu(Fields.Date);
	}

	@Then("All media should be sorted by Date in ascending order")
	public void all_media_should_be_sorted_by_Date_in_ascending_order() {
		Assert.assertTrue(mediaManager.isMediaListSorted(Fields.Date,SortingType.ascending));
		Assert.assertEquals(mediaManager.verifyListHeaderUI(Fields.Date), "Date ▲");
	}

	@When("Driver sort Picture view using Date field in descending order")
	public void driver_sort_Picture_view_using_Date_field_in_descending_order() {
		mediaManager.Back(1);
		toolBar.waitTillPageTitleDisplayed("Driver");
		driverPage.clickSubSection(Fields.MediaManager);
		toolBar.waitForPageTitle();
		mediaManager.sortMenu(Fields.Date);
		mediaManager.sortMenu(Fields.Date);
	}

	@Then("All media should be sorted by Date in descending order")
	public void all_media_should_be_sorted_by_Date_in_descending_order() {
		Assert.assertTrue(mediaManager.isMediaListSorted(Fields.Date,SortingType.descending));
		Assert.assertEquals(mediaManager.verifyListHeaderUI(Fields.Date), "Date ▼");
	}
	
	@When("Driver cancel delete operation by clicking cancel in the pop up")
	public void driver_cancel_delete_operation_by_clicking_cancel_in_the_pop_up() {
		mediaManager.waitForDeleteAlertTitle();
		mediaManager.selectMenuFromDeleteAlert(Fields.Cancel);
	}
	
	@Then("Driver has access to Pnet Picture section in Media Manager Page")
	public void driver_has_access_to_Pnet_Picture_section_in_Media_Manager_Page() {
		toolBar.waitForPageTitle();
		Assert.assertEquals(toolBar.getPageTitle(), "Media Manager - Pictures");
	}

	@When("Driver taken multiple picture and saved media using camera menu")
	public void driver_taken_multiple_picture_and_saved_media_using_camera_menu() throws InterruptedException {
		homePage.clickSubSection(Fields.System);
		toolBar.waitTillPageTitleDisplayed("System");
		systemPage.clickSubSection(Fields.Camera);
		systemPage.takeMultiplePicture();
	}

	@Then("Multiple pictures taken should get updated in picture list view")
	public void multiple_pictures_taken_should_get_updated_in_picture_list_view() {
		int count = (int) testContext.getScenarioContext().getContext("Count");
		int count2 = mediaManager.getListCount();
		Assert.assertNotEquals(count, count2);
		Assert.assertEquals(count2, count+3);
	}

}
