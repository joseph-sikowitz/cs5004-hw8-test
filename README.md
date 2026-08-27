# Adventure Game

A Text- and GUI-based adventure game written in Java that loads game files from JSON format and allows users to save and load their progress.
This project is our final project submission for CS5004 - Object Oriented Design, taught by Dr. Keith Bagley, during the Fall 2025 semester at Northeastern University in Boston, MA.

## Authors

- Vasilios Nicholas
- Joseph Sikowitz

## Instructions

Notes for running our game_engine.jar:

1. Please build and run `game_engine.jar` with Java 25.
2. Please place the images for the game in the following relative path: `resources/images/`
3. The JSON data files may either be placed in `resources/` or within the same directory as the jar file.
4. While the dialog boxes for the Take, Examine, and Answer buttons render and scale properly on Windows, please ensure
   that they display all items and/or fixtures that are present under the Room description part of the GUI.
   If these dialog windows aren't displaying all elements, the dialog window may need to be resized manually.

### Installation & Setup

#### 1. Prerequisites

| Requirement              | Notes                                                                                                                   |
| ------------------------ | ----------------------------------------------------------------------------------------------------------------------- |
| JDK 25                   | The version the project targets, per the README and the Tech Stack section. Temurin builds are available from Adoptium. |
| Git                      | Used only for cloning.                                                                                                  |
| `curl` (or `wget`)       | Used for fetching JARs from Maven Central.                                                                              |
| IntelliJ IDEA (optional) | A module file (`cs5004-hw8-test.iml`) is already provided.                                                              |

Verification:

```bash
java -version && javac -version
```

#### 2. Clone

```bash
git clone https://github.com/joseph-sikowitz/Adventure-Game
cd Adventure-Game
```

#### 3. Download dependencies

Every artifact below is fetched from Maven Central and must be present before anything will compile. The versions listed are those the source was written against.

##### 3.1 Runtime / compile dependencies

| Artifact (Maven coordinates)                            | File                             |
| ------------------------------------------------------- | -------------------------------- |
| `com.fasterxml.jackson.core:jackson-core:2.17.2`        | `jackson-core-2.17.2.jar`        |
| `com.fasterxml.jackson.core:jackson-annotations:2.17.2` | `jackson-annotations-2.17.2.jar` |
| `com.fasterxml.jackson.core:jackson-databind:2.17.2`    | `jackson-databind-2.17.2.jar`    |

```bash
mkdir -p lib
BASE=https://repo1.maven.org/maven2
JV=2.17.2

for A in jackson-core jackson-annotations jackson-databind; do
  curl -fL -o "lib/$A-$JV.jar" \
    "$BASE/com/fasterxml/jackson/core/$A/$JV/$A-$JV.jar"
done
```

`jackson-databind` requires the other two Jackson artifacts at the same version, so the trio must be kept in lockstep.

Only `com.fasterxml.jackson.databind.JsonNode` and `ObjectMapper` are imported by the sources inspected so far; `jackson-core` and `jackson-annotations` are required as transitive dependencies of `jackson-databind` rather than being imported directly. No `org.json` import appears, and the module configuration declares no `org.json` library, so `org.json:json:20090211` appears unused. It is fetched only if `javac` reports an unresolved `org.json` import:

```bash
curl -fL -o lib/json-20090211.jar \
  "$BASE/org/json/json/20090211/json-20090211.jar"
```

Matching `-sources` and `-javadoc` artifacts may also be fetched (same URL with `-sources.jar` or `-javadoc.jar` appended) purely for IDE documentation; they are never placed on the classpath. Legacy `jackson-*-asl` 1.x and `jackson-datatype-json-org` artifacts are superseded by 2.17.2 and should not be downloaded.

##### 3.2 Test dependencies

