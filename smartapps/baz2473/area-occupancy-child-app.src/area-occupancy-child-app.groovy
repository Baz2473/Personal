/*
 Copyright (C) 2017 Baz2473
 Name: Area Occupancy Child App
*/   
public static String areaOccupancyChildAppVersion() { return "v3.1.1.1" }

private isDebug() {
        if (debugging) { 
            return true 
        } else {
                return false
                }
}

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
    page(name: "versions")
}

def versions() {
    dynamicPage(name: "versions", title: "\t\t\t\t      Reference") {
    section("") { 
          paragraph image : "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao3.png",
          title: "\t  Current App Versions:\n\nArea Occupancy:\t\t\t\t\t${areaOccupancyVersion()}\n\nArea Occupancy Child App:\t\t${areaOccupancyChildAppVersion()}\n\nArea Occupancy DTH:\t\t\t${areaOccupancyDTHVersion()}",
		  end

   } // end of versions section
                                                                   }
}  // end of versions page "
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

     section("") {
     href(name: "href", title: "View App Versions", required: false, page: "versions")
     }

     section("debugLogging") {
     input "debugging", "bool", title: "Enable Logging?", defaultValue: false, submitOnChange: true
     }
     section("Only Perform 'ON' Actions If DISARMED") {
     input "onlyIfDisarmed", "bool", title: "Only If Alarm Is Disarmed", defaultValue: false
     }
     section("To Proceed, Please Select Motion As Method For Detecting Occupancy\nFor $app.label!") {
     input "motionActivated", "bool", title: "Motion?", defaultValue: false, submitOnChange: true
     
}

if (motionActivated) {
    section("Select The Motion Sensors In '$app.label'") {
             input "entryMotionSensors", "capability.motionSensor", title: "Entry Sensors?", required: true, multiple: true, submitOnChange: true            	     
}
if (entryMotionSensors && !exitMotionSensors) {
    section("Manually Force Vacant State\nWith A Timer For $app.label's Area?") {
             input "noExitSensor", "bool", title: "No Exit Sensor For\n$app.label?", defaultValue: false, submitOnChange: true
}}
if (entryMotionSensors || entryMotionTimeout) {
    section("Monitored Door In An Adjacent Area To $app.label") {
             input "monitoredDoor2", "bool", title: "Adjacent Monitored Doors?", defaultValue: false, submitOnChange: true
}}
if (monitoredDoor2) {
    section("Select The Doors\nThat Are Monitored 'Off Of' '$app.label'?") {
             input "adjacentDoors", "capability.contactSensor", title: "Which Adjacent Doors?", multiple: true, required: true, submitOnChange: true
}}
if (entryMotionSensors && monitoredDoor2) {  
          section("Motion Sensors When The Door Is 'Open'\n'Outside' Of $app.label") {
                   input "exitMotionSensorsWhenDoorIsOpen", "capability.motionSensor", title: "$adjacentDoors OPEN Exit Sensors?", required: true, multiple: true, submitOnChange: false     
}}
if (entryMotionSensors && monitoredDoor2) {  
          section("Motion Sensors When The Door Is 'Closed'\n'Outside' Of $app.label") {
                   input "exitMotionSensorsWhenDoorIsClosed", "capability.motionSensor", title: "$adjacentDoors CLOSED Exit Sensors?", required: true, multiple: true, submitOnChange: false   
}}
if (entryMotionSensors && !doors2 && !monitoredDoor2 && !noExitSensor) {  
          section("Select The Motion Sensors 'Outside' $app.label") {
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
             if (monitoredDoor) {
                 input "doors", "capability.contactSensor", title: "Doors?", multiple: true, required: true, submitOnChange: true
                 if (doors) { 
             	     input "actualEntrySensorsTimeout", "number", title: "$app.label's Timeout?",required: true, defaultValue: null, submitOnChange: true
                     input "autoEngagedFunction", "bool", title: "Automatically Engage $app.label When A Switch Turns On?",defaultValue: false, submitOnChange: true
                     if (autoEngagedFunction) {
                         input "autoEngagedSwitches", "capability.switch", title: "Which Switches?", multiple: true, required: false, defaultValue: null, submitOnChange: true
                         }
           		     input "actionOnDoorOpening", "bool", title: "Turn ON Something When\n$doors Opens?", defaultValue: false, submitOnChange: true
                     if (actionOnDoorOpening) {
                         input "onlyIfAreaVacant", "bool", title: "But Only IF $app.label Is Vacant", defaultValue: true, submitOnChnage: true
                		 input "doorOpeningAction", "capability.switchLevel", title: "Turn On?", multiple: true, required: true, submitOnChange: true
                		 input "setLevelAt", "number", title: "Set Level To? %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null 
                 		 if (setLevelAt) {  
                     	 input "onlyDuringCertainTimes", "bool", title: "Only During Certain Times?", defaultValus: false, submitOnChange: true
                         if (onlyDuringCertainTimes) {
                             if (!onlyDuringNighttime && !fromTime) {
                                  input "onlyDuringDaytime", "bool", title: "Only During The Daytime", defaultValue: false, submitOnChange: true
                                  }
                             if (!onlyDuringDaytime && !fromTime) {
                                  input "onlyDuringNighttime", "bool", title: "Only During The Nighttime", defaultValue: false, submitOnChange: true
                                  }
                             if (!onlyDuringDaytime && !onlyDuringNighttime) {
                                  input "fromTime", "time", title: "From?", required: true, submitOnChange: true
                                  input "toTime", "time", title: "Until?", required: true
                                  }
                         input "anotherAction", "bool", title: "Another Time Schedule?", defaultValue: false, submitOnChange: true
                         if (anotherAction) {
                             input "onlyIfASensorIsActive", "bool", title: "But Only If A Sensor Is Active?", defaultValue: false, submitOnChange: true
                             if (onlyIfASensorIsActive) {
                                 input "onlyIfThisSensorIsActive", "capability.motionSensor", title: "Only If This Sensor Is Active!", multiple: true, required: true
                                 }
                             input "onlyIfAreaVacant2", "bool", title: "But Only IF $app.label Is Vacant", defaultValue: false, submitOnChnage: true
                             input "doorOpeningAction2", "capability.switchLevel", title: "Turn On?", multiple: true, required: true, submitOnChange: true
                             input "setLevelAt2", "number", title: "Set Level To? %", required: true, multiple: false, range: "1..100", submitOnChange: true 
                             if (!onlyDuringNighttime2 && !fromTime2) {
                                  input "onlyDuringDaytime2", "bool", title: "Only During The Daytime", defaultValue: false, submitOnChange: true
                                  }
                             if (!onlyDuringDaytime2 && !fromTime2) {
                                  input "onlyDuringNighttime2", "bool", title: "Only During The Nighttime", defaultValue: false, submitOnChange: true
                                  }
                             if (!onlyDuringDaytime2 && !onlyDuringNighttime2) {
                                  input "fromTime2", "time", title: "From?", required: true, submitOnChange: true
                                  input "toTime2", "time", title: "Until?", required: true
                                  }
                         input "turnOffAfter", "bool", title: "Turn Off After Set Time?", defaultValue: false, submitOnChange: true
                         if (turnOffAfter) {
                             input "offAfter", "number", title: "Turn Off After?", required: true, submitOnChange: true, defaultValue: null 
                         }
                         }}
                   }
             input "actionOnDoorClosing", "bool", title: "Turn OFF When\n$doors Closes?", defaultValue: false, submitOnChange: true

}}}}} // end of Monitored Door Control Section

if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2) && !switches) {
    section("Do You Want 'Any' Lights\nTo Automatically Turn 'ON'?") {
             input "switchOnControl", "bool", title: "OCCUPIED 'ON' Control?", defaultValue: false, submitOnChange: true
}}
if (switchOnControl) {
    section("Turn ON Which Dimmable Lights?\nWhen '$app.label' Changes To 'OCCUPIED'") {
             input "dimmableSwitches1", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
             if (dimmableSwitches1) {          
                 input "setLevelTo", "number", title: "Set Level To %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null             
}}}
 // end of Auto ON Control Section

