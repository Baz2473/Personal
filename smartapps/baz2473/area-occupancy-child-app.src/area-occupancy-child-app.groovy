/*
 Copyright (C) 2017 Baz2473
 Name: Area Occupancy Child App
 */

public static String areaOccupancyChildAppVersion() {
    return "v6.2.1.5"
}

definition    (
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
        } 
    }
} 

def areaName() {
    dynamicPage(name: "areaName", title: "A New Device Will Be Created With This Name!", install: true, uninstall: childCreated()) {
        if (!childCreated()) {
            section {
                label title: "What Name?\n(Required):", required: true,  submitOnChange: true
            }
        } else {
            section {
                paragraph "Area Name:\n${app.label}"
            }
        }
        section("") {
            href(name: "href", title: "View App Versions", required: false, page: "versions")
        }
        section("Only turn things 'ON' if the alarm is disarmed") {
            input "onlyIfDisarmed", "bool", title: "Only if disarmed?", defaultValue: false, submitOnChange: false
        }
        section("Select the motion sensors in '$app.label'") {
            input "entryMotionSensors", "capability.motionSensor", title: "Entry motion sensors?", required: true, multiple: true, submitOnChange: true
        }
            if (entryMotionSensors) {
                section("Select the motion sensors 'Outside' $app.label") {
                    input "exitMotionSensors", "capability.motionSensor", title: "Exit sensors?", required: true, multiple: true, submitOnChange: true
               	    }
                }
            if (exitMotionSensors) {
                section("Select if $app.label has a door to monitor?") {
                    input "monitoredDoor", "bool", title: "Monitor a door?", defaultValue: false, submitOnChange: true
                    if (monitoredDoor) {
                        input "doors", "capability.contactSensor", title: "Doors?", multiple: true, required: true, submitOnChange: true
                        if (doors) {
                            input "actualEntrySensorsTimeout", "number", title: "$app.label's timeout?",required: true, defaultValue: null, submitOnChange: true
                            input "actionOnDoorOpening", "bool", title: "Turn 'ON' something when\n$doors opens?", defaultValue: false, submitOnChange: true
                            if (actionOnDoorOpening) {
                                input "onlyIfAreaVacant", "bool", title: "But only if $app.label is vacant", defaultValue: true, submitOnChnage: true
                                input "doorOpeningAction", "capability.switchLevel", title: "Turn on?", multiple: true, required: true, submitOnChange: true
                                input "setLevelAt", "number", title: "Set level to? %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null
                                if (setLevelAt) {
                                    input "onlyDuringCertainTimes", "bool", title: "Only during certain times?", defaultValus: false, submitOnChange: true
                                    if (onlyDuringCertainTimes) {
                                        if (!onlyDuringNighttime && !fromTime) {
                                            input "onlyDuringDaytime", "bool", title: "Only during the daytime", defaultValue: false, submitOnChange: true
                                        	}
                                        if (!onlyDuringDaytime && !fromTime) {
                                            input "onlyDuringNighttime", "bool", title: "Only during the nighttime", defaultValue: false, submitOnChange: true
                                        	}
                                        if (!onlyDuringDaytime && !onlyDuringNighttime) {
                                            input "fromTime", "time", title: "From?", required: true, submitOnChange: true
                                            input "toTime", "time", title: "Until?", required: true
                                        	}
                                        input "anotherAction", "bool", title: "Another time schedule?", defaultValue: false, submitOnChange: true
                                        if (anotherAction) {
                                            input "onlyIfASensorIsActive", "bool", title: "But only if a sensor is active?", defaultValue: false, submitOnChange: true
                                            if (onlyIfASensorIsActive) {
                                                input "onlyIfThisSensorIsActive", "capability.motionSensor", title: "Only if this sensor is active!", multiple: true, required: true
                                            	}
                                            input "onlyIfAreaVacant2", "bool", title: "But only if $app.label is vacant", defaultValue: false, submitOnChnage: true
                                            input "doorOpeningAction2", "capability.switchLevel", title: "Turn on?", multiple: true, required: true, submitOnChange: true
                                            input "setLevelAt2", "number", title: "Set Level To? %", required: true, multiple: false, range: "1..100", submitOnChange: true
                                            if (!onlyDuringNighttime2 && !fromTime2) {
                                                input "onlyDuringDaytime2", "bool", title: "Only during the daytime", defaultValue: false, submitOnChange: true
                                            	}
                                            if (!onlyDuringDaytime2 && !fromTime2) {
                                                input "onlyDuringNighttime2", "bool", title: "Only during the nighttime", defaultValue: false, submitOnChange: true
                                            	}
                                            if (!onlyDuringDaytime2 && !onlyDuringNighttime2) {
                                                input "fromTime2", "time", title: "From?", required: true, submitOnChange: true
                                                input "toTime2", "time", title: "Until?", required: true
                                            	}
                                            input "turnOffAfter", "bool", title: "Turn off after set time?", defaultValue: false, submitOnChange: true
                                            if (turnOffAfter) {
                                                input "offAfter", "number", title: "Turn off after?", required: true, submitOnChange: true, defaultValue: null
                                            	}
                                        	}
                                   	 	}
                                	}
                                input "actionOnDoorClosing", "bool", title: "Turn off when\n$doors closes?", defaultValue: false, submitOnChange: true    
                            	}
                            }
                        }
                    }
                }
            if (exitMotionSensors && !switches) {
                section("Do you want 'Any' lights\nTo automatically turn 'ON'?") {
                    input "switchOnControl", "bool", title: "OCCUPIED 'ON' Control?", defaultValue: false, submitOnChange: true
                	}
                }
            if (switchOnControl) {
                section("Turn ON which lights\nwhen '$app.label' changes to 'OCCUPIED'") {
                    input "dimmableSwitches1", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
                    if (dimmableSwitches1) {
                        input "setLevelTo", "number", title: "Set level to %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null
                    	}
                    }
                }
            if (exitMotionSensors) {
                section("Do you want any lights\nto automatically turn 'OFF'?") {
                    input "offRequired", "bool", title: "VACANT 'OFF' Control?", defaultValue: false, submitOnChange: true
                	}
                }
            if (offRequired) {
                section("Only if different chosen areas are 'Vacant'?") {
                    input "otherAreaVacancyCheck", "bool", title: "Other area vacancy check", defaultValue: false, submitOnChange: true
                    if (otherAreaVacancyCheck) {
                        input "thisAreaMustBeVacant", "capability.estimatedTimeOfArrival", title: "What area?", defaultValue: null, multiple: false, required: false, submitOnChange: true
                    	}
                    }
                }
            if (offRequired) {
                section("Do you want any lights\nto turn 'OFF'\nafter $app.label changes to 'VACANT'") {
                    input "delayedOff", "bool", title: "Delayed Off?", defaultValue: false, submitOnChange: true
                    input "onlyDuringDaytime9", "bool", title: "Only during the daytime", defaultValue: false, submitOnChange: true
                	}
                }
            if (delayedOff) {
                section("Turn OFF which lights\nafter $app.label changes to 'VACANT'") {
                    input "switches2", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
                    if (switches2) {
                        input "dimByLevel", "number", title: "Reduce level by %\nbefore turning off!", required: false, multiple: false, range: "1..99", submitOnChange: false, defaultValue: null
                    	}
                    }
                }
            if (exitMotionSensors) {
                section("Do You Want To Automatically Switch\n$app.label's Automation 'ON' If It Was Disabled\nWhen Activation Of Certain Modes Occur?") {
                    input "resetAutomation", "bool", title: "Reset Automation On Mode Selection?", defaultValue: false, submitOnChange: true
                    if (resetAutomation) {
                        input "resetAutomationMode", "mode", title: "Select Your Automation Reset Modes?", required: true, multiple: true, submitOnChange: true
                    	}
                    }
                }
            if (exitMotionSensors) {
                section("Do You Require Switching $app.label To 'VACANT'\nOn Activation Of Your Away Modes") {
                    input "noAwayMode", "bool", title: "Auto Vacate When\nYour Away Modes Activate?", defaultValue: false, submitOnChange: true
                    if (noAwayMode) {
                        input "awayModes", "mode", title: "Select Your Away Modes?", required: true, multiple: true, submitOnChange: true
                    	}
                    }
                }
			if (exitMotionSensors) {
                section("Do You Require Switching $app.label To 'VACANT'\nIf Any Persons Presence Changes To Away?") {
                    input "presence", "bool", title: "Auto Vacate On\nAny Presence Change?", defaultValue: false, submitOnChange: true
                    if (presence) {
                        input "presenceSensors", "capability.presenceSensor", title: "Select Who? Leaving\nWill Activate 'VACANT'", required: true, multiple: true, submitOnChange: true
                    	}
                    }
                }
                section("Do Not Disturb Control") {
                if (entryMotionSensors && monitoredDoor && doors) {
                    input "donotdisturbControl", "bool", title: "Do Not Disturb Control?", submitOnChange: true
                    if (donotdisturbControl) {
                        paragraph "How Many Minutes Must $app.label\nStay Motionless While 'Engaged'\nBefore Activating 'Do Not Disturb'?"
                        input "dndCountdown", "number", title: "How Many Minutes?", required: true, submitOnChange: true
                    	}
                } else {
                    paragraph "Do Not Disturb Is Only Accessable In Conjunction With Entry Motion Sensors & A Monitored Door!"
                	}
                } 
            if (entryMotionSensors && doors) {
                section("Action On Engaged") {
                    input "actionOnEngaged", "bool", title: "Turn ON Something When\n$app.label Changes To Engaged?", defaultValue: false, submitOnChange: true
                    if (actionOnEngaged) {
                        input "engagedAction", "capability.switch", title: "Turn On?", multiple: true, required: true, submitOnChange: true
                    	}
                	}
            	}
            if (entryMotionSensors) {
                section("Action On Vacant") {
                    input "actionOnVacant", "bool", title: "Turn OFF Something When\n$app.label Changes To Vacant?", defaultValue: false, submitOnChange: true
                    if (actionOnVacant) {
                        input "vacantAction", "capability.switch", title: "Turn Off?", multiple: true, required: true, submitOnChange: true
                    	}
                	}
                section("Reset Entire Room On SHM Setting To Away?") {
                    input "resetOnSHMChangingToAway", "bool", title: "Reset Entire Area When SHM Sets To AWAY?", required: false, submitOnChange: false
                	}
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
                            	}
                            }
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
                            	}
                            }
                    	} 
                	} 
                section("Select ALL Of The Lights That Are In $app.label?") {
                    input "checkableLights", "capability.switch", title: "Lights?", required: true, multiple: true
                	}
            	}
        	}
    	}

