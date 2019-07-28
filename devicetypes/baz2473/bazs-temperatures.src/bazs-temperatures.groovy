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
    definition (name: "Baz's Temperatures", namespace: "Baz2473", author: "Baz2473") {
        capability "Temperature Measurement"
        capability "Switch Level"
        capability "Sensor"
        capability "Health Check"  
        command "setTemp1", ["number"]
        command "setTemp2", ["number"]
        command "setTemp3", ["number"]
 		command "setTemp4", ["number"]
        command "setTemp5", ["number"]
        command "setTemp6", ["number"]
        command "setTemp7", ["number"]
        command "setTemp8", ["number"]
        command "setTemp9", ["number"]
 		command "setTemp10", ["number"]
        command "setTemp11", ["number"]
        command "setTemp12", ["number"]
        command "setTemp13", ["number"]
        command "setTemp14", ["number"]
        command "setTemp15", ["number"]
 		command "setTemp16", ["number"]
        command "setTemp17", ["number"]
        command "setTemp18", ["number"]
        command "setTemp19", ["number"]
        command "setTemp20", ["number"]
        command "setTemp21", ["number"]
 		command "setTemp22", ["number"]
        command "setTemp23", ["number"]
        command "setTemp24", ["number"]
        command "setTemp25", ["number"]

        
        
        
        
        
        
    }

    tiles (scale: 2) {   
        valueTile("temp1", "device.temp1", width: 1, height: 1) {
            state("temp1", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp2", "device.temp2", width: 1, height: 1) {
            state("temp2", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp3", "device.temp3", width: 1, height: 1) {
            state("temp3", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp4", "device.temp4", width: 1, height: 1) {
            state("temp4", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp5", "device.temp5", width: 1, height: 1) {
            state("temp5", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp6", "device.temp6", width: 1, height: 1) {
            state("temp6", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp7", "device.temp7", width: 1, height: 1) {
            state("temp7", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp8", "device.temp8", width: 1, height: 1) {
            state("temp8", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp9", "device.temp9", width: 1, height: 1) {
            state("temp9", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp10", "device.temp10", width: 1, height: 1) {
            state("temp10", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp11", "device.temp11", width: 1, height: 1) {
            state("temp11", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp12", "device.temp12", width: 1, height: 1) {
            state("temp12", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp13", "device.temp13", width: 1, height: 1) {
            state("temp13", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp14", "device.temp14", width: 1, height: 1) {
            state("temp14", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp15", "device.temp15", width: 1, height: 1) {
            state("temp15", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp16", "device.temp16", width: 1, height: 1) {
            state("temp16", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp17", "device.temp17", width: 1, height: 1) {
            state("temp17", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp18", "device.temp18", width: 1, height: 1) {
            state("temp18", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp19", "device.temp19", width: 1, height: 1) {
            state("temp19", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp20", "device.temp20", width: 1, height: 1) {
            state("temp20", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp21", "device.temp21", width: 1, height: 1) {
            state("temp21", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp22", "device.temp22", width: 1, height: 1) {
            state("temp22", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp23", "device.temp23", width: 1, height: 1) {
            state("temp23", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp24", "device.temp24", width: 1, height: 1) {
            state("temp24", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }
        valueTile("temp25", "device.temp25", width: 1, height: 1) {
            state("temp25", label:'${currentValue}', unit:"F",
                backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 20, color: "#1e9cbb"],
                    [value: 22, color: "#90d2a7"],
                    [value: 24, color: "#44b621"],
                    [value: 26, color: "#f1d801"],
                    [value: 28, color: "#d04e00"],
                    [value: 30, color: "#bc2323"]
                ]
            )
        }

        valueTile("Highest", "device.temperature", width: 1, height: 1) {
            state("temperature", label:'Highest', unit:"F"
            )
        }
    	valueTile("Average", "device.temperature", width: 1, height: 1) {
            state("temperature", label:'Average', unit:"F"
            )
        }
     	valueTile("Lowest", "device.temperature", width: 1, height: 1) {
            state("temperature", label:'Lowest', unit:"F"
            )
        }
        
        valueTile("OldHighest", "device.temperature", width: 1, height: 1) {
            state("temperature", label:'Old Highest', unit:"F"
            )
        }
    	valueTile("OldAverage", "device.temperature", width: 1, height: 1) {
            state("temperature", label:'Old Average', unit:"F"
            )
        }
     	valueTile("OldLowest", "device.temperature", width: 1, height: 1) {
            state("temperature", label:'Old Lowest', unit:"F"
            )
        }
        standardTile("Empty", "device.t", width: 1, height: 1) {
            state("temp", label:''
            )
        }
        
        main "temp1"
        details("temp1","Empty","Empty","Empty","Empty","Empty","temp21","Empty","Empty","Empty","Empty","temp2","Empty","temp3","Empty","Empty","Empty","temp4","temp5","temp6","temp7","temp8","Empty","Empty","Empty","temp22","temp9","Empty","temp10","Empty","Empty","Empty","Empty","Empty","temp11","temp12","Empty","temp13","temp14","Empty","temp15","temp16","temp19","temp17","temp18","Empty","Empty","Empty","Empty","Empty","temp20","Empty","Empty","Empty")
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
    
    if (!device.currentState("temp1")) {
        setTemp1(getTemp1())
    }
    if (!device.currentState("temp2")) {
        setTemp2(getTemp2())
    }
    if (!device.currentState("temp3")) {
        setTemp3(getTemp3())
    }
     if (!device.currentState("temp4")) {
        setTemp4(getTemp4())
    }
    if (!device.currentState("temp5")) {
        setTemp5(getTemp5())
    }
    if (!device.currentState("temp6")) {
        setTemp6(getTemp6())
    }   
    if (!device.currentState("temp7")) {
        setTemp7(getTemp7())
    }
    if (!device.currentState("temp8")) {
        setTemp8(getTemp8())
    }
    if (!device.currentState("temp9")) {
        setTemp9(getTemp9())
    }
     if (!device.currentState("temp10")) {
        setTemp10(getTemp10())
    }
    if (!device.currentState("tem11")) {
        setTemp11(getTemp11())
    }
    if (!device.currentState("temp12")) {
        setTemp12(getTemp12())
    }   
    if (!device.currentState("temp13")) {
        setTemp13(getTemp13())
    }
    if (!device.currentState("temp14")) {
        setTemp14(getTemp14())
    }
    if (!device.currentState("temp15")) {
        setTemp15(getTemp15())
    }
     if (!device.currentState("temp16")) {
        setTemp16(getTemp16())
    }
    if (!device.currentState("temp17")) {
        setTemp17(getTemp17())
    }
    if (!device.currentState("temp18")) {
        setTemp18(getTemp18())
    }   
    if (!device.currentState("temp19")) {
        setTemp19(getTemp19())
    }
    if (!device.currentState("temp20")) {
        setTemp20(getTemp20())
    }
    if (!device.currentState("temp21")) {
        setTemp21(getTemp21())
    }
     if (!device.currentState("temp22")) {
        setTemp22(getTemp22())
    }
    if (!device.currentState("temp23")) {
        setTemp23(getTemp23())
    }
    if (!device.currentState("temp24")) {
        setTemp24(getTemp24())
    }   
    if (!device.currentState("temp25")) {
        setTemp25(getTemp25())
    } 
    
    
    
    
}

def setLevel(value, rate = null) {
    setTemperature(value)
}


def setTemp1(value) {
    sendEvent(name:"temp1", value: value)
}
def setTemp2(value) {
    sendEvent(name:"temp2", value: value)
}
def setTemp3(value) {
    sendEvent(name:"temp3", value: value)
}
def setTemp4(value) {
    sendEvent(name:"temp4", value: value)
}
def setTemp5(value) {
    sendEvent(name:"temp5", value: value)
}
def setTemp6(value) {
    sendEvent(name:"temp6", value: value)
}
def setTemp7(value) {
    sendEvent(name:"temp7", value: value)
}
def setTemp8(value) {
    sendEvent(name:"temp8", value: value)
}
def setTemp9(value) {
    sendEvent(name:"temp9", value: value)
}
def setTemp10(value) {
    sendEvent(name:"temp10", value: value)
}
def setTemp11(value) {
    sendEvent(name:"temp11", value: value)
}
def setTemp12(value) {
    sendEvent(name:"temp12", value: value)
}
def setTemp13(value) {
    sendEvent(name:"temp13", value: value)
}
def setTemp14(value) {
    sendEvent(name:"temp14", value: value)
}
def setTemp15(value) {
    sendEvent(name:"temp15", value: value)
}
def setTemp16(value) {
    sendEvent(name:"temp16", value: value)
}
def setTemp17(value) {
    sendEvent(name:"temp17", value: value)
}
def setTemp18(value) {
    sendEvent(name:"temp18", value: value)
}
def setTemp19(value) {
    sendEvent(name:"temp19", value: value)
}
def setTemp20(value) {
    sendEvent(name:"temp20", value: value)
}
def setTemp21(value) {
    sendEvent(name:"temp21", value: value)
}
def setTemp22(value) {
    sendEvent(name:"temp22", value: value)
}
def setTemp23(value) {
    sendEvent(name:"temp23", value: value)
}
def setTemp24(value) {
    sendEvent(name:"temp24", value: value)
}
def setTemp25(value) {
    sendEvent(name:"temp25", value: value)
}






private getTemp1() {
    def ts = device.currentState("temp1")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp2() {
    def ts = device.currentState("temp2")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp3() {
    def ts = device.currentState("temp3")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp4() {
    def ts = device.currentState("temp4")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp5() {
    def ts = device.currentState("temp5")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp6() {
    def ts = device.currentState("temp6")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp7() {
    def ts = device.currentState("temp7")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp8() {
    def ts = device.currentState("temp8")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp9() {
    def ts = device.currentState("temp9")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp10() {
    def ts = device.currentState("temp10")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp11() {
    def ts = device.currentState("temp11")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp12() {
    def ts = device.currentState("temp12")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp13() {
    def ts = device.currentState("temp13")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp14() {
    def ts = device.currentState("temp14")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp15() {
    def ts = device.currentState("temp15")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp16() {
    def ts = device.currentState("temp16")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp17() {
    def ts = device.currentState("temp17")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp18() {
    def ts = device.currentState("temp18")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp19() {
    def ts = device.currentState("temp19")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp20() {
    def ts = device.currentState("temp20")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp21() {
    def ts = device.currentState("temp21")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp22() {
    def ts = device.currentState("temp22")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp23() {
    def ts = device.currentState("temp23")
    Integer value = ts ? ts.integerValue : 0
    return value
}

private getTemp24() {
    def ts = device.currentState("temp24")
    Integer value = ts ? ts.integerValue : 0
    return value
}
private getTemp25() {
    def ts = device.currentState("temp25")
    Integer value = ts ? ts.integerValue : 0
    return value
}