if (exitMotionSensors || entryMotionTimeout || monitoredDoor2) {
    section("Do You Want Any Lights\nTo Automatically Turn 'OFF'?") {
             input "offRequired", "bool", title: "VACANT 'OFF' Control?", defaultValue: false, submitOnChange: true
}}
if (offRequired) {
    section("Only If Different Chosen Areas Are 'Vacant'?") {
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
             input "onlyDuringDaytime9", "bool", title: "Only During The Daytime", defaultValue: false, submitOnChange: true
}}
if (delayedOff) {
    section("Turn OFF Which Lights\nWith A Delay,\nAfter $app.label Changes to 'VACANT'") {
             input "switches2", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
             if (switches2) {
                 input "dimByLevel", "number", title: "Reduce Level By %\nBefore Turning Off!", required: false, multiple: false, range: "1..99", submitOnChange: false, defaultValue: null               
}}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2) && switches2 && delayedOff) {
    section("How Many Seconds 'After' $app.label Is 'VACANT'\nUntil You Want The Lights To Dim Down?") {
             input "dimDownTime", "number", title: "How Many Seconds?", required: true, defaultValue: null, submitOnChange: false
}}
if ((exitMotionSensors || entryMotionTimeout || monitoredDoor2) && switches2 && delayedOff) {
    section("How Many Seconds 'After'\n$app.label's Lights Have Dimmed Down,\nUntil You Want Them To Turn OFF?") {
             input "switchesOffCountdownInSeconds", "number", title: "How Many Seconds?", required: true, defaultValue: null, submitOnChange: false
             
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

section("Do Not Disturb Control") {
         if (entryMotionSensors && monitoredDoor && doors) {
             input "donotdisturbControl", "bool", title: "Do Not Disturb Control?", submitOnChange: true
             if (donotdisturbControl) {
                 paragraph "How Many Minutes Must $app.label\nStay Motionless While 'Engaged'\nBefore Activating 'Do Not Disturb'?"
                 input "dndCountdown", "number", title: "How Many Minutes?", required: true, submitOnChange: true
          }} else {
                  paragraph "Do Not Disturb Is Only Accessable In Conjunction With Entry Motion Sensors & A Monitored Door!"               

}} // end of Do Not Disturb Control Section

if(entryMotionSensors) {
	section("Action On Engaged") {
         input "actionOnEngaged", "bool", title: "Turn ON Something When\n$app.label Changes To Engaged?", defaultValue: false, submitOnChange: true
                     if (actionOnEngaged) {
                		 input "engagedAction", "capability.switch", title: "Turn On?", multiple: true, required: true, submitOnChange: true
                         }
}
section("Action On Vacant") {
         input "actionOnVacant", "bool", title: "Turn OFF Something When\n$app.label Changes To Vacant?", defaultValue: false, submitOnChange: true
                     if (actionOnVacant) {
                		 input "vacantAction", "capability.switch", title: "Turn Off?", multiple: true, required: true, submitOnChange: true
                         }
}
section("Reset Entire Room On SHM Setting To Away?") {
         input "resetOnSHMChangingToAway", "bool", title: "Reset Entire Area When SHM Sets To AWAY?", required: false, submitOnChange: false
}
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
             if(dimmableSwitches1 && switchOnControl) { input "onSwitchesAndLightsSubscribed1", "bool", title: "Lights Turning ON", required: false, submitOnChange: true, defaultValue: false }
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
                      input "otherAreaSubscribedDonotdisturb", "bool", title: "Force A Vacancy Check When $otherArea Changes To DO NOT DISTURB", required: false, submitOnChange: true, defaultValue: false                 
                   }} else {
                            paragraph "Checking the status of other areas is only possible if you have selected the area"
                            }}
    
} // end of Subscriptions Selection Section
} // end of Section After Occupancy Selection Section

	section("Turn Something 'ON' or 'OFF' In $app.label\nAt A Certain Time!") {
         input "timedControl", "bool", title: "Perform Timed Actions?", required: false, defualtValue: false, submitOnChange: true
         if (timedControl) {
             input "timedTurnOnControl", "bool", title: "Turn ON?", required: false, defualtValue: false, submitOnChange: true
             if (timedTurnOnControl && timedControl) {   
                 input "switchToTurnOnAtThisTime", "capability.switch", title: "Switchs?", required: true, multiple: true
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
                 input "switchToTurnOffAtThisTime", "capability.switch", title: "Switchs?", required: true, multiple: true
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
             input "checkableLights", "capability.switch", title: "Lights?", required: true, multiple: true
             }
      }
} // end of if(motionActivated)
             
}} // end of areaName page