def installed() {}
def updated() {
    unsubscribe()
    initialize()
    
    if (!childCreated()) {
        spawnChildDevice(app.label)
    	}
    if (noAwayMode || resetAutomation) {
        subscribe(location, modeEventHandler)
    	}
    if (checkableLights) {
        subscribe(checkableLights, "switch.on", checkableLightsSwitchedOnEventHandler)
        subscribe(checkableLights, "switch.off", checkableLightsSwitchedOffEventHandler)
    	}
    if (doors) {
        subscribe(doors, "contact.open", monitoredDoorOpenedEventHandler)
        subscribe(doors, "contact.closed", monitoredDoorClosedEventHandler)
    	}
    if (entryMotionSensors) { 
        subscribe(entryMotionSensors, "motion.active", entryMotionActiveEventHandler)
        subscribe(entryMotionSensors, "motion.inactive", entryMotionInactiveEventHandler)
    	}
    if (exitMotionSensors) { 
        subscribe(exitMotionSensors, "motion.inactive", exitMotionInactiveEventHandler)
    	}
    if (onlyIfDisarmed) {
        subscribe(location, "alarmSystemStatus", shmStatusEventHandler)
    	}
    if (presence) {
        subscribe(presenceSensors, "presence.not present", presenceAwayEventHandler)
   	    }    
    }
    
