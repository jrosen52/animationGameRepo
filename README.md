CS 441 Third Project

For our third project, we were assigned to create a game that involves animation in some way. I choose to do a Pong game, since 
that seemed like a simple yet fun game that could be easily modified. I based the core game on an implementation from Game
Code School (http://gamecodeschool.com/android/programming-a-pong-game-for-android/), then did my own modifications. This includes
haveing the background color change with each ball collision, and adding powerups that either grant an extra life or an extra 
ball.

There are 5 main classes: MainActivity, PongView, Bar, Ball, and Powerup. The Bar, ball, and powerup classes create those
respective objects.Most of the implementation for how the game is created and operated is contained in PongView. There the 
canvas is created, the objects are created, and how the objects react to each other is dictated. In MainActivity, the PongView
is implemeted in onCreate and can be paused and resumed.

Here is documentation for each git commit:

Sep 25: Initial project created

Sep 29: Created class for the ball object

Sep 30: Created class for bar object

Oct 2: Added class that creates the pong view

Oct 4: Added touch controls

Oct 7: Bug fixes to properly create objects in Pong View

Oct 9: Change color with each ball bounce

Oct 10: Added class for powerups

Oct 11: Fixed powerup creation

Oct 12: Completed extra ball powerup

Oct 13: Added extra life powerup and other fixes
