# Feature Tasks: LAB 26 

## Health Tracker app
 
###Finger Exercises
One key part of health is building finger strength and endurance. On the main page, display a number and a button. The number should increase when the button is clicked.

Stretch goal: the app should show encouraging messages as the user passes milestones in their button-clicking (perhaps a message at every 10 clicks, and serious props at every 50).

###Stopwatch
Still on the main page, add a stopwatch. Have a button to [Start/Pause] and [Reset] the clock. The start/pause button should toggle between saying “start” and “pause.” And you should only be able to reset when the stopwatch is paused.

Have the view update as fast as possible while still leaving the buttons responsive.

Display the clock as 0:00:00.000 so it should be able to track up to 9 hours, 59 minutes, 59 seconds and 999 milliseconds.

###Inspiring Image Carousel
At the top of our main page, we want to inspire our users with images of the type of person they can become. Display an image with a caption below it. (The caption should NOT be part of the image; it should be text.) Allow users to cycle through images and read the captions. Pressing next should go to the next image and its caption, pressing prev should go back. The app should display a (1/N), (2/N) … indicator so users know how many items are in the list.

* You may choose the images and captions. Have fun.

* Your app must use a class to model the images and their captions. Use the class to manually instantiate each unique item. Add all of the items to an array.

* Your app should use the array of image objects to cycle through what it’s showing.

# Feature Tasks: LAB 27

Move your Finger Exercises and Stopwatch into their own pages of your app. Add buttons on the homepage to link to those pages, and ensure that the user can use the back button on the device to return to the app homepage. (It’s good practice to include some sort of back button on the page as well.)

Health Notifications
Create a new activity for Notifications. Allow users to set up reminders to drink water, which should appear every 2 hours in the notification bar. (For testing, you might want to shorten this to 15 or 30 seconds.)

Styling
Apply consistent styling across the various Activities in your app. Make sure they have a consistent look and feel, and that buttons are in similar locations on each screen.

### Feature Tasks: LAB 28

### Displaying the Exercise Diary
Add a new Activity to hold the Exercise Diary. In that view, display all entries from the Exercise database in a ListView. (At this point, that should show a single row.) Make sure that Exercise looks reasonable.

### Adding to the Exercise Diary
At the top of the Exercise Diary activity, add a form that allows a user to enter data about an exercise. When they hit submit, the information about that exercise should be stored in the database and displayed in the ListView. You can choose how the timestamp works: either let the user enter when they completed the exercise, or use the time when they hit submit on the form

#### Sources for lab26:
* source: https://stackoverflow.com/questions/4597690/android-timer-how-to

# Change Log

Date: 1/10/19:

* Time: 10:00AM
* Description: https://github.com/zahram1087/health-tracker/commit/88970d49c58eb5d952c14c2cda56ecc153ca8a56

* Time : 10:41AM
* Description: updated Readme



# screenshots of App Features:

* HomePage:
![Home_Page](../assets/mainActivity.png)

* FingerExcercise:
![Finger_excercise](../assets/finger.png)

* Timer:
![Timer](../assets/timer.png)

* Notifications:
![Notifications](../assets/notification.png)