def initialize() {
    if (onAtThisTime) {
        schedule(onAtThisTime, turnOnAtThisTime)
    	}
    if (offAtThisTime) {
        schedule(offAtThisTime, turnOffAtThisTime)
    	}
    if (onAtSunriseChosen || offAtSunriseChosen) {
        subscribe(location, "sunrise", sunriseHandler)
    	}
    if (onAtSunsetChosen || offAtSunsetChosen) {
        subscribe(location, "sunset", sunsetHandler)
    	}
}
def uninstalled() {
    getChildDevices().each {
    deleteChildDevice(it.deviceNetworkId)
    }
}
def areaOccupancyVersion() {
    parent.areaOccupancyVersion()
}
def areaOccupancyDTHVersion() {
    def child = getChildDevice(getArea())
    child.DTHVersion()
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
    log.trace "${app.label} Area Has Been Uninstalled!"
}    
private getArea() {
    return "aa_${app.id}"
}
def spawnChildDevice(areaName) {
    app.updateLabel(app.label)
    if (!childCreated())
    def child = addChildDevice("Baz2473", "Area Occupancy Status", getArea(), null, [name: getArea(), label: areaName, completedSetup: true])
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


def checkableLightsSwitchedOnEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacant','vacantdimmed'].contains(areaState)) {
          child.generateEvent('vacanton')
          } else if (['vacantclosed','vacantdimmedclosed'].contains(areaState)) {
          			   child.generateEvent('vacantonclosed')
          			   } else if (['occupied'].contains(areaState)) {
                       			    child.generateEvent('occupiedon')
                       				} else if (['occupiedmotion'].contains(areaState)) {
                                   			     child.generateEvent('occupiedonmotion')
   								    			 } else if (['engaged'].contains(areaState)) {
       											 			  child.generateEvent('engagedon')
   												  			  } else if (['engagedmotion'].contains(areaState)) {
     														  			   child.generateEvent('engagedonmotion')
   															  			   } else if (['checking'].contains(areaState)) {
       																	    			child.generateEvent('checkingon')
   																		    			} else if (['donotdisturb'].contains(areaState)) {
       																								 child.generateEvent('donotdisturbon')
    																					 			 }
}

def checkableLightsSwitchedOffEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def checkableLightsState = checkableLights.currentState("switch")
    if (['vacanton','vacantdimmed'].contains(areaState) && !checkableLightsState.value.contains("on")) {
       	  child.generateEvent('vacant')
    	  } else if (['vacantonclosed','vacantdimmedclosed'].contains(areaState) && !checkableLightsState.value.contains("on")) {
       	  			   child.generateEvent('vacantclosed')
    	 			   } else if (['occupiedon'].contains(areaState) && !checkableLightsState.value.contains("on")) {
        			  			    child.generateEvent('occupied')
   					  			    } else if (['occupiedonmotion'].contains(areaState) && !checkableLightsState.value.contains("on")) {
       							  			     child.generateEvent('occupiedmotion')
    							   			     } else if (['engagedon'].contains(areaState) && !checkableLightsState.value.contains("on")) {
       										 			      child.generateEvent('engaged')
   											     			  } else if (['engagedonmotion'].contains(areaState) && !checkableLightsState.value.contains("on")) {
      													   			       child.generateEvent('engagedmotion')
    																	   } else if (['checkingon'].contains(areaState) && !checkableLightsState.value.contains("on")) {
       																			    	child.generateEvent('checking')
   																		  			    } else if (['donotdisturbon'].contains(areaState) && !checkableLightsState.value.contains("on")) {
       																		   		   			     child.generateEvent('donotdisturb')
   																					    			 }
}

/////////////////////////////////////////// THESE DEF'S ARE USED IN runIn() FUNCTIONS //////////////////////////////////////////////

def donotdisturb() {
    def child = getChildDevice(getArea())
    def lightsState = checkableLights.currentState("switch")
    if (lightsState.value.contains("on")) {
        child.generateEvent('donotdisturbon')
    } else {
            child.generateEvent('donotdisturb')
    }
} 

def doaoff() {
    doorOpeningAction2.each {
        it.setLevel(0)
        it.off()
    }
} 

def engaged() {
    def child = getChildDevice(getArea())
    def lightsState = checkableLights.currentState("switch")
    if (lightsState.value.contains("on")) {
        child.generateEvent('engagedonmotion')
    } else {
            child.generateEvent('engagedmotion')
    }
    def automationState = child.getAutomationState()
    if (switchOnControl && ['automationon'].contains(automationState)) {
        dimmableSwitches1.each {
        						def currentLevel = it.currentValue("level")
        						if (currentLevel < setLevelTo) {
            						if (onlyIfDisarmed) {
                						def shmStatus = location.currentState("alarmSystemStatus")?.value
                						if (shmStatus == "off") {
                   						    it.setLevel(setLevelTo)
                    						log.trace "Setting level of $it to $setLevelTo %"
                						}
            						} else {
                    						it.setLevel(setLevelTo)
                    						log.trace "Setting level of $it to $setLevelTo %"
            						}
        						} else if (it.currentValue("switch") == 'off') {
                   						   it.on()
                   						   log.trace "Level previously set... Switching on $it"
        						}
        }
    }
    if (actionOnEngaged) {
        engagedAction.on()
    }
} 
/////////////////////////////////////////// END OF THE DEF'S USED IN runIn() FUNCTIONS //////////////////////////////////////////////

def entryMotionActiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['occupiedon','vacanton','vacantdimmed'].contains(areaState)) {
        child.generateEvent('occupiedonmotion')
    }
    if (['occupied','vacant'].contains(areaState)) {
        child.generateEvent('occupiedmotion')
    }
    if (['engagedon'].contains(areaState)) {
        child.generateEvent('engagedonmotion')
        unschedule(donotdusturb)
    }
    if (['engaged'].contains(areaState)) {
        child.generateEvent('engagedmotion')
        unschedule(donotdusturb)
    }
    if (doors) {
        def doorsState = doors.currentState("contact")
        if (!doorsState.value.contains("open") && !['engaged','engagedon','engagedonmotion'].contains(areaState)) {
        	if (now() < (state.stateChangedAt + 10000)) {
            	engaged()
        	} else {
            		log.debug("now is ${now()} but last state change + 10000 was at ${state.stateChangedAt + 10000}")
            }
    	}
    }
    def automationState = child.getAutomationState()
    if (switchOnControl && ['automationon'].contains(automationState)) {
        dimmableSwitches1.each {
        						def currentLevel = it.currentValue("level")
        						if (currentLevel < setLevelTo) {
            						if (onlyIfDisarmed) {
                						def shmStatus = location.currentState("alarmSystemStatus")?.value
                						if (shmStatus == "off") {
                    						it.setLevel(setLevelTo)
                    						log.trace "Setting level of $it to $setLevelTo %"
                						}
            						} else {
                   							it.setLevel(setLevelTo)
                   							log.trace "Setting level of $it to $setLevelTo %"
                   					}
        						} else if (it.currentValue("switch") == 'off') {
                   						   it.on()    
                   						   log.trace "Level previously set... Switching on $it"                    
                				}
         }
     }
}

