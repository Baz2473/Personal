/*
 Copyright (C) 2017 Baz2473
 Name: Area Occupancy Child App
 */

public static String areaOccupancyChildAppVersion() {
    return "v5.1.1.0"
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
            section("Does $app.label have any monitored doors by another area?") {
                input "monitoredDoor2", "bool", title: "Other monitored doors?", defaultValue: false, submitOnChange: true
            }
        }
        if (monitoredDoor2) {
            section("How many doors are in $app.label ?") {
                input name: "numberOfMonitoredDoors", type: "enum", title: "Number of monitored doors", options: ["1","2"], defaultValue: "null", required: true, submitOnChange: true
            }}
        if (numberOfMonitoredDoors == "1" || numberOfMonitoredDoors == "2") {
            section("Select the first door\nTo be monitored in '$app.label'?") {
                input "adjacentDoor1", "capability.contactSensor", title: "Which door?", multiple: false, required: true, submitOnChange: true
            }}
        if (numberOfMonitoredDoors == "1" && adjacentDoor1) {
            section("Motion sensors when $adjacentDoor1 is 'Open'\n'Outside' of $app.label") {
                input "exitMotionSensorsWhenDoorIsOpen", "capability.motionSensor", title: "'$adjacentDoor1 'OPEN' exit sensors?", required: true, multiple: true, submitOnChange: true
            }}
        if (numberOfMonitoredDoors == "1" && adjacentDoor1 && exitMotionSensorsWhenDoorIsOpen) {
            section("Motion sensors when $adjacentDoor1 is 'Closed'\n'Outside' of $app.label") {
                input "exitMotionSensorsWhenDoorIsClosed", "capability.motionSensor", title: "'$adjacentDoor1 'CLOSED' exit sensors?", required: true, multiple: true, submitOnChange: true
            }}
        if (numberOfMonitoredDoors == "2") {
            section("Select the second door\nTo be monitored in '$app.label'?") {
                input "adjacentDoor2", "capability.contactSensor", title: "Which door?", multiple: false, required: true, submitOnChange: true
            }}
            if (adjacentDoor2) {
                if (numberOfMonitoredDoors == "2") {
                    section("Motion sensors when $adjacentDoor1 & $adjacentDoor2 are both 'Open'\n'Outside' of $app.label") {
                        input "exitMotionSensorsWhenDoor1N2AreOpen", "capability.motionSensor", title: "'$adjacentDoor1 & $adjacentDoor2 'OPEN' exit sensors?", required: true, multiple: true, submitOnChange: true
                    }}
                if (numberOfMonitoredDoors == "2" && exitMotionSensorsWhenDoor1N2AreOpen) {
                    section("Motion sensors when $adjacentDoor1 & $adjacentDoor2 are both 'Closed'\n'Outside' of $app.label") {
                        input "exitMotionSensorsWhenDoor1N2AreClosed", "capability.motionSensor", title: "'$adjacentDoor1 & $adjacentDoor2 'CLOSED' exit sensors?", required: true, multiple: true, submitOnChange: true
                    }}
                if (numberOfMonitoredDoors == "2" && exitMotionSensorsWhenDoor1N2AreOpen && exitMotionSensorsWhenDoor1N2AreClosed) {
                    section("Motion sensors when $adjacentDoor1 is 'OPEN' & $adjacentDoor2 Is 'Closed'\n'Outside' of $app.label") {
                        input "exitMotionSensorsWhenDoor1IsOpen2IsClosed", "capability.motionSensor", title: "'$adjacentDoor1 'OPEN' $adjacentDoor2 'CLOSED' exit sensors?", required: true, multiple: true, submitOnChange: true
                    }}
                if (numberOfMonitoredDoors == "2" && exitMotionSensorsWhenDoor1N2AreOpen && exitMotionSensorsWhenDoor1N2AreClosed && exitMotionSensorsWhenDoor1IsOpen2IsClosed) {
                    section("Motion sensors when $adjacentDoor2 is 'OPEN' & $adjacentDoor1 is 'Closed'\n'Outside' of $app.label") {
                        input "exitMotionSensorsWhenDoor2IsOpen1IsClosed", "capability.motionSensor", title: "'$adjacentDoor2 'OPEN' $adjacentDoor1 'CLOSED' exit sensors?", required: true, multiple: true, submitOnChange: true
                    }}}
            if (entryMotionSensors && !doors2 && !monitoredDoor2) {
                section("Select the motion sensors 'Outside' $app.label") {
                    input "exitMotionSensors", "capability.motionSensor", title: "Exit sensors?", required: true, multiple: true, submitOnChange: true
                }}
            if (entryMotionSensors) {
                section("Use the status change of another area to force a check on $app.label's State?") {
                    input "otherAreaCheck", "bool", title: "Other area occupancy changes?", defaultValue: false, submitOnChange: true
                    if (otherAreaCheck) {
                        input "otherArea", "capability.estimatedTimeOfArrival", title: "Subscribe to this area's occupancy change?", required: true, multiple: true, submitOnChange: true
                    }}}
            if (entryMotionSensors) {
                section("Perform another vacancy check if $app.label is 'Occupied' and motionless?") {
                    input "anotherVacancyCheck", "bool", title: "Another vacancy check?", defaultValue: false, submitOnChange: true
                    if (anotherVacancyCheck) {
                        input "anotherCheckIn", "number", title: "How many seconds 'After' inactivity\n(Only checks when $app.label is 'Occupied')", required: true, submitOnChange: true, range: "actualEntrySensorsTimeout..99999"
                    }}}
            if (exitMotionSensors || monitoredDoor2) {
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
            if ((exitMotionSensors || monitoredDoor2) && !switches) {
                section("Do you want 'Any' lights\nTo automatically turn 'ON'?") {
                    input "switchOnControl", "bool", title: "OCCUPIED 'ON' Control?", defaultValue: false, submitOnChange: true
                	}
                }
            if (switchOnControl) {
                section("Turn ON which dimmable lights?\nwhen '$app.label' changes to 'OCCUPIED'") {
                    input "dimmableSwitches1", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
                    if (dimmableSwitches1) {
                        input "setLevelTo", "number", title: "Set level to %", required: true, multiple: false, range: "1..100", submitOnChange: true, defaultValue: null
                    	}
                    }
                }
            if (exitMotionSensors || monitoredDoor2) {
                section("Do you want any lights\nto automatically turn 'OFF'?") {
                    input "offRequired", "bool", title: "VACANT 'OFF' Control?", defaultValue: false, submitOnChange: true
                	}
                }
            if (offRequired) {
                section("Only if different chosen areas are 'Vacant'?") {
                    input "otherAreaVacancyCheck", "bool", title: "Other area vacancy check", defaultValue: false, submitOnChange: true
                    if (otherAreaVacancyCheck) {
                        input "thisArea", "capability.estimatedTimeOfArrival", title: "What area?", defaultValue: null, multiple: false, required: false, submitOnChange: true
                    	}
                    }
                }
            if (offRequired) {
                section("Do you want 'Any' lights\nto instantly turn 'OFF'!!\nwhen $app.label changes to Vacant?") {
                    input "instantOff", "bool", title: "Instant Off!!", defaultValue: false, submitOnChange: true
               	 	}
                }
            if (instantOff) {
                section("Turn OFF which lights\nIMMEDIATELY when $app.label changes to 'VACANT'") {
                    input "switches3", "capability.switchLevel", title: "Lights?", required: true, multiple: false, submitOnChange: true
                	}
                }
            if (offRequired) {
                section("Do you want any lights\nto turn 'OFF' with a delay?\nafter $app.label changes to 'VACANT'") {
                    input "delayedOff", "bool", title: "Delayed Off?", defaultValue: false, submitOnChange: true
                    input "onlyDuringDaytime9", "bool", title: "Only during the daytime", defaultValue: false, submitOnChange: true
                	}
                }
            if (delayedOff) {
                section("Turn OFF which lights\nwith a delay,\nafter $app.label changes to 'VACANT'") {
                    input "switches2", "capability.switchLevel", title: "Lights?", required: true, multiple: true, submitOnChange: true
                    if (switches2) {
                        input "dimByLevel", "number", title: "Reduce level by %\nbefore turning off!", required: false, multiple: false, range: "1..99", submitOnChange: false, defaultValue: null
                    	}
                    }
                }
            if (exitMotionSensors || monitoredDoor2) {
                section("Do You Want To Automatically Switch\n$app.label's Automation 'ON' If It Was Disabled\nWhen Activation Of Certain Modes Occur?") {
                    input "resetAutomation", "bool", title: "Reset Automation On Mode Selection?", defaultValue: false, submitOnChange: true
                    if (resetAutomation) {
                        input "resetAutomationMode", "mode", title: "Select Your Automation Reset Modes?", required: true, multiple: true, submitOnChange: true
                    	}
                    }
                }
            if (exitMotionSensors || monitoredDoor2) {
                section("Do You Require Switching $app.label To 'VACANT'\nOn Activation Of Your Away Modes") {
                    input "noAwayMode", "bool", title: "Auto Vacate When\nYour Away Modes Activate?", defaultValue: false, submitOnChange: true
                    if (noAwayMode) {
                        input "awayModes", "mode", title: "Select Your Away Modes?", required: true, multiple: true, submitOnChange: true
                    	}
                    }
                }
            if (exitMotionSensors || monitoredDoor2) {
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
    if (awayModes && noAwayMode || resetAutomationMode && resetAutomation) {
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
    if (numberOfMonitoredDoors == "1") {
        subscribe(exitMotionSensorsWhenDoorIsOpen, "motion.inactive", exitMotionSensorsWhenDoorIsOpenInactiveEventHandler)
        subscribe(exitMotionSensorsWhenDoorIsClosed, "motion.inactive", exitMotionSensorsWhenDoorIsClosedInactiveEventHandler)
    }
    if (numberOfMonitoredDoors == "2") {
        subscribe(exitMotionSensorsWhenDoor1N2AreOpen, "motion.inactive", emswd1N2aboEventHandler)
        subscribe(exitMotionSensorsWhenDoor1N2AreClosed, "motion.inactive", emswd1N2abcEventHandler)
        subscribe(exitMotionSensorsWhenDoor1IsOpen2IsClosed, "motion.inactive", emswd1o2cEventHandler)
        subscribe(exitMotionSensorsWhenDoor2IsOpen1IsClosed, "motion.inactive", emswd2o1cEventHandler)
    }
    if (onlyIfDisarmed) {
        subscribe(location, "alarmSystemStatus", shmStatusEventHandler)
    }
    if (otherArea && otherAreaCheck) { 
        subscribe(otherArea, "occupancyStatus.vacant", otherAreaOccupancyStatusEventHandler)
    }
    if (presenceSensors && presence) {
        subscribe(presenceSensors, "presence.not present", presenceAwayEventHandler)
   	    }
    }
    
def initialize() {
    
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

//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
def mainAction() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def automationState = child.getAutomationState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (entryMotionState.value.contains("active")) {
        log.trace "474 mainAction() Running ---- Entry motion is Active"
        if (dimmableSwitches1 && switchOnControl && ['automationon'].contains(automationState)) {
            dimmableSwitches1.each {
            def currentLevel = it.currentValue("level")
            if (currentLevel < setLevelTo) {
                if (onlyIfDisarmed) {
                    def shmStatus = location.currentState("alarmSystemStatus")?.value
                    if (shmStatus == "off") {
                        it.setLevel(setLevelTo)
                        log.trace "483 Setting level of $it to $setLevelTo %"
                    } else {
                           log.trace "485 SHM is ARMED! No Lights Will Turn On!"
                           }
                } else {
                       it.setLevel(setLevelTo)
                       log.trace "489 Setting level of $it to $setLevelTo %"
                       }
            } else if (it.currentValue("switch") == 'off') {
                       it.on()
                       log.trace "493 Level previously set... Switching on $it"
                      }
                }
          }
          if (doors) {
              def doorsState = doors.currentState("contact")
              if (!doorsState.value.contains("open") && ['occupiedmotion','occupiedonmotion'].contains(areaState)) {
                   checking()
              } else if (doorsState.value.contains("open") && ['checking','checkingon','engaged','engagedmotion','engagedon','engagedonmotion','donotdisturb','donotdisturbon'].contains(areaState)) {
                         occupied()
                         } else if (['vacant','vacantdimmed','vacanton'].contains(areaState)) {
                                      occupied()
                                      }
          } else if (['vacant','vacantdimmed','vacanton'].contains(areaState)) {
                       occupied()
                     }
            
 //-----------------------------------------------------------------------INACTIVE FROM HERE DOWN---------------------------------------------------------------------------------------------
            
   } else {
          log.trace "514 mainAction() Running ---- Entry motion is Inactive"
            
          if (donotdisturbControl && ['engaged','engagedon'].contains(areaState)) {
              if (!atomicState.dnd) {
                   atomicState.dnd = true
                   runIn(dndCountdown * 60, donotdisturb)
                   }
          }
          if (exitMotionSensors && ['occupied','occupiedon'].contains(areaState)) {
              def ems = exitMotionSensors.currentState("motion")
              if (ems.value.contains("active")) {
                  vacant()
                  }
          }  
          if (numberOfMonitoredDoors == "2" && ['occupied','occupiedon'].contains(areaState)) {
              def adjacentDoor1State = adjacentDoor1.currentValue("contact")
              def adjacentDoor2State = adjacentDoor2.currentValue("contact")   
              if (adjacentDoor1State.contains("open") && adjacentDoor2State.contains("open")) {
                  def emsD1oND2o = exitMotionSensorsWhenDoor1N2AreOpen.currentValue("motion")
                  if (emsD1oND2o.contains("active")) {
                      vacant()
                      }
              } else if (!adjacentDoor1State.contains("open") && !adjacentDoor2State.contains("open")) {
                          def emsD1cND2c = exitMotionSensorsWhenDoor1N2AreClosed.currentValue("motion")
                          if (emsD1cND2c.contains("active")) {
                              vacant()
                             }
              } else if (adjacentDoor1State.contains("open") && !adjacentDoor2State.contains("open")) {
                         def emsD1oND2c = exitMotionSensorsWhenDoor1IsOpen2IsClosed.currentValue("motion")
                         if (emsD1oND2c.contains("active")) {
                             vacant()
                            }
              } else if (!adjacentDoor1State.contains("open") && adjacentDoor2State.contains("open")) {
                          def emsD1cND2o = exitMotionSensorsWhenDoor2IsOpen1IsClosed.currentValue("motion")
                          if (emsD1cND2o.contains("active")) {
                              vacant()
                             }
              }
           }
           if (numberOfMonitoredDoors == "1" && ['occupied','occupiedon'].contains(areaState) && !exitMotionSensors) {
               def adjacentDoor1State = adjacentDoor1.currentValue("contact")
               if (adjacentDoor1State.contains("open")) {
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
                  vacant()
            } else {
                    if (anotherVacancyCheck && anotherCheckIn && ['occupied','occupiedon'].contains(areaState)) {
                        if (!atomicState.ffi) {
                            log.trace "558 forceVacantIf will be activated in $anotherCheckIn seconds"
                            atomicState.ffi = true
                            runIn(anotherCheckIn, forceVacantIf)
                           }
                    }
                    if (switches3 && instantOff && offRequired) {
                        def switches3State = switches3.currentState("switch")
                        if (switches3State.value.contains("on") && ['vacanton'].contains(areaState) && ['automationon'].contains(automationState)) {
                            if (thisArea && !andThisArea) {
                                def thisAreaState = thisArea.currentState("occupancyStatus")
                                if (thisAreaState.value.contains("vacant")) {
                                    switches3.off()
                                } else {
                                        log.trace "555 Doing Nothing Because The $thisArea Is Still Occupied"
                                       }
                            } else {
                                    if (thisArea && andThisArea) {
                                        def thisAreaState = thisArea.currentState("occupancyStatus")
                                        def andThisAreaState = andThisArea.currentState("occupancyStatus")
                                        if (thisAreaState.value.contains("vacant") && andThisAreaState.value.contains("vacant")) {
                                            switches3.off()
                                        } else {
                                                log.trace "582 Doing Nothing Because 1 Of Your Other Areas Are Still Occupied"
                                               }
                                    } else {
                                            switches3.off()
                                           }
                             }
                      }
                }
                if (switches2 && delayedOff && offRequired) {
                    def switches2State = switches2.currentState("switch")
                    if (switches2State.value.contains("on") && ['vacanton'].contains(areaState) && ['automationon'].contains(automationState)) {
                        if (thisArea) {
                            def thisAreaState = thisArea.currentState("occupancyStatus")
                            if (thisAreaState.value.contains("vacant")) {
                                if (onlyDuringDaytime9) {
                                    def s = getSunriseAndSunset()
                                    def sunrise = s.sunrise.time
                                    def sunset = s.sunset.time
                                    def timenow = now()
                                    if (timenow > sunrise && timenow < sunset) {
                                        if (!atomicState.ddtDimLights) {
                                            atomicState.ddtDimLights = true
                                            dimLights()
                                        }
                                    }
                                } else {
                                    if (!atomicState.ddtDimLights) {
                                        atomicState.ddtDimLights = true
                                        dimLights()
                                    }
                                }
                            } else {
                                log.trace "620 Doing Nothing Because The $thisArea Is Still Occupied"
                            }
                        } else {
                            if (onlyDuringDaytime9) {
                                def s = getSunriseAndSunset()
                                def sunrise = s.sunrise.time
                                def sunset = s.sunset.time
                                def timenow = now()
                                if (timenow > sunrise && timenow < sunset) {
                                    if (!atomicState.ddtDimLights) {
                                        atomicState.ddtDimLights = true
                                        dimLights()
                                    }
                                } else {
                                    log.trace "668 The Time Is After Sunset, Doing Nothing"
                                }
                            } else {
                                if (!atomicState.ddtDimLights) {
                                    atomicState.ddtDimLights = true
                                    dimLights()
                                }
                            }
                        }
                    } else if (switches2State.value.contains("on") && ['vacantdimmed'].contains(areaState) && ['automationon'].contains(automationState)) {
                        if (!atomicState.socis) {
                            if (exitMotionSensors) {
                                def exitMotionState = exitMotionSensors.currentState("motion")
                                if (!exitMotionState.value.contains("active")) {
                                    atomicState.socis = true
                                    switches2Off()
                                }
                            } else {
                                atomicState.socis = true
                                switches2Off()
                            }
                        } else {
                                log.trace("socis already true!")
                        }
                    }
                }
            }
        }
}

//888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888

def checkableLightsSwitchedOnEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacant','vacantdimmed'].contains(areaState)) {
          child.generateEvent('vacanton')
          } else if (['occupied'].contains(areaState)) {
                       child.generateEvent('occupiedon')
                       } else if (['occupiedmotion'].contains(areaState)) {
                                    child.generateEvent('occupiedonmotion')
   								    }  else if (['engaged'].contains(areaState)) {
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

def checking() {
    def child = getChildDevice(getArea())
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('checkingon')
            runIn(actualEntrySensorsTimeout, engaged, [overwrite: false])
        } else {
            child.generateEvent('checking')
            runIn(actualEntrySensorsTimeout, engaged, [overwrite: false])
        }
    } else {
        child.generateEvent('checking')
        runIn(actualEntrySensorsTimeout, engaged, [overwrite: false])
    }
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

def dimLights() {
    log.trace "Activating dimLights()"
    atomicState.ddtDimLights = false
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!entryMotionState.value.contains("active")) {
        def child = getChildDevice(getArea())
        def areaState = child.getAreaState()
        if (['vacanton'].contains(areaState)) {
            dimLightsNow()
        } else {
            log.trace "Re-Evaluated because the lights were told to dim but your room is not in the vacanton state!"
            mainAction()
        }
    } else {
        log.trace "The lights were told to dim but the motion in this room is not inactive"
    }
}

def dimLightsNow() {
    log.trace "Dimming The Lights NOW!"
    setVacantDimmed()
    switches2.each {
        def currentLevel = it.currentValue("level")
        if (currentLevel > dimByLevel) {
            def newLevel = (currentLevel - dimByLevel)
            it.setLevel(newLevel)
            log.trace "The $it have been dimmed to $newLevel %"
        }
    }
}

def setVacantDimmed() {
    log.trace "Setting vacantdimmed now!"
    def child = getChildDevice(getArea())
    child.generateEvent('vacantdimmed')
}

def donotdisturb() {
    def child = getChildDevice(getArea())
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
} 

def doaoff() {
    doorOpeningAction2.each {
        it.setLevel(0)
        it.off()
    }
} 

def engaged() {
    def child = getChildDevice(getArea())
    if (actionOnEngaged) {
        engagedAction.on()
    }
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('engagedonmotion')
        } else {
            child.generateEvent('engagedmotion')
        }
        if (dimmableSwitches1 && switchOnControl && ['automationon'].contains(automationState)) {
            dimmableSwitches1.each {
                def currentLevel = it.currentValue("level")
                if (currentLevel < setLevelTo) {
                    if (onlyIfDisarmed) {
                        def shmStatus = location.currentState("alarmSystemStatus")?.value
                        if (shmStatus == "off") {
                            it.setLevel(setLevelTo)
                            log.trace "478 Setting level of $it to $setLevelTo %"
                        } else {
                            log.trace "SHM is ARMED! No Lights Will Turn On!"
                        }
                    } else {
                        it.setLevel(setLevelTo)
                        log.trace "484 Setting level of $it to $setLevelTo %"
                    }
                } else if (it.currentValue("switch") == 'off') {
                    it.on()
                    log.trace "Level previously set... Switching on $it"
                }
            }
        }
    } else {
        child.generateEvent('engagedmotion')
    }
} 

