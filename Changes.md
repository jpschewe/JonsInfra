Release 2.0.1
=============

* use java-library plugin for gradle, this reduces the libraries needed to compile in projects that depend on us

Release 2.0.0
=============

* upgrade dependent libraries
* Remove IOUtils in favor of the IOUtils class in commons-io - API breaking change 

Release 1.7.0
=============

* #4 - setup publishing to jcenter
* #3 - build with gradle
* #2 - fix issue with indentation in XML output
* switched to a markdown changelog format

Old changelog below here
-------------------------

2004-11-23  Jon Schewe  <jpschewe@mtu.net>

	* src/net/mtu/eggplant/util/gui/GraphicsUtils.java
	(GraphicsUtils): Added support for dual head systems by using
	window.getGraphicsConfiguration().getBounds() instead of
	window.getToolkit().getScreenSize().

2004-11-12  Jon Schewe  <jpschewe@mtu.net>

	* build.xml: Fixed build of api documentation for Releases.

	* Release 0.5
	
	* Updated with new build template.  Removed old code and updated
	libraries.  Depend on commons-collections now to do collections
	stuff instead of my own implementations. 

2004-04-04  Jon Schewe  <jpschewe@mtu.net>

	* src/net/mtu/eggplant/util/DefaultBean.java (DefaultBean):
	Removed deprecations.

2004-01-28  Jon Schewe  <jpschewe@mtu.net>
	
	* Release 0.4

	* src/net/mtu/eggplant/util/gui/GraphicsUtils.java (centerWindow):
	Updated to use geom package for more accurate measurements and
	fixed the calculation to use width AND height, rather than just width.

2004-01-22  Jon Schewe  <jpschewe@mtu.net>
	
	* Release 0.3

	* src/net/mtu/eggplant/util/Debug.java: Deprecated Debug in favor
	of Log4j

	* src/net/mtu/eggplant/util/DefaultBean.java (DefaultBean): Fixed
	some method names.
	(DefaultBean): Added getPropertyChangeListeners methods.

2003-07-02  Jon Schewe  <jpschewe@mtu.net>

	* src/net/mtu/eggplant/util/network/TCPServer.java: Fixed javadoc

2002-09-30  Jon Schewe  <jpschewe@mtu.net>

	* src/net/mtu/eggplant/util/BasicFileFilter.java: Modified accept
	to allow directories to be selected.  This allows one to traverse
	through directories. 

2002-08-25  Jon Schewe  <jpschewe@mtu.net>
	
	* Release 0.2
	
2002-08-25  Jon Schewe  <jpschewe@mtu.net>

	* build.xml: Upgraded to junit-3.8

2002-05-27  Jon Schewe  <jpschewe@mtu.net>

	* build.xml: Upgraded to junit-3.7

2001-01-07  Jon Schewe  <jpschewe@mtu.net>

	* prj.el: Upgraded to junit-3.4

2000-12-29  Jon Schewe  <jpschewe@eggplant.mtu.net>

	* : Moved all files into the package net.mtu.eggplant and changed
	utils to util.  Also changed all file headers for new licensing,
	BSD style rather than LGPL.
	
2000-12-01  Jon Schewe  <jpschewe@eggplant.mtu.net>

	* utils/StringUtils.java: Found a bug with the replace string
	having the search string in it and calling searchAndReplace, gets
	into an infinite loop.

2000-11-14  Jon Schewe  <jpschewe@eggplant.mtu.net>

	* utils/DefaultBean.java: Made serializable so that it's easy to
	save things out.	

	* utils/algorithms/Finding.java: Added detect method.

	* utils/ComparisonUtils.java: Added some convenient comparison
	functions.  

	* utils/gui/SortableTable.java: Added ability to sort tables.

2000-10-19  Jon Schewe  <jpschewe@eggplant.mtu.net>

	* utils/algorithms/Copying.java: Added for copying Collections.

2000-10-10  Jon Schewe  <jpschewe@eggplant.mtu.net>

	* utils/Unique.java: Made anonymous inner class named so that
	javadoc doesn't get confused.  This bug has been reported to Sun.

2000-10-04  Jon Schewe  <jpschewe@eggplant.mtu.net>

	* utils/predicates/InstanceOf.java: My first default predicate.
	More to come.

	* utils/gui/SortedListModel.java: Having a sorted list model is a
	really nifty idea, so I wrote one.  It's being used in JavaDome
	for the nodes in the main editor panel.

	* utils/event/CollectionEvent.java: Added collection events for
	collections that you can listen to. 

	* utils/algorithms/Finding.java: Added to help replace JGL.

	* utils/algorithms/Filtering.java: Java's collections framework is
	just too far from JGL.  Need to enhance it with Filters.

	* utils/UnaryPredicate.java: Needed for Finding and Filtering classes.

	* utils/Unique.java: Added default comparator for use when
	creating total order comparators.

	* utils/DefaultUnique.java: Added a correct equals method.

	* utils/CollectionUtils.java: Needed instanceof check for objects
	in a container, particularly for assertions.

	* utils/Bean.java: Now follows standard naming conection where
	interface is the name used and Default is prepended for default class.

	* utils/gui/BetterBoxLayout.java: Added for better layout.  Swing's box
	doesn't size so good. 

