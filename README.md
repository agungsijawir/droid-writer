This project aims to deliver a simple rich text editor UI widget for the Android platform. Extends basic EditText widget functionality with text formatting abilities.

## Currently supported features
- Basic text formatting (bold, underlined, italics)
- The ability to modify font size and style
- The ability to insert pictures into the text area (e.g. smileys)

## Fatures under development
- Ordered and unordered list support
- Export/Import text to/from HTML and BBCode

## Usage
The project is still in an early phase, so currently the only way to use it is to check it out with svn, and use it as an Android Library project.

Once you linked the DroidWriter lib with your project, you can simply use the DroidWriter widget in a layout file, e.g.:
```xml
<com.android.droidwriter.DroidWriter 
android:id="@+id/DroidWriter"
android:layout_height="wrap_content"
android:layout_width="fill_parent" />
```

## Author
This repo is originally comes from: http://code.google.com/p/droid-writer.
