# Weather-Application ![license](https://img.shields.io/badge/license-MIT-blue)  <img style="float: right;" src="https://i.pinimg.com/originals/77/0b/80/770b805d5c99c7931366c2e84e88f251.png" alt=moon width="48" height="48">

A command line application that presents the user with the weather at 
their current location. Written in Java, the application 
uses the Visual Crossing Weather API to get the latest weather 
forecast information as well as alerts in the area. The application presents
the information taken from the Visual Crossing Weather API in a clean 
and organized fashion to its users.

### User Preview
![Preview]([/Users/Kavinraj/Weather_Application/Read Me Resources/User_Interface_Preview.png](https://github.com/ComicBolt/Weather-Application/assets/88584708/8f59766f-30f0-411b-92f4-ae8dbc759109))

### Current Features
- Can display the current temperature at user's location
- Can display the high's and low's of next 15 days at user's location
- Can display the temperature for the next 15 days at user's location
- Can display current alerts at user's location

### Installation
- Step 1: Download the code from github in a directory of your choice 
  using the command below
```bash
  git clone https://github.com/ComicBolt/Weather-Application.git
```
- Step 2: Open the folder named `Weather_Application`, then `build`, and finally `libs`
- Step 3: You should now see a `.jar` file named `Weather_Application-1.0-SNAPSHOT`. Copy the path to the file. 
Be sure to not include the `.jar` file name.
- Step 4: Open your terminal and copy the command below. **Make sure you replace PathToFile with the path to the jar file**
 ```bash
  cd PathToFile
```
- Step 5: Finally copy the command below 
 ```bash
  java -jar Weather_Application-1.0-SNAPSHOT.jar
```

- You are now all set to go!


### To do List

- [x] Basic 15-day weather forecast display
- [ ] Create an option to get the users location automatically
- [ ] Add hour by hour weather forecast
- [ ] Change from command line application to desktop application **Priority**
- [ ] Celsius/Fahrenheit toggle


### Note
This is an experimental project :)


