# Design Plan

* Notes: No API is provided for the frontend (game player/authoring environment), since there will be no public methods (only protected) in these packages.

## Introduction

#### Primary Design Goals
Our primary goal in designing this project is to provide a general game engine from which a designer can create a few different kinds of Tower Defense games. This means the framework must be general enough to allow for different versions of the game (for example, versions where there is no set path for the enemies to travel) to be created using the same basic structure. 
#### Design Architecture
This design's primary architecture will focused on maintaining the addition of new variations on our objects (to support different games and different implementations of each game) closed by creating general enough classes representing the base game contents that are extended to provide more specific functionality.

In addition, from the perspective of the authoring environment, the design will flexibly allow the user to click on a square to generate a path for the enemies to travel. This is a very flexible design, because it allows the author to generate any path they desire. It is also closed, because the code used to generate an active path will not need to be modified to create different kinds of paths. There will also be clickable boxes in a  menu to select different kinds of defenses, which will take you to an editor page that allows the author to customize the defense parameters like size and kind of defense. This is a closed design because to add a new kind of defense, a new class would be made. To add different functionality to a defense, we would substitute a parameter in the defense that represents the functionality, to keep the defense class closed. Other elements would be more open, like generating checkboxes for different kinds of behavior that a defense could have, because it would involve some minor changes to the code to provide a new defense option, though it will be more closed by reading the options in from a properties file.

#### Describing The Game Genre
Some games we looked to for inspiration about the common features of tower defense games include Bloons, Plants v. Zombies, and Clash of Clans. These games gave us an idea of what all games in the Tower Defense genre have in common:
* __Tower__ - This is the objective for the enemies. The tower is essentially a representation of the user's success in the game, and if the tower runs out of health, the user loses the game. The locations of the towers for different kinds of tower defense games are described under the enemies section, as they define the behavior of the enemies.
* __Defense__ - These can be found in 2 forms across the games:
    1. __Active Defense__ - These are defense elements that react to the presence of enemies with some active behavior,
    like targeting and shooting.
        * Examples include the pea shooters in Plants v. Zombies, or the Dart Monkeys in Bloons.
    2. __Passive Defense__ -  These are defense elements that once placed do not have any behavior other than causing damage
    when an enemy collides with them, and taking damage when this happens themselves.
        * Examples include spikes in Bloons and barriers in Clash of Clans.
        * The Pea Shooters in Plants v. Zombies are an interesting example, since they are active, but the layout of Plants 
        v. Zombies allows for collisions between the defense and the enemies, so they also take damage when a zombie collides with them.
    
    All of these defense elements are placed in a location and are rooted there unless they are deleted, or moved by the player.
* __Enemies__ - These are moving sprites (balloons in Bloons, zombies in Plants v. Zombies, and soldiers in Clash of Clans) that march towards the end of the screen (or Tower). Across all the games, there are different tiers of the enemies (like balloons that spawn other balloons). The way enemies travel varies across the games:
    1. _Bloons_ - enemies follow a set path designated by the level (tower is at the end of the path).
    2. _Plants v. Zombies_ - enemies go in a straight line from the right to the left of the screen (tower is at the left edge).
    3. _Clash of Clans_ -  enemies travel radially inward towards the center (tower is at the center).
* __Maps and Waves__ - Progressing throughout the game in all of the games is characterized by Maps, which define a beginning layout for the player to work with. The Map is defeated if the player still has health left (Tower health) at the end of all the Waves, which are characterized by groups of enemies coming through, with breaks in between each wave to change the layout of the defenses.
* __Resources__ - In each game, there are resources that are collected from playing the game, which allow for upgrading the defense structures or buying new ones. There are also new defense structures that are unlocked by playing the game and can be purchased once unlocked.
    1. In Plants v. Zombies, the resource is sunlight, which allows you to plant more seeds, which you get as time passes in the game. There are also resources dropped each time a zombie is killed.
    2. In Bloons the resource is money which is generated each time a balloon is popped.
