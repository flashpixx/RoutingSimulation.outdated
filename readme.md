# Light-Jason - Lightwire version of AgentSpeak(L) GridWorld

![Circle CI](https://circleci.com/gh/LightJason/GridWorld.svg?style=shield)


## <a name="info">Information</a>

* [JMX](http://www.eclipse.org/jetty/documentation/current/jmx-chapter.html)
* [JPS](https://github.com/ClintFMullins/JumpPointSearch-Java) / [Description](https://harablog.wordpress.com/2011/09/07/jump-point-search/)
* [JPS+ Goal Bounding](https://github.com/SteveRabin/JPSPlusWithGoalBounding)
* [Path Finding](http://www.cokeandcode.com/main/tutorials/path-finding/)
* [CSS Voxels](http://www.voxelcss.com/)
* [Jar push to local Maven repository](http://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-in-maven-project)
* [Game Art](http://opengameart.org/)
* [Routing](http://simblob.blogspot.de/2016/02/updating-my-introduction-to-a.html)


## Requirements

* [JRE 1.8](http://www.java.com/)

### Development

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/)
* [Maven 3 or higher](http://maven.apache.org/)
* [Doxygen](http://www.doxygen.org/) with [GraphViz](http://www.graphviz.org/)
* [Source code documentation](http://flashpixx.github.io/Light-Jason-GridWorld/)


## <a name="download">Current Developer Download</a>

* [Windows Executable](https://github.com/flashpixx/Light-Jason-GridWorld/raw/binary-master/Light-Jason-GridWorld-0.1.exe)
* [Native Jar](https://github.com/flashpixx/Light-Jason-GridWorld/raw/binary-master/Light-Jason-GridWorld-0.1.jar)


## <a name="szenario">Szenario</a>

Distribution or standalone grid-world game with [LibGDX](https://libgdx.badlogicgames.com/) and 
[HTML](https://github.com/libgdx/libgdx/wiki/Deploying-your-application#deploy-to-htmljs). Each agent must search food and must eat to grow up, can bite other
agents to get power. The bited agent lost power. The weight of the agent defines the biting power, also an agent can be growl other agent to warn. The user can
be add pinboard, agent generates (of it own agents) and define food generators (but the own user agents don't eat own food). Each agent can be seperate itself
to smaller agents. Each agent can be push / pull / list a pinboard on the grid world to left messages (a pinboard is limited to a fixed number of items). The
user can send a signal to all own agents to come home (the distance from the home grid to the agent current position defines the costs of the call). Each
action decreases the current power of the agent, e.g. for large agents a bit is cheap, but moving expensive. If an agent lose its power, it dies. Each agent
has some skills. These attributes can be modified or transfer to other agents (dealing).

* Agent actions
    * eat (small agents are picky to eat own food)
    * bite/shoot (see _Weapons_)
    * growl (only an agent is near - distance based on the weight)
    * talk (only an agent is near - one cell and one agent)
    * shout (distanced based a parameter value and current power)
    * move (moving power is defined by the underling tilemap)
    * split (based on the current weight - weight is divide to the number of agents)
    * cure (another agent - if another team agent, the cure information is cached so a bit / growl is reduced)
    * home (returns the current direction to the home grid)
    * member (returns the position - if exists - of the nearest agent of my team)
    * invite (agent can invite another agent to be a member of the team - defined only to one agent)
    * abandon (an invited agent can removed from the team membership)
    
* Agent attributes
    * element-based: water, sand, grass
    * action-based: bite, shoot
    * explore-based: forsight, acuity
    
* Perceive
    * an agent percive its environment based on the power and weight
    * weight defines the distance
    * power the probability of the visibility
    
* Team member
    * an agent can be invited to another team, so the power is increased because the team size is increased
    * if a team member bits or growl to another team member the power is decreased
    * if eat is failed, because a team member has picked up earlier the power is decreased
    
* Generators
    * generates agents based on a script
    * script can be changes all the time
    * generator number is limited
    * the agent generating process depends on a probability which is defined on the number of generators
    * sources reduce the global power (costs)
    
* Food sources
    * food sources are limited but can set on any grid cell
    * the source generates around itself some food elements 
    * sources reduce the global power (costs)
    
* Pinboard (agent is near to pinboard - one cell)
    * push message
    * list (all messages)
    * pull message
    
* Grid World ([Web RPG](http://rpgjs.com/) / [LibGDX RPG](https://github.com/libgdx/libgdx/wiki/Tile-maps))
    * agents can be moved on the current grid to each position (except generators, pinboard and other agents)
    * if an agent is moved to the border of a local grid, only the number of agents and current food elements is transfered from the neighbour
    * on each grid the coherency is calculated
    * a tilemap is defined for the moving costs of the agent
    

* User challenge
    * maximize the power over all own agents, but the power is definied by the weight, the rate of movements of all agents and distance from home
    * define a good home grid world (each user can define the number of cells individually)
    * position of agent and food generators can be individual designed and modified
    * the tilemap can be also modified by the user with a [Web Map Generator](https://github.com/elias-schuett/Online-Tile-Map-Editor) 
    [Standalone Map Generator](http://www.mapeditor.org/download.html)
    
* Wormholes
    * connect two cells on a grid (also possible to connect cells located on different grids)
    * allow instant travel between connected 
    * static: connection endures over game time
    * dynamic: connection changes either cyclic, e.g. A<=>B -> A<=>C -> B<=>C -> A<=>B -> ... or randomly with fixed endpoints or also random endpoints
    
* Walls
    * impenetrable
    * movable
    * destructible
  
* Weapons
    * bite (only an agent is near - one cell, bite power is based on the weight)
    * arrow shot (at another agent's direction, effects and hit probability decreases over distance)
    * scatter shot (similar to _arrow shot_ but also effects neighboring cells)
    