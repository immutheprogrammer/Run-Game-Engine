# Run-Game-Engine

How to build on all platforms:

its pretty simple just run the buld.gradle file and run the main file. 

if you find any issues please report them into the issues section.

**Known Issues:**

if you look in the gradle file you might be wondering why I import imgui-java 3 time for each platform and thats becuase it imports the macos bindings for me instead of the windows bindings for some reason.

I can't figure out why imgui-java imports the macos bindings.

another bug is when resizeing the window some features may break.

when using the engine make sure the screen width and height are at 1920x1080 for it to work properly.

**(fixed)** when panning the window the and you are holding a texture the position of the texture will shift.

and when making the grid width 20 or under the some of the grids vertical lines wount load properly.

there is no documentation yet but once the engine is at a more complete state I will spend some time writing the docs.



