# Adventure Game

This project was created for the final project submission for CS5004 - Object Oriented Design during the Fall 2025 semester at Northeastern University in Boston, MA.

## Authors

- Vasilios Nicholas
- Joseph Sikowitz

## Project Design

### High Level Design

Our design for the final iteration of this adventure game implements the full MVC architecture.

#### Model

The model contains interfaces of the elements
that comprise the main features of the game including fixtures, items, monsters, players, and rooms. All concrete elements in the game
extend the `AbstractElement` abstract class. Their interfaces also extend the `Element` interface. We have several interfaces that various
elements implement in order to guarantee certain behavior such as `Picturable`, `Scorable`, `Activatable`, `Targeter`, `Weightable`, `PlayerAffector`,
and `Affector`. Given that we are developing a game-engine based on a process-flow engine as opposed to a finished, shippable game, each element has an interface,
giving our model the maximum possible design reuse for anyone seeking to add additional elements or additional subtypes of elements to the game.
The model also holds a `FileProcessor` class that handles ingesting a JSON file to start the game and writing to a JSON save
file so that the user can save and restore the current game state. Finally, the last major component of the model is the `AdventureGameModel`
class. This class is the main point of communication for the controller. It provides access to the models components to enable game play
based on user input and to pass relevant data to the controller.

#### Controller

The controller is composed of three interfaces: `Controller`, `ICommand`, and `GameInputOutputProcessor`. `GameInputOutputProcessor` is an Adaptor pattern used by the
`GameController`, the class implementing Controller, to process commands entered by the user through the view and adapts as appropriate for the View.
`GameInputOutputProcessor` receives raw input from the View and processes it into a `UserCommand` ordinal. The `GameController` takes the valid input from the user given
by the `GameInputOutputProcessor` and executes the appropriate `ICommand` associated with it. `ICommand` is a Command Pattern
that is contained within a map where `UserCommand` ordinals are the keys. When executed, the concrete `ICommand` subtype executes the action on the `IAdventureGameModel` class
and updates the view based on changes in from the model. Controller package communication with the Model is directly isolated to the `ICommand` command pattern.
The execute method of the command pattern returns a boolean encapsulating whether the game over state of the model has been reached to the `GameController`.
Our team decided to provide the player with four health statuses. Full health is awake, after losing health the player becomes fatigued, after that
the player is woozy, and finally the player is asleep.

#### View

We have two `IAdventureGameView` subtypes:

1. `IAdventureGameTextView` for CLI-/text-based view.
2. `IAdventureGameGraphicView` for GUI-/graphics-based view.

For scoring, we used the values provided by the JSON but created our own rankings
:

1. Novice
2. Squire
3. Knight
4. Baron
5. Prince
6. King

We also opted to not change the "user menu" from what is was in the specs for the assignment.

### UML

- View our UML Class Diagram for full project structure

- View the 2 UML Object Diagrams for object instances created during a run of the game.
- View the 2 UML Sequence Diagrams to see the interactions between the objects specified in the object diagrams.

