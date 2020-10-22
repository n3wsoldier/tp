# AniChan User Guide



## Table of Contents
1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
<br/>&nbsp;3.1 [View the help: `help`](#31-view-the-help-help)
<br/>&nbsp;3.2 [Adding a user: `addws`](#32-adding-a-user-adduser)
<br/>&nbsp;3.3 [Switching users: `switchws`](#33-switching-users-switchuser)
<br/>&nbsp;3.4 [Browse through all Anime: `browse`](#34-browse-through-all-anime-browse)
<br/>&nbsp;3.5 [Watchlist management: `watchlist`](#35-watchlist-management-watchlist)
<br/>&nbsp;3.6 [Add an Anime to the current watchlist: `add`](#36-add-an-anime-to-the-current-watchlist-add)
<br/>&nbsp;3.7 [Remove an Anime from the current watchlist: `remove`](#37-remove-an-anime-to-the-current-watchlist-remove)
<br/>&nbsp;3.8 [View all anime in watchlist: `view`](#38-view-all-anime-in-watchlist-view)
<br/>&nbsp;3.9 [Bookmark an Anime: `bookmark`](#39-bookmark-an-anime-bookmark)
<br/>&nbsp;3.10 [Search: `search`](#310-search-search)
<br/>&nbsp;3.11 [View the information of an Anime: `info`](#311-view-the-information-of-an-anime-info)
<br/>&nbsp;3.12 [Estimate time need to translate script: `estimate`](#312-estimate-time-needed-to-translate-script-estimate)
<br/>&nbsp;3.13 [Exit AniChan: `exit`](#313-exit-anichan-exit)
<br/>&nbsp;3.14 [Saving and loading data](#314-saving-and-loading-data)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction

AniChan is an all-rounded tool to effectively create and organize anime lists with viewing statistics, efficiency-focused features, and tools to improve anime-watching experience.


## 2. Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest version of `AniChan` from [here](https://github.com/AY2021S1-CS2113T-F12-2/tp/releases/tag/V1.0).
3. Copy the file to the folder you want to use as the home folder for your AniChan.
4. Open cmd and change directory into the folder. Run “java -jar anichan.jar”.
5. Type a command into the command prompt and press ‘Enter’ to execute it. e.g. typing `help` and pressing ‘Enter’ will display the help message.


## 3. Features 
**Command Format**

* Commands are case-sensitive.
E.g. Only `help` will work, and so `HELP`, `hElp`, and its other variant will not work.

* Words in UPPERCASE are values that can be supplied by the user.
E.g. in `browse -s SORT_CATEGORY -p PAGE_NUMBER` where SORT_CATEGORY and PAGE_NUMBER are 
parameters that can be used as `browse -s name -o asc`.

* Square brackets indicate optional parameters.
E.g. `browse [-s SORT_CATEGORY]` can be used simply as `browse` or `browse -s name`.
  
* The order of parameters are not important.
E.g. Both `-n USERNAME -dob DATE_FORMAT` and `-dob DATE_FORMAT -n USERNAME` are 
both acceptable and will produce the same output.  

<br/><br/>

### 3.1 View the help: `help`
This command will provide the details of all available commands and their usage. 
This is done by displaying the ‘Command Summary’ as listed below to the user. 

Format: `help`

<br/>

### 3.2 Adding a user: `addws`
Adds a new workspace to user.

Format: `addws -n <NAME>`

Example of usage: `addws -n Crispy Donuts Studio`

The expected outcome:
```
Successfully added new workspace: Crispy Donuts Studio
```

<br/>

### 3.3 Switching users: `switchws`
Switch the current active workspace of user.

Format: `switchws -n <NAME>`

Example of usage: `switchws -n Crispy Donuts Studio`

The expected outcome:
```
Workspace changed to Crispy Donuts Studio
```

<br/>

### 3.4 Browse through all Anime: `browse`
Browse through all anime from the source. It can be displayed in sorted order.

Format: `browse [-s SORT_CATEGORY] [-o DISLAY_ORDER] [-p PAGE_NUMBER`]

Note: 

`-s name` will sort the list by alphabetical order

`-s rating` will sort the list by rating

`-o asc` will arrange it in ascending order

`-o dsc` will arrange it in descending order

`-p <N>` will display page N of the list

`-p <N>` N must be a positive integer value

The order of the parameter does not matter

If no parameters or only `-o` is specified then it will display in its anime id order.

Example of usage: `browse -s name -p 1 -o dsc`

The expected outcome:
```
1. .hack//Gift
2. .hack//Legend Of The Twilight
3. .hack//Liminality
4. .hack//Sign
5. 3x3 Eyes
6. A Chinese Ghost Story
7. ARIA The ANIMATION
8. Abashiri Ikka
9. Ace wo Nerae!
10. Ace wo Nerae! 2
11. Ace wo Nerae: Final Stage
12. After War Gundam X
13. Agatha Christie's Great Detectives Poirot and Marple
14. Agent Aika
15. Ah! My Goddess: The Movie
16. Ai Yori Aoshi
17. Ai Yori Aoshi: Enishi
18. Aim for the Ace! (1979)
19. Air
20. Air Master
Browsing Page: 1
```

<br/>

### 3.5 Watchlist management: `watchlist`
This command handles all watchlist management related operations: 
* Create a new watchlist.
* List all created watchlist(s).
* Select another watchlist to use.
* Delete a watchlist that is no longer needed.

Note:
* Active watchlist refers to the watchlist that you are currently using for 
adding anime into or removing anime from.

Format: 
* `watchlist -n <WATCHLIST_NAME>`
* `watchlist -l`
* `watchlist -s <WATCHLIST_INDEX>`
* `watchlist -d <WATCHLIST_INDEX>`

Example of usage: `watchlist -n Adventure Anime`
* Ensure the watchlist name is unique in your workspace.

The expected outcome: 
```
Watchlist "Adventure Anime" has been created successfully!
```

Example of usage: `watchlist -l`

The expected outcome: 
```
Currently, you have 2 watchlist(s):
	1. Default
	2. Adventure Anime
```

Example of usage: `watchlist -s 2`
* Selected watchlist is also known as the **active watchlist**, which is the one
that you are using for adding anime into or removing anime from.
* Notice how the name of the watchlist in the bracket of your prompt have changed.

The expected outcome: 
```
"Adventure Anime" is now your active watchlist!
```

Example of usage: `watchlist -d 2`
* For deletion to succeed, you must have at least two watchlist.
* If the currently active (selected) watchlist is deleted, then AniChan will automatically set
the first watchlist in the list of watchlist to be the new active watchlist.

The expected outcome: 
```
Watchlist "Adventure Anime" has been deleted successfully!
Changed active watchlist to: "Default".
```

<br/>

### 3.6 Add an Anime to the current watchlist: `add`
Add an anime to the currently selected watchlist

Format: `add -a <ANIME_ID>`

Example of usage: `add -a 3`

The expected outcome: 

```
Trigun added to watchlist!
```

<br/>

### 3.7 Remove an Anime from the current watchlist: `remove`
Remove an anime from the currently selected watchlist

Format: `remove -d <ANIME_ID_IN_WATCHLIST>`

Note:

The index used has to be the Anime ID in the watchlist, and not the general Anime ID

Example of usage: 'remove -d 1'

The expected outcome:

```
Trigun successfully removed from watchlist
```

<br/>

### 3.8 View all anime in watchlist: `view`
View all anime that is in the specified watchlist

Format: `view -v <WATCHLIST_ID>`

Example of usage: 'view -v 1'

The expected outcome:
```
Here are the anime in Default watchlist:
	1. Cowboy Bebop
	2. Witch Hunter Robin
```

<br/>

### 3.9 Bookmark an Anime: `bookmark`
This command handles all bookmark related operations: 
* List all anime within bookmark.
* Add an anime into bookmark.
* Delete an anime from bookmark. 
* Edit episode for an anime within bookmark
* View details of an anime within bookmark [v2.0]

Format: 

`bookmark -l` will list bookmark

`bookmark -a <ANIME_ID>` will add the Anime into bookmark

`bookmark -d <BOOKMARK_ID>` will delete the Anime with bookmark id from bookmark

`bookmark <BOOKMARK_ID> -e <EPISODE>` will edit the episode for Anime with bookmark id

Example of usage: `bookmark -l`

The expected outcome: 
```
 Listing all anime in bookmark: 
	1. InuYasha the Movie 3: Swords of an Honorable Ruler Ep: 5
	2. To Heart
```

Example of usage: `bookmark -a 410`

The expected outcome: 
```
Saving 410. InuYasha the Movie 2: The Castle Beyond the Looking Glass to bookmark.
```

Example of usage: `bookmark -d 1`

The expected outcome: 
```
Removing InuYasha the Movie 3: Swords of an Honorable Ruler! :(
```

Example of usage: `bookmark 1 -e 5`

The expected outcome: 
```
Editing InuYasha the Movie 3: Swords of an Honorable Ruler to have 5 episode
```

<br/>

### 3.10 Search `search`
Searches for a specific anime or to search for a specific genre

Format:

`search -n <SEARCH_TERM>` will search for all anime series that contains the search term

`search -g <SEARCH_TERM>` will list all genres that matches the search term

Example of usage: `search -n bey`

The expected outcome:
```
[ID:216] Haruka: Beyond the Stream of Time – A Tale of the Eight Guardians
[ID:257] Beyblade
[ID:410] InuYasha the Movie 2: The Castle Beyond the Looking Glass
```

Example of usage: `search -g Slice of Life`

The expected outcome:
```
[ID:7] Honey and Clover
[ID:8] Hungry Heart: Wild Striker
[ID:35] Ai Yori Aoshi
[ID:39] Beck: Mongolian Chop Squad
[ID:48] Azumanga Daioh: The Animation
[ID:81] Air
...
[ID:446] Strawberry Marshmallow
[ID:447] KamiChu!
[ID:464] I My Me! Strawberry Eggs
[ID:467] Kiki's Delivery Service
[ID:475] Marmalade Boy Movie
[ID:488] Teacher's Time
```

<br/>

### 3.11 View the information of an Anime: `info`
View all the information regarding a specific anime

Format: `info -a <ANIME_ID>`

Example of usage: `info -a 1`

The expected outcome:
```
Here is the information for the anime:
Index: 1
Name: Cowboy Bebop
Episodes: 26
Release Date: 03/Apr/1998
Rating: 86
Genre: [Action, Adventure, Drama, Sci-Fi]
```

### 3.12 Estimate time needed to translate script: `estimate`
Estimates the time required to finish translating a script, users may provide
their estimated words per hour speed or use the average translator speed as an estimate.

Format: `estimate <SCRIPT_FILE_NAME> [-wph WORDS_PER_HOUR]`
* **Only one** `.txt` file is accepted by AniChan.
* You have to specify the file extension too! E.g. `script.txt`.
* If the option `-wph` is not specified, **AniChan** will calculate the estimation timings
using the average translator's translation speed of 400, 500, and 600 words per hour. 
This will produce three estimation timings for you to consider.

Example of usage: `estimate script.txt`

The expected outcome: 
```
Average translator (400 words per hour) takes: 5 hour(s) 47 minute(s).
Average translator (500 words per hour) takes: 4 hour(s) 38 minute(s).
Average translator (600 words per hour) takes: 3 hour(s) 51 minute(s).
```

<br/>

Example of usage: `estimate script.txt -wph 777`

The expected outcome:
```
You would need 2 hour(s) 58 minute(s).
```

### 3.13 Exit AniChan: `exit`
Exit AniChan 

Format: `exit`

Example of usage: `exit`

The expected outcome:
```
Sayonara <NAME>!
```

<br/>

### 3.14 Saving and loading data
User, workspace(s), watchlist(s), and bookmark(s) data will be **saved automatically** when they are 
created or modified, and will be **loaded automatically** when AniChan is launched. 

In the folder where AniChan is launched, there will be a `data` folder which would contain these data:
* User data is stored in `data/user.txt`.
* Watchlist(s) data are stored in `data/<WORKSPACE-NAME>/watchlist.txt`, e.g. if your workspace is named "AniTranslator",
then the watchlist data can be found in `data/AniTranslator/watchlist.txt`.
* Bookmark(s) data are also stored in the same location as watchlist data, `data/WORKSPACE-NAME/bookmark.txt`.

## 4. FAQ
Coming soon!


## 5. Command Summary

|Feature|Command|
|---|---|
| Help                                     | `help`                                                   |
| Add workspace                            | `addws -n <NAME>>`                                       |
| Switch workspace                         | `switchws -n <NAME>`                                     |
| Browse                                   | `browse -s [name/rating] -p <1-26> -o [asc/dsc]`         |
| Create watchlist                         | `watchlist -n <WATCHLIST_NAME>`                          |
| List all watchlist                       | `watchlist -l`                                           |
| Select watchlist                         | `watchlist -s <WATCHLIST_INDEX>`                         |
| Delete watchlist                         | `watchlist -d <WATCHLIST_INDEX>`                         |
| Add to watchlist                         | `add -a <ANIME_ID>`                                      |
| Remove from watchlist                    | `remove -d <ANIME_ID_IN_WATCHLIST>`                      |
| View Anime in watchlist                  | `view -v <WATCHLIST_ID>`                                 |
| Search by title                          | `search -n <SEARCH_TERM>`                                |
| Search by genre                          | `search -g <SEARCH_TERM>`                                |
| View Anime information                   | `info -a <ANIME_ID>`                                     |
| Estimate time needed to translate script | `estimate <SCRIPT_FILE_NAME> [-wph WORDS_PER_HOUR]`      |
