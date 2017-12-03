/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {

    definition (name: "Baz's Lights Needed Switch", namespace: "Using", author: "Baz2473") {
		capability "Switch"
        capability "Relay Switch"
		capability "Sensor"
		capability "Actuator"

		command "onPhysical"
		command "offPhysical"
	}

	tiles {
		standardTile("switch", "device.switch", width: 3, height: 3,) {
			state "off", label: 'No', action: "switch.on", icon: "st.Weather.weather14", backgroundColor: "#f7ff00"
			state "on", label: 'Yes', action: "switch.off", icon: "st.Weather.weather4", backgroundColor: "#000000"
		}
		standardTile("on", "device.switch", decoration: "flat") {
			state "default", label: 'Yes', icon: "st.Weather.weather4", action: "onPhysical", backgroundColor: "#000000"
		}
        standardTile("on2", "device.switch", decoration: "flat") {
        }
		standardTile("off", "device.switch", decoration: "ring") {
			state "default", label: 'No', icon: "st.Weather.weather14", action: "offPhysical", backgroundColor: "#f7ff00"
		}
        main "switch"
		details(["switch","on","on2","off"])
	}
}

def parse(description) {
}

def on() {
	log.debug "$version on()"
	sendEvent(name: "switch", value: "on")
}

def off() {
	log.debug "$version off()"
	sendEvent(name: "switch", value: "off")
}

def onPhysical() {
	log.debug "$version onPhysical()"
	sendEvent(name: "switch", value: "on", type: "physical")
}

def offPhysical() {
	log.debug "$version offPhysical()"
	sendEvent(name: "switch", value: "off", type: "physical")
}

private getVersion() {
	"PUBLISHED"
}