def installed() {}
def updated() {
	unsubscribe()
	initialize()
         
if (!childCreated()) {
     spawnChildDevice(app.label) 
     }        
    if (adjacentDoors && monitoredDoor2 && adjacentMonitoredDoorOpeningSubscribed) { 
        subscribe(adjacentDoors, "contact.open", adjacentMonitoredDoorOpeningEventHandler) 
        }
    if (adjacentDoors && monitoredDoor2 && adjacentMonitoredDoorClosingSubscribed) { 
        subscribe(adjacentDoors, "contact.closed", adjacentMonitoredDoorClosingEventHandler) 
        }
    if (autoEngagedFunction && autoEngagedSwitches && doors) {
        subscribe(autoEngagedSwitches, "switch.on", autoEngagedSwitchesEventHandler)
        }
    if (awayModes && noAwayMode || resetAutomationMode && resetAutomation) { 
        subscribe(location, modeEventHandler) 
        }
    if (checkableLights) {
        subscribe(checkableLights, "switch.on", checkableLightsSwitchedOnEventHandler)
        subscribe(checkableLights, "switch.off", checkableLightsSwitchedOffEventHandler)
        }
    if (dimmableSwitches1 && switchOnControl && onSwitchesAndLightsSubscribed1) {
        subscribe(dimmableSwitches1, "switch.on", dimmableSwitches1OnEventHandler)
        subscribe(dimmableSwitches1, "switch.off", dimmableSwitches1OffEventHandler)
        }    
    if (doors && monitoredDoor && monitoredDoorsOpeningSubscribed) { 
        subscribe(doors, "contact.open", monitoredDoorOpenedEventHandler) 
        }
    if (doors && monitoredDoor && monitoredDoorsClosingSubscribed) {
        subscribe(doors, "contact.closed", monitoredDoorClosedEventHandler) 
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
    if (otherArea && otherAreaCheck && otherAreaSubscribedVacant) {
        subscribe(otherArea, "occupancyStatus.vacant", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedVacant) {
        subscribe(otherArea, "occupancyStatus.vacanton", otherAreaOccupancyStatusEventHandler)
        }
    if (onlyIfDisarmed) {
        subscribe(location, "alarmSystemStatus", shmStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedOccupied) {
        subscribe(otherArea, "occupancyStatus.occupied", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedOccupied) {
        subscribe(otherArea, "occupancyStatus.occupiedon", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedEngaged) {
        subscribe(otherArea, "occupancyStatus.engaged", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedEngaged) {
        subscribe(otherArea, "occupancyStatus.engagedon", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedChecking) {
        subscribe(otherArea, "occupancyStatus.checking", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedChecking) {
        subscribe(otherArea, "occupancyStatus.checkingon", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedDonotdisturb) {
        subscribe(otherArea, "occupancyStatus.donotdisturb", otherAreaOccupancyStatusEventHandler)
        }
    if (otherArea && otherAreaCheck && otherAreaSubscribedDonotdisturb) {
        subscribe(otherArea, "occupancyStatus.donotdisturbon", otherAreaOccupancyStatusEventHandler)
        }
    if (presenceSensors && presence) { 
        subscribe(presenceSensors, "presence.not present", presenceAwayEventHandler) 
        }
    if (switches2 && delayedOff && dimmingLightsSubscribed) { 
        subscribe(switches2, "switch.on", switches2OnEventHandler)
        subscribe(switches2, "switch.off", switches2OffEventHandler)
        }
    if (switches3 && instantOff && instantOffSwitchesAndLightsSubscribed) { 
        subscribe(switches3, "switch.on", switches3OnEventHandler)
        subscribe(switches3, "switch.off", switches3OffEventHandler)
}}
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
    }}
def uninstalled() {
	getChildDevices().each {
    deleteChildDevice(it.deviceNetworkId) }
    }
def areaOccupancyVersion() {
    parent.areaOccupancyVersion()
    }
def areaOccupancyDTHVersion() {
    def child = getChildDevice(getArea())
    child.DTHVersion()
    }

//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
def mainAction() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (entryMotionState.value.contains("active")) {
        ifDebug("mainAction() Running ---- Entry motion is Active")
        if (dimmableSwitches1 && switchOnControl && ['automationon'].contains(automationState)) {
            dimmableSwitches1.each {
            						def currentLevel = it.currentValue("level")
            						if (currentLevel < setLevelTo) { 
                						if (onlyIfDisarmed) {
                 						    def shmStatus = location.currentState("alarmSystemStatus")?.value
                  						    if (shmStatus == "off") {
                    						    it.setLevel(setLevelTo)
                     						    ifDebug("489 Setting level of $it to $setLevelTo %")
                  					  		} else {
                      								ifDebug("SHM is ARMED! No Lights Will Turn On!")
                    						       }
               						    } else {
                       						    it.setLevel(setLevelTo)
                       						    ifDebug("495 Setting level of $it to $setLevelTo %")
               						           }
            						} else if (it.currentValue("switch") == 'off') {
                      						   it.on()
                       						   ifDebug("Level previously set... Switching on $it")
            						          }
            }
        }
        if (doors) {
                    def doorsState = doors.currentState("contact") 
                    if (!doorsState.value.contains("open") && ['donotdisturb','donotdisturbon'].contains(areaState)) {
                         engaged()
                       } else if (!doorsState.value.contains("open") && ['occupied','occupiedon'].contains(areaState)) { 
                                   checking()                 
                                 } else if (!doorsState.value.contains("open") && ['checking','checkingon'].contains(areaState)) {                                                                                                       
                                             runIn(actualEntrySensorsTimeout, engaged, [overwrite: false])
                                           } else if (doorsState.value.contains("open") && ['checking','checkingon','engaged','engagedon','donotdisturb','donotdisturbon'].contains(areaState)) { 
                                                      occupied()   
                                                     } else if (['vacant','vacantdimmed','vacanton'].contains(areaState)) { 
                                                                  occupied()
                                                               } 
                   } else if (['vacant','vacantdimmed','vacanton'].contains(areaState)) { 
                                occupied()
                             }       

//-----------------------------------------------------------------------INACTIVE FROM HERE DOWN---------------------------------------------------------------------------------------------

} else { 
        ifDebug("mainAction() Running ---- Entry motion is Inactive")
        if (noExitSensor && ['occupied','occupiedon'].contains(areaState)) { 
            ifDebug("525 Vacant will be activated in $entryMotionTimeout seconds")
            runIn(entryMotionTimeout, vacant)//, [overwrite: false])
            }                            
        if (donotdisturbControl && ['engaged','engagedon'].contains(areaState)) {                             
            runIn(dndCountdown * 60, donotdisturb)//, [overwrite: false])
            }
        if (exitMotionSensors && ['occupied','occupiedon'].contains(areaState) && !adjacentDoors) {
            def exitMotionState = exitMotionSensors.currentState("motion")
            if (exitMotionState.value.contains("active")) { 
                vacant()
                }
           }                            
        if (adjacentDoors && ['occupied','occupiedon'].contains(areaState) && !exitMotionSensors) {         
            def adjacentDoorsState = adjacentDoors.currentValue("contact")
            if (adjacentDoorsState.contains("open")) {
                def exitMotionStateWithDoorsOpen = exitMotionSensorsWhenDoorIsOpen.currentValue("motion") 
                if (exitMotionStateWithDoorsOpen.contains("active")) {
                    vacant()
                   }
               } else {
                       def exitMotionStateWithDoorsClosed = exitMotionSensorsWhenDoorIsClosed.currentValue("motion") 
                       if (exitMotionStateWithDoorsClosed.contains("active")) {
                           vacant()
                          }
                      }
            }                                
        if (['checking','checkingon'].contains(areaState)) { 
              runIn(actualEntrySensorsTimeout, vacant)//, [overwrite: false])
           } else {
                   if (anotherVacancyCheck && anotherCheckIn && ['occupied','occupiedon'].contains(areaState)) {
                       runIn(anotherCheckIn, forceVacantIf)//, [overwrite: false])
                      }
                   if (switches3 && instantOff && offRequired) { 
                       def switches3State = switches3.currentState("switch")  
                       if (switches3State.value.contains("on") && ['vacanton'].contains(areaState) && ['automationon'].contains(automationState)) { 
                           if (thisArea && !andThisArea) { 
                               def thisAreaState = thisArea.currentState("occupancyStatus")
                               if (thisAreaState.value.contains("vacant")) {
                                   switches3.off()
                                  } else { 
                                          ifDebug("568 Doing Nothing Because Your Other Area Is Still Occupied")                                                                                   
                                         }
                              } else {
                                      if (thisArea && andThisArea) { 
                                          def thisAreaState = thisArea.currentState("occupancyStatus")
                                          def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                          if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                              switches3.off()     
                                             } else {
                                                     ifDebug("576 Doing Nothing Because 1 Of Your Other Areas Are Still Occupied")                                             
                                                    }
                                         } else {
                                                 switches3.off()
                                                }
                                     }
                           }
                       }
                    if (switches2 && delayedOff && offRequired) { 
                        def switches2State = switches2.currentState("switch")  
                        if (switches2State.value.contains("on") && ["vacanton"].contains(areaState) && ['automationon'].contains(automationState)) { 
                            if (thisArea && !andThisArea) { 
                                def thisAreaState = thisArea.currentState("occupancyStatus")
                                if (thisAreaState.value.contains("vacant")) {
                                        if (onlyDuringDaytime9) {
                                            def s = getSunriseAndSunset()
                                            def sunrise = s.sunrise.time
                                            def sunset = s.sunset.time
                                            def timenow = now()
                                            if (timenow > sunrise && timenow < sunset) {
                                                ifDebug("597 The Lights Will Dim Down In $dimDownTime Seconds")
                                                runIn(dimDownTime, dimLights)
                                               } else {}
                                           } else {
                                                   ifDebug("601 The Lights Will Dim Down In $dimDownTime Seconds")
                                                   runIn(dimDownTime, dimLights)
                                                  } 
                                     } else {
                                             ifDebug("605 Doing Nothing Because Your Other Area Is Still Occupied")                          
                                            }
                                  } else {
                                          if (thisArea && andThisArea) { 
                                              def thisAreaState = thisArea.currentState("occupancyStatus")
                                              def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                              if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                                      if (onlyDuringDaytime9) {
                                                          def s = getSunriseAndSunset()
                                                          def sunrise = s.sunrise.time
                                                          def sunset = s.sunset.time
                                                          def timenow = now()
                                                          if (timenow > sunrise && timenow < sunset) {
                                                              ifDebug("618 The Lights Will Dim Down In $dimDownTime Seconds")
                                                              runIn(dimDownTime, dimLights)
                                                             } else {}
                                                          } else {
                                                                  ifDebug("622 The Lights Will Dim Down In $dimDownTime Seconds")
                                                                  runIn(dimDownTime, dimLights)
                                                                 } 
                                                 } else {
                                                         ifDebug("626 Doing Nothing Because 1 Of Your Other Areas Are Still Occupied")                                                                                                                
                                                        }
                                              } else {
                                                          if (onlyDuringDaytime9) {
                                                              def s = getSunriseAndSunset()
                                                              def sunrise = s.sunrise.time
                                                              def sunset = s.sunset.time
                                                              def timenow = now()
                                                              if (timenow > sunrise && timenow < sunset) {
                                                                  ifDebug("635 The Time Is Before Sunset So The Lights Will Dim Down In $dimDownTime Seconds")
                                                                  runIn(dimDownTime, dimLights)
                                                                 } else { 
                                                                         ifDebug("638 The Time Is After Sunset, Doing Nothing")}
                                                                        } else {
                                                                                ifDebug("640 The Lights Will Dim Down In $dimDownTime Seconds")
                                                                                runIn(dimDownTime, dimLights)
                                                                               } 
                                                    }
                                      	   }
                           		  }
                 		 }                      
				}
		}
}                    

//888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888

def adjacentMonitoredDoorClosingEventHandler(evt) {
    ifDebug("Re-Evaluation Caused By An Ajacent Monitored Door Closing")
    mainAction()
} 

def adjacentMonitoredDoorOpeningEventHandler(evt) {
    ifDebug("Re-Evaluation Caused By An Ajacent Monitored Door Opening")
    mainAction()
}

def autoEngagedSwitchesEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()             
    def doorsState = doors.currentState("contact") 
    if (!doorsState.value.contains("open") && ['vacant','vacanton'].contains(areaState)) {
         engaged() 
         }
}

def checkableLightsSwitchedOnEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacant','vacantdimmed'].contains(areaState)) { 
          child.generateEvent('vacanton')
          if (occupancyStatusChangesSubscribed) { 
              ifDebug("Re-Evaluation Caused By $app.label Changing To VacantOn")
              mainAction() 
          	  }
          } else if (['occupied'].contains(areaState)) { 
                        child.generateEvent('occupiedon')
                        if (occupancyStatusChangesSubscribed) { 
                            ifDebug("Re-Evaluation Caused By $app.label Changing To OccupiedOn")
                            mainAction() 
                        	}
                        } else if (['engaged'].contains(areaState)) { 
                                      child.generateEvent('engagedon')
                                      if (occupancyStatusChangesSubscribed) { 
      									  ifDebug("Re-Evaluation Caused By $app.label Changing To EngagedOn")
      									  mainAction() 
                                     	  }
                                      } else if (['checking'].contains(areaState)) { 
                                                    child.generateEvent('checkingon')
                                                    if (occupancyStatusChangesSubscribed) { 
                                                        ifDebug("Re-Evaluation Caused By $app.label Changing To CheckingOn")
     												    mainAction() 
                                                   	    }
                                                    } else if (['donotdisturb'].contains(areaState)) { 
                                                                                child.generateEvent('donotdisturbon')
                                                                                if (occupancyStatusChangesSubscribed) { 
                                                                                    ifDebug("Re-Evaluation Caused By $app.label Changing To DonotdisturbON")
                                                                                    mainAction() 
                                                                                    }
                                                                          }
}

def checkableLightsSwitchedOffEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def checkableLightsState = checkableLights.currentState("switch")
    if (['vacanton','vacantdimmed'].contains(areaState) && !checkableLightsState.value.contains("on")) { 
          child.generateEvent('vacant')
          } else if (['occupiedon'].contains(areaState) && !checkableLightsState.value.contains("on")) { 
                        child.generateEvent('occupied')
                        } else if (['engagedon'].contains(areaState) && !checkableLightsState.value.contains("on")) { 
                                      child.generateEvent('engaged')
                                      } else if (['checkingon'].contains(areaState) && !checkableLightsState.value.contains("on")) { 
                                                    child.generateEvent('checking')
                                                    } else if (['donotdisturbon'].contains(areaState) && !checkableLightsState.value.contains("on")) { 
                                                                                child.generateEvent('donotdisturb')
                                                                                }
} 

