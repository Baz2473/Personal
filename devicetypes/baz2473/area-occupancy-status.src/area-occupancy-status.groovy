/*****************************************************************************************************************
*
*  A SmartThings device handler for handling Areas as devices which have states.
*  Copyright (C) 2017 Baz2473
*  Copyright (C) 2017 bangali
*
*  License:
*  This program is free software: you can redistribute it and/or modify it under the terms of the GNU 
*  General Public License as published by the Free Software Foundation, either version 3 of the License, or 
*  (at your option) any later version.
*
*  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
*  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
*  for more details.
*
*  You should have received a copy of the GNU General Public License along with this program.
*  If not, see <http://www.gnu.org/licenses/>.
*
*  Name: Area Occupancy Status
*
*  Version: 3.0.0
*  Added Automation Status Custom Commands So They Are Available To WebCoRE.
*
*  Version: 2.1.1
*  Minor Changes And Updates To Away Modes And Automation Events & Logs.
*
*  Version: 2.1.0
*  Changed Display & Added A New Tile To Display The Automation Status Seperatley To The Occupancy Status.
*
*  Version: 1.0.4
*  Removed NON CLICKABLE Buttons  
*
*  Version: 1.0.3
*  Removed Click Options On Buttons That Should Not Be Clickable, The Purpose In My Occupancy App Is To Automatically Detect Status.
*  I Have Left 'VACANT' And 'OUT OF ORDER' As Clickable In Case It Fails To Detect Vacancy,
*  And To Override All Automation With The 'OUT OF ORDER' (OOO) Button.
*
*  Version: 1.0.2
*  Updated OOO Icon And Color On All Mini Tiles For Better Viewing
*
*  Version: 1.0.1
*  Updated to bring Versions in line  
*
*****************************************************************************************************************/

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
        command "occupied"
        command "checking"
		command "engaged"
		command "donotdisturb"
		command "heavyuse"
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
				attributeState "vacant", label: 'Vacant', icon:"st.Home.home18", backgroundColor:"#ffffff"
                attributeState "occupied", label: 'Occupied', action: "vacant", icon:"st.Home.home4", backgroundColor:"#32cd32"
                attributeState "checking", label: 'Checking', action: "vacant", icon:"st.Health & Wellness.health9", backgroundColor:"#e86d13"
				attributeState "engaged", label: 'Engaged', action: "vacant", icon:"st.locks.lock.locked", backgroundColor:"#ff0000"
                attributeState "donotdisturb", label: 'Do Not Disturb', action: "vacant", icon:"st.Office.office6", backgroundColor:"#00abf5"
				attributeState "heavyuse", label: 'Heavy Use', action: "vacant", icon:"st.Health & Wellness.health5", backgroundColor:"#8a5128"
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
}

def parse(String description)	{}

def installed()     {   initialize();   vacant();   automationon()  }

def updated()	{   initialize()    }

def initialize() {}

def vacant()	{	stateUpdate('vacant')		}

def occupied()	{	stateUpdate('occupied')		}

def checking()	{	stateUpdate('checking')		}

def engaged()	{	stateUpdate('engaged')		}

def donotdisturb()	{	stateUpdate('donotdisturb')		}

def heavyuse()		{	stateUpdate('heavyuse')		}

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
	def msgTextMap = ['vacant':'Vacant Since: ', 'occupied':'Occupied Since: ', 'checking':'Checking Status: ', 'engaged':'Engaged Since: ', 'donotdisturb':'Do Not Disturb!: ', 'heavyuse':'Area Is In Constant Use: ', 'outoforder':'Area Has Been OUT OF ORDER since: ']
	if (!occupancyStatus || !(msgTextMap.containsKey(occupancyStatus))) {
    	log.debug "${device.displayName}: Missing or invalid parameter Occupancy Status. Allowed values are: Vacant, Occupied, Checking, Engaged, Heavyuse, Donotdisturb or Outoforder."
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