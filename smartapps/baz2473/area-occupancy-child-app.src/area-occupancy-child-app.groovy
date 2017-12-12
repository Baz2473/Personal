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
     section("Select Your Method For Detecting Occupancy\nFor $app.label!") {
     if(!contactOrAccelerationActivated && !followedBy) {
     input "motionActivated", "bool", title: "Motion?", defaultValue: false, submitOnChange: true
     }
     if(!motionActivated && !followedBy) {
     input "contactOrAccelerationActivated", "bool", title: "Seperate Contact/Acceleration?", defaultValue: false, submitOnChange: true
     }
     if(!motionActivated && !contactOrAccelerationActivated) {
     input "followedBy", "bool", title: "Followed By?", defaultValue: false, submitOnChange: true
     }
     
} // end of Occupancy Detection Selection Method

if(motionActivated) {
    section("Select The Motion Sensors In '$app.label'") {
             input "entryMotionSensors", "capability.motionSensor", title: "Entry Sensors?", required: true, multiple: true, submitOnChange: true            	     
}
if (entryMotionSensors && !exitMotionSensors) {
    section("Manually Force Vacant State\nWith A Timer For $app.label's Area?") {
             input "noExitSensor", "bool", title: "No Exit Sensor For\n$app.label?", defaultValue: false, submitOnChange: true
}}
if (entryMotionSensors || entryMotionTimeout) {
    section("Select This If There Is A\nMonitored Door In An Adjacent Area To $app.label") {
             input "monitoredDoor2", "bool", title: "Adjacent Monitored Doors?", defaultValue: false, submitOnChange: true
}}
if (monitoredDoor2) {
    section("Select The Doors\nThat Are Monitored 'Off Of' '$app.label'?") {
             input "adjacentDoors", "capability.contactSensor", title: "Which Adjacent Doors?", multiple: true, required: true, submitOnChange: true
}}
if (entryMotionSensors && monitoredDoor2) {  
          section("Motion Sensors When The Door Is 'Open'\n'Outside' Of $app.label") {
                   input "exitMotionSensorsWhenDoorIsOpen", "capability.motionSensor", title: "$adjacentDoors OPEN Exit Sensors?", required: true, multiple: true, submitOnChange: true     
}}
if (entryMotionSensors && monitoredDoor2) {  
          section("Motion Sensors When The Door Is 'Closed'\n'Outside' Of $app.label") {
                   input "exitMotionSensorsWhenDoorIsClosed", "capability.motionSensor", title: "$adjacentDoors CLOSED Exit Sensors?", required: true, multiple: true, submitOnChange: true    
}}
if (entryMotionSensors && !doors2 && !monitoredDoor2 && !noExitSensor) {  
          section("Select The Motion Sensors In The Area's\nImmediately 'Outside' Of $app.label") {
                   input "exitMotionSensors", "capability.motionSensor", title: "Exit Sensors?", required: true, multiple: true, submitOnChange: true   
}}
if (entryMotionSensors) {
    section("Use The Status Change Of Another Area To Force A Check On $app.label's State?") {
             input "otherAreaCheck", "bool", title: "Other Area Occupancy Changes?", defaultValue: false, submitOnChange: true
             if (otherAreaCheck) {
                 input "otherArea", "capability.estimatedTimeOfArrival", title: "Subscribe To This Area's Occupancy Changes?", required: true, multiple: true, submitOnChange: true
}}}
if (entryMotionSensors) {
    section("Perform Another Vacancy Check If $app.label Is 'Occupied' And Motionless?") {
             input "anotherVacancyCheck", "bool", title: "Another Vacancy Check?", defaultValue: false, submitOnChange: true
             if (anotherVacancyCheck) {
                 input "anotherCheckIn", "number", title: "How Many Seconds 'After' Inactivity\n(Only Checks When $app.label is 'Occupied')", required: true, submitOnChange: true, range: "actualEntrySensorsTimeout..99999"
}}}
if (noExitSensor) {
    section("Use A Countdown Timer To Change This Rooms State?") {
             input "countdownTimer", "bool", title: "Use A Countdown Timer?", defaultValue: false, submitOnChange: true
             if (countdownTimer) { 
                 input "entryMotionTimeout", "number", title: "How Many Seconds After Inactivity?", required: false, multiple: false, defaultValue: null, range: "1..99999", submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Select If $app.label Has A Door To Monitor?") {
             input "monitoredDoor", "bool", title: "Monitor Doors?", defaultValue: false, submitOnChange: true
}}
if (monitoredDoor) {
    section("Select The Doors\nThat Enter / Exit '$app.label'?") {
             input "doors", "capability.contactSensor", title: "Doors?", multiple: true, required: true, submitOnChange: true
}}
if ((exitMotionSensors || entryMotionTimeout) && doors) {
    section("IMPORTANT!!!\nInput $app.label's 'ACTUAL' Motion Sensor's 'Timeout'") {
             input "actualEntrySensorsTimeout", "number", title: "How Many Seconds?",required: true, defaultValue: null, submitOnChange: true
             input "actionOnDoorOpening", "bool", title: "Turn ON Something When\n$doors Open?", defaultValue: false, submitOnChange: true
             if (actionOnDoorOpening) {
                 input "onlyIfAreaVacant", "bool", title: "Only IF $app.label Is Vacant", defaultValue: true, submitOnChnage: true
                 input "doorOpeningAction", "capability.switchLevel", title: "Turn On?", multiple: true, required: true, submitOnChange: true
                 input "setLevelAt", "number", title: "Set Level To? %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null 
                 if (setLevelAt) {  
                     input "onlyDuringCertainTimes", "bool", title: "Only During Certain Times?", defaultValus: false, submitOnChange: true
                     if (onlyDuringCertainTimes) {
                         input "onlyDuringSunriseAndSunsetChosen", "bool", title: "Only Between Sunrise & Sunset", defaultValue: false, submitOnChange: true
                         if (!onlyDuringSunriseAndSunsetChosen) {
                              input "fromTime", "time", title: "From?", required: true
                              input "toTime", "time", title: "Until?", required: true
                              }
                         input "anotherAction", "bool", title: "Another Time Schedule?", defaultValue: false, submitOnChange: true
                         if (anotherAction) {
                             input "onlyIfAreaVacant2", "bool", title: "Only IF $app.label Is Vacant", defaultValue: false, submitOnChnage: true
                             input "doorOpeningAction2", "capability.switchLevel", title: "Light To Turn On?", multiple: true, required: true, submitOnChange: true
                             input "setLevelAt2", "number", title: "Set Light Level To What? %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null 
                             input "onlyDuringSunriseAndSunsetChosen2", "bool", title: "Only Between Sunrise & Sunset", defaultValue: false, submitOnChange: true
                             if (!onlyDuringSunriseAndSunsetChosen) {
                                  input "fromTime2", "time", title: "From?", required: true
                                  input "toTime2", "time", title: "Until?", required: true
                                  }
                             input "offAfter", "number", title: "Turn Off After?", required: true, defaultValue: 90
                             
}}}}
             input "actionOnDoorClosing", "bool", title: "Turn OFF When\n$doors Closed?", defaultValue: false, submitOnChange: true
             if (actionOnDoorClosing) {
                 }

}} // end of Monitored Door Control Section

if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2) && !switches) {
    section("Do You Want 'Any' Lights\nTo Automatically Turn 'ON'?") {
             input "switchOnControl", "bool", title: "OCCUPIED 'ON' Control?", defaultValue: false, submitOnChange: true
}}
if (switchOnControl) {
    section("Switch On Lights At Different Levels\nDuring Different Modes?") {
             input "switchOnModeControl", "bool", title: "Different Levels\nDuring Different Modes?", defaultValue: false, submitOnChange: true
}}
if (!switchOnModeControl && switchOnControl) {
    section("Turn ON Which Dimmable Lights?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "dimmableSwitches1", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
             if (dimmableSwitches1) {          
                 input "setLevelTo", "number", title: "Set Level To %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null             
}}}
if (switchOnModeControl) {
    section("Turn ON Which Dimmable Lights?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "dimmableSwitches2", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
             if (dimmableSwitches2) {          
                 input "duringMode1", "mode", title: "During Mode 1", required: true, multiple: false, submitOnChange: true
                 input "setLevelTo1", "number", title: "Set Level To %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                    
                 }
             if (setLevelTo1 && dimmableSwitches2) {
                 input "duringMode2", "mode", title: "During Mode 2", required: true, multiple: false, submitOnChange: true
                 input "setLevelTo2", "number", title: "Set Level To %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                              
                 }
             if (setLevelTo2 && dimmableSwitches2) {
                 input "duringMode3", "mode", title: "During Mode 3", required: false, multiple: false, submitOnChange: true
                 input "setLevelTo3", "number", title: "Set Level To %", required: false, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                           
                 }
             if (setLevelTo3 && dimmableSwitches2) {
                 input "duringMode4", "mode", title: "During Mode 4", required: false, multiple: false, submitOnChange: true
                 input "setLevelTo4", "number", title: "Set Level To %", required: false, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null                             
                 } 
                 
}} // end of Auto ON Control Section

