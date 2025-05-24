## v0.31(next)
+ Added a new configurable option that changes how hoes work. Normal usage with right click is removed and players must use the old school way of breaking grass with left click.
+ Added tooltips for all config options when looking at them in Mod Menu
+ Changed creeper explosion to be calculated from their eyes instead feet (also made it toggleable feature via mod menu)
+ Fixed a bug where Hearty Stew & Chicken Soup would not give back an empty bowl item when finished using it if the player inventory is full.
+ Fixed incompatibility with mods that add custom creepers to not get affected by the creeper shearing and ruin their intended experience.
+ Updated the mod to Fabric API 0.116.0, Fabric Loader 0.16.14 & BTWR Shared Library 0.53

## v0.30.1
+ Fixed a bug with older data files from version 0.29 that were causing issues.

## v0.30
### WARNING! Game breaking update, back up your worlds if you don't want to lose some items/blocks.
+ Removed the Hemp plant block and all of its associated items as it's no longer needed in the scope of this mod. They will be removed from your worlds.
+ Moved the "Brick Drying" functionality to another unreleased mod. This includes the Wet Brick & Brick blocks & items, which means they will also be removed from your worlds.
+ Moved the SHEARS_EFFICIENT block tag to BTWR-SL mod
+ Fixed some mixin related bugs on mod launch with other mods requiring older versions of BTWR Shared Library
+ Updated the mod to Fabric API 0.115.0, Fabric Loader 0.16.10 & BTWR Shared Library 0.47

## 0.29
+ Added a new common block tag "WOODEN_MISC_BLOCKS" for miscellaneous blocks that aren't tagged as wooden,
but should be treated as such. This was created especially for the "Don't spawn mobs on wood" config option,
but can be used by other purposes eventually.

+ Made vine blocks harvestable with Diamond shears.
+ Changed the efficiency conditions for shears tools and added a specific SHEARS_EFFICIENT block tag.
+ Fixed Shears & Diamond Shears not making a crafting sound when crafted.
+ Fixed a bug where certain items that should have a returning stack on crafting them with recipes to
properly return the item used (like buckets, axes in recipes, etc.)
+ Fixed mod description to display properly in the mod list.
+ Fixed the mod's license to show properly (changed from MIT to CC-BY 4.0)
+ Removed the Cooked Carrot and Boiled Potato items (moved them to an unreleased mod)
+ Removed the default 16 item count for the newly added food items (back to default 64)
+ Updated the mod to BTWR-SL 0.44

## 0.28.2
+ Added the ability to make mobs unable to spawn on all types of wood blocks. This can be disabled in the config.
+ Added config option to make spawn capacity of monsters per chunk slightly higher. This can be disabled in the config.

+ Added Diamond Shears to the Conventional Item Tags SHEAR TOOLS
+ Added Shears cutting sound for crafting Cut Leather recipes.
+ Moved shear recipes to use the conventional item tag SHEAR_TOOLS.
+ Fixed the names of tanned and scoured cut leather items being swapped.
+ Improved the naming of switchable options in the config file and the configuration menu through Mod Menu.
+ Updated the project to Fabric API 0.114.0 && to require BTWR: Shared Library 0.40.

### 0.28.1
+ Made the mod (actually) depend on BTWR- Shared Library mod
+ Fixed a bug that caused the game to crash when a block was updating the Brick-Placed block.
+ Moved the COOKED_MEATS_FOR_SANDWICH item tag to BTWRConventionalTags in BTWR Shared Library
+ Changed Hearty Stew to use the COOKED MEAT FOODS Conventional Item tag

## 0.28
+ Enabled (fixed) the ability to knockback entities without the proper weapon if the player is sprinting
+ Fixed a bug that crashed the game when attacking silverfish
+ Fixed being able to melee knockback entities with crossbow or bow while knockback restrictions are enabled.
+ Fixed a server side bug that caused breaking of directional dropping blocks to crash the client.
+ Fixed Shear tools from being too slow in breaking Hemp.
+ Fixed placement sounds for some brick blocks
+ Code refactoring/improvements
+ Removed the following items because  they have no use in the scope of this mod:
  Filament, Gear, Belt, Strap, Padding, Rope, Stone Brick -> (moved to Tough Environment).
+ Removed LightBlock and Rope Coil block for the same reason
+ Removal of the LightBlock also fixed a bug that caused its item to get registered twice and crashed the client on connecting to dedicated servers.
+ Reverted the name change of Stick to Shaft

+ Updated the mod to Fabric API 0.110.0 & Fabric Loader 0.16.9

## 0.27

