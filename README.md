Group Members: Vasilios Nicholas, Joseph Sikowitz

Team: Boston Celtics

Project: CS5004, Fall 2025, Homework 8

Our design for this adventure game uses the model and controller components of the MVC framework. The model contains the elements 
that comprise the main features of the game including fixtures, items, monsters, players, and rooms. All concrete elements in the game
extend the AbstractElement abstract class. Their interfaces also extend the Element interface. We have several interfaces that various
elements implement in order to guarantee certain functionality such as Picturable, Scorable, Activatable, Targeter, Weightable, PlayerAffector,
and Affector. The model also holds a FileProcessor class that handles ingesting a JSON file to start the game and writing to a JSON save 
file so that the user can save and restore the current game state. Finally, the last major component of the model is the AdventureGameModel 
class. This class is the main point of communication for the controller. It provides access to the models components to enable game play 
based on user input. The controller is comprised of two classes: GameController and GameCommandReader. The GameCommandReader is used by the 
GameController to process commands entered by the user. The GameController takes the input from the user given by the GameCommandReader and 
sends it to the model for processing. It is also reponsible for taking a Readable input and appending to an Appendable output.

Between homework 7 and homework 8, our design evolved in significant ways. The creation of the additional interfaces to guarantee 
functionality was a large change as was the creation of a RoomService class to allow for all Room objects to have access to all other Room
objects. The largest difference between our original design and the current one was the elimination of the ActionDelegate hierachy.
Originally, ActionDelegates were to be used by game elements to perform actions on themselves or other game elements. After reviewing the
JSON data provided in homework 8, we decided that this system was overly complex for the new parameters set by the data. As our design
has evolved, we have sought to adhere to the SOLID principles as closely as possible while recognizing that any design choice involves
compromise. As such, within our model, each class has a single responsibility. We have also sought to create our model such that new code 
can be added, but that the existing code does not need to be modified, following the Open/Closed Principle. For instance, the modular nature 
of our interfaces would make it easy for someone to add new elements to the game that implement some of the same functionality as existing 
elements without changing existing ones. Our design has also incorporated the Liskov Substitution Principle. All of interfaces for game 
elements extend the Element interface, making subtype substitutions for supertypes possible. As for the Interface Segregation Principle, 
we have divided our interfaces as much as possible based on functionality so that downstream code does not depend on things they do not use. 
Finally, we have tried to assure that abstractions do not depend on low-level details meeting the Dependency Inversion Principle.