def checking() {
def child = getChildDevice(getArea())
def areaState = child.getAreaState()
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('checkingon')
        } else {                
                 child.generateEvent('checking')
               }
     } else {
             child.generateEvent('checking')
     	    }
    if (occupancyStatusChangesSubscribed) { 
        ifDebug("Re-Evaluation Caused By $app.label Changing To Checking")
        mainAction() 
        }
} // end of checking

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
    ifDebug("${app.label} Area Has Been Uninstalled!")
}

def dimLights() {
    ifDebug("Activating dimLights()")
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (offRequired && ['automationon'].contains(automationState)) {
        if (!entryMotionState.value.contains("active")) { 
        	if (['vacanton'].contains(areaState)) {
        		switches2.each {
        		if (it.currentValue("switch") == 'on') {
            		def currentLevel = it.currentValue("level")
           	   	    def newLevel = (currentLevel > dimByLevel ? currentLevel - dimByLevel : 1)
                	it.setLevel(newLevel)
                    ifDebug("The $it have been dimmed to $newLevel %")
                	}
     			}
            child.generateEvent('vacantdimmed')
            ifDebug("The lights will turn off in $switchesOffCountdownInSeconds seconds")
        	runIn(switchesOffCountdownInSeconds, switches2Off)  
    		} else {
                ifDebug("Re-Evaluated because the lights were told to dim but your room is not in the vacanton state!")
                mainAction() 
            }
        } else {
            ifDebug("Re-Evaluated because the lights were told to dim but the motion in this room is not inactive")
        	mainAction()
        }
   	 }
} 