if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Do You Want Any Lights\nTo Automatically Turn 'OFF'?") {
             input "offRequired", "bool", title: "VACANT 'OFF' Control?", defaultValue: false, submitOnChange: true
}}
if (offRequired) {
    section("Only If A Different Chosen Areas Are 'Vacant'?") {
             input "otherAreaVacancyCheck", "bool", title: "Other Area Vacancy Check", defaultValue: false, submitOnChange: true
             if (otherAreaVacancyCheck) {
                 input "thisArea", "capability.estimatedTimeOfArrival", title: "What Area?", defaultValue: null, multiple: false, required: false, submitOnChange: true
                 if (offRequired && thisArea) {
                      input "andThisArea", "capability.estimatedTimeOfArrival",  title: "What Other Area?", defaultValue: null, multiple: false, required: false, submitOnChange: true
}}}}
if (offRequired) {
    section("Do You Want 'Any' Lights\nTo Instantly Turn 'OFF'!!\nWhen $app.label Changes To Vacant?") {
             input "instantOff", "bool", title: "Instant Off!!", defaultValue: false, submitOnChange: true
}}
if (instantOff) {
    section("Turn OFF Which Lights\nIMMEDIATELY When $app.label Changes to 'VACANT'") {
             input "switches3", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
}}
if (offRequired) {
    section("Do You Want Any Lights\nTo Turn 'OFF' With A Delay?\nAfter $app.label Changes To 'VACANT'") {
             input "delayedOff", "bool", title: "Delayed Off?", defaultValue: false, submitOnChange: true
}}
if (delayedOff) {
    section("Turn OFF Which Lights\nWith A Delay,\nAfter $app.label Changes to 'VACANT'") {
             input "switches2", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
             if (switches2) {
                 input "dimByLevel", "number", title: "Reduce Level By %\nBefore Turning Off!", required: false, multiple: false, range: "1..99", submitOnChange: true, defaultValue: null               
}}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2) && switches2 && delayedOff) {
    section("How Many Seconds 'After' $app.label Is 'VACANT'\nUntil You Want The Lights To Dim Down?") {
             input "dimDownTime", "number", title: "How Many Seconds?", required: true, defaultValue: null, submitOnChange: true
}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2) && switches2 && delayedOff) {
    section("How Many Seconds 'After'\n$app.label's Lights Have Dimmed Down,\nUntil You Want Them To Turn OFF?") {
             input "switchesOffCountdownInSeconds", "number", title: "How Many Seconds?", required: true, defaultValue: null, submitOnChange: true
             
}} // end of Auto OFF Control Section

if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Do You Want To Automatically Switch\n$app.label's Automation 'ON' If It Was Disabled\nWhen Activation Of Certain Modes Occur?") {
             input "resetAutomation", "bool", title: "Reset Automation On Mode Selection?", defaultValue: false, submitOnChange: true
             if (resetAutomation) {
                 input "resetAutomationMode", "mode", title: "Select Your Automation Reset Modes?", required: true, multiple: true, submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Do You Require Switching $app.label To 'VACANT'\nOn Activation Of Your Away Modes") {
             input "noAwayMode", "bool", title: "Auto Vacate When\nYour Away Modes Activate?", defaultValue: false, submitOnChange: true
             if (noAwayMode) {
                 input "awayModes", "mode", title: "Select Your Away Modes?", required: true, multiple: true, submitOnChange: true
}}}
if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Do You Require Switching $app.label To 'VACANT'\nIf Any Persons Presence Changes To Away?") {
             input "presence", "bool", title: "Auto Vacate On\nAny Presence Change?", defaultValue: false, submitOnChange: true
             if (presence) {
                 input "presenceSensors", "capability.presenceSensor", title: "Select Who? Leaving\nWill Activate 'VACANT'", required: true, multiple: true, submitOnChange: true

}}} // end of Resetting Section

