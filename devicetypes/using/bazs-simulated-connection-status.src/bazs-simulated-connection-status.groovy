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

    definition (name: "Baz's Simulated Connection Status", namespace: "Using", author: "Baz2473") {
        capability "Switch"
        capability "Relay Switch"
        capability "Sensor"
        capability "Actuator"

        command "onPhysical"
        command "offPhysical"
    }

    tiles(scale:2) {
        standardTile("switch", "device.switch", width: 6, height: 6, canChangeIcon: true) {
            state "off", label: 'Dis-Connected', icon: "", backgroundColor: "#ff0000"
            state "on", label: 'Connected', icon: "", backgroundColor: "#00A0DC"
        }
        standardTile("off2", "device.switch", width: 5, height: 1,  decoration: "flat") {
            state "default", backgroundColor: "#ffffff"
        }
        standardTile("off1", "device.switch", width: 1, height: 1,  decoration: "flat") {
            state "default", backgroundColor: "#ffffff"
        }
        main "switch"
        details(["off2","off1","switch","off1","off2"])
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