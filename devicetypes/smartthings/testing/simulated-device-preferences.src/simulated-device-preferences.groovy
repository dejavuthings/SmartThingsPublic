/**
 *  Copyright 2019 SmartThings
 *
 *  DTH showing example preference usage and to facilitate testing
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
	definition (name: "Simulated Device Preferences", namespace: "smartthings/testing", author: "SmartThings", mnmn: "SmartThings", vid: "generic-switch") {
		capability "Actuator"
		capability "Sensor"
		capability "Switch"
	}

	preferences {
		section {
			input(title: "Section 1 Title",
					description: "Section 1 Description",
					displayDuringSetup: false,
					type: "paragraph",
					element: "paragraph")
			input("textInput", "text",
					title: "Text Title",
					description: "Text Description",
					defaultValue: "default value",
					required: false)
			input("enumInput", "enum",
					title: "Enum Title (key/value options)",
					description: "Enum Description (key/value options)",
					options: ["Option1Key":"Option 1 Value", "Option2Key":"Option 2 Value", "Option3Key":"Option 3 Value", "Option4Key":"Option 4 Value"],
					defaultValue: "Option1Key",
					required: false)
			input("enumInput2", "enum",
					title: "Enum Title 2 (List of options)",
					description: "Enum Description 2 (List of options)",
					options: ["Option 1 Value", "Option 2 Value", "Option 3 Value", "Option 4 Value"],
					defaultValue: "Option 1 Value",
					required: false)
			input("enumInput3", "enum",
					title: "Enum Title 3 (no options)", description: "Enum Description 3 (no options)",
					required: false)
			input("enumInput4", "enum",
					title: "Enum Title 4 (Lists of Maps options)",
					description: "Enum Description 4 (Lists of Maps options)",
					options: [
							["Option 1 Value":"Option 1 Value"],
							["Option 2 Value":"Option 2 Value"],
							["Option 3 Value":"Option 3 Value"],
							["Option 4 Value":"Option 4 Value"]],
					defaultValue: "Option 1 Value",
					required: false)
			input("booleanInput", "boolean",
					title: "Boolean Title",
					description: "Boolean Description",
					defaultValue: "true",
					required: false)
			input("boolInput", "bool",
					title: "Bool Title",
					description: "Bool Description",
					defaultValue: false,
					required: false)
		}
		section {
			input(title: "Section 2 Title",
					description: "Section 2 Description",
					displayDuringSetup: false,
					type: "paragraph",
					element: "paragraph")
			input("numInput", "number",
					title: "Number Title (range 1-10)",
					description: "Number Description (range 1-10)",
					defaultValue: 5,
					range: "1..10",
					required: false)
			input("numInput2", "number",
					title: "Number Title (range -10-10)",
					description: "Number Description (range -10-10)",
					defaultValue: 5,
					range: "-10..10",
					required: false)
			input("numInput3", "number",
					title: "Number Title (range *..*)",
					description: "Number Description (range *..*)",
					defaultValue: 5,
					range: "*..*",
					required: false)
			input("numInput4", "number",
					title: "Number Title (no range)",
					description: "Number Description (no range)",
					defaultValue: 5,
					required: false)
			input("decInput", "decimal",
					title: "Decimal Title",
					description: "Decimal Description",
					defaultValue: "5.0",
					required: false)
			input("passInput", "password",
					title: "Password Title",
					description: "Password Description",
					defaultValue: "default password",
					required: false)
		}
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.Home.home30", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.Home.home30", backgroundColor:"#FFFFFF", nextState:"turningOn", defaultState: true
				attributeState "turningOn", label:'Turning On', action:"switch.off", icon:"st.Home.home30", backgroundColor:"#00A0DC", nextState:"turningOn"
				attributeState "turningOff", label:'Turning Off', action:"switch.on", icon:"st.Home.home30", backgroundColor:"#FFFFFF", nextState:"turningOff"
			}
		}

		standardTile("explicitOn", "device.switch", width: 2, height: 2, decoration: "flat") {
			state "default", label: "On", action: "switch.on", icon: "st.Home.home30", backgroundColor: "#ffffff"
		}
		standardTile("explicitOff", "device.switch", width: 2, height: 2, decoration: "flat") {
			state "default", label: "Off", action: "switch.off", icon: "st.Home.home30", backgroundColor: "#ffffff"
		}

		main(["switch"])
		details(["switch", "explicitOn", "explicitOff"])

	}
}

def parse(description) {
}

def updated() {
	Map newPreferences = [
		booleanInput: booleanInput,
		boolInput: boolInput,
		decInput: decInput,
		enumInput: enumInput,
		enumInput2: enumInput2,
		enumInput3: enumInput3,
		enumInput4: enumInput4,
		numInput: numInput,
		numInput2: numInput2,
		numInput3: numInput3,
		numInput4: numInput4,
		passInput: passInput,
		textInput: textInput
	]
	newPreferences.each { k, v ->
		if (state.preferences[k] != v) {
			log.debug "Updating '$k' ${state.preferences[k]} -> $v"
		}
	}
	state.preferences = newPreferences
}

def on() {
	sendEvent(name: "switch", value: "on", isStateChange: true)
}

def off() {
	sendEvent(name: "switch", value: "off", isStateChange: true)
}

def installed() {
	on()
}