if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Heavy Use Control") {
             input "heavyuseControl", "bool", title: "Heavy Use Control?", submitOnChange: true
}} 
if (heavyuseControl) {
    section("How Many Times Must $app.label Change To Occupied\nBefore Activating 'Heavy Use'?") {
             input "instantHeavyuse", "bool", title: "INSTANT Heavy Use?", required: false, defaultValue: false, submitOnChange: true
             if (!instantHeavyuse) {
             input "numberBeforeHeavyuseActivation", "number", title: "How Many Times?", required: true, submitOnChange: true
             input "minutesBeforeHeavyuseActivation", "number", title: "Within How Many Minutes?", required: true, submitOnChange: true
             input "heavyuseTimeout", "number", title: "Timeout Seconds?", required: true, submitOnChange: true
             
}}}       // end of Heavy Use Control Section       

section("Do Not Disturb Control") {
         if (entryMotionSensors && monitoredDoor && doors) {
             input "donotdisturbControl", "bool", title: "Do Not Disturb Control?", submitOnChange: true
             if (donotdisturbControl) {
                 paragraph "How Many Minutes Must $app.label\nStay Motionless While 'Engaged'\nBefore Activating 'Do Not Disturb'?"
                 input "dndCountdown", "number", title: "How Many Minutes?", required: true, submitOnChange: true
          }} else {
                  paragraph "Do Not Disturb Is Only Accessable In Conjunction With Entry Motion Sensors & A Monitored Door!"               

}} // end of Do Not Disturb Control Section

section("Subscriptions!") {
         input "subscriptionsSelected", "bool", title: "Override Subscriptions?", required: false, submitOnChange: true
         if (subscriptionsSelected) {
             if(exitMotionSensors) { input "exitMotionActiveSubscribed", "bool", title: "Exit Motion Active?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensors) { input "exitMotionInactiveSubscribed", "bool", title: "Exit Motion Inactive?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsOpen && monitoredDoor2) { input "exitMotionWhenDoorIsOpenActiveSubscribed", "bool", title: "Exit Motion When Door Is Open Active?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsOpen && monitoredDoor2) { input "exitMotionWhenDoorIsOpenInactiveSubscribed", "bool", title: "Exit Motion When Door Is Open Inactive?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsClosed && monitoredDoor2) { input "exitMotionWhenDoorIsClosedActiveSubscribed", "bool", title: "Exit Motion When Door Is Closed Active?", required: false, submitOnChange: true, defaultValue: true }
             if(exitMotionSensorsWhenDoorIsClosed && monitoredDoor2) { input "exitMotionWhenDoorIsClosedInactiveSubscribed", "bool", title: "Exit Motion When Door Is Closed Inactive?", required: false, submitOnChange: true, defaultValue: true }
             if(doors && monitoredDoor) { input "monitoredDoorsOpeningSubscribed", "bool", title: "Monitored Doors Opening?", required: false, submitOnChange: true, defaultValue: true }
             if(doors && monitoredDoor) { input "monitoredDoorsClosingSubscribed", "bool", title: "Monitored Doors Closing?", required: false, submitOnChange: true, defaultValue: true }
             if(adjacentDoors && monitoredDoor2) { input "adjacentMonitoredDoorOpeningSubscribed", "bool", title: "Adjacent Monitored Doors Opening?", required: false, submitOnChange: true, defaultValue: true }
             if(adjacentDoors && monitoredDoor2) { input "adjacentMonitoredDoorClosingSubscribed", "bool", title: "Adjacent Monitored Doors Closing?", required: false, submitOnChange: true, defaultValue: true }
             if(dimmableSwitches1 && !switchOnModeControl && switchOnControl) { input "onSwitchesAndLightsSubscribed1", "bool", title: "Lights Turning ON", required: false, submitOnChange: true, defaultValue: false }
             if(dimmableSwitches2 && switchOnModeControl && switchOnControl) { input "onSwitchesAndLightsSubscribed2", "bool", title: "Lights Turning ON", required: false, submitOnChange: true, defaultValue: false }
             if(switches2 && delayedOff && offRequired) { input "dimmingLightsSubscribed", "bool", title: "Lights Dimming Off", required: false, submitOnChange: true, defaultValue: false }
             if(switches3 && instantOff && offRequired) { input "instantOffSwitchesAndLightsSubscribed", "bool", title: "Lights Turning Off", required: false, submitOnChange: false }
             if(entryMotionSensors) { input "entryMotionActiveSubscribed", "bool", title: "Entry Motion Active", required: false, submitOnChange: true, defaultValue: true }
             if(entryMotionSensors) { input "entryMotionInactiveSubscribed", "bool", title: "Entry Motion Inactive", required: false, submitOnChange: true, defaultValue: true }
             input "occupancyStatusChangesSubscribed","bool", title: "$app.label's Occupancy Status Changes?", required: false, submitOnChange: true, defaultValue: true
             if(subscriptionsSelected) {
                if(otherArea && otherAreaCheck) {
                   input "openOtherArea", "bool", title: "Open Other Area Subscriptions", defaultValue: false, submitOnChange: true
                   if(openOtherArea) {
                      input "otherAreaSubscribedVacant", "bool", title: "Force A Vacancy Check When $otherArea Changes To VACANT!", required: false, submitOnChange: true, defaultValue: false
                      input "otherAreaSubscribedOccupied", "bool", title: "Force A Vacancy Check When $otherArea Changes To OCCUPIED!", required: false, submitOnChange: true, defaultValue: false 
                      input "otherAreaSubscribedEngaged", "bool", title: "Force A Vacancy Check When $otherArea Changes To ENGAGED", required: false, submitOnChange: true, defaultValue: false
                      input "otherAreaSubscribedChecking", "bool", title: "Force A Vacancy Check When $otherArea Changes To CHECKING!", required: false, submitOnChange: true, defaultValue: false
                      input "otherAreaSubscribedHeavyuse", "bool", title: "Force A Vacancy Check When $otherArea Changes To HEAVY USE!", required: false, submitOnChange: true, defaultValue: false 
                      input "otherAreaSubscribedDonotdisturb", "bool", title: "Force A Vacancy Check When $otherArea Changes To DO NOT DISTURB", required: false, submitOnChange: true, defaultValue: false                 
                   }} else {
                            paragraph "Checking the status of other areas is only possible if you have selected the area"
                            }}
    
}} // end of Subscriptions Selection Section

} // end of Section After Occupancy Selection Section