```bash
mkdir -p lib/JUnit5.8.1
JUP=5.8.1; PLT=1.8.1

for A in junit-jupiter junit-jupiter-api junit-jupiter-engine junit-jupiter-params; do
  curl -fL -o "lib/JUnit5.8.1/$A-$JUP.jar" \
    "$BASE/org/junit/jupiter/$A/$JUP/$A-$JUP.jar"
done

for A in junit-platform-commons junit-platform-engine; do
  curl -fL -o "lib/JUnit5.8.1/$A-$PLT.jar" \
    "$BASE/org/junit/platform/$A/$PLT/$A-$PLT.jar"
done

curl -fL -o lib/JUnit5.8.1/opentest4j-1.2.0.jar \
  "$BASE/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar"
curl -fL -o lib/JUnit5.8.1/apiguardian-api-1.1.2.jar \
  "$BASE/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar"

# Required for the test task (section 6)
curl -fL -o lib/JUnit5.8.1/junit-platform-console-standalone-$PLT.jar \
  "$BASE/org/junit/platform/junit-platform-console-standalone/$PLT/junit-platform-console-standalone-$PLT.jar"
```

The first eight artifacts correspond exactly to the `JUnit5.8.1` module library declared at `TEST` scope in `cs5004-hw8-test.iml`. The console-standalone launcher is additional and is not referenced by the module.

##### 3.3 Integrity check (optional)

Each Maven Central artifact is published alongside a `.sha1` file, so a download can be validated by appending `.sha1` to the URL and comparing:

```bash
shasum lib/jackson-databind-2.17.2.jar
curl -sfL "$BASE/com/fasterxml/jackson/core/jackson-databind/2.17.2/jackson-databind-2.17.2.jar.sha1"
```

#### 4. Compile

```bash
CP="lib/jackson-annotations-2.17.2.jar:lib/jackson-core-2.17.2.jar:lib/jackson-databind-2.17.2.jar"

mkdir -p out
find src -name "*.java" > sources.txt
javac --release 25 -cp "$CP" -d out @sources.txt
```

On Windows, `;` must be substituted for `:` as the classpath separator, and `dir /s /b src\*.java > sources.txt` used in place of `find`.

The module inherits the project compiler output (`inherit-compiler-output="true"`), so IntelliJ writes to `out/production/<module>` and `out/test/<module>`. A separate directory such as `build/classes` may be preferred for the commands above if IDE and command-line output should not be interleaved.

#### 5. Build the JAR

`game_engine.jar` is a build output and is produced here. Two packaging strategies are available. If a manifest is tracked at `src/META-INF/MANIFEST.MF`, its `Main-Class` and `Class-Path` entries may be reused via `--manifest src/META-INF/MANIFEST.MF` in place of the manifest authored below.

##### 5.1 Thin JAR (dependencies referenced externally)

A manifest is written declaring the entry point and the relative paths of the Jackson JARs. `Class-Path` entries are space-separated and resolved relative to the JAR's own location, so `lib/` must travel with the archive.

```bash
cat > manifest.txt << 'EOF'
Manifest-Version: 1.0
Main-Class: enginedriver.GameEngineApp
Class-Path: lib/jackson-annotations-2.17.2.jar lib/jackson-core-2.17.2.jar lib/jackson-databind-2.17.2.jar
EOF

jar --create --file game_engine.jar --manifest manifest.txt \
    -C out .
```

`resources/` is deliberately excluded. Image paths are assembled in `FileProcessor` and `AdventureGameGraphicView` from `System.getProperty("user.dir")` plus `resources/images/`, and game files are opened through `FileReader`, so all resources are resolved from the filesystem relative to the working directory rather than from the classpath. Two consequences follow, matching the README: images must sit at the relative path `resources/images/`, while game JSON files may sit either in `resources/` or beside the JAR itself.

A trailing newline at the end of the manifest is required, and manifest lines longer than 72 bytes are wrapped by the tooling — one JAR per line with a leading space is the safer form if wrapping causes trouble.

##### 5.2 Fat JAR (dependencies unpacked in)

Since no shade plugin is available, the dependency JARs are expanded into the output tree before packaging. Signature files must be discarded, or verification errors will be raised at launch.

```bash
mkdir -p fat && cd fat
for J in ../lib/jackson-*-2.17.2.jar; do
  jar --extract --file "$J"
done
rm -rf META-INF/*.SF META-INF/*.DSA META-INF/*.RSA META-INF/MANIFEST.MF
cd ..

cp -R out/* fat/
jar --create --file game_engine.jar \
    --main-class enginedriver.GameEngineApp \
    -C fat .
```

