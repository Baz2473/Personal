/*
  Copyright (C) 2017 Baz2473
  Name: Area Occupancy Status 
*/
public static String DTHVersion() { return "v3.1.1.0" }

metadata {
	      definition (
    	              name: "Area Occupancy Status", 
                      namespace: "Baz2473", 
                      author: "Baz2473") { 
                                          capability "Actuator"
                                          capability "Sensor"
                                          capability "estimatedTimeOfArrival"
		                                  attribute "occupancyStatus", "string"
                                          attribute "automationStatus", "string"
                                          command "turnalloff"
                                          command "turnon"
		                                  command "vacant"
                                          command "vacantclosed"
                                          command "vacantdimmed"
                                          command "vacantdimmedclosed"
                                          command "vacanton"
                                          command "vacantonclosed"
                                          command "occupied"
                                          command "occupiedmotion"
                                          command "occupiedon"
                                          command "occupiedonmotion"
                                          command "checking"
                                          command "checkingon"
		                                  command "engaged"
                                          command "engagedmotion"
                                          command "engagedon"
                                          command "engagedonmotion"
		                                  command "donotdisturb"
                                          command "donotdisturbon"
                                          command "automationon"
                                          command "automationoff"
                                          command "updateOccupancyStatus", ["string"]
                                          command "updateAutomationStatus", ["string"]
	                                     }
    
	simulator	{
   }
    
	tiles(scale: 2)	{
    	multiAttributeTile(name: "occupancyStatus", type: "generic", width: 2, height: 2, canChangeBackground: false) {
			tileAttribute ("device.occupancyStatus", key: "PRIMARY_CONTROL") {
				attributeState "vacant", label: 'vacant', action: "turnon", icon:"st.Home.home18", backgroundColor:"#606060"
				attributeState "vacantclosed", label: 'vacant', action: "turnon", icon:"st.Home.home18", backgroundColor:"#606060"
                attributeState "vacantdimmed", label: 'vacant', action: "turnalloff", icon:"st.Home.home18", backgroundColor:"#cdc8a3"
                attributeState "vacantdimmedclosed", label: 'vacant', action: "turnalloff", icon:"st.Home.home18", backgroundColor:"#cdc8a3"
                attributeState "vacanton", label: 'vacant', action: "turnalloff", icon:"st.Home.home18", backgroundColor:"#c1b419"
                attributeState "vacantonclosed", label: 'vacant', action: "turnalloff", icon:"st.Home.home18", backgroundColor:"#c1b419"
                attributeState "occupied", label: 'occupied', action: "vacant", icon:"st.Home.home4", backgroundColor:"#156700"
                attributeState "occupiedmotion", label: 'motion', action: "vacant", icon:"st.Health & Wellness.health12", backgroundColor:"#156700"
                attributeState "occupiedon", label: 'occupied', action: "vacanton", icon:"st.Home.home4", backgroundColor:"#32cd32"
                attributeState "occupiedonmotion", label: 'motion', action: "vacanton", icon:"st.Health & Wellness.health12", backgroundColor:"#32cd32"
                attributeState "checking", label: 'checking', action: "vacant", icon:"st.Health & Wellness.health9", backgroundColor:"#bf6700"
                attributeState "checkingon", label: 'checking', action: "vacanton", icon:"st.Health & Wellness.health9", backgroundColor:"#ff8a00"
				attributeState "engaged", label: 'engaged', action: "vacant", icon:"st.locks.lock.locked", backgroundColor:"#af0000"
				attributeState "engagedmotion", label: 'motion', action: "vacant", icon:"st.Health & Wellness.health12", backgroundColor:"#af0000"
                attributeState "engagedon", label: 'engaged', action: "vacanton", icon:"st.locks.lock.locked", backgroundColor:"#ff0000"
                attributeState "engagedonmotion", label: 'motion', action: "vacanton", icon:"st.Health & Wellness.health12", backgroundColor:"#ff0000"
                attributeState "donotdisturb", label: 'dnd', action: "vacant", icon:"st.Office.office6", backgroundColor:"#410099"
                attributeState "donotdisturbon", label: 'dnd', action: "vacanton", icon:"st.Office.office6", backgroundColor:"#6d00ff"
                }
       		tileAttribute ("device.status", key: "SECONDARY_CONTROL") {
				attributeState "default", label:'${currentValue}'
			    }
      }
       multiAttributeTile(name: "automationStatus", type: "generic", width: 2, height: 2, canChangeBackground: false) {
			tileAttribute ("device.automationStatus", key: "PRIMARY_CONTROL") {
				attributeState "automationon", label: 'Automation ON', action: "automationoff", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#32CD32"
                attributeState "automationoff", label: 'Automation OFF', action: "automationon", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#FF0000"        
                }
       		tileAttribute ("device.automationstatus", key: "SECONDARY_CONTROL")	{
				attributeState "default", label:'${currentValue}'
			    }
       }
		main (["occupancyStatus"])
		details (["occupancyStatus","automationStatus"])
	                }
  preferences {}            	 
}
def parse(String description)	{
    }
def installed() {   
    initialize();   vacant();   automationon()  
    }
def updated() {
    initialize()    
    }
def initialize() {
    }
def vacant() {
    stateUpdate('vacant')		
    }
def vacantclosed() {
	stateUpdate('vacantclosed')
    }
def vacantdimmed() {
    stateUpdate('vacantdimmed')
    }
