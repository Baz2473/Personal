/*
  Copyright (C) 2017 Baz2473
  
  Name: Area Occupancy

  Version: 1.0.0

*/

definition (
    name: "Area Occupancy",
    namespace: "Baz2473",
    author: "Baz2473",
    description: "This app creates the AREAS you wish to monitor the occupancy status of.",
    category: "My Apps",
    singleInstance: true,
    iconUrl: "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao1.png",
	iconX2Url: "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao2.png",
	iconX3Url: "https://raw.githubusercontent.com/Baz2473/Personal/master/Ao3.png"
)

preferences	{
	page(name: "mainPage", title: "Installed Area(s)", install: true, uninstall: true, submitOnChange: true) {
		section {
            app(name: "Area Occupancy", appName: "Area Occupancy Child App", namespace: "Baz2473", title: "Create a 'NEW' area to monitor", multiple: true)
		}
        remove("Remove Area Occupancy?", "Are You Sure You Want To Remove Area Occupancy Completley!", "Removing Area Occupancy Will Also Remove All Area's Created By This SmartApp")
	}
}
def installed()		{	initialize()	}
def updated()		{
	initialize()
}
def initialize()	{
	log.info "Area Occupancy: there are ${childApps.size()} Areas."
	childApps.each	{ child ->
    log.info "Area Occupancy: Area: ${child.label}"
}}