def dimmableSwitches1OnEventHandler(evt) { 
    ifDebug("Re-Evaluated by Dimmable Switches1 On")
    mainAction() 
}

def dimmableSwitches1OffEventHandler(evt) {
    ifDebug("Re-Evaluated by Dimmable Switches1 Off")
    mainAction() 
}

def dimmableSwitches2OnEventHandler(evt) {
    ifDebug("Re-Evaluated by Dimmable Switches2 On")
    mainAction() 
}

def dimmableSwitches2OffEventHandler(evt) {
    ifDebug("Re-Evaluated by Dimmable Switches2 Off")
    mainAction() 
}

def dimmableSwitches1On() {
        if (onlyIfDisarmed) {
            def shmStatus = location.currentState("alarmSystemStatus")?.value
            if (shmStatus == "off") {
                def child = getChildDevice(getArea())
                def automationState = child.getAutomationState()
                     if (dimmableSwitches1 && switchOnControl && ['automationon'].contains(automationState)) {      
                         ifDebug("The Selected Switch(es) Have Either Been Turned 'ON' Or Had Their Respective Level(s) Set")
                         dimmableSwitches1.each {
                         it.setLevel(setLevelTo)
                         }
					}
            } else {
            	ifDebug("SHM is ARMED! No Lights Will Turn On!")
            }
         } else {
                 def child = getChildDevice(getArea())
                 def automationState = child.getAutomationState()
                 if (dimmableSwitches1 && switchOnControl && ['automationon'].contains(automationState)) {      
                     ifDebug("The Selected Switch(es) Have Either Been Turned 'ON' Or Had Their Respective Level(s) & Color(s) Set")
                     dimmableSwitches1.each {
                     it.setLevel(setLevelTo)
                     }
				 }
         }
}

