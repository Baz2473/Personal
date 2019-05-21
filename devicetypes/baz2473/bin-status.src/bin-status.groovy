/*
  Copyright (C) 2017 Baz2473
  Name: Area Occupancy Status 
*/
public static String DTHVersion() { return "v1.0.0.1" }

metadata {
	      definition (
    	              name: "Bin Status", 
                      namespace: "Baz2473", 
                      author: "Baz2473") { 
                                          capability "Actuator"
                                          capability "Switch"
                                          capability "Relay Switch"
                                          capability "Sensor"
                                          capability "estimatedTimeOfArrival"
		                                  attribute "binStatus", "string"
		                                  command "no"
                                          command "greenbin"
		                                  command "brownbin"
                                          command "updateBinStatus", ["string"]
	                                     }
    
	simulator	{
   }
    
	tiles(scale: 2)	{
    	multiAttributeTile(name: "binStatus", type: "generic", width: 2, height: 2, canChangeBackground: false) {
			tileAttribute ("device.binStatus", key: "PRIMARY_CONTROL") {
				attributeState "no", label: 'No', icon:"st.Office.office10", backgroundColor:"#ffffff"
                attributeState "greenbin", label: 'Yes', action: "no", icon:"st.Office.office10", backgroundColor:"#17ba06"
				attributeState "brownbin", label: 'Yes', action: "no", icon:"st.Outdoor.outdoor3", backgroundColor:"#8b4514"
                }
      }
       
		main (["binStatus"])
		details (["binStatus"])
	                }
  preferences {}            	 
}
def parse(String description)	{
    }
def installed() {   
    initialize();   no()
    }
def updated() {
    initialize()    
    }
def initialize() {
    }
def no() {
    stateUpdate('no')	
    }
def greenbin() {
    stateUpdate('greenbin')
    }
def brownbin() {
    stateUpdate('brownbin')
    }
def off() {
    sendEvent(name: "switch", value: "off")
    stateUpdate('no')
}
private	stateUpdate(state) {
	    if (device.currentValue('binStatus') != state)
		    updateBinStatus(state)
	        resetTile(state)
            }
private updateBinStatus(binStatus = null) {
	    binStatus = binStatus?.toLowerCase()
	    sendEvent(name: "binStatus", value: binStatus, descriptionText: "${device.displayName} changed to ${binStatus}", isStateChange: true, displayed: true)
        }
private	resetTile(binStatus) {
        sendEvent(name: binStatus, value: binStatus, descriptionText: "reset tile ${binStatus} to ${binStatus}", isStateChange: true, displayed: false)
        }
def generateEvent(state = null)	{
    if (state)
	    stateUpdate(state)
        return null
        }
def getBinState() {
    return device.currentValue('binStatus')
    }