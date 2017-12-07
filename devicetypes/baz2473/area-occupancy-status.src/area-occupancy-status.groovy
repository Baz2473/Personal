/*
  A SmartThings device handler for handling Areas as devices which have states.
  
  Copyright (C) 2017 Baz2473
  
  Name: Area Occupancy Status

  Version: 1.0.0  

*/

metadata {
	definition (
    	name: "Area Occupancy Status", 
        namespace: "Baz2473", 
        author: "Baz2473")		{
		capability "Sensor"
        capability "estimatedTimeOfArrival"
		attribute "occupancyStatus", "string"
        attribute "automationStatus", "string"
		command "vacant"
        command "vacanton"
        command "occupied"
        command "occupiedon"
        command "checking"
        command "checkingon"
		command "engaged"
        command "engagedon"
		command "donotdisturb"
        command "donotdisturbon"
		command "heavyuse"
        command "heavyuseon"
        command "automationon"
        command "automationoff"
        command "updateOccupancyStatus", ["string"]
        command "updateAutomationStatus", ["string"]
	}
    
	simulator	{
	}
    
	tiles(scale: 2)		{
    	multiAttributeTile(name: "occupancyStatus", width: 2, height: 2, canChangeBackground: false)		{
			tileAttribute ("device.occupancyStatus", key: "PRIMARY_CONTROL")		{
				attributeState "vacant", label: 'OFF', icon:"st.Home.home18", backgroundColor:"#606060"
                attributeState "vacanton", label: 'ON', icon:"st.Home.home18", backgroundColor:"#c1b419"
                attributeState "occupied", label: 'OFF', action: "vacant", icon:"st.Home.home4", backgroundColor:"#156700"
                attributeState "occupiedon", label: 'ON', action: "vacant", icon:"st.Home.home4", backgroundColor:"#32cd32"
                attributeState "checking", label: 'OFF', action: "vacant", icon:"st.Health & Wellness.health9", backgroundColor:"#bf6700"
                attributeState "checkingon", label: 'ON', action: "vacant", icon:"st.Health & Wellness.health9", backgroundColor:"#ff8a00"
				attributeState "engaged", label: 'OFF', action: "vacant", icon:"st.locks.lock.locked", backgroundColor:"#af0000"
                attributeState "engagedon", label: 'ON', action: "vacant", icon:"st.locks.lock.locked", backgroundColor:"#ff0000"
                attributeState "donotdisturb", label: 'OFF', action: "vacant", icon:"st.Office.office6", backgroundColor:"#410099"
                attributeState "donotdisturbon", label: 'ON', action: "vacant", icon:"st.Office.office6", backgroundColor:"#6d00ff"
				attributeState "heavyuse", label: 'OFF', action: "vacant", icon:"st.Health & Wellness.health5", backgroundColor:"#8a5128"
                attributeState "heavyuseon", label: 'ON', action: "vacant", icon:"st.Health & Wellness.health5", backgroundColor:"#8a5128"
            }
       		tileAttribute ("device.status", key: "SECONDARY_CONTROL")	{
				attributeState "default", label:'${currentValue}'
			}
        }
       multiAttributeTile(name: "automationStatus", width: 2, height: 2, canChangeBackground: false)		{
			tileAttribute ("device.automationStatus", key: "PRIMARY_CONTROL")		{
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

def parse(String description)	{}

def installed()     {   initialize();   vacant();   automationon()  }

def updated()	{   initialize()    }

def initialize() {}

def vacant()	{	stateUpdate('vacant')		}
def vacanton()	{	stateUpdate('vacanton')		}

def occupied()	{	stateUpdate('occupied')		}
def occupiedon()	{	stateUpdate('occupiedon')		}

def checking()	{	stateUpdate('checking')		}
def checkingon()	{	stateUpdate('checkingon')		}

def engaged()	{	stateUpdate('engaged')		}
def engagedon()	{	stateUpdate('engagedon')		}

def donotdisturb()	{	stateUpdate('donotdisturb')		}
def donotdisturbon()	{	stateUpdate('donotdisturbon')		}

def heavyuse()		{	stateUpdate('heavyuse')		}
def heavyuseon()		{	stateUpdate('heavyuseon')		}

def automationon() {  automationStateUpdate('automationon')  }

def automationoff()  {  automationStateUpdate('automationoff')  }

private	stateUpdate(state)	{
	if (device.currentValue('occupancyStatus') != state)
		updateOccupancyStatus(state)
	resetTile(state)
}
private	automationStateUpdate(automationState)	{
	if (device.currentValue('automationStatus') != automationState)
		updateAutomationStatus(automationState)
	resetAutomationTile(automationState)
}
private updateOccupancyStatus(occupancyStatus = null) 	{
	occupancyStatus = occupancyStatus?.toLowerCase()
	def msgTextMap = ['vacant':'Vacant Since: ', 'vacanton':'Vacanton Since: ', 'occupied':'Occupied Since: ', 'occupiedon':'Occupiedon Since: ','checking':'Checking Status: ','checkingon':'Checkingon Status: ','engaged':'Engaged Since: ','engagedon':'Engagedon Since: ' ,'donotdisturb':'Do Not Disturb!: ','donotdisturbon':'Do Not Disturbon!: ', 'heavyuse':'Area Is In Constant Use: ','heavyuseon':'Area Is on In Constant Use: ']
	if (!occupancyStatus || !(msgTextMap.containsKey(occupancyStatus))) {
    	log.debug "${device.displayName}: Missing or invalid parameter Occupancy Status. Allowed values are: Vacant, Occupied, Checking, Engaged, Heavyuse, Donotdisturb, Vacanton, Occupiedon, Checkingon, Engagedon, Heavyuseon or Donotdisturbon."
        return
    }
	sendEvent(name: "occupancyStatus", value: occupancyStatus, descriptionText: "${device.displayName} changed to ${occupancyStatus}", isStateChange: true, displayed: true)
    def statusMsg = msgTextMap[device.currentValue('occupancyStatus')] + formatLocalTime()
	sendEvent(name: "status", value: statusMsg, isStateChange: true, displayed: false)
}
private updateAutomationStatus(automationStatus = null) 	{
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
private formatLocalTime(format = "h:mm:ss a 'on' EEE, d MMM yyyy", time = now())		{
	def formatter = new java.text.SimpleDateFormat(format)
	formatter.setTimeZone(location.timeZone)
	return formatter.format(time)
}

private	resetTile(occupancyStatus)	{
    sendEvent(name: occupancyStatus, value: occupancyStatus, descriptionText: "reset tile ${occupancyStatus} to ${occupancyStatus}", isStateChange: true, displayed: false)
}
private	resetAutomationTile(automationStatus)	{
    sendEvent(name: automationStatus, value: automationStatus, descriptionText: "reset tile ${automationStatus} to ${automationStatus}", isStateChange: true, displayed: false)
}
def generateEvent(state = null)		{
        if	(state)
		stateUpdate(state)
        return null
}
def generateAutomationEvent(automationState = null)		{
        if	(automationState)
		automationStateUpdate(automationState)
        return null
}
def getAreaState()	{	return device.currentValue('occupancyStatus')		}
def getAutomationState()  {  return device.currentValue('automationStatus')    }