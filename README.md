# velozient_drone
drone test for velozient


# Algorithm used
1.- sort locations by package weight from lighter to heaviest
2.- sort drones by carry capacity from weakest to stronger  
3.- assign lighter packages to the weakest available drone until drone capability is reached
4.- when the drone's capacity is reached, the next available drone starts filling up


# Walkthrough solution
For this solution, first I sort the drones and the locations by package weight from the smallest to greater and then start to filling up the weakest drone until its capacity are reached, if the current package its heavier than the remaining drone capacity, the next drone with higher capacity in the queue starts to be filled. 
I assumed that there is no heavier package than drone capacity.

# Technical dependencies and libraries
This solution was reached with the following characteristic:
•	Visual Studio Code
•	Java 8
Dependencies
•	jUnit: for test
•	mokito: for test
•	commons-io: for read/write files