def entryMotionActiveEventHandler(evt) {
    atomicState.socis = false
    atomicState.ddtDimLights = false
    if (anotherVacancyCheck) {
        atomicState.ffi = false
        unschedule(forceVacantIf)
    }
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['occupiedon','vacanton','vacantdimmed'].contains(areaState)) {
        child.generateEvent('occupiedonmotion')
    }
    if (['occupied','vacant'].contains(areaState)) {
        child.generateEvent('occupiedmotion')
    }
    if (doors) {
        atomicState.dnd = false
        unschedule(donotdusturb)
        def doorsState = doors.currentState("contact")
        if (['engagedon'].contains(areaState)) {
            child.generateEvent('engagedonmotion')
        }
        if (['engaged'].contains(areaState)) {
            child.generateEvent('engagedmotion')
        }
        if (!doorsState.value.contains("open") && ['vacant','vacantdimmed','vacanton','occupied','occupiedmotion','occupiedon','occupiedonmotion','checking','checkingon','donotdisturb','donotdisturbon'].contains(areaState)) {
            engaged()
        }
    }
    log.trace "Re-Evaluation Caused By An Entry Motion Sensor Being 'ACTIVE'"
    mainAction()
}

def entryMotionInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (['occupiedonmotion'].contains(areaState) && !entryMotionState.value.contains("active")) {
        child.generateEvent('occupiedon')
    }
    if (['occupiedmotion'].contains(areaState) && !entryMotionState.value.contains("active")) {
        child.generateEvent('occupied')
    }
    if (doors) {
        unschedule(engaged)
        if (['engagedonmotion'].contains(areaState)) {
            child.generateEvent('engagedon')
        }
        if (['engagedmotion'].contains(areaState)) {
            child.generateEvent('engaged')
        }
    }
    if (!entryMotionState.value.contains("active")) {
        log.trace "Re-Evaluation Caused By An Entry Motion Sensor Being 'INACTIVE'"
        mainAction()
    } else {
        log.trace "Waiting for your other sensor(s) to become INACTIVE"
    }
}

def exitMotionInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (doors) {
        def monitoredDoorState = doors.currentValue("contact")
        if (!monitoredDoorState.contains("open") && !['vacantdimmed','vacanton'].contains(areaState)) {
            log.trace "Exit motion is INACTIVE but the $app.label door is closed so this will be ignored!!!"
        } else {
            log.trace "Re-Evaluation Caused By An Exit Motion Sensor Being 'INACTIVE'"
            mainAction()
        }
    } else if (!['vacant'].contains(areaState)) {
        log.trace "Re-Evaluation Caused By An Exit Motion Sensor Being 'INACTIVE'"
        mainAction()
    }
}



def emswd1N2aboEventHandler(evt) {
def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           def ad1State = adjacentDoor1.currentValue("contact")
           def ad2State = adjacentDoor2.currentValue("contact")
           if (ad1State.contains("open") && ad2State.contains("open")) {
               log.trace "Re-Evaluation Caused By A D1&D2 (BOTH OPEN) Exit Motion Sensor Being 'INACTIVE'"
               mainAction()
               }
    }
}

def emswd1N2abcEventHandler(evt) {
def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           def ad1State = adjacentDoor1.currentValue("contact")
           def ad2State = adjacentDoor2.currentValue("contact")
           if (!ad1State.contains("open") && !ad2State.contains("open")) {           
                log.trace "Re-Evaluation Caused By A D1&D2 (BOTH COSED) Exit Motion Sensor Being 'INACTIVE'"
                mainAction()
                }
    }
}

