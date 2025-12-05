Group Members: Vasilios Nicholas, Joseph Sikowitz

Team: Boston Celtics

Project: CS5004, Fall 2025, Homework 9

Our design for the final iteration of this adventure game implements the full MVC architecture. The model contains the elements 
that comprise the main features of the game including fixtures, items, monsters, players, and rooms. All concrete elements in the game
extend the AbstractElement abstract class. Their interfaces also extend the Element interface. We have several interfaces that various
elements implement in order to guarantee certain behavior such as Picturable, Scorable, Activatable, Targeter, Weightable, PlayerAffector,
and Affector. The model also holds a FileProcessor class that handles ingesting a JSON file to start the game and writing to a JSON save 
file so that the user can save and restore the current game state. Finally, the last major component of the model is the AdventureGameModel 
class. This class is the main point of communication for the controller. It provides access to the models components to enable game play 
based on user input and to pass relevant data to the controller. 
The controller is composed of three interfaces: Controller, ICommand, and GameInputOutputProcessor. GameInputOutputProcessor is an Adaptor pattern used by the 
GameController, the class implementing Controller, to process commands entered by the user through the view and adapts as appropriate for the View. 
GameInputOutputProcessor receives raw input from the View and processes it into a UserCommand ordinal. The GameController 
takes the valid input from the user given by the GameInputOutputProcessor and executes the appropriate ICommand associated with it. ICommand is a Command Pattern 
that is contained within a map where UserCommand ordinals are the keys. When executed, the concrete ICommand subtype executes the action on the IAdventureGameModel class
and updates the view based on changes in from the model. Controller package communication with the Model is directly isolated to the ICommand Command Pattern. 
The execute method of the Command pattern returns a boolean encapsulating whether the game over state of the model has been reached to the GameController.
Our team decided to provide the player with four health statuses. Full health is awake, after losing health the player becomes fatigued, after that
the player is woozy, and finally the player is asleep. For scoring, we used the values provided by the JSON but created our own rankings 
(in ascending order): Novice, Squire, Knight, Baron, Prince, and King. We also opted to not change the "user menu" from what is in the specs.

Between homework 8 and homework 9, our Controller design evolved in significant ways. We decoupled the text view from the GameController, making a IAdventureGameView interface
and making the text view a concrete subtype of said interface.  The GUI or graphics view also implements the IAdventureGameView interface.
We also isolated GameController behavior to strictly processing Command Pattern executions. Minimal refactoring was needed for our model: We added an Inventory service class that
encapsulates a Map of Element types hashed by their names in all lowercase. This was done to consolidate/delegate proper formatting of keys for Maps containing elements to one class.
FileProcessor, Room, and Player now use this Inventory service class to store elements. 
The other change to the model was refactoring return types of IAdventureGameModel methods, mainly to pass the pictures for each element to the view. 
The GameInputOutputProcessor subtype for the text view "adapts" this extra data from the model out of the data passed to the text view.

TODO - add HW9 changes below:
SOLID Principle Application:
The largest difference between our original design and the current one was the elimination of the ActionDelegate hierarchy.
Originally, ActionDelegates were to be used by game elements to perform actions on themselves or other game elements. After reviewing the
JSON data provided in homework 8, we decided that this system was overly complex for the new parameters set by the data. As our design
has evolved, we have sought to adhere to the SOLID principles as closely as possible while recognizing that any design choice involves
compromise. As such, within our model, each class has a single responsibility. We have also sought to create our model such that new code 
can be added, but that the existing code does not need to be modified, following the Open/Closed Principle. For instance, the modular nature 
of our interfaces would make it easy for someone to add new elements to the game that implement some of the same functionality as existing 
elements without changing existing ones. Our design has also incorporated the Liskov Substitution Principle. All the interfaces for the game 
elements extend the Element interface, making subtype substitutions for supertypes possible. As for the Interface Segregation Principle, 
we have divided our interfaces as much as possible based on functionality so that downstream code does not depend on things they do not use. 
Finally, we have tried to assure that abstractions do not depend on low-level details meeting the Dependency Inversion Principle.