def vacantdimmedclosed() {
    stateUpdate('vacantdimmedclosed')
    }
def vacanton() {	
    stateUpdate('vacanton')		
    }
def vacantonclosed() {	
    stateUpdate('vacantonclosed')		
    }
def occupied() {
    stateUpdate('occupied')
    }
def occupiedmotion() {
    stateUpdate('occupiedmotion')
    }    
def occupiedon() {
    stateUpdate('occupiedon')
    }
def occupiedonmotion() {
    stateUpdate('occupiedonmotion')
    }
def checking() {
    stateUpdate('checking')
    }
def checkingon() {
    stateUpdate('checkingon')
    }
def engaged() {
    stateUpdate('engaged')
    }
def engagedmotion() {
    stateUpdate('engagedmotion')
    }
def engagedon()	{
    stateUpdate('engagedon')
    }
def engagedonmotion()	{
    stateUpdate('engagedonmotion')
    }   
def donotdisturb() {
    stateUpdate('donotdisturb')	
    }
def donotdisturbon() {
    stateUpdate('donotdisturbon')
    }
def automationon() {
    automationStateUpdate('automationon')
    }
def automationoff() {
    automationStateUpdate('automationoff')
    }
private	stateUpdate(state) {
	    if (device.currentValue('occupancyStatus') != state)
		    updateOccupancyStatus(state)
	        resetTile(state)
            }
private	automationStateUpdate(automationState) {
	    if (device.currentValue('automationStatus') != automationState)
		    updateAutomationStatus(automationState)
	        resetTile(automationState)
            }
private updateOccupancyStatus(occupancyStatus = null) {
	    occupancyStatus = occupancyStatus?.toLowerCase()
	    def msgTextMap = ['vacant':'Vacant Since: ','vacantdimmed':'Vacant & Dimmed Since: ','vacanton':'Vacant & On Since: ','vacantclosed':'Vacant & Closed Since: ','vacantdimmedclosed':'Vacant, Closed & Dimmed Since: ','vacantonclosed':'Vacant, Closed & On Since: ', 'occupied':'Occupied Since: ', 'occupiedmotion':'Occupied & Motion Since: ', 'occupiedon':'Occupied & On Since: ', 'occupiedonmotion':'Occupied & Motion Since: ','checking':'Checking Status: ','checkingon':'Checking Status Since: ','engaged':'Engaged Since: ', 'engagedmotion':'Engaged & Motion Since: ','engagedon':'Engaged & On Since: ','engagedonmotion':'Engaged & Motion Since: ' ,'donotdisturb':'DND Since: ','donotdisturbon':'DND & On Since: ']
        if (!occupancyStatus || !(msgTextMap.containsKey(occupancyStatus))) {
    	     log.debug "${device.displayName}: Missing or invalid parameter Occupancy Status. Allowed values are: vacant, vacantclosed, vacantdimmed, vacantdimmedclosed, occupied, occupiedonmotion, checking, engaged, engagedonmotion, donotdisturb, vacanton, vacantonclosed, occupiedon, checkingon, engagedon or donotdisturbon."
             return
             }
	    sendEvent(name: "occupancyStatus", value: occupancyStatus, descriptionText: "${device.displayName} changed to ${occupancyStatus}", isStateChange: true, displayed: true)
        def statusMsg = msgTextMap[device.currentValue('occupancyStatus')] + formatLocalTime()
        sendEvent(name: "status", value: statusMsg, isStateChange: true, displayed: false)
        }
private updateAutomationStatus(automationStatus = null) {
	    automationStatus = automationStatus?.toLowerCase()
	    def msgTextMap = ['automationoff':'Off Since: ', 'automationon':'On Since: ']
	    if (!automationStatus || !(msgTextMap.containsKey(automationStatus))) {
    	     log.debug "${device.displayName}: Missing or invalid parameter Occupancy Status. Allowed values are: Automation Off or Automation On."
             return
             }
	    sendEvent(name: "automationStatus", value: automationStatus, descriptionText: "${device.displayName} changed to ${automationStatus}", isStateChange: true, displayed: true)
        def statusMsg = msgTextMap[device.currentValue('automationStatus')] + formatLocalTime()
	    sendEvent(name: "automationstatus", value: statusMsg, isStateChange: true, displayed: false)
        }
private formatLocalTime(format = "h:mm:ss a 'on' EEE, d MMM yyyy", time = now()) {
	    def formatter = new java.text.SimpleDateFormat(format)
	    formatter.setTimeZone(location.timeZone)
	    return formatter.format(time)
        }
private	resetTile(occupancyStatus) {
        sendEvent(name: occupancyStatus, value: occupancyStatus, descriptionText: "reset tile ${occupancyStatus} to ${occupancyStatus}", isStateChange: true, displayed: false)
        }
private	resetAutomationTile(automationStatus) {
        sendEvent(name: automationStatus, value: automationStatus, descriptionText: "reset tile ${automationStatus} to ${automationStatus}", isStateChange: true, displayed: false)
        }
def generateEvent(state = null)	{
    if (state)
	    stateUpdate(state)
        return null
        }
def generateAutomationEvent(automationState = null)	{
    if (automationState)
		automationStateUpdate(automationState)
        return null
        }
def getAreaState() {
    return device.currentValue('occupancyStatus')
    }
def getAutomationState() {
    return device.currentValue('automationStatus')
    }
def turnalloff() {
    parent.turnalloff()
    }
def turnon() {
    parent.turnon()
}