[View all diagrams here](https://github.com/vasiliosnicholas/Adventure-Game/blob/HW9/HW9_UML_Scenarios_Team_Boston_Celtics.pdf?raw=true)

## Instructions

Notes for running our game_engine.jar: 1. Please run `game_engine.jar` with Java 25. 2. Please place the images for the game in the following relative path: `resources/images/` 3. The JSON data files may either be placed in `resources/` or within the same directory as the jar file. 4. While the dialog boxes for the Take, Examine, and Answer buttons render and scale properly on Windows, please ensure
that they display all items and/or fixtures that are present under the Room description part of the GUI.
If these dialog windows aren't displaying all elements, the dialog window may need to be resized manually.

Between homework 8 and homework 9, our Controller design evolved in significant ways. We decoupled the text view from the `GameController`, made an `IAdventureGameView` interface,
and made the text view a concrete subtype of said interface. The GUI or graphics view also implements the `IAdventureGameView` and the `IAdventureGameGraphicView` interfaces.
The `IAdventureGameGraphicView` adds a method for Event-based models to set the view's event handler.
The graphics view handles GUI specific actions, such as opening a menu or opening a dialog box internally. The graphics view only sends game specific commands back to the controller.
We also isolated `GameController` behavior to strictly process command pattern executions. Minimal refactoring was needed for our model: We added an Inventory service class that
encapsulates a `Map` of `Element` types hashed by their names in all lowercase. This was done to consolidate/delegate proper formatting of keys for `Maps` containing `Element` types to one class,
thereby improving the single responsibility principle of classes that need to store `Element` types. `FileProcessor`, `Room`, and `Player` now use this `Inventory` service class to store `Element` types.
The other change to the model was refactoring return types of `IAdventureGameModel` methods, mainly to pass the pictures for each element to the view.
The `GameInputOutputProcessor` subtype for the text view "adapts" this extra data from the model out of the data that is passed to the text view, thus functioning as an Adaptor Pattern.

## SOLID Principle Application

As our design has evolved over the course of the semester, we have sought to adhere to the SOLID principles as closely as possible while recognizing that any design choice involves
compromise. As such, within each of our packages, each class has a single responsibility. We have also sought to create our model such that new code
can be added, but that the existing code does not need to be modified, following the Open/Closed Principle. For instance, the modular nature
of our interfaces would make it easy for someone to add new elements to the game that implement some of the same functionality as existing
elements without changing existing ones. Our design has also incorporated the Liskov Substitution Principle. All the interfaces for the game
elements extend the Element interface, making subtype substitutions for supertypes possible. As for the Interface Segregation Principle,
we have divided our interfaces as much as possible based on functionality so that downstream code does not depend on things they do not use.
Finally, we have tried to assure that abstractions do not depend on low-level details meeting the Dependency Inversion Principle.

### Separation of Concerns - Communication between MVC components in our code is limited to one class per module

- Controller Interface that communicates with Model: `GameInputOutputProcessor`
- Model Interface that communicates with Controller: `IAdventureGameModel`
- Controller Interface that communicates with View: `GameInputOutputProcessor`
- View Interface that communicates with Controller: `IAdventureGameView` for text view or `IAdventureGameGraphicView` for graphics/GUI view.

## Project Structure

```bash
Adventure-Game/
├── batchtest.txt
├── cs5004-hw8-test.iml
├── game_engine.jar
├── HW9_UML_Scenarios_Team_Boston_Celtics.pdf
├── lib
│   ├── jackson-annotations-2.17.2.jar
│   ├── jackson-annotations-2.17.2-javadoc.jar
│   ├── jackson-annotations-2.17.2-sources.jar
│   ├── jackson-core-2.17.2.jar
│   ├── jackson-core-2.17.2-javadoc.jar
│   ├── jackson-core-2.17.2-sources.jar
│   ├── jackson-core-asl-1.8.6.jar
│   ├── jackson-databind-2.17.2.jar
│   ├── jackson-databind-2.17.2-javadoc.jar
│   ├── jackson-databind-2.17.2-sources.jar
│   ├── jackson-datatype-json-org-1.8.0.jar
│   ├── jackson-mapper-asl-1.8.6.jar
│   ├── json-20090211.jar
│   └── JUnit5.8.1
│       ├── apiguardian-api-1.1.2.jar
│       ├── junit-jupiter-5.8.1.jar
│       ├── junit-jupiter-api-5.8.1.jar
│       ├── junit-jupiter-engine-5.8.1.jar
│       ├── junit-jupiter-params-5.8.1.jar
│       ├── junit-platform-commons-1.8.1.jar
│       ├── junit-platform-engine-1.8.1.jar
│       └── opentest4j-1.2.0.jar
├── README.md
├── resources
│   ├── alignquest.json
│   ├── empty_rooms.json
│   ├── empty_rooms_missing_data.json
│   ├── images
│   │   ├── algorithms.png
│   │   ├── billboard.png
│   │   ├── bridge.png
│   │   ├── coming-soon.png
│   │   ├── congratulations.png
│   │   ├── courtyard.png
│   │   ├── darkness.png
│   │   ├── dining.png
│   │   ├── east.png
│   │   ├── entrance.png
│   │   ├── epic_adventurer.png
│   │   ├── exhibit-1.png
│   │   ├── exhibit-2.png
│   │   ├── exhibit-3.png
│   │   ├── foyer.png
│   │   ├── game_engine.png
│   │   ├── generic_item.png
│   │   ├── generic_location.png
│   │   ├── generic-monster.png
│   │   ├── generic_monster.png
│   │   ├── generic_puzzle.png
│   │   ├── golden-ticket.png
│   │   ├── hair-clippers.png
│   │   ├── hq.png
│   │   ├── key.png
│   │   ├── kitchen.png
│   │   ├── lamp.png
│   │   ├── library-before.png
│   │   ├── library.png
│   │   ├── livingroom.png
│   │   ├── monitor-room.png
│   │   ├── monster-rabbit.png
│   │   ├── monster-teddy.png
│   │   ├── museum.png
│   │   ├── nighty_night.png
│   │   ├── north.png
│   │   ├── plate.png
│   │   ├── profk.png
│   │   ├── robot.png
│   │   ├── secret_room.png
│   │   ├── south.png
│   │   ├── spooky-voice.png
│   │   ├── study.png
│   │   └── west.png
│   ├── museum.json
│   └── simple_hallway.json
├── src
│   ├── controller
│   │   ├── AbstractCommand.java
│   │   ├── AnswerCommand.java
│   │   ├── Controller.java
│   │   ├── DropCommand.java
│   │   ├── EastCommand.java
│   │   ├── EndOfTurnActionsCommand.java
│   │   ├── ExamineCommand.java
│   │   ├── GameController.java
│   │   ├── GameGraphicInputOutputProcessor.java
│   │   ├── GameInputOutputProcessor.java
│   │   ├── GameTextInputOutputProcessor.java
│   │   ├── ICommand.java
│   │   ├── InvalidArgumentCommand.java
│   │   ├── InvalidCommand.java
│   │   ├── InventoryCommand.java
│   │   ├── LookCommand.java
│   │   ├── NorthCommand.java
│   │   ├── QuitCommand.java
│   │   ├── RestoreCommand.java
│   │   ├── SaveCommand.java
│   │   ├── SouthCommand.java
│   │   ├── StartGameCommand.java
│   │   ├── TakeCommand.java
│   │   ├── UseCommand.java
│   │   ├── UserCommands.java
│   │   ├── UserPrompts.java
│   │   ├── WaitCommand.java
│   │   └── WestCommand.java
│   ├── enginedriver
│   │   └── GameEngineApp.java
│   ├── META-INF
│   │   └── MANIFEST.MF
│   ├── model
│   │   ├── AbstractElement.java
│   │   ├── AbstractPuzzle.java
│   │   ├── Activatable.java
│   │   ├── AdventureGameModel.java
│   │   ├── Affector.java
│   │   ├── CannotGetRoomException.java
│   │   ├── ConcreteFixture.java
│   │   ├── ConcreteItem.java
│   │   ├── ConcreteMonster.java
│   │   ├── ConcretePlayer.java
│   │   ├── ConcretePuzzle.java
│   │   ├── ConcreteRoom.java
│   │   ├── Directions.java
│   │   ├── Element.java
│   │   ├── EnvironmentAffectedForPlayer.java
│   │   ├── FileProcessor.java
│   │   ├── Fixture.java
│   │   ├── FixtureJsonFields.java
│   │   ├── HealthStatus.java
│   │   ├── IAdventureGameModel.java
│   │   ├── Inventory.java
│   │   ├── Item.java
│   │   ├── ItemJsonFields.java
│   │   ├── JsonFields.java
│   │   ├── Monster.java
│   │   ├── MonsterJsonFields.java
│   │   ├── Picturable.java
│   │   ├── PlayerAffector.java
│   │   ├── Player.java
│   │   ├── PlayerJsonFields.java
│   │   ├── PlayerRanks.java
│   │   ├── Puzzle.java
│   │   ├── PuzzleJsonFields.java
│   │   ├── Room.java
│   │   ├── RoomJsonFields.java
│   │   ├── RoomService.java
│   │   ├── RoomStatus.java
│   │   ├── Scorable.java
│   │   ├── TakeItemStatus.java
│   │   ├── Targeter.java
│   │   ├── UseSuccessful.java
│   │   └── Weightable.java
│   └── view
│       ├── AdventureGameGraphicView.java
│       ├── AdventureGameTextView.java
│       ├── IAdventureGameGraphicView.java
│       └── IAdventureGameView.java
└── test
   ├── controller
   │   ├── CommandPatternTest.java
   │   ├── GameControllerTest.java
   │   ├── GameTextInputOutputProcessorTest.java
   │   ├── UserCommandsTest.java
   │   └── UserPromptsTest.java
   └── model
       ├── AbstractElementTest.java
       ├── AbstractPuzzleTest.java
       ├── AdventureGameModelTest.java
       ├── CannotGetRoomExceptionTest.java
       ├── ConcreteFixtureTest.java
       ├── ConcreteItemTest.java
       ├── ConcreteMonsterTest.java
       ├── ConcretePlayerTest.java
       ├── ConcretePuzzleTest.java
       ├── ConcreteRoomTest.java
       ├── DirectionsTest.java
       ├── FileProcessorTest.java
       ├── FixtureJsonFieldsTest.java
       ├── HealthStatusTest.java
       ├── ItemJsonFieldsTest.java
       ├── JsonFieldsTest.java
       ├── MonsterJsonFieldsTest.java
       ├── PlayerJsonFieldsTest.java
       ├── PlayerRanksTest.java
       ├── PuzzleJsonFieldsTest.java
       ├── RoomJsonFieldsTest.java
       ├── RoomServiceTest.java
       ├── RoomStatusTest.java
       ├── TakeItemStatusTest.java
       └── UseSuccessfulTest.java
```
