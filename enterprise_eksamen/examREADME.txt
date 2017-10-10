	
Packages:		
	* Enterprise Java Beans are in package westerdals.eksamen.enterprise.backend.ejb;
		-ejb tests.
	* @Entity classes are in westerdals.eksamen.enterprise.backend.entity;

	* Frontend controller classes are in package westerdals.eksamen.enterprise.frontend
	* Html files are in westerdals.eksamen.enterprise.frontend.webapp and inside this package i have a directory named eksamen which adds /eksamen/ to my_cantina URL, 
		however you can still open frontpage by just localhost:8080/my_cantina so i hope this meets the required specification

	* Page Objects are located in package westerdals.eksamen.enterprise.frontend.po
	* Utilities and Integration test files are in westerdals.eksamen.enterprise.frontend.


Afterthought:
	* I have done all the excercises except the last one "Extra" as i had no time for this one.

	* I should perhaps have had som more constraints on the beans, for eksampel regex for special characters in names, but i forgot and have no time to do that now.
	
	* My exeption handling is not perticularely well made either, but i prevent the site from crashing on bad input.


		
The tests described in the exam for backend are inside:    
	/***
     * #######################################################################################
     * Tests mentioned in the exam
     */
And the other thests are tests made while creating the beans.


Files taken or adapted from repository:
	* Pom files. 
	* DeleterEJB, 
	* JbossUtil, 
	* WebTestBase
	* WebTestBase.