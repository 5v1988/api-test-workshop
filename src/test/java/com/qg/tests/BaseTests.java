package com.qg.tests;

import com.qg.TestConstants;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class BaseTests implements TestConstants {

  @BeforeSuite
  public void setUp(){
    System.out.println("Running the tests!");
  }

  @AfterSuite
  public void tearDown(){
    System.out.println("Ending the tests!");
  }

}

