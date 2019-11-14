/*
 Copyright (C) 2017 Baz2473
 Name: Area Occupancy Child App
 */

public static String areaOccupancyChildAppVersion() {
    return "v8.0.0.0"
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
        
        section("Select the Entry motion sensors in '$app.label'") {
            input "entryMotionSensors", "capability.motionSensor", title: "Entry sensors?", required: true, multiple: true, submitOnChange: true
        }
            if (entryMotionSensors) {
                section("Select the Exit motion sensors 'Outside' $app.label") {
                    input "exitMotionSensors", "capability.motionSensor", title: "Exit sensors?", required: true, multiple: true, submitOnChange: true
               	    }
                }
            if (exitMotionSensors) {
                section("Select if $app.label has a door to monitor?") {
                    input "monitoredDoor", "bool", title: "Monitor a door?", defaultValue: false, submitOnChange: true
                    if (monitoredDoor) {
                        input "doors", "capability.contactSensor", title: "Doors?", multiple: false, required: true, submitOnChange: true
                        if (doors) {
                            input "immediateExitSensor", "capability.motionSensor", title: "$app.label's immediate exit sensor", multiple: false, required: true, submitOnChange: true
                            }
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
            if (entryMotionSensors && doors) {
                section("Action On Engaged") {
                	input "movementDetectedWhileDoorClosedActivatesEngaged", "bool", title: "Make $app.label ENGAGED\nif movement is detected while the door is closed?", defaultValue: false, submitOnChange: true
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
        subscribe(immediateExitSensor, "motion.inactive", immediateExitMotionInactiveEventHandler)
    	}
    if (entryMotionSensors) { 
        subscribe(entryMotionSensors, "motion.active", entryMotionActiveEventHandler)
        subscribe(entryMotionSensors, "motion.inactive", entryMotionInactiveEventHandler)
    	}
    if (exitMotionSensors) { 
        subscribe(exitMotionSensors, "motion.inactive", exitMotionInactiveEventHandler)
    	}
    if (presence) {
        subscribe(presenceSensors, "presence.not present", presenceAwayEventHandler)
   	    }    
    }
    
def initialize() {
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
    if (['vacant','vacantdimmed','vacantclosed','vacantdimmedclosed'].contains(areaState)) {
          if (doors) { 
          	  def doorsState = doors.currentState("contact")
        	  if (!doorsState.value.contains("open")) { 
              	   child.generateEvent("vacantonclosed")
              } else { 
              		  child.generateEvent("vacanton")
              }
          } else { 
          		  child.generateEvent('vacanton')
          }
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
    if (['vacanton','vacantdimmed','vacantonclosed','vacantdimmedclosed'].contains(areaState) && !checkableLightsState.value.contains("on")) {
    	  if (doors) { 
          	  def doorsState = doors.currentState("contact")
        	  if (!doorsState.value.contains("open")) { 
              	   child.generateEvent("vacantclosed")
              } else { 
              		  child.generateEvent("vacant")
              }
          } else { 
       	  		  child.generateEvent('vacant')
          }
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

  
                          /////////////////////////////////////////// END OF THE DEF'S USED IN runIn() FUNCTIONS //////////////////////////////////////////////


def immediateExitMotionInactiveEventHandler(evt) {
    if (movementDetectedWhileDoorClosedActivatesEngaged) {
   		def entryMotionState = entryMotionSensors.currentState("motion")
   		def doorsState = doors.currentState("contact")
    	if (entryMotionState.value.contains("active") && !doorsState.value.contains("open")) {
            engaged()
    	}
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
    if (actionOnEngaged) {
        engagedAction.on()
   }
} 

def entryMotionActiveEventHandler(evt) {
    atomicState.emii = false
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    def checkables = checkableLights.currentState("switch")
    if (movementDetectedWhileDoorClosedActivatesEngaged) {
        def doorsState = doors.currentState("contact")
       	if (!doorsState.value.contains("open") && !['engaged','engagedon','engagedonmotion'].contains(areaState)) {
           	engaged()
            return
       	}
    }
    if (['occupiedon','vacanton','vacantdimmed'].contains(areaState)) {
        child.generateEvent('occupiedonmotion')
        return
    }
    if (['occupied','vacant'].contains(areaState)) {
       if (checkables.value.contains("on")) {
          child.generateEvent('occupiedonmotion')
       } else {
               child.generateEvent('occupiedmotion')
       }
        return
    }
    if (['engagedon'].contains(areaState)) {
        child.generateEvent('engagedonmotion')
        return
    }
    if (['engaged'].contains(areaState)) {
       if (checkables.value.contains("on")) {
          child.generateEvent('engagedonmotion')
       } else {
               child.generateEvent('engagedmotion')
       }
        return
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
              child.generateEvent('vacantclosed')
              return
        }
        if (['engagedonmotion'].contains(areaState)) {
              child.generateEvent('engagedon')
              return
        }
        if (['engagedmotion'].contains(areaState)) {
              child.generateEvent('engaged')
              return
        }
        if (['occupiedonmotion'].contains(areaState)) {
               if (exitMotionState.value.contains("active")) {
                   child.generateEvent('vacanton')
                   return
               } else {
                       child.generateEvent('occupiedon')
                       return
               }
        }
        if (['checkingon'].contains(areaState)) {            
              child.generateEvent('vacantonclosed')
              return
         }
     }
}

def exitMotionInactiveEventHandler(evt) {
    def exitMotionState = exitMotionSensors.currentState("motion")
    def child = getChildDevice(getArea())
   	def automationState = child.getAutomationState()
    if (!exitMotionState.value.contains("active") && ['automationon'].contains(automationState)) {
    	def areaState = child.getAreaState()
    	if (doors) {
        	def doorsState = doors.currentState("contact")
        	if (!doorsState.value.contains("open") && !['vacantdimmedclosed','vacantonclosed'].contains(areaState)) {
				 return
			} 
    	}
    }
}

def modeEventHandler(evt) {
    def child = getChildDevice(getArea())
    def automationState = child.getAutomationState()
    if (resetAutomation && resetAutomationMode && resetAutomationMode.contains(evt.value) && ['automationoff'].contains(automationState)) {
        child.generateAutomationEvent('automationon')
    }
    if (awayModes && awayModes.contains(evt.value) && noAwayMode) {
        turnalloff()
    }
}


def monitoredDoorOpenedEventHandler(evt) {
    def child = getChildDevice(getArea())
    def areaState = child.getAreaState()
    if (['vacantclosed'].contains(areaState)) {
    	  child.generateEvent('vacant')
    }
    if (['vacantonclosed'].contains(areaState)) {
    	  child.generateEvent('vacanton')
    }
    if (['vacantdimmedclosed'].contains(areaState)) {
    	  child.generateEvent('vacantdimmed')
    }
    if (['checking','checkingon','engaged','engagedmotion','engagedon','engagedonmotion'].contains(areaState)) {
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
} 

def monitoredDoorClosedEventHandler(evt) {
    def child = getChildDevice(getArea())
   	def areaState = child.getAreaState()
  	if (['vacant'].contains(areaState)) {
          child.generateEvent('vacantclosed')
    }
    if (['vacanton'].contains(areaState)) {
          child.generateEvent('vacantonclosed')
    }
    if (['vacantdimmed'].contains(areaState)) {
          child.generateEvent('vacantdimmedclosed')
    }
  	if (['occupiedmotion'].contains(areaState)) {
   	      child.generateEvent('checking')
    }
   	if (['occupiedonmotion'].contains(areaState)) {
   	      child.generateEvent('checkingon')
    }
}

def presenceAwayEventHandler(evt) {
	turnalloff()
    def child = getChildDevice(getArea())
    child.generateAutomationEvent('automationon')
}

def shmStatusEventHandler(evt) {
    def shmStatus = location.currentState("alarmSystemStatus")?.value
    if (shmStatus == "away") {
        if (resetOnSHMChangingToAway) {
            turnalloff()
            def child = getChildDevice(getArea())
    	    child.generateAutomationEvent('automationon')
        }
    }
}





                          /////////////////////////////////////////// THESE DEF'S ARE ALSO CONTROLLED BY THE CHILD APP //////////////////////////////////////////////

def turnalloff() {
    def child = getChildDevice(getArea())
    def entryMotionState = entryMotionSensors.currentState("motion")
    if (!entryMotionState.value.contains("active")) {
     	 checkableLights.each {
                			  if (it.hasCommand("setLevel")) {
                    			  it.setLevel(0)
                			  } else {
                    				  it.off()
                			  }
         }
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
     }
}

def turnon() {
    checkableLights.each {
      			         if (it.hasCommand("setLevel")) {
                			 it.setLevel(75)
            			 } else {
                				 it.on()
            			 }
    }
}

                       /////////////////////////////////////////// END OF THE DEF'S CONTROLLED BY THE CHILD APP //////////////////////////////////////////////
