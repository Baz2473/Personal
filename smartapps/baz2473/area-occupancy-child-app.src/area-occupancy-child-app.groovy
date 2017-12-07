/*
 Copyright (C) 2017 Baz2473
 
 Name: Area Occupancy Child App

 Version: 1.0.0
*/

definition	(
    name: "Area Occupancy Child App",
    namespace: "Baz2473",
    parent: "Baz2473:Area Occupancy",
    author: "Baz2473",
    description: "DO NOT INSTALL THIS APP DIRECTLY!!!!. This is a CHILD APP!!!",
    category: "My Apps",
    iconUrl: "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao1.png",
	iconX2Url: "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao2.png",
	iconX3Url: "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao3.png"
)

preferences {
	page(name: "areaName")
}

def areaName() {
	dynamicPage(name: "areaName", title: "A New Device Will Be Created With This Name!", install: true, uninstall: childCreated()) {
	if (!childCreated()) {
	     section {
		 label title: "What Name?\n(Required):", required: true,  submitOnChange: true
}}
    else {
          section {
	      paragraph "Area Name:\n${app.label}"
}}  
     section("Select Your Choice Of Activation Method!") {
     if(!contactOrAccelerationActivated && !followedBy) {
     input "motionActivated", "bool", title: "Motion?", defaultValue: false, submitOnChange: true
     }
     if(!motionActivated && !followedBy) {
     input "contactOrAccelerationActivated", "bool", title: "Seperate Contact/Acceleration?", defaultValue: false, submitOnChange: true
     }
     if(!motionActivated && !contactOrAccelerationActivated) {
     input "followedBy", "bool", title: "Followed By?", defaultValue: false, submitOnChange: true
     }
}
if(motionActivated == true) {
    section("Select The Motion Sensor(s) In '$app.label'") {
             input "entryMotionSensors", "capability.motionSensor", title: "Which Motion Sensor(s)?\n(Required)", required: true, multiple: true, submitOnChange: true            	     
}
if (entryMotionSensors && !exitMotionSensors) {
    section("Manually Force Vacant State\nWith A Timer For $app.label's Area?") {
             input "noExitSensor", "bool", title: "No Exit Sensor For\n$app.label?", defaultValue: false, submitOnChange: true
}}
if (entryMotionSensors || entryMotionTimeout) {
    section("Select This If There Is A Door\nMonitored 'Off Of' $app.label\nBut 'Not For' $app.label?") {
             input "monitoredDoor2", "bool", title: "Adjacent Monitored Door(s)?\n(Optional)", defaultValue: false, submitOnChange: true
}}
if (monitoredDoor2 == true) {
    section("Select The Door(s)\nThat Are Monitored 'Off Of' '$app.label'?") {
             input "adjacentDoors", "capability.contactSensor", title: "Which Adjacent Door(s)?\n(Required)", multiple: true, required: true, submitOnChange: true
}}
if (entryMotionSensors && monitoredDoor2 == true) {  
          section("Select The Motion Sensor(s) When The Door Is 'Open' In The Area's\nImmediately 'Outside' $app.label When 'Exiting'") {
                   input "exitMotionSensorsWhenDoorIsOpen", "capability.motionSensor", title: "Which 'Exit' Motion Sensor(s)?\n(Required)", required: true, multiple: true, submitOnChange: true     
}}
if (entryMotionSensors && monitoredDoor2 == true) {  
          section("Select The Motion Sensor(s) When The Door Is 'Closed' In The Area's\nImmediately 'Outside' $app.label When 'Exiting'") {
                   input "exitMotionSensorsWhenDoorIsClosed", "capability.motionSensor", title: "Which 'Exit' Motion Sensor(s)?\n(Required)", required: true, multiple: true, submitOnChange: true    
}}
if (entryMotionSensors && !doors2 && (monitoredDoor2 == false || monitoredDoor2 == null) && (noExitSensor == false || noExitSensor == null)) {  
          section("Select The Motion Sensor(s) In The Area's\nImmediately 'Outside' $app.label When 'Exiting'") {
                   input "exitMotionSensors", "capability.motionSensor", title: "Which 'Exit' Motion Sensor(s)?\n(Required)", required: true, multiple: true, submitOnChange: true   
}}
if (entryMotionSensors) {
    section("Use The Status Change Of Another Area To Change $app.label's State?") {
             input "otherAreaCheck", "bool", title: "Other Area State Change?\n(Optional)", defaultValue: false, submitOnChange: true
             if (otherAreaCheck == true) {
                 input "otherArea", "capability.estimatedTimeOfArrival", title: "Allow Subscribing To This Area's Status Changes?", required: true, multiple: true, submitOnChange: true
}}}
if (entryMotionSensors) {
    section("Perform Another Vacancy Check If $app.label Is 'Occupied' And Motionless?") {
             input "anotherVacancyCheck", "bool", title: "Another Vacancy Check?\n(Optional)", defaultValue: false, submitOnChange: true
             if (anotherVacancyCheck == true) {
                 input "anotherCheckIn", "number", title: "How Many Seconds 'After' Inactivity\nOnly When 'Occupied'?", required: true, submitOnChange: true, range: "actualEntrySensorsTimeout..99999"
}}}
if (noExitSensor == true) {
    section("Use A Countdown Timer To Change This Rooms State?") {
             input "countdownTimer", "bool", title: "Use A Countdown Timer?\n(Optional)", defaultValue: false, submitOnChange: true
             if (countdownTimer == true) { 
                 input "entryMotionTimeout", "number", title: "How Many Seconds After Inactivity?\n(Required)", required: false, multiple: false, defaultValue: null, range: "1..99999", submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) {
    section("Select If $app.label Has A Door To Monitor?") {
             input "monitoredDoor", "bool", title: "Monitor Door(s)?\n(Optional)", defaultValue: false, submitOnChange: true
}}
if (monitoredDoor == true) {
    section("Select The Door(s)\nThat Enter / Exit '$app.label'?") {
             input "doors", "capability.contactSensor", title: "Which Door(s)?\n(Required)", multiple: true, required: true, submitOnChange: true
}}
if ((exitMotionSensors || entryMotionTimeout) && doors) {
    section("IMPORTANT!!!\nInput $app.label's 'ACTUAL' Motion Sensor's 'Timeout'") {
             input "actualEntrySensorsTimeout", "number", title: "How Many Seconds?\n(Required) ",required: true, defaultValue: null, submitOnChange: true
}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) && !switches) {
    section("Do You Want 'Any' Light(s)\nTo Automatically Turn 'ON'?") {
             input "switchOnControl", "bool", title: "Auto 'ON' Control?\n(Optional)", defaultValue: false, submitOnChange: true
}}
if (switchOnControl == true) {
    section("Do You Want To Switch On Lights At Different Levels\nDuring Different Modes?") {
             input "switchOnModeControl", "bool", title: "Different Levels\nDuring Different Modes?\n(Optional)", defaultValue: false, submitOnChange: true
}}
if ((switchOnModeControl == false || switchOnModeControl == null) && switchOnControl == true) {
    section("Turn ON Which Dimmable Light(s)?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "dimmableSwitches1", "capability.switchLevel", title: "Switch(s) / Light(s)?\n(Required)", required: true, multiple: true, submitOnChange: true
             if (dimmableSwitches1) {          
                 input "setLevelTo", "number", title: "Set Light Level To What? %\n(Required)", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null             
}}}
if (switchOnModeControl == true) {
    section("Turn ON Which Dimmable Light(s)?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "dimmableSwitches2", "capability.switchLevel", title: "Switch(s) / Light(s)?\n(Required)", required: true, multiple: true, submitOnChange: true
             if (dimmableSwitches2) {          
                 input "duringMode1", "mode", title: "During Mode 1\n(Required)", required: true, multiple: false, submitOnChange: true
                 input "setLevelTo1", "number", title: "Set Light Level To What? %\n(Required)", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                    
                 }
             if (setLevelTo1 && dimmableSwitches2) {
                 input "duringMode2", "mode", title: "During Mode 2\n(Required)", required: true, multiple: false, submitOnChange: true
                 input "setLevelTo2", "number", title: "Set Light Level To What? %\n(Required)", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                              
                 }
             if (setLevelTo2 && dimmableSwitches2) {
                 input "duringMode3", "mode", title: "During Mode 3\n(Optional)", required: false, multiple: false, submitOnChange: true
                 input "setLevelTo3", "number", title: "Set Light Level To What? %\n(Optional)", required: false, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                           
                 }
             if (setLevelTo3 && dimmableSwitches2) {
                 input "duringMode4", "mode", title: "During Mode 4\n(Optional)", required: false, multiple: false, submitOnChange: true
                 input "setLevelTo4", "number", title: "Set Light Level To What? %\n(Optional)", required: false, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                             
                 }                  
}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) {
    section("Do You Want 'Any' Light's\nTo Automatically Turn 'OFF'?") {
             input "offRequired", "bool", title: "Auto 'OFF' Control?", defaultValue: false, submitOnChange: true
}}
if (offRequired == true) {
    section("Only If A Chosen Area Is 'Vacant'?") {
             input "thisArea", "capability.estimatedTimeOfArrival", title: "What Area?", defaultValue: null, multiple: false, required: false, submitOnChange: true
             if (offRequired == true && thisArea) {
                 input "andThisArea", "capability.estimatedTimeOfArrival",  title: "What Other Area?", defaultValue: null, multiple: false, required: false, submitOnChange: true
}}}
if (offRequired == true) {
    section("Do You Want 'Any' Light's\nTo Instantly Turn 'OFF'!!\nWhen $app.label Changes To Vacant?") {
             input "instantOff", "bool", title: "Option 1:\nInstant Off!!", defaultValue: false, submitOnChange: true
}}
if (instantOff == true) {
    section("Turn OFF Which Light(s)\nIMMEDIATELY When $app.label Changes to 'VACANT'") {
             input "switches3", "capability.switchLevel", title: "Which Switch(s) / Lights?\n(Required)", required: true, multiple: true, submitOnChange: true
}}
if (offRequired == true) {
    section("Do You Want 'Any' Light's\nTo Turn 'OFF' With A Delay?\nAfter $app.label Changes To 'VACANT'") {
             input "delayedOff", "bool", title: "Option 2:\nDelayed Off?", defaultValue: false, submitOnChange: true
}}
if (delayedOff == true) {
    section("Turn OFF Which Light(s)\nWith A Delay,\nAfter $app.label Changes to 'VACANT'") {
             input "switches2", "capability.switchLevel", title: "Which Switch(s) / Lights?\n(Required)", required: true, multiple: true, submitOnChange: true
             if (switches2) {
                 input "dimByLevel", "number", title: "Reduce Light Level By What?\nBefore Turning Off!\n(Optional)", required: false, multiple: false, range: "1..99", submitOnChange: true, defaultValue: null               
}}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) && switches2 && delayedOff == true) {
    section("How Many Seconds 'After' $app.label Is 'VACANT'\nUntil You Want The Light(s) To Dim Down?") {
             input "dimDownTime", "number", title: "How Many Seconds?\n(Required)", required: true, defaultValue: null, submitOnChange: true
}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) && switches2 && delayedOff == true) {
    section("How Many Seconds 'After'\n$app.label's Light(s) Have Dimmed Down,\nUntil You Want The Light(s) To Turn OFF?") {
             input "switchesOffCountdownInSeconds", "number", title: "How Many Seconds?\n(Required)", required: true, defaultValue: null, submitOnChange: true
}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) {
    section("Do You Want To Automatically Switch\n$app.label's Automation 'ON' If It Was Disabled\nWhen Activation Of Certain Mode(s) Occur?") {
             input "resetAutomation", "bool", title: "Reset Automation On Mode Selection?\n(Optional)", defaultValue: false, submitOnChange: true
             if (resetAutomation == true) {
                 input "resetAutomationMode", "mode", title: "Select Your Automation Reset Mode(s)?\n(Required)", required: true, multiple: true, submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) {
    section("Do You Require Switching $app.label To 'VACANT'\nOn Activation Of Your Away Mode(s)") {
             input "noAwayMode", "bool", title: "Auto Vacate When\nYour Away Mode(s) Activate?\n(Optional)", defaultValue: false, submitOnChange: true
             if (noAwayMode == true) {
                 input "awayModes", "mode", title: "Select Your Away Mode(s)?\n(Required)", required: true, multiple: true, submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) {
    section("Do You Require Switching $app.label To 'VACANT'\nIf Any Persons Presence Changes To Away?") {
             input "presence", "bool", title: "Auto Vacate On\nAny Presence Change?\n(Optional)", defaultValue: false, submitOnChange: true
             if (presence == true) {
                 input "presenceSensors", "capability.presenceSensor", title: "Select Who? Leaving\nWill Activate 'VACANT'\n(Required)", required: true, multiple: true, submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2 == true) {
    section("Heavy Use Control") {
             input "heavyuseControl", "bool", title: "Heavy Use Control?", submitOnChange: true
}} 
if (heavyuseControl == true) {
    section("How Many Times Must $app.label Change To Occupied\nBefore Activating 'Heavy Use'?") {
             input "instantHeavyuse", "bool", title: "INSTANT Heavy Use?", required: false, defaultValue: false, submitOnChange: true
             if (!instantHeavyuse == true) {
             input "numberBeforeHeavyuseActivation", "number", title: "How Many Times?", required: true, submitOnChange: true
             input "minutesBeforeHeavyuseActivation", "number", title: "Within How Many Minutes?", required: true, submitOnChange: true      
}}}             
if (heavyuseControl == true) {
    section("How Long (If Not Entered) Until You Require\n$app.label's 'Heavy Use State'\nTo Switch 'OFF'?") {
             input "heavyuseTimeout", "number", title: "How Many Seconds?", required: true, submitOnChange: true
}}
section("Do Not Disturb Control") {
         if (entryMotionSensors && monitoredDoor == true && doors) {
             input "donotdisturbControl", "bool", title: "Do Not Disturb Control?", submitOnChange: true
             if (donotdisturbControl == true) {
                 paragraph "How Many Minutes Must $app.label\nStay Motionless While 'Engaged'\nBefore Activating 'Do Not Disturb'?"
                 input "dndCountdown", "number", title: "How Many Minutes?", required: true, submitOnChange: true
          }} else {
                  paragraph "Do Not Disturb Is Only Accessable In Conjunction With Entry Motion Sensors & A Monitored Door!"               
}}
section("Subscriptions!") {
         input "subscriptionsSelected", "bool", title: "Override Subscriptions?", required: false, submitOnChange: true
         if (subscriptionsSelected == true) {
             if(exitMotionSensors) { input "exitMotionActiveSubscribed", "bool", title: "Exit Motion Active?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensors) { input "exitMotionInactiveSubscribed", "bool", title: "Exit Motion Inactive?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsOpen && monitoredDoor2 == true) { input "exitMotionWhenDoorIsOpenActiveSubscribed", "bool", title: "Exit Motion When Door Is Open Active?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsOpen && monitoredDoor2 == true) { input "exitMotionWhenDoorIsOpenInactiveSubscribed", "bool", title: "Exit Motion When Door Is Open Inactive?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsClosed && monitoredDoor2 == true) { input "exitMotionWhenDoorIsClosedActiveSubscribed", "bool", title: "Exit Motion When Door Is Closed Active?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsClosed && monitoredDoor2 == true) { input "exitMotionWhenDoorIsClosedInactiveSubscribed", "bool", title: "Exit Motion When Door Is Closed Inactive?", required: false, submitOnChange: true, defaultValue: true }
             if(doors && monitoredDoor == true) { input "monitoredDoorsOpeningSubscribed", "bool", title: "Monitored Doors Opening?", required: false, submitOnChange: true, defaultValue: true }
             if(doors && monitoredDoor == true) { input "monitoredDoorsClosingSubscribed", "bool", title: "Monitored Doors Closing?", required: false, submitOnChange: true, defaultValue: true }
             if(adjacentDoors && monitoredDoor2 == true) { input "adjacentMonitoredDoorOpeningSubscribed", "bool", title: "Adjacent Monitored Doors Opening?", required: false, submitOnChange: true, defaultValue: true }
             if(adjacentDoors && monitoredDoor2 == true) { input "adjacentMonitoredDoorClosingSubscribed", "bool", title: "Adjacent Monitored Doors Closing?", required: false, submitOnChange: true, defaultValue: true }
             if(dimmableSwitches1 && switchOnModeControl == false && switchOnControl == true) { input "onSwitchesAndLightsSubscribed1", "bool", title: "Lights Turning ON", required: false, submitOnChange: true, defaultValue: false }
             if(dimmableSwitches2 && switchOnModeControl == true && switchOnControl == true) { input "onSwitchesAndLightsSubscribed2", "bool", title: "Lights Turning ON", required: false, submitOnChange: true, defaultValue: false }
             if(switches2 && delayedOff == true && offRequired == true) { input "dimmingLightsSubscribed", "bool", title: "Lights Dimming Off", required: false, submitOnChange: true, defaultValue: false }
             if(switches3 && instantOff == true && offRequired == true) { input "instantOffSwitchesAndLightsSubscribed", "bool", title: "Lights Turning Off", required: false, submitOnChange: false }
             if(entryMotionSensors) { input "entryMotionActiveSubscribed", "bool", title: "Entry Motion Active", required: false, submitOnChange: true, defaultValue: true }
             if(entryMotionSensors) { input "entryMotionInactiveSubscribed", "bool", title: "Entry Motion Inactive", required: false, submitOnChange: true, defaultValue: true }
             input "occupancyStatusChangesSubscribed","bool", title: "$app.label's Occupancy Status Changes?", required: false, submitOnChange: true, defaultValue: true
          if(subscriptionsSelected == true) {
             if(otherArea && otherAreaCheck == true) {
                input "openOtherArea", "bool", title: "Open Other Area Subscriptions", defaultValue: false, submitOnChange: true
                 if(openOtherArea == true) {
                input "otherAreaSubscribedVacant", "bool", title: "Force A Vacancy Check When $otherArea Changes To VACANT!", required: false, submitOnChange: true, defaultValue: false
                input "otherAreaSubscribedOccupied", "bool", title: "Force A Vacancy Check When $otherArea Changes To OCCUPIED!", required: false, submitOnChange: true, defaultValue: false 
                input "otherAreaSubscribedEngaged", "bool", title: "Force A Vacancy Check When $otherArea Changes To ENGAGED", required: false, submitOnChange: true, defaultValue: false
                input "otherAreaSubscribedChecking", "bool", title: "Force A Vacancy Check When $otherArea Changes To CHECKING!", required: false, submitOnChange: true, defaultValue: false
                input "otherAreaSubscribedHeavyuse", "bool", title: "Force A Vacancy Check When $otherArea Changes To HEAVY USE!", required: false, submitOnChange: true, defaultValue: false 
                input "otherAreaSubscribedDonotdisturb", "bool", title: "Force A Vacancy Check When $otherArea Changes To DO NOT DISTURB", required: false, submitOnChange: true, defaultValue: false                 
               }} else {
                       paragraph "Checking the status of other areas is only possible if you have selected the area"
                       }}
    
}}}
if(contactOrAccelerationActivated == true) {
   if (!entryAccelerationChosen == true) {
    section("Do You Wish To Activate 'OCCUPIED'\nBy Contact Sensor(s) Opening?") {
            input "entryContactChosen", "bool", title: "Contact?", defaultValue: false, submitOnChange: true
            if (entryContactChosen == true) {
                    input "entryContactSensors", "capability.contactSensor", title: "Which Contact Sensor(s)", required: true, multiple: true, submitOnChange: true               	     
}}}
if (!entryContactChosen == true) {
    section("Do You Wish To Activate 'OCCUPIED'\nBy Acceleration Sensor(s) Being Active?") {
             input "entryAccelerationChosen", "bool", title: "Acceleration?", defaultValue: false, submitOnChange: true
             if (entryAccelerationChosen == true) {   
                 input "entryAccelerationSensors", "capability.accelerationSensor", title: "Which Acceleration Sensor(s)", required: true, multiple: true, submitOnChange: true                 	     
}}}
if (!exitAccelerationChosen == true) {
    section("Do You Wish To Activate 'VACANT'\nBy Contact Sensor(s) Opening?") {
            input "exitContactChosen", "bool", title: "Contact?", defaultValue: false, submitOnChange: true
            if (exitContactChosen == true) {
                input "exitContactSensors", "capability.contactSensor", title: "Which Contact Sensor(s)", required: true, multiple: true, submitOnChange: true
                if (exitContactSensors) {
                    input "onlyIfInactive2", "bool", title: "Only If\nSelected Motion Sensors\nAre 'Inactive'?\n(Optional)", defaultValue: false, submitOnChange: true
                    if (onlyIfInactive2 == true) {
                        input "onlyIfThisSensor2", "capability.motionSensor", title: "These Motion Sensor(s)\nMust Be Inactive!", required: true, multiple: true, submitOnChange: true                                       
}}}}}
if (!exitContactChosen == true) {
    section("Do You Wish To Activate 'VACANT'\nBy Acceleration Sensor(s) Being Active?") {
             input "exitAccelerationChosen", "bool", title: "Acceleration?", defaultValue: false, submitOnChange: true
             if (exitAccelerationChosen == true) {
                    input "exitAccelerationSensors", "capability.accelerationSensor", title: "Which Acceleration Sensor(s)", required: true, multiple: true, submitOnChange: true
                 if (exitAccelerationSensors) {
                     input "onlyIfInactive2", "bool", title: "Only If\nSelected Motion Sensors\nAre 'Inactive'?\n(Optional)", defaultValue: false, submitOnChange: true
                     if (onlyIfInactive2 == true) { 
                         input "onlyIfThisSensor2", "capability.motionSensor", title: "These Motion Sensor(s)?\nMust Be Inactive!", required: true, multiple: true, submitOnChange: true                                                 
}}}}}}
if (followedBy == true) {
    section("Please Select The 2 Actioins Required To Activate Occupied & Vacant!") {
             input "firstAction", "capability.contactSensor", title: "Which Contact Sensor?", required: true, multiple: false, submitOnChange: true
             input "secondAction", "capability.accelerationSensor", title: "Which Acceleration Sensor?", required: true, multiple: false, submitOnChange: true
}}
if (followedBy == true && !switches4) {
    section("Do You Want Any Light(s)\nTo Automatically Turn 'ON'?") {
             input "switch4OnControl", "bool", title: "Auto 'ON' Control?\n(Optional)", defaultValue: false, submitOnChange: true
}}
if (followedBy == true && switch4OnControl == true) {
    section("Turn ON Which Switch(s)?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "switches4", "capability.switch", title: "Switch(s)?\n(Required)", required: true, multiple: true, submitOnChange: true            
}}
if (switches4) {
    section("Do You Want Any Switch(s)\nTo Turn 'OFF'!!\nWhen $app.label Changes To Vacant?") {
             input "instant4Off", "bool", title: "Instant Off!!", defaultValue: false, submitOnChange: true
}}
if (instant4Off == true) {
    section("Turn OFF Which Switch(s)\nWhen $app.label Changes to 'VACANT'") {
             input "switches5", "capability.switch", title: "Which Switch(s)\n(Required)", required: true, multiple: true, submitOnChange: true
}}
section("Notifications?") {
           input "sendNotifications", "bool", title: "Send Notifications?", defaultValue: false, submitOnChange: true
           if (sendNotifications == true) {
           if (exitMotionSensors || exitContactSensors || exitAccelerationSensors || exitContactFBExitContactSensors || exitContactFBExitAccelerationSensors ||
               exitAccelerationFBExitContactSensors || exitAccelerationFBExitAccelerationSensors || followedBy) {
               input "sendVacantNotification", "bool", title: "When $app.label Changes To\n'Vacant'?", required: false, submitOnChange: true
               if (sendVacantNotification == true) {
                         input "vacantMessage", "text", title: "What Message?", required: true
                         input("recipients", "contact", title: "Send notifications to:") {
                         input "phone", "phone", title: "Warn with text message (optional)",
                         description: "Phone Number", required: false	
                         }}} else {
                                    paragraph "Sending an 'Vacant' notification is only available if you select a source for detecting vacancy!" 
                                    }
           if (entryMotionSensors || entryContactSensors || entryAccelerationSensors || entryContactFBEntryContactSensors || entryContactFBEntryAccelerationSensors ||
               entryAccelerationFBEntryContactSensors || entryAccelerationFBEntryAccelerationSensors || followedBy) {               
               input "sendOccupiedNotification", "bool", title: "When $app.label Changes To\n'Occupied'?", required: false, submitOnChange: true
               if (sendOccupiedNotification == true) {
		                 input "occupiedMessage", "text", title: "What Message?", required: true
                         input("recipients", "contact", title: "Send notifications to:") {
                         input "phone", "phone", title: "Warn with text message (optional)",
                         description: "Phone Number", required: false	
                         }}} else {
                                    paragraph "Sending an 'Occupied' notification is only available if you select a source for detecting occupancy!"
                                    }
          
          } // end of send notification (true) section
       } // end of all of send notifications section! 
  
section("Time Actions") {
         input "timedControl", "bool", title: "Timed Control?", required: false, defualtValue: false, submitOnChange: true
         if (timedControl == true) {
             input "timedTurnOnControl", "bool", title: "Turn ON?", required: false, defualtValue: false, submitOnChange: true
             if (timedTurnOnControl == true && timedControl == true) {
                 input "switchOnAtThisTime", "capability.switch", title: "Which Switch(s)?", required: true, multiple: true, submitOnChange: true
                 input "onAtThisTime", "time", title: "At What Time?", required: true, submitOnChange: true
                 }
             input "timedTurnOffControl", "bool", title: "Turn OFF?", required: false, defualtValue: false, submitOnChange: true
             if (timedTurnOffControl == true && timedControl == true) {
                 input "switchOffAtThisTime", "capability.switch", title: "Which Switch(s)?", required: true, multiple: true, submitOnChange: true
                 input "offAtThisTime", "time", title: "At What Time?", required: true, submitOnChange: true 
         }}}
section("Which Light(s) Are In $app.label?") {
             input "checkableLights", "capability.switch", title: "Which Light(s)?\n(Required)", required: true, multiple: true, submitOnChange: true
}
}}                                                
//---------------------------------------------------------------------------------------------------------------------------------------------------------

def installed() {}

def updated() {
	unsubscribe()
	initialize()
    
    state.occupiedCounter = 0
     
if (!childCreated()) {
     spawnChildDevice(app.label) 
     }        
    if (adjacentDoors && monitoredDoor2 == true && adjacentMonitoredDoorOpeningSubscribed == true) { 
        subscribe(adjacentDoors, "contact.open", adjacentMonitoredDoorOpeningEventHandler) 
        }
    if (adjacentDoors && monitoredDoor2 == true && adjacentMonitoredDoorClosingSubscribed == true) { 
        subscribe(adjacentDoors, "contact.closed", adjacentMonitoredDoorClosingEventHandler) 
        }
    if (awayModes && noAwayMode == true || resetAutomationMode && resetAutomation == true) { 
        subscribe(location, modeEventHandler) 
        }
    if (checkableLights) {
        subscribe(checkableLights, "switch.on", checkableLightsSwitchedOnEventHandler)
        subscribe(checkableLights, "switch.off", checkableLightsSwitchedOffEventHandler)
        }
    if (dimmableSwitches1 && switchOnControl == true && switchOnModeControl == false && onSwitchesAndLightsSubscribed1 == true) {
        subscribe(dimmableSwitches1, "switch.on", dimmableSwitches1OnEventHandler)////
        subscribe(dimmableSwitches1, "switch.off", dimmableSwitches1OffEventHandler)////
        }          
    if (dimmableSwitches2 && switchOnControl == true && switchOnModeControl == true && onSwitchesAndLightsSubscribed2 == true) {
        subscribe(dimmableSwitches2, "switch.on", dimmableSwitches2OnEventHandler)////
        subscribe(dimmableSwitches2, "switch.off", dimmableSwitches2OffEventHandler)////
        }   
    if (doors && monitoredDoor == true && monitoredDoorsOpeningSubscribed == true) { 
        subscribe(doors, "contact.open", monitoredDoorOpenedEventHandler) 
        }
    if (doors && monitoredDoor == true && monitoredDoorsClosingSubscribed == true) {
        subscribe(doors, "contact.closed", monitoredDoorClosedEventHandler) 
        }  
    if (entryContactSensors && contactOrAccelerationActivated == true) {
        subscribe(entryContactSensors, "contact.open", entryContactOpenedEventHandler)
        }
    if (entryAccelerationSensors && contactOrAccelerationActivated == true) {
        subscribe(entryAccelerationSensors, "acceleration.active", entryContactOpenedEventHandler)
        }
    if (exitContactSensors && contactOrAccelerationActivated == true) {
        subscribe(exitContactSensors, "contact.open", exitContactOpenedEventHandler)
        }
    if (exitAccelerationSensors && contactOrAccelerationActivated == true) {
        subscribe(exitAccelerationSensors, "acceleration.active", exitContactOpenedEventHandler)
        }
    if (entryMotionSensors && entryMotionActiveSubscribed == true)	{ 
        subscribe(entryMotionSensors, "motion.active", entryMotionActiveEventHandler)
        }
    if (entryMotionSensors && entryMotionInactiveSubscribed == true)	{ 
        subscribe(entryMotionSensors, "motion.inactive", entryMotionInactiveEventHandler) 
        }
    if (exitMotionSensors && exitMotionActiveSubscribed == true) {
        subscribe(exitMotionSensors, "motion.active", exitMotionActiveEventHandler)
        }
    if (exitMotionSensors && exitMotionInactiveSubscribed == true) { 
        subscribe(exitMotionSensors, "motion.inactive", exitMotionInactiveEventHandler)  
        }
    if (exitMotionSensorsWhenDoorIsOpen && monitoredDoor2 == true && exitMotionWhenDoorIsOpenActiveSubscribed == true) {
        subscribe(exitMotionSensorsWhenDoorIsOpen, "motion.active", exitMotionSensorsWhenDoorIsOpenActiveEventHandler)
        }
    if (exitMotionSensorsWhenDoorIsOpen && monitoredDoor2 == true && exitMotionWhenDoorIsOpenInactiveSubscribed == true) {
        subscribe(exitMotionSensorsWhenDoorIsOpen, "motion.inactive", exitMotionSensorsWhenDoorIsOpenInactiveEventHandler)
        }
    if (exitMotionSensorsWhenDoorIsClosed && monitoredDoor2 == true && exitMotionWhenDoorIsClosedActiveSubscribed == true) {
        subscribe(exitMotionSensorsWhenDoorIsClosed, "motion.active", exitMotionSensorsWhenDoorIsClosedActiveEventHandler)
        }
    if (exitMotionSensorsWhenDoorIsClosed && monitoredDoor2 == true && exitMotionWhenDoorIsClosedInactiveSubscribed == true) {
        subscribe(exitMotionSensorsWhenDoorIsClosed, "motion.inactive", exitMotionSensorsWhenDoorIsClosedInactiveEventHandler)
        }
    if (followedBy == true && firstAction) {
        subscribe(firstAction, "contact.open", followedByContactOpenedEventHandler)
        state.backDoorHasBeenOpened = false
        }    
    if (followedBy == true && secondAction) {
        subscribe(secondAction, "acceleration.active", followedByAccelerationActiveEventHandler)
        state.gateHasBeenOpened = false
        }        
    if (otherArea && otherAreaCheck == true && otherAreaSubscribedVacant == true) {
        subscribe(otherArea, "occupancyStatus.vacant", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck == true && otherAreaSubscribedOccupied == true) {
        subscribe(otherArea, "occupancyStatus.occupied", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck == true && otherAreaSubscribedEngaged == true) {
        subscribe(otherArea, "occupancyStatus.engaged", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck == true && otherAreaSubscribedChecking == true) {
        subscribe(otherArea, "occupancyStatus.checking", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck == true && otherAreaSubscribedHeavyuse == true) {
        subscribe(otherArea, "occupancyStatus.heavyuse", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck == true && otherAreaSubscribedDonotdisturb == true) {
        subscribe(otherArea, "occupancyStatus.donotdisturb", otherAreaOccupancyStatusEventHandler)
        }
    if (presenceSensors && presence == true) { 
        subscribe(presenceSensors, "presence.not present", presenceAwayEventHandler) 
        }
    if (switches2 && delayedOff == true && dimmingLightsSubscribed == true) { 
        subscribe(switches2, "switch.on", switches2OnEventHandler)////
        subscribe(switches2, "switch.off", switches2OffEventHandler)////
        }
    if (switches3 && instantOff == true && instantOffSwitchesAndLightsSubscribed == true) { 
        subscribe(switches3, "switch.on", switches3OnEventHandler)////
        subscribe(switches3, "switch.off", switches3OffEventHandler)////
}}
//---------------------------------------------------------------------------------------------------------------------------------------------------------
def	initialize() { 

if (onAtThisTime && timedTurnOnControl == true && timedControl == true) {
    schedule(onAtThisTime, turnOnAtThisTime) 
    }
if (offAtThisTime && timedTurnOffControl == true && timedControl == true) {
    schedule(offAtThisTime, turnOffAtThisTime) 
    }
}
def uninstalled() {
	getChildDevices().each {
    deleteChildDevice(it.deviceNetworkId) }
    }
//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
def mainAction() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (entryMotionState.value.contains("active")) {     
        if(dimmableSwitches1 && switchOnControl == true && switchOnModeControl == false && ['automationon'].contains(automationState)) {
           dimmableSwitches1.each {
           it.on()
           def currentLevel = it.currentValue("level")
           if (currentLevel < setLevelTo) { 
               it.setLevel(setLevelTo)
               }}}
        if(dimmableSwitches2 && switchOnControl == true && switchOnModeControl == true && ['automationon'].contains(automationState)) {
           def currentMode = location.currentMode
           dimmableSwitches2.each {  
           it.on()
           def currentLevel = it.currentValue("level")
           if (currentMode.name == duringMode1 && currentLevel < setLevelTo1) {
               it.setLevel(setLevelTo1)
               }
           if (currentMode.name == duringMode2 && currentLevel < setLevelTo2) {
               it.setLevel(setLevelTo2)
               }
           if (currentMode.name == duringMode3 && currentLevel < setLevelTo3) {
               it.setLevel(setLevelTo3)
               } 
           if (currentMode.name == duringMode4 && currentLevel < setLevelTo4) {
               it.setLevel(setLevelTo4)
               }    
           }} 
               if (doors) {                      
                   def doorsState = doors.currentState("contact") 
                   if (!doorsState.value.contains("open") && ['donotdisturb','donotdisturbon','vacant','vacanton'].contains(areaState)) {
                        engaged()
                        } else {
                                if (!doorsState.value.contains("open") && ['occupied','occupiedon','heavyuse','heavyuseon'].contains(areaState)) { 
                                     checking()                 
                                     } else { 
                                             if (!doorsState.value.contains("open") && ['checking','checkingon'].contains(areaState)) {                                                                                                       
                                                  runIn(actualEntrySensorsTimeout, engaged, [overwrite: false])
                                                  } else { 
                                                          if (doorsState.value.contains("open") && ['checking','checkingon','engaged','engagedon','donotdisturb','donotdisturbon'].contains(areaState)) { 
                                                              occupied()   
                                                              } else { 
                                                                      if (['vacant','vacanton'].contains(areaState)) { 
                                                                            occupied()
                                                                            } 
}}}}}
                       else { 
                             if (['vacant','vacanton'].contains(areaState)) { 
                                   occupied()
                                   }       
}}  //------INACTIVE FROM HERE DOWN-------
     else {  
           if (noExitSensor == true && ['occupied','occupiedon'].contains(areaState)) {                         
               runIn(entryMotionTimeout, vacant, [overwrite: false])
               }
           if (heavyuseControl == true && ['heavyuse','heavyuseon'].contains(areaState)) {                               
               runIn(heavyuseTimeout, heavyuseCheck, [overwrite: false])
               }
           if (donotdisturbControl == true && ['engaged','engagedon'].contains(areaState)) {                             
               runIn(dndCountdown * 60, donotdisturb, [overwrite: false])
               }
           if (exitMotionSensors && ['occupied','occupiedon'].contains(areaState) && !adjacentDoors) {
               def exitMotionState = exitMotionSensors.currentState("motion")
               if (exitMotionState.value.contains("active")) { 
                   vacant()
                   }} 
           if (adjacentDoors && ['occupied','occupiedon'].contains(areaState) && !exitMotionSensors) {         
               def adjacentDoorsState = adjacentDoors.currentValue("contact")
               if (adjacentDoorsState.contains("open")) {
                   def exitMotionStateWithDoorsOpen = exitMotionSensorsWhenDoorIsOpen.currentValue("motion") 
                   if (exitMotionStateWithDoorsOpen.contains("active")) {
                       vacant()
                       }} else {
                                def exitMotionStateWithDoorsClosed = exitMotionSensorsWhenDoorIsClosed.currentValue("motion") 
                                if (exitMotionStateWithDoorsClosed.contains("active")) {
                                    vacant()
                                    }}}                                
           if (['checking','checkingon'].contains(areaState)) { 
                 runIn(actualEntrySensorsTimeout, vacant, [overwrite: false])
                 } else {
                         if (anotherVacancyCheck == true && anotherCheckIn && ['occupied','occupiedon'].contains(areaState)) {
                             runIn(anotherCheckIn, forceVacantIf, [overwrite: false])
                             }
                         if (switches3 && instantOff == true && offRequired == true) { 
                             def switches3State = switches3.currentState("switch")  
                             if (switches3State.value.contains("on") && ['vacanton'].contains(areaState) && ['automationon'].contains(automationState)) { 
                                 if (thisArea && !andThisArea) { 
                                     def thisAreaState = thisArea.currentState("occupancyStatus")
                                     if (thisAreaState.value.contains("vacant")) {
                                         switches3.off()
                                         } else { 
                                                 runIn(30, checkOtherAreaAgain)
                                                 }} else {
                                                          if (thisArea && andThisArea) { 
                                                              def thisAreaState = thisArea.currentState("occupancyStatus")
                                                              def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                                              if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                                                  switches3.off()     
                                                                  } else {
                                                                          runIn(30, checkOtherAreaAgain)
                                                                          }} else {
                                                                                   switches3.off()
                                                                                   }}}}
                        if (switches2 && delayedOff == true && offRequired == true) { 
                            def switches2State = switches2.currentState("switch")  
                            if (switches2State.value.contains("on") && ["vacanton"].contains(areaState) && ['automationon'].contains(automationState)) { 
                                if (thisArea && !andThisArea) { 
                                    def thisAreaState = thisArea.currentState("occupancyStatus")
                                    if (thisAreaState.value.contains("vacant")) {
                                        runIn(dimDownTime, dimLights)
                                        } else {
                                                runIn(30, checkOtherAreaAgain)                                
                                                }} else {
                                                         if (thisArea && andThisArea) { 
                                                             def thisAreaState = thisArea.currentState("occupancyStatus")
                                                             def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                                             if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                                                 runIn(dimDownTime, dimLights)   
                                                                 } else {
                                                                         runIn(30, checkOtherAreaAgain)
                                                                         }} else {
                                                                                  runIn(dimDownTime, dimLights)
                                                                                  }}}}                      
}}}                    
//888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
def adjacentMonitoredDoorClosingEventHandler(evt) {
    log.info "Re-Evaluation Caused By An Ajacent Monitored Door Closing"
    mainAction()
} 
def adjacentMonitoredDoorOpeningEventHandler(evt) {
    log.info "Re-Evaluation Caused By An Ajacent Monitored Door Opening"
    mainAction()
}
def checkableLightsSwitchedOnEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacant'].contains(areaState)) { 
          child.generateEvent('vacanton')
          } else {
                  if (['occupied'].contains(areaState)) { 
                        child.generateEvent('occupiedon')
                        } else {
                                if (['engaged'].contains(areaState)) { 
                                      child.generateEvent('engagedon')
                                      } else {
                                              if (['checking'].contains(areaState)) { 
                                                    child.generateEvent('checkingon')
                                                    } else {
                                                            if (['heavyuse'].contains(areaState)) { 
                                                                  child.generateEvent('heavyuseon')
                                                                  } else {
                                                                          if (['donotdisturb'].contains(areaState)) { 
                                                                                child.generateEvent('donotdisturbon')
                                                                 
                                                    
}}}}}}}
def checkableLightsSwitchedOffEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacanton'].contains(areaState)) { 
          child.generateEvent('vacant')
          } else {
                  if (['occupiedon'].contains(areaState)) { 
                        child.generateEvent('occupied')
                        } else {
                                if (['engagedon'].contains(areaState)) { 
                                      child.generateEvent('engaged')
                                      } else {
                                              if (['checkingon'].contains(areaState)) { 
                                                    child.generateEvent('checking')
                                                    } else {
                                                            if (['heavyuseon'].contains(areaState)) { 
                                                                  child.generateEvent('heavyuse')
                                                                  } else {
                                                                          if (['donotdisturbon'].contains(areaState)) { 
                                                                                child.generateEvent('donotdisturb')
}}}}}}} 
def checking() {
def child = getChildDevice(getArea())
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('checkingon')
           } else {                
                   child.generateEvent('checking')
                   }} else {
                            child.generateEvent('checking')
                            }
    if(occupancyStatusChangesSubscribed) { 
       log.info "Re-Evaluation Caused By $app.label Changing To Checking"
       mainAction() 
}}
def checkOtherAreaAgain() {
    unschedule(checkOtherAreaAgain)
    mainAction()
}
private childAreaState() { 
        return (getChildDevice(getArea())).getAreaState() 
}
private childAutomationState()  { 
        return (getChildDevice(getArea())).getAutomationState()  
}
private childCreated() {
        if (getChildDevice(getArea()))
            return true
            else
            return false
}
def childUninstalled() {
    log.debug "${app.label} Area Has Been Uninstalled!"
}
def dimLights() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (offRequired == true && ['automationon'].contains(automationState) && !['heavyuse','heavyuseon'].contains(areaState)) {
        switches2.each {
        if (it.currentValue("switch") == 'on') {
            def currentLevel = it.currentValue("level")
            def newLevel = (currentLevel > dimByLevel ? currentLevel - dimByLevel : 1)
                it.setLevel(newLevel)
                switchesOffCountdown()        
}}}} 
def dimmableSwitches1OnEventHandler(evt) { 
    log.info "Re-Evaluated by Dimmable Switches1 On"
    mainAction() 
}
def dimmableSwitches1OffEventHandler(evt) {
    log.info "Re-Evaluated by Dimmable Switches1 Off"
    mainAction() 
}
def dimmableSwitches2OnEventHandler(evt) {
    log.info "Re-Evaluated by Dimmable Switches2 On"
    mainAction() 
}
def dimmableSwitches2OffEventHandler(evt) {
    log.info "Re-Evaluated by Dimmable Switches2 Off"
    mainAction() 
}
private dimmableSwitches1On() {
        def child = getChildDevice(getArea())
        def automationState = child.getAutomationState()
        if (switchOnModeControl == false) {
            if (dimmableSwitches1 && switchOnControl == true && ['automationon'].contains(automationState)) {      
                log.debug "The Selected Switch(es) Have Either Been Turned 'ON' Or Had Their Respective Level(s) & Color(s) Set"
                dimmableSwitches1.each {
                def currentLevel = it.currentValue("level")
                if (currentLevel < setLevelTo) {  
                    it.setLevel(setLevelTo)
}}}}}
private dimmableSwitches2On() {
        def child = getChildDevice(getArea())
        def automationState = child.getAutomationState()
        if (switchOnModeControl == true) {
           def currentMode = location.currentMode
           if (dimmableSwitches2 && switchOnControl == true && ['automationon'].contains(automationState)) {      
               log.debug "The Selected Switch(es) Have Either Been Turned 'ON' Or Had Their Respective Level(s) & Color(s) Set"
               dimmableSwitches2.each {    
               def currentLevel = it.currentValue("level")
                if (currentMode.name == duringMode1 && currentLevel < setLevelTo1) {
                   it.setLevel(setLevelTo1)
                   }
               if (currentMode.name == duringMode2 && currentLevel < setLevelTo2) {
                   it.setLevel(setLevelTo2)
                   }
               if (currentMode.name == duringMode3 && currentLevel < setLevelTo3) {
                   it.setLevel(setLevelTo3)
                   } 
               if (currentMode.name == duringMode4 && currentLevel < setLevelTo4) {
                   it.setLevel(setLevelTo4)
                   }      
}}}} 
def donotdisturb() {      
def child = getChildDevice(getArea())
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('donotdisturbon')
           } else {                
                   child.generateEvent('donotdisturb')
                   }} else {
                            child.generateEvent('donotdisturb')
                            }
    if(occupancyStatusChangesSubscribed) {
       log.info "Re-Evaluation Caused By $app.label Changing To Do Not Disturb"
       mainAction() 
}}
def engaged() {
    def child = getChildDevice(getArea())
        if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('engagedon')
           } else {                
                   child.generateEvent('engaged')
                   }} else {
                            child.generateEvent('engaged')
                            }
        if(occupancyStatusChangesSubscribed == true) { 
           log.info "Re-Evaluation Caused By $app.label Changing To Engaged"
           mainAction() 
}}
def entryContactOpenedEventHandler(evt) {
    log.debug "An Entry Contact Was Opened In $app.label"
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacant'].contains(areaState)) {
          log.debug "An Entry Contact Was Opened, $app.label Was 'VACANT', So 'OCCUPIED' Has Been Set!"
          occupied()                                                                    
}}
def exitContactOpenedEventHandler(evt) {
     if (onlyIfThisSensor2 && onlyIfInactive2 == true) {
         def cMotionState = onlyIfThisSensor2.currentState("motion")
         if (!cMotionState.value.contains("active")) {
              log.debug "An Exit Contact Was Opened & The Motion Requirement Was Met"
              def child = getChildDevice(getArea())
              def areaState = child.getAreaState()
              if (['occupied','occupiedon'].contains(areaState)) {
                    log.debug "$app.label Was 'OCCUPIED', So 'VACANT' State Has Been Set!"
                    vacant()                                                                    
}}} else {
          if (onlyIfInactive2 == false) {
              log.debug "An Exit Contact Was Opened & There Was No Motion Restriction Set"
              def child = getChildDevice(getArea())
              def areaState = child.getAreaState()
              if (['occupied','occupiedon'].contains(areaState)) {
                    log.debug "$app.label Was 'OCCUPIED', So 'vacant' State Has Been Set!"
                    vacant()                                                                     
}}}}
def	entryMotionActiveEventHandler(evt) {
    log.info "Re-Evaluation Caused By An Entry Motion Sensor Being 'ACTIVE'"
    unschedule(engaged)
    unschedule(vacant)
    unschedule(switches2Off)
    unschedule(dimLights)
    unschedule(donotdisturb)
    unschedule(forceVacantIf)
    unschedule(heavyuseCheck)
    unschedule(checkOtherAreaAgain)
    mainAction() 
}
def	entryMotionInactiveEventHandler(evt) {
    log.info "Re-Evaluation Caused By An Entry Motion Sensor Being 'INACTIVE'"
    unschedule(engaged)
    mainAction() 
}
def exitMotionActiveEventHandler(evt) { 
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           log.info "Re-Evaluation Caused By An Exit Motion Sensor Being 'ACTIVE'"
           mainAction() 
}}
def exitMotionInactiveEventHandler(evt) { 
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           log.info "Re-Evaluation Caused By An Exit Motion Sensor Being 'INACTIVE'"
           mainAction() 
}}
def exitMotionSensorsWhenDoorIsOpenActiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (adjacentDoorsState.contains("open")) {
            log.info "Re-Evaluation Caused By An (OPEN) Exit Motion Sensor Being 'ACTIVE'"
            mainAction()
}}}}
def exitMotionSensorsWhenDoorIsOpenInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (adjacentDoorsState.contains("open")) {
            log.info "Re-Evaluation Caused By An (OPEN) Exit Motion Sensor Being 'INACTIVE'"
            mainAction()
}}}}
def exitMotionSensorsWhenDoorIsClosedActiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (!adjacentDoorsState.contains("open")) {
             log.info "Re-Evaluation Caused By A (CLOSED) Exit Motion Sensor Being 'ACTIVE'"
             mainAction()
}}}}
def exitMotionSensorsWhenDoorIsClosedInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (!adjacentDoorsState.contains("open")) {
             log.info "Re-Evaluation Caused By A (CLOSED) Exit Motion Sensor Being 'INACTIVE'"
             mainAction()
}}}} 
def followedByAccelerationActiveEventHandler(evt) {
    if (state.backDoorHasBeenOpened == true) {
        state.backDoorHasBeenOpened = false
        occupied()
        if (switches4) {
            switches4.on()
            }
        unschedule(resetBackDoor)
        } else {
                if (state.gateHasBeenOpened == false) {
                    state.gateHasBeenOpened = true
                    runIn(30, resetGate)
                    } else {
                            state.gateHasBeenOpened = false
                            unschedule(resetGate)
}}}
def followedByContactOpenedEventHandler(evt) {
    if (state.gateHasBeenOpened == true) {
        state.gateHasBeenOpened = false
        vacant()
        if (switches5) {
            switches5.off()
            }
        unschedule(resetGate)
        } else {
                if (state.backDoorHasBeenOpened == false) {
                    state.backDoorHasBeenOpened = true
                    runIn(30, resetBackDoor)
                    } else {
                            state.backDoorHasBeenOpened = false
                            unschedule(resetBackDoor)
}}}
def forceVacantIf() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (['occupied','occupiedon'].contains(areaState) && !entryMotionState.value.contains("active")) { 
          vacant()
}}
private getArea() {	
        return "aa_${app.id}" 
}
def heavyuse() {
def child = getChildDevice(getArea())
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('heavyuseon')
           } else {                
                   child.generateEvent('heavyuse')
                   }} else {
                            child.generateEvent('heavyuse')
                            }
    if(occupancyStatusChangesSubscribed) { 
       log.info "Re-Evaluation Caused By $app.label Changing To Heavy Use"
       mainAction() 
}}
def heavyuseCheck() {
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (entryMotionState.value.contains("active")) { 
        occupied()
        } else {
                vacant()
}}
def leftHome() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (['automationon'].contains(automationState)) {
          log.debug "$app.label was set to 'VACANT' because the mode changed to away or Heavy Use Was Disabled!"
          vacant()
          log.debug "switchesOffCountdown Sent"
          switchesOffCountdown()  
}}
def	modeEventHandler(evt) {
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    if (resetAutomation == true && resetAutomationMode && resetAutomationMode.contains(evt.value) && ['automationoff'].contains(automationState)) {
       log.info "$app.label's Automation Has Been Enabled Because Your Reset Mode Was ACTIVATED!"
       child.generateAutomationEvent('automationon') 
       }       
    if (awayModes && awayModes.contains(evt.value) && noAwayMode == true) {
        log.debug "$app.label Was Set To 'VACANT' Because Your Away Mode Was 'ACTIVATED'!"
        leftHome() 
}}    
def monitoredDoorOpenedEventHandler(evt) { 
    log.info "Re-Evaluated by A Monitored Door Opening"
    unschedule(engaged)
    unschedule(vacant)
    unschedule(donotdisturb)
    unschedule(forceVacantIf)
    unschedule(switches2Off)
    unschedule(dimLights)
    unschedule(heavyuseCheck)
    unschedule(checkOtherAreaAgain)
    mainAction() 
}
def monitoredDoorClosedEventHandler(evt) { 
    log.info "Re-Evaluated by A Monitored Door Closing"
    mainAction() 
}
def occupied() {
    state.occupiedTime = now()
    def child = getChildDevice(getArea())
        if (checkableLights) {
            def lightsState = checkableLights.currentState("switch")
            if (lightsState.value.contains("on")) {
                child.generateEvent('occupiedon')
                } else {                
                        child.generateEvent('occupied')
                        }} else {
                                 child.generateEvent('occupied')
                                 }
        if(occupancyStatusChangesSubscribed == true) { 
           log.info "Re-Evaluation Caused By $app.label Changing To Occupied"
           mainAction() 
           }
        if (instantHeavyuse == true && heavyuseControl == true) {
            if (state.occupiedTime < state.vacantTime + 2000) {
                log.info "Heavy Use Has Been Activated By The 'NEW INSTANT' Method!"
                heavyuse()
                }}
    state.occupiedCounter = state.occupiedCounter + 1
    log.info "Occupied Counter Is At $state.occupiedCounter"
    if (instantHeavyuse == false && heavyuseControl == true && minutesBeforeHeavyuseActivation) {
        log.info "The Occupied State Counter Will Reset In $minutesBeforeHeavyuseActivation Minutes"
        runIn(minutesBeforeHeavyuseActivation * 60, resetOccupiedCounter) 
        }
    if (state.occupiedCounter == 1) { 
        state.startTime = now() 
        }
    if (heavyuseControl == true && state.occupiedCounter == numberBeforeHeavyuseActivation) { 
        def threshold = 1000 * 60 * minutesBeforeHeavyuseActivation
        if (now() <= state.startTime + threshold) { 
            heavyuse()
            state.occupiedCounter = 0
            log.debug "heavy use mode was activated" 
            } else { 
                    state.occupiedCounter = 0 
                    }} else {
                             log.debug "startTime value is $state.startTime Milliseconds" 
                             }
    if (sendOccupiedNotification == true) {
        log.info "Occupied Notifications Is Active"
        String message = occupiedMessage
        if (location.contactBookEnabled && recipients) {
            log.debug "You Have Chosen To Send Notifications & Your Contact Book Is Enabled! Sending Message '$occupiedMessage'"
            sendNotificationToContacts(message, recipients) 
            } else { 
                    if (phone) { 
                        log.debug "You Have Chosen To Send Notifications But Your Contact Book Is Disabled! Sending '$occupiedMessage' SMS To Phone Number"
                        sendSms(phone, message) 
                        }                                                                                                        
}}}
def otherAreaOccupancyStatusEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           log.info "Vacant Force Check Performed by $otherArea Occupancy Changing To Vacant"
           forceVacantIf()
}}
def presenceAwayEventHandler(evt) { 
    forceVacantIf() 
} 
def resetBackDoor() {
    state.backDoorHasBeenOpened = false
}
def resetGate() {
    state.gateHasBeenOpened = false
}
def resetOccupiedCounter() {
    log.info "The Occupied Counter Has Been Reset Dut To Inactivity For Your Entire Time Count"
    state.occupiedCounter = 0
}
def spawnChildDevice(areaName) {
    app.updateLabel(app.label)
    if (!childCreated())
	     def child = addChildDevice("Baz2473", "Area Occupancy Status", getArea(), null, [name: getArea(), label: areaName, completedSetup: true])
}
def switchesOffCountdown() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (offRequired == true && ['automationon'].contains(automationState)) {
        log.info "switchesOff Will Be Started In $switchesOffCountdownInSeconds Seconds"
        runIn(switchesOffCountdownInSeconds, switches2Off)  
}}
def switches2OnEventHandler(evt) { 
    log.info "Re-Evaluated by Switches2 On"
    mainAction() 
}
def switches2OffEventHandler(evt) { 
    log.info "Re-Evaluated by Switches2 Off"
    mainAction() 
}
def switches2Off() {
    def child = getChildDevice(getArea())
    switches2.each {
    it.setLevel(0)   
}}
def switches3OnEventHandler(evt) { 
    log.info "Re-Evaluated by Switches3 On"
    mainAction() 
}
def switches3OffEventHandler(evt) { 
    log.info "Re-Evaluated by Switches3 Off"
    mainAction() 
}
def turnOffAtThisTime() {
    log.info "the time turn OFF test worked"
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    switchOffAtThisTime.each {
    def switchOffAtThisTimeState = switchOffAtThisTime.currentState("switch")  
    if (switchOffAtThisTimeState.value.contains("on") && ['automationon'].contains(automationState)) { 
        it.off()
        log.info "the time turn OFF test completed"
}}} 
def turnOnAtThisTime() {
    log.info "the time turn ON test worked"
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    switchOnAtThisTime.each {
    def switchOnAtThisTimeState = switchOnAtThisTime.currentState("switch")  
    if (!switchOnAtThisTimeState.value.contains("on") && ['automationon'].contains(automationState)) { 
         it.on()
         log.info "the time turn ON test completed"
}}}
def vacant() { 
    state.vacantTime = now()
    def child = getChildDevice(getArea())
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('vacanton')
            } else {                
                    child.generateEvent('vacant')
                    }} else {
                             child.generateEvent('vacant')
                             }
    if(occupancyStatusChangesSubscribed == true) {
       log.info "Re-Evaluation Caused By $app.label Changing To Vacant"
       mainAction() 
       }
    if (sendVacantNotification == true) {
        log.info "Vacant Notifications Is Active"
        String message = vacantMessage
        if (location.contactBookEnabled && recipients) {
            log.debug "You Have Chosen To Send Notifications & Your Contact Book Is Enabled! Sending Message '$vacantMessage'"
            sendNotificationToContacts(message, recipients) 
            } else { 
                    if (phone) { 
                        log.debug "You Have Chosen To Send Notifications But Your Contact Book Is Disabled! Sending '$vacantMessage' SMS To Phone Number"
                        sendSms(phone, message)                               
}}}}                         