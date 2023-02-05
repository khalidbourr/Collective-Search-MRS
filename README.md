# Collective Search MRS
 Using ROS and X-klaim for programming robot collective search.

In this scenario, four robots are tasked with locating every flag in the area without prior knowledge of their positions. The robots move randomly, avoid obstacles, and get information on their proximity to the flags. Once the distance falls below a certain threshold, it indicates that the flags have been discovered, and all other robots are notified. When every flag has been located, all robots halt.

# Install


1. clone the repository: `git clone https://github.com/khalidbourr/Collective_Search_MRS`.
2. change directory: `cd Collective_Search_ws/Flag_ws/src/multi_robot/worlds`.
3. copy models to gazebo.
```
$ sudo cp -r red_flag ~/.gazebo/models
$ sudo cp -r green_flag ~/.gazebo/models
$ sudo cp -r blue_flag ~/.gazebo/models
$ sudo cp -r white_flag ~/.gazebo/models
$ sudo cp -r basic_box ~/.gazebo/models
$ sudo cp -r walls_evry ~/.gazebo/models
```
4. Change directory to: `cd Collective_Search_ws/Flag_ws`
5. Build the workspace: `catkin_make`

# run scenario

## terminal 1: 

```
$ cd Collective_Search_ws/Flag_ws
$ source devel/setup.bash
$ roslaunch rosbridge_server rosbridge_websocket.launch 
```
## terminal 2

```
$ cd Collective_Search_ws/Flag_ws
$ source devel/setup.bash
$ roslaunch multi_robot multi_robot.launch 
```
