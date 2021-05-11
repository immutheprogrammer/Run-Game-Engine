# Run-Game-Engine

How to build on all platforms:

its pretty simple just run the buld.gradle file and run the main file. 

**Known Issues:**

if you look in the gradle file you might be wondering why I import imgui-java 3 time for each platform and thats becuase it imports the macos bindings for me instead of the windows bindings for some reason.

I can't figure out why imgui-java imports the macos bindings.

another bug is when resizeing the window some features may break.

when using the engine make sure it is at 1920x1080 for it to work properly.

and when making the grid width 20 or under the some of the grids vertical lines wount load properly.

there is no documentation yet but once the engine is at a more complete state I will spend some time writing the docs.