def donotdisturb() {      
def child = getChildDevice(getArea())
def areaState = child.getAreaState()
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('donotdisturbon')
        } else {                
			    child.generateEvent('donotdisturb')
				}
    } else {
            child.generateEvent('donotdisturb')
            }
    if (occupancyStatusChangesSubscribed) {
        ifDebug("Re-Evaluation Caused By $app.label Changing To Do Not Disturb")
        mainAction() 
        }
} // end of do not disturb

def doaoff() {
    doorOpeningAction2.each {
    it.setLevel(0)
    it.off()
	}
} // end of doa2Off

def engaged() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (actionOnEngaged) {
        engagedAction.on()
        }
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('engagedon')
        } else {                
                child.generateEvent('engaged')
                }
    } else {
            child.generateEvent('engaged')
            }
    if (occupancyStatusChangesSubscribed) { 
        ifDebug("Re-Evaluation Caused By $app.label Changing To Engaged")
        mainAction() 
        }
} // end of engaged

def	entryMotionActiveEventHandler(evt) {
    ifDebug("Re-Evaluation Caused By An Entry Motion Sensor Being 'ACTIVE'")
    unschedule(vacant)
    unschedule(switches2Off)
    unschedule(dimLights)
    unschedule(donotdisturb)
    unschedule(forceVacantIf)
    unschedule(checkOtherAreaAgain)
    if (doors) {    
                unschedule(engaged)
                def doorsState = doors.currentState("contact") 
                def child = getChildDevice(getArea())
  			    def areaState = child.getAreaState()
                if (!doorsState.value.contains("open") && ['vacant','vacantdimmed','vacanton','occupied','occupiedon','checking','checkingon','donotdisturb','donotdisturbon'].contains(areaState)) {
                     engaged()
                } else {
                        mainAction()
                }
    } else {
            mainAction() 
    }
}

def	entryMotionInactiveEventHandler(evt) {
    ifDebug("Re-Evaluation Caused By An Entry Motion Sensor Being 'INACTIVE'")
    if (doors) {
    	unschedule(engaged)
    }
    mainAction() 
}

def exitMotionActiveEventHandler(evt) { 
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {       
           ifDebug("Re-Evaluation Caused By An Exit Motion Sensor Being 'ACTIVE'")
           mainAction() 
           }
}

def exitMotionInactiveEventHandler(evt) { 
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           ifDebug("Re-Evaluation Caused By An Exit Motion Sensor Being 'INACTIVE'")
           mainAction() 
           }
}

def exitMotionSensorsWhenDoorIsOpenActiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
      if (adjacentDoors) {
          def adjacentDoorsState = adjacentDoors.currentValue("contact")
          if (adjacentDoorsState.contains("open")) {
              ifDebug("Re-Evaluation Caused By An (OPEN) Exit Motion Sensor Being 'ACTIVE'")
              mainAction()
          }
       }
    }
}

def exitMotionSensorsWhenDoorIsOpenInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (adjacentDoorsState.contains("open")) {
            ifDebug("Re-Evaluation Caused By An (OPEN) Exit Motion Sensor Being 'INACTIVE'")
            mainAction()
            }
        }
    }
}

def exitMotionSensorsWhenDoorIsClosedActiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (!adjacentDoorsState.contains("open")) {
             ifDebug("Re-Evaluation Caused By A (CLOSED) Exit Motion Sensor Being 'ACTIVE'")
             mainAction()
        }
     }
   }
}

def exitMotionSensorsWhenDoorIsClosedInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
    if (adjacentDoors) {
        def adjacentDoorsState = adjacentDoors.currentValue("contact")
        if (!adjacentDoorsState.contains("open")) {
             ifDebug("Re-Evaluation Caused By A (CLOSED) Exit Motion Sensor Being 'INACTIVE'")
             mainAction()
        }
     }
  }
} 

def forceTurnAllOff() {
    def child = getChildDevice(getArea())
    ifDebug("The Alarm Is Now Set So Performing All Full Reset Of $app.label!")
    checkableLights.each {
    if (it.hasCommand("setLevel")) {
        it.setLevel(0)
    } else {                                    
            it.off()
            }
    }
    ifDebug("Generating VACANT Event!")                    
    child.generateEvent('vacant')
    unschedule()
    ifDebug("All Scheduled Jobs Have Been Cancelled!")
    ifDebug("Generating AUTOMATION ON Event!") 
    child.generateAutomationEvent('automationon')
}

def forceVacantIf() {
    ifDebug("forcing Vacant Check")
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    ifDebug("The entry Motion State Is: $entryMotionState")
    if (!['vacant'].contains(areaState) && !entryMotionState.value.contains("active")) {
           vacant()
           }
}

private getArea() {	
        return "aa_${app.id}" 
}

def leftHome() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (['automationon'].contains(automationState)) {
          ifDebug("$app.label was set to 'VACANT' because the mode changed to away or Heavy Use Was Disabled!")
          vacant()
          ifDebug("switchesOffCountdown Sent")
          runIn(switchesOffCountdownInSeconds, switches2Off)  
          }
}

def	modeEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    if (resetAutomation && resetAutomationMode && resetAutomationMode.contains(evt.value) && ['automationoff'].contains(automationState)) {
       ifDebug("$app.label's Automation Has Been Enabled Because Your Reset Mode Was ACTIVATED!")
       child.generateAutomationEvent('automationon') 
       }       
    if (awayModes && awayModes.contains(evt.value) && noAwayMode) {
        ifDebug("$app.label Was Set To 'VACANT' Because Your Away Mode Was 'ACTIVATED'!")
        leftHome() 
        }
}   

