# Use Cases 
1. User wants to create a path in the grid without repeating the same action over and over - user can click, hold and drag across
gridtiles. This creates a path flowing the direction of the mouse drag. This info is stored in the back end on creation.
2. User wants to change the color of the background - user can click the colorpicker under the title board color and select a color
for the background to be. This info is stored in the back end on creation.
3. User wants to access different editors without opening different windows - user can click on the tabs for enemy editor, defense editor,
and projectile editor.
4. User wants to select and image and know what image they selected in an editor - user can click on an image within the appropriate
editor's image selector and observe the selected image scale up in size. This info is stored in the back end on creation.
5. User wants to save prefs on current editors - user clicks save button at the bottom of the editor and it saves the user-entered params.
This info is stored in the back end on creation.
6. User wants to create a new level - user clicks the create levels button on the authoring environment, then clicks add new level in the
level editor. This info is stored in the back end on creation.
7. User wants to specify prefs in a new level - user clicks the edit button next to a level in level editor, and enters the appropriate parameters.
This info is stored in the back end on creation.
8. User wants to create a tower with specific params - user clicks the edit tower button in authoring env and adds params.
This info is stored in the back end on creation.
9. User wants to edit resources - user clicks the edit resources button in authoring env and adds params. This info is stored in the back end on creation.
10. User wants to create a type of defense - user clicks the tab for the defense editor and enters desired params. This info is stored in the back end on creation.
11. User wants to create a type of projectile - user clicks the tab for the projectile editor and enters desired params. This info is stored in the back end on creation.
12. User wants to create a type of enemy - user clicks the tab for the enemy editor and enters desired params. This info is stored in the back end on creation.
13. User no longer wants to add the parameters they just entered into an editor - user clicks the cancel button of the editor to 
clear the editor without saving info to the back end
14. User wants to go to the authoring environment - on the splash screen for the game engine, user clicks on the create game button
15. User wants to select an image for a defense, enemy, or projectile that is not in the image selector of the editor - user clicks the button 
under the image selector of an editor to open a filechooser and choose a png image file, which will be added to the image selector
16. User wants to title game - user enters title into the textfield above the grid in the authoring environment
17. User wants to save changes to the authoring environment (background color, pathing, game name) - user clicks save button at the bottom of the authoring 
environment screen, back end writes to an xml file
18. User wants to set start and end for a path - user presses shift and left clicks the start and end of a path, the colors of the start and end
tiles change, and the path's start and end are written to the back end
19. User wants to edit a created level that already has params - user clicks edit beside a level in the level editor, and changes the appropriate params from 
the previous params which are read in to be displayed on opening this single level editor from the back end
20. User wants to edit a created tower - user clicks create tower in authoring env, and changes the appropriate params from 
the previous params which are read in to be displayed on opening this tower editor from the back end
21. User wants to edit a created resource - user clicks create resource in the authoring env, and changes the appropriate params from 
the previous params which are read in to be displayed on opening this resource editor from the back end
22. User wants to go back to splash screen 
23. User wants to edit a created defense type - user clicks on a defense type in the defense view (scrollpane of defense types) in the authoring env, and changes the appropriate params from 
the previous params which are read in to be displayed on opening this defense editor from the back end
24. User wants to edit a created enemy type - user clicks on an enemy type in the enemy view (scrollpane of enemy types) in the authoring env, and changes the appropriate params from 
the previous params which are read in to be displayed on opening this enemy editor from the back end
25. User wants to edit a created projectile type - user clicks on a projectile type in the projectile view (scrollpane of projectile types) in the authoring env, and changes the appropriate params from 
the previous params which are read in to be displayed on opening this projectile editor from the back end
26. User wants to load a game they were working on - user clicks the load game button on the splash screen 
27. User wants to go to player view - user clicks test game and a filechooser pops up to allow the user to select game data files to load into the player, these are
then loaded into the player for the user to test the game they wish to test
28. User can see defense types they previously created - user can see scrollpane at bottom of the defense editor with all created defense types
29. User can see enemy types they previously created - user can see scrollpane at bottom of the enemy editor with all created enemy types
30. User can see projectile types they previously created - user can see scrollpane at bottom of the projectile editor with all created projectile types
31. User, as a player, wants to place a defense - user clicks on an available toolbar with defenses presented and clicks on where the destination would be. If they lack the appropriate resources or illegally place the defense, an error is thrown
32. User, as a player, wants to pause the game state - user clicks on a pause button, where they are presented with options to resume or save the game state to an xml
33. User wants to make teams - for each object that has health or takes damage, a hostility/team parameter is available to declare the objects alliance
34. User wants to allow minions to go anywhere on board (using AI) - on path editor, player clicks option of undefined paths
35. User wants to give minions ways to damage structures - in minion editor, user adds projectile option and chooses an existing projectile to add to the minion.