Verification of the result:

```bash
unzip -p game_engine.jar META-INF/MANIFEST.MF
jar --list --file game_engine.jar | head
```

#### 6. Tests

```bash
TESTCP="$CP:lib/JUnit5.8.1/junit-jupiter-api-5.8.1.jar:lib/JUnit5.8.1/junit-platform-commons-1.8.1.jar:lib/JUnit5.8.1/opentest4j-1.2.0.jar:lib/JUnit5.8.1/apiguardian-api-1.1.2.jar"

mkdir -p out-test
find test -name "*.java" > test-sources.txt
javac -cp "out:$TESTCP" -d out-test @test-sources.txt

java -jar lib/JUnit5.8.1/junit-platform-console-standalone-1.8.1.jar \
     --class-path "out:out-test:$CP" --scan-class-path out-test
```

The console-standalone launcher (section 3.2) bundles the Jupiter engine, so the individual engine JARs need not be listed.

#### 7. IntelliJ IDEA setup

1. The project folder is opened via **File → Open**.
2. A project SDK of 25 is selected, since the module uses `inheritedJdk` and pins no language level of its own.
3. Under **File → Project Structure → Modules**, `src` is marked as _Sources_ and `test` as _Tests_ (as already declared in `cs5004-hw8-test.iml`). No resource root is configured.
4. Section 3 is completed first, then the eight JARs under `lib/JUnit5.8.1/` are added as a module library named `JUnit5.8.1` at `TEST` scope.
5. The Jackson JARs are added as _project_ libraries named `fasterxml.jackson.core.databind` and `jackson-annotations-2.17.2`, matching the `orderEntry` names in the module file. These definitions live in `.idea/libraries/` rather than in the `.iml`, so they must be recreated if that directory is untracked.
6. Optionally, the CheckStyle-IDEA plugin is installed, as a configuration block for it is present in the module file.
7. Optionally, **Build → Build Artifacts** is configured to emit the JAR instead of the manual `jar` invocation.

### CLI Usage

```bash
game_engine <filename> -text #Run with text view
game_engine <filename> -graphics #Run with graphics view
game_engine <filename> -batch <source file> #Run in text view with commands from <source_file> with game commands, outputs to stdout
game_engine <filename> -batch <source file> <target file> #Same as above but outputs to <target_file> instead of stdout
```

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

For scoring, we used the values provided by the JSON but created our own rankings:

1. Novice
2. Squire
3. Knight
4. Baron
5. Prince
6. King

Our team decided to provide the player with four health statuses:

1. Full health is **awake.**
2. After losing health the player becomes **fatigued.**
3. After that the player is **woozy.**
4. Finally, the player is **asleep.**

#### Controller

The controller is composed of three interfaces: `Controller`, `ICommand`, and `GameInputOutputProcessor`. `GameInputOutputProcessor` is an Adaptor pattern used by the
`GameController`, the class implementing Controller, to process commands entered by the user through the view and adapts as appropriate for the View.
`GameInputOutputProcessor` receives raw input from the View and processes it into a `UserCommand` ordinal. The `GameController` takes the valid input from the user given
by the `GameInputOutputProcessor` and executes the appropriate `ICommand` associated with it. `ICommand` is a Command Pattern
that is contained within a map where `UserCommand` ordinals are the keys. When executed, the concrete `ICommand` subtype executes the action on the `IAdventureGameModel` class
and updates the view based on changes in from the model. Controller package communication with the Model is directly isolated to the `ICommand` command pattern.
The execute method of the command pattern returns a boolean encapsulating whether the game over state of the model has been reached to the `GameController`. Note that we have a single controller class that handles both the text-based and GUI-based views.

#### View

We have two `IAdventureGameView` subtypes:

1. `IAdventureGameTextView` for CLI-/text-based view.
2. `IAdventureGameGraphicView` for GUI-/graphics-based view.

We also opted to not change the "user menu" from what is was in the specs for the assignment.

### UML