* __Resource Facilitators__ - Tower defense games also allow for "defense" like structures to be placed, whose main purpose is instead to generate resources, or make it easier to get resources.
    1. In Bloons, this is seen with a monkey house, which improves the defenses of the monkey defense, resulting in more coins collected.
    2. In Plants v. Zombies sunflowers generate sunlight to be used to plant more plant defenses.

Thus, our design will need to support very general versions of all of these concepts in order to support different kinds of tower defense games.

### Design At A High Level
![](https://i.imgur.com/YmOfWux.jpg)
This image describes the interfaces between the two frontends (authoring environment and player environment) at a high level by defining some general methods that describe the API. The getTypes() method is intended to allow the user to get a dynamically generated list of available backend options for the designer to select from.

## Overview
Prompt: This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. Describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's API. Include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). Discuss specific classes, methods, and data structures, but not individual lines of code.

In terms of a high level design, we have opted to divide the project into four primary components: Game Player, Game Data, Authoring Environment, and Game Engine. The following image is a general representation of the way in which these four components connect:
* ![](https://i.imgur.com/7Lp18x4.gif)

Both the authoring environment and game player will have access to the engine's class names as well as the modifiable aspects of each game object. The authoring environment will allow a user to choose specific values and behaviors for the open-ended aspects of game objects provided by the engine and these specifics will be converted to game data. The game data is coupled with the engine in the game player to produce a fully functioning game. The game engine contains the game loop that will update states and the game player will render these updated states.
#### Game Player
* The game player depends on reliable game data and engine functionality. The game player will display elements in the game, allow for interaction from a user, and update as the game loop progresses. In order to update the visual representation of the game, bindings can be used or getters to retrieve game object state information.

#### Authoring environment
* The authoring environment is going to be the best feature of our project. It will focus on providing the author with an intuitive user interface in order to conviently create games. Furthermore, there will be view and controller heigharcies to generalize the behavior and content within different views. Lastly, the view will focus on displaying things, so it will delegate things like creating objects to the game engine in the back end. 

#### Game Data
* The game data module is meant to connect the authoring environment in the front-end with the game engine in the back-end. It creates the objects such as defensive structures, enemies, and paths used by both the authoring environment as well as the engine. The authoring environment determines values for attributes such as health, movement speed, and location which is communicated to the engine. This is processed using serialization to transform the game's data into XML files to be processed by the game engine.

#### Game Engine
* The game engine will utilize an entity-component-like design pattern, where all game entities will have modular features. The engine will use small inheritance hierarchies and composition to achieve a flexible and robust way of implementing game objects. Collisions will be a modular feature of each collidable object. In order to implement the game loop, the engine will utilize a map that holds locations as keys and game objects as values. From this, the loop can iterate through all game objects on the map and update them.



## User Interface
#### Wire Frame
The components that the user will interact with are shown in the wireframe attached at this [link](https://www.figma.com/file/pkLgLnRf0OOvqX0MHVLp1a/Crouton?node-id=171%3A13). This is a runnable version of our authoring environment.

#### How Game Is Presented To Designer
The authoring environment, as shown in the link above, will contain a canvas that allows the author to drag items to or select portions of the canvas to place game elements. These game elements will have their own menu to select specific parameters to customize the objects behavior and appearance. This provides maximum flexibility for the user to combine the available defenses and behaviors. To simplify user interacgtions, we will have multiple tabs. For example, a behavior customization tab, and an image customization tab for a new kind of defense.

#### Erroneous Data
When it comes to erroneous data, we will be relying on the game engine to check each object is valid with its given parameters. For example, if the author were to set the health of a defense to a negative number, the game engine would catch this and throw an exception such that the authoring environment can catch the exception and display an error to the author describing the erroneous data. 


#### Design Goals
* Creating a simple and intuitive interface to make game creation faster and convenient.
    * Java already has tons of classes to create, style, and manage applications; now we are taking some of them and combining them in order to create this intuitive interface. For example, the combobox allows users to easily select an option from a given list, and we will be using this alot in our authoring environment to give the author options for customizing the game. 
* Creating a UI that is as thin as possible by delegating object creation and saving of files to the backend. 
    * This leaves much of the implementation work to be done in the backend, in granular classes, which allows the user environment to be simpler to work with. It also ensures the View code deals specificially with displaying, and not with any logic.
* Allowing the user flexibility for selecting images, starting defense types, as well as projectiles, among other things.
    * This provides the designer with many choices for how to implement their game, while packaging them up into higher-level objects like defense. This is ontop JavaFX's approach of using simple nodes (ImageView for example), and will map these to backend objects with fields for location and other necessary parameters.
* Promoting extension of game objects (ie. new types of projectiles)
    * Allows a wider variety of game objects to be created, making it easier for a game developer to create a wider variety of games, without also requiring the developer working on extending the game objects to go through a large refactoring of the game engine to be able to add different game objects with significantly different behaviors than those previously added
* Promoting extension of game object behavior by allowing user to select any type of behavior for a defense object.
    * In order to allow the author to customize object behavior, we will ideally want to them to use Groovy in order to give them tons of freedom for their games. However, this is more of an extension so for now we will be using comboboxes to limit the behavior options a user can select. We can then add more options to the combobox until we maybe switch to Groovy. 
* Maintaining modularity between the Game Engine, Authoring Environment, and Player, by ensuring communication via API classes.
    * This will allow flexibility in that someone extending functionality in one of those components does not have to understand the other components, but instead can add to the component whose functionality they desire to extend, utilizing the other components' API returns to access the necessary data and objects from those components 


## Design Details 
### Engine
* The engine will function as the module that runs the game and controls the game loop. It will have all the objects available for a game author to choose from and certain attributes will be left open for the author to specify. To handle different types of objects in the game, the engine will have classes that have a concrete implementation and implement an Entity interface so that the game loop can process the overall system. Each concrete implementation is composed of component classes that define an objects behavior (ie. a dart class will use a "Projectile" hierarchy subclass to determine its behavior). As such, the game loop will be able to update all Entities of specific types in lock-step.
* The engine's behavior will be structured around maintaining a entity component system. Any sort of object that needs to interact or exist on screen will be made into an entity.
* Entity is a super class that can hold a couple of generic subclasses, like projectile, defense, etc.
* Each entity holds a number of components, or packets of related info that govern the entity's existance. For example, a projectile entity may have components of type physics (to govern movement and collision) and health (to govern damage and health), as well as images such that the entity can provide its properties to the front end for display.
* To manage interactions, each different component is handled by a System, a manager that governs the interactions between components and consequently the entities that reside over them.
* In the game loop, a System manager controls the rotation of operating systems to change the position, health values, rotations, and generalized behaviors of any given object that may matter.
* To the front end, a list of active entity IDs is listened for and a getter of immutable maps is provided for the front end to retrieve the active elements on screen. If anything, either in the back or front end, wants to create a new object to be visualized, a call to engine.create is made with the correct parameters as determined by properties files. 
* By sharing IDs, the front end can update state by retrieving updated parameters of the active entity objects, while also allowing the frontend to modify the entities by acting on a certain ID.
### Game Data 
* Game data is very straight forward in how it converts the game to XML and back. Essentially, it has delegated to GameEngine the model of grouping objects into one another as fields (pyramid structure) such that at the 'very top' is a single GameEngine Object. This object would have fields that include MinionCreator, Path, Health, Bullets etc. The serializable function would convert this entire game into XML. 
* Game data is also creating the Properties files, for multiple reasons. The first reason is that there is an open standard for what properties that author can define for each type of object. It can also be used to check if the author has set the matching data type for the fields of each object. The names of object packages would match the name(s) of the properties folders. Each subclass has it's own properties folder, this will have duplicated properties, but would make it more clear about the differences between each object. 
### Authoring Environment/Game Player (Frontend)
* This will function as a lightweight section that focuses on creating a view for the two kinds of environments, without focusing on the logic of instantiating objects. It will also emphasize the addition of new components and flexibility for the designer by making use of inheritance and allowing for different combinations of behavior and objects in the frontend. To achieve this, it delegates implementation details to the game engine, as well as the actual saving to an xml file, but provides the inputs from the user to do this.

## Example games

* Three example games are Plants versus Zombies, Clash of Clans, and Crystal Defenders. Each of these games fits into the tower defense genre, but they all differ in terms of their implementation. For example, Plant versus Zombies is a tower defense game where there are set lanes enemies can travel down in order to attack your base. Additionally, you can place towers on the same lanes enemies travel through, and your towers have health. However, in Clash of Clans, enemies take the shortest path to attack your base, and the enemies will break anything that's in their way. In other words, there is no set path for enemies to travel through, but your towers still have health. Lastly, in Crystal Defenders, you can only place towers in areas that enemies cannot travel through, and your towers have no health because enemies cannot destroy them. Enemies will then travel down a path that is usually not a straight line in order to attack your base. 
    * ![](https://i.imgur.com/dkgDyKY.png) Crystal Defender Example
    * ![](https://i.imgur.com/vqYvn35.jpg) Plants versus Zombies Example
    * ![](https://i.imgur.com/lWXdBVy.png) Clash of Clans Example

* When it comes down to our design, we want to make things as general as possible so that we can eventually make all these different kinds of games. The authoring environment supports all these different implementations because the authoring environment will allow authors to define what paths enemies will take, where the player can set towers, and edit the health of towers. 

## Design Considerations
* Game loop placement in backend
    * The alternative was to place it in the frontend, but the decision was made to keep the frontend lightweight and containing simply the visuals for the game. This allows the game engine to be fully functional with any frontend implementation, as well as keeps the logic of the how the game runs in the backend.
    * Pros for implementing it in the frontend would be a clearer updating of the frontend values being displayed, but the cons involved tying the implementation of the frontend with that of the backend.
    * Another alternative would have been to place it in its own section, but keeping the game loop separate from both the backend and the frontend seemed unnecessary. In addition, it made both ends of the project nonfunctional without it.
* Delegating object instantiation and creation of XML to game engine
    * This also contributes to keeping the game logic in the backend, and maintaining the view as just a visualization.
    * Some benefits for creating the objects that will be parsed to XML using XStream in the frontend were directly allowing the frontend to know about the parameters and backend objects, which simplified the process. However, the cons were that it mixed viewing and game logic, as well as created a dependency between the frontend and the backend. Thus, the pros of keeping the objects themselves within the backend and the frontend simply telling the backend to create them and write them to the file seemed more modular, and aligned more clearly with the single responsibility principle.
    * At first we discussed having a separate module for XML parsing, but realized this would be much more complicated when XStream was introduced in class, and that we could incorporate this as part of the game engine.
* Using properties files to store parameter information to keep the views and the game engine on the same page
    * This adds a bit of a complication in terms of using the file to find the backend parameters in the frontend, but it standardizes the parameters between the view and model, as well as prevents the frontend from hardcoding the parameters and creating these dependencies. That was an alternative we considered, but we decided that would create too much backend functionality in the frontend, as well as violate the single responsibility principle.
    * Another alternative would have been to have the objects themselves in the frontend, as well as the file writing, but this would not make much sense since these are game engine elements fundamentally, and the game engine also needs to know about them to run the game loop.
* Using a Entity Component System to handle interactions of multiple engine objects
    * The ECS allows entities to be composed of different features which are managed by relevant systems, such that we don't have to iterate over the totality of any possible interactions. Different interactions between components are managed by systems, and entities simply hold these components that determine their state.
    * The alternatives are generally primitive, like every object holding on its collection of states and having to check the intersection of each object, deciding outcomes in an intelligent matter that prioritizes interactions in a smart way.
    * The issue with the ECS is that it creates a massive dependency on the front end to correctly map the ID given from the backend to the associated object on the frontend. If the mapping is incorrect, the updates on the backend would all impact the wrong object on the front end.
* Using composition to give defense parameter flexibility
    * e.g. allowing Monkeys to have either bombs or darts as projectiles
    * An alternative we considered was using inheritance and extending classes, but we realized that things like projectiles coming from a defense do not define the defense, but do make it up. In addition, they make up the defense, but can be changed. This pointed us to using composition.
    * We also considered having them as completely separate concepts, but allowing one to be passed in as an argument gives flexibility for assigning different behaviors to different game elements as the designer.