def monitoredDoorOpenedAction() {
    def lightStateForDoorAction = doorOpeningAction.currentState("switch")
    doorOpeningAction.each {
    it.on()
    it.setLevel(setLevelAt)
    }
    mainAction()
} // end of monitoredDoorOpenedAction

def monitoredDoorOpenedAction2() {
    def lightStateForDoorAction2 = doorOpeningAction2.currentState("switch")
    doorOpeningAction2.each {
    it.on()
    it.setLevel(setLevelAt2)
    }
    mainAction()
} // end of monitoredDoorOpenedAction2

def monitoredDoorOpenedEventHandler(evt) { 
    unschedule(engaged)
    unschedule(vacant)
    unschedule(donotdisturb)
    unschedule(forceVacantIf)
    unschedule(switches2Off)
    unschedule(dimLights)
    unschedule(heavyuseCheck)
    unschedule(checkOtherAreaAgain)
    if (!actionOnDoorOpening) {                      
        ifDebug("Re-Evaluated by A Monitored Door Opening")
        mainAction() 
        }
    if (actionOnDoorOpening) {
        def child = getChildDevice(getArea())
        def areaState = child.getAreaState()
        if (!onlyDuringDaytime && !onlyDuringNighttime && !onlyDuringDaytime2 && !onlyDuringNighttime2 && !onlyDuringCertainTimes) {
             monitoredDoorOpenedAction()
             mainAction()
             }   
        if (onlyDuringCertainTimes && (onlyDuringDaytime || onlyDuringNighttime || onlyDuringDaytime2 || onlyDuringNighttime2)) {
            def s = getSunriseAndSunset()
            def sunrise = s.sunrise.time
            def sunset = s.sunset.time
            def timenow = now()
            if (onlyDuringDaytime) {  
                if (timenow > sunrise && timenow < sunset) {
                    if (onlyIfAreaVacant) {
                        if (['vacant'].contains(areaState)) {
                              ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                              monitoredDoorOpenedAction()
                        } else {}
                    } else {
                            monitoredDoorOpenedAction()
                            }
                 } 
            } 
            if (onlyDuringNighttime) { 
               if (timenow > sunset || timenow < sunrise) {
                    if (onlyIfAreaVacant) {
                        if (['vacant'].contains(areaState)) {
                              ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                              monitoredDoorOpenedAction()
                        } else {}
                     } else {
                             monitoredDoorOpenedAction()
                             }
                         }
            } 
            if (onlyDuringDaytime2) { 
               if (timenow > sunrise && timenow < sunset) {
                    if (onlyIfAreaVacant2) { 
                        if (['vacant'].contains(areaState)) {
                              ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                              monitoredDoorOpenedAction2()
                        } else {}
                     } else {
                             monitoredDoorOpenedAction2()
                             }
                }
            }
            if (onlyDuringNighttime2) { 
               if (timenow > sunset || timenow < sunrise) {
                    if (onlyIfAreaVacant2) { 
                        if (['vacant'].contains(areaState)) {
                              ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                              monitoredDoorOpenedAction2()
                        } else {}
                    } else {
                            monitoredDoorOpenedAction2()
                            }
                }
            }
        } // end of only during daytime or nighttime
        
           if (!onlyDuringDaytime && !onlyDuringNighttime && !onlyDuringDaytime2 && !onlyDuringNighttime2 && onlyDuringCertainTimes) {
                def between = timeOfDayIsBetween(fromTime, toTime, new Date(), location.timeZone)
                ifDebug("Between = $between")
                def between2 = timeOfDayIsBetween(fromTime2, toTime2, new Date(), location.timeZone)
                ifDebug("Between2 = $between2")
                if (between) {
                    if (onlyIfAreaVacant) {
                        if (['vacant'].contains(areaState)) {
                              ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                              monitoredDoorOpenedAction()
                                                            } else {}
                                          } else {
                                                  monitoredDoorOpenedAction()
                                                  }
                             } // end of between being true
                
           if (between2) {
               if (onlyIfASensorIsActive) {
                   def theMotionState = onlyIfThisSensorIsActive.currentState("motion")
                   if (theMotionState.value.contains("active")) { 
                       if (onlyIfAreaVacant2) {
                           if (['vacant'].contains(areaState)) {
                                 ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                                 monitoredDoorOpenedAction2()
                                                               } else {ifDebug("Doing NOTHING because $onlyIfAreaVacant2 was not vacant")}
                                              } else {
                                                      monitoredDoorOpenedAction2()
                                                      }
                                                                } else {ifDebug("Doing NOTHING because $onlyIfThisSensorIsActive was not active")}
                                          } else {
                                                  if (onlyIfAreaVacant2) {
                                                      if (['vacant'].contains(areaState)) {
                                                            ifDebug("The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!")
                                                            monitoredDoorOpenedAction2()
                                                                                          } else {ifDebug("Doing NOTHING because $onlyIfAreaVacant2 was not vacant")}
                                                                         } else {
                                                                                 monitoredDoorOpenedAction2()
                                                                                 }
                                                 }
                         } // end of between2 being true
     
            } // end of only if all during times are not selected
            
     } // end of action on door opening
        
} // end of monitoredDoorOpenedEventHandler

def monitoredDoorClosedEventHandler(evt) { 
    ifDebug("Re-Evaluated by A Monitored Door Closing")
    if (!actionOnDoorClosing) {
         if (turnOffAfter) {
             ifDebug("Turn Off After Was True SO ")
             ifDebug("The Lights Should Go Off In $offAfter Seconds")
             runIn(offAfter, doaoff, [overwrite: false]) 
         } else {
                 mainAction()
                }
    } // end of no action on door closing
    if (actionOnDoorClosing) {
        def doorsState = doors.currentState("contact")
        if (doorsState.value.contains("open")) {
            ifDebug("doing NOTHING because A Door Is Still Open")
        } else {
                ifDebug("The Light Was Turned OFF Because The Door Was Closed")
                doorOpeningAction.each {
                it.setLevel(0)
                ifDebug("Re-Evaluated by A Monitored Door Closing")
                mainAction() 
                }
         }
   } //end of action on door closing

} // end of monitoredDoorClosedEventHandler