def emswd1o2cEventHandler(evt)  {
def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           def ad1State = adjacentDoor1.currentValue("contact")
           def ad2State = adjacentDoor2.currentValue("contact")
           if (ad1State.contains("open") && !ad2State.contains("open")) {
                log.trace "Re-Evaluation Caused By A D1 (OPEN) & D2 (CLOSED) Exit Motion Sensor Being 'INACTIVE'"
                mainAction()
           }
    }
}

def emswd2o1cEventHandler(evt) {
def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
           def ad1State = adjacentDoor1.currentValue("contact")
           def ad2State = adjacentDoor2.currentValue("contact")
           if (!ad1State.contains("open") && ad2State.contains("open")) {
                log.trace "Re-Evaluation Caused By A D2 (OPEN) & D1 (CLOSED) Exit Motion Sensor Being 'INACTIVE'"
                mainAction()
                }
    }
}


def exitMotionSensorsWhenDoorIsOpenInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
        if (adjacentDoor1) {
            def adjacentDoor1State = adjacentDoor1.currentValue("contact")
            if (adjacentDoor1State.contains("open")) {
                log.trace "Re-Evaluation Caused By An (OPEN) Exit Motion Sensor Being 'INACTIVE'"
                mainAction()
            }
        }
    }
}


def exitMotionSensorsWhenDoorIsClosedInactiveEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
        if (adjacentDoor1) {
            def adjacentDoor1State = adjacentDoor1.currentValue("contact")
            if (!adjacentDoor1State.contains("open")) {
                log.trace "Re-Evaluation Caused By A (CLOSED) Exit Motion Sensor Being 'INACTIVE'"
                mainAction()
            }
        }
    }
}

