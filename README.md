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

        set {_layer} to new fixed map layer:
            draw resized map image named "windmillsmall.jpg" on the map at pixel 0, 0
            
        set {_x} to 0
        set {_y} to 0 
        set {_layer2} to new map layer:
            colour pixels between pixel 20, 20 and pixel 108, 20 on the map blue
            colour pixels between pixel 20, 20 and pixel 64, 44  on the map blue
            colour pixels between pixel 64, 44 and pixel 108, 20 on the map blue
            colour pixels in radius 20 of pixel 100, 100 on the map green
            render an oval with width 10 and height 25 at pixel 50, 80 on the map in rgb(183,50,100)
            colour pixel {_x}, {_y} on the map red
            add 1 to {_x}
            if {_x} is 128:
                set {_x} to 0
                add 1 to {_y}


        set map layers of {_view} to {_layer}, {_layer2}

        set map view of {_item} to {_view}
        give {_item} to player
```

## Syntax:
### Effects
#### Draw Map Content
```
(draw|render) %image/string% on the map at %mappixel%
```
Draws text or images on a map. Must be used in a map layer section.

**Examples**
```
set {_img} to the map image from file "myimage.png"
set {_layer} to a new map layer:
  draw {_img} on the map at pixel 0, 0
  draw "%mapColor(red)%%Hello, world!" on the map at pixel 40, 60
```
***

#### Draw Map Pixels
```
colo[u]r %mappixels% on the map [as] %color%
```
Sets the color of one or more pixels on a map. Must be used in a map layer section.

**Examples**
```
set {_layer} to a new map layer:
    color the pixel at 0, 0 on the map as red
```
***

#### Draw Map Line
```
colo[u]r [the] pixels (between|from) %mappixel% (and|to) %mappixel% on the map [as] %color%
(draw|render) [a] line (between|from) %mappixel% (and|to) %mappixel% on the map [with|in|using] %color%
```
Draws a line on a map. Must be used in a map layer section.

**Examples**
```
set {_layer} to a new map layer:
    colour the pixels between pixel 0, 0 and pixel 127, 127 on the map red
    draw a line from pixel 88, 20 to pixel 40, 20 on the map using red
```
***

#### Draw Map Oval
```
colo[u]r [the] pixels in radius %number% [of|around] %mappixel% on the map [as] %color%
(draw|render) [a] [solid] circle (with|of) radius %number% (at|around) %mappixel% on the map [with|in|using] %color%
(draw|render) [a[n]] [solid] oval (with|of) width %number% and height %number% (at|around) %mappixel% on the map [with|in|using] %color%
```
Draws an oval or a circle on a map. Must be used in a map layer section.

**Examples**
```
set {_layer} to a new map layer:
    colour the pixels in radius 25 of pixel 127, 127 on the map red
    draw an oval of width 20 and height 30 around pixel 40, 20 on the map using red
```
***

#### Draw Map Rectangle
```
colo[u]r [the] pixels witihin %mappixel% (and|to) %mappixel% on the map [as] %color%
(draw|render) [a] [solid] rect[angle] (within|from) %mappixel% (and|to) %mappixel% on the map [with|in|using] %color%
```
Draws a rectangle on a map. Must be used in a map layer section.

**Examples**
```
set {_layer} to a new map layer:
    colour the pixels within pixel 0, 0 and pixel 127, 127 on the map red
    draw a rectangle from pixel 88, 20 to pixel 40, 40 on the map using red
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
    draw {_img} on the map at pixel 0, 0
```
***

#### Map Pixel
```
[the] [map] pixel [at] %number%,[ ]%number%
```
Represents the coordinates of a pixel on a map.

**Examples**
```
set {_pixel} to the map pixel at 0, 0
set {_layer} to a new map layer:
    colour the pixel at {_pixel} on the map blue
    draw a rectangle from pixel 100, 100 to {_pixel} on the map using blue
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
[a[n]] [new] map view (from|with) id %number%
```
Creates a new empty map view. This is not a map item, but a map view that can be used to render a map.
To assign this map view to a map item, use `set {_map}'s map view to {_view}`.

This can also be used to get a map view from its id. If a map view with the given id does not exist, this will return null.

**Examples**
```
set {_view} to a new map view
set {_layer} to a new map layer:
    color pixels within pixel 0, 0 and pixel 127, 127 on the map blue
add {_layer} to map layers of {_view}
set {_map}'s map view to {_view}
```
***

### Sections
#### New Map Layer Section
```
set %variable% to [a] [new] [fixed] map layer
```
Creates a new map layer. This is not a map item, but a map layer that can be used to render a map.

The code inside this section will be run every time the map is rendered, which is every five ticks for 
each map item in a player's inventory and approximately every 50 ticks for each map on an item frame.

These can add up quickly, so keep your code as efficient as possible inside the section. Alternatively, you can use the "fixed" tag to render the layer only once per player.

Also, note that local variables will persist through each render. This allows you to, for example, keep track 
of how many times the layer has been rendered by adding 1 to a local variable each time.

**Examples**
```
set {_layer} to a new map layer:
    color pixels within pixel 0, 0 and pixel 127, 127 on the map blue
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
