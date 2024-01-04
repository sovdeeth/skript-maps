# Skript-Maps

This addon is meant to assist Skript users in drawing their own custom content onto maps. Currently, this addon is in **alpha** and has limited syntax.
Expect issues and bugs, or unfinished content.

It supports:
* drawing text onto maps
* drawing images onto maps
* loading images from a specific directory
* colouring specific pixels, lines, rectangles, or circles a certain colour
* managing multiple layers of rendering on a map

Maps are not saved through server restarts, however, so be warned!

## Code Example
```applescript
command test:
    trigger:
        set {_item} to a filled map
        set {_view} to new map view in world "world"

        set {_layer} to new map layer:
            colour pixels between 20, 20 and 108, 20 on the map blue
            colour pixels between 20, 20 and 64, 44  on the map blue
            colour pixels between 64, 44 and 108, 20 on the map blue
            colour pixels in radius 20 of 100, 100 on the map green
            colour pixel 100, -100 on the map red

        set map layers of {_view} to {_layer}

        set map view of {_item} to {_view}
        give {_item} to player
```

## Syntax:
### Effects
#### Draw Map Content
```
draw %image/string% on the map at %number%, %number%"
```
Draws text or images on a map. Must be used in a map layer section.

**Examples**
```
set {_img} to the map image from file "myimage.png"
set {_layer} to a new map layer:
  draw {_img} on the map at 0, 0
  draw "%mapColor(red)%%Hello, world!" on the map at 40, 60
```
***

#### Draw Map Pixels
```
colo[u]r [the] pixel [at] %number%,[ ]%number% on the map [as] %color%
colo[u]r [the] pixels (between|within) [[the] pixel [at]] %number%,[ ]%number% and [[the] pixel [at]] %number%,[ ]%number% on the map [as] %color%
colo[u]r [the] pixels in radius %number% [of|around] [[the] pixel [at]] %number%,[ ]%number% on the map [as] %color%
```
Sets the color of one or more pixels on a map. Must be used in a map layer section.

**Examples**
```
set {_layer} to a new map layer:
    color the pixel at 0, 0 on the map as red
    color the pixels between 0, 0 and 127, 127 on the map red
    color the pixels in radius 10 of 64, 64 on the map rgb(30, 50, 80)
```
***

### Expressions
#### Map Image
```
[the] [resized] map image [from file|named] %string%
```
Loads an image from a file. The image must be in the plugins/skript-maps/images folder. Take care not to do this too often, as it can cause lag.
If `resized` is used, the image will be resized to 128x128 pixels, which is the size of a whole map.

**Examples**
```
set {_img} to the resized map image from file "myimage.png"
set {_layer} to a new map layer:
    draw {_img} on the map at 0, 0
```
***

#### Map Layers
```
map layers of %mapview%
%mapview%'[s] map layers
```
The layers of a map. Each layer can have its own code for drawing on the map, and their contents will be drawn to the map in order.
This means the last layer will be drawn on top of the others.

Layers can be added or removed at any time, and their order can be changed by removing and re-adding them.

**Examples**
```
set map layers of {_map-view} to reversed map layers of {_map-view}
set map layers of {_map-view} to {_layer1}, {_layer2}, {_layer3}
clear map layers of {_map-view}
add {_layer} to map layers of {_map-view}
remove {_layer} from map layers of {_map-view}
```
***

#### Map View
```
map view of %itemstacks%
%itemstacks%'[s] map view
```
The map view of an item stack. This can be used the change the properties of a displayed map, or have layers 
added and removed to change the content of the map.

If a map item does not yet have a map view, this will return nothing. You can use the "new map view" expression instead.

Be warned, resetting this property may clear the map, or it may cause the map to be completely regenerated as
an entirely new map, depending on your server jar.

**Examples**
```
set {_map} to a new map",
set {_view} to {_map}'s map view
set {_view}'s map scale to 2
set {_map}'s map view to {_view}
```
***

#### New Map View
```
[a[n]] [new] [empty] map view [in [world] %world%]
```
Creates a new empty map view. This is not a map item, but a map view that can be used to render a map.
To assign this map view to a map item, use `set {_map}'s map view to {_view}`.

**Examples**
```
set {_view} to a new map view
set {_layer} to a new map layer:
    color pixels within 0, 0 and 127, 127  on the map in radius 10 of 64, 64
add {_layer} to map layers of {_view}
set {_map}'s map view to {_view}
```
***

### Sections
#### New Map Layer Section
```
set %variable% to [a] [new] map layer
```
Creates a new map layer. This is not a map item, but a map layer that can be used to render a map.

The code inside this section will be run every time the map is rendered, which is every five ticks for
each map item in a player's inventory and approximately every 50 ticks for each map on an item frame.

These can add up quickly, so keep your code as efficient as possible inside the section.

**Examples**
```
set {_layer} to a new map layer:
    color pixels within 0, 0 and 127, 127 on the map in radius 10 of 64, 64
add {_layer} to map layers of {_view}
```
***

### Functions
#### Map Colour
```
mapColor(color: color)
mapColorRGB(red: int, green: int, blue: int)
```
Returns a map colour string from red, green, and blue values, or from a Skript colour, for use in colouring text on maps. This colour 
will not be exact, as there is a limited number of colours available on maps. The colour string is in the format `"§x;"`, where `x` is the code for the map colour.

**Examples**
```
draw "%mapColor(blue)%text" at 0, 0 on the map
draw "%mapColorRGB(1, 30, 230)%text" at 0, 0 on the map
```
***