+ Added new food items:
1. Ham and Eggs
2. Scrambled Eggs (raw and cooked)
3. Mushroom Omelette (raw and cooked)
4. Chowder
5. Steak and Potatoes
6. Kebab (raw and cooked)
7. Steak, Pork & Wolf Dinner Stews (Wolf dinner unacquirable because no wolf meat)
8. Chicken Soup & Hearty Stew

+ Changed the newly added food items max stack limit to 16

## 0.26.1

+ Fixed a bug where breaking leaves with Diamond Shears didn't drop the block & another one where it was breaking them very slow.
+ Conventional tags changes;
  added new gears item tag for all mechanical gear items that are interchangeable for btwr ones.
  moved hemp fibers to the "strings" conventional item tag from fabric.
+ Updated the mod to Fabric Loader 0.16.7 & Fabric API 0.107.0

## 0.26

+ Slightly reworked directional drop logic so items should drop better than before.
+ Updated the mod to Fabric Loader 0.16.5 & Fabric API 0.104.0

## 0.25.3

+ Fixed Diamond Shears breaking leaves slower than iron ones & fixed some leaves loot tables to drop properly.
+ Fixed a bug where you could do knockback with a bow or crossbow from melee hits to entities.

## 0.25.2

+ Fixed a bug with custom shapeless recipes that have additional drops to drop properly.
+ Fixed placement code for bricks items as blocks to work much better(and as intended).
+ Fixed missing recipes and generally improved the code that generates them.

## 0.25.1

+ Updated 0.25.1 to latest minecraft versions

## 0.25

+ Added sandwich food item.
+ Removed recipe for cooking brick on campfires.
+ Changed water buckets to place non-persistent water, instead of source blocks.
+ Changed waterlogged blocks to dissipate their water when broken.
+ Optimized directional dropping code for mods that support it (like Sturdy Trees & Tough Environment)
+ Fixed code to work properly on the server side.

## 0.24

+ Added Unfired Brick item & block & the ability to sun dry them into regular bricks.
+ Added Diamond Armor Plate item and changed recipes for making diamond armor.
+ Added Fuel values to all "flammable" items like wooden club, gear and all hemp derivatives.
+ Added recipes for cut leather to be used with some existing recipes that require normal leather.
+ Added missing recipe for making Strap.
+ Rebalanced Shears block breaking speed.
+ Made the Brick item placeable in the world as a block.
+ Fixed a bug with creepers not igniting with fire charge.
+ Fixed Strap recipe only giving one strap instead of 4
+ Changes to BTWR conventional tags.

## 0.23.2

+ Fixed a bug with axes getting consumed on crafting with them.
+ Updated the mod to Fabric Loader 0.15.11

## v0.23.1

+ Added Tanned Leather Armor.
+ Added Recipes for acquiring Scoured & Tanned Leather.
+ Increased Shears mining speed for effective blocks.

+ Updated the mod to Fabric Loader 0.15.10


## v0.23

I decided that by default this mod will not change the balance of the game.
Most of that will happen in the BTWR Modpack itself.

+ ### Added the following items/blocks:
  - Gear
  - Belt
  - Strap
  - Stone Brick
  - Hemp Crop, Hemp Fibers & Hemp Fabric
  - Light Block
  - Rope
  - Rope Coil
  - Scoured & Tanned leather
  - Cut Leather for all 3 variations (normal, scoured, tanned)

+ ### Added some Config options toggles through Mod Menu:
  - For disabling Baby Zombies spawning.
  - For knockback restrictions (needing a weapon item to do knockback to entities)
  

+ Added Custom Conventional tags to handle interactions between the sidemods better.

* Fixed being unable to shear sheep with Diamond Shears.

+ Brought back Wooden & Stone sword recipes.
+ Restored vanilla mining speeds & durability of all tools.
+ Updated lang file to modify name of Stick to Shaft.

## v0.22

+ Added Wooden & Bone Club weapons.
+ Added Creeper Oysters Item.
+ Added Diamond Ingot Item.
+ Added Diamond Shears Item.
+ Added Sounds to certain item when they are crafted for extra immersion. :)

+ Changed monsters and animals to no longer take knockback if you aren't using a weapon item. (Club, Sword, Bow, Crossbow && Trident)
+ Changed how creepers work. You can now neuter them by right-clicking on them with Shears.
+ Changed the durability and mining speed of all tools. This makes getting iron tools(& above) much more useful.
+ Changed name of Stick to Shaft.

+ Removed Wooden & Stone Sword.

+ Brought back Wooden & Stone Sword recipes.
+ Restored vanilla mining speeds & durability of all tools.
+ Restored the ability to knockback with any weapon.

+ # Release Alpha

+ Initial public release
+ Versions before 0.22 were private testing only.