def occupied() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (actionOnVacant) {
        if (['engaged','engagedon'].contains(areaState)) {
              vacantAction.off()
              }
    }
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('occupiedon')
           } else {                
                   child.generateEvent('occupied')
                  }
    } else {
            child.generateEvent('occupied')
           }
    if (occupancyStatusChangesSubscribed) { 
        ifDebug("Re-Evaluation Caused By $app.label Changing To Occupied")
        mainAction() 
     }
}

def otherAreaOccupancyStatusEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {  
           ifDebug("Vacant Force Check Performed by $otherArea Occupancy Changing To Your Required State")
           forceVacantIf()
	}
}

def presenceAwayEventHandler(evt) { 
    forceVacantIf()
    runIn(15, turnalloff)
} 

def shmStatusEventHandler(evt) {
    def child = getChildDevice(getArea())
    def shmStatus = location.currentState("alarmSystemStatus")?.value 
    if (shmStatus == "away") {
        ifDebug("The Alarm Is Now ARMED AWAY!")
        if (resetOnSHMChangingToAway) {
            ifDebug("The Alarm Is Now ARMED AWAY! & You Requested A Full Reset...")
            forceTurnAllOff()
            }
      } else if (shmStatus == "stay") {
                 ifDebug("The Alarm Is Now ARMED STAY!")
               } else if (shmStatus == "off") {
                          ifDebug("The Alarm Is Now OFF!")
                          }                  
}

def spawnChildDevice(areaName) {
    app.updateLabel(app.label)
    if (!childCreated())
	     def child = addChildDevice("Baz2473", "Area Occupancy Status", getArea(), null, [name: getArea(), label: areaName, completedSetup: true])
}

def sunriseHandler(evt) {
    ifDebug("The Sun has risen! Performing Any Sunrise Actions")
    if (onAtSunriseChosen) {
        switchToTurnOnAtThisTime.on()
        }
    if (offAtSunriseChosen) {
        switchToTurnOffAtThisTime.off()
        }
}

def sunsetHandler(evt) {
    ifDebug("The Sun has set! Performing Any Sunset Actions")
    if (onAtSunsetChosen) {
        switchToTurnOnAtThisTime.on()
        }
    if (offAtSunsetChosen) {
        switchToTurnOffAtThisTime.off()
        }
}

def switches2OnEventHandler(evt) { 
    ifDebug("Re-Evaluated by Switches2 On")
    mainAction() 
}

def switches2OffEventHandler(evt) { 
    ifDebug("Re-Evaluated by Switches2 Off")
    mainAction() 
}

def switches2Off() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!entryMotionState.value.contains("active")) { 
    	if (['vacantdimmed'].contains(areaState)) {
    		switches2.each {
    		it.setLevel(0)  
            ifDebug("the $it are now off")
    		}
        } else {
            ifDebug("Re-Evaluated because the lights were told to switch off but the room was not in the vacantdimmed state!")
                mainAction() 
              }   
    } else {
        ifDebug("Re-Evaluated because the lights were told to switch off but the motion in the room was not inactive!")
        	mainAction()
          }
}

def switches3OnEventHandler(evt) { 
    ifDebug("Re-Evaluated by Switches3 On")
    mainAction() 
}

def switches3OffEventHandler(evt) { 
    ifDebug("Re-Evaluated by Switches3 Off")
    mainAction() 
}

def turnOffAtThisTime() {
    ifDebug("the time turn OFF test worked")
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    switchOffAtThisTime.each {
    it.off()
    }
} 

def turnOnAtThisTime() {
    ifDebug("the time turn ON test worked")
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    switchOnAtThisTime.each {
    if (['automationon'].contains(automationState)) { 
         it.on()
         }
    }
}

def vacant() { 
    ifDebug("Performing vacant()")
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('vacanton')  
            ifDebug("1419 Re-Evaluation Caused By $app.label Changing To Vacanton")
            mainAction()
            } else { 
                    child.generateEvent('vacant')
                    ifDebug("1423 Re-Evaluation Caused By $app.label Changing To Vacant")
                    mainAction()
                    }
    } else {
            child.generateEvent('vacant')
            ifDebug("1541 Re-Evaluation Caused By $app.label Changing To Vacant")
            mainAction()
            }
    if (occupancyStatusChangesSubscribed) {
        ifDebug("1545 Re-Evaluation Caused By $app.label occupancyStatus Vacant(on) Change being subscribed to")
        mainAction() 
        }
} // end of vacant  

def turnalloff() {
    def child = getChildDevice(getArea())
    if (!entryMotionSensors && checkableLights) {
        ifDebug("You Have Told Me That $app.label Is Vacant Turning Off All Lights!")
        checkableLights.each {
        if (it.hasCommand("setLevel")) {
            it.setLevel(0)
        } else {                                    
                it.off()
                }
        }
        child.generateEvent('vacant')
        unschedule()
        ifDebug("All Scheduled Jobs Have Been Cancelled!")                                                     
    }
    if (entryMotionSensors && checkableLights) {
        def entryMotionState = entryMotionSensors.currentState("motion")
        if (!entryMotionState.value.contains("active")) { 
             ifDebug("You Have Told Me That $app.label Is Vacant Turning Off All Lights!")
             checkableLights.each {
             if (it.hasCommand("setLevel")) {
                 it.setLevel(0)
             } else {
                     it.off()
                     }
             }
             child.generateEvent('vacant')
             unschedule()
             ifDebug("All Scheduled Jobs Have Been Cancelled!")
        } else {
                ifDebug("Not Performing All Off Because $app.label Was Not Vacant!")
                }
    }
} // end of turnalloff

def turnon() {
    def child = getChildDevice(getArea())
    if (checkableLights) {
        ifDebug("You Have Told Me To Turn On All Lights in $app.label")
        checkableLights.each {
        if (it.hasCommand("setLevel")) {
            it.setLevel(75)
        } else {                                    
                it.on()
                }
        }
        child.generateEvent('vacanton')
        unschedule()
    }

}



private ifDebug(msg = null)     {  if (msg && isDebug()) log.debug msg  }