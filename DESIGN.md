# DESIGN: Crouton
## Project Design Goals
Crouton focused mainly on the following goals:
1. Implement an engine that would be data-driven and lightweight, allowing for ease of adding new behavior to games
2. Create an intuitive authoring environment where users can develop a tower defense type game
3. Develop enough flexibility for an author to associate behaviors to any type of component within a tower defense game

Features that can be easily added are as follows:
1. Systems to dictate game behavior
2. Components to store Entity states
3. Types of Entities through properties files and Components
4. Editors in the authoring environment 
5. Behaviors for any type of function in the frontend
6. Data-driven communication between a frontend and its backend component
7. 
## High-Level Design
The engine is split up into a couple main subparts, as driven by the Entity-Component-System for the architecture. The entire concept of the architecture is to use composition rather than inheritance, with each entity and system being vague but having specificity emerge from assigning combinations of components to each.
* Entities:
    * Entities are completed "objects" in the game, which could be anything as specific to a dart flying across a screen or the specifications of a certain level. Entities are associated with an ID that both the authoring environment and engine need to maintain. Aside from that, Entities don't hold primitive data aside from a list of components that are assigned to it.
    * Entities are killed and created based on conditions as determine by Entity Manager.
    * Entities never see one another, but rather serve as "buckets" in which Components sit and hold data.
* Components:
    * Components are small data packets of different features. The data maintained in one component is generally limited in scope but large in usage, such as "Location", "Speed", or "Active".
    * Components also know their entity owner, such that distinct components, despite not necessarily knowing what other components it shares, all have a pointer upwards to their owner entity and don't get confused between one another
    * EntityFactory gives an entity its relevant components based on the inputs given from frontend to a create call.
    * Components interact with one another in systems, with the values of other components being accessed by getters and being changed by setters.
* Systems:
    * Systems manage behaviors that result from combinations of certain component data, such as movement relying on location, speed, and angle components.
    * During each iteration of the timeline, each system updates as appropriate to the behavior it manages.

    * Each system holds Maps of the owner entity ID to the relevant component, such that algorithms that analyze the interaction of one component can affect a different component from the same entity
    
        E.g. If Entity A has a collision component that collides with entity B's collisions component, both A and B should see a reduction to their health component's value
    
    * These three buckets of behavior and data are all created, updated, and destroyed by the interactions in EntityFactor, EntityManager, and SystemsManager
    * The Engine class provides the main API of the backend, providing methods to create individual entities, run the game, edit an entity, save xml files, and more. In an ideal case, the Engine class should be the only public class in the entirety of the backend.

#### Front end design

* High Level Design of the Authoring Environment
    * Clickable Objects - created user input components, such as buttons, that must be passed controllers to determine their behavior upon being clicked or otherwise interacted with
    * Views - views are any classes that bring together visual components; they include editors, but they also assemble larger components, such as editors, as well (e.g. in AuthoringView) 
    * Editors - fall under a specific type of view, that is intended for user input to create game enitities in the back-end
    * Controllers - define the behavior of interactive visual components of the authoring environment, many of them send edited or newly entered information to the back end to be saved, format data into a map to be sent into the back end
    * Data - Contains the actual save method that sends data to the back-end, help load in the data for a previously saved entity type for entities Enemy, Defense, and Projectile, so that it can be edited
    * Resources - contain text for labels of visual components so it they can be changed dynamically by switching out properties files

The classes interacted as follows: the Editor subclasses set up inputs, and then called the Editor abstract class method to create save and cancel buttons. This method had a Controller passed in to control saving behavior for that specific editor subclass's user inputs. The controller contained a call to the abstract Controller class method createMap, which created a map of the data from the user inputs to send to the back-end. It also generally contained a call to the save method of the abstract Data class to send the data to the back-end. The editors themselves all extended View as they all assembled visual components (user inputs, which were all ClickableNodes), but were assembled into the authoring environment in AuthoringView.

Some other controllers did other work, such as proving the functionality for back buttons, etc. They also served to provide functionality for the add and delete buttons in classes that allowed multiple waves or levels to be added to the display to add more ClickableEditorEntries to the Editor to allow the user to add more waves or levels. 

## Assumptions and Simplifications of Design

#### Engine

The engine team decided to utilize the Entity-Component-System (ECS) design pattern to create games. Some assumptions of ECS are as follows:
1. Every object in a game can be represented as an Entity
2. Every Entity is composed of Components
3. Components are buckets of data relating to a particular behavior
4. Systems act on Components through referencing an Entity via a unique entity ID number

Since we assume that everything in a game is an Entity, meta-data for a game and abstract game elements (ie. levels) are also considered Entities and these elements had to fit into the ECS framework. Doing so simplified our design by allowing the engine to have Systems coordinating state changes and behavior in the game. As such, engine had few managing classes, only an EntityManager, SystemsManager, and Engine class, thus simplifying design while simultaneously making it slightly more difficult and less intuitive to implement abstract elements of the game, like levels and maps.

#### Authoring Environment

 The authoring environment also does not rely on any of the data formatting to save the state of
the game created by the author. It simply makes a call to engine.save(), delegating the problem of how to serialize the objects to the
backend. The objects created by the user also live in the engine, so the authoring environment does rely on the engine having
a create, save and edit functionality in order to make any game. Because the authoring environment allows the user to add custom images,
it does not depend on the images in the authoring environment resource folder, other than to set defaults.