if(contactOrAccelerationActivated) {
   if (!entryAccelerationChosen) {
    section("Do You Wish To Activate 'OCCUPIED'\nBy Contact Sensor(s) Opening?") {
            input "entryContactChosen", "bool", title: "Contact?", defaultValue: false, submitOnChange: true
            if (entryContactChosen) {
                    input "entryContactSensors", "capability.contactSensor", title: "Which Contact Sensor(s)", required: true, multiple: true, submitOnChange: true               	     
}}}
if (!entryContactChosen) {
    section("Do You Wish To Activate 'OCCUPIED'\nBy Acceleration Sensor(s) Being Active?") {
             input "entryAccelerationChosen", "bool", title: "Acceleration?", defaultValue: false, submitOnChange: true
             if (entryAccelerationChosen) {   
                 input "entryAccelerationSensors", "capability.accelerationSensor", title: "Which Acceleration Sensor(s)", required: true, multiple: true, submitOnChange: true                 	     
}}}
if (!exitAccelerationChosen) {
    section("Do You Wish To Activate 'VACANT'\nBy Contact Sensor(s) Opening?") {
            input "exitContactChosen", "bool", title: "Contact?", defaultValue: false, submitOnChange: true
            if (exitContactChosen) {
                input "exitContactSensors", "capability.contactSensor", title: "Which Contact Sensor(s)", required: true, multiple: true, submitOnChange: true
                if (exitContactSensors) {
                    input "onlyIfInactive2", "bool", title: "Only If\nSelected Motion Sensors\nAre 'Inactive'?\n(Optional)", defaultValue: false, submitOnChange: true
                    if (onlyIfInactive2) {
                        input "onlyIfThisSensor2", "capability.motionSensor", title: "These Motion Sensor(s)\nMust Be Inactive!", required: true, multiple: true, submitOnChange: true                                       
}}}}}
if (!exitContactChosen) {
    section("Do You Wish To Activate 'VACANT'\nBy Acceleration Sensor(s) Being Active?") {
             input "exitAccelerationChosen", "bool", title: "Acceleration?", defaultValue: false, submitOnChange: true
             if (exitAccelerationChosen) {
                    input "exitAccelerationSensors", "capability.accelerationSensor", title: "Which Acceleration Sensor(s)", required: true, multiple: true, submitOnChange: true
                 if (exitAccelerationSensors) {
                     input "onlyIfInactive2", "bool", title: "Only If\nSelected Motion Sensors\nAre 'Inactive'?\n(Optional)", defaultValue: false, submitOnChange: true
                     if (onlyIfInactive2) { 
                         input "onlyIfThisSensor2", "capability.motionSensor", title: "These Motion Sensor(s)?\nMust Be Inactive!", required: true, multiple: true, submitOnChange: true                                                 
}}}}}}
if (followedBy) {
    section("Please Select The 2 Actioins Required To Activate Occupied & Vacant!") {
             input "firstAction", "capability.contactSensor", title: "Which Contact Sensor?", required: true, multiple: false, submitOnChange: true
             input "secondAction", "capability.accelerationSensor", title: "Which Acceleration Sensor?", required: true, multiple: false, submitOnChange: true
}}
if (followedBy && !switches4) {
    section("Do You Want Any Light(s)\nTo Automatically Turn 'ON'?") {
             input "switch4OnControl", "bool", title: "Auto 'ON' Control?\n(Optional)", defaultValue: false, submitOnChange: true
}}
if (followedBy && switch4OnControl) {
    section("Turn ON Which Switch(s)?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "switches4", "capability.switch", title: "Switch(s)?\n(Required)", required: true, multiple: true, submitOnChange: true            
}}
if (switches4) {
    section("Do You Want Any Switch(s)\nTo Turn 'OFF'!!\nWhen $app.label Changes To Vacant?") {
             input "instant4Off", "bool", title: "Instant Off!!", defaultValue: false, submitOnChange: true
}}
if (instant4Off) {
    section("Turn OFF Which Switch(s)\nWhen $app.label Changes to 'VACANT'") {
             input "switches5", "capability.switch", title: "Which Switch(s)\n(Required)", required: true, multiple: true, submitOnChange: true
}}
if (motionActivated || contactOrAccelerationActivated || followedBy) {
    section("Notifications?") {
             input "sendNotifications", "bool", title: "Send Notifications?", defaultValue: false, submitOnChange: true
             if (sendNotifications) {
                 if (exitMotionSensors || exitContactSensors || exitAccelerationSensors || exitContactFBExitContactSensors || exitContactFBExitAccelerationSensors ||
                     exitAccelerationFBExitContactSensors || exitAccelerationFBExitAccelerationSensors || followedBy) {
                     input "sendVacantNotification", "bool", title: "Vacant?", required: false, submitOnChange: true
                     if (sendVacantNotification) {
                         input "vacantMessage", "text", title: "Message?", required: true
                         input("recipients", "contact", title: "Send To:") {
                         input "phone", "phone", title: "Warn with text message (optional)",
                         description: "Phone Number", required: false	
                         }}} else {
                                    paragraph "Sending an 'Vacant' notification is only available if you select a source for detecting vacancy!" 
                                    }
                 if (entryMotionSensors || entryContactSensors || entryAccelerationSensors || entryContactFBEntryContactSensors || entryContactFBEntryAccelerationSensors ||
                     entryAccelerationFBEntryContactSensors || entryAccelerationFBEntryAccelerationSensors || followedBy) {               
                     input "sendOccupiedNotification", "bool", title: "Occupied?", required: false, submitOnChange: true
                     if (sendOccupiedNotification) {
		                 input "occupiedMessage", "text", title: "Message?", required: true
                         input("recipients", "contact", title: "Send To:") {
                         input "phone", "phone", title: "Warn with text message (optional)",
                         description: "Phone Number", required: false	
                         }}} else {
                                    paragraph "Sending an 'Occupied' notification is only available if you select a source for detecting occupancy!"
                                    }
          
  } // end of send notification (true) section
}} // end of all of send notifications section! 
  
section("Turn Something 'ON' or 'OFF' In $app.label\nAt A Certain Time!") {
         input "timedControl", "bool", title: "Perform Timed Actions?", required: false, defualtValue: false, submitOnChange: true
         if (timedControl) {
             input "timedTurnOnControl", "bool", title: "Turn ON?", required: false, defualtValue: false, submitOnChange: true
             if (timedTurnOnControl && timedControl) {   
                 input "switchToTurnOnAtThisTime", "capability.switch", title: "Switchs?", required: true, multiple: true, submitOnChange: true
                 if (!onAtSunsetChosen && !onAtThisTime) {
                      input "onAtSunriseChosen", "bool", title: "At Sunrise?", required: false, multiple: false, submitOnChange: true
                      }
                 if (!onAtSunriseChosen && !onAtThisTime) {     
                      input "onAtSunsetChosen", "bool", title: "At Sunset?", required: false, multiple: false, submitOnChange: true
                      }
                 if (!onAtSunriseChosen && !onAtSunsetChosen) {
                      input "onAtThisTime", "time", title: "Time?", required: true, submitOnChange: true
                      }}
             input "timedTurnOffControl", "bool", title: "Turn OFF?", required: false, defualtValue: false, submitOnChange: true
             if (timedTurnOffControl && timedControl) {
                 input "switchToTurnOffAtThisTime", "capability.switch", title: "Switchs?", required: true, multiple: true, submitOnChange: true
                 if (!offAtSunriseChosen && !offAtThisTime) {
                      input "offAtSunsetChosen", "bool", title: "At Sunset?", required: false, multiple: false, submitOnChange: true
                      }
                 if (!offAtSunsetChosen && !offAtThisTime) {
                      input "offAtSunriseChosen", "bool", title: "At Sunrise?", required: false, multiple: false, submitOnChange: true
                      }
                 if (!offAtSunsetChosen && !offAtSunriseChosen) {
                      input "offAtThisTime", "time", title: "Time?", required: true, submitOnChange: true 
                      }}
 } // end of Timed Action (true) section
} // end of Timed Control section