def forceTurnAllOff() {
    def child = getChildDevice(getArea())
    log.trace "The Alarm Is Now Set So Performing All Full Reset Of $app.label!"
    checkableLights.each {
        if (it.hasCommand("setLevel")) {
            it.setLevel(0)
        } else {
            it.off()
        }
    }
    log.trace "Generating VACANT Event!"
    child.generateEvent('vacant')
    unschedule()
    log.trace "All Scheduled Jobs Have Been Cancelled!"
    log.trace "Generating AUTOMATION ON Event!"
    child.generateAutomationEvent('automationon')
}

def forceVacantIf() {
    log.trace "forcing Vacant Check"
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!['vacant'].contains(areaState) && !entryMotionState.value.contains("active")) {
        vacant()
    } else {
        log.trace "Vacant check returned '$app.label is NOT vacant!'"
    }
}

private getArea() {
    return "aa_${app.id}"
}

def leftHome() {
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    if (['automationon'].contains(automationState)) {
        log.trace "$app.label was set to 'VACANT' because the mode changed to away or Heavy Use Was Disabled!"
        vacant()
        switches2Off()
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
    def lightStateForDoorAction = doorOpeningAction.currentState("switch")
    doorOpeningAction.each {
        it.setLevel(setLevelAt)
        log.trace "1031 Setting level of $it to $setLevelAt %"
    }
    log.trace "Re-Evaluation Caused By A Monitored Door Being 'OPENED'"
    mainAction()
} 

def monitoredDoorOpenedAction2() {
    def lightStateForDoorAction2 = doorOpeningAction2.currentState("switch")
    doorOpeningAction2.each {
        it.setLevel(setLevelAt2)
        log.trace "1042 Setting level of $it to $setLevelAt2 %"
    }
    log.trace "Re-Evaluation Caused By A Monitored Door Being 'OPENED'"
    mainAction()
}

def monitoredDoorOpenedEventHandler(evt) {
    unschedule(engaged)
    unschedule(donotdisturb)
    if (anotherVacancyCheck) {
        unschedule(forceVacantIf)
    }
    if (!actionOnDoorOpening) {
        log.trace "Re-Evaluated by A Monitored Door Opening"
        mainAction()
    }
    if (actionOnDoorOpening) {
        def child = getChildDevice(getArea())
        def areaState = child.getAreaState()
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
                        if (['vacant'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction()
                        }
                    } else {
                        monitoredDoorOpenedAction()
                    }
                } else {
                	mainAction()
                }
            }
            if (onlyDuringNighttime) {
                if (timenow > sunset || timenow < sunrise) {
                    if (onlyIfAreaVacant) {
                        if (['vacant'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction()
                        }
                    } else {
                        monitoredDoorOpenedAction()
                    }
                } else {
                	mainAction()
                }
            }
            if (onlyDuringDaytime2) {
                if (timenow > sunrise && timenow < sunset) {
                    if (onlyIfAreaVacant2) {
                        if (['vacant'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction2()
                        }
                    } else {
                        monitoredDoorOpenedAction2()
                    }
                } else {
                	mainAction()
                }
            }
            if (onlyDuringNighttime2) {
                if (timenow > sunset || timenow < sunrise) {
                    if (onlyIfAreaVacant2) {
                        if (['vacant'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction2()
                        }
                    } else {
                        monitoredDoorOpenedAction2()
                    }
                } else {
                	mainAction()
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
                    if (['vacant'].contains(areaState)) {
                        log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                        monitoredDoorOpenedAction()
                    } else {
                		mainAction()
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
                            if (['vacant'].contains(areaState)) {
                                log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                                monitoredDoorOpenedAction2()
                            } else {
                                mainAction()
                            }
                        } else {
                            monitoredDoorOpenedAction2()
                        }
                    } else {
                        mainAction()
                    }
                } else {
                    if (onlyIfAreaVacant2) {
                        if (['vacant'].contains(areaState)) {
                            log.trace "The Light Was Turned ON To $setLevelAt % Because The Door Was Opened & $app.label Was VACANT & The Time Selection Matched!"
                            monitoredDoorOpenedAction2()
                        } else {
                            mainAction()
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
    log.trace "Re-Evaluated by A Monitored Door Closing"
    if (!actionOnDoorClosing) {
        if (turnOffAfter) {
            log.trace "Turn Off After Was True So The Lights Should Go Off In $offAfter Seconds"
            mainAction()
            runIn(offAfter, doaoff, [overwrite: false])
        } else {
            mainAction()
        }
    } 
    if (actionOnDoorClosing) {
        def doorsState = doors.currentState("contact")
        if (doorsState.value.contains("open")) {
            log.trace "doing NOTHING because A Door Is Still Open"
            mainAction()
        } else {
            log.trace "The Light Was Turned OFF Because The Door Was Closed"
            doorOpeningAction.each {
                it.setLevel(0)
                log.trace "Re-Evaluated by A Monitored Door Closing"
                mainAction()
            }
        }
    } 
    
}

def occupied() {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (actionOnVacant) {
        if (['engaged','engagedon','engagedmotion','engagedonmotion'].contains(areaState)) {
            vacantAction.off()
        }
    }
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('occupiedonmotion')
        } else {
            child.generateEvent('occupiedmotion')
        }
    } else {
        child.generateEvent('occupiedmotion')
    }
}

def otherAreaOccupancyStatusEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (!['vacant'].contains(areaState)) {
        log.trace "Vacant Force Check Performed by $otherArea Occupancy Changing To Your Required State"
        forceVacantIf()
    }
}

def presenceAwayEventHandler(evt) {
    forceVacantIf()
    runIn(15, turnalloff)
}

def shmStatusEventHandler(evt) {
    def shmStatus = location.currentState("alarmSystemStatus")?.value
    if (shmStatus == "away") {
        log.trace "The Alarm Is Now ARMED AWAY!"
        if (resetOnSHMChangingToAway) {
            log.trace "The Alarm Is Now ARMED AWAY! & You Requested A Full Reset..."
            forceTurnAllOff()
        }
    } else if (shmStatus == "stay") {
        log.trace "The Alarm Is Now ARMED STAY!"
    } else if (shmStatus == "off") {
        log.trace "The Alarm Is Now OFF!"
    }
}

def spawnChildDevice(areaName) {
    app.updateLabel(app.label)
    if (!childCreated())
    def child = addChildDevice("Baz2473", "Area Occupancy Status", getArea(), null, [name: getArea(), label: areaName, completedSetup: true])
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

def switches2Off() {
    atomicState.socis = false
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!entryMotionState.value.contains("active")) {
        def child = getChildDevice(getArea())
        def areaState = child.getAreaState()
        if (['vacantdimmed'].contains(areaState)) {
            switches2OffNow()
        } else {
            log.trace "Re-Evaluated because the lights were told to switch off but the room was not in the vacantdimmed state!"
            mainAction()
        }
    } else {
        log.trace "Re-Evaluated because the lights were told to switch off but the motion in the room was not inactive!"
        mainAction()
    }
}

def switches2OffNow() {
    switches2.each {
        it.setLevel(0)
        log.trace "The $it are now off"
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

def vacant() {
    log.trace "Performing vacant"
    def child = getChildDevice(getArea())
    if (anotherVacancyCheck) {
        unschedule(forceVacantIf)
    }
    if (checkableLights) {
        def lightsState = checkableLights.currentState("switch")
        if (lightsState.value.contains("on")) {
            child.generateEvent('vacanton')
            log.trace "1339 Re-Evaluation Essential By $app.label Changing To Vacanton as to start the lights dimming procedure"
            mainAction()
        } else {
            child.generateEvent('vacant')
            log.trace "1343 Re-Evaluation Essential By $app.label Changing To Vacant as to start the lights dimming procedure"
            mainAction()
        }
    } else {
        child.generateEvent('vacant')
        log.trace "1348 Re-Evaluation Essential By $app.label Changing To Vacant as to start the lights dimming procedure"
        mainAction()
        
    }
} 

def turnalloff() {
    def child = getChildDevice(getArea())
    if (!entryMotionSensors && checkableLights) {
        log.trace "You Have Told Me That $app.label Is Vacant Turning Off All Lights!"
        checkableLights.each {
            if (it.hasCommand("setLevel")) {
                it.setLevel(0)
            } else {
                it.off()
            }
        }
        child.generateEvent('vacant')
        atomicState.ddtDimLights = false
        atomicState.socis = false
        atomicState.ffi = false
        log.trace "All Scheduled Jobs Have Been Cancelled!"
    }
    if (entryMotionSensors && checkableLights) {
        def entryMotionState = entryMotionSensors.currentState("motion")
        if (!entryMotionState.value.contains("active")) {
            log.trace "You Have Told Me That $app.label Is Vacant Turning Off All Lights!"
            checkableLights.each {
                if (it.hasCommand("setLevel")) {
                    it.setLevel(0)
                } else {
                    it.off()
                }
            }
            child.generateEvent('vacant')
            atomicState.ddtDimLights = false
            atomicState.socis = false
            log.trace "All Scheduled Jobs Have Been Cancelled!"
        } else {
            log.trace "Not Performing All Off Because $app.label Was Not Vacant!"
        }
    }
} 

def turnon() {
    def child = getChildDevice(getArea())
    if (checkableLights) {
        log.trace "You Have Told Me To Turn On All Lights in $app.label"
        checkableLights.each {
            if (it.hasCommand("setLevel")) {
                it.setLevel(75)
            } else {
                it.on()
            }
        }
        child.generateEvent('vacanton')
    }
    
}