def entryMotionInactiveEventHandler(evt) {
	def exitMotionState = exitMotionSensors.currentState("motion")
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!entryMotionState.value.contains("active")) {
        def child = getChildDevice(getArea())
        def areaState = child.getAreaState()
        if (['occupiedmotion'].contains(areaState)) {
        	  if (exitMotionState.value.contains("active")) {
                  child.generateEvent('vacant')
                  return
              } else {
                      child.generateEvent('occupied')
                      return
              }
        }
        if (['checking'].contains(areaState)) {
              unschedule(engaged)
              child.generateEvent('vacantclosed')
              return
        }
        if (['engagedonmotion'].contains(areaState)) {
              child.generateEvent('engagedon')
              if (donotdisturbControl) {
                  runIn(dndCountdown * 60, donotdisturb)
              }
              return
        }
        if (['engagedmotion'].contains(areaState)) {
              child.generateEvent('engaged')
              if (donotdisturbControl) {
                  runIn(dndCountdown * 60, donotdisturb)
              }
              return
        }
        if (['occupiedonmotion'].contains(areaState)) {
               if (exitMotionState.value.contains("active")) {
                   def automationState = child.getAutomationState()
                   if (offRequired && ['automationon'].contains(automationState)) {
                       if (thisAreaMustBeVacant) {
                           def thisAreaState = thisAreaMustBeVacant.currentState("occupancyStatus")
                           if (thisAreaState.value.contains("vacant") || thisAreaState.value.contains("vacantclosed") || thisAreaState.value.contains("vacanton") || thisAreaState.value.contains("vacantonclosed") || thisAreaState.value.contains("vacantdimmed") || thisAreaState.value.contains("vacantdimmedclosed")) {
                               if (onlyDuringDaytime9) {
                                   def s = getSunriseAndSunset()
                                   def sunrise = s.sunrise.time
                                   def sunset = s.sunset.time
                                   def timenow = now()
                                   if (timenow > sunrise && timenow < sunset) {
                                       child.generateEvent('vacantdimmed')
									   switches2.each {
       												  def currentLevel = it.currentValue("level")
       												  if (currentLevel > dimByLevel) {
            											  def newLevel = (currentLevel - dimByLevel)
           												  it.setLevel(newLevel)
           												  log.trace "The $it have been dimmed to $newLevel %"
        											  }
    								   }   
    								} else {
                                            child.generateEvent('vacanton')   
                                    }
                               } else {
                                       child.generateEvent('vacantdimmed')
						  		       switches2.each {
       								   			      def currentLevel = it.currentValue("level")
        								  		   	  if (currentLevel > dimByLevel) {
            								 	 	 	  def newLevel = (currentLevel - dimByLevel)
            								  		 	  it.setLevel(newLevel)
           									  			  log.trace "The $it have been dimmed to $newLevel %"
        								  			  }
    								   } 
    						   }
                            } else {
                                    child.generateEvent('vacanton')
                            }
                        } else {
                            	if (onlyDuringDaytime9) {
                               	    def s = getSunriseAndSunset()
                                	def sunrise = s.sunrise.time
                                	def sunset = s.sunset.time
                                	def timenow = now()
                                	if (timenow > sunrise && timenow < sunset) {
                                        child.generateEvent('vacantdimmed')
										switches2.each {
       												   def currentLevel = it.currentValue("level")
        											   if (currentLevel > dimByLevel) {
           												   def newLevel = (currentLevel - dimByLevel)
            											   it.setLevel(newLevel)
            											   log.trace "The $it have been dimmed to $newLevel %"
        											   }
    									}  
    								} else {
                                            child.generateEvent('vacanton')
                                    }
                                } else {
                                       child.generateEvent('vacantdimmed')
									   switches2.each {
        											  def currentLevel = it.currentValue("level")
      												  if (currentLevel > dimByLevel) {
           												  def newLevel = (currentLevel - dimByLevel)
           												  it.setLevel(newLevel)
           												  log.trace "The $it have been dimmed to $newLevel %"
       					 							  }
    								  }       
    						   }
                        }
                    } else {
                            child.generateEvent('vacanton')
                    }
               } else {
                       child.generateEvent('occupiedon')
               }
        }
        if (['checkingon'].contains(areaState)) {
              unschedule(engaged)
              def automationState = child.getAutomationState()
              if (offRequired && ['automationon'].contains(automationState)) {
                  if (onlyDuringDaytime9) {
              	      def s = getSunriseAndSunset()
                      def sunrise = s.sunrise.time
                      def sunset = s.sunset.time
                      def timenow = now()
                      if (timenow > sunrise && timenow < sunset) {
                          child.generateEvent('vacantdimmedclosed')
						  switches2.each {
       									 def currentLevel = it.currentValue("level")
      									 if (currentLevel > dimByLevel) {
            								 def newLevel = (currentLevel - dimByLevel)
           									 it.setLevel(newLevel)
           									 log.trace "The $it have been dimmed to $newLevel %"
        								 }
   				 		  }  
    				   } else {
                               child.generateEvent('vacantonclosed')
                       }
                  } else {
                          child.generateEvent('vacantdimmedclosed')
						  switches2.each {
        								 def currentLevel = it.currentValue("level")
        								 if (currentLevel > dimByLevel) {
          									 def newLevel = (currentLevel - dimByLevel)
            								 it.setLevel(newLevel)
           									 log.trace "The $it have been dimmed to $newLevel %"
        								 }
    					  }   
    			  }
              } else {
                      child.generateEvent('vacantonclosed')
              }
         }
     }
}

def exitMotionInactiveEventHandler(evt) {
    def exitMotionState = exitMotionSensors.currentState("motion")
    if (!exitMotionState.value.contains("active")) {
    	def child = getChildDevice(getArea())
    	def areaState = child.getAreaState()
   	    def automationState = child.getAutomationState()
    	if (doors) {
        	def doorsState = doors.currentState("contact")
        	if (!doorsState.value.contains("open") && !['vacantdimmedclosed','vacantonclosed'].contains(areaState)) {
				 return
			} else {
           	  	    if (offRequired && ['vacantdimmed','vacantdimmedclosed'].contains(areaState) && ['automationon'].contains(automationState)) {
						switches2.each {
     			    	it.setLevel(0)
       			    	log.trace "The $it are now off"
    					}
           			}
        	}
    	} else if (offRequired && ['vacantdimmed'].contains(areaState) && ['automationon'].contains(automationState)) {
				   switches2.each {
       		   	   it.setLevel(0)
       		       log.trace "The $it are now off"
   			   	   }
        } 
    }
}

def forceTurnAllOff() {
    log.trace "The Alarm Is Now Set So Performing All Full Reset Of $app.label!"
    checkableLights.each {
        if (it.hasCommand("setLevel")) {
            it.setLevel(0)
        } else {
            it.off()
        }
    }
    log.trace "Generating VACANT Event!"
    def child = getChildDevice(getArea())
    child.generateEvent('vacant')
    unschedule()
    log.trace "All Scheduled Jobs Have Been Cancelled!"
    log.trace "Generating AUTOMATION ON Event!"
    child.generateAutomationEvent('automationon')
}



