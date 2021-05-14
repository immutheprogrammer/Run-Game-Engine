# Run-Game-Engine

How to build on all platforms:

its pretty simple just run the main file and make sure you can use .gradle files. 

if you find any issues please report them into the issues section.

I encourage you to go and mess with the source code and maybe even learn something.

**Some of The Features:**

the run game engine has a batch renderer for faster render times,
I will be implementing physics very soon,
you can use spritesheets,
it has an ECS system,
it can handle input with keyboards, mice and controllers,
and is has a grid system.


**Some Future Features:**

The engine may have a simple 3D render made with the Vulkan rendering api (which might give me the biggest headache ever) so if you want to make a 3D game, you can.

particle system

im still brainstorming ideas for some more features...

**Known Issues:**

if you look in the gradle file you might be wondering why I import imgui-java 3 time for each platform and thats becuase it imports the macos bindings for me instead of the windows bindings for some reason.

I can't figure out why imgui-java imports the macos bindings.

**(Fixed)** another bug is when resizeing the window some features may break.

**(Fixed)** when panning the window the and you are holding a texture the position of the texture will shift.

**(Fixed)** when making the grid width 20 or under the some of the grids vertical lines wount load properly.

when deleting the level.json file and running the game again it will throw an error saying that there is no file but it will create a new one, also you might be wonder why there is no inspector thats becuase there is no default game object that is picked. To fix this drag a texture onto the canvas and restart the engine and it should work just fine if you dont drag any texture and stop the engine, the engine will crash. You have to delete the level.json file and then follow the steps I mention before. 

**About The Documentation:**

there is no documentation yet but once the engine is at a more complete state I will spend some time writing the docs.