section("Select ALL Of The Lights That Are In $app.label?") {
             input "checkableLights", "capability.switch", title: "Lights?", required: true, multiple: true, submitOnChange: true
             }
             
}} // end of Programatic Setup Section

//---------------------------------------------------------------------------------------------------------------------------------------------------------

def installed() {}

def updated() {
	unsubscribe()
	initialize()
    
    state.occupiedCounter = 0
    state.previousState = 'occupiedon'
     
if (!childCreated()) {
     spawnChildDevice(app.label) 
     }        
    if (adjacentDoors && monitoredDoor2 && adjacentMonitoredDoorOpeningSubscribed) { 
        subscribe(adjacentDoors, "contact.open", adjacentMonitoredDoorOpeningEventHandler) 
        }
    if (adjacentDoors && monitoredDoor2 && adjacentMonitoredDoorClosingSubscribed) { 
        subscribe(adjacentDoors, "contact.closed", adjacentMonitoredDoorClosingEventHandler) 
        }
    if (awayModes && noAwayMode || resetAutomationMode && resetAutomation) { 
        subscribe(location, modeEventHandler) 
        }
    if (checkableLights) {
        subscribe(checkableLights, "switch.on", checkableLightsSwitchedOnEventHandler)
        subscribe(checkableLights, "switch.off", checkableLightsSwitchedOffEventHandler)
        }
    if (dimmableSwitches1 && switchOnControl && switchOnModeControl && onSwitchesAndLightsSubscribed1) {
        subscribe(dimmableSwitches1, "switch.on", dimmableSwitches1OnEventHandler)////
        subscribe(dimmableSwitches1, "switch.off", dimmableSwitches1OffEventHandler)////
        }          
    if (dimmableSwitches2 && switchOnControl && switchOnModeControl && onSwitchesAndLightsSubscribed2) {
        subscribe(dimmableSwitches2, "switch.on", dimmableSwitches2OnEventHandler)////
        subscribe(dimmableSwitches2, "switch.off", dimmableSwitches2OffEventHandler)////
        }   
    if (doors && monitoredDoor && monitoredDoorsOpeningSubscribed) { 
        subscribe(doors, "contact.open", monitoredDoorOpenedEventHandler) 
        }
    if (doors && monitoredDoor && monitoredDoorsClosingSubscribed) {
        subscribe(doors, "contact.closed", monitoredDoorClosedEventHandler) 
        }  
    if (entryContactSensors && contactOrAccelerationActivated) {
        subscribe(entryContactSensors, "contact.open", entryContactOpenedEventHandler)
        }
    if (entryAccelerationSensors && contactOrAccelerationActivated) {
        subscribe(entryAccelerationSensors, "acceleration.active", entryContactOpenedEventHandler)
        }
    if (exitContactSensors && contactOrAccelerationActivated) {
        subscribe(exitContactSensors, "contact.open", exitContactOpenedEventHandler)
        }
    if (exitAccelerationSensors && contactOrAccelerationActivated) {
        subscribe(exitAccelerationSensors, "acceleration.active", exitContactOpenedEventHandler)
        }
    if (entryMotionSensors && entryMotionActiveSubscribed)	{ 
        subscribe(entryMotionSensors, "motion.active", entryMotionActiveEventHandler)
        }
    if (entryMotionSensors && entryMotionInactiveSubscribed)	{ 
        subscribe(entryMotionSensors, "motion.inactive", entryMotionInactiveEventHandler) 
        }
    if (exitMotionSensors && exitMotionActiveSubscribed) {
        subscribe(exitMotionSensors, "motion.active", exitMotionActiveEventHandler)
        }
    if (exitMotionSensors && exitMotionInactiveSubscribed) { 
        subscribe(exitMotionSensors, "motion.inactive", exitMotionInactiveEventHandler)  
        }
    if (exitMotionSensorsWhenDoorIsOpen && monitoredDoor2 && exitMotionWhenDoorIsOpenActiveSubscribed) {
        subscribe(exitMotionSensorsWhenDoorIsOpen, "motion.active", exitMotionSensorsWhenDoorIsOpenActiveEventHandler)
        }
    if (exitMotionSensorsWhenDoorIsOpen && monitoredDoor2 && exitMotionWhenDoorIsOpenInactiveSubscribed) {
        subscribe(exitMotionSensorsWhenDoorIsOpen, "motion.inactive", exitMotionSensorsWhenDoorIsOpenInactiveEventHandler)
        }
    if (exitMotionSensorsWhenDoorIsClosed && monitoredDoor2 && exitMotionWhenDoorIsClosedActiveSubscribed) {
        subscribe(exitMotionSensorsWhenDoorIsClosed, "motion.active", exitMotionSensorsWhenDoorIsClosedActiveEventHandler)
        }
    if (exitMotionSensorsWhenDoorIsClosed && monitoredDoor2 && exitMotionWhenDoorIsClosedInactiveSubscribed) {
        subscribe(exitMotionSensorsWhenDoorIsClosed, "motion.inactive", exitMotionSensorsWhenDoorIsClosedInactiveEventHandler)
        }
    if (followedBy && firstAction) {
        subscribe(firstAction, "contact.open", followedByContactOpenedEventHandler)
        state.backDoorHasBeenOpened = false
        }    
    if (followedBy && secondAction) {
        subscribe(secondAction, "acceleration.active", followedByAccelerationActiveEventHandler)
        state.gateHasBeenOpened = false
        }        
    if (otherArea && otherAreaCheck && otherAreaSubscribedVacant) {
        subscribe(otherArea, "occupancyStatus.vacant", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedOccupied) {
        subscribe(otherArea, "occupancyStatus.occupied", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedEngaged) {
        subscribe(otherArea, "occupancyStatus.engaged", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedChecking) {
        subscribe(otherArea, "occupancyStatus.checking", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedHeavyuse) {
        subscribe(otherArea, "occupancyStatus.heavyuse", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedDonotdisturb) {
        subscribe(otherArea, "occupancyStatus.donotdisturb", otherAreaOccupancyStatusEventHandler)
        }
    if (presenceSensors && presence) { 
        subscribe(presenceSensors, "presence.not present", presenceAwayEventHandler) 
        }
    if (switches2 && delayedOff && dimmingLightsSubscribed) { 
        subscribe(switches2, "switch.on", switches2OnEventHandler)////
        subscribe(switches2, "switch.off", switches2OffEventHandler)////
        }
    if (switches3 && instantOff && instantOffSwitchesAndLightsSubscribed) { 
        subscribe(switches3, "switch.on", switches3OnEventHandler)////
        subscribe(switches3, "switch.off", switches3OffEventHandler)////
}}
//---------------------------------------------------------------------------------------------------------------------------------------------------------
def	initialize() { 

if (onAtThisTime && timedTurnOnControl && timedControl) {
    schedule(onAtThisTime, turnOnAtThisTime) 
    }
if (offAtThisTime && timedTurnOffControl && timedControl) {
    schedule(offAtThisTime, turnOffAtThisTime) 
    }
if (onAtSunriseChosen || offAtSunriseChosen) {
    subscribe(location, "sunrise", sunriseHandler)
    }
if (onAtSunsetChosen || offAtSunsetChosen) {
    subscribe(location, "sunset", sunsetHandler)
    }
//////////////////////////////////////////
if (onlyDuringSunriseAndSunsetChosen || onlyDuringSunriseAndSunsetChosen2) {
    def sunriseAndSunsetTimes = getSunriseAndSunset()
    }
//////////////////////////////////////////
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
        if(dimmableSwitches1 && switchOnControl && !switchOnModeControl && ['automationon'].contains(automationState)) {
           dimmableSwitches1.each {
           def currentLevel = it.currentValue("level")
           if (currentLevel < setLevelTo) { 
               it.setLevel(setLevelTo)
               }}}
        if(dimmableSwitches2 && switchOnControl && switchOnModeControl && ['automationon'].contains(automationState)) {
           def currentMode = location.currentMode
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
           }} 
               if (doors) {                      
                   def doorsState = doors.currentState("contact") 
                   if (!doorsState.value.contains("open") && ['donotdisturb','donotdisturbon'].contains(areaState)) {
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
           if (noExitSensor && ['heavyuseon'].contains(areaState)) { 
               state.previousState = 'heavyuseon'
               runIn(entryMotionTimeout, vacant, [overwrite: false])
               }
           if (noExitSensor && ['occupiedon',].contains(areaState)) { 
               state.previousState = 'occupiedon'
               runIn(entryMotionTimeout, vacant, [overwrite: false])
               }     
           if (noExitSensor && ['occupied','heavyuse'].contains(areaState)) {                         
               runIn(entryMotionTimeout, vacant, [overwrite: false])
               }                           
           if (donotdisturbControl && ['engaged','engagedon'].contains(areaState)) {                             
               runIn(dndCountdown * 60, donotdisturb, [overwrite: false])
               }
           if (exitMotionSensors && ['heavyuseon'].contains(areaState) && !adjacentDoors) {
               def exitMotionState = exitMotionSensors.currentState("motion")
               if (exitMotionState.value.contains("active")) { 
                   state.previousState = 'heavyuseon'
                   vacant()
                   }} 
           if (exitMotionSensors && ['occupiedon'].contains(areaState) && !adjacentDoors) {
               def exitMotionState = exitMotionSensors.currentState("motion")
               if (exitMotionState.value.contains("active")) { 
                   state.previousState = 'occupiedon'
                   vacant()
                   }}
           if (exitMotionSensors && ['occupied','heavyuse'].contains(areaState) && !adjacentDoors) {
               def exitMotionState = exitMotionSensors.currentState("motion")
               if (exitMotionState.value.contains("active")) { 
                   vacant()
                   }} 
           if (adjacentDoors && ['heavyuseon'].contains(areaState) && !exitMotionSensors) { 
               state.previousState = 'heavyuseon'
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
           if (adjacentDoors && ['occupiedon'].contains(areaState) && !exitMotionSensors) { 
               state.previousState = 'occupiedon'
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
           if (adjacentDoors && ['occupied','heavyuse'].contains(areaState) && !exitMotionSensors) {         
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
                         if (anotherVacancyCheck && anotherCheckIn && ['heavyuseon'].contains(areaState)) {
                             state.previousState = 'heavyuseon'
                             runIn(anotherCheckIn, forceVacantIf, [overwrite: false])
                             }
                         if (anotherVacancyCheck && anotherCheckIn && ['occupiedon'].contains(areaState)) {
                             state.previousState = 'occupiedon'
                             runIn(anotherCheckIn, forceVacantIf, [overwrite: false])
                             }
                         if (anotherVacancyCheck && anotherCheckIn && ['occupied','heavyuse'].contains(areaState)) {
                             runIn(anotherCheckIn, forceVacantIf, [overwrite: false])
                             }
                         if (switches3 && instantOff && offRequired) { 
                             def switches3State = switches3.currentState("switch")  
                             if (switches3State.value.contains("on") && ['vacanton','heavyuseon'].contains(areaState) && ['automationon'].contains(automationState)) { 
                                 if (thisArea && !andThisArea) { 
                                     def thisAreaState = thisArea.currentState("occupancyStatus")
                                     if (thisAreaState.value.contains("vacant")) {
                                         switches3.off()
                                         } else { 
                                                 log.info "Doing Nothing Because Your Other Area Is Still Occupied"                                                                                    
                                                 }} else {
                                                          if (thisArea && andThisArea) { 
                                                              def thisAreaState = thisArea.currentState("occupancyStatus")
                                                              def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                                              if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                                                  switches3.off()     
                                                                  } else {
                                                                          log.info "Doing Nothing Because 1 Of Your Other Areas Are Still Occupied"                                             
                                                                          }} else {
                                                                                   switches3.off()
                                                                                   }}}}
                        if (switches2 && delayedOff && offRequired) { 
                            def switches2State = switches2.currentState("switch")  
                            if (switches2State.value.contains("on") && ["vacanton"].contains(areaState) && ['automationon'].contains(automationState)) { 
                                if (thisArea && !andThisArea) { 
                                    def thisAreaState = thisArea.currentState("occupancyStatus")
                                    if (thisAreaState.value.contains("vacant")) {
                                        log.debug "The Previous State Was $state.previousState"
                                        if (state.previousState == 'occupiedon') {
                                            log.debug "The Lights Will Dim Down In $dimDownTime Seconds"
                                            runIn(dimDownTime, dimLights)
                                            }
                                        if (state.previousState == 'heavyuseon') {
                                            log.debug "The Lights Will Dim Down In Double $dimDownTime Seconds Because Heavy Use Was Active"
                                            runIn(dimDownTime * 2, dimLights)
                                            }
                                        } else {
                                                log.info "Doing Nothing Because Your Other Area Is Still Occupied"                           
                                                }} else {
                                                         if (thisArea && andThisArea) { 
                                                             def thisAreaState = thisArea.currentState("occupancyStatus")
                                                             def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                                             if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                                             log.debug "The Previous State Was $state.previousState"
                                                                 if (state.previousState == 'occupiedon') { 
                                                                     log.debug "The Lights Will Dim Down In $dimDownTime Seconds"
                                                                     runIn(dimDownTime, dimLights)   
                                                                     }
                                                                 if (state.previousState == 'heavyuseon') {
                                                                     log.debug "The Lights Will Dim Down In Double $dimDownTime Seconds Because Heavy Use Was Active"
                                                                     runIn(dimDownTime * 2, dimLights)
                                                                     }
                                                             } else {
                                                                     log.info "Doing Nothing Because 1 Of Your Other Areas Are Still Occupied"                                                                                                                
                                                                     }} else {
                                                                              log.debug "The Previous State Was $state.previousState"
                                                                              if (state.previousState == 'occupiedon') { 
                                                                                  log.debug "The Lights Will Dim Down In $dimDownTime Seconds"
                                                                                  runIn(dimDownTime, dimLights)   
                                                                                  }
                                                                              if (state.previousState == 'heavyuseon') {
                                                                                  log.debug "The Lights Will Dim Down In Double $dimDownTime Seconds Because Heavy Use Was Active"
                                                                                  runIn(dimDownTime * 2, dimLights)
                                                                                  }
                                                                               // runIn(dimDownTime, dimLights)
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
          if(occupancyStatusChangesSubscribed) { 
             log.info "Re-Evaluation Caused By $app.label Changing To VacantOn"
             mainAction() 
          }} else {
                  if (['occupied'].contains(areaState)) { 
                        child.generateEvent('occupiedon')
                        if(occupancyStatusChangesSubscribed) { 
                           log.info "Re-Evaluation Caused By $app.label Changing To OccupiedOn"
                           mainAction() 
                        }} else {
                                if (['engaged'].contains(areaState)) { 
                                      child.generateEvent('engagedon')
                                      if(occupancyStatusChangesSubscribed) { 
      									 log.info "Re-Evaluation Caused By $app.label Changing To EngagedOn"
      									 mainAction() 
                                      }} else {
                                              if (['checking'].contains(areaState)) { 
                                                    child.generateEvent('checkingon')
                                                    if(occupancyStatusChangesSubscribed) { 
                                                       log.info "Re-Evaluation Caused By $app.label Changing To CheckingOn"
     												   mainAction() 
                                                    }} else {
                                                            if (['heavyuse'].contains(areaState)) {   
                                                                  child.generateEvent('heavyuseon')
                                                                  if(occupancyStatusChangesSubscribed) { 
                                                                     log.info "Re-Evaluation Caused By $app.label Changing To HeavyuseOn"
                                                                     mainAction() 
                                                                  }} else {
                                                                          if (['donotdisturb'].contains(areaState)) { 
                                                                                child.generateEvent('donotdisturbon')
                                                                                if(occupancyStatusChangesSubscribed) { 
                                                                                   log.info "Re-Evaluation Caused By $app.label Changing To DonotdisturbON"
                                                                                   mainAction() 
                                                                 
                                                    
}}}}}}}}
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
def areaState = child.getAreaState()
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
    if (offRequired && ['automationon'].contains(automationState) && !['heavyuse','heavyuseon'].contains(areaState)) {
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
        if (!switchOnModeControl) {
            if (dimmableSwitches1 && switchOnControl && ['automationon'].contains(automationState)) {      
                log.debug "The Selected Switch(es) Have Either Been Turned 'ON' Or Had Their Respective Level(s) & Color(s) Set"
                dimmableSwitches1.each {
                def currentLevel = it.currentValue("level")
                if (currentLevel < setLevelTo) {  
                    it.setLevel(setLevelTo)
}}}}}
private dimmableSwitches2On() {
        def child = getChildDevice(getArea())
        def automationState = child.getAutomationState()
        if (switchOnModeControl) {
           def currentMode = location.currentMode
           if (dimmableSwitches2 && switchOnControl && ['automationon'].contains(automationState)) {      
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
def areaState = child.getAreaState()
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
def doorOpeningAction2Off() {
    doorOpeningAction2.off
}
def engaged() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
        if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('engagedon')
           } else {                
                   child.generateEvent('engaged')
                   }} else {
                            child.generateEvent('engaged')
                            }
        if(occupancyStatusChangesSubscribed) { 
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
     if (onlyIfThisSensor2 && onlyIfInactive2) {
         def cMotionState = onlyIfThisSensor2.currentState("motion")
         if (!cMotionState.value.contains("active")) {
              log.debug "An Exit Contact Was Opened & The Motion Requirement Was Met"
              def child = getChildDevice(getArea())
              def areaState = child.getAreaState()
              if (['occupied','occupiedon'].contains(areaState)) {
                    log.debug "$app.label Was 'OCCUPIED', So 'VACANT' State Has Been Set!"
                    vacant()                                                                    
}}} else {
          if (!onlyIfInactive2) {
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
def areaState = child.getAreaState() 
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
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (resetAutomation && resetAutomationMode && resetAutomationMode.contains(evt.value) && ['automationoff'].contains(automationState)) {
       log.info "$app.label's Automation Has Been Enabled Because Your Reset Mode Was ACTIVATED!"
       child.generateAutomationEvent('automationon') 
       }       
    if (awayModes && awayModes.contains(evt.value) && noAwayMode) {
        log.debug "$app.label Was Set To 'VACANT' Because Your Away Mode Was 'ACTIVATED'!"
        leftHome() 
}}    
def monitoredDoorOpenedEventHandler(evt) { 
    unschedule(engaged)
    unschedule(vacant)
    unschedule(donotdisturb)
    unschedule(forceVacantIf)
    unschedule(switches2Off)
    unschedule(dimLights)
    unschedule(heavyuseCheck)
    unschedule(checkOtherAreaAgain)
    if (actionOnDoorOpening) {
        def child = getChildDevice(getArea())
        def areaState = child.getAreaState()
        if (onlyDuringCertainTimes) {
            def between = timeOfDayIsBetween(fromTime, toTime, new Date(), location.timeZone)
            def between2 = timeOfDayIsBetween(fromTime2, toTime2, new Date(), location.timeZone)
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
              if (onlyDuringSunriseAndSunsetChosen) {
                    def sunriseTime = {sunriseAndSunsetTimes.sunrise}
                    log.debug "$sunriseTime"
                    def sunsetTime = {sunriseAndSunsetTimes.sunset}
                    log.debug "$sunsetTime"
                    def betweenSrAndSs = timeOfDayIsBetween(sunriseTime, sunsetTime, new Date(), location.timeZone)
                    if (betweenSrAndSs) {
                        if (onlyIfAreaVacant) {
                            if (['vacant'].contains(areaState)) {
                                  log.debug "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                                  doorOpeningAction.each {
                                  it.on()
                                  it.setLevel(setLevelAt)
                                  log.info "Re-Evaluated by A Monitored Door Opening"
                                  mainAction() 
                                  }} else {}
                        }} else {
                                doorOpeningAction.each {
                                it.on()
                                it.setLevel(setLevelAt)
                                log.info "Re-Evaluated by A Monitored Door Opening"
                                mainAction() 
                                }}}
       
                if (onlyDuringSunriseAndSunsetChosen2) {
                    def sunriseTime2 = {sunriseAndSunsetTimes.sunrise}
                    log.debug "$sunriseTime2"
                    def sunsetTime2 = {sunriseAndSunsetTimes.sunset}
                    log.debug "$sunsetTime2"
                    def betweenSrAndSs2 = timeOfDayIsBetween(sunriseTime2, sunsetTime2, new Date(), location.timeZone)
                        if (betweenSrAndSs2) {
                            if (onlyIfAreaVacant2) {
                                if (['vacant'].contains(areaState)) {
                                      log.debug "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                                      doorOpeningAction.each {
                                      it.on()
                                      it.setLevel(setLevelAt2)
                                      log.info "Re-Evaluated by A Monitored Door Opening"
                                      mainAction() 
                                      }} else {}
                        } else {
                                doorOpeningAction.each {
                                it.on()
                                it.setLevel(setLevelAt2)
                                log.info "Re-Evaluated by A Monitored Door Opening"
                                mainAction() 
                                }}}} //*////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (between) {
                if (onlyIfAreaVacant) {
                    if (['vacant'].contains(areaState)) {
                          log.debug "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                          doorOpeningAction.each {
                          it.on()
                          it.setLevel(setLevelAt)
                          log.info "Re-Evaluated by A Monitored Door Opening"
                          mainAction() 
                          }} else {}
                } else {
                        doorOpeningAction.each {
                        it.on()
                        it.setLevel(setLevelAt)
                        log.info "Re-Evaluated by A Monitored Door Opening"
                        mainAction() 
                        }}}
            if (between2) {
                if (onlyIfAreaVacant2) {
                    if (['vacant'].contains(areaState)) {
                    log.debug "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                    doorOpeningAction2.each {
                    it.setLevel(setLevelAt2)
                    log.info "Re-Evaluated by A Monitored Door Opening"
                    } runIn(offAfter, doorOpeningAction2Off)
                    } else {}
            } else {
                    doorOpeningAction2.each {
                    it.setLevel(setLevelAt2)
                    log.info "Re-Evaluated by A Monitored Door Opening"
                    runIn(offAfter, doorOpeningAction2Off)
                    }}}
      } else {
              doorOpeningAction.each {
              it.on()
              it.setLevel(setLevelAt)
              log.info "Re-Evaluated by A Monitored Door Opening"
              mainAction() 
              }}
 } else {                                
         log.info "Re-Evaluated by A Monitored Door Opening"
         mainAction() 
}}
def monitoredDoorClosedEventHandler(evt) { 
    log.info "Re-Evaluated by A Monitored Door Closing"
    if (actionOnDoorClosing) {
        def doorsState = doors.currentState("contact")
        if (doorsState.value.contains("open")) {
            log.info "doing NOTHING because A Door Is Still Open"
            } else {
                    log.debug "The Light Was Turned OFF Because The Door Was Closed"
                    doorOpeningAction.each {
                    it.setLevel(0)
                    log.info "Re-Evaluated by A Monitored Door Closing"
                    mainAction() 
                    }}} else {
                              mainAction() 
}}
def occupied() {
    state.occupiedTime = now()
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
        if (checkableLights) {
            def lightsState = checkableLights.currentState("switch")
            if (lightsState.value.contains("on")) {
                child.generateEvent('occupiedon')
                } else {                
                        child.generateEvent('occupied')
                        }} else {
                                 child.generateEvent('occupied')
                                 }
        if(occupancyStatusChangesSubscribed) { 
           log.info "Re-Evaluation Caused By $app.label Changing To Occupied"
           mainAction() 
           }
        if (instantHeavyuse && heavyuseControl) {
            if (state.occupiedTime < state.vacantTime + 2000) {
                log.info "Heavy Use Has Been Activated By The 'NEW INSTANT' Method!"
                heavyuse()
                }}
    state.occupiedCounter = state.occupiedCounter + 1
    log.info "Occupied Counter Is At $state.occupiedCounter"
    if (!instantHeavyuse && heavyuseControl && minutesBeforeHeavyuseActivation) {
        log.info "The Occupied State Counter Will Reset In $minutesBeforeHeavyuseActivation Minutes"
        runIn(minutesBeforeHeavyuseActivation * 60, resetOccupiedCounter) 
        }
    if (state.occupiedCounter == 1) { 
        state.startTime = now() 
        }
    if (heavyuseControl && state.occupiedCounter == numberBeforeHeavyuseActivation) { 
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
    if (sendOccupiedNotification) {
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
def sunriseHandler(evt) {
    log.debug "Sun has risen!"
    if (onAtSunriseChosen) {
        switchToTurnOnAtThisTime.on()
        }
    if (offAtSunriseChosen) {
        switchToTurnOffAtThisTime.off()
        }
}
def sunsetHandler(evt) {
    log.debug "Sun has set!"
    if (onAtSunsetChosen) {
        switchToTurnOnAtThisTime.on()
        }
    if (offAtSunsetChosen) {
        switchToTurnOffAtThisTime.off()
        }
}
def switchesOffCountdown() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (offRequired && ['automationon'].contains(automationState)) {
        log.info "The Lights Will Be Switched Off In $switchesOffCountdownInSeconds Seconds"
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
    def areaState = child.getAreaState()
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('vacanton')   
            } else { 
                    child.generateEvent('vacant')
                    }} else {
                             child.generateEvent('vacant')
                             }
    if(occupancyStatusChangesSubscribed) {
       log.info "Re-Evaluation Caused By $app.label Changing To Vacant"
       mainAction() 
       }
    if (sendVacantNotification) {
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