def leftHome() {
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    if (['automationon'].contains(automationState)) {
        log.trace "$app.label was set to 'VACANT' because the mode changed to away"
        child.generateEvent('vacant')
		switches2.each {
       				    it.setLevel(0)
       				    log.trace "The $it are now off"
    	}    				
    }
}

def modeEventHandler(evt) {
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    if (resetAutomation && resetAutomationMode && resetAutomationMode.contains(evt.value) && ['automationoff'].contains(automationState)) {
        log.trace "$app.label's Automation Has Been Enabled Because Your Reset Mode Was ACTIVATED!"
        child.generateAutomationEvent('automationon')
    }
    if (awayModes && awayModes.contains(evt.value) && noAwayMode) {
        log.trace "$app.label Was Set To 'VACANT' Because Your Away Mode Was 'ACTIVATED'!"
        leftHome()
    }
}

def monitoredDoorOpenedAction() {
    doorOpeningAction.each {
        it.setLevel(setLevelAt)
        log.trace "Setting level of $it to $setLevelAt %"
    }
} 

def monitoredDoorOpenedAction2() {
    doorOpeningAction2.each {
        it.setLevel(setLevelAt2)
        log.trace "Setting level of $it to $setLevelAt2 %"
    }
}

def monitoredDoorOpenedEventHandler(evt) {
    unschedule(engaged)
    unschedule(donotdisturb)
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacantclosed'].contains(areaState)) {
    	  child.generateEvent('vacant')
    }
    if (['checking','checkingon','engaged','engagedmotion','engagedon','engagedonmotion','donotdisturb','donotdisturbon'].contains(areaState)) {
    	if (actionOnVacant) {
        	if (['engaged','engagedon','engagedmotion','engagedonmotion'].contains(areaState)) {
            	vacantAction.off()
        	}
    	}
    	def lightsState = checkableLights.currentState("switch")
    	if (lightsState.value.contains("on")) {
        	child.generateEvent('occupiedonmotion')
    	} else {
           	    child.generateEvent('occupiedmotion')
    	}
    }
    if (actionOnDoorOpening) {
        if (!onlyDuringDaytime && !onlyDuringNighttime && !onlyDuringDaytime2 && !onlyDuringNighttime2 && !onlyDuringCertainTimes) {
            monitoredDoorOpenedAction()
        }
        if (onlyDuringCertainTimes && (onlyDuringDaytime || onlyDuringNighttime || onlyDuringDaytime2 || onlyDuringNighttime2)) {
            def s = getSunriseAndSunset()
            def sunrise = s.sunrise.time
            def sunset = s.sunset.time
            def timenow = now()
            if (onlyDuringDaytime) {
                if (timenow > sunrise && timenow < sunset) {
                    if (onlyIfAreaVacant) {
                        if (['vacant','vacantclosed'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction()
                        }
                    } else {
                        monitoredDoorOpenedAction()
                    }
                }
            }
            if (onlyDuringNighttime) {
                if (timenow > sunset || timenow < sunrise) {
                    if (onlyIfAreaVacant) {
                        if (['vacant','vacantclosed'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction()
                        }
                    } else {
                        monitoredDoorOpenedAction()
                    }
                }
            }
            if (onlyDuringDaytime2) {
                if (timenow > sunrise && timenow < sunset) {
                    if (onlyIfAreaVacant2) {
                        if (['vacant','vacantclosed'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction2()
                        }
                    } else {
                        monitoredDoorOpenedAction2()
                    }
                }
            }
            if (onlyDuringNighttime2) {
                if (timenow > sunset || timenow < sunrise) {
                    if (onlyIfAreaVacant2) {
                        if (['vacant','vacantclosed'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction2()
                        }
                    } else {
                        monitoredDoorOpenedAction2()
                    }
                }
            }
        } 
        if (!onlyDuringDaytime && !onlyDuringNighttime && !onlyDuringDaytime2 && !onlyDuringNighttime2 && onlyDuringCertainTimes) {
            def between = timeOfDayIsBetween(fromTime, toTime, new Date(), location.timeZone)
            log.trace "Between = $between"
            def between2 = timeOfDayIsBetween(fromTime2, toTime2, new Date(), location.timeZone)
            log.trace "Between2 = $between2"
            if (between) {
                if (onlyIfAreaVacant) {
                    if (['vacant','vacantclosed'].contains(areaState)) {
                        log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                        monitoredDoorOpenedAction()
                    }
                } else {
                    monitoredDoorOpenedAction()
                }
            } 
            if (between2) {
                if (onlyIfASensorIsActive) {
                    def theMotionState = onlyIfThisSensorIsActive.currentState("motion")
                    if (theMotionState.value.contains("active")) {
                        if (onlyIfAreaVacant2) {
                            if (['vacant','vacantclosed'].contains(areaState)) {
                                log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                                monitoredDoorOpenedAction2()
                            }
                        } else {
                            monitoredDoorOpenedAction2()
                        }
                    }
                } else {
                    if (onlyIfAreaVacant2) {
                        if (['vacant','vacantclosed'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction2()
                        }
                    } else {
                        monitoredDoorOpenedAction2()
                    }
                }
            }
            
        }
        
    }
    
} 

def monitoredDoorClosedEventHandler(evt) {
	def doorsState = doors.currentState("contact")
    if (!doorsState.value.contains("open")) {
    	def child = getChildDevice(getArea())
    	def areaState = child.getAreaState()
   		if (['vacant'].contains(areaState)) {
              child.generateEvent('vacantclosed')
        }
    	if (['occupiedmotion'].contains(areaState)) {
       	      child.generateEvent('checking')
    		  runIn(actualEntrySensorsTimeout, engaged)
   	 	}
   		if (['occupiedonmotion'].contains(areaState)) {
    	      child.generateEvent('checkingon')
    	      runIn(actualEntrySensorsTimeout, engaged)
        }
        if (turnOffAfter) {
            log.trace "Turn Off After Was True So The Lights Should Go Off In $offAfter Seconds"
            runIn(offAfter, doaoff, [overwrite: false])
        }
        if (actionOnDoorClosing) {
            log.trace "The Light Was Turned OFF Because The Door Was Closed"
            doorOpeningAction.each {
                			        it.setLevel(0)
            			       	    }
    	}
    }
}

def presenceAwayEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!['vacant'].contains(areaState) && !entryMotionState.value.contains("active")) {
    	   checkableLights.each {
       						     if (it.hasCommand("setLevel")) {
           						     it.setLevel(0)
        						 } else {
           							     it.off()
        						 }
    	   }
    child.generateEvent('vacant')
    } 
}

def shmStatusEventHandler(evt) {
    def shmStatus = location.currentState("alarmSystemStatus")?.value
    if (shmStatus == "away") {
        if (resetOnSHMChangingToAway) {
            log.trace "The Alarm Is Now ARMED AWAY! & You Requested A Full Reset..."
            forceTurnAllOff()
        }
    }
}



def sunriseHandler(evt) {
    log.trace "The Sun has risen! Performing Any Sunrise Actions"
    if (onAtSunriseChosen) {
        switchToTurnOnAtThisTime.on()
    }
    if (offAtSunriseChosen) {
        switchToTurnOffAtThisTime.off()
    }
}

def sunsetHandler(evt) {
    log.trace "The Sun has set! Performing Any Sunset Actions"
    if (onAtSunsetChosen) {
        switchToTurnOnAtThisTime.on()
    }
    if (offAtSunsetChosen) {
        switchToTurnOffAtThisTime.off()
    }
}

def turnOffAtThisTime() {
    switchOffAtThisTime.each {
        it.off()
    }
}

def turnOnAtThisTime() {
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    switchOnAtThisTime.each {
        					 if (['automationon'].contains(automationState)) {
            					   it.on()
        					 }
    }
}

def turnalloff() {
    def child = getChildDevice(getArea())
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!entryMotionState.value.contains("active")) {
         if (doors) {
             def doorsState = doors.currentState("contact")
             if (!doorsState.value.contains("open")) {
                  child.generateEvent('vacantclosed')
             } else {
                     child.generateEvent('vacant')
             }
      	 } else {
               	 child.generateEvent('vacant')
         }
         checkableLights.each {
                			  if (it.hasCommand("setLevel")) {
                    			  it.setLevel(0)
                			  } else {
                    				  it.off()
                			  }
         }
     }
}

def turnon() {
    log.trace "You Have Told Me To Turn On All Lights in $app.label"
    checkableLights.each {
      			         if (it.hasCommand("setLevel")) {
                			 it.setLevel(75)
            			 } else {
                				 it.on()
            			 }
    }
}

def stateChanged() {
	state.stateChangedAt = now()
}
