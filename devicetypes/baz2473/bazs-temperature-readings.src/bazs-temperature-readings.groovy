/**
 *  Copyright 2014 SmartThings
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
    definition (name: "Baz's Temperature Readings", namespace: "Baz2473", author: "Baz2473") {
        capability "Temperature Measurement"
        capability "Switch Level"
        capability "Sensor"
        capability "Health Check"  
        command "setHighestTemperature", ["number"]
        command "setAverageTemperature", ["number"]
        command "setLowestTemperature", ["number"]
 		command "setOldHighestTemperature", ["number"]
        command "setOldAverageTemperature", ["number"]
        command "setOldLowestTemperature", ["number"]
        
    }

    tiles (scale: 2) {   
        valueTile("highestTemperature", "device.highesttemperature", width: 2, height: 2) {
            state("highesttemperature", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 0, color: "#153591"],
                    [value: 10, color: "#1e9cbb"],
                    [value: 14, color: "#90d2a7"],
                    [value: 18, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 26, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("averageTemperature", "device.averagetemperature", width: 2, height: 2) {
            state("averagetemperature", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 0, color: "#153591"],
                    [value: 10, color: "#1e9cbb"],
                    [value: 14, color: "#90d2a7"],
                    [value: 18, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 26, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("lowestTemperature", "device.lowesttemperature", width: 2, height: 2) {
            state("lowesttemperature", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 0, color: "#153591"],
                    [value: 10, color: "#1e9cbb"],
                    [value: 14, color: "#90d2a7"],
                    [value: 18, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 26, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("Highest", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'Highest', unit:"F"
            )
        }
    	valueTile("Average", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'Average', unit:"F"
            )
        }
     	valueTile("Lowest", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'Lowest', unit:"F"
            )
        }
        valueTile("oldHighestTemperature", "device.oldhighesttemperature", width: 2, height: 2) {
            state("oldhighesttemperature", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 0, color: "#153591"],
                    [value: 10, color: "#1e9cbb"],
                    [value: 14, color: "#90d2a7"],
                    [value: 18, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 26, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("oldAverageTemperature", "device.oldaveragetemperature", width: 2, height: 2) {
            state("oldaveragetemperature", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 0, color: "#153591"],
                    [value: 10, color: "#1e9cbb"],
                    [value: 14, color: "#90d2a7"],
                    [value: 18, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 26, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("oldLowestTemperature", "device.oldlowesttemperature", width: 2, height: 2) {
            state("oldlowesttemperature", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 0, color: "#153591"],
                    [value: 10, color: "#1e9cbb"],
                    [value: 14, color: "#90d2a7"],
                    [value: 18, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 26, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("OldHighest", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'Old Highest', unit:"F"
            )
        }
    	valueTile("OldAverage", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'Old Average', unit:"F"
            )
        }
     	valueTile("OldLowest", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'Old Lowest', unit:"F"
            )
        }
        standardTile("Empty", "device.t", width: 6, height: 1) {
            state("temp", label:'TEMPERATURES'
            )
        }
        
        main "averageTemperature"
        details("highestTemperature","averageTemperature","lowestTemperature","Highest","Average","Lowest","Empty","oldHighestTemperature","oldAverageTemperature","oldLowestTemperature","OldHighest","OldAverage","OldLowest")
    }
}

def parse(String description) {
    def pair = description.split(":")
    createEvent(name: pair[0].trim(), value: pair[1].trim(), unit:"F")
}

def installed() {
    initialize()
}

def updated() {
    initialize()
}

def initialize() {
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    sendEvent(name: "healthStatus", value: "online")
    sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
    
    if (!device.currentState("highesttemperature")) {
        setHighestTemperature(getHighestTemperature())
    }
    if (!device.currentState("averagetemperature")) {
        setAverageTemperature(getAverageTemperature())
    }
    if (!device.currentState("lowesttemperature")) {
        setLowestTemperature(getLowestTemperature())
    }
     if (!device.currentState("oldhighesttemperature")) {
        setOldHighestTemperature(getOldHighestTemperature())
    }
    if (!device.currentState("oldaveragetemperature")) {
        setOldAverageTemperature(getOldAverageTemperature())
    }
    if (!device.currentState("oldlowesttemperature")) {
        setOldLowestTemperature(getOldLowestTemperature())
    }   
}

def setLevel(value, rate = null) {
    setTemperature(value)
}


def setHighestTemperature(value) {
    sendEvent(name:"highesttemperature", value: value)
}
def setAverageTemperature(value) {
    sendEvent(name:"averagetemperature", value: value)
}
def setLowestTemperature(value) {
    sendEvent(name:"lowesttemperature", value: value)
}
def setOldHighestTemperature(value) {
    sendEvent(name:"oldhighesttemperature", value: value)
}
def setOldAverageTemperature(value) {
    sendEvent(name:"oldaveragetemperature", value: value)
}
def setOldLowestTemperature(value) {
    sendEvent(name:"oldlowesttemperature", value: value)
}






private getHighestTemperature() {
    def ts = device.currentState("highesttemperature")
    Integer value = ts ? ts.integerValue : 72
    return value
}

private getAverageTemperature() {
    def ts = device.currentState("averagetemperature")
    Integer value = ts ? ts.integerValue : 72
    return value
}

private getLowestTemperature() {
    def ts = device.currentState("lowesttemperature")
    Integer value = ts ? ts.integerValue : 72
    return value
}
private getOldHighestTemperature() {
    def ts = device.currentState("oldhighesttemperature")
    Integer value = ts ? ts.integerValue : 72
    return value
}

private getOldAverageTemperature() {
    def ts = device.currentState("oldaveragetemperature")
    Integer value = ts ? ts.integerValue : 72
    return value
}

private getOldLowestTemperature() {
    def ts = device.currentState("oldlowesttemperature")
    Integer value = ts ? ts.integerValue : 72
    return value
}