One dependency between the authoring environment and the game engine comes from the way that the authoring environment
communicates with the engine. The way that the authoring environment knows which parameter names to send to the engine so
that it can instantiate its objects is by reading a properties file containing all the parameters for that backend object.
We discovered that properties files are unordered, so the only way to guarantee that the parameters could be passed into the
backend in a particular order, matching up with the proper name from the properties files, was by first alphabetizing
the values read in from the files. This means that when the parameters are sent to the backend, they must be sent in
alphabetical order, which is a dependency that is well documented each time it occurs in the comments.

#### Game Player

The assumption we made when creating the various views for the game player is that each object we needed to display lived in the engine, that there was only one copy of it in the engine, and we just had to fetch the information we needed from the engine to actually display images on the screen. This meant looping over all the entities in the engine to find the given type we were looking for, then checking to see if that type had an x and y component. If it did, we would then grab the x and y locations, create an image view based off of the specified file path, then set the image's location to the one in the engine. However, there is one case where this assumption could cause errors. In the event that the user creates more than one map and saves it, loading that game will cause the player to display the wrong map. This is because the player view is looking for anything that is a tile in the engine, however there are plenty of tiles in the engine if you create more than one map. Thus, the player will then display the combined version of the maps which isn't good. The only way fix this is by checking an entity's active status, which the engine will set to true based off of the state of the game. This means that I could get rid of this assumption/bug by checking for active statuses before we go ahead and create the map in the player view. 

The other assumption we made is that your computer is running fast enough to keep up with all these computations. The code is not very effieicent because we have lots of nested loops running in the player. This means that less powerful computers will have a hard time trying to render the player. The only way to get rid of this assumption is by refactoring the code. One way would be to use properties and just bind these properties to image views locations. This way views don't have to check every thing in the engine to grab one specific location, effectively getting rid of one nested loop. Using this style, the engine would then just be in charge of modifying the properties and the views would update automatically. 

## Adding new features (especially unimplemented)
#### Engine

1. New Entity Types

    Entities are defined by the Components they have. As such, a new Entity type would need two things: 1) A properties file where the name of the file is the name of the new type of Entity 2) Any new Components that would make up the Entity. To make a new Component, extend the Component class and implement any methods of the superclass. In order to have new behavior, a new ComponentSystem subclass needs to be implemented, being sure to override the `updateSystem` method. In this `ComponentSystem` subclass, accept maps of integer to `Component` of whichever `Component` subclasses are needed.
    
    

2. New Behavior
 
    Since Systems and Components dictate behavior, we must decide how behavior states can be represented in a data format. These buckets of behavior state will become subclasses of `Component` and a class to change behavior states will be a subclass of `ComponentSystem`. The new `ComponentSystem` subclass will accept a map of integer IDs to the new `Component` subclass instances. For any `Entity` type that needs to add the new `Component` subclass, add the appropriate keys (name of Component) to the `Entity`'s properties file. Override `updateSystem` method of the new `ComponentSystem` subclass and implement how new behavior is updated.
    
    We can even break up a given behavior into smaller bits, and create multiple systems to delegate that. This is also helpful for code reuse, especially if there's a chance two different types of behavior have some overlap (e.g. collisions are needed by a number of behaviors). Besides that, it's just easier to write that kind of code.
    
3. further AI (for the "challenge" bit)

    Since every interaction is defined by interactions between components whose behaviors are dictated by systems, creating a system for AI in the enemies (or whatever else) would involve doing just that. Anything else just wouldn't fall in line with our design, and would hurt more than help.
    
    To *actually* implement this, we would need some way to encode the behavior or decision-making style of an AI in a component. Things like ways to determine the next target, methods for traversing the environment, and interactions with other enemies would need components that can, in relatively simple terms, tell a system how that enemy will make decisions. This can be as simple as an enum for each behavior type. Honestly the contents of these components doesn't really matter, we just have to write the systems to understand it.
    
    As for creating the behavior, there are a couple of options. we can:
    
    * have existing systems modified to contain behaviors for AI (e.g. modify MovementSystem to now consider entities whose AI determines that it wants to change its movement), or 
    * create a system that processes the AI and stores the "decision" into another component. That other component would then used by other systems to modify behavior. this is similar to the previous, but now the actual processing of AI happens in a separate system. or,
    * create a new system that defines the behaviors of AI and *can change* state as well. 
    
    The thing is, we can get as granular as we like, and can create as many systems and components as we need to delegate the behavior. The third option mentioned would be nice, then, because we can split up the system into *many* systems that each only process a *single* bit of AI behavior.
    
    These Ai options would need to be simple enough to allow the user to edit them without there being a UX disaster, which is a real challenge. Some dropdown menus are probably the easiest way, if a bit lame.
    
#### FrontEnd

1. One feature we desperately wish we had is the ability to load a previously created game into the authoring environment. We didn't have time to implement this feature, but we think we have a decent picture of how it would be done. In order to implement this feature, the each editor will need a method to grab pre-exisiting entities from the engine and populate the screen with the info. This
would be a massive time commitment, but ulitmately worth it. The controller would live in the StartView class and would just open up a file chooser then call Engine.load with the specified file path. Next, the controller would tell the AuthoringView that the user wants to load a pre-existing game into it by calling AuthoringView.loadFile(). This would then signal all the editors to load their data from the engine, and then everything else should work as normal.