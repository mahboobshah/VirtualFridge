About VirtualFridge App:
------------------------

This App acts as a virtual fridge. To load items into this fridge, capture image of a receipt from a grocery store and this app picks groceries from the text processed from the image (using Optical Character Recognition)

Additionally, this app gives recipe suggestions using Yummly service. One can select any combination of groceries from the list and search for recipes having those groceries as ingredients. 



How to run this app:

1) Download the source from https://github.com/rmtheis/tess-two (OCR Library files)

2) Build this using Android NDK.
   - cd <PROJ_DIR>/tess-two
   - ndk-build
   - android update project --path
   - ant release

3) once it is succesfully built, import project into Eclipse  File -> Import -> Existing Android code into workspace -> tess-two

4) Right click the project, Android Tools -> Fix Project Properties. Right click -> Properties -> Android -> Check Is Library.

5) Now configure this project to use tess-two library.. Right click on Project -> Properties -> Android -> Library -> Add, and choose tess-two. 

6) Download language files from here: http://code.google.com/p/tesseract-ocr/downloads/list and add this to assets folder or programatically add to SD card.

7) Last but not the least, update your Yummly App ID and App Key in RecipeDetailActivity.java and RecipeListActivity.java.


TO-DO:

1) Refine image processing

2) Persist current state of fridge using SQLite/SharedPreferences

3) UI improvements

4) Add more details to Recipe Detail Activity. e.g: add image, add link to source website, add nutrition info, etc,.

5) Sync fridge data with cloud 
.....

6) Adding test line