- View our UML Class Diagram for full project structure
- View the 2 UML Object Diagrams for object instances created during a run of the game.
- View the 2 UML Sequence Diagrams to see the interactions between the objects specified in the object diagrams.

[View all diagrams here](https://github.com/vasiliosnicholas/Adventure-Game/blob/HW9/HW9_UML_Scenarios_Team_Boston_Celtics.pdf?raw=true)

### SOLID Principle Application

As our design has evolved over the course of the semester, we have sought to adhere to the SOLID principles as closely as possible while recognizing that any design choice involves
compromise. As such, within each of our packages, each class has a single responsibility. We have also sought to create our model such that new code
can be added, but that the existing code does not need to be modified, following the Open/Closed Principle. For instance, the modular nature
of our interfaces would make it easy for someone to add new elements to the game that implement some of the same functionality as existing
elements without changing existing ones. Our design has also incorporated the Liskov Substitution Principle. All the interfaces for the game
elements extend the Element interface, making subtype substitutions for supertypes possible. As for the Interface Segregation Principle,
we have divided our interfaces as much as possible based on functionality so that downstream code does not depend on things they do not use.
Finally, we have tried to assure that abstractions do not depend on low-level details meeting the Dependency Inversion Principle.

### Evolution from previous iteration

Between homework 8 and homework 9, our Controller design evolved in significant ways. We decoupled the text view from the `GameController`, made an `IAdventureGameView` interface,
and made the text view a concrete subtype of said interface. The GUI or graphics view also implements the `IAdventureGameView` and the `IAdventureGameGraphicView` interfaces.
The `IAdventureGameGraphicView` adds a method for Event-based models to set the view's event handler.
The graphics view handles GUI specific actions, such as opening a menu or opening a dialog box internally. The graphics view only sends game specific commands back to the controller.
We also isolated `GameController` behavior to strictly process command pattern executions. Minimal refactoring was needed for our model: We added an Inventory service class that
encapsulates a `Map` of `Element` types hashed by their names in all lowercase. This was done to consolidate/delegate proper formatting of keys for `Maps` containing `Element` types to one class,
thereby improving the single responsibility principle of classes that need to store `Element` types. `FileProcessor`, `Room`, and `Player` now use this `Inventory` service class to store `Element` types.
The other change to the model was refactoring return types of `IAdventureGameModel` methods, mainly to pass the pictures for each element to the view.
The `GameInputOutputProcessor` subtype for the text view "adapts" this extra data from the model out of the data that is passed to the text view, thus functioning as an Adaptor Pattern.

### Separation of Concerns - Communication between MVC components in our code is limited to one class per module

- Controller Interface that communicates with Model: `GameInputOutputProcessor`
- Model Interface that communicates with Controller: `IAdventureGameModel`
- Controller Interface that communicates with View: `GameInputOutputProcessor`
- View Interface that communicates with Controller: `IAdventureGameView` for text view or `IAdventureGameGraphicView` for graphics/GUI view.

## Tech Stack

- Java 25
- Jackson for JSON input/output parsing
- Java Swing for GUI

## Project Structure

```bash
Adventure-Game/
├── batchtest.txt # You can run our game_engine in batch command mode with this sample file
├── cs5004-hw8-test.iml
├── game_engine.jar # Where the .jar should be placed
├── HW9_UML_Scenarios_Team_Boston_Celtics.pdf
├── lib # Dependencies used for our project
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
├── resources # some sample game data
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
│   ├── controller  # Controller module includes classes command pattern and controller class
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
│   ├── enginedriver # top-level class where MVC classes can be swapped out
│   │   └── GameEngineApp.java
│   ├── META-INF
│   │   └── MANIFEST.MF
│   ├── model # Model class contains I/O classes, all game element classes, as well as business logic classes
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
│   └── view #Module contains both text and GUI view classes
│       ├── AdventureGameGraphicView.java
│       ├── AdventureGameTextView.java
│       ├── IAdventureGameGraphicView.java
│       └── IAdventureGameView.java
└── test #Comprehensive Test suite of the Model and Controller Classes.
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

---

## Generative AI disclosure

Claude Opus 5 was used to generate parts of the install & build section of this README
All other README contents and all code was written by the authors.
